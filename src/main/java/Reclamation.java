

import org.apache.commons.lang.builder.EqualsBuilder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Reclamation {

    /**
     * cette classe modélise une réclamation d'assurance et contient les méthodes
     * pour accéder aux clés soin, date et montant, ainsi que pour modifier la
     * clé montant.
     */

    //----------------------
    //ATTRIBUTS D'INSTANCE
    //----------------------

    private int soin;
    private String date;
    private String montant;




    /**
     * Ce constructeur initialise une nouvelle réclamation avec les valeurs
     * données en paramètre
     *
     * @param soin le numero de soin
     * @param date la date sous format "AAAA-MM-JJ"
     * @param montant le montant de la reclamation ou du remboursement
     */
    public Reclamation(int soin, String date,
                       String montant) {

        this.soin = soin;
        this.date = date;
        this.montant = montant;
    }

    /**
     * Permet d'obtenir le numéro du soin de cette réclamation
     * @return le numero du soin de cette réclamation
     */
    public int getSoin() {
        return soin;
    }

    /**
     * Permet d'obtenir la date de cette réclamation
     * @return la date de cette reclamation
     */
    public String getDate() {
        return date;
    }

    /**
     * Permet d'obtenir le montant de cette reclamation
     * @return le montant de cette réclamation
     */
    public String getMontant() {
        return montant;
    }



    /**
     * permet de modifier le montant de cette reclamation pour entre-autre le
     * faire correspondre au montant du remboursement
     * @param montant le montant de reclamation
     */
    public void setMontant(String montant) {
        this.montant = montant;
    }

    /**
     * permet de modifier le montant de cette reclamation pour entre-autre le
     * faire correspondre au montant du remboursement. reçoit un montant de
     * type double et ajoute un signe de $.
     *
     * @param montant le montant de reclamation
     */
    public void setMontant(double montant) {

        this.montant = new Dollar(montant).centsToDollar();
    }


    /**
     * Construit une representation sous forme de chaine de caractères de cette
     * Reclamation.
     *
     * @return une representation sous forme de chaine de caractères de cette
     *         Reclamation.
     */
    @Override
    public String toString() {
        return "main.java.core.Reclamation{" +
                "soin=" + soin +
                ", date='" + date + '\'' +
                ", montant='" + montant + '\'' +
                '}';
    }

    /**
     * Teste si cette core.Reclamation est égale a autreReclamation.
     * Deux core.Reclamation a1 et a2 sont égales si leurs valeurs de soin sont
     * égales, leur date sont égales et leurs montants sont égaux
     *
     * @param autreReclamation la Reclamation a comparer avec cette core.Reclamation
     *
     * @return true si cette Reclamation est égal a autreReclamation, false
     *         sinon
     */
    @Override
    public boolean equals(Object autreReclamation) {
        if (this == autreReclamation) return true;

        if (autreReclamation == null || getClass() != autreReclamation.getClass()) return false;

        Reclamation that = (Reclamation) autreReclamation;

        return new EqualsBuilder()
                .append(soin, that.soin)
                .append(date, that.date)
                .append(montant, that.montant)
                .isEquals();
    }

}
