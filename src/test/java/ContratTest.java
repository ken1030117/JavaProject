import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContratTest extends Contrat {

    //pour initialiser les valeurs attendues
    Taux TAUXA0;
    Taux TAUXA334;
    Taux TAUXA7;

    Taux TAUXB0;
    Taux TAUXB334;
    Taux TAUXB7;

    Taux TAUXC0;
    Taux TAUXC334;
    Taux TAUXC7;

    Taux TAUXD0;
    Taux TAUXD334;
    Taux TAUXD7;

    Taux TAUXE0;

    @BeforeEach
    void setUp() {
        TAUXA0 = new Taux("Kinésithérapie", 0);
        TAUXA334 = new Taux("Soins dentaires", 0);
        TAUXA7 = new Taux("Orthophonie, ergothérapie", 0);

        TAUXB0 = new Taux("Massothérapie", 0.5, 40);
        TAUXB334 = new Taux("Soins dentaires", 0.5);
        TAUXB7 = new Taux("Orthophonie, ergothérapie", 0.7);

        TAUXC0 = new Taux("Médecin généraliste privé", 0.90);
        TAUXC334 = new Taux("Soins dentaires", 0.9);
        TAUXC7 = new Taux("Orthophonie, ergothérapie", 0.9);

        TAUXD0 = new Taux("Massothérapie", 1, 85);
        TAUXD334 = new Taux("Soins dentaires", 1);
        TAUXD7 = new Taux("Orthophonie, ergothérapie", 1,
                90);

        TAUXE0 = new Taux("Massothérapie",  .15);
    }

    //tests pour contrat A, valeur de soin valide
    @Test
    void getTauxA0() {
        Taux taux = getTaux('A',150);
        Assertions.assertEquals(TAUXA0,taux);
    }

    @Test
    void getTauxA334() {
        Taux taux = getTaux('A',334);
        Assertions.assertEquals(TAUXA334,taux);
    }

    @Test
    void getTauxA700() {
        Taux taux = getTaux('A',700);
        Assertions.assertEquals(TAUXA7,taux);
    }

    //test pour contrat B valeurs valides
    @Test
    void getTauxB0() {
        Taux taux = getTaux('B',0);
        Assertions.assertEquals(TAUXB0,taux);
    }

    @Test
    void getTauxB334() {
        Taux taux = getTaux('B',334);
        Assertions.assertEquals(TAUXB334,taux);
    }

    @Test
    void getTauxB700() {
        Taux taux = getTaux('B',700);
        Assertions.assertEquals(TAUXB7,taux);
    }

    //test pour contrat C valeurs valides
    @Test
    void getTauxC0() {
        Taux taux = getTaux('C',175);
        Assertions.assertEquals(TAUXC0,taux);
    }

    @Test
    void getTauxC334() {
        Taux taux = getTaux('C',334);
        Assertions.assertEquals(TAUXC334,taux);
    }

    @Test
    void getTauxC700() {
        Taux taux = getTaux('C',700);
        Assertions.assertEquals(TAUXC7,taux);
    }

    //test pour contrat D valeurs valides
    @Test
    void getTauxD0() {
        Taux taux = getTaux('D',0);
        Assertions.assertEquals(TAUXD0,taux);
    }

    @Test
    void getTauxD334() {
        Taux taux = getTaux('D',334);
        Assertions.assertEquals(TAUXD334,taux);
    }

    @Test
    void getTauxD700() {
        Taux taux4 = getTaux('D',700);
        Assertions.assertEquals(TAUXD7,taux4);
    }
    //test pour contrat E valeurs valide
    @Test
    void getTauxE0() {
        Taux taux = getTaux('E',0);
        Assertions.assertEquals(TAUXE0,taux);
    }

    //tests valeurs invalides
    @Test
    void getTauxEspace0() {
        Taux taux = getTaux(' ',0);
        assertNull(taux);
    }

    @Test
    void getTauxASoinNeg() {
        Taux taux = getTaux('A',-1);
        assertNull(taux);

    }

    @Test
    void getTauxA800() {
        Taux taux = getTaux('A',800);
        assertNull(taux);
    }

    @Test
    void getTauxA8() {
        Taux taux = getTaux('A',8);
        assertNull(taux);
    }

    //tests pour obtenir l'index
    @Test
    void getIndexContratA() {
        char contrat = 'A';
        assertEquals(0,getIndexContrat(contrat));
    }

    @Test
    void getIndexContratB() {
        char contrat = 'B';
        assertEquals(1,getIndexContrat(contrat));
    }

    @Test
    void getIndexContratC() {
        char contrat = 'C';
        assertEquals(2,getIndexContrat(contrat));
    }

    @Test
    void getIndexContratD() {
        char contrat = 'D';
        assertEquals(3,getIndexContrat(contrat));
    }

    @Test
    void getIndexContratE() {
        char contrat = 'E';
        assertEquals(4,getIndexContrat(contrat));
    }


    //tests pour valeurs invalides
    @Test
    void getIndexContrat0() {
        char contrat = '0';
        assertThrows(IllegalArgumentException.class,
                () -> getIndexContrat(contrat));
    }

    @Test
    void getIndexContratEspace() {
        char contrat = ' ';
        assertThrows(IllegalArgumentException.class,
                () -> getIndexContrat(contrat));
    }

    @Test
    void getIndexContratChaineVide() {
        char contrat = '\0';
        assertThrows(IllegalArgumentException.class,
                () -> getIndexContrat(contrat));
    }

    @Test
    void getIndexContratCharacterQuelconques() {
        char contrat = 'X';
        assertThrows(IllegalArgumentException.class,
                () -> getIndexContrat(contrat));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTaux() {
    }

    @Test
    void getIndexContrat() {
    }

}