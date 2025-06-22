
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JButton;

public class KlassenHinzu {

	private JFrame frame;
	private JTextField textField;
	private MYSQL mysql = new MYSQL("127.0.0.1",3306,"projektdb");
	private SchuelerHinzu2 sh2 = new SchuelerHinzu2(MainWindow.getInstance());

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public KlassenHinzu() {
		initialize3();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize3() {
		frame = new JFrame();
		frame.setBounds(100, 100, 222, 215);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Klasse:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 11, 80, 23);
		frame.getContentPane().add(lblNewLabel);
		
		JTextField txtField_Klassenname = new JTextField();
		txtField_Klassenname.setBounds(110, 12, 86, 20);
		frame.getContentPane().add(txtField_Klassenname);
		txtField_Klassenname.setColumns(10);
		
		String[] raumListe = mysql.getDaten("select Raumbezeichnung from raum","Raumbezeichnung");
		System.out.println(raumListe[0]);
		
		JLabel lblRaum = new JLabel("Raum:");
		lblRaum.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRaum.setBounds(10, 63, 80, 23);
		frame.getContentPane().add(lblRaum);
		
		JComboBox comboBox = new JComboBox(raumListe);
		comboBox.setBounds(113, 66, 83, 22);
		frame.getContentPane().add(comboBox);
		
		JButton btnbesteatigen = new JButton("Bestätigen");
		btnbesteatigen.setBounds(54, 122, 89, 23);
		btnbesteatigen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String klassenname = txtField_Klassenname.getText();
				int raumID = comboBox.getSelectedIndex();
				String raumID_String = (String) comboBox.getItemAt(raumID);
				String[] RaumNummer = mysql.getDaten("select RaumID from raum where Raumbezeichnung = '"+ raumID_String+"'","RaumID");
				System.out.println(RaumNummer[0]);
				System.out.println(klassenname);
				mysql.setNewSchueler("INSERT INTO `projektdb`.`klasse` (`Klassename`,`Raum_RaumID`) VALUES('" + klassenname + "', " + RaumNummer[0] + ");");
			    System.out.println("Wurde hinzugefügt");
			    frame.dispose();
			    sh2.Signal2();
			}
			
		});
		frame.getContentPane().add(btnbesteatigen);
	}
}