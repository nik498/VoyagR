package ca.qc.bdeb.voyagr.Vue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import ca.qc.bdeb.voyagr.Modele.Modele;

/**
 * Permet de lancer une alerte sur l'information des créateurs de l'app VoyagR
 */
public class APropos extends DialogFragment {
    /**
     * Permet de lancer une alerte si l'utilisateur veut savoir plus sur les créateurs de l'app VoyagR
     *
     * @param savedInstanceState état du dialog
     * @return le dialog construit
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (Modele.getLangueFrancais()) {
            builder.setTitle("À propos");
            builder.setMessage("VoyagR est le projet d'intégration en sciences informatiques et mathématiques de Maxime Bourdeau et Nicolas Charron.");
        } else {
            builder.setTitle("Über VoyagR");
            builder.setMessage("VoyagR ist das Integrationsprojekt in Informatik und Mathematik von Maxime Bourdeau und Nicolas Charron.");
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        return builder.create();
    }
}
