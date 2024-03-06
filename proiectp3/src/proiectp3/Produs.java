package proiectp3;

/**
 * Clasa pentru reprezentarea unui produs din stocul magazinului online
 */

public class Produs {
	private int idProdus;
	
	public String nume;
	public int cantitate;
	public String categorie;
	public double pret;
	
	/**
	 * Constructor cu parametri pentru un produs
	 * 
	 * 
	 * @param nume Numele produsului
	 * @param cantitate Cantitate produsului
	 * @param categorie Categoria din care va face parte produsul
	 * @param pret Pretul produsului 

	 */
	
	public Produs(String nume, int cantitate, String categorie, double pret) {
		super();
		this.nume = nume;
		this.cantitate = cantitate;
		this.categorie = categorie;
		this.pret = pret;
	}
	
	/**
	 * Metoda de acces pentru a obtine numele produsului
	 * 
	 * @return nume Numele produsului
	 */
	
	public String getNume() {
		return nume;
	}
	
	/**
	 * Metoda pentru a seta numele produsului
	 * 
	 * @return nume Numele produsului
	 */
	
	public void setNume(String nume) {
		this.nume = nume;
	}
	
	/**
	 * Metoda de acces pentru a obtine cantitatea produsului
	 * 
	 * @param cantitate Cantitatea produsului
	 */

	public int getCantitate() {
		return cantitate;
	}
	
	/**
	 * Metoda de acces pentru a seta cantitatea produsului
	 * 
	 * @param cantitate Cantitatea produsului
	 */
	
	public void setCantitate(int cantitate) {
		this.cantitate = cantitate;
	}
	
	/**
	 * Metoda de acces pentru a obtine categoria produsului
	 * 
	 * @return cateogrie Categoria produsului
	 */
	
	
	public String getCategorie() {
		return categorie;
	}
	
	/**
	 * Metoda pentru a seta categoria produsului
	 * 
	 * @return cateogrie Categoria produsului
	 */
	
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	/**
	 * Metoda de acces pentru a obtine pretul produsului
	 * 
	 * @return pret Pretul produsului 
	 */
	
	public double getPret() {
		return pret;
	}
	
	/**
	 * Metoda pentru a seta pretul produsului
	 * 
	 * @return pret Pretul produsului 
	 */
	
	public void setPret(double pret) {
		this.pret = pret;
	}
	
	/**
	 * Metoda pentru afisarea produsului
	 */
	
	 public int getIdProdus() {
	        return idProdus;
	    }

	    // Adăugați această metodă pentru a seta id-ul produsului
	    public void setIdProdus(int idProdus) {
	        this.idProdus = idProdus;
	    }
	
	@Override
	public String toString() {
		return "Produs [nume=" + nume + ", cantitate=" + cantitate + ", categorie=" + categorie + ", pret=" + pret
				+ "]";
	}

	

}
