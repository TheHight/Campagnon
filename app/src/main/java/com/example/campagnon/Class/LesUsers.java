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
    String responseStr;
    OkHttpClient client = new OkHttpClient();
    private static ArrayList<User> listUser = new ArrayList<User>();

    public static void ajouterUser(User unUser){
        listUser.add(unUser);
    }

    public static ArrayList<User> getListUser() {
        return listUser;
    }

    public static ArrayList<User> getListProducteurs() {
        ArrayList<User> listProd = new ArrayList<User>();
        for(User unUser : listUser){
            if(!unUser.estConsommateur()){
                listProd.add(unUser);
            }
        }
        return listProd;
    }

    public static ArrayList<User> getListConso() {
        ArrayList<User> listConso = new ArrayList<User>();
        for(User unUser : listUser){
            if(unUser.estConsommateur()){
                listConso.add(unUser);
            }
        }
        return listConso;
    }

    public static User getUserID(String unID){
        for(User unUser : listUser){
            if(unUser.getIdentifiant().equals(unID)){
                return unUser;
            }
        }
        return null;
    }

    public static void clearListe(){
        listUser.clear();
    }



}
