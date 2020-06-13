package com.example.campagnon;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.CustomGridAdapter;
import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.Produit;
import com.example.campagnon.Class.User;

import java.util.List;

public class MesProduitsActivity extends AppCompatActivity {
    User leProd;
    Produit leProduit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_produit_producteur_side);
        leProd = LesUsers.getUserID(getIntent().getExtras().getString("identifiant"));
        List<Produit> image_details = leProd.getListProduit();
        final GridView gridView = (GridView) findViewById(R.id.gridviewProduitProd);
        gridView.setAdapter(new CustomGridAdapter(this, image_details));


    }
}