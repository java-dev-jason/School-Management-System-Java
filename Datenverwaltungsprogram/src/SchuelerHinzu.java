import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class SchuelerHinzu {

	private JFrame frame;
	private String vorname;
	private String nachname;
	private String gbDatum;
	private String email;
	private String adresseStr;
	private String adressePLZ;
	private String adresseOrt;
	private JTextField txtField_Vorname = new JTextField();
	private JTextField txtField_Nachname = new JTextField();
	private JTextField txtField_GbDatum = new JTextField();
	private JTextField txtField_Email = new JTextField();
	private JTextField txtField_AdresseStr = new JTextField();
	private JTextField txtField_AdressePLZ = new JTextField();
	private JTextField txtField_AdresseOrt = new JTextField();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchuelerHinzu window = new SchuelerHinzu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SchuelerHinzu() {
		
	}

	public void initialize2() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 253, 389);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblVorname = new JLabel("Vorname:");
		lblVorname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblVorname.setBounds(10, 20, 80, 20);
		frame.getContentPane().add(lblVorname);
	
		txtField_Vorname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtField_Vorname.setBounds(100, 20, 127, 20);
		frame.getContentPane().add(txtField_Vorname);
		txtField_Vorname.setColumns(10);
		
		JLabel lblNachname = new JLabel("Nachname:");
		lblNachname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNachname.setBounds(10, 40, 80, 20);
		frame.getContentPane().add(lblNachname);
		
		txtField_Nachname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtField_Nachname.setColumns(10);
		txtField_Nachname.setBounds(100, 40, 127, 20);
		frame.getContentPane().add(txtField_Nachname);
		
		JLabel lblGeburtsdatum = new JLabel("Gbdatum:");
		lblGeburtsdatum.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGeburtsdatum.setBounds(10, 60, 80, 20);
		frame.getContentPane().add(lblGeburtsdatum);
		
		txtField_GbDatum.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtField_GbDatum.setColumns(10);
		txtField_GbDatum.setBounds(100, 60, 127, 20);
		frame.getContentPane().add(txtField_GbDatum);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(10, 80, 80, 20);
		frame.getContentPane().add(lblEmail);
		
		txtField_Email.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtField_Email.setColumns(10);
		txtField_Email.setBounds(100, 80, 127, 20);
		frame.getContentPane().add(txtField_Email);
		
		JLabel lblAdresseStr = new JLabel("Strasse:");
		lblAdresseStr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAdresseStr.setBounds(10, 100, 80, 20);
		frame.getContentPane().add(lblAdresseStr);
		
		txtField_AdresseStr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtField_AdresseStr.setColumns(10);
		txtField_AdresseStr.setBounds(100, 100, 127, 20);
		frame.getContentPane().add(txtField_AdresseStr);
		
		JLabel lblAdressePLZ = new JLabel("Plz:");
		lblAdressePLZ.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAdressePLZ.setBounds(10, 120, 80, 20);
		frame.getContentPane().add(lblAdressePLZ);
		
		txtField_AdressePLZ.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtField_AdressePLZ.setColumns(10);
		txtField_AdressePLZ.setBounds(100, 120, 127, 20);
		frame.getContentPane().add(txtField_AdressePLZ);
		
		JLabel lblAdresseOrt = new JLabel("Ort:");
		lblAdresseOrt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAdresseOrt.setBounds(10, 140, 80, 20);
		frame.getContentPane().add(lblAdresseOrt);
		
		txtField_AdresseOrt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtField_AdresseOrt.setColumns(10);
		txtField_AdresseOrt.setBounds(100, 140, 127, 20);
		frame.getContentPane().add(txtField_AdresseOrt);
		
		JButton btnBestaetigen = new JButton("Bestaetigen");
		btnBestaetigen.setBounds(72, 255, 110, 23);
		btnBestaetigen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SchuelerHinzu2 sh2 = new SchuelerHinzu2(MainWindow.getInstance());
				sh2.Start();
				frame.dispose();
			}
			
		});
		frame.getContentPane().add(btnBestaetigen);
		
		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.setBounds(72, 280, 110, 23);
		btnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
			
		});
		frame.getContentPane().add(btnAbbrechen);
	}

	public String[] getDaten() {
		vorname = txtField_Vorname.getText();
		nachname = txtField_Nachname.getText();
		gbDatum = txtField_GbDatum.getText();
		email = txtField_Email.getText();
		
		String[] Daten = new String[] {vorname,nachname,gbDatum,email};
		
		return Daten;
	}
	public String[] getAdressDaten() {
		adresseStr = txtField_AdresseStr.getText();
		adressePLZ = txtField_AdressePLZ.getText();
		adresseOrt = txtField_AdresseOrt.getText();
		String[] adressDaten = new String[] {adresseStr,adressePLZ,adresseOrt};
		return adressDaten;
	}
	
	
}
