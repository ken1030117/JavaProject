import net.sf.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TauxTest {

    Taux taux = new Taux("Massothérapie", 0.50, 40);

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTaux() {
        assertEquals(0.50 , taux.getTaux());

    }

    @Test
    void getMax() {
        assertEquals(40 , taux.getMax());

    }

    @Test
    void testEquals() {
    }

    @Test
    void testToString() {

        assertEquals("Taux{" +
                "categorie='" + "Massothérapie" + '\'' +
                ", taux=" + taux.getTaux() +
                ", max=" + taux.getMax() +
                '}',taux.toString());
    }
}