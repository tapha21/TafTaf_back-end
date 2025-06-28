package banque.myapp.data.models.entity;

import banque.myapp.data.models.enums.Role;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@CompoundIndexes({
        @CompoundIndex(name = "unique_telephone", def = "{'telephone' : 1}", unique = true)
})
public class User extends AbstractEntity {
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private Role role;
    private double solde;


    // --- Getters ---
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public Role getRole() {
        return role;
    }

    public double getSolde() {return solde;}

    // --- Setters ---
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public void setSolde(double solde) {this.solde = solde;}
}
