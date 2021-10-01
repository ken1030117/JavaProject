
/**
 * les propriétés "client" et "contrat" sont remplacées par une nouvelle
 * propriété "dossier" qui contiendra la lettre du contrat suivi du
 * numéro du client
 *
 */

import java.util.ArrayList;

public class Client {

    /**
     * Cette classe modélise un client et contient les méthode permettant
     * d'accéder aux clés client, contrat, mois et reclamations.
     */


    //--------------------
    //ATTRIBUTS D'INSTANCE
    //--------------------

    private String dossier;
    private String mois;

    private ArrayList<Reclamation> reclamations;
    private  String total = "0";



    boolean  aDepasserLimite = false;



    private double[] limiteMensuelle= {250.00 , 200.00 , 250.00 , 150.00 ,
            300.00};



    private double[] limiteMaxMensuelle= {500.00 ,500.00 ,500.00 ,500.00 ,
            500.00 };


    //------------
    //CONSTRUCTEUR
    //------------

    /**
     * Constructeur
     *
     * ANTÉCÉDENT: les données doivent déjà avoir été validées
     *
     * @param dossier la lettre A,B,C,D ou E correspondant au contrat suivi
     *                du numéro de client
     * @param mois le mois pour lequel les réclamations sont faites
     * @param reclamations la liste des réclamations
     */
    public Client(String dossier, String mois,
                  ArrayList<Reclamation> reclamations){
        this.dossier = dossier;
        this.mois = mois;
        this.reclamations = reclamations;
    }

    //-------
    //GETTERS
    //-------

    /**
     * Premet d'obtenir le dosssier du Client
     * @return retourne le dossier du Client
     */
    public String getDossier() {
        return dossier;
    }

    /**
     * Premet d'obtenir le numero de client du Client
     *
     * @return retourne le numéro de client du Client
     */
    public String getClient() {
        return dossier.substring(1);
    }

    /**
     * Permet d'obtenir le contrat A,B,C,D ou E du Client
     *
     * @return le contrat A,B,C,D ou E du Client
     */
    public char getContrat() {
        return dossier.charAt(0);
    }

    /**
     * Permet d'obtenir le mois pour lequel ont fait des réclamations
     *
     * @return le mois pour lequel ont fait des réclamations
     */
    public String getMois() {
        return mois;
    }


    /**
     * Permet d'obtenir la liste des réclamation du client pour le mois en cours
     *
     * @return la liste des réclamation du client pour le mois en cours
     */
    public ArrayList<Reclamation> getReclamations() {
        return reclamations;
    }


    /**
     * Permet d'obtenir le total de toute les reclamations
     * @return le total des reclamations
     */
    public String getTotal() {
        return total;
    }

    /**
     * permet d'obtenir la limite mensuel
     * @return la limite mensuelle
     */

    public double[] getLimiteMensuelle() {
        return limiteMensuelle;
    }

    /**
     * permet d'obtenir la limite max mensuel
     * @return la limite mensuelle
     */
    public double[] getLimiteMaxMensuelle() {
        return limiteMaxMensuelle;
    }

    /**
     * permet d'obtenir aDepasserLimite
     * @return aDepasserLimite
     */
    public boolean getADepasserLimite() {
        return aDepasserLimite;
    }


    /**
     * modifier aDepasserLimite
     * @param aDepasserLimite le boolean a depasser
     */
    public void setaDepasserLimite(boolean aDepasserLimite) {
        this.aDepasserLimite = aDepasserLimite;
    }


    /**
     * Permet d'ajouter une valeur à total
     * @param cout la somme à ajouter .
     */
    public void addTotal(String cout) {
        double montants = Double.parseDouble(cout);
        total = String.valueOf(montants + Double.parseDouble(total));


    }

    /**
     * permet d'obtenir l'index de la limite
     * des soins n'allant pas jusqu'a 500$
     * @param soin le numéro de soin
     * @return l'index de la limite mensuel
     */

    public int getIndexLimite(int soin){
        int limite = 5;
        if(soin == 100){
            limite = 0;
        }else if(soin == 175){
            limite = 1 ;
        }else if(soin == 200){
            limite = 2;
        }else if(soin == 500){
            limite = 3;
        }else if(soin == 600){
            limite = 4 ;
        }
        return limite;
    }

    /**
     * permet d'obtenir l'index de la limite
     * des soins allant jusqu'a 500$
     * @param soin le numéro de soin
     * @return l'index de la limite mensuel
     */

    public int getIndexLimiteMax(int soin ){
        int limite = 5;
        if(soin == 0){
            limite = 0;
        }else if(soin == 150){
            limite = 1 ;
        }else if(soin >= 300 && soin <=399){
            limite = 2;
        }else if(soin == 400){
            limite = 3;
        }else if(soin == 700){
            limite = 4 ;
        }
        return limite;
    }


    /**
     * Construit une representation sous forme de chaine de caracteres de ce
     * Client.
     *
     * @return une representation sous forme de chaine de caractères de ce
     *         Client.
     */
    @Override
    public String toString() {
        return "Client{"
                + "dossier='" + dossier + '\''
                + ", mois='" + mois + '\''
                + ", reclamations=" + reclamations
                +'}';
    }
}
