

import net.sf.json.*;
import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.lang.System.exit;


public class ReadWriteFichierJson {

    /**
     * Cette classe contient des méthodes permettant de lire un fichier Json et
     * de le transformer en JSONObject et d'écrire un fichier Json à partir d'un
     * JSONObject.
     */


    //-----------
    //CONSTANTES
    //-----------

    private static final String ERR_INPUT = "Le fichier d'entrée n'existe pas";
    private static final String ERR_JSON = "Le fichier JSON n'est pas valide";
    private static final String ERR_OUTPUT = "Le fichier de sortie n'existe " +
            "pas";

    //------------------
    //MÉTHODES PUBLIQUES
    //------------------

    /**
     * Cette méthode transforme un fichier json dont le chemin est donné en
     * paramètre en JSONObject
     *
     * @param cheminFichier le chemin du fichier Json
     * @return un JSONObject contenant les clés et valeurs du fichier
     * @throws InvalideDataException si le fichier est null, s'il n'existe
     *                               pas, ou s'il n'a pas le bon format
     */
    public static JSONObject FichierToJson (String cheminFichier)
            throws InvalideDataException {
        String jsontext;
        JSONObject jsonObject = null;
        try {
            jsontext = IOUtils.toString(new FileInputStream(cheminFichier),
                    "UTF-8");
            jsonObject = (JSONObject) JSONSerializer.toJSON(jsontext);
        } catch (FileNotFoundException e) {
            System.out.println(ERR_INPUT);
            exit(-1);
        } catch (JSONException | IOException e) {
            throw new InvalideDataException(ERR_JSON);
        }
        return jsonObject;
    }

    /**
     * Cette méthode écrit un fichier Json au chemin entré en paramètre avec
     * l'objet json entré en paramètre.
     * @param cheminFichierSortie le chemin du fichier de sortie
     * @param jsonObject l'objet Json à transcrire dans le fichier de sortie
     * @throws InvalideDataException si le chemin est invalide, ou si l'objet
     *                               json est invalide
     */
    public static void jsonToFichier (String cheminFichierSortie,
                                      JSONObject jsonObject)
            throws InvalideDataException {

        try {
            IOUtils.write(jsonObject.toString(2),
                    new FileOutputStream(cheminFichierSortie),
                    "UTF-8");
        } catch (FileNotFoundException e){
            throw new InvalideDataException(ERR_OUTPUT);
        } catch (JSONException | IOException e) {
            throw new InvalideDataException(ERR_JSON);
        }
    }

}
