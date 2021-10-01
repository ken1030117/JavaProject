


import org.apache.commons.lang.builder.EqualsBuilder;

public class Taux {

    /**
     * Cette classe modélise un objet contenant les informations relatives au taux
     * de remboursement d'une compagnie d'assurance et le maximum remboursable pour
     * une catégorie de soin. Elle contient les méthodes qui permet d'accéder à
     * ces informations.
     */

    //--------------------
    //ATTRIBUTS D'INSTANCE
    //--------------------

    private String categorie;   //la categorie de soin
    private double taux;        //le taux de remboursement
    private double max;         //le maximum remboursable pour ce soin


    //-------------
    //CONSTRUCTEURS
    //-------------

    /**
     * Ce constructeur initialise un nouveau taux de remboursement pour les
     * catégories spécifiant un maximum remboursable.
     *
     * @param categorie la catégorie de soin couvert par ce taux
     * @param taux le taux de remboursement
     * @param max le montant maximum de remboursement
     */
    public Taux (String categorie, double taux, double max){
        this.categorie = categorie;
        this.taux = taux;
        this.max = max;
    }

    /**
     * Ce constructeur à 2 paramètres initialise un nouveau taux de
     * remboursement avec les valeurs catégorie et taux entrées en paramètre.
     * Le maximum est initialisé à 0.
     *
     * @param categorie la catégorie de soin couvert par ce taux
     * @param taux le taux de remboursement
     */
    public Taux (String categorie, double taux){
        this.categorie = categorie;
        this.taux = taux;
        this.max = 0;
    }

    //-------
    //GETTERS
    //-------

    /**
     * Permet d'obtenir le taux de remboursement
     *
     * @return le taux de remboursement
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Permet d'obtenir le taux de remboursement
     *
     * @return le taux de remboursement
     */
    public double getTaux() {
        return taux;
    }

    /**
     * Permet d'obtenir le maximum remboursable
     *
     * @return le maximum remboursable
     */
    public double getMax() {
        return max;
    }

    /**
     * Teste si ce taux est égal a autreTaux.
     * Deux core.Taux a1 et a2 sont égaux si leurs taux sont égaux et
     * leurs max sont égaux.
     *
     * @param autreTaux le core.Taux a comparer avec ce core.Taux
     * @return true si ce taux est égal a autreTaux, false sinon
     */
    @Override
    public boolean equals(Object autreTaux) {
        if (this == autreTaux) return true;

        if (!(autreTaux instanceof Taux)) return false;

        Taux taux1 = (Taux) autreTaux;

        return new EqualsBuilder()
                .append(getTaux(), taux1.getTaux())
                .append(getMax(), taux1.getMax())
                .append(categorie, taux1.categorie)
                .isEquals();
    }

    /**
     * Construit une representation sous forme de chaine de caractères de ce
     * core.Taux.
     *
     * @return une representation sous forme de chaine de caractères de ce
     *         core.Taux.
     */
    @Override
    public String toString() {
        return "Taux{" +
                "categorie='" + categorie + '\'' +
                ", taux=" + taux +
                ", max=" + max +
                '}';
    }
}
