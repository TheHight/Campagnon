package com.example.campagnon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.Commande;
import com.example.campagnon.Class.CustomGridAdapterProducteur;
import com.example.campagnon.Class.LesCommandes;
import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.Produit;
import com.example.campagnon.Class.User;

import java.util.List;

public class CommandesAttentesActivity extends AppCompatActivity {
    User leClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_commandes_en_attentes_prodside);

    }
}
