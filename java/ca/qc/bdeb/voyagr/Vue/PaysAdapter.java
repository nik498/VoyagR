package ca.qc.bdeb.voyagr.Vue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ca.qc.bdeb.voyagr.Modele.Modele;
import ca.qc.bdeb.voyagr.Modele.Pays;
import ca.qc.bdeb.voyagr.R;

/**
 * Cette classe est un adapter pour le RecyclerView. Elle permet de créer les éléments inclus
 * dans le RecyclerView et de les changer selon le type de tri voulu.
 */
public class PaysAdapter extends RecyclerView.Adapter<PaysAdapter.ViewHolder> {

    private ArrayList<Pays> liste;
    private Context context;
    private Activity activity;

    /**
     * Constructeur du PaysAdapter
     * @param pays Le pays que l'on veut mettre dans le RecyclerView
     */
    public PaysAdapter(ArrayList<Pays> pays, Activity activity) {
        this.liste = pays;
        this.activity = activity;
    }

    /**
     * Classe interne qui gère comment est affiché chaque pays dans le RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView nom;

        /**
         * Constructeur de la classe interne de ViewHolder
         * @param view View dans laquelle on veut construire les PaysAdapter
         */
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            nom = (TextView) view.findViewById(R.id.tvNomPaysListe);
            context = itemView.getContext();
        }
    }

    /**
     * Cette méthode crée le RecyclerView la première fois qu'il est appelé
     * @param parent Ce qui contient le RecyclerView
     * @param ViewType Type de vue du parent
     * @return le contenant de la vue avec le RecyclerView
     */
    @NonNull
    @Override
    public PaysAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pays, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Cette méthode change le RecyclerView et ses éléments lorsque l'utilisateur scroll vers la bas,
     * ou lorsqu'il change le type de tri. Les informations en bas du nom du pays seront alors changées
     * en conséquence
     * @param viewHolder Contenant de la RecyclerView
     * @param i Indice du pays qui doit être modifié par le RecyclerView, ou affiché suite à un déroulement
     *          de la part de l'utilisateur
     */
    @Override
    public void onBindViewHolder(@NonNull PaysAdapter.ViewHolder viewHolder, int i) {
        final Pays pays = liste.get(i);
        TextView tv;
        tv = viewHolder.itemView.findViewById(R.id.tvNomPaysListe);
        TextView footer;
        footer = viewHolder.itemView.findViewById(R.id.tvFooterPays);
        if (Modele.getLangueFrancais()) {
            tv.setText(pays.getNomFR());
        } else {
            tv.setText(pays.getNomDE());
        }

        if(Modele.getTabTypeDeTri()[0]){
            footer.setText("");
        } else if (Modele.getTabTypeDeTri()[1] && Modele.getLangueFrancais()){
            footer.setText(pays.getCapitaleFR());
        } else if (Modele.getTabTypeDeTri()[1] && !Modele.getLangueFrancais()){
            footer.setText(pays.getCapitaleDE());
        } else if (Modele.getTabTypeDeTri()[2] && !Modele.getLangueFrancais()){
            footer.setText(Modele.espacerLesNombreInt(pays.getPopulation()) + " Einwohner");
        } else if (Modele.getTabTypeDeTri()[2] && Modele.getLangueFrancais()){
            footer.setText(Modele.espacerLesNombreInt(pays.getPopulation()) + " habitants");
        } else if (Modele.getTabTypeDeTri()[4]){
            footer.setText(Modele.espacerLesNombreDouble(pays.getSuperficie()) + " km\u00B2");
        } else if (Modele.getTabTypeDeTri()[3]){
            footer.setText(Modele.espacerLesNombreDouble(pays.getPib()) + " G\u0024 USD");
        }

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Modele.lireFichierPays(context, pays.getNomEN());
                Modele.setPaysClique(pays.getNomEN());
                Modele.setVientDeLaCarte(false);

                intent = new Intent(context, InfoPays.class);
                context.startActivity(intent);
                activity.finish();
            }
        });
    }

    /**
     * Trouve le nombre d'items dans la liste qui sert à construire le RecyclerView
     * @return le nombre d'items
     */
    @Override
    public int getItemCount() {
        if (liste != null) {
            return liste.size();
        } else {
            return 0;
        }
    }

}
