package com.example.campagnon;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

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
        final Button enregister = (Button) findViewById(R.id.inscription);
        enregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText textMdp = findViewById(R.id.mdpInscription1);
                final EditText textMdp2 = findViewById(R.id.mdpInscription2);
                if(textMdp.getText().toString().equals(textMdp2.getText().toString())){

                    new BackTaskInscription().execute();

                }else{
                    Toast.makeText(Inscription.this, "Les deux mots de passe ne correspondent pas ",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        final RadioGroup Radio = (RadioGroup) findViewById(R.id.radioGroupStatut);
        Radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final RadioButton radioConso = (RadioButton) findViewById(R.id.radioButton4);
                final TextView textNom = (TextView) findViewById(R.id.textViewN);
                final EditText NomEntre = (EditText) findViewById(R.id.nomEntreprise);
                if(radioConso.isChecked()){
                    textNom.setVisibility(View.VISIBLE);
                    NomEntre.setVisibility(View.VISIBLE);
                }else{
                    textNom.setVisibility(View.GONE);
                    NomEntre.setVisibility(View.GONE);
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
                final EditText textMdp = findViewById(R.id.mdpInscription1);
                final EditText textMdp2 = findViewById(R.id.mdpInscription2);
                final EditText textLogin = findViewById(R.id.idInscription);
                final EditText textTel = findViewById(R.id.tel);
                final EditText textCp = findViewById(R.id.code_postal);
                final EditText textVille = findViewById(R.id.ville);
                final EditText textAdresse = findViewById(R.id.adresse);
                final EditText textMail = findViewById(R.id.email);
                final EditText nomEntreprise = findViewById(R.id.nomEntreprise);
                final RadioButton radioUtil = findViewById(R.id.radioButton3);
                String statut = "Producteur";
                if (radioUtil.isChecked()) {
                    statut = "Consommateur";
                }
                String x="0";
                String y="0";
                try {
                    URI uri = new URI("https://api-adresse.data.gouv.fr/search/?q="+textAdresse.getText().toString().replace(' ','+')+"+"+textVille.getText().toString().replace(' ','+')+"+"+textCp.getText().toString().replace(' ','+')+"&limit=1");
                    JSONObject donne = new JSONObject(getHttpResponse(uri));
                    x =donne.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").getString(0);
                    y =donne.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").getString(1);
                } catch (Exception e) {
                    Toast.makeText(Inscription.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        .add("x", x)
                        .add("y", y)
                        .add("nomEntreprise", nomEntreprise.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/inscription.php")
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
                    final CheckBox Carte = (CheckBox) findViewById(R.id.checkBoxCB);
                    if(Carte.isChecked()){

                        new BackTaskAjoutType(Carte.getText().toString()).execute();
                    }
                    final CheckBox Espece = (CheckBox) findViewById(R.id.checkBoxEspece);
                    if(Espece.isChecked()){
                        new BackTaskAjoutType(Espece.getText().toString()).execute();
                    }

                    finish();
                }else{
                    Toast.makeText(Inscription.this, "Cet identifiant existe déjà !",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(Inscription.this, "Erreur de connexion !!!!!",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }



    private class BackTaskAjoutType extends AsyncTask<Void, Void, Void> {

        private String typ;
        public BackTaskAjoutType(String type) {
            super();
            typ = type;
        }
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {
                final EditText textLogin = findViewById(R.id.idInscription);

                RequestBody formBody = new FormBody.Builder()
                        .add("id", textLogin.getText().toString())
                        .add("type", typ)
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/ajoutPayement.php")
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

            } catch (Exception e) {
                Toast.makeText(Inscription.this, "Erreur de connexion !!!!!",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
    public static String getHttpResponse(URI uri2) {
        StringBuilder response = new StringBuilder();
        try {
            HttpGet get = new HttpGet();
            get.setURI(uri2);
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(get);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                Log.d("[GET REQUEST]", "HTTP Get succeeded");

                HttpEntity messageEntity = httpResponse.getEntity();
                InputStream is = messageEntity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (Exception e) {
            Log.e("[GET REQUEST]", e.getMessage());
        }
        Log.d("[GET REQUEST]", "Done with HTTP getting");
        return response.toString();
    }
}
