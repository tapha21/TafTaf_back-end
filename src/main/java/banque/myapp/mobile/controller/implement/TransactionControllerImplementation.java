package banque.myapp.mobile.controller.implement;

import banque.myapp.data.models.entity.Transaction;
import banque.myapp.data.models.entity.User;
import banque.myapp.data.models.enums.TypeTransaction;
import banque.myapp.mobile.controller.interfa.TransactionControllerInterface;
import banque.myapp.mobile.dto.request.TransactionRequestDto;
import banque.myapp.mobile.dto.response.TransactionResponseDto;
import banque.myapp.mobile.mapper.TransactionMapperMobile;
import banque.myapp.service.interfac.TransactionService;
import banque.myapp.service.interfac.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class TransactionControllerImplementation implements TransactionControllerInterface {

    private final TransactionService transactionService;
    private final UserService userService;
    private final TransactionMapperMobile transactionMapperMobile;

    public TransactionControllerImplementation(
            TransactionService transactionService,
            UserService userService,
            TransactionMapperMobile transactionMapperMobile) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.transactionMapperMobile = transactionMapperMobile;
    }



    @Override
    public ResponseEntity<Map<String, Object>> getById(String id) {
        Map<String, Object> response = new HashMap<>();
        Transaction transaction = transactionService.findById(id);
        if (transaction != null) {
            TransactionResponseDto dto = transactionMapperMobile.toTransactionResponseDto(transaction);
            response.put("success", true);
            response.put("data", dto);
            return ResponseEntity.ok(response);
        }
        response.put("success", false);
        response.put("message", "Transaction introuvable");
        return ResponseEntity.badRequest().body(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> envoyerArgent(TransactionRequestDto request) {
        Map<String, Object> response = new HashMap<>();

        // Trouver l’envoyeur (connecté)
        User envoyeur = userService.findByNumero(request.getNumeroEnvoyeur());
        if (envoyeur == null) {
            response.put("success", false);
            response.put("message", "Envoyeur non trouvé");
            return ResponseEntity.badRequest().body(response);
        }

        // Trouver le receveur via son numéro
        User receveur = userService.findByNumero(request.getNumeroReceveur());
        if (receveur == null) {
            response.put("success", false);
            response.put("message", "Receveur non trouvé");
            return ResponseEntity.badRequest().body(response);
        }

        double montant = request.getMontant();

        if (envoyeur.getSolde() < montant) {
            response.put("success", false);
            response.put("message", "Solde insuffisant");
            return ResponseEntity.badRequest().body(response);
        }

        // Mise à jour des soldes
        envoyeur.setSolde(envoyeur.getSolde() - montant);
        receveur.setSolde(receveur.getSolde() + montant);

        // Sauvegarde
        userService.save(envoyeur);
        userService.save(receveur);

        Transaction transaction = transactionMapperMobile.toTransaction(request);
        transaction.setEnvoyeur(envoyeur);
        transaction.setReceveur(receveur);
        transaction.setDate(new Date());
        transaction.setType(TypeTransaction.Transfert);
        transactionService.save(transaction);

        response.put("success", true);
        response.put("data", transactionMapperMobile.toTransactionResponseDto(transaction));
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<Map<String, Object>> getSoldeUtilisateur(String idUtilisateur) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.findById(idUtilisateur);

        if (user == null) {
            response.put("success", false);
            response.put("message", "Utilisateur introuvable");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", true);
        response.put("solde", user.getSolde());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> supprimerTransaction(String id) {
        Map<String, Object> response = new HashMap<>();
        Transaction transaction = transactionService.findById(id);

        if (transaction == null) {
            response.put("success", false);
            response.put("message", "Transaction introuvable");
            return ResponseEntity.badRequest().body(response);
        }

        transactionService.deleteById(id);
        response.put("success", true);
        response.put("message", "Transaction supprimée avec succès");
        return ResponseEntity.ok(response);
    }

}
