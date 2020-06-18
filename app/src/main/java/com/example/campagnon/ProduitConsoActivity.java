package com.example.campagnon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.Commande;
import com.example.campagnon.Class.LesCommandes;
import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.Produit;
import com.example.campagnon.Class.User;
import com.squareup.picasso.Picasso;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProduitConsoActivity extends AppCompatActivity {
    User monClient;
    User leProd;
    Produit leProduit;
    String responseStr;
    OkHttpClient client = new OkHttpClient();
    EditText quantite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conso_fiche_produit);
        //On récupere les objets
        monClient = LesUsers.getUserID(getIntent().getExtras().getString("identifiant"));
        leProd = LesUsers.getUserID(getIntent().getExtras().getString("idProducteur"));
        leProduit = leProd.chercherProduit(getIntent().getExtras().getString("leProduit"));

        ImageView monImage = (ImageView) findViewById(R.id.display_image_produit1);
        Picasso.with(this).load(leProduit.getImage()).into(monImage);
        TextView nom = (TextView) findViewById(R.id.textViewProduitt);
        nom.setText(leProduit.getNom_produit());

        TextView prixkilo = (TextView) findViewById(R.id.display_prix_kilo_pageproduit);
        prixkilo.setText(leProduit.getPrix_kg() +" €");

        TextView typeProduit = (TextView) findViewById(R.id.display_type_produit_pageproduit);
        typeProduit.setText(leProduit.getType_produit());

        TextView description = (TextView) findViewById(R.id.display_description_produit);
        description.setText(leProduit.getDecription());
        quantite = (EditText) findViewById(R.id.editTextQ);
        //On récupere l'objet ImageView sur le layout
        final ImageView imagePanier = (ImageView) findViewById(R.id.ajouter_au_panier2);
        //Fonction qui se déclenche au moment du clique sur l'image panier
        imagePanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackTaskAjouterLigneCommande().execute();
            }
        });
    }

    private class BackTaskAjouterLigneCommande extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {

                RequestBody formBody = new FormBody.Builder()
                        .add("idConso", monClient.getIdentifiant())
                        .add("idProd", leProd.getIdentifiant())
                        .add("idProduit", leProduit.getNom_produit())
                        .add("quantite", quantite.getText().toString())

                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/ajouterCommande.php")
                        .post(formBody)
                        .build();
                Response response = client.newCall(request).execute();
                responseStr = response.body().string();
            }
            catch (Exception e) {
                Log.d("Test", "Erreur de connexion Supp !!!!");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try{
                Commande uneCommande = new Commande();
                uneCommande.setQuantite(quantite.getText().toString());
                uneCommande.setEtat("EC");
                uneCommande.setLeProd(leProd);
                uneCommande.setLeConso(monClient);
                uneCommande.setLeProduit(leProduit);
                LesCommandes.ajouterCommande(uneCommande);
                finish();
            }catch (Exception E){
                Log.d("Erreur", "onPostExecute: "+E.getMessage());
            }

        }
    }

}