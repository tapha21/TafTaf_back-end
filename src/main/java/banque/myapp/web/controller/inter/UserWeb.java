package banque.myapp.web.controller.inter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping("/api/web/user")
public interface UserWeb {
    @GetMapping("")
    ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );


    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> getById(@PathVariable String id);
    @GetMapping("/numero")
    ResponseEntity<Map<String, Object>> getByTelephone(@RequestParam String telephone);
}
