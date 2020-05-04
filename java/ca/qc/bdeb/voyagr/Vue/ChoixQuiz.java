package ca.qc.bdeb.voyagr.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import ca.qc.bdeb.voyagr.Modele.Modele;
import ca.qc.bdeb.voyagr.R;

/**
 * Cette calsse représente le menu où l'utilisateur choisit quel type de quiz il veut faire et combien de questions
 */
public class ChoixQuiz extends AppCompatActivity {

    private Button btnCapitale, btnPib, btnRetour, btnCommencer;
    private Switch modeInfini;
    private ImageView imgInfo;
    private EditText nbQuestion;
    private TextView tvNombreQuestion, typeQuiz;
    private boolean quizCapitale = false, quizPib = false, switchCoche = false;
    private String prochainType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_quiz);

        quizCapitale = false;
        quizPib = false;
        switchCoche = false;
        prochainType = getResources().getString(R.string.typeQuiz);

        creerComposante();
        creerActionComposantes();
        bloquerQuiz();
    }

    /**
     * Permet de créer les composantes dans la classe ChoixQuiz
     */
    private void creerComposante() {

        btnCapitale = (Button) findViewById(R.id.btnQuizCapitale);
        btnPib = (Button) findViewById(R.id.btnQuizPib);
        btnRetour = (Button) findViewById(R.id.btnRetourAuParametre);
        btnCommencer = (Button) findViewById(R.id.btnCommencerQuiz);
        modeInfini = (Switch) findViewById(R.id.switchInfini);
        nbQuestion = (EditText) findViewById(R.id.txtNbQuestion);
        tvNombreQuestion = (TextView) findViewById(R.id.tvNombreQuestion);
        typeQuiz = (TextView) findViewById(R.id.tvTypeQuiz);
        imgInfo =(ImageView) findViewById(R.id.imgPlusInfo);



    }

    /**
     * Permet de créer les événements sur les composantes à l'écran
     */
    private void creerActionComposantes() {
        btnCapitale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizCapitale = true;
                quizPib = false;
                btnCommencer.setEnabled(true);
                Modele.setQuestionEnCoursCapitale(true);
                Toast.makeText(ChoixQuiz.this, btnCapitale.getText(), Toast.LENGTH_SHORT).show();
                typeQuiz.setText(prochainType +" " +btnCapitale.getText());
            }
        });

        btnPib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizCapitale = false;
                quizPib = true;
                Modele.setQuestionEnCoursCapitale(false);
                btnCommencer.setEnabled(true);
                Toast.makeText(ChoixQuiz.this, btnPib.getText(), Toast.LENGTH_SHORT).show();
                typeQuiz.setText(prochainType +" " +btnPib.getText());
            }
        });


        btnCommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!switchCoche) {
                    if (nbQuestion.getText().length() != 0) {
                        int nombreQuestion = Integer.parseInt(nbQuestion.getText().toString());

                        if (nombreQuestion >= 190) {
                            Toast.makeText(ChoixQuiz.this, R.string.maximumQuestion, Toast.LENGTH_SHORT).show();
                        } else if (nombreQuestion < 5) {
                            Toast.makeText(ChoixQuiz.this, R.string.minimumQuestion, Toast.LENGTH_SHORT).show();
                        } else {
                            Modele.setNbQuestionMax(nombreQuestion);
                            Modele.setQuizCapitale(quizCapitale);
                            Modele.setQuizPib(quizPib);
                            startActivity(new Intent(ChoixQuiz.this, Quiz.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(ChoixQuiz.this, R.string.nombreQuestion, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Modele.setNbQuestionMax(999);
                    startActivity(new Intent(ChoixQuiz.this, Quiz.class));
                    finish();
                }

            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ChoixQuiz.this, CarteDuMonde.class));
            }
        });

        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment modeInfini = new InfoModeInfini();
                modeInfini.show(getSupportFragmentManager(), "MyDialogFragmentTag");
            }
        });

        gererSwitch();
    }

    /**
     * Permet de bloquer le quiz dépendant des variable associées aux boutons cliqués
     */
    private void bloquerQuiz(){
        if (!switchCoche && !quizPib &&!quizCapitale){
            btnCommencer.setEnabled(false);
        }
    }

    /**
     * Permet de changer les composants de l'activité si l'utiliseur coche/décoche la switch
     */
    private void gererSwitch(){
        modeInfini.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    switchCoche=true;
                    quizCapitale = true;
                    quizPib = true;
                    btnCommencer.setEnabled(true);
                    nbQuestion.setEnabled(false);
                    nbQuestion.setVisibility(View.GONE);
                    tvNombreQuestion.setEnabled(false);
                    tvNombreQuestion.setVisibility(View.GONE);
                    btnCapitale.setEnabled(false);
                    btnPib.setEnabled(false);
                    typeQuiz.setText(R.string.aleatoire);
                } else {
                    switchCoche=false;
                    quizCapitale = false;
                    quizPib = false;
                    bloquerQuiz();

                    nbQuestion.setEnabled(true);
                    nbQuestion.setVisibility(View.VISIBLE);
                    tvNombreQuestion.setEnabled(true);
                    tvNombreQuestion.setVisibility(View.VISIBLE);
                    btnCapitale.setEnabled(true);
                    btnPib.setEnabled(true);
                    typeQuiz.setText(R.string.typeDeQuiz);
                }
            }
        });

    }
}


