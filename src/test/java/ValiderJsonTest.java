
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValiderJsonTest {

    JSONObject objet = new JSONObject();
    JSONObject reclamationValide = new JSONObject();

    JSONArray array = new JSONArray();

    @BeforeEach
    void setUp() {
        reclamationValide.accumulate("soin", 100);
        reclamationValide.accumulate("date", "2021-01-11");
        reclamationValide.accumulate("montant", "234.00$");
        array.add(reclamationValide);

        objet.accumulate("dossier", "A100323");
        objet.accumulate("mois", "2021-01");
        objet.accumulate("reclamations", array);

    }

    @Test
    void estJsonValide() {
        Assertions.assertTrue(ValiderJson.estJsonValide(objet));
    }


    @Test
    void estListeReclamationsValide() {
        Assertions.assertTrue(ValiderJson.estListeReclamationsValide(objet));
    }

    @Test
    void estSoinValide() {
        Assertions.assertTrue(ValiderJson.estSoinValide(
                reclamationValide.getInt("soin")));
    }

    @Test
    void estDateValide() {
        Assertions.assertTrue(ValiderJson.estDateValide("2021-01",
                reclamationValide.getString("date")));
    }


    @Test
    void sontDonneesClientValide() {
        Assertions.assertTrue(ValiderJson.sontDonneesClientValide(objet));
    }


    @Test
    void estReclamationValide() {
        Assertions.assertTrue(ValiderJson.estReclamationValide("2021-01",
                reclamationValide));
    }

}