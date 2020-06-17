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
    //Attributs disponible partout
    User leProd;
    String responseStr;
    String identifiant;
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //On lance le layout page_accueil_prod
        setContentView(R.layout.page_accueil_prod);
        //On récupere l'identifiant du précedent intent
        identifiant=getIntent().getStringExtra("identifiant");

        //On récupere le TextView qui affiche le nom de l'User
        final TextView textUser = (TextView) findViewById(R.id.display_nom_user);
        //On écrit de nom de l'user dans le textView
        textUser.setText(identifiant);

        //On récupere l'objet image view correspondant au profil
        final ImageView imageProfil = (ImageView) findViewById(R.id.profil_conso_access);{
            //lorsque l'on clique sur cette image...
        imageProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creation nouvelle activity
                Intent intent = new Intent(AccueilPrincipalProd.this, Modification.class);
                //Donne les identifiants en para au nouveau intent
                intent.putExtra("identifiant", identifiant);
                //Lance la page modification
                startActivity(intent);

            }
        });


        //On récupere le bouton correspondant à mes produits
        final Button mesProduits = (Button) findViewById(R.id.interface_prod_1);
        //Lorsque l'on clique sur le bouton...
        mesProduits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Création nouvel intent
                Intent intent = new Intent(AccueilPrincipalProd.this, MesProduitsActivity.class);
                //On lui donne l'identifiant en para
                intent.putExtra("identifiant", identifiant);
                //on lance l'intent
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


    //VOIR ACCUEUILPRINCIPALPROD
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
                            User leProd = LesUsers.getUserID(getIntent().getStringExtra("identifiant"));
                            leProduit.setLeProd(leProd);
                            leProd.addProduit(leProduit);
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