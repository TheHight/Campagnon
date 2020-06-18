package com.example.campagnon.Class;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class LesCommandes {
    private static ArrayList<Commande> listCommande = new ArrayList<Commande>();

    public static void ajouterCommande(Commande uneCommande){
        listCommande.add(uneCommande);
    }

    public static ArrayList<Commande> getListCommande() {
        return listCommande;
    }

    public static ArrayList<Commande> getListCommandeEnCours(User monUser) {
        ArrayList<Commande> listCommandeEnCours = new ArrayList<Commande>();
        for(Commande uneCommande : listCommande){
            if(uneCommande.getEtat().equals("EC") && (uneCommande.getLeConso().equals(monUser)|| uneCommande.getLeProduit().getLeProd().equals(monUser)) ){
                listCommandeEnCours.add(uneCommande);
            }
        }
        return listCommandeEnCours;
    }

    public static ArrayList<Commande> getListCommandeValide(User monUser) {
        ArrayList<Commande> listCommandeV = new ArrayList<Commande>();
        for(Commande uneCommande : listCommande){
            if(uneCommande.getEtat().equals("V") && (uneCommande.getLeConso().equals(monUser)|| uneCommande.getLeProduit().getLeProd().equals(monUser)) ){
                listCommandeV.add(uneCommande);
            }
        }
        return listCommandeV;
    }

    public static ArrayList<Commande> getListCommandeRecup(User monUser) {
        ArrayList<Commande> listCommandeR = new ArrayList<Commande>();
        for(Commande uneCommande : listCommande){
            if(uneCommande.getEtat().equals("R") && (uneCommande.getLeConso().equals(monUser)|| uneCommande.getLeProduit().getLeProd().equals(monUser)) ){
                listCommandeR.add(uneCommande);
            }
        }
        return listCommandeR;
    }

    public static ArrayList<Produit> getListProduitCommande() {
        ArrayList<Commande> listCommandeEC = getListCommande();
        ArrayList<Produit> listProduit = new ArrayList<Produit>();
        for(Commande uneCommande : listCommandeEC){
            listProduit.add(uneCommande.getLeProduit());
        }
        return listProduit;
    }

    public static ArrayList<Produit> getListProduitCommandeEC(User monUser) {
        ArrayList<Commande> listCommandeEC = getListCommandeEnCours(monUser);
        ArrayList<Produit> listProduit = new ArrayList<Produit>();
        for(Commande uneCommande : listCommandeEC){
            listProduit.add(uneCommande.getLeProduit());
        }
        return listProduit;
    }


    public static void clearListe(){
        listCommande.clear();
    }



}
