package com.example.campagnon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MesProducteurs extends AppCompatActivity {

    String responseStr;
    OkHttpClient client = new OkHttpClient();
    String identifiant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_conso1);
        identifiant = getIntent().getStringExtra("identifiant");


        final ArrayList<HashMap<String,String>> listeProd = new  ArrayList<HashMap<String,String>>();

        HashMap<String,String> item ;
        User monUser = LesUsers.getUserID(identifiant);
        for(User unUser : monUser.getListUserProdClient()){
            item = new HashMap<String,String>();

            item.put("ligne1",unUser.getNomEntreprise());
            //item.put("idCommande" , uneComm.getIdCommande());

            listeProd.add(item);
        }
        ListView listViewProducteur = (ListView) findViewById(R.id.list_producteur);

        SimpleAdapter adapter = new SimpleAdapter(MesProducteurs.this, listeProd, android.R.layout.simple_list_item_1,

                new String[]{"ligne1"},new int[]{android.R.id.text1 , android.R.id.text2});

        listViewProducteur.setAdapter(adapter);
        listViewProducteur.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MesProducteurs.this, LeProducteur.class);
                User monUser = LesUsers.getUserID(identifiant);
                intent.putExtra("Prod", monUser.getListUserProdClient().get(position).getIdentifiant());
                intent.putExtra("identifiant", identifiant.toString());
                startActivity(intent);
            }
        });
    }


}