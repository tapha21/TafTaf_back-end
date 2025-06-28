package banque.myapp.mobile.controller.interfa;

import banque.myapp.mobile.dto.request.TransactionRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/mobile/transaction")
public interface    TransactionControllerInterface {

    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> getById(@PathVariable String id); // 🔍 Obtenir une transaction par ID

    @PostMapping("/envoyer")
    ResponseEntity<Map<String, Object>> envoyerArgent(@RequestBody TransactionRequestDto request); // 💸 Envoi d'argent

    @GetMapping("/solde/{idUtilisateur}")
    ResponseEntity<Map<String, Object>> getSoldeUtilisateur(@PathVariable String idUtilisateur); // 💰 Solde d’un utilisateur

    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, Object>> supprimerTransaction(@PathVariable String id); // ❌ Suppression (client)

}
