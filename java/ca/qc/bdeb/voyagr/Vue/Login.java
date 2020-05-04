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
 * Cette classe permet de gérer le Login de l'utilisateur et d'envoyer
 * les informations pertinentes à la classe Gestion Firebase
 */
public class Login extends AppCompatActivity {

    private EditText emailUtilisateur, motDePasseUtilisateur;
    private Button connecter;
    private TextView nouveauCompte, motDePasseOublier;

    /**
     * Permet de lancer l'activité et le layout lorsque le onCreate est appelé. Cette méthode va aussi
     * permet au programme de faire un lien avec les autres méthodes de la classe
     * @param savedInstanceState état de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        GestionFirebase.setAccesFirebase(FirebaseAuth.getInstance());
        GestionFirebase.setUtilisateurFirebase(GestionFirebase.getAccesFirebase().getCurrentUser());
        resterConnecter();
        setupUIViews();
        activerBouton();
    }


    /**
     * Permet de faire les changements d'activity lorsque l'utilisateur clique sur un bouton ou sur un text field
     */
    private void activerBouton() {
        nouveauCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Login.this, CreerCompte.class));
            }
        });
        connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valider();
            }
        });
        motDePasseOublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Login.this, MotDePasseOublier.class));
            }
        });
    }

    /**
     * Permet de s'assurer que le compte de l'utilisateur est créer dans Firebase. S'il n'est pas
     * déjà existant, l'utilisateur ne sera pas en mesure de se connecté à l'application. Si
     * l'utilisateur n'a jamais vérifié son compte, VoyagR va lui demander de vérifier ses courriels
     */
    private void valider() {
        String motDePasse = motDePasseUtilisateur.getText().toString().trim();
        String email = emailUtilisateur.getText().toString().trim();

        if (motDePasse.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, getString(R.string.infoManquante), Toast.LENGTH_SHORT).show();
        } else {
            GestionFirebase.getAccesFirebase().signInWithEmailAndPassword(email, motDePasse).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        GestionFirebase.setUtilisateurFirebase(GestionFirebase.getAccesFirebase().getCurrentUser());
                        verificationEmail();
                    } else {
                        Toast.makeText(Login.this, getString(R.string.erreurConnexion), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Permet d'activer les composantes graphiques pour la classe qui gère la connexion
     */
    private void setupUIViews() {
        emailUtilisateur = (EditText) findViewById(R.id.txtEmail);
        motDePasseUtilisateur = (EditText) findViewById(R.id.txtMotDePasse);
        connecter = (Button) findViewById(R.id.btnConnecter);
        nouveauCompte = (TextView) findViewById(R.id.tvCreerCompte);
        motDePasseOublier = (TextView) findViewById(R.id.tvMotDePasseOublier);
    }


    /**
     * Permet à un utilisateur de rester connecter sans avoir à refaire son mot de passe entre chaque
     * retour dans VoyagR
     */
    private void resterConnecter() {
        if (GestionFirebase.getUtilisateurFirebase() != null) {
            finish();
            startActivity(new Intent(Login.this, CarteDuMonde.class));
            Toast.makeText(Login.this, R.string.bienvenue, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Permet de gérer si l'utilisateur à vérifier son email pour Firebase
     */
    private void verificationEmail() {
        Boolean utilisateurVerfier = GestionFirebase.getUtilisateurFirebase().isEmailVerified();
        if (utilisateurVerfier) {
            Toast.makeText(Login.this, getString(R.string.connexionReussi), Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(Login.this, CarteDuMonde.class));
            Toast.makeText(Login.this, R.string.bienvenue, Toast.LENGTH_SHORT).show();
        } else {
            finish();
            startActivity(new Intent(Login.this, VerificationEmail.class));

        }
    }
}
