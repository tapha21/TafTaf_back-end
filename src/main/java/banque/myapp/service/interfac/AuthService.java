package banque.myapp.service.interfac;



import banque.myapp.data.models.entity.User;
import banque.myapp.mobile.dto.request.LoginRequestDto;

import java.util.Optional;

public interface AuthService {
    Optional<User> login(LoginRequestDto loginRequest);
}
