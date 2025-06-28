package banque.myapp.data.models.repository;

import banque.myapp.data.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByTelephone(String telephone);
    Page<User> findByTelephone(String telephone, Pageable pageable);

}
