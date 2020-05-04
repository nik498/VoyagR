package ca.qc.bdeb.voyagr.Vue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import ca.qc.bdeb.voyagr.Modele.Modele;

/**
 * Cette classe extends un dialog pour faire apparaître un avertissement si l'utilisateur essaye d'accéder à la liste des favoris si celle-ci est vide.
 */
public class ListeVide extends DialogFragment {

    /**
     * Contenu de l'alerte à creer
     * @param savedInstanceState état de l'alerte dans l'application
     * @return L'alerte
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Erreur sur la liste!");
        if (Modele.getLangueFrancais()) {
            builder.setMessage("Attention! Il n'y a aucun élément dans votre liste de favoris! Pour ajouter un pays à votre liste de favoris, appuyez sur l'icône de coeur sur l'écran d'un pays.");
        } else {
            builder.setMessage("Achtung! Es befinden sich keine Artikel in deiner Favoritenliste! Um ein Land zu Ihrer Favoritenliste hinzuzufügen, tippen Sie auf das Herzsymbol auf dem Bildschirm eines Landes.");
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        return builder.create();
    }

}
