
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Cette classe contient des méthodes permettant de transformer un objet
 * json en une instance Client, et vice versa.
 */

public class JSON {

    //----------
    //CONSTANTES
    //----------

    private static final String DOSSIER = "dossier";
    private static final String MOIS = "mois";
    private static final String RECLAMATIONS = "reclamations";
    private static final String REMBOURSEMENTS = "remboursements";
    private static final String TOTAL = "total";
    private static final String SOIN = "soin";
    private static final String DATE = "date";
    private static final String MONTANT = "montant";
    private static final String MESSAGE = "message";
    public static final String NBCLES_ERR = "Il y a un nombre de clés plus " +
            "élevé que le nombre admis.";
    public static final String DONNEES_INVALIDES = "Données invalides";
    public static final String MOIS_INVALIDE = "La mois est invalide. " +
            "Veuillez bien entrer le mois du contrat.";
    public static final String DATE_INVALIDE = "La date est invalide. " +
            "Veuillez bien entrer le mois du contrat.";
    public static final String SOIN_INVALIDE = "Le soin est invalide. " +
            "Veuillez bien entrer le numéro correspondant au soin du contrat.";
    public static final String SOIN_INVALIDE2 = "Le nombre de soin " +
            "excède la quantité permise pour une journée";
    public static final String DOSSIER_INVALIDE = "Le numéro du dossier est " +
            "invalide. Veuillez bien entrer le numéro du dossier.";
    public static final String MONTANT_INTROUVABLE = "Montant non trouvé. " +
            "Assurez-vouz que le montant est présent et bien écrit.";
    public static final String DOSSIER_INTROUVABLE = "Dossier non trouvé. " +
            "Veuillez vous assurer que vous avez un dossier avec la compagnie.";
    public static final String MOIS_INTROUVABLE = "Mois non trouvé. " +
            "Veuillez vous assurer que vous avez le mois correct du contrat.";
    public static final String RECLAMATION_INTROUVABLE = "Réclamations non " +
            "trouvés. Veuillez vous assurer que vous avez une " +
            "réclamation à faire afin d'avoir un remboursement..";
    public static final String SOIN_INTROUVABLE = "Soin non trouvé. " +
            "Veuillez vous assurer que vous avez le numéro correspondant " +
            "au soin demandé.";
    public static final String DATE_INTROUVABLE = "Date non trouvée. " +
            "Veuillez vous assurer que vous avez la date est présente " +
            "et à un mois non précedant celui du contrat.";
    public static final String MONTANT_INVALIDE = "Montant invalide. " +
            "Assurez-vouz que le montant est bien écrit.";
    public static final String MONTANT_INVALIDE2 = "Montant invalide. " +
            "Assurez-vouz que le montant excède 0.";
    public static final String MONTANT_INVALIDE3 = "Montant invalide. " +
            "Assurez-vouz que le montant est plus petit que 500$.";

    //-------------------
    //MÉTHODES PUBLIQUES
    //-------------------

    /**
     * Cette méthode retourne l'objet Client correspondant à l'objet json
     * entré en paramètre.
     * <p>
     * ANTECEDENT: l'objet json doit avoir été préalablement validé
     *
     * @param jsonObj l'objet json à transformer en instance de Client
     * @return l'instance Client correspondant à l'objet json entré en
     * paramètre.
     */
    public static Client jsonToClient(JSONObject jsonObj) {
        Client client;

        client = new Client(jsonObj.getString(DOSSIER),
                jsonObj.getString(MOIS),
                JSONArrayToArrayList(jsonObj.getJSONArray(RECLAMATIONS)));

        return client;
    }

    /**
     * Cette méthode construit un objet Json de remboursement destiné à être
     * écrit dans le fichier de sortie du programme.
     * <p>
     * ANTÉCÉDENT: Les montants de la liste de réclamations doivent
     * préalablement avoir été modifiés avec les montants des
     * remboursements
     *
     * @param client l'instance du Client qu'on veut transformer en objet json
     * @return l'objet Json construit à partir duClient entré en paramètre.
     * Si le client est null, l'objet json de sortie contiendra la clé
     * "message" contenant la valeur "Données invalides".
     */
    public static JSONObject clientToJson(Client client) {
        JSONObject newJsonObj = new JSONObject();

        newJsonObj.accumulate(DOSSIER, client.getDossier());
        newJsonObj.accumulate(MOIS, client.getMois());
        newJsonObj.accumulate(REMBOURSEMENTS, listeToJsonArray(client));
        newJsonObj.accumulate(TOTAL, client.getTotal() + "$");

        return newJsonObj;
    }

    /**
     * Cette méthode retourne l'objet Stats correspondant à l'objet json
     * entré en paramètre.
     *
     * ANTECEDENT: l'objet json doit avoir été préalablement validé
     *
     * @param jsonObj l'objet json à transformer en instance de Stat
     * @return l'instance Client correspondant à l'objet json entré en
     * paramètre.
     */
    public static Stats jsonToStats(JSONObject jsonObj) {
        Stats stat;

        stat = new Stats(jsonObj.getInt("nbReclamationValide"),
                jsonObj.getInt("nbDemandeRejetee"),
                JSONArrayToDict(jsonObj.getJSONArray("soins")));

        return stat;
    }

