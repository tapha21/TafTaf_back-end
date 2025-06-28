package banque.myapp.web.dto.response;

import banque.myapp.data.models.enums.Role;

public class UserResponseDto {
    private String id;
    private String login;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private Role role;
    private double solde;

    // getters et setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }
}
