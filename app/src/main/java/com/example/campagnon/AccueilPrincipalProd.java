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
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccueilPrincipalProd extends AppCompatActivity {
    User leProd;
    String responseStr;
    String identifiant;
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_accueil_prod);
        identifiant=getIntent().getStringExtra("identifiant");


        final TextView textUser = (TextView) findViewById(R.id.display_nom_user);
        textUser.setText(identifiant);

        final ImageView imageProfil = (ImageView) findViewById(R.id.profil_conso_access);{
        imageProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilPrincipalProd.this, Modification.class);
                intent.putExtra("identifiant", identifiant);
                startActivity(intent);

            }
        });

        final Button mesProduits = (Button) findViewById(R.id.interface_prod_1);
        mesProduits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilPrincipalProd.this, MesProduitsActivity.class);
                intent.putExtra("identifiant", identifiant);
                startActivity(intent);


            }
        });


            final Button commandesAttentes = (Button) findViewById(R.id.interface_prod_2);

            {
                commandesAttentes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AccueilPrincipalProd.this, CommandesAttentesActivity.class);
                        intent.putExtra("identifiant", identifiant);
                        startActivity(intent);
                    }


                });
            }
        }
        leProd = LesUsers.getUserID(getIntent().getStringExtra("identifiant"));

        new BackTaskRecupererLesProduit().execute();
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
                        if(identifiant.equals(row.getString("idProd"))){
                            Produit leProduit = new Produit();
                            //leProduit.setImage(row.getString("image"));
                            leProduit.setNom_produit(row.getString("nom_produit"));
                            leProduit.setImage(row.getString("image"));
                            leProduit.setPrix_kg(row.getString("prix_kg"));
                            leProduit.setQté_produit(row.getString("qte_produit"));
                            leProduit.setType_produit(row.getString("type_produit"));
                            leProduit.setDecription(row.getString("description"));
                            LesUsers.getUserID(getIntent().getStringExtra("identifiant")).addProduit(leProduit);
                        }

                    }
                }else{
                }
            } catch (Exception e) {
                Toast.makeText(AccueilPrincipalProd.this, e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        }

    }
}