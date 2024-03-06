package proiectp3;

import java.util.ArrayList;

/**
 * Clasa pentru gestionarea stocului magazinului
 * @author Sturzu Stefan Lucian
 * 
 */

public class Stoc {
	private ArrayList<Produs> produse;
	
	/**
	 * Constructor fara parametrii pentru initializarea
	 * unui sistem de management al stocului de produse
	 */
	
	public Stoc(){
		this.produse = new ArrayList<Produs>();
	}
	
	/**
	 * Metoda pentru adaugarea unui produs in stoc
	 * 
	 * @param produs Produsul care urmeaza sa fie adaugat in stoc
	 */
	public void adaugaProdus(Produs produs) {
		this.produse.add(produs);
	}
	
	/**
	 * Metoda pentru stergerea unui produs din stocul magazinului
	 * 
	 * @param nume Numele produsului care va fi sters
	 */
    public void stergeProdus(String nume) {
    	for (Produs produs : produse) {
    	    if (produs.getNume().equals(nume)) {
    	        produse.remove(produs);
    	        break;
    	    }
    	}
    }
	
	/**
	 * Metoda pentru actualizarea cantitatii unui produs
	 * 
	 * @param nume Numele produsului
	 * @param cantitateActualizata = Noua cantitate a produsului
	 */
	
	public void actualizeazaStoc(int cantitateActualizata , String nume ) {
		 for (int i = 0; i < produse.size(); i++) {
		        Produs produs = produse.get(i);
		        if (produs.getNume().equals(nume)) {
		            produs.setCantitate(cantitateActualizata);
		            break;  
			
		        }
		 }
	}
	
    /**
     * Metoda pentru cautarea dupa nume sau dupa categorie
     * 
     * @param conditie Conditie de cautare (nume sau categorie)
     * @return Lista de produse care corespund conditiei de cautare
     */
    public ArrayList<Produs> cauta(String conditie) {
        ArrayList<Produs> rezultate = new ArrayList<Produs>();
        for (Produs produs : produse) {
            if (produs.getNume().contains(conditie) || produs.getCategorie().contains(conditie)) {
                rezultate.add(produs);
            }
        }
        return rezultate;
    }
	
    /**
     * Metoda pentru afisarea tuturor produselor din stoc
     * @return Lista tuturor produselor din stoc
     */
    public ArrayList<Produs> afiseazaToateProdusele() {
    	ArrayList<Produs> rezultate = new ArrayList<Produs>();
    	for (Produs produs : produse) {
    		rezultate.add(produs);
        }
    	return rezultate;
    }
    
    /**
     * Metoda pentru afisarea produselor cu o cantitate scazuta
     * 
     * @param  cantitateScz Cantitatea minima 
     * @return Lista produselor cu cantitate scazuta
     */
    public ArrayList<Produs> afiseazaProduseleCuCantitateScazuta(int cantitateScz) {
    	ArrayList<Produs> rezultate = new ArrayList<Produs>();
    	for (Produs produs : produse) {
            if (produs.getCantitate() < cantitateScz) {
            	rezultate.add(produs);
            }
        }
    	return rezultate;
    }	
    
}