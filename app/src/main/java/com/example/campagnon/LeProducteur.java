package com.example.campagnon;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LeProducteur extends AppCompatActivity {
    String identifiantConso;
    String identifiantProd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conso_fiche_producteur);
        identifiantConso = getIntent().getStringExtra("identifiant");
        identifiantProd = getIntent().getStringExtra("Prod");
        User monProd = LesUsers.getUserID(identifiantProd);
        TextView nomEntreprise = (TextView) findViewById(R.id.display_nom_prod);
        nomEntreprise.setText(monProd.getNomEntreprise());
        TextView adresse = (TextView) findViewById(R.id.display_adresseprod_ficheprod);
        adresse.setText(monProd.getAdresse());
    }
}