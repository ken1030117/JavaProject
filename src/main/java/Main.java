import java.io.File;

/**
 * Classe main du projet. Les arguments correspondent aux fichiers d'entrée et
 * de sortie de la ligne de commande.
 */



public class Main {

    private static String directory =
            System.getProperty("user.dir") + File.separator + "src"
                    + File.separator + "main" + File.separator + "resources";
    private static String filename = directory + File.separator + "stats.json";

    /**
     * Affiche les statistiques.
     *
     */
    public static void afficherStatistique() {

        try {
            Stats statTest = JSON.jsonToStats(
                    ReadWriteFichierJson.FichierToJson(filename));
            System.out.println(statTest.toString());
        } catch (InvalideDataException e) {
            System.out.println(e);
        }
    }

    /**
     * Initialse les statistiques.
     *
     */
    public static void initialiserStatistique() {
        new Stats().init();
    }

    /**
     * Affiche un message d'erreur pour les options non reconnues et
     * le programme finit.
     *
     * @param args
     *
     */
    public static void AfficherOptionsNonReconnues(String[] args) {
        System.out.println("+ Option non reconnue : " + args[0]);
        System.out.println("+ Usage: Refund [-S,-SR,-p] inputfile "
                + "outputfile .");
        System.exit(1);
    }

    /**
     * Prendre en charge la gestion les options de statistiques.
     *
     * @param args
     *
     */
    public static void gererOptionStatistiques(String[] args) {

        if (args[0].equals("-S")) {
            afficherStatistique();
        } else if (args[0].equals("-SR")) {
            initialiserStatistique();
        } else {
            AfficherOptionsNonReconnues(args);
        }
    }

    /**
     *  Prends en charge la gestion de tous les options.
     *
     * @param args
     *
     */
    public static void gestionDesOptions(String[] args) {
        if (args.length == 1) {
            gererOptionStatistiques(args);
        } else if (args.length == 2) {
            try { ReglesDAffaires.inputToOutput(args[0], args[1], false);
            } catch (InvalideDataException e) {
                System.out.println(e); }
        } else if (args.length == 3 && args[0].equals("-p")) {
            System.out.println("+ Option : -p ");
            try { ReglesDAffaires.inputToOutput(args[1], args[2], true);
            } catch (InvalideDataException e) {
                System.out.println(e); }

        } else {
            AfficherOptionsNonReconnues(args);
        }
    }

    public static void main(String[] args) {
        if (args.length != 3 && args.length != 2 && args.length != 1) {
        System.out.println("+ Nombre d'arguments invalide...");
        System.exit(1);
        } else {
        gestionDesOptions(args);
        }
    }
}