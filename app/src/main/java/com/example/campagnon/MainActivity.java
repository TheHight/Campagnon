package com.example.campagnon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.User;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String responseStr;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_connexion);
        final TextView textNouveau = (TextView) findViewById(R.id.nouveauUtilisateur);
        textNouveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Inscription.class);
                startActivity(intent);
            }
        });
        final TextView textMdpOublie = (TextView) findViewById(R.id.mdpOublie);
        textMdpOublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Mdp_oublie.class);
                startActivity(intent);
            }
        });

        final Button buttonConnection = (Button) findViewById(R.id.connecter);
        buttonConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackTaskAuthentification().execute();

            }
        });
    }



    private class BackTaskRecupererUsers extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            LesUsers.clearListe();
        }
        @Override
        protected Void doInBackground(Void... params){
            try {

                RequestBody formBody = new FormBody.Builder()
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/recupererUser.php")
                        .post(formBody)
                        .build();
                Response response = client.newCall(request).execute();
                responseStr = response.body().string();
            }
            catch (Exception e) {
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            try {
                if(!responseStr.equals("false")){

                    JSONArray array = new JSONArray(responseStr);
                    for (int i = 0; i < array.length(); i++){
                        User newUser = new User();
                        JSONObject row = array.getJSONObject(i);
                        newUser.setIdentifiant(row.getString("identifiant"));
                        newUser.setAdresse(row.getString("adresse"));
                        newUser.setCode_postal(row.getString("cp"));
                        newUser.setStatut(row.getString("statut"));
                        newUser.setVille(row.getString("ville"));
                        newUser.setEmail(row.getString("email"));
                        newUser.setTel(row.getString("tel"));
                        newUser.setX(row.getString("x"));
                        newUser.setY(row.getString("y"));
                        newUser.setNomEntreprise(row.getString("nomEntreprise"));
                        LesUsers.ajouterUser(newUser);
                    }
                }
            } catch (Exception e) {

            }
        }

    }

    private class BackTaskAuthentification extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {
                final EditText textLogin = findViewById(R.id.idConnect);
                final EditText textMdp = findViewById(R.id.mdpConnect);
                RequestBody formBody = new FormBody.Builder()
                        .add("id", textLogin.getText().toString())
                        .add("mdp", textMdp.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/identification.php")
                        .post(formBody)
                        .build();
                Response response = client.newCall(request).execute();
                responseStr = response.body().string();
            }
            catch (Exception e) {
                Log.d("Test", "Erreur de connexion 1 !!!!");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            if (responseStr.compareTo("false") != 0) {
                try {
                    new BackTaskRecupererUsers().execute();
                    JSONObject log = new JSONObject(responseStr);
                    if(log.getString("statut").toString().equals("Consommateur")){
                        Intent intent = new Intent(MainActivity.this, AccueilPrincipalConso.class);
                        intent.putExtra("identifiant", log.getString("identifiant"));
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(MainActivity.this, AccueilPrincipalProd.class);
                        intent.putExtra("identifiant", log.getString("identifiant"));
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Erreur de connexion 2!!!!!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Login ou mot de passe non valide !", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
