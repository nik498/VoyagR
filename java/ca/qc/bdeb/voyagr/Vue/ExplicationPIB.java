package ca.qc.bdeb.voyagr.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.qc.bdeb.voyagr.R;

/**
 * Clase qui gère l'écran qui explique le calcul du PIB
 */
public class ExplicationPIB extends AppCompatActivity {

    private TextView tvPIB;
    private Button btnRetour;

    /**
     * Lance l'activité explication PIB
     * @param savedInstanceState état du layout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explication_pib);
        creerComposantes();
        creerActionsBoutons();
    }

    /**
     * Cette méthode permet de créer et d'afficher les composantes
     */
    private void creerComposantes(){
        tvPIB = (TextView) findViewById(R.id.tvExplicationPIB);

        btnRetour = (Button) findViewById(R.id.btnRetourPIB);
    }

    /**
     * Cette méthode crée les listeners sur les composantes
     */
    private void creerActionsBoutons(){
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExplicationPIB.this, Aide.class));
                finish();
            }
        });
    }
}
