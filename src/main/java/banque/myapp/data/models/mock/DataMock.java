package banque.myapp.data.models.mock;

import banque.myapp.data.models.entity.Transaction;
import banque.myapp.data.models.entity.User;
import banque.myapp.data.models.enums.Role;
import banque.myapp.data.models.enums.TypeTransaction;
import banque.myapp.data.models.repository.TransactionRepository;
import banque.myapp.data.models.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Order(1)
//@Component
public class DataMock implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public DataMock(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Ne rien faire si données déjà présentes
        if (userRepository.count() > 0 || transactionRepository.count() > 0) {
            return;
        }

        // Créer un admin
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("123");
        admin.setNom("Tall");
        admin.setPrenom("Tapha");
        admin.setTelephone("772901490");
        admin.setAdresse("Hlm grand yoff");
        admin.setRole(Role.Admin);
        admin.setSolde(0);
        userRepository.save(admin);

        // Création des clients
        List<User> clients = new ArrayList<>();

        User client1 = new User();
        client1.setLogin("flo");
        client1.setPassword("123");
        client1.setNom("siby");
        client1.setPrenom("yaya");
        client1.setTelephone("775362410");
        client1.setAdresse("Hlm grand yoff");
        client1.setRole(Role.Client);

        User client2 = new User();
        client2.setLogin("talla");
        client2.setPassword("123");
        client2.setNom("Tall");
        client2.setPrenom("talla");
        client2.setTelephone("770032454");
        client2.setAdresse("Hlm grand yoff");
        client2.setRole(Role.Client);

        User client3 = new User();
        client3.setLogin("paco");
        client3.setPassword("123");
        client3.setNom("faty");
        client3.setPrenom("paco");
        client3.setTelephone("774381344");
        client3.setAdresse("Hlm grand yoff");
        client3.setRole(Role.Client);

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);

        userRepository.saveAll(clients);

        List<Transaction> allTransactions = new ArrayList<>();

        // Pour chaque client, créer 4 transactions : 2 dépôts, 2 retraits
        for (User client : clients) {
            double solde = 0;

            // Dépôt 1
            Transaction depot1 = new Transaction();
                depot1.setEnvoyeur(client);
            depot1.setType(TypeTransaction.Depot);
            depot1.setDate(new Date());
            depot1.setMontant(24500);
            allTransactions.add(depot1);
            solde += 24500;

            // Retrait 1
            Transaction retrait1 = new Transaction();
            retrait1.setEnvoyeur(client);
            retrait1.setType(TypeTransaction.Retrait);
            retrait1.setDate(new Date());
            retrait1.setMontant(15000);
            allTransactions.add(retrait1);
            solde -= 15000;

            // Dépôt 2
            Transaction depot2 = new Transaction();
            depot2.setEnvoyeur(client);
            depot2.setType(TypeTransaction.Depot);
            depot2.setDate(new Date());
            depot2.setMontant(340000);
            allTransactions.add(depot2);
            solde += 340000;

            // Retrait 2
            Transaction retrait2 = new Transaction();
            retrait2.setEnvoyeur(client);
            retrait2.setType(TypeTransaction.Retrait);
            retrait2.setDate(new Date());
            retrait2.setMontant(93400);
            allTransactions.add(retrait2);
            solde -= 93400;

            // Mise à jour du solde client
            client.setSolde(solde);
        }

        // Sauvegarde des transactions
        transactionRepository.saveAll(allTransactions);

        // Sauvegarde des clients avec leur solde mis à jour
        userRepository.saveAll(clients);

        System.out.println("✅ Données mock créées : 3 clients + 1 admin, transactions et soldes initialisés.");
    }
}
