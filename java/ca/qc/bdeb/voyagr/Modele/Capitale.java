package ca.qc.bdeb.voyagr.Modele;

/**
 * Permet de créer des "objets" Capitale pour les utiliser dans des listes
 */
public class Capitale {

    private String capitale, nomPays;

    /**
     * Constructeur de la capitale d'un pays
     * @param capitale nom de la capitale
     * @param nomPays nom du pays dont c'est la capitale
     */
    public Capitale(String capitale, String nomPays) {
        this.capitale = capitale;
        this.nomPays = nomPays;
    }

    public Capitale(){
    }

    public void setCapitale(String capitale) {
        this.capitale = capitale;
    }

    public void setNomPays(String nomPays) {
        this.nomPays = nomPays;
    }

    public String getCapitale() {
        return capitale;
    }

    public String getNomPays() {
        return nomPays;
    }

    @Override
    public String toString() {
        return "Capitale{" +
                "capitale='" + capitale + '\'' +
                ", nomPays='" + nomPays + '\'' +
                '}';
    }
}
