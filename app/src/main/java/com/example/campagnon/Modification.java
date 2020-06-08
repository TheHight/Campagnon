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

import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.User;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Modification extends AppCompatActivity {
    String responseStr;
    EditText textMdp;
    EditText textMdp2;
    EditText textLogin;
    EditText textTel;
    EditText textCp;
    EditText textVille;
    EditText textAdresse;
    EditText textMail;
    EditText nomEntreprise;
    User monUser;
    OkHttpClient client = new OkHttpClient();
    String identifiant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_inscription);
        textMdp = findViewById(R.id.mdpInscription1);
        textMdp2 = findViewById(R.id.mdpInscription2);
        textLogin = findViewById(R.id.idInscription);
        textLogin.setFocusable(false);
        textTel = findViewById(R.id.tel);
        textCp = findViewById(R.id.code_postal);
        textVille = findViewById(R.id.ville);
        textAdresse = findViewById(R.id.adresse);
        textMail = findViewById(R.id.email);
        nomEntreprise = findViewById(R.id.nomEntreprise);


        identifiant = getIntent().getStringExtra("identifiant");
        monUser = LesUsers.getUserID(identifiant);
        textLogin.setText(monUser.getIdentifiant());
        textLogin.setFocusable(false);
        textMail.setText(monUser.getEmail());
        textTel.setText(monUser.getTel());
        textCp.setText(monUser.getCode_postal());
        textVille.setText(monUser.getVille());
        textAdresse.setText(monUser.getAdresse());
        nomEntreprise.setText(monUser.getNomEntreprise());


        final TextView statut = (TextView) findViewById(R.id.statutViewText);
        if (monUser.getStatut().equals("Producteur")) {
            nomEntreprise.setVisibility(View.VISIBLE);

            statut.setText("Nom de l'entreprise :");
        } else {
            statut.setVisibility(View.GONE);
        }

        final RadioGroup Radio = (RadioGroup) findViewById(R.id.radioGroupStatut);
        Radio.setVisibility(View.GONE);
        final Button enregister = (Button) findViewById(R.id.inscription);
        enregister.setText("Enregistrer");
        enregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText textMdp = findViewById(R.id.mdpInscription1);
                final EditText textMdp2 = findViewById(R.id.mdpInscription2);
                if (textMdp.getText().toString().equals(textMdp2.getText().toString())) {

                    new BackTaskEnregistrement().execute();

                } else {
                    Toast.makeText(Modification.this, "Les deux mots de passe ne correspondent pas ",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    private class BackTaskEnregistrement extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Void doInBackground(Void... params){
            try {

                RequestBody formBody = new FormBody.Builder()
                        .add("id", textLogin.getText().toString())
                        .add("email", textMail.getText().toString())
                        .add("adresse", textAdresse.getText().toString())
                        .add("ville", textVille.getText().toString())
                        .add("cp", textCp.getText().toString())
                        .add("tel", textTel.getText().toString())
                        .add("mdp", textMdp.getText().toString())
                        .add("nomEntreprise", nomEntreprise.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://campagnon.tk/mettreajourprofil.php")
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
                    Toast.makeText(Modification.this, "Cet identifiant existe déjà !",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(Modification.this, "Erreur de connexion !!!!!",
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
                if (responseStr.compareTo("false") != 0) {
                    try {

                        monUser.setEmail(textLogin.getText().toString());
                        monUser.setTel(textTel.getText().toString());
                        monUser.setCode_postal(textCp.getText().toString());
                        monUser.setVille(textVille.getText().toString());
                        monUser.setAdresse(textAdresse.getText().toString());
                        monUser.setNomEntreprise(nomEntreprise.getText().toString());
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(Modification.this, "Erreur de connexion !!!!!",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Modification.this, "Le mot de passe n'est pas valide !", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(Modification.this, "Erreur de connexion !!!!!",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
}
