package ca.qc.bdeb.voyagr.Modele;

/**
 * Cette classe représente le PIB d'un pays et est utilisée dans le fonctionnement du quiz
 */
public class Pib {

    private String nomPays;
    private double pib;

    /**
     * Constructeur du PIB
     * @param nomPays Nom du pays auquel est associé ce PIB
     * @param pib Pib du pays associé
     */
    public Pib(String nomPays, double pib) {
        this.nomPays = nomPays;
        this.pib = pib;
    }

    /**
     * Constructeur vide du PIB
     */
    public Pib() {
    }

    public String getNomPays() {
        return nomPays;
    }

    public void setNomPays(String nomPays) {
        this.nomPays = nomPays;
    }

    public double getPib() {
        return pib;
    }

    public void setPib(double pib) {
        this.pib = pib;
    }
}
