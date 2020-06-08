package com.example.campagnon;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LeProducteur extends AppCompatActivity {
    JSONObject log;
    JSONObject Prod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conso_fiche_producteur);
        try {
            log = new JSONObject(getIntent().getStringExtra("log"));
            Prod = new JSONObject(getIntent().getStringExtra("Prod"));
            TextView nomEntreprise = (TextView) findViewById(R.id.display_nom_prod);
            nomEntreprise.setText(Prod.getString("nomEntreprise"));
            TextView adresse = (TextView) findViewById(R.id.display_adresseprod_ficheprod);
            adresse.setText(Prod.getString("adresse"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}