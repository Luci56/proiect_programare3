package proiectp3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		try (PrintWriter printWriter = new PrintWriter(new File("output.txt"))) {
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true) {
                @Override
                public void println(String x) {
                    printWriter.println(x);
                    super.println(x);
                }
            });
		
		 Stoc Stoc = citesteProduseDinFisier("StocProduse.txt");
		 
		 Stoc.adaugaProdus(new Produs("jordan retro 10",60,"Adidasi baschet",200));
		 
		 System.out.println("Afisarea tuturor produselor din stoc");
		 ArrayList<Produs> ProduseStoc = Stoc.afiseazaToateProdusele();
		 for(Produs produs : ProduseStoc) {
			 System.out.println(produs);
		 }
		 
		 System.out.println("\n");
		 
		 System.out.println("Afisarea produselor cu cantitate scazuta");
		 ArrayList<Produs> ProduseCantScz = Stoc.afiseazaProduseleCuCantitateScazuta(31);
		 for(Produs produs : ProduseCantScz) {
			 System.out.println(produs);
		 }
		 
		 System.out.println("\n");
		 
		 System.out.println("Afisare Produse dupa actualizare de stoc");
		 Stoc.actualizeazaStoc(20, "Adidas NMD");
		 ArrayList<Produs> ProduseActualizareStoc = Stoc.afiseazaToateProdusele();
		 for(Produs produs : ProduseActualizareStoc) {
			 System.out.println(produs);
		 }
		 
		 System.out.println("\n");
		 
		 System.out.println("Afiseaza produsele dupa cautare nume");
		 ArrayList<Produs> ProdusCautatNume = Stoc.cauta("Nike Lebron 7");
		 for(Produs produs : ProdusCautatNume) {
			 System.out.println(produs);
	}
		 System.out.println("\n");
		 
		 System.out.println("Afiseaza produsele dupa cautare categorie");
		 ArrayList<Produs> ProdusCautatCategorie = Stoc.cauta("Adidasi alergat");
		 for(Produs produs : ProdusCautatCategorie) {
			 System.out.println(produs);
	}
		 
		 System.out.println("\n");
		 
		 System.out.println("Afisarea produselor dupa stergere de produs");
		 Stoc.stergeProdus("Adidas NMD");
		 ArrayList<Produs> toateProduseleDupaStergere = Stoc.afiseazaToateProdusele();
	        for (Produs produs : toateProduseleDupaStergere) {
	            System.out.println(produs);
	        }
	        
	        System.out.println("\n");
		 } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		 
		

}
	
	 private static Stoc citesteProduseDinFisier(String StocProduse) {
	        Stoc stoc = new Stoc();

	        try {
	            File fisier = new File(StocProduse);
	            Scanner scanner = new Scanner(fisier);

	            while (scanner.hasNextLine()) {
	                String linie = scanner.nextLine();
	                String[] detaliiProdus = linie.split(",");

	                if (detaliiProdus.length == 4) {
	                    String nume = detaliiProdus[0];
	                    int cantitate = Integer.parseInt(detaliiProdus[1]);
	                    String categorie = detaliiProdus[2];
	                    double pret = Double.parseDouble(detaliiProdus[3]);

	                    Produs produs = new Produs(nume,cantitate,categorie,pret);
	                    produs.setNume(nume);
	                    produs.setCantitate(cantitate);
	                    produs.setPret(pret);
	                    produs.setCategorie(categorie);

	                    stoc.adaugaProdus(produs);
	                }
	            }

	            scanner.close();
	        } catch (FileNotFoundException e) {
	            System.err.println("Fisierul nu a fost gasit: " + e.getMessage());
	        }

	        return stoc;
	    }
	 

	 
	
}
