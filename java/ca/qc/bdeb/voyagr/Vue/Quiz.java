package ca.qc.bdeb.voyagr.Vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ca.qc.bdeb.voyagr.Modele.Modele;
import ca.qc.bdeb.voyagr.R;

/**
 * Cette classe permet de gérer le quiz et les fonctions reliés
 */
public class Quiz extends AppCompatActivity  {

    private Button btnChoix1, btnChoix2, btnChoix3, btnChoix4, btnRetour, btnSuivant;
    private TextView tvScore, tvQuestion;
    private Modele modele = new Modele();
    private boolean  quizLive = true, deuxiemeVerifaction = false;
    private int indiceBouton = -1, pourcentageInt;
    private boolean couleurBouton1 = false, couleurBouton2 = false, couleurBouton3 = false, couleurBouton4 = false;
    private String questionCapitale, questionPib, score;
    private double nbQuestionReponduQuiz = 0.0, scoreQuiz = 0.0;

    /**
     * Permet de lancer l'activité et le layout associé à la classe qui gère le quiz
     * @param savedInstanceState état de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        creerComposantes();
        creerActionBoutons();

        //BbtnSuivant est bloquer afin d'obliger l'utilisateur à répondre à la question
        btnSuivant.setEnabled(false);
        questionCapitale = tvQuestion.getText().toString();
        questionPib=getString(R.string.questionOfficielPib);
        score = getResources().getString(R.string.score) + " ";

        //lance un type de question spécifique selon le choix de l'utilisateur
        if (Modele.isQuizCapitale() && !Modele.isQuizPib()) {
            modele.questionCapitale();

        } else if ((!Modele.isQuizCapitale() && Modele.isQuizPib())) {
            tvQuestion.setText(questionPib + modele.getBonneQuestion() + " G\u0024 USD?");
            modele.questionPib();
        } else {
            if(modele.modeQuestionInfini()==0){
                tvQuestion.setText(questionCapitale + " " + modele.getBonneQuestion() + "?");
            } else{
                tvQuestion.setText(questionPib + modele.getBonneQuestion() + " G\u0024 USD?");
            }
        }
        afficherUneNouvelleQuestion();
    }

    /**
     * Permet de lier les composantes graphiques de la classe à celle du layout du quiz.
     */
    private void creerComposantes() {
        btnChoix1 = (Button) findViewById(R.id.button1);
        btnChoix2 = (Button) findViewById(R.id.button2);
        btnChoix3 = (Button) findViewById(R.id.button3);
        btnChoix4 = (Button) findViewById(R.id.button4);
        btnRetour = (Button) findViewById(R.id.btnRetourCarte);
        btnSuivant = (Button) findViewById(R.id.btnSuivant);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
    }

