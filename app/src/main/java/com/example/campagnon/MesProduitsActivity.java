package com.example.campagnon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.campagnon.Class.CustomGridAdapterProducteur;
import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.Produit;
import com.example.campagnon.Class.User;

import java.util.List;

public class MesProduitsActivity extends AppCompatActivity {
    User leProd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_produit_producteur_side);
        leProd = LesUsers.getUserID(getIntent().getExtras().getString("identifiant"));
        List<Produit> image_details = leProd.getListProduit();
        final GridView gridView = (GridView) findViewById(R.id.gridviewProduitProd);
        gridView.setAdapter(new CustomGridAdapterProducteur(this, image_details));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                Produit produit = (Produit) o;
                Intent intent = new Intent(MesProduitsActivity.this, ProduitProducteurActivity.class);
                intent.putExtra("identifiant", leProd.getIdentifiant());
                intent.putExtra("leProduit", ((Produit) o).getNom_produit());
                intent.putExtra("idProducteur", leProd.getIdentifiant());
                startActivity(intent);
            }
        });

        final ImageView imageAjout = (ImageView) findViewById(R.id.add_product_button);
        {
            imageAjout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MesProduitsActivity.this, ajouterProduitProducteurActivity.class);
                    intent.putExtra("identifiant", leProd.getIdentifiant());
                    startActivity(intent);

                }
            });
        }

    }
}