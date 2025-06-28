package banque.myapp.web.controller.inter;

import banque.myapp.mobile.dto.request.TransactionRequestDto;
import banque.myapp.web.dto.request.SimpleTransactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/web/transaction")
public interface TransactionWeb {
    @GetMapping("")
    ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );
    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> getById(@PathVariable String id);
    @PutMapping("/annuler/{id}")
    ResponseEntity<Map<String, Object>> annulerTransaction(@PathVariable String id);
    @PostMapping("/depot")
    ResponseEntity<Map<String, Object>> FaireDepot(@RequestBody SimpleTransactionDto request);
    @PostMapping("/retrait")
    ResponseEntity<Map<String, Object>> FaireRetrait(@RequestBody SimpleTransactionDto request);
    @GetMapping("/filtrer")
    ResponseEntity<Map<String, Object>> getByType(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );
    @GetMapping("/user/transactions")
    ResponseEntity<Map<String, Object>> getTransactionsByTelephone(@RequestParam String telephone,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "5") int size);
    @GetMapping("/filtrer/multiple")
    ResponseEntity<Map<String, Object>> getByTypeAndTelephone(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String telephone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );


}
