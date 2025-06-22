import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class AdminWindow {

	private JFrame frame;
	private JPanel panel_verwaltung = new JPanel();
	private MYSQL mysql = new MYSQL("127.0.0.1",3306,"projektdb");
	private CardLayout clay = new CardLayout(0,0);
	private JTable tabelle;
	private String[] ID;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public AdminWindow() {
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 713, 442);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JButton btnVerwaltungSchueler = new JButton();
		btnVerwaltungSchueler.setText("Schüler");
		btnVerwaltungSchueler.setBounds(10, 131, 155, 36);
		ButtonFarbe(btnVerwaltungSchueler);
		btnVerwaltungSchueler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow mainwindow = MainWindow.getInstance();
				mainwindow.Admin();
				frame.dispose();
			}
			
		});
		frame.getContentPane().add(btnVerwaltungSchueler);
		
		JButton btnVerwaltungRechte = new JButton("Verwaltung Accounts");
		btnVerwaltungRechte.setBounds(10, 178, 155, 36);
		ButtonFarbe(btnVerwaltungRechte);
		frame.getContentPane().add(btnVerwaltungRechte);
		
		JButton btnBerechtigungen = new JButton("Berechtigungen");
		btnBerechtigungen.setBounds(10, 227, 155, 36);
		ButtonFarbe(btnBerechtigungen);
		frame.getContentPane().add(btnBerechtigungen);
		
		JPanel panel_organisation = new JPanel();
		panel_organisation.setBounds(175, 24, 512, 355);
		frame.getContentPane().add(panel_organisation);
		panel_organisation.setLayout(clay);
		
		panel_organisation.add(panel_verwaltung, "Verwaltung");
		panel_verwaltung.setLayout(null);
		
		JButton btnAccountHinzu = new JButton("Hinzufügen");
		btnAccountHinzu.setBounds(25, 24, 89, 23);
		ButtonFarbe(btnAccountHinzu);
		btnAccountHinzu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AccountHinzu acchin = new AccountHinzu();
				acchin.initializeAccHinzu();
				Anzeige();
			}
			
		});
		btnAccountHinzu.setVisible(false);
		frame.getContentPane().add(btnAccountHinzu);
		
		JButton btnAccountLoeschen = new JButton("Löschen");
		btnAccountLoeschen.setBounds(25, 54, 89, 23);
		ButtonFarbe(btnAccountLoeschen);
		btnAccountLoeschen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tabelle.getSelectedRow();
				mysql.setNewSchueler("DELETE FROM benutzer WHERE BenutzerID = " + ID[index]);
				Anzeige();
			}
			
		});
		btnAccountLoeschen.setVisible(false);
		frame.getContentPane().add(btnAccountLoeschen);

		btnVerwaltungRechte.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnAccountLoeschen.setVisible(true);
				btnAccountHinzu.setVisible(true);
				Anzeige();
				clay.show(panel_organisation, "Verwaltung");
			}
			
		});
	}
	
	private void Anzeige() {
		String[] Titel = {"ID","Username","Recht"};
		String[][] Daten = new String[mysql.getDaten("SELECT BenutzerID FROM benutzer", "BenutzerID").length][3];
		
		ID = mysql.getDaten("select BenutzerID From benutzer Order by BenutzerID","BenutzerID");
		String[] Username = mysql.getDaten("select Username From benutzer Order by BenutzerID","Username");
		String[] Recht = mysql.getDaten("select Recht From benutzer Order by BenutzerID","Recht");
		
	    for (int i = 0; i < ID.length; i++) {
	        Daten[i][0] = ID[i];
	        Daten[i][1] = Username[i];
	        Daten[i][2] = Recht[i];
	    }
		
		tabelle = new JTable(Daten,Titel);
		tabelle.setBounds(10, 22, 448, 344);
		panel_verwaltung.removeAll();
		panel_verwaltung.add(tabelle);
	    panel_verwaltung.revalidate();
	    panel_verwaltung.repaint();
	}
	
	public void ButtonFarbe(JButton button) {
		button.setFont(new Font("Calibri", Font.PLAIN, 14));
		button.setBackground(Color.GRAY);
		button.setForeground(Color.white);
		button.setUI(new StyledButtonUI());
	}
}
