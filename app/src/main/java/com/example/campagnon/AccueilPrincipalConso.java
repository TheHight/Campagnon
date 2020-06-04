package com.example.campagnon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class AccueilPrincipalConso extends AppCompatActivity {
    JSONObject log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_accueil_conso);
        try {
            log = new JSONObject(getIntent().getStringExtra("log"));
            final TextView textUser = (TextView) findViewById(R.id.display_nom_user);
            textUser.setText(log.getString("identifiant"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ImageView imageProfil = (ImageView) findViewById(R.id.profil_conso_access);
        imageProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilPrincipalConso.this, Modification.class);
                intent.putExtra("log", log.toString());
                startActivity(intent);
            }
        });
    }
}
