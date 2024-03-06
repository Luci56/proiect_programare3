package proiectp3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Clasa care contine testele unitare ale aplicatiei
 * 
 */

class StocTest {

	@Test
	void adaugaProdus() {
        Stoc Stoc = new Stoc();
        Produs produs = new Produs("jordan 4", 15, "Adidasi baschet", 300);
        Stoc.adaugaProdus(produs);
        assertTrue(Stoc.cauta("jordan 4").contains(produs));
    }
	
    @Test
    void stergeProdus() {
        Stoc Stoc = new Stoc();
        Produs produs = new Produs("Adidas ultraboost",20,  "Adidasi alergat", 200);
        Stoc.adaugaProdus(produs);
        Stoc.stergeProdus("Adidas ultraboost");
        assertFalse(Stoc.cauta("Adidas ultraboost").contains(produs));
    }

	
    @Test
    void actualizeazaStoc( ) {
    	Stoc Stoc = new Stoc();
    	Produs produs = new Produs("Nike Spizake", 30, "Adidasi lifestyle", 250);
    	Stoc.adaugaProdus(produs);
    	Stoc.actualizeazaStoc(10, "Nike Spizake");
    	assertEquals(10,Stoc.cauta("Nike Spizake").get(0).getCantitate());
    }
	
    @Test
    void cauta() {
        Stoc Stoc = new Stoc();
        Produs produs1 = new Produs("Nike AirMax",30, "Adidasi baschet", 150);
        Produs produs2 = new Produs("Nike Air Force 1",40, "Adidasi lifestyle", 100);
        Stoc.adaugaProdus(produs1);
        Stoc.adaugaProdus(produs2);

        assertEquals(2, Stoc.cauta("").size());
        assertEquals(1, Stoc.cauta("Nike Air Force 1").size());
        assertEquals(1, Stoc.cauta("Adidasi lifestyle").size());
    }
    
    @Test
    void afiseazaToateArticolele() {
        Stoc Stoc = new Stoc();
        Produs produs1 = new Produs("Adidas NMD",50, "Adidasi alergat", 120);
        Produs produs2 = new Produs("Nike Kyrie 3",60, "Bucatarie", 80);
        Stoc.adaugaProdus(produs1);
        Stoc.adaugaProdus(produs2);

        assertEquals(2, Stoc.afiseazaToateProdusele().size());
    }
    
    @Test
    void afiseazaProduseleCuCantitateScazuta() {
    	Stoc Stoc = new Stoc();
    	Produs produs = new Produs("Nike Lebron 7",5,"Adidasi de baschet",150);
    	Stoc.adaugaProdus(produs);
    	assertEquals(1,Stoc.afiseazaProduseleCuCantitateScazuta(6).size());
    }
    
}
