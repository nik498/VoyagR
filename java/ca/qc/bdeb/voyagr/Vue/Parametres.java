package ca.qc.bdeb.voyagr.Vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import java.util.Locale;
import ca.qc.bdeb.voyagr.Modele.Modele;
import ca.qc.bdeb.voyagr.R;

/**
 * Cette classe permet de gérer le changement de langue et le changement du type de carte désiré
 * par l'utilisateur
 */
public class Parametres extends AppCompatActivity  {

    private Button retourArriere;
    private Modele modele = new Modele();
    private Button btnNormal, btnHybride, btnTerrain, btnFrancais, btnAllemand;

    /**
     * Permet de lancer l'activité et le layout rélié à la classe
     * @param savedInstanceState état de l'activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);
        creerComposantes();
        creerActionBoutons();
        //permet de bloquer le boutons associé à la langue en cours
        if (Modele.getLangueFrancais()){
            btnFrancais.setEnabled(false);
        } else{
            btnAllemand.setEnabled(false);
        }
        bloquerTypeCarteActuel();

    }


    /**
     * Permet de creer les actions sur tous les boutons relié à la classe
     */
    private void creerActionBoutons() {
        retourArriere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Parametres.this, Menu.class));
                finish();
            }
        });


        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modele.setTypeCarte(GoogleMap.MAP_TYPE_NORMAL);
                retournerALaCarte(btnNormal);
                modele.modifierTableauCarte();
                Modele.setTabTypeDeCarte(0);
            }
        });

        btnHybride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modele.setTypeCarte(GoogleMap.MAP_TYPE_HYBRID);
                retournerALaCarte(btnHybride);
                modele.modifierTableauCarte();
                Modele.setTabTypeDeCarte(1);
            }
        });

        btnTerrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modele.setTypeCarte(GoogleMap.MAP_TYPE_TERRAIN);
                retournerALaCarte(btnTerrain);
                modele.modifierTableauCarte();
                Modele.setTabTypeDeCarte(2);
            }
        });

        btnFrancais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=getResources().getString(R.string.changementLangueMessageFR);
                lancerMessageConfirmation(message, btnFrancais);
            }
        });

        btnAllemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=getResources().getString(R.string.changementLangueMessageDE);
                lancerMessageConfirmation(message, btnAllemand);
            }
        });
    }

    /**
     * Permet d'afficher un message qui confirme le choix de l'utilisateur
     * @param message Avertissement à l'utilisateur
     * @return boolean vrai si l'utilisateur confirme le message
     */
    private void lancerMessageConfirmation(String message, final Button btnClique) {

        AlertDialog.Builder alerte = new AlertDialog.Builder(this);
        alerte.setTitle(getResources().getString(R.string.changementLangue));
        alerte.setMessage(message);
        alerte.setCancelable(false);
        String boutonPositif = getString(R.string.ok);
        alerte.setPositiveButton(boutonPositif, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(btnClique==btnAllemand){
                   //entrée dans le if du changement de langue
                    setLocale("de");
                    Modele.setLangueFrancais(false);
                    Modele.changerLangueListe();
                }
                else {
                    setLocale("fr");
                    Modele.setLangueFrancais(true);
                    Modele.changerLangueListe();
                }
            }
        });
        String boutonNegatif= getString(R.string.annuler);
        alerte.setNegativeButton(boutonNegatif, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });
        AlertDialog alertDialog=alerte.create();
        alertDialog.show();
    }

    /**
     * Permet de creer les composantes pour ajouter des actions
     */
    private void creerComposantes() {
        retourArriere = (Button) findViewById(R.id.btnRetourParametres);
        btnNormal = (Button) findViewById(R.id.btnCarteDefaut);
        btnHybride = (Button) findViewById(R.id.btnCarteSatellite);
        btnTerrain = (Button) findViewById(R.id.btnCarteRelief);
        btnAllemand = (Button) findViewById(R.id.btnDE);
        btnFrancais = (Button) findViewById(R.id.btnFR);
    }

    /**
     * Permet de changer la langue de l'application
     * @param lang abreviation de la nouvelle langue
     */
    private void setLocale(String lang){
        Locale nouvelleLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = nouvelleLocale;
        res.updateConfiguration(conf, dm);
        Intent rafraichi = new Intent(Parametres.this, CarteDuMonde.class);
        finish();
        startActivity(rafraichi);
    }

    /**
     * Permet de bloquer le bouton dont la carte est déjà actif
     */
    private void bloquerTypeCarteActuel() {
        for (int i = 0; i<Modele.getTabTypeDeCarte().length; i++) {
            if (Modele.getTabTypeDeCarte()[i]) {
                switch (i) {
                    case 0:
                        btnNormal.setEnabled(false);
                        break;
                    case 1:
                        btnHybride.setEnabled(false);
                        break;

                    case 2:
                        btnTerrain.setEnabled(false);
                        break;
                }
            }
        }
    }


    /**
     * Permet de retourner à la carte et d'afficher le nouveau type de la carte
     * @param btnCliquer bouton que l'utilisateur à cliqué afin d'en récupérer le texte
     */
    private void retournerALaCarte(Button btnCliquer){
        startActivity(new Intent(Parametres.this, CarteDuMonde.class));
        Toast.makeText(this, btnCliquer.getText(), Toast.LENGTH_SHORT).show();
        this.finish();

    }


}
