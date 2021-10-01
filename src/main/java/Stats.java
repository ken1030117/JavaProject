
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Stats {

    /**
     * Cette classe modélise une statistique et contient les méthode
     * permettant
     * de compter les differentes statistques et de les afficher.
     */

    private static String directory =
            System.getProperty("user.dir") + File.separator + "src"
                    + File.separator + "main" + File.separator + "resources";
    private static String filename = directory + File.separator + "stats.json";

    //--------------------
    //ATTRIBUTS D'INSTANCE
    //--------------------

    private int nombreReclamationsValide = 0;
    private int nombreDemandeRejetee = 0;
    private HashMap<String, Integer> soins = new HashMap<>();

    //------------
    //CONSTRUCTEUR
    //------------

    /**
     * Constructeur.
     *
     * ANTÉCÉDENT: les données doivent déjà avoir été validées
     *
     * @return un objet de type Stats initialise
     */
    public Stats() {
        nombreReclamationsValide = 0;
        nombreDemandeRejetee = 0;
        soins = new HashMap<>();
        for (String key : soins.keySet()) {
            soins.put(key, 0);
        }
    }

    /**
     * Constructeur.
     *
     * ANTÉCÉDENT: les données doivent déjà avoir été validées
     *
     * @param nbRecValide
     * @param nbDemandeRejetee
     * @param soinsP
     */
    public Stats(int nbRecValide, int nbDemandeRejetee,
                 HashMap<String, Integer> soinsP) {
        nombreReclamationsValide = nbRecValide;
        nombreDemandeRejetee = nbDemandeRejetee;
        soins = soinsP;
    }

    /**
     * Va nous permettre d'initialiser les statistiques.
     *
     */
    public Stats init() {
        Stats  newStats = new Stats();
        newStats.initSoins();
        try {
            ReadWriteFichierJson.jsonToFichier(filename,
                    JSON.statsToJson(newStats));
        } catch (InvalideDataException e) { System.out.println(e); }

        return newStats;
    }

    /**
     * Va nous permettre d'initialiser les soins.
     *
     */
    public void initSoins() {
        soins.put("Massothérapie", 0);
        soins.put("Ostéopathie", 0);
        soins.put("Médecin généraliste privé", 0);
        soins.put("Psychologie individuelle", 0);
        soins.put("Soins dentaires", 0);
        soins.put("Naturopathie, acuponcture", 0);
        soins.put("Chiropratie", 0);
        soins.put("Physiothérapie", 0);
        soins.put("Orthophonie, ergothérapie", 0);

    }

    /**
     * Permet d'obtenir le nombre de reclamations valides traitees.
     * @return le nombre de reclamations valides traitees
     */
    public int getNombreReclamationsValide() {
        return nombreReclamationsValide;
    }

    /**
     * Permet d'obtenir le nombre de demande rejetees.
     * @return le le nombre de demande rejetees
     */
    public int getNombreDemandeRejetee() {
        return nombreDemandeRejetee;
    }

    /**
     * Permet d'obtenir les statistiques par categories de soins.
     * @return les soins
     */
    public HashMap<String, Integer> getSoins() {
        return soins;
    }

    /**
     * Permet de mettre a jour le nombre de demande rejetees.
     *
     */
    public void updateNombreDemandeRejetee() {
        nombreDemandeRejetee = getNombreDemandeRejetee() + 1;
    }

    /**
     * Calcule la statistiques  pour les reclamations et les soins.
     *
     */
    public Stats calculerStatReclamationEtSoin(Client client, Stats stats) {
        ArrayList<Reclamation> reclamations = client.getReclamations();
        for (int i = 0; i < reclamations.size(); ++i) {
            Reclamation reclamation = reclamations.get(i);
            String key = Contrat.getTaux(client.getContrat(),
                    reclamation.getSoin()).getCategorie();
            stats.soins.put(key, stats.soins.getOrDefault(key, 0) + 1);
        }
        nombreReclamationsValide = nombreReclamationsValide
                + reclamations.size();

        return stats;
    }


    /**
     * Construit une representation des statistiques.
     *
     * @return l'affichage des statistiques.
     */
    @Override
    public String toString() {
        return "Statistiques :" + '\n'
                + "Nombre de réclamations valides traitées : "
                + nombreReclamationsValide + '\n'
                + "Nombre de demandes rejetées : "
                + nombreDemandeRejetee + '\n'
                + "Soins : " + '\n'
                + "     Massothérapie : " + soins.get("Massothérapie") + '\n'
                + "     Ostéopathie : " + soins.get("Ostéopathie") + '\n'
                + "     Médecin généraliste privé : "
                + soins.get("Médecin généraliste privé") + '\n'
                + "     Psychologie individuelle : "
                + soins.get("Psychologie individuelle") + '\n'
                + "     Soins dentaires : " + soins.get("Soins dentaires") + '\n'
                + "     Naturopathie, acuponcture : "
                + soins.get("Naturopathie, acuponcture") + '\n'
                + "     Chiropratie : " + soins.get("Chiropratie") + '\n'
                + "     Physiothérapie : " + soins.get("Physiothérapie") + '\n'
                + "     Orthophonie, ergothérapie : "
                + soins.get("Orthophonie, ergothérapie");
    }
}
