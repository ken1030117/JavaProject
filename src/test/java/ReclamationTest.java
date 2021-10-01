import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ReclamationTest {

    Reclamation reclamation;
    Reclamation copieCat;


    @BeforeEach
    void setUp() {
        reclamation = new Reclamation(100, "2021-01-11",
                "100.00$");
        copieCat = new Reclamation(100, "2021-01-11",
                "100.00$");

    }

    @AfterEach
    void tearDown() {
    }

    // Test des différentes méthodes
    // Test de la methode getSoin()
    @Test
    void getSoin(){

        assertEquals(100, reclamation.getSoin());
    }

    // Test de la methode getDate()
    @Test
    void getDate(){

        assertEquals("2021-01-11", reclamation.getDate());
    }

    // Test de la methode getMontant()
    @Test
    void getMontant() {

        assertEquals("100.00$", reclamation.getMontant());
    }

    // Test de la methode setMontant(String montant)
    @Test
    void setMontantFormatString(){

        reclamation.setMontant("150.25$");
        assertEquals("150.25$", reclamation.getMontant());
    }

    //Test de la methode setMontant(Double montant)
    @Test
    void setMontantFormatDouble(){

        reclamation.setMontant(200.25);
        assertEquals("200.25$", reclamation.getMontant());
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
        assertTrue(reclamation.equals(copieCat));
    }


}