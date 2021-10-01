import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JSONTest {
    JSONObject objet = new JSONObject();
    JSONObject objet2 = new JSONObject();
    JSONObject reclamV1 = new JSONObject();
    JSONArray array = new JSONArray();
    ArrayList<Reclamation> listesDesReclamations =
            new ArrayList<>();

    Client client1 = new Client("A123456",
            "2021-01", listesDesReclamations);


    @BeforeEach
    void setUp() {


        reclamV1.accumulate("soin", 100);
        reclamV1.accumulate("date", "2021-01-11");
        reclamV1.accumulate("montant", "100.00$");
        array.add(reclamV1);
        objet.accumulate("dossier", "A123456");
        objet.accumulate("mois", "2021-01");
        objet.accumulate("reclamations", array);
        objet2.accumulate("dossier", "A123456");
        objet2.accumulate("mois", "2021-01");
        objet2.accumulate("remboursements", array);
        Reclamation reclamation1 = new Reclamation(100, "2021-01-11", "100.00$");
        listesDesReclamations.add(reclamation1);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void jsonToClient() {
        assertEquals("A123456", JSON.jsonToClient(objet).getDossier());
    }

    @Test
    void jsonToClient2() {
        assertEquals("2021-01", JSON.jsonToClient(objet).getMois());
    }

    @Test
    void jsonToClient3() {
        assertEquals(listesDesReclamations, JSON.jsonToClient(objet).getReclamations());
    }

    @Test
    void clientToJson() {

    }


    @Test
    void msgErrToJson() {
    }
}