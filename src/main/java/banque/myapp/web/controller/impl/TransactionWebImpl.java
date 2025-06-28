package banque.myapp.web.controller.impl;

import banque.myapp.data.models.entity.Transaction;
import banque.myapp.data.models.entity.User;
import banque.myapp.data.models.enums.TypeTransaction;
import banque.myapp.mobile.dto.request.TransactionRequestDto;
import banque.myapp.mobile.dto.response.TransactionResponseDto;
import banque.myapp.mobile.mapper.TransactionMapperMobile;
import banque.myapp.service.interfac.TransactionService;
import banque.myapp.service.interfac.UserService;
import banque.myapp.web.controller.inter.TransactionWeb;
import banque.myapp.web.dto.request.SimpleTransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TransactionWebImpl implements TransactionWeb {

    private final TransactionService transactionService;
    private final UserService userService;
    private final TransactionMapperMobile transactionMapperMobile;

    public TransactionWebImpl(TransactionService transactionService, UserService userService, TransactionMapperMobile transactionMapperMobile) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.transactionMapperMobile = transactionMapperMobile;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAll(int page, int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Transaction> transactionPage = transactionService.findAll(pageable);
        List<TransactionResponseDto> transactions = transactionPage.getContent()
                .stream()
                .map(transactionMapperMobile::toTransactionResponseDto)
                .collect(Collectors.toList());
        response.put("success", true);
        response.put("data", transactions);
        response.put("currentPage", transactionPage.getNumber());
        response.put("totalItems", transactionPage.getTotalElements());
        response.put("totalPages", transactionPage.getTotalPages());

        return ResponseEntity.ok(response);
    }



    @Override
    public ResponseEntity<Map<String, Object>> getById(String id) {
        Map<String, Object> response = new HashMap<>();
        Transaction transaction = transactionService.findById(id);
        if (transaction != null) {
            response.put("success", true);
            response.put("data", transactionMapperMobile.toTransactionResponseDto(transaction));
            return ResponseEntity.ok(response);
        }
        response.put("success", false);
        response.put("message", "Transaction non trouvée");
        return ResponseEntity.badRequest().body(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> annulerTransaction(String id) {
        Map<String, Object> response = new HashMap<>();
        Transaction transaction = transactionService.findById(id);
        if (transaction != null) {
            transactionService.deleteById(id);
            response.put("success", true);
            response.put("message", "Transaction annulée avec succès");
            return ResponseEntity.ok(response);
        }
        response.put("success", false);
        response.put("message", "Transaction introuvable");
        return ResponseEntity.badRequest().body(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> FaireDepot(SimpleTransactionDto request) {
        Map<String, Object> response = new HashMap<>();
        User client = userService.findByNumero(request.getNumeroClient());

        if (client == null) {
            response.put("success", false);
            response.put("message", "Client non trouvé");
            return ResponseEntity.badRequest().body(response);
        }

        double montant = request.getMontant();
        client.setSolde(client.getSolde() + montant);
        userService.save(client);

        Transaction transaction = new Transaction();
        transaction.setMontant(request.getMontant());
        transaction.setDate(new Date());
        transaction.setType(TypeTransaction.Depot);
        transaction.setReceveur(client);
        transaction.setEnvoyeur(null); // admin non traçable ici

        transactionService.save(transaction);
        response.put("message", "Dépôt effectué");
        response.put("nouveau solde", client.getSolde());
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<Map<String, Object>> FaireRetrait(SimpleTransactionDto request) {
        Map<String, Object> response = new HashMap<>();
        User client = userService.findByNumero(request.getNumeroClient());

        if (client == null) {
            response.put("success", false);
            response.put("message", "Client non trouvé");
            return ResponseEntity.badRequest().body(response);
        }

        double montant = request.getMontant();
        if (client.getSolde() < montant) {
            response.put("success", false);
            response.put("message", "Solde insuffisant");
            return ResponseEntity.badRequest().body(response);
        }

        client.setSolde(client.getSolde() - montant);
        userService.save(client);

        Transaction transaction = new Transaction();
        transaction.setMontant(request.getMontant());
        transaction.setDate(new Date());
        transaction.setType(TypeTransaction.Retrait);
        transaction.setEnvoyeur(client);
        transaction.setReceveur(null);

        transactionService.save(transaction);
        response.put("message", "Retrait effectué");
        response.put("nouveau solde", client.getSolde());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getByType(String type, int page, int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        TypeTransaction typeTransaction;
        try {
            typeTransaction = TypeTransaction.valueOf(type);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", "Type de transaction invalide");
            return ResponseEntity.badRequest().body(response);
        }
        Page<Transaction> transactionPage = transactionService.findByType(typeTransaction, pageable);
        List<TransactionResponseDto> transactions = transactionPage
                .stream()
                .map(transactionMapperMobile::toTransactionResponseDto)
                .collect(Collectors.toList());
        response.put("success", true);
        response.put("data", transactions);
        response.put("currentPage", transactionPage.getNumber());
        response.put("totalPages", transactionPage.getTotalPages());
        response.put("totalElements", transactionPage.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getTransactionsByTelephone(@RequestParam String telephone,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionPage = transactionService.findByTelephone(telephone, pageable);

        List<TransactionResponseDto> transactionDtos = transactionPage.getContent()
                .stream()
                .map(transactionMapperMobile::toTransactionResponseDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", transactionDtos);
        response.put("currentPage", transactionPage.getNumber());
        response.put("totalPages", transactionPage.getTotalPages());
        response.put("totalElements", transactionPage.getTotalElements());

        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<Map<String, Object>> getByTypeAndTelephone(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String telephone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());

        TypeTransaction typeTransaction = null;

        if (type != null) {
            try {
                typeTransaction = TypeTransaction.valueOf(type);
            } catch (IllegalArgumentException e) {
                response.put("success", false);
                response.put("message", "Type de transaction invalide");
                return ResponseEntity.badRequest().body(response);
            }
        }

        Page<Transaction> transactionPage;

        // ⚙️ Logique conditionnelle selon les filtres fournis
        if (typeTransaction != null && telephone != null) {
            transactionPage = transactionService.findByTypeAndTelephone(typeTransaction, telephone, pageable);
        } else if (typeTransaction != null) {
            transactionPage = transactionService.findByType(typeTransaction, pageable);
        } else if (telephone != null) {
            transactionPage = transactionService.findByTelephone(telephone, pageable);
        } else {
            transactionPage = transactionService.findAll(pageable);
        }

        List<TransactionResponseDto> transactions = transactionPage
                .stream()
                .map(transactionMapperMobile::toTransactionResponseDto)
                .collect(Collectors.toList());

        response.put("success", true);
        response.put("data", transactions);
        response.put("currentPage", transactionPage.getNumber());
        response.put("totalPages", transactionPage.getTotalPages());
        response.put("totalElements", transactionPage.getTotalElements());

        return ResponseEntity.ok(response);
    }





}