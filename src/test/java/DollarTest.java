
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DollarTest {

    Dollar dollar1, dollar2, dollar3;

    @BeforeEach
    void setUp() {
        // Test du constructeur Dollar()
        dollar1 = new Dollar();
        // Test du constructeur Dollar(Double dollar)
        dollar2 = new Dollar(250.50);
        // Test du constructeur Dollar(String dollar)
        dollar3 = new Dollar("100.20$");
    }


    // Test de la methode getPartieEntier()
    @Test
    void getPartieEntierTest() {
        assertEquals(540,
                new Dollar("540.00$").getPartieEntier());
    }

    // Test de la methode getPartieFractionnaire()
    @Test
    void getPartieFractionnaireTest() {
        assertEquals(85,
                new Dollar("480.85$").getPartieFractionnaire());
    }

    // Test de la methode getCents()
    @Test
    void getCentsTest() {
        assertEquals(75060,
                new Dollar("750.60$").getCents());
    }



    // Test de la methode setDollar(int)
    @Test
    void dollar1Test() {
        dollar1.setDollar(12345);
        assertAll(
                () -> assertEquals(12345, dollar1.getCents()),
                () -> assertEquals(123, dollar1.getPartieEntier()),
                () -> assertEquals(45, dollar1.getPartieFractionnaire())
                , () -> assertEquals("123.45$", dollar1.centsToDollar())
        );
    }

    // Test du constructeur Dollar(Double dollar)
    @Test
    void dollar2Test() {
        assertAll(
                () -> assertEquals(25050, dollar2.getCents()),
                () -> assertEquals(250, dollar2.getPartieEntier()),
                () -> assertEquals(50, dollar2.getPartieFractionnaire())
                , () -> assertEquals("250.50$", dollar2.centsToDollar())
        );
    }

    // Test du constructeur Dollar(String dollar)
    @Test
    void dollar3Test() {
        assertAll(
                () -> assertEquals(10020, dollar3.getCents()),
                () -> assertEquals(100, dollar3.getPartieEntier()),
                () -> assertEquals(20, dollar3.getPartieFractionnaire())
                , () -> assertEquals("100.20$", dollar3.centsToDollar())
        );
    }
    @Test
    void tostring(){
        assertEquals("Dollar{" +
                "cents='"+ dollar2.getCents() + "'" +
                "partieEntier='"+ dollar2.getPartieEntier() + "'" +
                ", partieFractionnaire='"+ dollar2.getPartieFractionnaire() +"'" +
                "}",dollar2.toString());

    }

}

