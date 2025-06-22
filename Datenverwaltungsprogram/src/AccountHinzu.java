import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AccountHinzu {

	private JFrame frame;
	private JPasswordField pass_Field;
	private JTextField txt_Username;
	private MYSQL mysql = new MYSQL("127.0.0.1",3306,"projektdb");

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public AccountHinzu() {
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void initializeAccHinzu() {
		frame = new JFrame();
		frame.setBounds(100, 100, 221, 253);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		String[] RechteNamen = mysql.getDaten("select Recht from benutzer","Recht");
		ArrayList<String> RechteNamen2 = new ArrayList<String>();
		for(int i = 0; RechteNamen.length > i; i++) {
			boolean check = false;
			for(int j = 0; RechteNamen2.size() > j; j++) {
				if (RechteNamen[i].equals(RechteNamen2.get(j))) {
					check = true;
				}
			}
			if(check == false) {
				System.out.println(RechteNamen[i]);
				RechteNamen2.add(RechteNamen[i]);
			}
		}
		
		
		JComboBox comboBox_Rechte = new JComboBox(RechteNamen2.toArray());
		comboBox_Rechte.setBounds(89, 104, 105, 22);
		frame.getContentPane().add(comboBox_Rechte);
		
		JLabel lblRechte = new JLabel("Rechte:");
		lblRechte.setBounds(9, 108, 46, 14);
		frame.getContentPane().add(lblRechte);
		
		JLabel lblPasswort = new JLabel("Passwort:");
		lblPasswort.setBounds(9, 64, 60, 14);
		frame.getContentPane().add(lblPasswort);
		
		pass_Field = new JPasswordField();
		pass_Field.setBounds(89, 61, 105, 20);
		frame.getContentPane().add(pass_Field);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(9, 19, 60, 14);
		frame.getContentPane().add(lblUsername);
		
		txt_Username = new JTextField();
		txt_Username.setBounds(89, 16, 105, 20);
		frame.getContentPane().add(txt_Username);
		txt_Username.setColumns(10);
		
		JButton btnHinzu = new JButton("Hinzufügen");
		btnHinzu.setBounds(51, 146, 89, 23);
		btnHinzu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String Username = txt_Username.getText();
				String Passwort = pass_Field.getText();
				String Recht = comboBox_Rechte.getItemAt(comboBox_Rechte.getSelectedIndex()).toString();
				mysql.setNewSchueler("INSERT INTO `projektdb`.`benutzer` (`Username`,`Passwort`,`Recht`) VALUES('" + Username + "', '" + Passwort + "','" + Recht +"');");
			    frame.dispose();
			}
			
		});
		frame.getContentPane().add(btnHinzu);
		
		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.setBounds(51, 180, 89, 23);
		btnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
			
		});
		frame.getContentPane().add(btnAbbrechen);
	}

}
