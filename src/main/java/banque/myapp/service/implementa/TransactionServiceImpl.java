package banque.myapp.service.implementa;

import banque.myapp.data.models.entity.Transaction;
import banque.myapp.data.models.entity.User;
import banque.myapp.data.models.enums.TypeTransaction;
import banque.myapp.data.models.repository.TransactionRepository;
import banque.myapp.data.models.repository.UserRepository;
import banque.myapp.service.interfac.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findById(String id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteById(String id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> findByType(TypeTransaction type) {
        return transactionRepository.findByType(type);
    }

    @Override
    public Page<Transaction> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }
    // Dépôt par téléphone (admin)
    public Transaction faireDepotParTelephone(String telephone, double montant) {
        User user = userRepository.findByTelephone(telephone)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec ce téléphone"));
        user.setSolde(user.getSolde() + montant);
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setMontant(montant);
        transaction.setDate(new Date());
        transaction.setEnvoyeur(user);
        transaction.setType(TypeTransaction.Depot);

        return transactionRepository.save(transaction);
    }

    // Retrait par téléphone (admin)
    public Transaction faireRetraitParTelephone(String telephone, double montant) {
        User user = userRepository.findByTelephone(telephone)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec ce téléphone"));
        if (user.getSolde() < montant) {
            throw new RuntimeException("Solde insuffisant pour retrait");
        }
        user.setSolde(user.getSolde() - montant);
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setMontant(montant);
        transaction.setDate(new Date());
        transaction.setEnvoyeur(user);
        transaction.setType(TypeTransaction.Retrait);

        return transactionRepository.save(transaction);
    }

    // Envoi d'argent par téléphone (user)
    public Transaction envoyerArgentParTelephone(String telephoneEnvoyeur, String telephoneReceveur, double montant) {
        User envoyeur = userRepository.findByTelephone(telephoneEnvoyeur)
                .orElseThrow(() -> new RuntimeException("Envoyeur introuvable avec ce téléphone"));
        User receveur = userRepository.findByTelephone(telephoneReceveur)
                .orElseThrow(() -> new RuntimeException("Receveur introuvable avec ce téléphone"));

        if (envoyeur.getSolde() < montant) {
            throw new RuntimeException("Solde insuffisant pour envoyer de l'argent");
        }

        // Débiter l'envoyeur
        envoyeur.setSolde(envoyeur.getSolde() - montant);
        userRepository.save(envoyeur);

        // Créditer le receveur
        receveur.setSolde(receveur.getSolde() + montant);
        userRepository.save(receveur);

        // Créer la transaction
        Transaction transaction = new Transaction();
        transaction.setMontant(montant);
        transaction.setDate(new Date());
        transaction.setEnvoyeur(envoyeur);
        transaction.setType(TypeTransaction.Transfert);

        return transactionRepository.save(transaction);
    }
    @Override
    public Page<Transaction> findByType(TypeTransaction type, Pageable pageable) {
        return transactionRepository.findByType(type, pageable);
    }

    @Override
    public Page<Transaction> findByTelephone(String telephone, Pageable pageable) {
        User user = userRepository.findByTelephone(telephone)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Page<Transaction> envois = transactionRepository.findByEnvoyeur(user, pageable);
        Page<Transaction> receptions = transactionRepository.findByReceveur(user, pageable);

        List<Transaction> all = Stream.concat(
                        envois.getContent().stream(),
                        receptions.getContent().stream()
                ).sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toList());

        // Retourne une page manuelle (option simple)
        return new PageImpl<>(all, pageable, all.size());
    }
    @Override
    public Page<Transaction> findByTypeAndTelephone(TypeTransaction type, String telephone, Pageable pageable) {
        User user = userRepository.findByTelephone(telephone)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Page<Transaction> envois = transactionRepository.findByTypeAndEnvoyeur(type, user, pageable);
        Page<Transaction> receptions = transactionRepository.findByTypeAndReceveur(type, user, pageable);

        List<Transaction> all = Stream.concat(
                        envois.getContent().stream(),
                        receptions.getContent().stream()
                ).sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toList());

        return new PageImpl<>(all, pageable, all.size());
    }







}