    /**
     * Cette méthode construit un objet Json de remboursement destiné à être
     * écrit dans le fichier de sortie du programme.
     *
     * ANTÉCÉDENT: Les montants de la liste de réclamations doivent
     * préalablement avoir été modifiés avec les montants des
     * remboursements
     *
     * @param stats l'instance du Stats qu'on veut transformer en objet json
     * @return l'objet Json construit à partir duClient entré en paramètre.
     * Si le client est null, l'objet json de sortie contiendra la clé
     * "message" contenant la valeur "Données invalides".
     */
    public static JSONObject statsToJson(Stats stats) {
        JSONObject newJsonObj = new JSONObject();

        newJsonObj.accumulate("nbReclamationValide",
                stats.getNombreReclamationsValide());
        newJsonObj.accumulate("nbDemandeRejetee",
                stats.getNombreDemandeRejetee());
        newJsonObj.accumulate("soins", HashMapToJsonArray(stats));

        return newJsonObj;

    }

    /**
     * Cette méthode construit un nouvel objet json qui contiendra la clé
     * message pour laquelle on associera la valeur msgErreur entré en
     * paramètre.
     *
     * @param msgErreur le message d'erreur à insérer comme valeur à la clé
     *                  message de l'objet json retourné
     * @return un nouvel objet json contenant le message d'erreur entré en
     * paramètre.
     */
    public static JSONObject msgErrToJson(String msgErreur) {
        JSONObject newJsonObj = new JSONObject();
        newJsonObj.accumulate(MESSAGE, msgErreur);

        return newJsonObj;
    }


    //----------------
    //MÉTHODES PRIVÉES
    //----------------

    /**
     * Cette méthode retourne une ArrayList contenant toutes les réclamations
     * contenues dans le JSONArray entré en paramètre.
     *
     * @param data le JsonArray contenant toutes les reclamations faites pour
     *             un mois par un client.
     */
    private static ArrayList<Reclamation> JSONArrayToArrayList(JSONArray data) {
        ArrayList<Reclamation> reclamations = new ArrayList<>();

        for (int i = 0; i < data.size(); ++i) {
            JSONObject reclamation = data.getJSONObject(i);
            reclamations.add(new Reclamation(
                    reclamation.getInt(SOIN),
                    reclamation.getString(DATE),
                    reclamation.getString(MONTANT)));
        }
        return reclamations;
    }

    /**
     * Cette méthode retourne une ArrayList contenant toutes les réclamations
     * contenues dans le JSONArray entré en paramètre.
     *
     * @param data le JsonArray contenant toutes les reclamations faites pour
     *             un mois par un client.
     */
    private static HashMap<String, Integer> JSONArrayToDict(JSONArray data) {
        HashMap<String, Integer> soins = new HashMap<>();

        for (int i = 0; i < data.size(); ++i) {
            JSONObject soin = data.getJSONObject(i);
            Iterator<String> it = soin.keys();
            String key = it.next();
            soins.put(key, soin.getInt(key));
        }

        return soins;
    }

    /**
     * Cette méthode construit un objet json à partie de l'instance
     * Reclamation entrée en paramètre.
     *
     * @param reclamation la réclamation qu'on veut transformer en objet json
     * @return l'objet json correspondant à la réclamation entrée en paramètre
     */
    private static JSONObject reclamationToJsonObj(Reclamation reclamation) {
        JSONObject reclamationJson = new JSONObject();

        reclamationJson.accumulate(SOIN, reclamation.getSoin());
        reclamationJson.accumulate(DATE, reclamation.getDate());
        reclamationJson.accumulate(MONTANT, reclamation.getMontant());

        return reclamationJson;
    }

    /**
     * Cette méthode construit un objet JSONArray contenant les données de
     * remboursements de la liste de réclamation du client entré en paramètre
     * <p>
     * ANTECEDANT: les montants de la liste reclamations doivent avoir été
     * préalablement modifiés avec les montants de remboursement
     * propre à chaque réclamation.
     *
     * @param client le client pour lequel on cherche à obtenir un JSONArray
     *               à partir de sa liste de reclamations
     * @return un JSONArray contenant les données de remboursements du client
     */
    private static JSONArray listeToJsonArray(Client client) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj;

        for (int i = 0; i < client.getReclamations().size(); i++) {
            jsonObj = reclamationToJsonObj(client.getReclamations().get(i));
            jsonArray.add(jsonObj);
        }

        return jsonArray;

    }


    /**
     * Cette méthode construit un objet JSONArray contenant les statistiques des
     * soins.
     *
     *
     * @param stats
     * @return un JSONArray contenant les soins
     */
    private static JSONArray HashMapToJsonArray(Stats stats) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        HashMap<String, Integer> soins = stats.getSoins();
        for (String key : soins.keySet()) {
            jsonObj = soinToJsonObj(key, soins.get(key));
            jsonArray.add(jsonObj);
        }
        //jsonArray.add(jsonObj);
        return jsonArray;
    }

    /**
     * Cette méthode construit un objet json à partie de l'instance
     * Reclamation entrée en paramètre.
     *
     * @param k la réclamation qu'on veut transformer en objet json
     * @param value la réclamation qu'on veut transformer en objet json
     * @return l'objet json correspondant à la réclamation entrée en paramètre
     */
    private static JSONObject soinToJsonObj(String k, int value) {
        JSONObject soinJson = new JSONObject();
        soinJson.accumulate(k, value);

        return soinJson;
    }
}