package banque.myapp.mobile.controller.interfa;

import banque.myapp.mobile.dto.request.LoginRequestDto;
import banque.myapp.mobile.dto.request.RegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/api/mobile/auth")
public interface AuthentiControllerInterface {
    @PostMapping("/login")
    ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto request);
    @PostMapping("/register")
     ResponseEntity<Map<String, Object>> saveUser(@RequestBody RegisterRequestDto dto);
}
