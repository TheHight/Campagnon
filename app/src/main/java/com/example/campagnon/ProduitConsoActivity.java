package com.example.campagnon;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.Produit;
import com.example.campagnon.Class.User;
import com.squareup.picasso.Picasso;

public class ProduitConsoActivity extends AppCompatActivity {
    User monClient;
    User leProd;
    Produit leProduit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conso_fiche_produit);
        monClient = LesUsers.getUserID(getIntent().getExtras().getString("identifiant"));
        leProd = LesUsers.getUserID(getIntent().getExtras().getString("idProducteur"));
        leProduit = leProd.chercherProduit(getIntent().getExtras().getString("leProduit"));

        ImageView monImage = (ImageView) findViewById(R.id.display_image_produit1);
        Picasso.with(this).load(leProduit.getImage()).into(monImage);
        TextView nom = (TextView) findViewById(R.id.textViewProduitt);
        nom.setText(leProduit.getNom_produit());
    }
}