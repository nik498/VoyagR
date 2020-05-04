package ca.qc.bdeb.voyagr.Modele;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.Stack;

import ca.qc.bdeb.voyagr.R;

/**
 * Le Modèle permet de gérer les objets modèle et de gérer leurs interactions afin de pouvoir accomplir des tâches importantes
 * dans le fonctionnement de l'application, comme le quiz et les listes
 */
public class Modele {

    //TODO:espacer les nombres

    private static Random r = new Random();

    //String
    private static String bonneReponse, bonneQuestion;
    private static String paysClique = null;

    //tableau boolean
    private static boolean[] tabTypeDeTri = {true, false, false, false, false};
    private static boolean[] tabTypeDeCarte = {true, false, false};

    //int
    private static int positionBonneQuestionCapitale = -1, positionBonneQuestionPib = -1;
    private static int nbQuestionRepondu = 0;
    private static int scoreJoueur = 0;
    private static int nbQuestionMax = 0;
    private static int typeInfoPaysComplete = 0;
    private static int typeCarte = 1;

    //boolean
    private static boolean utiliserListeFavoris = false;
    private static boolean infoSupExiste = false;
    private static boolean langueFrancais = true;
    private static boolean quizCapitale = false, quizPib = false, questionEnCoursCapitale = false;
    private static boolean vientDeLaCarte = true;

    //listes
    private static ArrayList<Pays> listePays = new ArrayList<>();
    private static ArrayList<Capitale> listeCapitale = new ArrayList<>();
    private static ArrayList<Pib> listePib = new ArrayList<>();
    private static ArrayList<String> listeFavoriString = new ArrayList<>();
    private static ArrayList<Pays> listePaysFavori = new ArrayList<>();
    private static ArrayList<Integer> listeChoixReponseNumero = new ArrayList<>();
    private static ArrayList<Integer> listeChoixReponseTemporaire = new ArrayList<>();
    private static ArrayList<String> listeChoixReponseOfficiel = new ArrayList<>();
    private static ArrayList<String> listeInfoPrecisesPays = new ArrayList<>();


    /**
     * Permet de changer la langue des éléments des listes pour le quiz
     */
    public static void changerLangueListe() {

        if (listeCapitale.size() >= 1) {
            listeCapitale.clear();
            listePib.clear();
        }

        for (int i = 0; i < listePays.size(); i++) {
            Capitale capitale = new Capitale();
            Pib pib = new Pib();
            pib.setPib(listePays.get(i).getPib());
            if (langueFrancais) {
                capitale.setCapitale(listePays.get(i).getCapitaleFR());
                capitale.setNomPays(listePays.get(i).getNomFR());
                pib.setNomPays(listePays.get(i).getNomFR());
            } else {
                capitale.setCapitale(listePays.get(i).getCapitaleDE());
                capitale.setNomPays(listePays.get(i).getNomDE());
                pib.setNomPays(listePays.get(i).getNomDE());
            }
            listeCapitale.add(capitale);
            listePib.add(pib);
        }
    }


    /**
     * Permet de lire le fichier .txt qui contient toutes les informations de base sur les 197 pays du monde.
     * @param myContext Le contexte de l'activity qui lance cette méthode.
     */
    public static void lireFichier(Context myContext) {
        BufferedReader lecture = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        String ligne;
        try {
            inputStream = myContext.getResources().openRawResource(R.raw.voyagr);
            inputStreamReader = new InputStreamReader(inputStream);
            lecture = new BufferedReader(inputStreamReader);

            while ((ligne = lecture.readLine()) != null) {

                String tabInfos[] = ligne.split(",");
                Pays pays = new Pays();

                pays.setNomFR(tabInfos[0]);
                pays.setPopulation(Integer.parseInt(tabInfos[1]));
                pays.setSuperficie(Double.parseDouble(tabInfos[2]));
                pays.setCapitaleFR(tabInfos[3]);
                pays.setNomDE(tabInfos[4]);
                pays.setCapitaleDE(tabInfos[5]);
                pays.setPib(Double.parseDouble(tabInfos[6]));
                pays.setNomEN(tabInfos[7]);
                pays.setISO2(tabInfos[8]);

                listePays.add(pays);

            }


            lecture.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé");
        } catch (IOException ex) {
            System.out.println("Ioexeption");
        }
        changerLangueListe();
    }


