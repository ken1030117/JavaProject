import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReglesDAffairesTest {


    JSONObject test = new JSONObject();
    JSONObject objetErr8 = new JSONObject();
    JSONObject reclamationValide = new JSONObject();
    JSONObject reclamationValide2 = new JSONObject();
    JSONObject reclamationValide3 = new JSONObject();
    JSONObject reclamationValide4= new JSONObject();
    JSONObject reclamationValide5= new JSONObject();
    JSONObject reclamationNonVal = new JSONObject();
    JSONArray array = new JSONArray();
    Client client ;
    ArrayList<Reclamation> reclamations = new ArrayList<>();




    @BeforeEach
    void setUp() {

        reclamationNonVal.accumulate("date", "2021-01-11");
        reclamationNonVal.accumulate("montant", "234.00$");
        reclamationValide.accumulate("soin", 100);
        reclamationValide.accumulate("date", "2021-01-13");
        reclamationValide.accumulate("montant", "100.00$");
        reclamationValide2.accumulate("soin", 100);
        reclamationValide2.accumulate("date", "2021-01-13");
        reclamationValide2.accumulate("montant", "100.00$");
        reclamationValide3.accumulate("soin", 100);
        reclamationValide3.accumulate("date", "2021-01-13");
        reclamationValide3.accumulate("montant", "100.00$");
        reclamationValide4.accumulate("soin", 100);
        reclamationValide4.accumulate("date", "2021-01-13");
        reclamationValide4.accumulate("montant", "100.00$");
        reclamationValide5.accumulate("soin", 500);
        reclamationValide5.accumulate("date", "2021-01-13");
        reclamationValide5.accumulate("montant", "100.00$");


        array.add(reclamationValide);
        array.add(reclamationValide2);
        array.add(reclamationValide3);
        array.add(reclamationValide4);
        array.add(reclamationValide5);
        reclamations.add(new Reclamation(0,"2021-01-13","100$"));
        reclamations.add(new Reclamation(200,"2021-01-13","100$"));

        test.accumulate("dossier","A451236");
        test.accumulate("mois", "2021-01");
        test.accumulate("reclamations", array);
        client = new Client("B125784","01-2021" ,reclamations);
        ReglesDAffaires.updateLimite("600.00$",client,new Reclamation(100,"2021-01-13","1000$"));

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void inputToOutput() {

    }

    @Test
    void modifierJson() {
    }

    @Test
    void ajusterMontantsRemboursements() {
        ReglesDAffaires.ajusterMontantsRemboursements(client);
        assertAll(
                () -> assertEquals("40.00$", client.getReclamations().get(0).getMontant()),
                () -> assertEquals("100.00$", client.getReclamations().get(1).getMontant())
        );


    }

    @Test
    void calculerRemboursement() {

        assertEquals("40.00$",ReglesDAffaires.calculerRemboursement(client.getContrat(),
                client.getReclamations().get(0)));
    }

    @Test
    void calculRemboursement() {
        Taux taux = Contrat.getTaux(client.getContrat(), 100);

        assertEquals("50.00$" ,ReglesDAffaires.calculRemboursement(100.00,taux) );
    }

    @Test
    void msgErrRectoJson() {
        System.out.println(ReglesDAffaires.afficherMsgErr(test));
    }



    @Test
    void afficherAbsenceDonneeJson() {

        System.out.println(ReglesDAffaires.afficherMsgErr(objetErr8));


    }

    @Test
    void updateLimite() {
        assertEquals(-350, client.getLimiteMensuelle()[0]);
    }


    @Test
    void changerMontantTest() {

        assertEquals("250.00$",ReglesDAffaires.changerMontant(0,"600.00$",client));
    }




}