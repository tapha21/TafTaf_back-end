package banque.myapp.web.controller.impl;

import banque.myapp.data.models.entity.User;
import banque.myapp.mobile.dto.response.UserResponseDto;
import banque.myapp.mobile.mapper.UserMapperMobile;
import banque.myapp.service.interfac.UserService;
import banque.myapp.web.controller.inter.UserWeb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserWebImpl implements UserWeb {

    private final UserService userService;
    private final UserMapperMobile userMapperMobile;

    public UserWebImpl(UserService userService, UserMapperMobile userMapperMobile) {
        this.userService = userService;
        this.userMapperMobile = userMapperMobile;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAll(int page, int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.findAll(pageable);

        List<UserResponseDto> usersDto = userPage.getContent()
                .stream()
                .map(userMapperMobile::toUserResponseDto)
                .collect(Collectors.toList());

        response.put("success", true);
        response.put("data", usersDto);
        response.put("currentPage", userPage.getNumber());
        response.put("totalItems", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getById(String id) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.findById(id);
        if (user != null) {
            response.put("success", true);
            response.put("data", userMapperMobile.toUserResponseDto(user));
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Utilisateur introuvable");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getByTelephone(String telephone) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.findByNumero(telephone);

        if (user != null) {
            response.put("success", true);
            response.put("data", userMapperMobile.toUserResponseDto(user));
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Aucun utilisateur trouvé avec ce numéro.");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
