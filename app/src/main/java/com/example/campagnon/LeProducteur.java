package com.example.campagnon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.CustomGridAdapterConsoAddPanier;
import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.Produit;
import com.example.campagnon.Class.User;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LeProducteur extends AppCompatActivity {
    User monUser;
    User leProd;
    String responseStr;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conso_fiche_producteur);
        String identifiantConso = getIntent().getStringExtra("identifiant");
        String identifiantProd = getIntent().getStringExtra("Prod");
        leProd = LesUsers.getUserID(identifiantProd);
        monUser = LesUsers.getUserID(identifiantConso);
        TextView nomEntreprise = (TextView) findViewById(R.id.display_nom_prod);
        nomEntreprise.setText(leProd.getNomEntreprise());
        TextView adresse = (TextView) findViewById(R.id.display_adresseprod_ficheprod);
        adresse.setText(leProd.getAdresse());
        TextView distance = (TextView) findViewById(R.id.display_distance_ficheprod);
        distance.setText(String.valueOf(monUser.Distance(leProd))+"km");

        TextView type = (TextView) findViewById(R.id.display_produit_type_ficheprod);
        type.setText(leProd.typeProduitVendu());

        final Button fav =(Button) findViewById(R.id.Favoris);
        if(monUser.verifierFavUser(leProd)){
            fav.setText("Enlever");
        }
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fav.getText().equals("Ajouter")){
                    monUser.addUser(leProd);
                    fav.setText("Enlever");
                    new BackTaskAjouterFav().execute();
                }else{
                    monUser.supUser(leProd);
                    fav.setText("Ajouter");
                    new BackTaskSupprimerFav().execute();

                }
            }
        });


        List<Produit> image_details = leProd.getListProduit();
        final GridView gridView = (GridView) findViewById(R.id.gridviewProduit);
        gridView.setAdapter(new CustomGridAdapterConsoAddPanier(this, image_details));


        // When the user clicks on the GridItem
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                Produit produit = (Produit) o;
                Intent intent = new Intent(LeProducteur.this, ProduitConsoActivity.class);
                intent.putExtra("identifiant", monUser.getIdentifiant());
                intent.putExtra("leProduit", ((Produit) o).getNom_produit());
                intent.putExtra("idProducteur", leProd.getIdentifiant());
                startActivity(intent);
            }
        });
    }



    private class BackTaskAjouterFav extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {
                RequestBody formBody = new FormBody.Builder()
                        .add("idConso", monUser.getIdentifiant())
                        .add("idProd", leProd.getIdentifiant())
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/ajouterFav.php")
                        .post(formBody)
                        .build();
                Response response = client.newCall(request).execute();
                responseStr = response.body().string();
            }
            catch (Exception e) {
                Log.d("Test", "Erreur de connexion Ajout !!!!");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
        }
    }

    private class BackTaskSupprimerFav extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {
                RequestBody formBody = new FormBody.Builder()
                        .add("idConso", monUser.getIdentifiant())
                        .add("idProd", leProd.getIdentifiant())
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/supprimerFav.php")
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
        }
    }
}