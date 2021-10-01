import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadWriteFichierJsonTest {

    JSONObject objet = new JSONObject();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fichierToJson() {
    }


    @Test
    void jsonToFichier() {
        assertThrows(InvalideDataException.class, () -> ReadWriteFichierJson.jsonToFichier("", objet));
    }

}