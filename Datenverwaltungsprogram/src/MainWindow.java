import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;


public class MainWindow {

	private JFrame frame;
	private HashMap<JPanel, JList> JListMap = new HashMap();
	private HashMap<JPanel, JScrollPane> scrollPaneMap = new HashMap<>();
	private JList<String> tempoListe = new JList<String>();
	private ArrayList<JPanel> PanelListe = new ArrayList<JPanel>();
	DefaultListModel<String> listModel = new DefaultListModel<>();
	JList<String> list = new JList<String>(listModel);
	private CardLayout cardLayout = new CardLayout();
	private JTextField textField_Eingabe;
	private JTextField textField_suchabfrage;
	private MYSQL mysql = new MYSQL("localhost",3306,"projektdb");
	JPanel panel_organisation = new JPanel(cardLayout);
	private SchuelerHinzu sh = new SchuelerHinzu();
	private String[] schuelerID;
	static MainWindow window;
	private AdminWindow adminwindow = new AdminWindow();
	private JLabel lblID = new JLabel("ID:");
	private JTextField txtField_ID = new JTextField();
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public MainWindow() {
	
	}
	
	public void MainWindowStart(MainWindow window, String Rechte) {
		switch(Rechte) {
		case "Admin":
			AdminPanel();
			break;
		case "Schulverwaltung":
			initialize();
			BTN_Schueler_Loeschen();
			BTN_Schueler_Hinzu();
			BTN_Klasse_Hinzu();
			BTN_Klasse_Loeschen();
			break;
		case "Lehrer":
			initialize();
			break;
		default:
			System.out.println("ERROR: Rechte konnten nicht zugewiesen werden!");
			break;
		}
		this.window = window;
	}
	
	private void AdminPanel() {
		adminwindow = new AdminWindow();
		adminwindow.initialize();
	}
	
	public void Admin() {
		initialize();
		BTN_Schueler_Loeschen();
		BTN_Schueler_Hinzu();
		BTN_Klasse_Hinzu();
		BTN_Klasse_Loeschen();
		BTN_Zurueck();
	}
	
