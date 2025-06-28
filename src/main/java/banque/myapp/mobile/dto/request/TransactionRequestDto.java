package banque.myapp.mobile.dto.request;

import java.util.Date;

public class TransactionRequestDto {
    private double montant;
    private Date date;
    private String numeroEnvoyeur;
    private String numeroReceveur;

    // Getters
    public double getMontant() {
        return montant;
    }

    public Date getDate() {
        return date;
    }

    public String getNumeroEnvoyeur() {
        return numeroEnvoyeur;
    }

    public String getNumeroReceveur() {
        return numeroReceveur;
    }

    // Setters
    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNumeroEnvoyeur(String numeroEnvoyeur) {
        this.numeroEnvoyeur = numeroEnvoyeur;
    }

    public void setNumeroReceveur(String numeroReceveur) {
        this.numeroReceveur = numeroReceveur;
    }
}