    /**
     * Permet d'obtenir la position des choix de réponse pour des capitales
     */
    private static void getChoixDeReponseCapitale() {
        listeChoixReponseNumero.add(r.nextInt(listeCapitale.size()));
        listeChoixReponseNumero.add(r.nextInt(listeCapitale.size()));
        listeChoixReponseNumero.add(r.nextInt(listeCapitale.size()));


        if (listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(0)) {
            do {

                listeChoixReponseNumero.set(1, r.nextInt(listeCapitale.size()));
            }
            while (listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(0));
        }

        if (listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(2) || listeChoixReponseNumero.get(0) == listeChoixReponseNumero.get(2)) {
            do {

                listeChoixReponseNumero.set(2, r.nextInt(listeCapitale.size()));
            }
            while ((listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(2)) || listeChoixReponseNumero.get(2) == listeChoixReponseNumero.get(0));
        }

        if (listeChoixReponseNumero.get(2) == listeChoixReponseNumero.get(3) || listeChoixReponseNumero.get(0) == listeChoixReponseNumero.get(3) || listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(3)) {
            do {
                listeChoixReponseNumero.set(3, r.nextInt(listeCapitale.size()));
            }
            while ((listeChoixReponseNumero.get(2) == listeChoixReponseNumero.get(3)) || listeChoixReponseNumero.get(3) == listeChoixReponseNumero.get(0) || listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(3));
        }
    }

    /**
     * Permet de générer les questions sur les capitales
     */
    public static void questionCapitale() {

        if (listeChoixReponseOfficiel.size() == 4) {
            listeChoixReponseNumero.clear();
            listeChoixReponseTemporaire.clear();
            listeChoixReponseOfficiel.clear();
            if (positionBonneQuestionCapitale != -1) {
                listeCapitale.remove(positionBonneQuestionCapitale);
            }
        }

        positionBonneQuestionCapitale = r.nextInt(listeCapitale.size());
        listeChoixReponseNumero.add(positionBonneQuestionCapitale);
        bonneQuestion = listeCapitale.get(positionBonneQuestionCapitale).getCapitale();
        bonneReponse = listeCapitale.get(positionBonneQuestionCapitale).getNomPays();

        getChoixDeReponseCapitale(); //trouve les mauvais choix de réponse
        getRandomChoixReponse();


        for (int i = 0; i < listeChoixReponseTemporaire.size(); i++) {
            listeChoixReponseOfficiel.add(listeCapitale.get(listeChoixReponseTemporaire.get(i)).getNomPays());
        }
    }

    /**
     * Permet d'obtenir la position des choix de réponse pour des question sur le pib
     */
    private static void getChoixDeReponsePib() {
        listeChoixReponseNumero.add(r.nextInt(listePib.size()));
        listeChoixReponseNumero.add(r.nextInt(listePib.size()));
        listeChoixReponseNumero.add(r.nextInt(listePib.size()));


        if (listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(0)) {
            do {

                listeChoixReponseNumero.set(1, r.nextInt(listePib.size()));
            }
            while (listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(0));
        }

        if (listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(2) || listeChoixReponseNumero.get(0) == listeChoixReponseNumero.get(2)) {
            do {

                listeChoixReponseNumero.set(2, r.nextInt(listePib.size()));
            }
            while ((listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(2)) || listeChoixReponseNumero.get(2) == listeChoixReponseNumero.get(0));
        }

        if (listeChoixReponseNumero.get(2) == listeChoixReponseNumero.get(3) || listeChoixReponseNumero.get(0) == listeChoixReponseNumero.get(3) || listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(3)) {
            do {
                listeChoixReponseNumero.set(3, r.nextInt(listePib.size()));
            }
            while ((listeChoixReponseNumero.get(2) == listeChoixReponseNumero.get(3)) || listeChoixReponseNumero.get(3) == listeChoixReponseNumero.get(0) || listeChoixReponseNumero.get(1) == listeChoixReponseNumero.get(3));
        }
    }

