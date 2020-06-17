package com.example.campagnon;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.Produit;
import com.example.campagnon.Class.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ajouterProduitProducteurActivity extends AppCompatActivity {
    //Attribut accessible partout
    User leProd;
    Produit leProduit;
    OkHttpClient client = new OkHttpClient();
    EditText lien;
    EditText nom;
    Spinner type;
    EditText prixKilo;
    EditText description;
    EditText quantite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_producteur_un_produit);
        //On récupere le Producteur par rapport à son identifiant
        leProd = LesUsers.getUserID(getIntent().getExtras().getString("identifiant"));


        final ImageView monImage = (ImageView) findViewById(R.id.display_image_produit1);
        final Context lecontext = this;

        lien = (EditText) findViewById(R.id.editTextLien);
        //Lorque le text du lien change alors on remplace l'image par celle du nouveau lien si elle existe
        lien.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                try{
                    Picasso.with(lecontext).load(lien.getText().toString()).into(monImage);
                }catch (Exception e){

                }

            }
        });
        nom = (EditText) findViewById(R.id.editTextNomProduit);

        //On remplie le spinner avec Fruit et Légume
        type = (Spinner) findViewById(R.id.spinnerType);
        ArrayList<String> area = new ArrayList<>();
        area.add("Fruit");
        area.add("Légume");
        type.setAdapter(new ArrayAdapter<String>(this
                , android.R.layout.simple_list_item_1, area));

        //prixkilo.selec(leProduit.getPrix_kg());

        prixKilo = (EditText) findViewById(R.id.editTextKilo);

        description = (EditText) findViewById(R.id.editTextDescription);

        quantite = (EditText) findViewById(R.id.editTextQuantite);

        //Lorsque l'on clique sur le bouton enregister, on créer une tache asynchro permettant de faire appel à une page url qui va se charger d'enregistrer les informations dans la BDD
        final Button enregister = (Button) findViewById(R.id.enregistrer);
        enregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackTaskEnregistrement().execute();

            }
        });
    }

    //Tache permettant d'enregistrer le produit dans la BDD
    private class BackTaskEnregistrement extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){

            EditText lien = (EditText) findViewById(R.id.editTextLien);
            EditText nom = (EditText) findViewById(R.id.editTextNomProduit);
            Spinner type = (Spinner) findViewById(R.id.spinnerType);
            EditText prixKilo = (EditText) findViewById(R.id.editTextKilo);
            EditText description = (EditText) findViewById(R.id.editTextDescription);
            try {
                //On donne toutes ces informations dans le POST
                RequestBody formBody = new FormBody.Builder()
                        .add("type", type.getSelectedItem().toString())
                        .add("image", lien.getText().toString())
                        .add("nom", nom.getText().toString())
                        .add("qteProduit", quantite.getText().toString())
                        .add("prix", prixKilo.getText().toString())
                        .add("description", description.getText().toString())
                        .add("idProd", leProd.getIdentifiant())
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/ajouterProduit.php") //On les envoi à cette URL
                        .post(formBody)
                        .build();
                Response response = client.newCall(request).execute();
            }
            catch (Exception e) {
                Log.d("Test", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            try {
                //On créer le nouveau Produit en objet lorsqu'il a bien été crée
                leProduit=new Produit();
                leProduit.setDecription(description.getText().toString());
                leProduit.setImage(lien.getText().toString());
                leProduit.setNom_produit(nom.getText().toString());
                leProduit.setQté_produit(quantite.getText().toString());
                leProduit.setPrix_kg(prixKilo.getText().toString());
                leProduit.setType_produit(type.getSelectedItem().toString());
                leProd.addProduit(leProduit);
                //On fermme la page d'ajout du produit
                finish();
            }catch (Exception e) {
                Toast.makeText(ajouterProduitProducteurActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

    }
}