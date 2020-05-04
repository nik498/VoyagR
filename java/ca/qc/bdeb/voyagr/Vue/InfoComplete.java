package ca.qc.bdeb.voyagr.Vue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.qc.bdeb.voyagr.Modele.Modele;
import ca.qc.bdeb.voyagr.R;

/**
 * Cette classe est une template pour les informations détaillées en science, toursime, culture et économie des pays
 */
public class InfoComplete extends AppCompatActivity {

    private TextView paysActuel, texteInfo;
    private Button btnRetour;
    private int indice;
    private Modele modele;
    private String nomFichier;

    /**
     * Permet de lancer l'activité avec l'information détaillé d'un pays
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_complete);
        creerComposantes();
        creerActionsBoutons();
        modifierInfoPays();
    }

    /**
     * Cette méthode crée les composantes de la classe
     */
    private void creerComposantes(){
        btnRetour = (Button) findViewById(R.id.btnRetourInfoComplete);
        paysActuel = (TextView) findViewById(R.id.tvInfoCompleteTitre);
        texteInfo = (TextView) findViewById(R.id.tvInfoComplete);
    }

    /**
     * Cette méthode crée les listeners sur les ocmposantes de la classe
     */
    private void creerActionsBoutons(){
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modele.setTypeInfoPaysComplete(0);
                finish();
            }
        });
    }

    /**
     * Cette méthode affiche le bon texte selon le pays qui est cliqué et le type d'information demandé
     */
    private void modifierInfoPays(){
        indice = modele.getIndice(Modele.getPaysClique());
        if (Modele.getLangueFrancais()) {
            paysActuel.setText(Modele.getListePays().get(indice).getNomFR());
        } else {
            paysActuel.setText(Modele.getListePays().get(indice).getNomDE());
        }

        nomFichier = Modele.getListePays().get(indice).getNomEN();
        Modele.lireFichierPays(this, nomFichier);

        switch(Modele.getTypeInfoPaysComplete()){
            case 1:
                if(Modele.getLangueFrancais()){
                    texteInfo.setText(Modele.getListeInfoPrecisesPays().get(3));
                } else {
                    texteInfo.setText(Modele.getListeInfoPrecisesPays().get(7));
                }
                break;
            case 2:
                if(Modele.getLangueFrancais()){
                    texteInfo.setText(Modele.getListeInfoPrecisesPays().get(0));
                } else {
                    texteInfo.setText(Modele.getListeInfoPrecisesPays().get(4));
                }
                break;
            case 3:
                if(Modele.getLangueFrancais()){
                    texteInfo.setText(Modele.getListeInfoPrecisesPays().get(2));
                } else {
                    texteInfo.setText(Modele.getListeInfoPrecisesPays().get(6));
                }
                break;
            case 4:
                if(Modele.getLangueFrancais()){
                    texteInfo.setText(Modele.getListeInfoPrecisesPays().get(1));
                } else {
                    texteInfo.setText(Modele.getListeInfoPrecisesPays().get(5));
                }
                break;
            default:
                break;
        }
    }
}