	private void BTN_Zurueck() {
		JButton btnZurueck = new JButton("Zurück");
		adminwindow.ButtonFarbe(btnZurueck);
		btnZurueck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				adminwindow.initialize();
				frame.dispose();
			}
			
		});
		btnZurueck.setBounds(25, 460, 100, 25);
		frame.getContentPane().add(btnZurueck);
	}
	
	private void BTN_Schueler_Loeschen(){

		JButton btnSchuelerLoeschen = new JButton("Schüler -");
		adminwindow.ButtonFarbe(btnSchuelerLoeschen);
        btnSchuelerLoeschen.setBounds(625, 460, 100, 25);
        btnSchuelerLoeschen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	lblID.setVisible(true);
            	txtField_ID.setVisible(true);
        		System.out.println("Dingens wurd hinzugefügt");
        		if(txtField_ID.getText() != null) {
        			System.out.println("Wurde gelöscht");
        			mysql.setNewSchueler("DELETE FROM Schüler where SchülerID = '" + txtField_ID.getText() + "'");
        		}
            }

        });
        btnSchuelerLoeschen.setVisible(true);
        frame.getContentPane().add(btnSchuelerLoeschen);
	}
	
	private void BTN_Schueler_Hinzu() {
		JButton btnSchuelerHinzu = new JButton("Schüler +");
		adminwindow.ButtonFarbe(btnSchuelerHinzu);
		btnSchuelerHinzu.setBounds(500, 460, 100, 25);
		btnSchuelerHinzu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sh.initialize2();
			}
			
		});
		frame.getContentPane().add(btnSchuelerHinzu);
	}
	
	private void BTN_Klasse_Hinzu() {
		JButton btnKlasseHinzufuegen = new JButton("Klasse +");
		adminwindow.ButtonFarbe(btnKlasseHinzufuegen);
		btnKlasseHinzufuegen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				KlassenHinzu kh = new KlassenHinzu();
			}
			
		});
		btnKlasseHinzufuegen.setBounds(350, 460, 100, 25);
		frame.getContentPane().add(btnKlasseHinzufuegen);
	}
	
	private void BTN_Klasse_Loeschen() {
		JButton btnKlasseloeschen = new JButton("Klasse -");
		adminwindow.ButtonFarbe(btnKlasseloeschen);
		btnKlasseloeschen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String loesch_Name = list.getSelectedValue();
				int klassenID = getKlasseID(loesch_Name);
				
				String[] schuelerListe = mysql.getDaten("select SchülerID from Schüler Where K_KlasseID = " + klassenID,"SchülerID");
				int schuelerListenlaenge = schuelerListe.length;
				
				mysql.setNewSchueler("Delete from Schüler where K_KlasseID = '" + klassenID +"'");
				System.out.println("An dem Befehl liegts nicht");
				
				mysql.setNewSchueler("Delete from klasse where Klassename = '" + loesch_Name +"'");
				KlassenAnzeigen();
			}
			
		});
		btnKlasseloeschen.setBounds(225, 460, 100, 25);
		frame.getContentPane().add(btnKlasseloeschen);
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 754, 536);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		panel_organisation.setBounds(160, 72, 578, 377);
		frame.getContentPane().add(panel_organisation);
		
		JLabel lblNewLabel_2_1_1_1_1 = new JLabel("TAI24");
		lblNewLabel_2_1_1_1_1.setBounds(271, 5, 29, 14);
		
		JScrollPane scrollPane = new JScrollPane(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String auswahl = list.getSelectedValue();
				cardLayout.show(panel_organisation, auswahl);
			}
		});
		scrollPane.setBounds(10, 72, 151, 377);
		frame.getContentPane().add(scrollPane);
		
		JLabel lbl_WANord = new JLabel("WA-Nord");
		lbl_WANord.setBounds(22, 11, 102, 32);
		frame.getContentPane().add(lbl_WANord);
		lbl_WANord.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		textField_suchabfrage = new JTextField();
		textField_suchabfrage.setBounds(261, 41, 248, 20);
		frame.getContentPane().add(textField_suchabfrage);
		textField_suchabfrage.setColumns(10);
		
		JLabel lbl_TitelSuche = new JLabel("SUCHE");
		lbl_TitelSuche.setBounds(211, 44, 46, 14);
		frame.getContentPane().add(lbl_TitelSuche);
		
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblID.setBounds(660, 41, 29, 17);
		lblID.setVisible(false);
		frame.getContentPane().add(lblID);
		
		txtField_ID.setBounds(699, 41, 29, 20);
		txtField_ID.setVisible(false);
		frame.getContentPane().add(txtField_ID);
		txtField_ID.setColumns(10);
		
		KlassenAnzeigen();
	}
	
	private void Anzeige(JPanel auswahl,String textAusgabe) {
		JLabel tempoLabel = new JLabel();
		tempoLabel.setText(textAusgabe);
		tempoLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		tempoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tempoLabel.setBounds(181, 5, 115, 27);
		auswahl.setLayout(null);
		auswahl.add(tempoLabel);
		
		 int listenLaenge = list.getModel().getSize();
		 int klassenID = 0;
		
		for(int i = 0; i < listenLaenge; i++) {
			if(list.getModel().getElementAt(i) == textAusgabe) {
				klassenID = i + 1;
			}
		}
		ArrayList<String[]> Daten = new ArrayList<>();
		String[] Titel = {"ID","Vorname","Nachname","Geburtsdatum","Email","Nummer"};
		
		schuelerID = mysql.getDaten("select SchülerID from Schüler Where K_KlasseID = " + klassenID,"SchülerID");
		String[] vorname = mysql.getDaten("select Vorname from Schüler Where K_KlasseID = " + klassenID,"Vorname");
		String[] nachname = mysql.getDaten("select Nachname from Schüler Where K_KlasseID = " + klassenID,"Nachname");
		String[] gbDaten = mysql.getDaten("select Geburtsdatum from Schüler Where K_KlasseID = " + klassenID,"Geburtsdatum");
		String[] email = mysql.getDaten("select email from Schüler Where K_KlasseID = " + klassenID,"email");
		String[] schuelerNummer = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};//mysql.getDaten("select SchülerNummer from Schüler Where K_KlasseID = " + klassenID,"SchülerNummer");
		
		for(int i = 0; i < vorname.length; i++) {
			Daten.add(new String[] {schuelerID[i],vorname[i],nachname[i],gbDaten[i],email[i],schuelerNummer[i]});
		}
		
		JTable tabelle = new JTable(Daten.toArray(new String[0][0]),Titel);
		tabelle.setBounds(10, 50, 568, 377);
		auswahl.add(tabelle);
		
		auswahl.revalidate();
		auswahl.repaint();
	}
	
	public void KlassenAnzeigen() {
		ArrayList<String> klassenListe = mysql.getExecutedStatement("select Klassename from klasse ORDER BY KlasseID ASC;");
		PanelListe.clear();
		listModel.clear();
		
		for(int i = 0; i < klassenListe.size(); i++) {
			listModel.add(listModel.size(), klassenListe.get(i));
			PanelListe.add(new JPanel());
			Anzeige(PanelListe.get(PanelListe.size() - 1),klassenListe.get(i));
			panel_organisation.add(PanelListe.get(PanelListe.size() - 1),klassenListe.get(i));	
		}
		AutoInkrementReset();
	}
	
	public void Signalerhalten() {
		String[] localDaten = sh.getDaten();
		String[] localAdressDaten = sh.getAdressDaten();
		Date sqlDate = Date.valueOf(localDaten[2]);
		
		mysql.setNewSchueler("INSERT INTO `projektdb`.`adresse` (`Straße`,`PLZ`,`Ort`)"
		        + " VALUES ('" 
				+ localAdressDaten[0] + "', '" 
		        + localAdressDaten[1] + "', '" 
		        + localAdressDaten[2] + "');");
        int AdressID = getAdressID(localAdressDaten[0],localAdressDaten[1],localAdressDaten[2]);
        System.out.println("AdressID: " + AdressID);
		mysql.setNewSchueler("INSERT INTO `projektdb`.`schüler` (`Vorname`,`Nachname`,`Geburtsdatum`,`email`,`K_KlasseID`,`Adresse_AdresseID`)"
		        + " VALUES ('" 
				+ localDaten[0] + "', '" 
		        + localDaten[1] + "', '" 
		        + sqlDate + "', '" 
		        + localDaten[3] + "', " 
		        + getKlasseID(list.getSelectedValue()) + ", " + AdressID + ");");
		
		KlassenAnzeigen();
		frame.revalidate();
		frame.repaint();
	}

	public static MainWindow getInstance() {
		return window;
	}
	
	private int getKlasseID(String klassenname) {
		System.out.println("Klassenname: " + klassenname);
		int klassenID = mysql.getDatenInt("select KlasseID from klasse where Klassename = '"+ klassenname +"'","KlasseID");
		System.out.println("Klassen ID: "+klassenID);
		return klassenID;
	}
	
	public void SignalErhalten_Klasse() {
		KlassenAnzeigen();
	}
	
	private void AutoInkrementReset() {
		int autoinkrement = list.getModel().getSize();
		    try {
		        mysql.executeStatement("ALTER TABLE klasse AUTO_INCREMENT = " + autoinkrement +";");
		        System.out.println("Auto-Inkrement für Tabelle 'klasse' wurde zurückgesetzt.");
		    } catch (Exception e) {
		        System.err.println("Fehler beim Zurücksetzen des Auto-Inkrements: " + e.getMessage());
		    }
		}
	
	private int getAdressID(String Straße,String PLZ,String Ort) {
		int AdressID = mysql.getDatenInt("Select AdresseID From Adresse where Straße = + '"+ Straße +"' AND PLZ = '" + PLZ +"' AND Ort = '" + Ort +"';","AdresseID");
	    return AdressID;
	}
	}
