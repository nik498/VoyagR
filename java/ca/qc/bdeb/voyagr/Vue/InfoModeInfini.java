package ca.qc.bdeb.voyagr.Vue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import ca.qc.bdeb.voyagr.Modele.Modele;

/**
 * Permet de lancer une alerte sur l'information du mode infini
 */
public class InfoModeInfini extends DialogFragment {
    /**
     * Permet de lancer une alerte si l'utilisateur veut savoir plus sur le mode infini
     *
     * @param savedInstanceState état du dialog
     * @return le dialog construit
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        if (Modele.getLangueFrancais()) {
            builder.setTitle("Information sur le mode infini");
            builder.setMessage("Le mode infini n'a pas de limite de questions. L'utilisateur arrête le quiz quand il veut. Les questions sont " +
                    "générées aléatoirement entre les questions sur les capitales et celles sur le PIB.");
        } else {
            builder.setTitle("Informationen über den Endlosmodus");
            builder.setMessage("Der Endlosmodus hat keine Grenzen für Fragen. Der Benutzer stoppt das Quiz, wann er will. Fragen werden nach dem Zufallsprinzip zwischen Kapital- und BIP-Fragen generiert.");
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        return builder.create();
    }
}
