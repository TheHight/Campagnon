package com.example.campagnon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.CustomGridAdapterProducteur;
import com.example.campagnon.Class.LesCommandes;
import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.Produit;
import com.example.campagnon.Class.User;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Panier extends AppCompatActivity {
    User leClient;
    String responseStr;
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_panier_historique);

        leClient = LesUsers.getUserID(getIntent().getExtras().getString("identifiant"));
        List<Produit> image_details = LesCommandes.getListProduitCommandeEC(leClient);
        final GridView gridView = (GridView) findViewById(R.id._dynamic);
        gridView.setAdapter(new CustomGridAdapterProducteur(this, image_details));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                Produit produit = (Produit) o;
                Intent intent = new Intent(Panier.this, ProduitConsoActivity.class);
                intent.putExtra("identifiant", leClient.getIdentifiant());
                intent.putExtra("leProduit", ((Produit) o).getNom_produit());
                intent.putExtra("idProducteur", ((Produit) o).getLeProd().getIdentifiant());
                startActivity(intent);
            }
        });

        Button monButton = (Button) findViewById(R.id.button);
        monButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackTaskModifierLigneCommande().execute();

            }
        });
    }

    private class BackTaskModifierLigneCommande extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {

                RequestBody formBody = new FormBody.Builder()
                        .add("idConso", leClient.getIdentifiant())

                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/validerCommande.php")
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
                Toast.makeText(Panier.this, "Le panier a été validé ",
                        Toast.LENGTH_SHORT).show();
                finish();
            }catch (Exception E){
                Log.d("Erreur", "onPostExecute: "+E.getMessage());
            }

        }
    }
}