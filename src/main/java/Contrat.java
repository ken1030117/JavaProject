
public class Contrat {

    /**
     * Cette classe contient les données des contrats A,B,C,D et E de la compagnie
     * d'assurance et le Taux de remboursement (instance qui contient aussi le
     * maximum payable) en fonction du contrat et du soin spécifié.
     */


    //----------
    //CONSTANTES
    //----------

    //Un tableau contenant les taux de remboursement et les maximum du Contrat A
    private static final Taux[] contratA = {
            new Taux("Massothérapie", 0.25),
            new Taux("Ostéopathie", 0.35),
            new Taux("Psychologie individuelle", 0.25),
            new Taux("Soins dentaires", 0),
            new Taux("Naturopathie, acupuncture", 0),
            new Taux("Chiropractie", 0.25),
            new Taux("Physiothérapie", 0.40),
            new Taux("Orthophonie, ergothérapie", 0),
            new Taux("Kinésithérapie",0),
            new Taux("Médecin généraliste privé",0.50)
    };

    //Un tableau contenant les taux de remboursement et les maximum du Contrat B
    private static final Taux[] contratB = {
            new Taux("Massothérapie", 0.50, 40),
            new Taux("Ostéopathie", 0.50, 50),
            new Taux("Psychologie individuelle", 1.00),
            new Taux("Soins dentaires", 0.50),
            new Taux("Naturopathie, acupuncture", 0),
            new Taux("Chiropractie", 0.50, 50),
            new Taux("Physiothérapie", 1.00),
            new Taux("Orthophonie, ergothérapie", 0.70),
            new Taux("Kinésithérapie",0.00),
            new Taux("Médecin généraliste privé",0.75)
    };

    //Un tableau contenant les taux de remboursement et les maximum du Contrat C
    private static final Taux[] contratC = {
            new Taux("Massothérapie", 0.90),
            new Taux("Ostéopathie", 0.95),
            new Taux("Psychologie individuelle", 0.90),
            new Taux("Soins dentaires", 0.90),
            new Taux("Naturopathie, acupuncture", 0.90),
            new Taux("Chiropractie", 0.90),
            new Taux("Physiothérapie", 0.75),
            new Taux("Orthophonie, ergothérapie", 0.90),
            new Taux("Kinésithérapie", 0.85),
            new Taux("Médecin généraliste privé", 0.90)
    };

    //Un tableau contenant les taux de remboursement et les maximum du Contrat D
    private static final Taux[] contratD = {
            new Taux("Massothérapie", 1, 85),
            new Taux("Ostéopathie", 1),
            new Taux("Psychologie individuelle", 1, 100),
            new Taux("Soins dentaires", 1),
            new Taux("Naturopathie, acupuncture", 1, 65),
            new Taux("Chiropractie", 1, 75),
            new Taux("Physiothérapie", 1, 100),
            new Taux("Orthophonie, ergothérapie", 1, 90),
            new Taux("Kinésithérapie", 1, 150),
            new Taux("Médecin généraliste privé", 0.95)
    };

    //Un tableau contenant les taux de remboursement et les maximum du Contrat E
    private static final Taux[] contratE = {
            new Taux("Massothérapie", 0.15),
            new Taux("Ostéopathie", 0.25),
            new Taux("Psychologie individuelle", 0.12),
            new Taux("Soins dentaires", 0.60),
            new Taux("Naturopathie, acupuncture", 0.25, 15),
            new Taux("Chiropractie", 0.30, 20),
            new Taux("Physiothérapie", 0.15),
            new Taux("Orthophonie, ergothérapie", 0.22),
            new Taux("Kinésithérapie", 0.15),
            new Taux("Médecin généraliste privé", 0.25, 20)
    };



    //un matrice servant à obtenir le contrat spécifié
    private static final Taux[][] contrats = {
            contratA,
            contratB,
            contratC,
            contratD,
            contratE,
    };

    //----------------
    //MÉTHODE PUBLIQUE
    //----------------

    /**
     * Cette méthode parcourt la matrice des contrats et retourne l'objet
     * Taux qui contient le taux de remboursement ainsi que le maximum alloué
     * pour le numéro de soin donné en paramètre selon le contrat donné en
     * paramètre. Si les valeurs sont invalides, le Taux retourné est null.
     *
     * @param contrat le contrat du client
     * @param soin le numéro du soin de la réclamation
     *
     * @return l'objet Taux qui contient le taux de remboursement ainsi que
     *         le maximum alloué pour le numéro de soin donné en paramètre
     *         selon le contrat donné en paramètre. Si les valeurs sont
     *         invalides, le Taux retourné est null.
     */
    protected static Taux getTaux(char contrat, int soin) {
        int indexSoin ;
        int indexContrats;
        Taux taux = null;

        try {
            if (ValiderJson.estSoinValide(soin)) {
                if(soin == 150){
                indexSoin=8;
                }else if(soin == 175){
                    indexSoin = 9;
                }
                else{
                    indexSoin = soin / 100;
                }
                indexContrats = getIndexContrat(contrat);
                taux = contrats[indexContrats][indexSoin];
            }
        }catch (IllegalArgumentException ignored) {}

        return taux;
    }

    /**
     * Cette méthode permet d'obtenir l'index dans le tableau de contrat du
     * contrat entré en paramètre.
     *
     * ANTÉCÉDENT:la valeur contrat doit avoir été préalablement validée
     *
     * @param contrat le contrat A,B,C,D ou E pour lequel on veut obtenir
     *                l'index du tableau
     *
     * @return l'index du tableau contrats pour le contrat entré en paramètre
     *
     * @throws IllegalArgumentException si la valeur de contrat ne correspond
     *         pas à A,B,C,D ou E
     */
    protected static int getIndexContrat(char contrat) {
        int indexContrat = 0;

        if (!Character.toString(contrat).matches("[ABCDE]"))
            throw new IllegalArgumentException("Contrat Invalide");

        for (char c = 'A' ; c < contrat ; c++){
            indexContrat++;
        }

        return indexContrat;
    }
}
