package banque.myapp.mobile.dto.request;

import banque.myapp.data.models.enums.Role;

public class UserRequestDto {
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private Role role;
    private double solde;
}