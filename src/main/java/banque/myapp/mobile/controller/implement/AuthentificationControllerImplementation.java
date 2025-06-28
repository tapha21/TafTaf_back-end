package banque.myapp.mobile.controller.implement;

import banque.myapp.data.models.entity.User;
import banque.myapp.data.models.enums.Role;
import banque.myapp.data.models.repository.UserRepository;
import banque.myapp.mobile.controller.interfa.AuthentiControllerInterface;
import banque.myapp.mobile.dto.request.LoginRequestDto;
import banque.myapp.mobile.dto.request.RegisterRequestDto;
import banque.myapp.service.interfac.AuthService;
import banque.myapp.service.interfac.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthentificationControllerImplementation implements AuthentiControllerInterface {

    private final AuthService authService;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthentificationControllerImplementation(
            AuthService authService,
            UserService userService,
            UserRepository userRepository
    ) {
        this.authService = authService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Map<String, Object>> login(LoginRequestDto request) {
        Map<String, Object> response = new HashMap<>();

        return authService.login(request)
                .map(user -> {
                    response.put("success", true);
                    response.put("data", user);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("success", false);
                    response.put("message", "Login ou mot de passe incorrect");
                    return ResponseEntity.badRequest().body(response);
                });
    }

    @Override
    public ResponseEntity<Map<String, Object>> saveUser(RegisterRequestDto dto) {
        Map<String, Object> response = new HashMap<>();

        if (dto.getTelephone() == null || dto.getTelephone().isEmpty()) {
            response.put("success", false);
            response.put("message", "Le numéro de téléphone est obligatoire");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<User> existing = userRepository.findByTelephone(dto.getTelephone());
        if (existing.isPresent()) {
            response.put("success", false);
            response.put("message", "Un utilisateur avec ce numéro existe déjà");
            return ResponseEntity.badRequest().body(response);
        }

        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword()); // ⚠️ à encoder avec BCrypt si sécurité voulue
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setTelephone(dto.getTelephone());
        user.setAdresse(dto.getAdresse());
        user.setSolde(0.0);
        user.setRole(Role.Client); // rôle automatique

        userService.save(user);

        response.put("success", true);
        response.put("message", "Utilisateur enregistré avec succès");
        response.put("user", user);
        return ResponseEntity.ok(response);
    }
}
