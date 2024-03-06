package proiectp3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class GUI extends JFrame {

    private JTextArea textArea;
    private JList<Produs> produseList;
    private DefaultListModel<Produs> produseModel;
    private ArrayList<Produs> cosProduse;
    private Connection connection;
    
    public GUI() {
        setTitle("Interfata Produse");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cosProduse = new ArrayList<>();
        produseModel = new DefaultListModel<>();
        produseList = new JList<>(produseModel);
        JScrollPane listScrollPane = new JScrollPane(produseList);

        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane textScrollPane = new JScrollPane(textArea);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(listScrollPane, BorderLayout.WEST);
        mainPanel.add(textScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton adaugaInCosButton = new JButton("Adauga in cos");
        
        adaugaInCosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugaInCos();
            }
            
            
        });
        JButton adaugaProdusButton = new JButton("Adauga produs");
        adaugaProdusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugaProdus(); // Apelează funcția adaugaProdus la apăsarea butonului
            }
        });

        JButton plaseazaComandaButton = new JButton("Plaseaza comanda");
        plaseazaComandaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plaseazaComanda();
            }
        });
        JButton cautaProdusButton = new JButton("Cauta produs");
        cautaProdusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cautaProdus(); // Apelează funcția cautaProdus la apăsarea butonului
            }
        });
        buttonPanel.add(cautaProdusButton);


        buttonPanel.add(adaugaInCosButton);
        buttonPanel.add(plaseazaComandaButton);
        buttonPanel.add(adaugaProdusButton);
        buttonPanel.add(cautaProdusButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        afiseazaProduseFromDatabase();

        setVisible(true);
    }
    
    private void initializeDatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Verifică dacă conexiunea este deja deschisă
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectp3", "root", "");
                System.out.println("Database Connected");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();  // Afișează eroarea pentru depanare
            JOptionPane.showMessageDialog(this, "Eroare la conectarea la baza de date.");
        }
    }



    private void closeDatabaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database Connection Closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void afiseazaProduseFromDatabase() {
        try {
            // Deschide conexiunea la baza de date
            initializeDatabaseConnection();

            // Caută toate produsele din tabelul "produs"
            String query = "SELECT * FROM produs";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // Afișează numele coloanelor
                ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.println(metaData.getColumnName(i));
                }

                // Adaugă fiecare produs în modelul produselor
             // Adaugă fiecare produs în modelul produselor
                while (resultSet.next()) {
                    int idProdus = resultSet.getInt("id");  
                    String nume = resultSet.getString("nume");
                    int cantitate = resultSet.getInt("cantitate");
                    String categorie = resultSet.getString("categorie");
                    double pret = resultSet.getDouble("pret");

                    Produs produs = new Produs(nume, cantitate, categorie, pret);
                    produs.setIdProdus(idProdus);  // Setează id-ul produsului
                    produseModel.addElement(produs);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la citirea produselor din baza de date.");
        } finally {
            // Închide conexiunea la baza de date
            closeDatabaseConnection();
        }
    }



    private void adaugaInCos() {
        Produs produsSelectat = produseList.getSelectedValue();
        if (produsSelectat != null) {
            cosProduse.add(produsSelectat);
            afiseazaProduseInCos();
        } else {
            JOptionPane.showMessageDialog(this, "Selectati un produs din lista!");
        }
    }
    private void afiseazaOutput(String mesaj) {
        JOptionPane.showMessageDialog(this, mesaj);
    }

    
    private void adaugaProdus() {
        String name = JOptionPane.showInputDialog(this, "Introduceti numele produsului:");
        String category = JOptionPane.showInputDialog(this, "Introduceti categoria produsului:");
        String quantityStr = JOptionPane.showInputDialog(this, "Introduceti cantitatea produsului:");
        double pret = Double.parseDouble(JOptionPane.showInputDialog(this, "Introduceti pretul produsului:"));

        try {
            int quantity = Integer.parseInt(quantityStr);
            Produs newProduct = new Produs(name, quantity, category, pret);

            // Adaugă subinterogările SQL pentru a adăuga produsul în baza de date
            String insertQuery = "INSERT INTO produs (nume, cantitate, categorie, pret) VALUES (?, ?, ?, ?)";

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectp3", "root", "");
                 PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {

                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, quantity);
                preparedStatement.setString(3, category);
                preparedStatement.setDouble(4, pret);

                preparedStatement.executeUpdate();

                updateOutput("Produs adaugat in baza de date: " + newProduct.getNume() +
                        " Cantitate: " + newProduct.getCantitate() +
                        " Pret: " + newProduct.getPret() + " RON");

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Eroare la adaugarea produsului in baza de date.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantitate invalida. Introduceti un numar valid.");
        }

        // Închide conexiunea la baza de date
        closeDatabaseConnection();

        afiseazaProduseFromDatabase(); // Actualizează lista de produse după adăugarea în baza de date
        showMainCard();
    }
    private void updateOutput(String message) {
        textArea.append(message + "\n");
    }
    private void showMainCard() {
        // Poți schimba această secțiune pentru a face altceva în loc de schimbarea cardului
        textArea.setText(""); // Curăță textArea
        produseModel.clear(); // Curăță modelul de produse
        afiseazaProduseFromDatabase(); // Afisează produsele din baza de date
    }




    private void afiseazaProduseInCos() {
        textArea.setText("Produsele din cos:\n");
        for (Produs produs : cosProduse) {
            textArea.append(produs.toString() + "\n");
        }
    }

    private void plaseazaComanda() {
        // Obține numele clientului folosind o casetă de dialog
        String numeClient = JOptionPane.showInputDialog(this, "Introduceți numele dvs.:");

        // Verifică dacă utilizatorul a introdus un nume sau a apăsat anulare
        if (numeClient == null || numeClient.trim().isEmpty()) {
            textArea.setText("Plasarea comenzii a fost anulată. Numele clientului nu a fost furnizat.");
            return;
        }

        // Obține adresa clientului folosind o casetă de dialog
        String adresaClient = JOptionPane.showInputDialog(this, "Introduceți adresa dvs.:");

        // Verifică dacă utilizatorul a introdus o adresă sau a apăsat anulare
        if (adresaClient == null || adresaClient.trim().isEmpty()) {
            textArea.setText("Plasarea comenzii a fost anulată. Adresa clientului nu a fost furnizată.");
            return;
        }

        try {
            // Deschide conexiunea la baza de date
            initializeDatabaseConnection();

            // Obține data curentă
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

            // Adaugă comanda în tabela comenzi
            String insertComandaQuery = "INSERT INTO comenzi (id_produs, nume_client, adresa_client, data_comanda, cantitate_comandata) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertComandaQuery)) {
                // Ajustează cu ID-ul produsului în funcție de comandă
                int idProdus = getIdProdusByName("Jordan 1 Low"); 
                preparedStatement.setInt(1, idProdus);
                preparedStatement.setString(2, numeClient); // Utilizează numele furnizat de utilizator
                preparedStatement.setString(3, adresaClient); // Utilizează adresa furnizată de utilizator
                preparedStatement.setDate(4, currentDate);
                preparedStatement.setInt(5, cosProduse.size()); // Cantitate comandată este numărul de produse din coș

                preparedStatement.executeUpdate();

                textArea.setText("Comanda a fost plasată cu succes!\nCoșul de cumpărături este golit.");

                // Actualizează cantitatea disponibilă în baza de date după plasarea comenzii
                updateCantitateProduseInDatabase();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Eroare la plasarea comenzii în baza de date.");
            }
        } finally {
            // Închide conexiunea la baza de date
            closeDatabaseConnection();
        }

        cosProduse.clear(); // Golește coșul de cumpărături după plasarea comenzii
        afiseazaProduseFromDatabase(); // Actualizează lista de produse după plasarea comenzii
    }

    private int getIdProdusByName(String numeProdus) throws SQLException {
        String query = "SELECT id FROM produs WHERE nume = ?"; 
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, numeProdus);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        throw new SQLException("ID-ul produsului nu a putut fi obținut.");
    }


    private void updateCantitateProduseInDatabase() {
        for (Produs produs : cosProduse) {
            try {
                // Actualizează cantitatea disponibilă în baza de date pentru fiecare produs
                String updateCantitateQuery = "UPDATE produs SET cantitate = cantitate - ? WHERE id = ?"; 
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateCantitateQuery)) {
                    preparedStatement.setInt(1, 1); // Ajustează cu cantitatea comandată
                    preparedStatement.setInt(2, produs.getIdProdus());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Eroare la actualizarea cantității produselor în baza de date.");
            }
        }
    }
    
    private void cautaProdus() {
        String numeCautat = JOptionPane.showInputDialog(this, "Introduceți numele produsului:");

        // Verifică dacă utilizatorul a introdus un nume sau a apăsat anulare
        if (numeCautat == null || numeCautat.trim().isEmpty()) {
            return; // Dacă nu s-a introdus niciun nume, nu se face nimic
        }

        try {
            // Deschide conexiunea la baza de date
            initializeDatabaseConnection();

            // Caută produsul în baza de date după nume
            String query = "SELECT * FROM produs WHERE nume = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, numeCautat);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Afișează rezultatele căutării
                textArea.setText("Rezultatele căutării după nume \"" + numeCautat + "\":\n");
                while (resultSet.next()) {
                    int idProdus = resultSet.getInt("id");
                    String nume = resultSet.getString("nume");
                    int cantitate = resultSet.getInt("cantitate");
                    String categorie = resultSet.getString("categorie");
                    double pret = resultSet.getDouble("pret");

                    textArea.append("ID: " + idProdus + " | Nume: " + nume + " | Cantitate: " + cantitate +
                            " | Categorie: " + categorie + " | Pret: " + pret + " RON\n");
                }

                if (!resultSet.isBeforeFirst()) {
                    textArea.append("Niciun rezultat găsit pentru numele \"" + numeCautat + "\".");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la căutarea produsului în baza de date.");
        } finally {
            // Închide conexiunea la baza de date
            closeDatabaseConnection();
        }
    }



    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        new GUI();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectp3","root","");
            System.out.println("Database Connected");
    
}
}
