package ca.qc.bdeb.voyagr.Vue;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.qc.bdeb.voyagr.Modele.Modele;
import ca.qc.bdeb.voyagr.Modele.Pays;
import ca.qc.bdeb.voyagr.R;

/**
 * Cette classe gère l'écran où l'utilisateur choisit selon quel caractéristique il veut trier la liste des pays
 */
public class ChoixTri extends AppCompatActivity {

    private Button btnPib, btnNom, btnSuperficie, btnCapitale, btnRetour, btnPopulation;
    private Modele modele = new Modele();
    private ArrayList<Pays> listeTri = new ArrayList<>();
    private ImageView imgCoeur;
    private ArrayList<Pays> listePays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_tri);
        listeTri = Modele.getListePays();

        creerComposantes();
        creerActionComposantes();
        bloquerTriActuel();
    }

    /**
     * Permet de lier les composantes graphiques de la classe à celle du layout du quiz.
     */
    private void creerComposantes() {
        btnNom = (Button) findViewById(R.id.btnTriNom);
        btnCapitale = (Button) findViewById(R.id.btnTriCapitale);
        btnPib = (Button) findViewById(R.id.btnTriPib);
        btnPopulation = (Button) findViewById(R.id.btnTriPopulation);
        btnSuperficie = (Button) findViewById(R.id.btnTriSuperficie);
        btnRetour = (Button) findViewById(R.id.btnRetourListe);
        imgCoeur = (ImageView) findViewById(R.id.imgCoeurTri);
        if (Modele.isUtiliserListeFavoris()) {
            imgCoeur.setImageResource(R.drawable.coeurbleuplein);
            listePays = modele.getListePaysFavori();
        } else {
            imgCoeur.setImageResource(R.drawable.coeurbleuvide);
            listePays = Modele.getListePays();
        }
    }


    /**
     * Permet de créer les événements sur les composantes à l'écran
     */
    private void creerActionComposantes() {
        btnCapitale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modele.modifierTableauTri();
                Modele.setTabTypeDeTri(1);
                Toast.makeText(ChoixTri.this, btnCapitale.getText(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChoixTri.this, ListePays.class));
                finish();

                Pays temp = null;
                int n = listePays.size();
                if (Modele.getLangueFrancais()) {
                    for (int i = 0; i < n; i++) {
                        for (int j = 1; j < (n - i); j++) {
                            if (listePays.get(j - 1).getCapitaleFR().compareTo(listePays.get(j).getCapitaleFR()) > 0) {
                                temp = listePays.get(j - 1);
                                listePays.set(j - 1, listePays.get(j));
                                listePays.set(j, temp);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < n; i++) {
                        for (int j = 1; j < (n - i); j++) {
                            if (listePays.get(j - 1).getCapitaleDE().compareTo(listePays.get(j).getCapitaleDE()) > 0) {
                                temp = listePays.get(j - 1);
                                listePays.set(j - 1, listePays.get(j));
                                listePays.set(j, temp);
                            }
                        }
                    }
                }

            }
        });

        btnPib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modele.modifierTableauTri();
                Modele.setTabTypeDeTri(3);
                Toast.makeText(ChoixTri.this, btnPib.getText(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChoixTri.this, ListePays.class));
                finish();

                Pays temp = null;
                int n = listePays.size();
                for (int i = 0; i < n; i++) {
                    for (int j = 1; j < (n - i); j++) {
                        if (listePays.get(j - 1).getPib() < listePays.get(j).getPib()) {
                            temp = listePays.get(j - 1);
                            listePays.set(j - 1, listePays.get(j));
                            listePays.set(j, temp);
                        }
                    }
                }

            }
        });

        btnPopulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modele.modifierTableauTri();
                Modele.setTabTypeDeTri(2);
                Toast.makeText(ChoixTri.this, btnPopulation.getText(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChoixTri.this, ListePays.class));
                finish();

                for (Pays pays : listePays) {
                    System.out.println(pays.getNomFR() + "   " + pays.getPopulation());
                }

                Pays temp = null;
                int n = listePays.size();
                for (int i = 0; i < n; i++) {
                    for (int j = 1; j < (n - i); j++) {
                        if (listePays.get(j - 1).getPopulation() < listePays.get(j).getPopulation()) {
                            temp = listePays.get(j - 1);
                            listePays.set(j - 1, listePays.get(j));
                            listePays.set(j, temp);
                        }
                    }
                }
            }
        });

        btnSuperficie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modele.modifierTableauTri();
                Modele.setTabTypeDeTri(4);
                Toast.makeText(ChoixTri.this, btnSuperficie.getText(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChoixTri.this, ListePays.class));
                finish();

                Pays temp = null;
                int n = listePays.size();
                for (int i = 0; i < n; i++) {
                    for (int j = 1; j < (n - i); j++) {
                        if (listePays.get(j - 1).getSuperficie() < listePays.get(j).getSuperficie()) {
                            temp = listePays.get(j - 1);
                            listePays.set(j - 1, listePays.get(j));
                            listePays.set(j, temp);
                        }
                    }
                }
            }
        });

        btnNom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modele.modifierTableauTri();
                Modele.setTabTypeDeTri(0);
                Toast.makeText(ChoixTri.this, btnNom.getText(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChoixTri.this, ListePays.class));
                finish();

                Pays temp = null;
                int n = listePays.size();
                if (Modele.getLangueFrancais()) {
                    for (int i = 0; i < n; i++) {
                        for (int j = 1; j < (n - i); j++) {
                            if (listePays.get(j - 1).getNomFR().compareTo(listePays.get(j).getNomFR()) > 0) {
                                temp = listePays.get(j - 1);
                                listePays.set(j - 1, listePays.get(j));
                                listePays.set(j, temp);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < n; i++) {
                        for (int j = 1; j < (n - i); j++) {
                            if (listePays.get(j - 1).getNomDE().compareTo(listePays.get(j).getNomDE()) > 0) {
                                temp = listePays.get(j - 1);
                                listePays.set(j - 1, listePays.get(j));
                                listePays.set(j, temp);
                            }
                        }
                    }
                }
            }
        });


        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoixTri.this, ListePays.class));
                finish();
            }
        });

        imgCoeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Modele.isUtiliserListeFavoris()) {
                    Modele.setUtiliserListeFavoris(false);
                    imgCoeur.setImageResource(R.drawable.coeurbleuvide);
                    //Modele.setTabTypeDeTri(0);
                    listePays = Modele.getListePays();
                } else if (!Modele.isUtiliserListeFavoris() && modele.getListePaysFavori().size() != 0) {
                    Modele.setUtiliserListeFavoris(true);
                    imgCoeur.setImageResource(R.drawable.coeurbleuplein);
                    //Modele.setTabTypeDeTri(0);
                    listePays = modele.getListePaysFavori();
                } else if (modele.getListePaysFavori().size() == 0) {
                    lancerMessageConfirmation();
                }
            }
        });

    }

    /**
     * Permet de notifier l'utilisateur qu'il n'a aucun pays favori
     */
    private void lancerMessageConfirmation() {
        DialogFragment listeVide = new ListeVide();
        listeVide.show(getSupportFragmentManager(), "MyDialogFragmentTag");
    }


    /**
     * Permet de bloquer le bouton dont le tri est déjà actif dans la classe listePays
     */
    private void bloquerTriActuel() {
        for (int i = 0; i < Modele.getTabTypeDeTri().length; i++) {
            if (Modele.getTabTypeDeTri()[i]) {
                switch (i) {
                    case 0:
                        btnNom.setEnabled(false);
                        break;
                    case 1:
                        btnCapitale.setEnabled(false);
                        break;

                    case 2:
                        btnPopulation.setEnabled(false);
                        break;
                    case 3:
                        btnPib.setEnabled(false);
                        break;
                    case 4:
                        btnSuperficie.setEnabled(false);
                        break;

                }
            }
        }
    }
}