    /**
     * Permet de créer les actions sur les boutons dont les boutons qui sont les choix de réponse possible
     */
    private void creerActionBoutons() {
        btnChoix1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationBonBouton();
                if (indiceBouton != 0) {
                    btnChoix1.setBackgroundColor(getResources().getColor(R.color.colorRouge));
                    couleurBouton1 = true;

                } else {
                    modele.setScoreJoueur(modele.getScoreJoueur() + 1);
                    scoreQuiz = scoreQuiz + 1;
                }
                modifierScore();
            }
        });

        btnChoix2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationBonBouton();
                if (indiceBouton != 1) {
                    btnChoix2.setBackgroundColor(getResources().getColor(R.color.colorRouge));
                    couleurBouton2 = true;
                } else {
                    modele.setScoreJoueur(modele.getScoreJoueur() + 1);
                    scoreQuiz = scoreQuiz + 1;
                }
                modifierScore();
            }
        });
        btnChoix3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationBonBouton();
                if (indiceBouton != 2) {
                    btnChoix3.setBackgroundColor(getResources().getColor(R.color.colorRouge));
                    couleurBouton3 = true;
                } else {
                    modele.setScoreJoueur(modele.getScoreJoueur() + 1);
                    scoreQuiz = scoreQuiz + 1;
                }
                modifierScore();
            }
        });
        btnChoix4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationBonBouton();
                if (indiceBouton != 3) {
                    btnChoix4.setBackgroundColor(getResources().getColor(R.color.colorRouge));
                    couleurBouton4 = true;
                } else {
                    modele.setScoreJoueur(modele.getScoreJoueur() + 1);
                    scoreQuiz = scoreQuiz + 1;
                }
                modifierScore();
            }
        });

        btnSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gererCouleurBoutons();

                if (quizLive) {
                    btnSuivant.setEnabled(false);
                } else {
                    btnSuivant.setEnabled(true);
                }

                if (deuxiemeVerifaction) {
                    finDuQuiz();
                }
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerMessageConfirmation();
            }
        });
    }

    /**
     * Permet de trouver quel est le bouton et de le mettre en vert
     */
    private void verificationBonBouton() {

        btnSuivant.setEnabled(true);
        for (int j = 0; j < modele.getListeChoixReponseOfficiel().size(); j++) {
            if (modele.getListeChoixReponseOfficiel().get(j).equals(modele.getBonneReponse())) {
                indiceBouton = j;
            }
        }

        switch (indiceBouton) {
            case 0:
                btnChoix1.setBackgroundColor(getResources().getColor(R.color.colorVert));
                couleurBouton1 = true;
                break;

            case 1:
                btnChoix2.setBackgroundColor(getResources().getColor(R.color.colorVert));
                couleurBouton2 = true;
                break;

            case 2:
                btnChoix3.setBackgroundColor(getResources().getColor(R.color.colorVert));
                couleurBouton3 = true;
                break;

            case 3:
                btnChoix4.setBackgroundColor(getResources().getColor(R.color.colorVert));
                couleurBouton4 = true;
                break;
        }
    }

    /**
     * Permet de modifier le score du joueur et d'afficher le score dans un text field
     */
    private void modifierScore() {

        Modele.setNbQuestionRepondu(Modele.getNbQuestionRepondu() + 1);
        nbQuestionReponduQuiz = nbQuestionReponduQuiz + 1;
        double pourcentage = (scoreQuiz / nbQuestionReponduQuiz) * 100;
        pourcentageInt = (int) Math.round(pourcentage);
        tvScore.setText(score + pourcentageInt + "%");


    }

    /**
     * Permet à la classe de prende les information de la question (bonne réponse, choix de réponse
     * et question) dans le modèle afin de les afficher.
     */
    private void afficherUneNouvelleQuestion() {

        btnChoix1.setText(modele.getListeChoixReponseOfficiel().get(0));
        btnChoix2.setText(modele.getListeChoixReponseOfficiel().get(1));
        btnChoix3.setText(modele.getListeChoixReponseOfficiel().get(2));
        btnChoix4.setText(modele.getListeChoixReponseOfficiel().get(3));

        if (Modele.isQuestionEnCoursCapitale()) {
            tvQuestion.setText(questionCapitale + " " + modele.getBonneQuestion() + "?");

        } else {
            tvQuestion.setText(questionPib + " " + modele.getBonneQuestion() + " G\u0024 USD?");

        }
    }

    /**
     *Permet de changer la couleur des boutons au bleu normal lorsqu'un utilisateur clique sur ceux-ci.
     * De plus, la méthode permet de relancer une question si l'utilisateur doit encore
     * répondre à des questions avant de terminer le quiz
     */
    private void gererCouleurBoutons() {

        if (couleurBouton1) {
            btnChoix1.setBackgroundColor(getResources().getColor(R.color.colorMarine));
            couleurBouton1 = false;
        }
        if (couleurBouton2) {
            btnChoix2.setBackgroundColor(getResources().getColor(R.color.colorMarine));
            couleurBouton2 = false;
        }
        if (couleurBouton3) {
            btnChoix3.setBackgroundColor(getResources().getColor(R.color.colorMarine));
            couleurBouton3 = false;
        }
        if (couleurBouton4) {
            btnChoix4.setBackgroundColor(getResources().getColor(R.color.colorMarine));
            couleurBouton4 = false;
        }

        if (!quizLive) {
            deuxiemeVerifaction = true;
        }

        if (nbQuestionReponduQuiz < Modele.getNbQuestionMax()) {
            if (Modele.isQuizCapitale() && !Modele.isQuizPib()) {
                modele.questionCapitale();
                afficherUneNouvelleQuestion();


            } else if ((!Modele.isQuizCapitale() && Modele.isQuizPib())) {
                modele.questionPib();
                afficherUneNouvelleQuestion();
            } else {
                modele.modeQuestionInfini();
                afficherUneNouvelleQuestion();
            }
        } else {
            quizLive = false;
            desactiverBouton();
        }


    }

    /**
     * Permet de désactivé tout les boutons inutile lorsque l'utilisateur à fini le quiz
     */
    private void desactiverBouton() {
        btnChoix1.setEnabled(false);
        btnChoix2.setEnabled(false);
        btnChoix3.setEnabled(false);
        btnChoix4.setEnabled(false);
        btnRetour.setEnabled(false);
        btnSuivant.setEnabled(true);

        String quizTerminer = getString(R.string.quizTerminer);
        tvQuestion.setText(quizTerminer);


    }

    /**
     * Permet que l'utilisateur confirme qu'il veut arrêter le quiz lorsqu'il n'a pas répondu à tout les questions
     */
    private void lancerMessageConfirmation() {
        AlertDialog.Builder alerte = new AlertDialog.Builder(this);
        alerte.setTitle(getResources().getString(R.string.quizNonTermine));
        alerte.setMessage(getResources().getString(R.string.perdreProgression));
        alerte.setCancelable(false);
        String boutonPositif = getString(R.string.ok);
        alerte.setPositiveButton(boutonPositif, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finDuQuiz();
            }
        });
        String boutonNegatif = getString(R.string.annuler);
        alerte.setNegativeButton(boutonNegatif, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alerte.create();
        alertDialog.show();
    }

    /**
     * Permet de gérer la fin du quiz lorsque l'utilisateur veut arrêter le quiz ou qu'il ne reste plus de
     * question à répondre
     */
    private void finDuQuiz() {
        Modele.setQuizPib(false);
        Modele.setQuizCapitale(false);

        startActivity(new Intent(Quiz.this, Menu.class));
        finish();
    }
}
