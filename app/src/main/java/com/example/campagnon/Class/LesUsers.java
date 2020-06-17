package com.example.campagnon.Class;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LesUsers {
    //ATTRIBUTS
    private static ArrayList<User> listUser = new ArrayList<User>();

    //Ajoue un utilisateur à la liste
    public static void ajouterUser(User unUser){
        listUser.add(unUser);
    }

    //Renvoie la liste
    public static ArrayList<User> getListUser() {
        return listUser;
    }

    //Fonction qui renvoie une liste de Type User uniquement de Producteur
    public static ArrayList<User> getListProducteurs() {
        //Création de l'ArrayList
        ArrayList<User> listProd = new ArrayList<User>();
        //Boucle permettant de parcourir toute la liste des User
        for(User unUser : listUser){
            //Vérifie qu'il ne s'agit pas d'un consommateur (donc un producteur)
            if(!unUser.estConsommateur()){
                //Ajoute le producteur à la liste des producteurs
                listProd.add(unUser);
            }
        }
        //Retourne la liste des prods
        return listProd;
    }

    //Fonction qui renvoie une liste de Type User uniquement de Producteur
    public static ArrayList<User> getListConso() {
        //Création de l'ArrayList
        ArrayList<User> listConso = new ArrayList<User>();
        //Boucle permettant de parcourir toute la liste des User
        for(User unUser : listUser){
            //Vérifie qu'il s'agit d'un consommateur
            if(unUser.estConsommateur()){
                //Ajoute le consommateur à la liste
                listConso.add(unUser);
            }
        }
        //Retourne la liste des Consommateur
        return listConso;
    }

    //Fonction qui permet de retourner l'utilisateur avec l'id passé en para
    public static User getUserID(String unID){
        //Iterateur de la listUser
        for(User unUser : listUser){
            //Compare l'identifiant de l'utilisateur avec celui passé en para si il s'agit du meme alors
            if(unUser.getIdentifiant().equals(unID)){
                //renvoie l'utilisateur
                return unUser;
            }
        }
        //utilisateur non trouvé
        return null;
    }

    //Permet de vider la liste
    public static void clearListe(){
        listUser.clear();
    }



}
