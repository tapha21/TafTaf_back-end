package banque.myapp.web.dto.request;

import banque.myapp.data.models.enums.TypeTransaction;

import java.util.Date;

public class TransactionRequestDto {
    private double montant;
    private Date date;
    private String envoyeurId;  // juste l'id du user
    private TypeTransaction type;

    // --- Getters ---
    public double getMontant() {
        return montant;
    }

    public Date getDate() {
        return date;
    }

    public String getEnvoyeurId() {
        return envoyeurId;
    }

    public TypeTransaction getType() {
        return type;
    }

    // --- Setters ---
    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEnvoyeurId(String envoyeurId) {
        this.envoyeurId = envoyeurId;
    }

    public void setType(TypeTransaction type) {
        this.type = type;
    }
}
