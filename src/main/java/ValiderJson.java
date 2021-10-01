
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ValiderJson {

    /**
     * Cette Classe permet de valider les données d'un JSONObject représentant un
     * client qui fait une demande de réclamations d'assurances.
     */


    //-----------
    //CONSTANTES
    //-----------

    //Regex pour confirmer les valeurs de type String
    private static final String REGEX_DOSSIER = "^[A-E]\\d{6}$";
    private static final String REGEX_MOIS = "^((\\d){4})-((0[1-9])|(1[0-2]))$";
    private static final String REGEX_MONTANT = "(^\\d+[,.]\\d{2})\\$$";

    //-----------------
    //MÉTHODE PUBLIQUE
    //-----------------

    /**
     * Cette méthode vérifie et valide les clés et les données contenues dans le
     * JSONObject entré en paramètre. Retourne true si le JSONObject est
     * une demande de reclamations valide, false sinon.
     *
     * @param jsonObject le JSONObject dont on veut vérifier la validité
     *
     * @return true si le JSONObject est valide, false sinon
     */
    public static boolean estJsonValide(JSONObject jsonObject){
        boolean estJsonValide = false;

        try {
            estJsonValide = sontDonneesClientValide(jsonObject)
                    && estListeReclamationsValide(jsonObject)
                    && estNbreSoinValide(jsonObject);
        } catch (JSONException ignored) {}

        return estJsonValide;
    }

    //----------------
    //MÉTHODES PRIVÉES
    //----------------

    /**
     * Cette méthode compte le nombre de clé contenu dans l'objet json entré
     * en paramètre.
     *
     * @param jsonObject le json object dont on veut avoir le nombre de clés
     * @return le nombre de clé contenu dans l'objet json entré en paramètre
     */
    protected static int getNbCles(JSONObject jsonObject){
        int compteur = 0;
        Iterator it = jsonObject.keys();

        while (it.hasNext()){
            compteur++;
            it.next();
        }
        return compteur;
    }

    /**
     * Cette méthode vérifie si le numéro du soin entré en paramètre est
     * valide, c'est à dire qu'il correspond bien aux numéros de soins
     * fournis par la compagnie d'assurance
     *
     * @param soin le numéro du soin à valider
     *
     * @return true si le numéro de soin est correspond à un des numéro de
     *         soins fournis dans les contrats de la compagnie d'assurance,
     *         false sinon.
     */
    public static boolean estSoinValide(int soin){

        return soin >= 0 && soin <= 700
                && (soin % 100 == 0 || (soin > 300 && soin < 400) || soin == 150 || soin == 175) ;
    }

    /**
     * Cette méthode vérifie si le format de la date entré en paramètre est
     * valide i.e. qu'elle est de format AAAA-MM-JJ, et qu'elle correspond
     * bien au mois entré en paramètre (mois spécifié dans la fiche de
     * réclamation).
     *
     * @param mois le mois spécifié dans la fiche de réclamation
     * @param date la date de la réclamation à valider
     *
     * @return true si la date est de format AAAA-MM-JJ et qu'elle correspond
     *         au même mois que le mois entré en paramètre, false sinon
     */
    public static boolean estDateValide(String mois, String date) {
        boolean estValide;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        estValide = date.length() == 10
                && sdf.parse(date, new ParsePosition(0)) != null
                && mois.equals(date.substring(0, 7));

        return estValide;
    }

    /**
     * Cette méthode vérifie si les données du client contenues dans le
     * JSONObject entré en paramètre sont valides i.e. que les valeurs dans
     * les clés client, contrat, et mois sont valides. Retourne true si les
     * données sont valides, false sinon
     *
     * @param jsonObject le JSONObject qui contient les données du client à
     *                   valider
     *
     * @return true si les valeurs dans les clés client, contrat et mois du
     *         JSONObject entré en paramètre sont valides, false sinon
     *
     * @throws JSONException si les clés sont invalides
     */
    public static boolean sontDonneesClientValide(JSONObject jsonObject){
        String dossier   = jsonObject.getString("dossier");
        String mois = jsonObject.getString("mois");

        return getNbCles(jsonObject) == 3 && dossier.matches(REGEX_DOSSIER)
                &&  mois.matches(REGEX_MOIS);
    }

    /**
     * Cette méthode vérifie si toutes les réclamations de la liste de
     * réclamations de l'objet Json entré en paramètre sont valides. Retourne
     * true si toutes les réclamations de la liste sont valides, false sinon.
     *
     * @param jsonObject L'objet json qui contient la liste des réclamations
     *                   à valider
     *
     * @return true si toutes les réclamations de la liste sont valides, false
     *         sinon
     *
     * @throws JSONException si les clés sont invalides
     */
    public static boolean estListeReclamationsValide(JSONObject jsonObject) {
        JSONArray reclamations = jsonObject.getJSONArray("reclamations");
        JSONObject reclamation; //pour stocker une des réclamation de la liste
        boolean estListeValide = true;
        int compteur = 0;

        while (estListeValide && compteur < reclamations.size()){
            reclamation = (JSONObject) reclamations.get(compteur);
            estListeValide = estReclamationValide(
                    jsonObject.getString("mois"), reclamation);
            compteur++;
        }
        return estListeValide;
    }

    /**
     * Cette méthode vérifie si la réclamation entrée en paramètre est
     * valide, i.e qu'elle vérifie que les valeurs dans les clés soin, date
     * et montant sont valides. Retourne true si les valeurs contenues dans
     * les clés sont valides, false sinon.
     *
     * @param mois le mois pour valider la date de réclamation
     * @param reclamation la reclamation à valider
     *
     * @return true si la réclamation est valide, false sinon
     *
     * @throws JSONException si les clés sont invalides
     */
    public static boolean estReclamationValide(String mois,
                                               JSONObject reclamation){
        int soin = reclamation.getInt("soin");
        String date = reclamation.getString("date");
        String montantString = reclamation.getString("montant");
        double montant = Double.parseDouble(montantString.substring(0,
                montantString.length() - 1)
                        .replace(',', '.'));

        return getNbCles(reclamation) == 3 && estSoinValide(soin)
                && estDateValide(mois, date)
                && montantString.matches(REGEX_MONTANT)
                && estMontantValide(montant) ;
    }

    /**
     * Permet de valide que le montant est superieru a 0 et inferieur a 500.
     *
     * @param montant
     * @return
     */
    public static boolean estMontantValide(double montant) {

        return montant > 0 &&  montant <= 500;
    }

    /**
     *
     * @param jsonObject
     * @return
     */
    public static boolean estNbreSoinValide(JSONObject jsonObject){

        JSONArray reclamations = jsonObject.getJSONArray("reclamations");
        return verifierNbreSoin(creerMapDateOccurance(reclamations));

    }

    /**
     * Cette méthode crée une map associant les dates et leur occurances.
     * @param reclamations La liste de réclamation du client
     * @return  un map des dates et leur occurances.
     */
    private static Map<String, Integer> creerMapDateOccurance(
            JSONArray reclamations) {

        Map<String, Integer> mapDate = new HashMap<String, Integer>();
        for (int i = 0; i < reclamations.size(); i++) {
            JSONObject reclamation = (JSONObject) reclamations.get(i);
            String key = reclamation.getString("date");
            mapDate.put(key, mapDate.getOrDefault(key, 0) +1);
        }

        return mapDate;
    }
    /**
     * Cette méthode affiche un message d"erreur si le nombre
     * de soins d'une date excède 4.
     * @param mapDate un hashMap
     *
     * @return return un boolean
     */
    private static boolean verifierNbreSoin(Map<String, Integer> mapDate) {

        for (Map.Entry<String, Integer> entry : mapDate.entrySet() ){
            if (entry.getValue() > 4) {
                return false;
            }
        }
        return true;
    }

}
