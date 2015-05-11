package ui.login;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * The UI of the login, it extends JPanel To be corrected set up TODO
 * 
 * @author koelio
 *
 */
public class LoginUI extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton sendLogin = new JButton("Login");
	private JButton register = new JButton("Register");

	private JLabel userLabel = new JLabel("Username:");
	private JTextField userField = new JTextField(20);
	private JLabel passLabel = new JLabel("Password:");
	private JPasswordField pwdField = new JPasswordField(20);

	private JCheckBox saveCheck = new JCheckBox("Remembre Me");

	public LoginUI() {
		setLayout(new GridBagLayout());

		GridBagConstraints lim = new GridBagConstraints();

		lim.gridx = 0;
		lim.gridy = 0;
		lim.insets = new Insets(30, 20, 0, 0);
		super.add(userLabel, lim);
		userField.setFont(new Font("TimesRoman", 0, 18));
		userField.setMinimumSize(new Dimension(150, 40));
		userField.setPreferredSize(new Dimension(150, 40));
		userField.requestFocus();

		lim = new GridBagConstraints();
		lim.gridx = 1;
		lim.gridy = 0;
		lim.insets = new Insets(30, 10, 0, 20);
		super.add(userField, lim);

		lim = new GridBagConstraints();
		lim.gridx = 0;
		lim.gridy = 1;
		lim.insets = new Insets(2, 20, 0, 0);
		super.add(passLabel, lim);

		pwdField.setFont(new Font("TimesRoman", 0, 18));
		pwdField.setMinimumSize(new Dimension(150, 40));
		pwdField.setPreferredSize(new Dimension(150, 40));

		lim = new GridBagConstraints();
		lim.gridx = 1;
		lim.gridy = 1;
		lim.insets = new Insets(0, 10, 0, 20);
		super.add(pwdField, lim);

		sendLogin.setMinimumSize(new Dimension(145, 25));
		sendLogin.setPreferredSize(new Dimension(145, 25));
		register.setMinimumSize(new Dimension(145, 25));
		register.setPreferredSize(new Dimension(145, 25));

		lim = new GridBagConstraints();
		lim.gridx = 1;
		lim.gridy = 2;
		lim.insets = new Insets(5, 0, 30, 170);
		super.add(sendLogin, lim);

		lim = new GridBagConstraints();
		lim.gridx = 1;
		lim.gridy = 2;
		lim.insets = new Insets(5, 170, 30, 30);
		super.add(register, lim);

		saveCheck.setSelected(true);
		RememberMeListener saveListener = new RememberMeListener(
				saveCheck);

	}

	public void setLoginListener(ActionListener actionListener) {
		sendLogin.addActionListener(actionListener);
	}

	public void setRegisterListener(ActionListener actionListener) {
		register.addActionListener(actionListener);
	}

	public void setEnterListener(KeyListener keyListener) {
		pwdField.addKeyListener(keyListener);

	}

	public String getLoginNick() {
		return userField.getText();
	}

	public String getPass() {
		return String.valueOf(pwdField.getPassword());
	}

}
