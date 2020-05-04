package ca.qc.bdeb.voyagr.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ca.qc.bdeb.voyagr.Modele.GestionFirebase;
import ca.qc.bdeb.voyagr.R;

/**
 * Cette classe permet de gérer les événements relié à la vérification du email
 */
public class VerificationEmail extends AppCompatActivity {

    private Button emailVerifier;
    private GestionFirebase gestionFirebase = new GestionFirebase();

    /**
     * Permet de lancer l'activity et le layout relié à la classe pour signaler l'envoie du email
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_email);
        activerComposante();
        activerAction();
        gestionFirebase.envoyerEmailVerification();
        gestionFirebase.deconnexion();
    }

    /**
     * Permet d'activer des événements sur les boutons
     */
    private void activerComposante() {
        emailVerifier = findViewById(R.id.btnVerificationFaite);
    }

    /**
     * Permet de connecter l'utilisateur s'il a vérifié son email
     */
    private void activerAction() {
        emailVerifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerificationEmail.this, Login.class));
                finish();
            }
        });
    }
}
