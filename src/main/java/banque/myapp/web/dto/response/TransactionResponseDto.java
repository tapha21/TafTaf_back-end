package banque.myapp.web.dto.response;
import banque.myapp.data.models.enums.TypeTransaction;

import java.util.Date;

public class TransactionResponseDto {
    private String id;
    private double montant;
    private Date date;
    private UserResponseDto envoyeur;  // IMPORTANT: correspond à UserResponseDto
    private TypeTransaction type;

    // getters et setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public UserResponseDto getEnvoyeur() { return envoyeur; }
    public void setEnvoyeur(UserResponseDto envoyeur) { this.envoyeur = envoyeur; }

    public TypeTransaction getType() { return type; }
    public void setType(TypeTransaction type) { this.type = type; }
}
