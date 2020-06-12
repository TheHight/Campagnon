package com.example.campagnon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class AccueilPrincipalProd extends AppCompatActivity {
    String identifiant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_accueil_prod);

        identifiant = getIntent().getStringExtra("identifiant");


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
    }
}