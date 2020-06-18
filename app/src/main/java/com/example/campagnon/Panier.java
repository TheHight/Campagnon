package com.example.campagnon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.CustomGridAdapterProducteur;
import com.example.campagnon.Class.LesCommandes;
import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.Produit;
import com.example.campagnon.Class.User;

import java.util.List;

public class Panier extends AppCompatActivity {
    User leClient;
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
    }
}