package com.example.campagnon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.Produit;
import com.example.campagnon.Class.User;

import org.json.JSONArray;
import org.json.JSONObject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccueilPrincipalConso extends AppCompatActivity {
    //VAriable accessible partout
    String identifiant;

    String responseStrCli;
    String responseStr;
    OkHttpClient client = new OkHttpClient();

    //A la creation de cet activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_accueil_conso);
        //On récupere l'identifiant qui a été donne dans l'activité précedente
        identifiant = getIntent().getStringExtra("identifiant");
        //On récupere le TextView qui affiche le nom de l'utilisateur
        final TextView textUser = (TextView) findViewById(R.id.display_nom_user);
        //On écrit le nom de l'utilisateur dans le TextView
        textUser.setText(identifiant);
        //On appelle les fonctions qui permettent de faire appel à la BDD
        new BackTaskRecupererMesProducteurs().execute();
        new BackTaskRecupererLesProduit().execute();

        //On récupere l'objet ImageView sur le layout
        final ImageView imageProfil = (ImageView) findViewById(R.id.profil_conso_access);
        //Fonction qui se déclenche au moment du clique sur l'image profil
        imageProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On va appeller la classe Modification
                Intent intent = new Intent(AccueilPrincipalConso.this, Modification.class);
                //On donne l'identifiant de cet intent à a prochaine intent
                intent.putExtra("identifiant", identifiant);
                //On lance la nouvelle activity
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
        //Fonction qui se déclenche au moment du clique sur le bouton "Autour de chez moi"
        final Button buttonMaps = (Button) findViewById(R.id.interface_conso_2);
        buttonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On prépare l'activity Maps
                Intent intent = new Intent(AccueilPrincipalConso.this, MapsActivity.class);
                //
                //On donne l'identifiant de cet intent à a prochaine intent
                intent.putExtra("identifiant", identifiant);
                //On lance la nouvelle activity
                startActivity(intent);
            }
        });

        //Fonction qui se déclenche au moment du clique sur le bouton "Panier et historique"
        final Button panier = (Button) findViewById(R.id.interface_conso_3);
        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On prépare l'activity Panier
                Intent intent = new Intent(AccueilPrincipalConso.this, Panier.class);
                //On donne l'identifiant de cet intent à a prochaine intent
                intent.putExtra("identifiant", identifiant);
                //On lance la nouvelle activity
                startActivity(intent);
            }
        });
    }


    //Procédure asynchronique (qui se lance en arriere plan) qui permet de récuperer tous les producteurs de l'utilsateur de la BDD pour l'ajouter à sa liste
    private class BackTaskRecupererMesProducteurs extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        //En arriere plan
        @Override
        protected Void doInBackground(Void... params){
            try {

                //Préparation de la requete post

                RequestBody formBody = new FormBody.Builder()
                        .add("id", identifiant) //on donne l'identifiant en parametre à notre requete
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/recupererProd.php") //on envoie la requete à cette URL
                        .post(formBody)
                        .build();
                Response response = client.newCall(request).execute();
                //On récupere la réponse
                responseStrCli = response.body().string();
            }
            catch (Exception e) {
                Log.d("Test", e.getMessage());
            }
            return null;
        }
        //Après avoir récuperé les informations
        @Override
        protected void onPostExecute(Void result) {

            try {
                //On vérifie si l'utilisateur à un producteur dans sa liste
                if(!responseStrCli.equals("false")){
                    //On convertie le résultat en JSONArray
                    JSONArray array = new JSONArray(responseStrCli);
                    //On parcourt le tableau par ligne
                    for (int i = 0; i < array.length(); i++){
                        //On récupere l'objet pour la ligne en cours
                        JSONObject row = array.getJSONObject(i);

                        responseStrCli =row.getString("identifiant");
                        //On ajoute le producteur à la lise des Producteurs du conso
                        LesUsers.getUserID(identifiant).addUser(LesUsers.getUserID(row.getString("identifiant")));
                    }
                }else{
                }
            } catch (Exception e) {
                Toast.makeText(AccueilPrincipalConso.this, responseStrCli, Toast.LENGTH_SHORT).show();
            }
        }

    }


    //Procédure asynchronique (qui se lance en arriere plan) qui permet de récuperer tous les produit.
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
                        .url("http://campagnon.tk/recupererProduit.php") //On envoie la requette à cette adresse
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
        //Après avoir executé la requette
        @Override
        protected void onPostExecute(Void result) {

            try {


                if(!responseStr.equals("false")){
                    JSONArray array = new JSONArray(responseStr);
                    //On parcourt chaque ligne
                    for (int i = 0; i < array.length(); i++){

                        JSONObject row = array.getJSONObject(i);
                        //On crée un objet Produit
                        Produit leProduit = new Produit();
                        //On lui set ses differents attributs
                        leProduit.setNom_produit(row.getString("nom_produit"));
                        leProduit.setImage(row.getString("image"));
                        leProduit.setPrix_kg(row.getString("prix_kg"));
                        leProduit.setQté_produit(row.getString("qte_produit"));
                        leProduit.setType_produit(row.getString("type_produit"));
                        leProduit.setDecription(row.getString("description"));
                        User leProd = LesUsers.getUserID(row.getString("idProd"));
                        leProduit.setLeProd(leProd);
                        //On attribut le Produit au Producteur correspondant
                        leProd.addProduit(leProduit);
                    }
                }else{
                }
            } catch (Exception e) {
                Toast.makeText(AccueilPrincipalConso.this, e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        }

    }
}
