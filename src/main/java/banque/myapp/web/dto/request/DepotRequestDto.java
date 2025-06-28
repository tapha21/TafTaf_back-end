package banque.myapp.web.dto.request;

import java.util.Date;

public class DepotRequestDto {
    private String montant;
    private Date date;
    private String numeroReceveur;

    // Getters
    public String getMontant() {
        return montant;
    }

    public Date getDate() {
        return date;
    }

    public String getNumeroReceveur() {
        return numeroReceveur;
    }

    // Setters
    public void setMontant(String montant) {
        this.montant = montant;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNumeroReceveur(String numeroReceveur) {
        this.numeroReceveur = numeroReceveur;
    }
}
