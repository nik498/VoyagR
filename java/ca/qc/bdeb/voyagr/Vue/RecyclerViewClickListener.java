package ca.qc.bdeb.voyagr.Vue;

import android.view.View;


/**
 * Interface qui permet d'override la construction du ClickListener dans une RecyclerView afin de
 * mieux convenir à nos besoins pour l'application.
 */
public interface RecyclerViewClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}