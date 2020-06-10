package com.example.campagnon;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LeProducteur extends AppCompatActivity {
    User monUser;
    User leProd;
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
    }
}