    /**
     * Permet de générer les questions sur les PIB
     */
    public static void questionPib() {

        if (listeChoixReponseOfficiel.size() == 4) {
            listeChoixReponseNumero.clear();
            listeChoixReponseTemporaire.clear();
            listeChoixReponseOfficiel.clear();
            if (positionBonneQuestionPib != -1) {
                listePib.remove(positionBonneQuestionPib);
            }
        }

        positionBonneQuestionPib = r.nextInt(listePib.size());
        listeChoixReponseNumero.add(positionBonneQuestionPib);
        bonneQuestion = espacerLesNombreDouble(listePib.get(positionBonneQuestionPib).getPib());
        bonneReponse = listePib.get(positionBonneQuestionPib).getNomPays();

        getChoixDeReponsePib(); //trouve les mauvais choix de réponse
        getRandomChoixReponse();


        for (int i = 0; i < listeChoixReponseTemporaire.size(); i++) {
            listeChoixReponseOfficiel.add(listePib.get(listeChoixReponseTemporaire.get(i)).getNomPays());
        }
    }

    /**
     * Permet de modifier l'ordre des choix de réponse
     */
    private static void getRandomChoixReponse() {

        while (listeChoixReponseTemporaire.size() < 4) {
            //position à enlever
            int positionEnlever = r.nextInt(listeChoixReponseNumero.size());
            listeChoixReponseTemporaire.add(listeChoixReponseNumero.get(positionEnlever));
            listeChoixReponseNumero.remove(positionEnlever);
        }
    }




    /**
     * Permet de lire les fichier d'informations supplémentaire
     *
     * @param myContext  contexte de la classe qui a lancé la méthode
     * @param nomFichier nom du pays en anglais qui est aussi le nom du fichier en français et en allemand
     */
    public static void lireFichierPays(Context myContext, String nomFichier) {

        listeInfoPrecisesPays.clear();

        BufferedReader lecture = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;

        String ligne;

        try {
            inputStream = myContext.getAssets().open(nomFichier + ".txt");
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            lecture = new BufferedReader(inputStreamReader);

            while ((ligne = lecture.readLine()) != null) {
                listeInfoPrecisesPays.add(ligne);
            }

            infoSupExiste = true;

            lecture.close();
            inputStreamReader.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé");
            infoSupExiste = false;

        } catch (IOException ex) {
            System.out.println("Ioexeption");
        }

    }

    /**
     * Cette méthode retourne l'indice dans la liste des pays du pays entré en paramètre
     * @param nomPays C'est le nom du pays duquel on cherche la position dans la liste des pays
     * @return
     */
    public static int getIndice(String nomPays) {

        int indice = -1; //-1 pour crash s'il c'est pas le même nom

        for (int i = 0; i < listePays.size(); i++) {
            if (listePays.get(i).getNomEN().equals(nomPays)) {
                indice = i;
            }
        }

        return indice;
    }

    public static ArrayList<String> getListeInfoPrecisesPays() {
        return listeInfoPrecisesPays;
    }

    public static Boolean getLangueFrancais() {
        return langueFrancais;
    }

    public static void setLangueFrancais(Boolean langueFrancais) {
        Modele.langueFrancais = langueFrancais;

    }

    public static boolean[] getTabTypeDeCarte() {
        return tabTypeDeCarte;
    }

    public static void setTabTypeDeCarte(int i) {
        tabTypeDeCarte[i] = true;
    }

    public static String getPaysClique() {
        return paysClique;
    }

    public static void setPaysClique(String paysClique) {
        Modele.paysClique = paysClique;
    }

    public static boolean isInfoSupExiste() {
        return infoSupExiste;
    }

    public static int getNbQuestionRepondu() {
        return nbQuestionRepondu;
    }

    public static void setNbQuestionRepondu(int nbQuestionRepondu) {
        Modele.nbQuestionRepondu = nbQuestionRepondu;
    }

    public static ArrayList<Pays> getListePaysFavori() {
        return listePaysFavori;
    }

    public static int getScoreJoueur() {
        return scoreJoueur;
    }

    public static void setScoreJoueur(int scoreJoueur) {
        Modele.scoreJoueur = scoreJoueur;
    }

    public static int getNbQuestionMax() {
        return nbQuestionMax;
    }

    public static void setNbQuestionMax(int nbQuestionMax) {
        Modele.nbQuestionMax = nbQuestionMax;
    }

    public static boolean[] getTabTypeDeTri() {
        return tabTypeDeTri;
    }

    public static void setTabTypeDeTri(int i) {
        tabTypeDeTri[i] = true;
    }

    public static boolean isQuizCapitale() {
        return quizCapitale;
    }

    public static void setQuizCapitale(boolean quizCapitale) {
        Modele.quizCapitale = quizCapitale;
    }

