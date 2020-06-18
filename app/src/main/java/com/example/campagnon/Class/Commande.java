package com.example.campagnon.Class;


public class Commande {
    //ATTRIBUTS
    private User leConso;
    private User leProd;
    private Produit leProduit;
    private String quantite;
    private String etat;
    private String date;
    //Constructeur
    public Commande() {
        etat ="";
    }

    //GETTER SETTTER
    public User getLeConso() {
        return leConso;
    }

    public void setLeConso(User leConso) {
        this.leConso = leConso;
    }

    public Produit getLeProduit() {
        return leProduit;
    }

    public void setLeProduit(Produit leProduit) {
        this.leProduit = leProduit;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public String getEtat() {
        return etat;
    }

    public User getLeProd() {
        return leProd;
    }

    public void setLeProd(User leProd) {
        this.leProd = leProd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
