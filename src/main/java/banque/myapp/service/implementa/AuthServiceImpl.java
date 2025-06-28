package banque.myapp.service.implementa;


import banque.myapp.data.models.entity.User;
import banque.myapp.data.models.repository.UserRepository;
import banque.myapp.mobile.dto.request.LoginRequestDto;
import banque.myapp.service.interfac.AuthService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> login(LoginRequestDto loginRequest) {
        return userRepository.findAll().stream()
                .filter(user -> user.getLogin().equals(loginRequest.getLogin())
                        && user.getPassword().equals(loginRequest.getPassword()))
                .findFirst();
    }
}