
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ClientTest  {

    Client client1;
    ArrayList<Reclamation> listesDesReclamations =
            new ArrayList<>();


    @BeforeEach
    void setUp() {
        Reclamation reclamation1 = new Reclamation(100, "2021-01-11", "100.00$");
        Reclamation reclamation2 = new Reclamation(200, "2021-01-15", "200.00$");
        Reclamation reclamation3 = new Reclamation(500, "2021-01-20", "300.00$");

        listesDesReclamations.add(reclamation1);
        listesDesReclamations.add(reclamation2);
        listesDesReclamations.add(reclamation3);

        client1 = new Client("A123456", "2021-01",
                listesDesReclamations);


    }

    // Test des différentes méthodes
    // Test de la méthode getDossier()
    @Test
    void getDossierTest(){

        assertEquals("A123456", client1.getDossier());
    }


    // Test de la méthode getClient()
    @Test
    void getClientTest(){

        assertEquals("123456", client1.getClient());
    }

    // Test de la méthode getContrat()
    @Test
    void getContratTest(){

        assertEquals('A', client1.getContrat());
    }

    // Test de la méthode getMois()
    @Test
    void getMoisTest(){
        assertEquals("2021-01", client1.getMois());
    }

    // Test de la méthode getReclamations()
    @Test
    void getReclamationsTest(){
        assertEquals(listesDesReclamations, client1.getReclamations());
    }


    @Test
    void getTotal() { assertEquals("0" , client1.getTotal()); }

    @Test
    void addTotal() {
        client1.addTotal("15.78");
        assertEquals("15.78",client1.getTotal());
    }

    @Test
    void getIndexLimite() {

assertAll(
        () -> assertEquals(0 ,client1.getIndexLimite(listesDesReclamations.get(0).getSoin())),
        () -> assertEquals(2 ,client1.getIndexLimite(listesDesReclamations.get(1).getSoin())),
        () -> assertEquals(3 ,client1.getIndexLimite(listesDesReclamations.get(2).getSoin()))
    );}



    @Test
    void getLimiteMensuelle() {

        assertEquals(300.00,client1.getLimiteMensuelle()[4]);
    }
}