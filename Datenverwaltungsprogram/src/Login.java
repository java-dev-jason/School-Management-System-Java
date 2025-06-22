import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Login {

	private JFrame frame;
	private MYSQL mysql = new MYSQL("127.0.0.1",3306,"projektdb");
	private JPasswordField passwordField;
	private JTextField textField;
	private String[] usernameListe = mysql.getDaten("SELECT Username FROM benutzer", "Username");
	private String[] pwListe = mysql.getDaten("SELECT Passwort FROM Benutzer", "Passwort");
	private boolean sehen = false;
	private MainWindow mainwindow = new MainWindow();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initializeLogin();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeLogin() {
		frame = new JFrame();
		frame.setBounds(100, 100, 504, 340);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPasswordField pass_Field = new JPasswordField();
		pass_Field.setBounds(171, 173, 140, 26);
		pass_Field.setEchoChar('*');
		frame.getContentPane().add(pass_Field);
		
		JLabel lblPass = new JLabel("Passwort:");
		lblPass.setBounds(123, 176, 48, 20);
		frame.getContentPane().add(lblPass);
		
		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setBounds(109, 138, 62, 20);
		frame.getContentPane().add(lblUsername);
		
		JTextField txt_username = new JTextField();
		txt_username.setBounds(171, 138, 140, 20);
		frame.getContentPane().add(txt_username);
		txt_username.setColumns(10);
		
		JButton btn_Login = new JButton("Login");
		btn_Login.setBounds(199, 225, 89, 23);
		btn_Login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < usernameListe.length; i++) {
					if(usernameListe[i].equals(txt_username.getText())) {
						if(pwListe[i].equals(pass_Field.getText())) {
							String[] rechte = mysql.getDaten("SELECT Recht From Benutzer where username = '" + usernameListe[i] + "';", "Recht");
							mainwindow.MainWindowStart(mainwindow,rechte[0]);
							frame.dispose();
						}
					}
				}
			}
			
		});
		frame.getContentPane().add(btn_Login);
		
		JButton btnAnzeige = new JButton("New button");
		btnAnzeige.setBounds(346, 158, 27, 23);
		btnAnzeige.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(sehen == true) {
					sehen = false;
					pass_Field.setEchoChar('*');
				}else {
					sehen = true;
					pass_Field.setEchoChar((char)0);
				}
			}
			
		});
		frame.getContentPane().add(btnAnzeige);
	}
}
