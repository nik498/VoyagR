package ca.qc.bdeb.voyagr.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ca.qc.bdeb.voyagr.Modele.Modele;
import ca.qc.bdeb.voyagr.Modele.Pays;
import ca.qc.bdeb.voyagr.R;

/**
 * Classe qui permet de gerer la liste de pays
 */
public class ListePays extends AppCompatActivity {


    private TextView tvListePays;
    private RecyclerView vuePays;
    private RecyclerView.Adapter adapter;
    private Button btnRetour, btnTrier;
    private ArrayList<Pays> listePays;

    /**
     * Permet de lancer le layout de la liste de pays
     * @param savedInstanceState état du layout/ de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_pays);
        creerComposantes();
        creerActionsBoutons();

        if(!Modele.isUtiliserListeFavoris()){
            listePays = Modele.getListePays();
        } else {
            listePays = Modele.getListePaysFavori();
        }
        vuePays = (RecyclerView) findViewById(R.id.viewPaysRecycler);
        vuePays.setItemAnimator(new DefaultItemAnimator());
        adapter = new PaysAdapter(listePays, this);
        vuePays.setAdapter(adapter);
        vuePays.setLayoutManager(new LinearLayoutManager(this));

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

    /**
     * Permet de creer les composantes dans la classe liste Pays
     */
    private void creerComposantes() {
        tvListePays = (TextView) findViewById(R.id.tvListePays);
        btnRetour = (Button) findViewById(R.id.btnRetourListe);
        btnTrier = (Button) findViewById(R.id.btnTrierListe);
    }

    /**
     * Permet de creer les actions sur les composantes de l'activité liste pays
     */
    private void creerActionsBoutons() {
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListePays.this, Menu.class));
                finish();
            }
        });
        btnTrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListePays.this, ChoixTri.class));
                finish();
            }
        });
    }
}
