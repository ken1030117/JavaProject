import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ReglesDAffaires {

    /**
     * Cette classe contient des méthodes permettant de modifier un objet json
     * provenant d'un fichier de réclamations afin de calculer les montants de
     * remboursements et de retourner un fichier json de sortie correspondant aux
     * règles d'affaires spécifiées par la compagnie d'assurance.
     */

    private static String directory =
            System.getProperty("user.dir") + File.separator + "src"
                    + File.separator + "main" + File.separator + "resources";
    private static String filename = directory + File.separator + "stats.json";


    /**
     * Cette méthode prend le fichier de reclamations json donné en paramètre,
     * applique les règles d'affaires requise (calcule les remboursements de
     * chaque réclamation) et retourne un fichier json de sortie contenant
     * les informations des remboursement du client à l'endroit spécifiée par
     * le outputPath donné en paramètre. Si le fichier d'entré n'a pas le bon
     * format ou qu'il contient des données invalides, un fichier json de
     * sortie contenant la clé "message" et la valeur "Données invalides"
     * sera renvoyé dans le output.
     *
     * @param inputPath  le chemin du fichier json de reclamations
     * @param outputPath le chemin dans lequel le fichier de remboursement
     *                   sera sauvegardé
     * @param prediction indique si l'option p est appelée.
     *
     * @throws InvalideDataException si le fichier de sortie n'est pas valide
     */
    public static void inputToOutput(String inputPath, String outputPath,
                                     Boolean prediction)
            throws InvalideDataException {
        JSONObject jsonObj;
        Stats stat = new Stats();
        try {
            if (!prediction) {stat = statCheckFileAndInitialise(stat);}
            jsonObj = ReadWriteFichierJson.FichierToJson(inputPath);
            jsonObj = modifierJson(jsonObj, stat, prediction);
            ReadWriteFichierJson.jsonToFichier(outputPath, jsonObj);
        } catch (InvalideDataException e) {
            if (!prediction) {stat.updateNombreDemandeRejetee();}
            ReadWriteFichierJson.jsonToFichier(outputPath,
                    JSON.msgErrToJson(JSON.DONNEES_INVALIDES));
        }
        ReadWriteFichierJson.jsonToFichier(filename, JSON.statsToJson(stat));
    }

    //-------------------------------------------
    //MÉTHODES PRIVÉES (PROTECTED POUR LES TESTS)
    //-------------------------------------------

    /**
     * Cette méthode prend un objet json contenant un fichier de réclamations
     * d'un client et retourne l'objet json des remboursements prêt à être
     * écrit dans un fichier. Si le json de réclamations est invalide,
     * l'objet json de sortie contiendra la clé "message" contenant la valeur
     * "Données invalides".
     *
     * @param jsonObject l'objet json contenant les valeurs de la reclamation
     *                   d'un client
     *
     * @return l'objet json contenant les remboursements associés à l'objet
     * json de réclamation entré en paramètre, prêt à être écrit dans
     * un fichier.
     */
    protected static JSONObject modifierJson(JSONObject jsonObject,
                                             Stats stat, Boolean prediction)
            throws InvalideDataException {
        Client client;
        JSONObject newJsonObj;
        if (ValiderJson.estJsonValide(jsonObject)) {
            client = JSON.jsonToClient(jsonObject);
            ajusterMontantsRemboursements(client);
            if (!prediction) {stat.calculerStatReclamationEtSoin(client, stat);}
            newJsonObj = JSON.clientToJson(client);
            newJsonObj = verifieLimite(newJsonObj,client);
        } else {
            if (!prediction) {stat.updateNombreDemandeRejetee();}
            newJsonObj = afficherMsgErr(jsonObject);
        }
        return newJsonObj;
    }

    protected static Stats statCheckFileAndInitialise(Stats stat) {

        File file = new File(filename);
        if (file.exists() && file.isFile()) {
            try {
                stat = JSON.jsonToStats(
                        ReadWriteFichierJson.FichierToJson(filename));
            } catch (InvalideDataException e) { System.out.println(e); }
        } else {
            try (FileWriter fw = new FileWriter(file)) {
                fw.flush();
            } catch (IOException e) { System.out.println(e); }
        }
        return stat;
    }

    /**
     * Cette méthode affiche un message d"erreur lorsque les informations
     * sur le fichier JSON entrée est invalide..
     *
     * @param jsonObject l'objet json contenant les valeurs de la reclamation
     *                   d'un client
     * @return l'objet json contenant le message d'erreur.
     */

    public static JSONObject afficherMsgErr(JSONObject jsonObject) {
        JSONObject newJsonObjErr;
        if (afficherMsgErrCles(jsonObject) == null) {
            newJsonObjErr = afficherAbsenceDonneeJson(jsonObject);
        } else {
            newJsonObjErr = afficherMsgErrCles(jsonObject);
        }
        return newJsonObjErr;
    }

    /**
     * Cette méthode affiche un message d'erreur
     * lorsque le nombre de clés du JSON est plus élevé que trois,
     * Elle vérifie aussi le nombre de clés pour chaque réclamations
     * si la clé réclamation est présente.
     *
     * @param jsonObject l'objet json contenant les valeurs de la reclamation
     *                   d'un client
     * @return l'objet json contenant le message d'erreur
     * pour la clé de l'objet manquant.
     */

    private static JSONObject afficherMsgErrCles(JSONObject jsonObject) {
        JSONObject newJsonObjErr = null;
        if (ValiderJson.getNbCles(jsonObject) > 3) {
            newJsonObjErr = JSON.msgErrToJson(JSON.NBCLES_ERR);
        } else if (jsonObject.has("reclamations")) {
            JSONArray reclamations = jsonObject.getJSONArray("reclamations");
            for (int i = 0; i < reclamations.size(); i++) {
                JSONObject reclamation = (JSONObject) reclamations.get(i);
                if (ValiderJson.getNbCles(reclamation) > 3) {
                    newJsonObjErr = JSON.msgErrToJson(JSON.NBCLES_ERR);
                }
            }
        }
        return newJsonObjErr;
    }

    /**
     * Cette méthode affiche un message d"erreur selon l'absence d'une clé
     * dans le l'objet JSON.
     *
     * @param jsonObject l'objet json contenant les valeurs de la reclamation
     *                   d'un client
     * @return l'objet json contenant le message d'erreur
     * pour la clé de l'objet manquant.
     */

    private static JSONObject afficherAbsenceDonneeJson
    (JSONObject jsonObject) {
        JSONObject newJsonObjErr;
        if (!jsonObject.has("dossier")) {
            newJsonObjErr = JSON.msgErrToJson(JSON.DOSSIER_INTROUVABLE);
        } else if (!jsonObject.has("mois")) {
            newJsonObjErr = JSON.msgErrToJson(JSON.MOIS_INTROUVABLE);
        } else if (!jsonObject.has("reclamations")) {
            newJsonObjErr = JSON.msgErrToJson(JSON.RECLAMATION_INTROUVABLE);
        } else {
            newJsonObjErr = afficherAbsenceDonneeRec(jsonObject);
            if (newJsonObjErr == null) {
                newJsonObjErr = msgErrtoJson(jsonObject);
            }
        }
        return newJsonObjErr;
    }

    /**
     * Cette méthode affiche un message d"erreur selon l'absence d'une clé
     * dans le l'Array JSON réclamations
     *
     * @param jsonObject l'objet json contenant les valeurs de la reclamation
     *                   d'un client
     * @return l'objet json contenant le message d'erreur pour la clé de l'objet manquant.
     */
    private static JSONObject afficherAbsenceDonneeRec(JSONObject jsonObject) {
        JSONObject newJsonObjErr = null;
        JSONArray reclamations = jsonObject.getJSONArray("reclamations");
        for (int i = 0; i < reclamations.size() && newJsonObjErr == null; i++) {
            JSONObject reclamation = (JSONObject) reclamations.get(i);
            if (!reclamation.has("soin")) {
                newJsonObjErr = JSON.msgErrToJson(JSON.SOIN_INTROUVABLE);
            } else if (!reclamation.has("date")) {
                newJsonObjErr = JSON.msgErrToJson(JSON.DATE_INTROUVABLE);
            } else if (!reclamation.has("montant")) {
                newJsonObjErr = JSON.msgErrToJson(JSON.MONTANT_INTROUVABLE);
            }
        }
        return newJsonObjErr;
    }


    /**
     * Cette méthode affiche un message d"erreur selon l'erreur
     * de format du ficher JSON.
     *
     * @param jsonObject l'objet json contenant les valeurs de la reclamation
     *                   d'un client
     * @return l'objet json contenant le message d'erreur pour les données mal formatés.
     */
    private static JSONObject msgErrtoJson(JSONObject jsonObject) {
        JSONObject newJsonObjErr;
        JSONArray reclamations = jsonObject.getJSONArray("reclamations");

        if (!jsonObject.getString("dossier").matches("^[A-E]\\d{6}$")) {
            newJsonObjErr = JSON.msgErrToJson(JSON.DOSSIER_INVALIDE);
        } else if ((!jsonObject.getString("mois").matches("^((\\d){4})-((0[1-9])|(1[0-2]))$"))) {
            newJsonObjErr = JSON.msgErrToJson(JSON.MOIS_INVALIDE);
        } else {
            newJsonObjErr = msgErrRectoJson(jsonObject, reclamations);
        }
        return newJsonObjErr;
    }

    /**
     * Cette méthode affiche un message d"erreur selon l'erreur
     * de validation de  la clé réclamation.
     *
     * @param jsonObject   l'objet json contenant les valeurs de la reclamation
     *                     d'un client
     * @param reclamations La liste de réclamation du client
     * @return l'objet json contenant le message d'erreur pour les données non valides.
     */
    private static JSONObject msgErrRectoJson
    (JSONObject jsonObject, JSONArray reclamations) {
        JSONObject newJsonObjErr = null;
        for (int i = 0; i < reclamations.size() && newJsonObjErr == null; i++) {
            JSONObject reclamation = (JSONObject) reclamations.get(i);
            if (!ValiderJson.estSoinValide(reclamation.getInt("soin"))) {
                newJsonObjErr = JSON.msgErrToJson(JSON.SOIN_INVALIDE);
            } else if (!ValiderJson.estDateValide(jsonObject.getString("mois"), reclamation.getString("date"))) {
                newJsonObjErr = JSON.msgErrToJson(JSON.DATE_INVALIDE);
            } else if (!reclamation.getString("montant").matches("(^\\d+[,.]\\d{2})\\$$")) {
                newJsonObjErr = JSON.msgErrToJson(JSON.MONTANT_INVALIDE);
            } else {
                newJsonObjErr = msgErrRecMontantToJson(reclamation,
                        reclamations);
            }
        }
        return newJsonObjErr;
    }

    /**
     * Cette méthode affiche un message d"erreur selon l'erreur
     * de validation de  la clé réclamation.
     *
     * @param jsonObject   l'objet json contenant les valeurs de la reclamation
     *                     d'un client
     * @return l'objet json contenant le message d'erreur pour le montant non valide. .
     */

    private static JSONObject msgErrRecMontantToJson(JSONObject jsonObject,
                                                     JSONArray reclamations) {
        JSONObject newJsonObjErr = null;
        Dollar montantCents = new Dollar(jsonObject.getString("montant"));
        if (montantCents.getCents() < 1) {
            newJsonObjErr = JSON.msgErrToJson(JSON.MONTANT_INVALIDE2);
        } else if (montantCents.getCents() > 50000) {
            newJsonObjErr = JSON.msgErrToJson(JSON.MONTANT_INVALIDE3);
        } else {
            Map<String, Integer> mapDate = creerMapDateOccurance(reclamations);
            newJsonObjErr = verifierNbreSoin(mapDate);
        }

        return newJsonObjErr;
    }

    /**
     * Cette méthode crée une map associant les dates et leur occurances.
     * @param reclamations La liste de réclamation du client
     * @return  un map des dates et leur occurances.
     */
    private static Map<String, Integer> creerMapDateOccurance(
            JSONArray reclamations) {

        HashMap<String, Integer> mapDate = new HashMap<String, Integer>();
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
     * @return l'objet json contenant le message d'erreur pour les nombre de
     * soins
     * trop élevé..
     */
    private static JSONObject verifierNbreSoin(Map<String, Integer> mapDate) {
        JSONObject jsonObject = null;
        for (Map.Entry<String, Integer> entry : mapDate.entrySet() ){
            if (entry.getValue() > 4) {
                jsonObject = JSON.msgErrToJson(JSON.SOIN_INVALIDE2);
                return jsonObject;
            }
        }
        return jsonObject;
    }


    /**
     * Cette méthode remplace le montant des réclamations du Client entré en
     * paramètre par le montant des remboursements approuvés par la compagnie
     * d'assurance selon le contrat du Client et le soin pour lequel il y a
     * une réclamation
     *
     * @param client le client pour lequel on veut calculer/modifier les
     *               montants de réclamations par les montants de
     *               remboursements.
     */
    protected static void ajusterMontantsRemboursements(Client client) {
        String montantRembourser;
        Reclamation reclamation;
        char contrat = client.getContrat();

        for (int i = 0; i < client.getReclamations().size(); i++) {
            reclamation = client.getReclamations().get(i);
            montantRembourser = calculerRemboursement(contrat, reclamation);
            updateLimite( montantRembourser, client,reclamation);
            montantRembourser = changerMontant(client.getIndexLimite(reclamation.getSoin()), montantRembourser, client);
            client.addTotal(montantRembourser.replace("$", "0"));
            reclamation.setMontant(montantRembourser);
        }
    }

    /**
     * Cette méthode calcule le montant du remboursement selon le contrat
     * donné en paramètre pour la reclamation donnée en paramètre.
     * <p>
     * ANTÉCÉDENT:les valeurs entrées en paramètre doivent avoir été
     * préalablement validées
     *
     * @param contrat     le contrat du client (A,B,C,D ou E)
     * @param reclamation la réclamation pour laquelle on veut calculer le
     *                    montant
     * @return le remboursement alloué à cette réclamation
     */
    protected static String calculerRemboursement(char contrat,
                                                  Reclamation reclamation) {
        String montantString = reclamation.getMontant();
        Double montant = Double.parseDouble(
                montantString.substring(0, montantString.length() - 1)
                        .replace(',', '.'));
        Taux taux = Contrat.getTaux(contrat, reclamation.getSoin());


        return calculRemboursement(montant, taux);
    }

    /**
     * Cette méthode calcule un montant de remboursement selon le montant et le
     * Taux entré en paramètre. Elle tient compte du maximum contenu dans la
     * variable taux et retournera la plus petite valeur entre le
     * remboursement calculé avec le taux de remboursement et le maximum de
     * remboursement permis.
     * <p>
     * ANTÉCÉDENT:les valeurs doivent avoir été préalablement validées
     *
     * @param montant le montant pour lequel on veut calculer un remboursement
     * @param taux    le Taux avec lequel on veut calculer le remboursement
     *                (l'objet Taux contient le taux de remboursement ainsi que
     *                le maximum alloué)
     * @return le remboursement alloué
     */
    protected static String calculRemboursement(Double montant, Taux taux) {

        Dollar remboursement = new Dollar(montant * taux.getTaux());

        if (taux.getMax() != 0) {
            Dollar max = new Dollar(taux.getMax());
            remboursement.setDollar(
                    Math.min(remboursement.getCents(), max.getCents()));
        }

        return remboursement.centsToDollar();
    }

    /**
     * permet de mettre a jour la limite mensuelle du
     * type de soin selectionné
     * @param coup      prix du soin remboursé
     * @param client    client qui se fait rembourser
     * @param reclamation   reclamaion du soin
     */
    public static void updateLimite( String coup, Client client,Reclamation reclamation ) {
        int indexlimite = client.getIndexLimite(reclamation.getSoin());
        if (indexlimite != 5) {
            client.getLimiteMensuelle()[indexlimite] -= Double.parseDouble(coup.replace("$", ""));
        }else {
            indexlimite = client.getIndexLimiteMax(reclamation.getSoin());
            if(indexlimite != 5){
                client.getLimiteMaxMensuelle()[indexlimite] -= Double.parseDouble(coup.replace("$", ""));
                if(client.getLimiteMaxMensuelle()[indexlimite] < 0 ){
                    client.setaDepasserLimite(true);
                }
            }
        }
    }

    /**
     * cette methode permet de savoir si le client a depasser une limite mensuel pour un type de soin.
     * @param jsonObject le fichier json ou s'ecrira le fichier d'erreur
     * @param client le client qui se fait rembourser
     * @return
     */
    public static JSONObject verifieLimite(JSONObject jsonObject, Client client){

        if(client.getADepasserLimite()){
            JSONObject temp =  JSON.msgErrToJson(JSON.MONTANT_INVALIDE3);
            jsonObject = temp ;

        }
        return jsonObject;
    }

    /**
     * modifie le remboursements d'un soin si celui ci depasse la limite actuelle
     * @param indexlimite   index du type de soin
     * @param coup          le coup du soin
     * @param client        le client qui se fait rembourser
     * @return
     */

    public static String changerMontant(int indexlimite, String coup, Client client) {

        double temporaire;
        if(indexlimite !=5) {
            if (client.getLimiteMensuelle()[indexlimite] < 0) {
                temporaire = Double.parseDouble(coup.replace("$", ""));
                temporaire += client.getLimiteMensuelle()[indexlimite];
                coup = String.valueOf(temporaire);
                client.getLimiteMensuelle()[indexlimite] = 0;
            }
            coup = new Dollar(coup).centsToDollar();

        }
        return coup;
    }

}

