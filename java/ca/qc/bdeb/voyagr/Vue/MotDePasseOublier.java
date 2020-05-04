package ca.qc.bdeb.voyagr.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ca.qc.bdeb.voyagr.Modele.GestionFirebase;
import ca.qc.bdeb.voyagr.R;

/**
 * Cette classe permet de gérer l'action que si un utilisateur à oublier son
 * mot de passe stocké dans Firebase
 */
public class MotDePasseOublier extends AppCompatActivity {

    private Button envoyerEmail;
    private EditText motDePasseEmail;
    private GestionFirebase gestionFirebase = new GestionFirebase();


    /**
     * Cet méthode permet de lancer l'activité et le layout relié à la classe MotDePasseOublie
     * @param savedInstanceState état de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mot_de_passe_oublier);
        creerComposantes();
        creerActionBoutons();
    }


    /**
     * Permet de créer les composantes dans la classe afin de les utiliser plus tard
     */
    private void creerComposantes() {
        envoyerEmail = (Button) findViewById(R.id.btnEnvoyerEmail);
        motDePasseEmail = (EditText) findViewById(R.id.txtEmailOublier);

    }

    /**
     * Permet de créer d'envoyer le email du mot de passe oublier lorsque l'utilisateur clique sur le
     * bouton envoyerEmail
     */
    private void creerActionBoutons() {
        envoyerEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = motDePasseEmail.getText().toString().trim();
                gestionFirebase.envoyerEmailMotDePasse(email);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Toast.makeText(MotDePasseOublier.this, GestionFirebase.getEtatEmail(), Toast.LENGTH_SHORT).show();

                    }
                }, 3000);

                finish();
                startActivity(new Intent(MotDePasseOublier.this, Login.class));

            }
        });
    }
}
