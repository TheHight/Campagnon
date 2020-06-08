package com.example.campagnon.Class;

import java.util.ArrayList;

public class User {

    private String identifiant;
    private String ville;
    private String code_postal;
    private String adresse;
    private ArrayList<String> mode_de_paiement;
    private ArrayList<User> listUserProdClient;
    private String statut;
    private String email;
    private String tel;
    private String nomEntreprise;

    public User() {
        mode_de_paiement = new ArrayList<String>();
        listUserProdClient = new ArrayList<User>();
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }


    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public ArrayList<String> getMode_de_paiement() {
        return mode_de_paiement;
    }

    public void addMode_de_paiement(String mode) {
        mode_de_paiement.add(mode);
    }

    public ArrayList<User> getListUserProdClient() {
        return listUserProdClient;
    }

    public void setListUserProdClient(ArrayList<User> listUserProdClient) {
        this.listUserProdClient = listUserProdClient;
    }

    public void addUser(User newUser) {
        listUserProdClient.add(newUser);
    }

    public boolean estConsommateur(){
        return (statut.equals("Consommateur"));
    }
}