package ca.qc.bdeb.voyagr.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

import ca.qc.bdeb.voyagr.R;

/**
 * Cette calsse représente le menu d'aide de l'appli
 */
public class Aide extends AppCompatActivity  {

    private TextView tvMerci, tvAide, tvAideDonnees;
    private Button btnRetour, btnExplicationPIB;

    /**
     * Permet de gérer l'activity Aide et de la relier à son layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide);
        creerComposantes();
        creerActionsBoutons();
    }


    /**
     * Permet de creer les composantes dans la classe Aide
     */
    private void creerComposantes(){
        tvMerci = (TextView) findViewById(R.id.tvMerci);
        tvAideDonnees = (TextView) findViewById(R.id.tvAideDonnees);
        tvAide = (TextView) findViewById(R.id.tvAide);
        btnRetour = (Button) findViewById(R.id.btnRetourAide);
        btnExplicationPIB = (Button) findViewById(R.id.btnExplicationPIB);
    }

    /**
     * Permet de creer les actions sur les composantes dans la classe Aide
     */
    private void creerActionsBoutons(){
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Aide.this, Menu.class));
                finish();
            }
        });
        btnExplicationPIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Aide.this, ExplicationPIB.class));
                finish();
            }
        });
    }
}
