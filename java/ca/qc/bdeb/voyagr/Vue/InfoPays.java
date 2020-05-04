package ca.qc.bdeb.voyagr.Vue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ca.qc.bdeb.voyagr.Modele.Modele;
import ca.qc.bdeb.voyagr.R;

/**
 * Cette classe permet d'afficher les éléments des pays dépendemment de celui qui est cliqué.
 * Elle permet aussi de modifier les textes dépendemment des actions de l'utilisateur
 */
public class InfoPays extends AppCompatActivity {


    private TextView paysActuel, capitaleActuelle, superficieActuelle, populationActuelle, pibActuel;
    private ImageView imgDrapeau, imgCoeur;
    private Button btnRetour, btnTourisme, btnEconomie, btnCulture, btnScience;
    private Modele modele = new Modele();
    private int indice;
    private Context context;
    private String idPays;

    /**
     * Cette méthode est appelée au lancement de cette activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pays);

        creerComposantes();
        creerActionBoutons();
        modifierInfoPays();
        bloquerBouton();
        context = this;

    }

    /**
     * Permet de créer les composantes pour effectuer des actions avec
     */
    private void creerComposantes() {
        paysActuel = (TextView) findViewById(R.id.tvNomPays);
        capitaleActuelle = (TextView) findViewById(R.id.tvCapital);
        superficieActuelle = (TextView) findViewById(R.id.tvSuperficie);
        pibActuel = (TextView) findViewById(R.id.tvPib);
        populationActuelle = (TextView) findViewById(R.id.tvPopulation);

        btnRetour = (Button) findViewById(R.id.btnRetourInfo);
        btnEconomie = (Button) findViewById(R.id.btnEconomie);
        btnScience = (Button) findViewById(R.id.btnSciences);
        btnCulture = (Button) findViewById(R.id.btnCulture);
        btnTourisme = (Button) findViewById(R.id.btnTourisme);

        imgDrapeau = (ImageView) findViewById(R.id.imgDrapeau);

        imgCoeur = (ImageView) findViewById(R.id.imgCoeur);
    }


    /**
     * Permet d'afficher les informations du pays dans la bonne langue
     */
    private void modifierInfoPays() {
        indice = Modele.getIndice(Modele.getPaysClique());

        if (Modele.getLangueFrancais()) {
            paysActuel.setText(Modele.getListePays().get(indice).getNomFR());
            capitaleActuelle.setText(Modele.getListePays().get(indice).getCapitaleFR());

        } else {
            paysActuel.setText(Modele.getListePays().get(indice).getNomDE());
            capitaleActuelle.setText(Modele.getListePays().get(indice).getCapitaleDE());
        }


        superficieActuelle.setText(modele.espacerLesNombreDouble(Modele.getListePays().get(indice).getSuperficie()) + " km\u00B2");
        pibActuel.setText(modele.espacerLesNombreDouble(Modele.getListePays().get(indice).getPib()) + " G\u0024 USD");
        String population = modele.espacerLesNombreInt(Modele.getListePays().get(indice).getPopulation());
        if (Modele.getLangueFrancais()) {
            populationActuelle.setText(population + " habitants");
        } else {
            populationActuelle.setText(population + " Einwohner");
        }

        //TODO:Mettre des commentaires partout et faire la javadoc
        idPays = Modele.getListePays().get(indice).getISO2();
        String nomPNG = "@drawable/" + idPays;
        int resID = getResources().getIdentifier(nomPNG, "drawable,", getPackageName());
        imgDrapeau.invalidate();
        imgDrapeau.setImageResource(resID);
        imgDrapeau.refreshDrawableState();
        imgDrapeau.invalidate();

        if (Modele.verifierSiFavori(indice)) {
            imgCoeur.setImageResource(R.drawable.coeurbleuplein);
        } else {
            imgCoeur.setImageResource(R.drawable.coeurbleuvide);
        }
    }


    /**
     * Permet d'activer les actions de tous les boutons du menu
     */
    private void creerActionBoutons() {
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if(Modele.isVientDeLaCarte()){
                    startActivity(new Intent(InfoPays.this, CarteDuMonde.class));
                } else {
                    startActivity(new Intent(InfoPays.this, ListePays.class));
                }
            }
        });
        //TODO: rajouter l'obligation de localisation dans le doc

        imgCoeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modele.modifierFavori(indice, context);
                if (Modele.verifierSiFavori(indice)) {
                    imgCoeur.setImageResource(R.drawable.coeurbleuplein);
                } else {
                    imgCoeur.setImageResource(R.drawable.coeurbleuvide);
                }
            }
        });

        btnScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modele.setTypeInfoPaysComplete(1);
                startActivity(new Intent(InfoPays.this, InfoComplete.class));
            }
        });

        btnEconomie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modele.setTypeInfoPaysComplete(2);
                startActivity(new Intent(InfoPays.this, InfoComplete.class));
            }
        });

        btnTourisme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modele.setTypeInfoPaysComplete(3);
                startActivity(new Intent(InfoPays.this, InfoComplete.class));
            }
        });

        btnCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modele.setTypeInfoPaysComplete(4);
                startActivity(new Intent(InfoPays.this, InfoComplete.class));
            }
        });
    }


    /**
     * Permet de bloquer les boutons s'il n'a pas d'info supplémentaire
     */
    private void bloquerBouton() {
        if (Modele.isInfoSupExiste()) {
            //on peut garder les boutons activé

        } else {
            btnEconomie.setEnabled(false);
            btnTourisme.setEnabled(false);
            btnCulture.setEnabled(false);
            btnScience.setEnabled(false);
            enleverIcones();
        }
    }

    /**
     * Permet d'enlever les images des boutons lorsque cette méthode est appelée
     */
    private void enleverIcones() {
        btnScience.setCompoundDrawables(null, null, null, null);
        btnEconomie.setCompoundDrawables(null, null, null, null);
        btnCulture.setCompoundDrawables(null, null, null, null);
        btnTourisme.setCompoundDrawables(null, null, null, null);
    }

}
