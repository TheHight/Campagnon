package com.example.campagnon;

public class User {

    private String prenom;
    private String nom;
    private String ville;
    private int code_postal;
    private String adresse;
    private String mode_de_paiement;


    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(int code_postal) {
        this.code_postal = code_postal;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMode_de_paiement() {
        return mode_de_paiement;
    }

    public void setMode_de_paiement(String mode_de_paiement) {
        this.mode_de_paiement = mode_de_paiement;
    }


}
