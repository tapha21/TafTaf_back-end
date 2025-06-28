package banque.myapp.service.interfac;



import banque.myapp.data.models.entity.Transaction;
import banque.myapp.data.models.enums.TypeTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();
    Transaction findById(String id);
    Transaction save(Transaction transaction);
    void deleteById(String id);
    List<Transaction> findByType(TypeTransaction type);
    Page<Transaction> findAll(Pageable pageable);
    Page<Transaction> findByType(TypeTransaction type, Pageable pageable);
    Page<Transaction> findByTelephone(String telephone, Pageable pageable);
    Page<Transaction> findByTypeAndTelephone(TypeTransaction type, String telephone, Pageable pageable);

}
