package com.example.campagnon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.Produit;
import com.example.campagnon.Class.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccueilPrincipalConso extends AppCompatActivity {
    String identifiant;
    String responseStr;
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_accueil_conso);
        identifiant = getIntent().getStringExtra("identifiant");
        final TextView textUser = (TextView) findViewById(R.id.display_nom_user);
        textUser.setText(identifiant);
        new BackTaskRecupererMesProducteurs().execute();
        new BackTaskRecupererLesProduit().execute();
        final ImageView imageProfil = (ImageView) findViewById(R.id.profil_conso_access);
        imageProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilPrincipalConso.this, Modification.class);
                intent.putExtra("identifiant", identifiant);
                startActivity(intent);
            }
        });
        final Button buttonProducteur = (Button) findViewById(R.id.interface_conso_1);
        buttonProducteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilPrincipalConso.this, MesProducteurs.class);
                intent.putExtra("identifiant", identifiant);
                startActivity(intent);
            }
        });
        final Button buttonMaps = (Button) findViewById(R.id.interface_conso_2);
        buttonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilPrincipalConso.this, MapsActivity.class);
                intent.putExtra("identifiant", identifiant);
                startActivity(intent);
            }
        });
    }

    private class BackTaskRecupererMesProducteurs extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {

                RequestBody formBody = new FormBody.Builder()
                        .add("id", identifiant)
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/recupererProd.php")
                        .post(formBody)
                        .build();
                Response response = client.newCall(request).execute();
                responseStr = response.body().string();
            }
            catch (Exception e) {
                Log.d("Test", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            try {

                if(!responseStr.equals("false")){
                    JSONArray array = new JSONArray(responseStr);
                    for (int i = 0; i < array.length(); i++){
                        JSONObject row = array.getJSONObject(i);
                        LesUsers.getUserID(identifiant).addUser(LesUsers.getUserID(row.getString("identifiant")));
                    }
                }else{
                }
            } catch (Exception e) {
                Toast.makeText(AccueilPrincipalConso.this, e.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }

    }



    private class BackTaskRecupererLesProduit extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {

                RequestBody formBody = new FormBody.Builder()
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/recupererProduit.php")
                        .post(formBody)
                        .build();
                Response response = client.newCall(request).execute();
                responseStr = response.body().string();
            }
            catch (Exception e) {
                Log.d("Test", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            try {


                if(!responseStr.equals("false")){
                    JSONArray array = new JSONArray(responseStr);
                    for (int i = 0; i < array.length(); i++){
                        JSONObject row = array.getJSONObject(i);
                        Produit leProduit = new Produit();
                        //leProduit.setImage(row.getString("image"));
                        leProduit.setNom_produit(row.getString("nom_produit"));
                        leProduit.setPrix_kg(row.getString("prix_kg"));
                        leProduit.setQté_produit(row.getString("qte_produit"));
                        leProduit.setType_produit(row.getString("type_produit"));
                        LesUsers.getUserID(row.getString("idProd")).addProduit(leProduit);
                    }
                }else{
                }
            } catch (Exception e) {
                Toast.makeText(AccueilPrincipalConso.this, e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        }

    }
}
