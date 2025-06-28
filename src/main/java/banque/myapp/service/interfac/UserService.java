package banque.myapp.service.interfac;


import banque.myapp.data.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(String id);
    User save(User user);
    void deleteById(String id);
    Page<User> findAll(Pageable pageable);
    User findByNumero(String telephone);

}