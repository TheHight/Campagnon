package com.example.campagnon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
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
    }




    private class BackTaskAuthentification extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {
                final EditText textLogin = findViewById(R.id.id);
                final EditText textMdp = findViewById(R.id.mdp);
                RequestBody formBody = new FormBody.Builder()
                        .add("login", textLogin.getText().toString())
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
                Log.d("Test", "Erreur de connexion !!!!");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            if (responseStr.compareTo("false") != 0) {
                try {
                    JSONObject log = new JSONObject(responseStr);
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("log", log.toString());
                    startActivity(intent);
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Erreur de connexion !!!!!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Login ou mot de passe non valide !", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