    public static boolean isQuizPib() {
        return quizPib;
    }

    public static void setQuizPib(boolean quizPib) {
        Modele.quizPib = quizPib;
    }

    public static int getTypeInfoPaysComplete() {
        return typeInfoPaysComplete;
    }

    public static void setTypeInfoPaysComplete(int typeInfoPaysComplete) {
        Modele.typeInfoPaysComplete = typeInfoPaysComplete;
    }

    public static boolean isUtiliserListeFavoris() {
        return utiliserListeFavoris;
    }

    public static void setUtiliserListeFavoris(boolean utiliserListeFavoris) {
        Modele.utiliserListeFavoris = utiliserListeFavoris;
    }

    public static boolean isQuestionEnCoursCapitale() {
        return questionEnCoursCapitale;
    }

    public static void setQuestionEnCoursCapitale(boolean questionEnCoursCapitale) {
        Modele.questionEnCoursCapitale = questionEnCoursCapitale;
    }

    public int getTypeCarte() {
        return typeCarte;
    }

    public void setTypeCarte(int typeCarte) {
        this.typeCarte = typeCarte;
    }

    public ArrayList<String> getListeChoixReponseOfficiel() {
        return listeChoixReponseOfficiel;
    }

    public String getBonneReponse() {
        return bonneReponse;
    }

    public static String getBonneQuestion() {
        return bonneQuestion;
    }

    public static ArrayList<Pays> getListePays() {
        return listePays;
    }

    public static boolean isVientDeLaCarte() {
        return vientDeLaCarte;
    }

    public static void setVientDeLaCarte(boolean vientDeLaCarte) {
        Modele.vientDeLaCarte = vientDeLaCarte;
    }

    /**
     * Permet de réinitialiser le tableau de boolean à false
     */
    public static void modifierTableauTri() {
        for (int i = 0; i < tabTypeDeTri.length; i++) {
            tabTypeDeTri[i] = false;
        }
    }

    /**
     * Permet de réinitialiser le tableau de boolean du type de carte à false
     */
    public static void modifierTableauCarte() {
        for (int i = 0; i < tabTypeDeCarte.length; i++) {
            tabTypeDeCarte[i] = false;
        }
    }

    /**
     * Cette méthode permet de générer des questions à l'infini pour les deux quiz, et décide aléatoirement du type de question.
     * @return Si la question sera une question de capitale ou de PIB
     */
    public static int modeQuestionInfini() {
        int typeQuestion = r.nextInt(2);

        if (typeQuestion == 0) {
            questionCapitale();
            questionEnCoursCapitale = true;
        } else {
            questionPib();
            questionEnCoursCapitale = false;
        }

        return typeQuestion;
    }

    /**
     * Permet de spliter les nombres séparés par 3 chiffres
     *
     * @param nombreAEspacer nombre à séparer
     * @return nombre avec les espaces
     */
    public static String espacerLesNombreInt(int nombreAEspacer) {
        Stack<Long> pileNombre = new Stack<>();
        String textEspacer = "";
        String nombreEspace = nombreAEspacer + "";
        long modulo = nombreEspace.length() % 3;
        int indice = 0;

        if (nombreEspace.length() > 3) {
            for (long i = 1; i < nombreAEspacer; i = i * 10) {
                long test = ((nombreAEspacer / i) % 10);
                pileNombre.add(test);
            }
            while (pileNombre.size() != 0) {
                if (modulo == indice) {
                    textEspacer = textEspacer + " ";
                    modulo = modulo + 3;
                }
                indice++;
                textEspacer = textEspacer + pileNombre.peek();
                pileNombre.pop();
            }
        } else {
            textEspacer = nombreAEspacer + "";
        }
        return textEspacer;
    }


    /**
     * Permet d'espacer aux trois chiffres les nombres double
     *
     * @param nombreAEspacer nombre double à espacer aux trois chiffres
     */
    public static String espacerLesNombreDouble(double nombreAEspacer) {
        int nombreSansDecimal = (int) Math.floor(nombreAEspacer);
        String nombreText = espacerLesNombreInt(nombreSansDecimal);
        String nombreDouble = nombreAEspacer + "";
        int longeurDouble = nombreDouble.length();
        String nombreInt = nombreSansDecimal + "";
        int longeurInt = nombreInt.length();

        for (int i = (longeurInt); i < longeurDouble; i++) {
            nombreText = nombreText + nombreDouble.charAt(i);
        }
        return nombreText;
    }


