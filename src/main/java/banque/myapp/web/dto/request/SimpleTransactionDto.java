package banque.myapp.web.dto.request;

public class SimpleTransactionDto {
    private String numeroClient;
    private double montant;

    // Getters
    public String getNumeroClient() {
        return numeroClient;
    }

    public double getMontant() {
        return montant;
    }

    // Setters
    public void setNumeroClient(String numeroClient) {
        this.numeroClient = numeroClient;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
