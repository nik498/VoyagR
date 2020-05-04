package ca.qc.bdeb.voyagr.Modele;

/**
 * Cette classe représente les pays de la carte et contient leurs attributs
 */
public class Pays{

    private String nomFR;
    private int population;
    private double superficie;
    private String capitaleFR, nomDE, capitaleDE;

    private double pib;
    private String nomEN;
    private String ISO2;

    /**
     * Constructeur d'un objet Pays
     * @param nomFR nom en français de ce pays
     * @param population population de ce pays
     * @param superficie superficie de ce pays
     * @param capitaleFR nom de la capitale du pays en français
     * @param nomDE nom du pays en allemand
     * @param capitaleDE nom de la capitale du pays en allemand
     * @param pib pib du pays
     * @param nomEN nom en anglais du pays
     * @param ISO2 code ISO 3166-1 alpha-2 du pays
     */
    public Pays( String nomFR, int population, double superficie, String capitaleFR, String nomDE, String capitaleDE, Double pib, String nomEN, String ISO2) {

        this.nomFR = nomFR;
        this.population = population;
        this.superficie = superficie;
        this.capitaleFR = capitaleFR;
        this.nomDE = nomDE;
        this.capitaleDE = capitaleDE;
        this.pib = pib;
        this.nomEN = nomEN;
        this.ISO2 = ISO2;
    }

    public Pays() {
    }



    public void setNomFR(String nomFR) {
        this.nomFR = nomFR;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public void setCapitaleFR(String capitaleFR) {
        this.capitaleFR = capitaleFR;
    }

    public void setNomDE(String nomDE) {
        this.nomDE = nomDE;
    }

    public void setCapitaleDE(String capitaleDE) {
        this.capitaleDE = capitaleDE;
    }

    public void setPib(Double pib) {
        this.pib = pib;
    }

    public void setNomEN(String nomEN) {
        this.nomEN = nomEN;
    }

    public void setISO2(String ISO2) {
        this.ISO2 = ISO2;
    }

    @Override
    public String toString() {
        return "Pays{" +
                "nomFR='" + nomFR + '\'' +
                ", population=" + population +
                ", superficie=" + superficie +
                ", capitaleFR='" + capitaleFR + '\'' +
                ", nomDE='" + nomDE + '\'' +
                ", capitaleDE='" + capitaleDE + '\'' +
                ", pib=" + pib +
                ", nomEN='" + nomEN + '\'' +
                ", ISO2='" + ISO2 + '\'' +
                '}';
    }

    public String getNomFR() {
        return nomFR;
    }

    public int getPopulation() {
        return population;
    }

    public double getSuperficie() {
        return superficie;
    }

    public String getCapitaleFR() {
        return capitaleFR;
    }

    public String getNomDE() {
        return nomDE;
    }

    public String getCapitaleDE() {
        return capitaleDE;
    }

    public Double getPib() {
        return pib;
    }

    public String getNomEN() {
        return nomEN;
    }

    public String getISO2() {
        return ISO2;
    }


}