    /**
     * Permet de lire les noms des pays dans le fichier des favoris
     *
     * @param context Contexte de l'activity qui lance la méthode
     */
    public static void lirePaysFavori(Context context) {
        listeFavoriString.clear();
        try {
            InputStream inputStream = context.openFileInput("favori.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligneFichierFavori = "";

                while ((ligneFichierFavori = bufferedReader.readLine()) != null) {
                    listeFavoriString.add(ligneFichierFavori);
                }

                inputStream.close();
                transeferLesStringFavoriEnPays();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    /**
     * Permet de trouver le pays associé au nom en anglais et d'ajouter le pays dans une liste
     */
    private static void transeferLesStringFavoriEnPays() {
        if (listeFavoriString.size() != 0) {
            for (int i = 0; i < listeFavoriString.size(); i++) {
                for (int j = 0; j < listePays.size(); j++) {
                    if (listeFavoriString.get(i).equals(listePays.get(j).getNomEN())) {
                        listePaysFavori.add(listePays.get(j));
                    }
                }
            }
        }
    }


    /**
     * Permet d'ajouter/retirer un pays s'il est déjà/ou pas dans la liste de favori
     *
     * @param indice pays à ajouter ou à retirer
     */
    public static void modifierFavori(int indice, Context context) {

        boolean paysEffacer = false;
        if (listePaysFavori.size() != 0) {
            for (int i = 0; i < listePaysFavori.size(); i++) {
                //paysEffacer = false;

                if (listePaysFavori.get(i).getNomEN().equals(listePays.get(indice).getNomEN())) {
                    listePaysFavori.remove(listePaysFavori.get(i));
                    i--;
                    //i = listePaysFavori.size() + 2;
                    paysEffacer = true;
                }

                /*for (int j = 0; j < listePaysFavori.size(); j++) {
                    if (listePaysFavori.get(i).getNomEN().equals(listePaysFavori.get(j).getNomEN())){
                        listePaysFavori.remove(i);
                    }
                }*/
            }
            if (!paysEffacer) {
                listePaysFavori.add(listePays.get(indice));
            }
        } else {
            listePaysFavori.add(listePays.get(indice));
        }

        enregistrerEnLocal(context, indice);
    }

    /**
     * Permet de vérifier si un certain pays est dans la liste de favori
     *
     * @param indice pays à vérifier dans la liste de favori
     * @return un boolean vrai si le pays est dans la liste de favori
     */
    public static boolean verifierSiFavori(int indice) {
        boolean paysFavori = false;

        if (listePaysFavori.size() != 0) {
            for (int i = 0; i < listePaysFavori.size(); i++) {
                if (listePaysFavori.get(i).equals(listePays.get(indice))) {
                    paysFavori = true;
                }
            }
        }
        return paysFavori;
    }

    /**
     * Enregistre en local le fichier qui stocke la liste des pays favoris
     * @param context
     */
    private static void enregistrerEnLocal(Context context, int indice) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("favori.txt", Context.MODE_PRIVATE));

            for (int i = 0; i < listePaysFavori.size(); i++) {
                boolean paysPareils = false;
                for (int j = 0; j < listePaysFavori.size(); j++) {
                    if(listePaysFavori.get(i).getNomEN().equals(listePaysFavori.get(j).getNomEN())){
                        paysPareils = true;
                    }
                }
                if (listePaysFavori.get(i).getNomEN()!=(listePays.get(indice).getNomEN()) && !paysPareils) {
                    outputStreamWriter.write(listePaysFavori.get(i).getNomEN() + "\n");
                }
            }

            /*for (int i = 0; i < listePaysFavori.size(); i++) {
                for (int j = 0; j < listePaysFavori.size(); j++) {

                    if (listePaysFavori.get(i).getNomEN()!=(listePays.get(indice).getNomEN())) {
                        outputStreamWriter.write(listePaysFavori.get(i).getNomEN() + "\n");
                    }if(listePaysFavori.get(i).getNomEN().equals(listePaysFavori.get(j).getNomEN())){
                        listePaysFavori.remove(listePaysFavori.get(i));
                    }
                }
            }*/
            outputStreamWriter.close();
        } catch (IOException e) {
        }
    }
}
