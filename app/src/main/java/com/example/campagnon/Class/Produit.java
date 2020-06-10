package com.example.campagnon.Class;


public class Produit {

    private String type_produit;
    private String image;
    private String nom_produit;
    private String qté_produit;
    private String prix_kg;

    public Produit() {
    }

    public String getType_produit() {
        return type_produit;
    }

    public void setType_produit(String type_produit) {
        this.type_produit = type_produit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getQté_produit() {
        return qté_produit;
    }

    public void setQté_produit(String qté_produit) {
        this.qté_produit = qté_produit;
    }

    public String getPrix_kg() {
        return prix_kg;
    }

    public void setPrix_kg(String prix_kg) {
        this.prix_kg = prix_kg;
    }
}
