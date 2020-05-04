package ca.qc.bdeb.voyagr.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import ca.qc.bdeb.voyagr.Modele.Modele;
import ca.qc.bdeb.voyagr.R;

/**
 * Cette classe permet de gérer le menu lorsque que l'utilisateur appuie sur le bouton "Menu"
 * se trouvant sur la carte du monde
 */
public class Menu extends AppCompatActivity {


    private Button btnAfficherListe, btnAfficherQuiz, btnAfficherParametre, btnAfficherAide, btnRetourArriere, btnSeDeconnecter, btnAfficherAPropos;
    private FirebaseAuth accesFirebase;
    private Modele modele = new Modele();

    /**
     * Permet de lancer l'activité et le layout associé à la classe Menu
     * @param savedInstanceState état de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        accesFirebase = FirebaseAuth.getInstance();
        creerComposantes();
        creerActionBoutons();
        modele.espacerLesNombreDouble(1267834.7867934);

    }

    /**
     * Cette méthode permet de créer les boutons dans la classe menu.
     */
    private void creerComposantes() {
        btnAfficherListe = (Button) findViewById(R.id.btnListePays);
        btnAfficherQuiz = (Button) findViewById(R.id.btnQuiz);
        btnAfficherParametre = (Button) findViewById(R.id.btnParametre);
        btnAfficherAide = (Button) findViewById(R.id.btnAide);
        btnRetourArriere = (Button) findViewById(R.id.btnRetour);
        btnSeDeconnecter = (Button) findViewById(R.id.btnDeconnection);
        btnAfficherAPropos = (Button) findViewById(R.id.btnAPropos);
    }

    /**
     * Permet d'activer les action de tout les boutons du menu
     */
    private void creerActionBoutons() {
        btnRetourArriere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Menu.this, CarteDuMonde.class));
                //pas de start activity, car on fait déjà un finish afin de revenir à la carte du monde
            }
        });

        btnAfficherParametre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Parametres.class));
                finish();
            }
        });

        btnSeDeconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accesFirebase.signOut();

                startActivity(new Intent(Menu.this, Login.class));
                finish();
            }
        });
        btnAfficherListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Menu.this, ListePays.class));
                finish();
            }
        });

        btnAfficherQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ChoixQuiz.class));
                finish();
            }
        });

        btnAfficherAide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Aide.class));
                finish();
            }
        });
        btnAfficherAPropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment aPropos = new APropos();
                aPropos.show(getSupportFragmentManager(), "MyDialogFragmentTag");
            }
        });
    }
}
