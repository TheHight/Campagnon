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
import android.widget.TextView;
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

public class ProduitProducteurActivity extends AppCompatActivity {
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
        leProd = LesUsers.getUserID(getIntent().getExtras().getString("idProducteur"));
        leProduit = leProd.chercherProduit(getIntent().getExtras().getString("leProduit"));

        final ImageView monImage = (ImageView) findViewById(R.id.display_image_produit1);
        Picasso.with(this).load(leProduit.getImage()).into(monImage);
        final Context lecontext = this;

        lien = (EditText) findViewById(R.id.editTextLien);
        lien.setText(leProduit.getImage());
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
        nom.setText(leProduit.getNom_produit());

        type = (Spinner) findViewById(R.id.spinnerType);
        ArrayList<String> area = new ArrayList<>();
        area.add("Fruit");
        area.add("Légume");
        type.setAdapter(new ArrayAdapter<String>(this
                , android.R.layout.simple_list_item_1, area));

        //prixkilo.selec(leProduit.getPrix_kg());

        prixKilo = (EditText) findViewById(R.id.editTextKilo);
        prixKilo.setText(leProduit.getPrix_kg());

        description = (EditText) findViewById(R.id.editTextDescription);
        description.setText(leProduit.getDecription());

        quantite = (EditText) findViewById(R.id.editTextQuantite);
        quantite.setText(leProduit.getQté_produit());

        final Button enregister = (Button) findViewById(R.id.enregistrer);
        enregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackTaskEnregistrement().execute();

            }
        });
    }


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
                RequestBody formBody = new FormBody.Builder()
                        .add("type", type.getSelectedItem().toString())
                        .add("image", lien.getText().toString())
                        .add("nom", nom.getText().toString())
                        .add("qteProduit", quantite.getText().toString())
                        .add("prix", prixKilo.getText().toString())
                        .add("description", description.getText().toString())
                        .add("anciennom", leProduit.getNom_produit().toString())
                        .add("idProd", leProd.getIdentifiant())
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/modifierProduit.php")
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
                leProduit.setDecription(description.getText().toString());
                leProduit.setImage(lien.getText().toString());
                leProduit.setNom_produit(nom.getText().toString());
                leProduit.setQté_produit(quantite.getText().toString());
                leProduit.setPrix_kg(prixKilo.getText().toString());
                finish();
            }catch (Exception e) {
                Toast.makeText(ProduitProducteurActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

    }
}