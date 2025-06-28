package banque.myapp.web.dto.request;

import java.util.Date;

public class RetraitRequestDto {
    private String montant;
    private Date date;
    private String numeroEnvoyeur;

    // Getters
    public String getMontant() {
        return montant;
    }

    public Date getDate() {
        return date;
    }

    public String getNumeroEnvoyeur() {
        return numeroEnvoyeur;
    }

    // Setters
    public void setMontant(String montant) {
        this.montant = montant;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNumeroEnvoyeur(String numeroEnvoyeur) {
        this.numeroEnvoyeur = numeroEnvoyeur;
    }
}