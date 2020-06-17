package com.example.campagnon.Class;


public class Commande {
    //ATTRIBUTS
    private User leConso;
    private Produit leProduit;
    private String quantite;
    private String etat;

    //Constructeur
    public Commande() {
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

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
