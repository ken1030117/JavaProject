
public class Dollar {

    /**
     * Cette classe va nous permettre d'effectuer des operations securisé sur
     * la monnaie.
     *
     */

    //--------------------
    //ATTRIBUTS D'INSTANCE
    //--------------------

    private int cents;
    private int partieEntier;
    private int partieFractionnaire;

    //------------
    //CONSTRUCTEUR
    //------------

    /**
     * Constructeur
     *
     * ANTÉCÉDENT: le montant doit être valides
     *
     * Permet d'initialisé un objet Dollar
     *
     */
    public Dollar(){
        this.cents = 0;
        this.partieEntier = 0;
        this.partieFractionnaire = 0;

    }

    /**
     * Constructeur
     *
     * ANTÉCÉDENT: le montant doit être valides
     *
     *
     * @param montant le montant Dollar en Double qu'on doit convertir en
     *                cents.
     */
    public Dollar(Double montant){
        this.cents = (int) (montant * 100);
        this.partieEntier = cents / 100;
        this.partieFractionnaire = cents % 100;

    }

    /**
     * Constructeur
     *
     * ANTÉCÉDENT: le montant doit être valides
     *
     *
     * @param montant le montant Dollar  en string qu'on doit convertir en
     *                cents.
     */
    public Dollar(String montant){
        double stringToDouble = Double.parseDouble(montant
                .substring(0, montant.length() - 1)
                .replace(',','.'));
        this.cents = (int) (stringToDouble * 100);
        this.partieEntier = cents / 100;
        this.partieFractionnaire = cents % 100;

    }

    //-------
    //GETTERS
    //-------

    /**
     * Premet d'obtenir la partie entier du dollar
     * @return retourne la partie entier
     */
    public int getPartieEntier() {
        return partieEntier;
    }

    /**
     * Premet d'obtenir la partie fractionnaire du dollar
     * @return retourne la partie fractionnaire
     */
    public int getPartieFractionnaire() {
        return partieFractionnaire;
    }

    /**
     * Premet d'obtenir des cents
     * @return retourne des cents.
     */
    public int getCents() {
        return cents;
    }

    /**
     * Permet d'avoir le dollar en string
     * @return retourne des dollar dans le format string.
     */
    public String centsToDollar() {
        return partieEntier + "." +
                String.format("%02d", partieFractionnaire) + "$";
    }

    /**
     * Permet de modifier la valeur du Dollar courant
     *
     *  @param cents prend des cents
     *
     */
    public void setDollar(int cents) {
        this.cents = cents;
        this.partieEntier = cents / 100;
        this.partieFractionnaire = cents % 100;

    }

    @Override
    public String toString() {
        return "Dollar{" +
                "cents='" + cents + '\'' +
                "partieEntier='" + partieEntier + '\'' +
                ", partieFractionnaire='" + partieFractionnaire + '\'' +
                '}';
    }

}
