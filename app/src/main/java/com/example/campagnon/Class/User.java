package com.example.campagnon.Class;

import java.util.ArrayList;

public class User {

    //ATTRIBUTS
    private String identifiant;
    private String ville;
    private String code_postal;
    private String adresse;
    private ArrayList<String> mode_de_paiement;
    private ArrayList<User> listUserProdClient;
    private ArrayList<Produit> listProduit;
    private String statut;
    private String email;
    private String tel;
    private String nomEntreprise;
    private String x;
    private String y;

    //Constucteur
    public User() {
        //Déclaration des arraylist
        mode_de_paiement = new ArrayList<String>();
        listUserProdClient = new ArrayList<User>();
        listProduit = new ArrayList<Produit>();
    }
    //Fonction permettant de calculer distant entre deux utilisateurs
    public double Distance(User autreUser) {
        Double x1 = Double.parseDouble(this.getX());
        Double x2 = Double.parseDouble(autreUser.getX());
        Double y1 = Double.parseDouble(this.getY());
        Double y2 = Double.parseDouble(autreUser.getY());
        double R = 6371e3; // metres
        double φ1 = x1 * Math.PI/180; // φ, λ in radians
        double φ2 = x2 * Math.PI/180;
        double Δφ = (x2-x1) * Math.PI/180;
        double Δλ = (y2-y1) * Math.PI/180;

        double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                Math.cos(φ1) * Math.cos(φ2) *
                        Math.sin(Δλ/2) * Math.sin(Δλ/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double d = R * c; // in kilometre
        d =Math.round(d*1.2);
        d =d/1000;
        return d;
    }

    //GETTER AND SETTER
    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
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

    public void supUser(User supprimeUser) {
        listUserProdClient.remove(supprimeUser);
    }

    public void addProduit(Produit newProduit) {
        listProduit.add(newProduit);
    }

    public void suppProduit(Produit supprimeProduit) {
        listProduit.remove(supprimeProduit);
    }
    public Boolean verifierFavUser(User unUser){
        Boolean re = false;
        for(User unautreUser : listUserProdClient){
            if(unautreUser.equals(unUser)){
                re = true;
            }
        }
        return re;
    }

    public Produit chercherProduit(String nom){
        Produit re = new Produit();
        for(Produit unProduit : listProduit){
            if(unProduit.getNom_produit().equals(nom)){
                re = unProduit;
            }
        }
        return re;
    }

    public String typeProduitVendu(){
        String type = " ";
        for(Produit unProduit : listProduit){
            if(!type.contains(unProduit.getType_produit())){
                type += unProduit.getType_produit()+ " ";
            }
        }
        return type;
    }

    public ArrayList<Produit> getListProduit() {
        return listProduit;
    }

    public void setListProduit(ArrayList<Produit> listProduit) {
        this.listProduit = listProduit;
    }

    public boolean estConsommateur(){
        return (statut.equals("Consommateur"));
    }
}
