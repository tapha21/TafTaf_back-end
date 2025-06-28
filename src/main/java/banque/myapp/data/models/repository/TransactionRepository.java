package banque.myapp.data.models.repository;


import banque.myapp.data.models.entity.Transaction;
import banque.myapp.data.models.entity.User;
import banque.myapp.data.models.enums.TypeTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    Optional<Transaction> findById(String id);
    List<Transaction> findAll();
    List<Transaction> findByType(TypeTransaction type);
    Page<Transaction> findByType(TypeTransaction type, Pageable pageable);
    Page<Transaction> findByEnvoyeur(User envoyeur, Pageable pageable);
    Page<Transaction> findByReceveur(User receveur, Pageable pageable);
    Page<Transaction> findByTypeAndEnvoyeurOrReceveur(TypeTransaction type, User envoyeur, User receveur, Pageable pageable);
    Page<Transaction> findByTypeAndEnvoyeur(TypeTransaction type, User envoyeur, Pageable pageable);
    Page<Transaction> findByTypeAndReceveur(TypeTransaction type, User receveur, Pageable pageable);


}