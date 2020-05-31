package com.example.campagnon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Inscription extends AppCompatActivity {
    String responseStr;
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_inscription);
        final Button enregister = (Button) findViewById(R.id.enregistrer);
        enregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText textMdp = findViewById(R.id.mdpInscription1);
                final EditText textMdp2 = findViewById(R.id.mdpInscription2);
                if(textMdp.getText().toString().equals(textMdp2.getText().toString())){
                    new BackTaskInscription().execute();
                }else{
                    Toast.makeText(Inscription.this, "Les deux mots de passe sont différents", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private class BackTaskInscription extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {
                final EditText textLogin = findViewById(R.id.id);
                final EditText textMdp = findViewById(R.id.mdpInscription1);
                final EditText textTel = findViewById(R.id.tel);
                final EditText textCp = findViewById(R.id.code_postal);
                final EditText textVille = findViewById(R.id.ville);
                final EditText textAdresse = findViewById(R.id.adresse);
                final EditText textMail = findViewById(R.id.email);
                final RadioButton radioUtil = findViewById(R.id.radioButton3);
                String statut = "Producteur";
                if(radioUtil.isChecked()){
                    statut = "Consommateur";
                }
                RequestBody formBody = new FormBody.Builder()
                        .add("id", textLogin.getText().toString())
                        .add("email", textMail.getText().toString())
                        .add("adresse", textAdresse.getText().toString())
                        .add("ville", textVille.getText().toString())
                        .add("cp", textCp.getText().toString())
                        .add("tel", textTel.getText().toString())
                        .add("mdp", textMdp.getText().toString())
                        .add("statut", statut)
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/inscription.php")
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
                Intent intent = new Intent(Inscription.this, MainActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(Inscription.this, "Erreur d'enregistrement", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
