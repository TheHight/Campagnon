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
    JSONObject log;
    JSONArray array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_conso1);
        try {
            log = new JSONObject(getIntent().getStringExtra("log"));
            new BackTaskRecupererMesProducteurs().execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private class BackTaskRecupererMesProducteurs extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {

                RequestBody formBody = new FormBody.Builder()
                        .add("id", log.getString("identifiant"))
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/recupererProd.php")
                        .post(formBody)
                        .build();
                Response response = client.newCall(request).execute();
                responseStr = response.body().string();
            }
            catch (Exception e) {
                Log.d("Test", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            try {

                if(!responseStr.equals("false")){
                    int id;
                    String nom_entreprise;
                    array = new JSONArray(responseStr);
                    final ArrayList<HashMap<String,String>> listeProd = new  ArrayList<HashMap<String,String>>();

                    HashMap<String,String> item ;

                    for (int i = 0; i < array.length(); i++){
                        item = new HashMap<String,String>();
                        JSONObject row = array.getJSONObject(i);

                        item.put("ligne1", row.getString("nomEntreprise"));

                        item.put("ligne2" , "Client :" );

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
                            try {
                                intent.putExtra("Prod", array.getJSONObject(position).toString());
                                intent.putExtra("log", log.toString());
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            startActivity(intent);
                        }
                    });


                }else{
                    TextView emptylist =(TextView) findViewById(R.id.textView21);
                    emptylist.setText("Vous n'avez pas encore ajouté de producteurs à votre liste !");
                }
            } catch (Exception e) {
                Toast.makeText(MesProducteurs.this, e.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
}