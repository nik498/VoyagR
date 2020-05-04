package ca.qc.bdeb.voyagr.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ca.qc.bdeb.voyagr.Modele.GestionFirebase;
import ca.qc.bdeb.voyagr.R;

/**
 * C'est la classe qui gère le création de compte pour un nouvel utilisateur
 */
public class CreerCompte extends AppCompatActivity {

    private EditText emailUtilisateur, motDePasseUtilisateur, nomUtilisateur;
    private String nomCompte, email, motDePasse;
    private Button creerCompte;
    private TextView seConnecter;
    private FirebaseAuth accesFirebase;
    private GestionFirebase gestionFirebase = new GestionFirebase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);
        accesFirebase = FirebaseAuth.getInstance();
        setupUIViews();
        activerEvenements();
    }

    /**
     * Permet d'activer les composants graphiques et de les relier à la classe
     */
    private void setupUIViews() {
        emailUtilisateur = (EditText) findViewById(R.id.txtEmail);
        motDePasseUtilisateur = (EditText) findViewById(R.id.txtMotDePasse);
        nomUtilisateur = (EditText) findViewById(R.id.txtNom);
        creerCompte = (Button) findViewById(R.id.btnCreerCompte);
        seConnecter = (TextView) findViewById(R.id.tvSeConnecter);
    }

    /**
     * Permet de changer d'activity
     */
    private void activerEvenements() {
        creerCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creerCompte();
            }
        });
        seConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(CreerCompte.this, Login.class));
            }
        });
    }

    /**
     * Permet de créer un compte dans Firebase
     */
    private void creerCompte() {
        nomCompte = nomUtilisateur.getText().toString().trim();
        motDePasse = motDePasseUtilisateur.getText().toString().trim();
        email = emailUtilisateur.getText().toString().trim();
        if (nomCompte.isEmpty() || motDePasse.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, getString(R.string.infoManquante), Toast.LENGTH_SHORT).show();
        } else if (motDePasse.length() < 6) {
            Toast.makeText(this, getString(R.string.mdpCourt), Toast.LENGTH_SHORT).show();
        } else {
            gestionFirebase.setNomUtilisateur(nomCompte);
            gestionFirebase.setEmailUtilisateur(email);
            accesFirebase.createUserWithEmailAndPassword(email, motDePasse).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        GestionFirebase.setAccesFirebase(accesFirebase);
                        GestionFirebase.setUtilisateurFirebase(accesFirebase.getCurrentUser());
                        if (!gestionFirebase.isUtilisateurVerifier()) {
                            Toast.makeText(CreerCompte.this, R.string.emailEnvoyer, Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(CreerCompte.this, VerificationEmail.class));
                        }
                    } else {
                        Toast.makeText(CreerCompte.this, getString(R.string.problemeCompte), Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }
}
