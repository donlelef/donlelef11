package ui.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.ChatUI;
import ui.TimerUI;

/**
 * The UI of the chat: it's composed of a {@link ChatUI} and a {@link TimerUI}
 * 
 * @author Alberto
 *
 */

public class MainLoginUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final LoginUI loginUI = new LoginUI();
	private final RegUI regUI = new RegUI();

	public MainLoginUI() {
		super();
		super.setSize(400, 650);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints lim = new GridBagConstraints();
		lim.gridx = 0;
		lim.gridy = 1;
		panel.add(loginUI, lim);

		lim = new GridBagConstraints();
		lim.gridx = 0;
		lim.gridy = 1;
		panel.add(regUI, lim);

		lim = new GridBagConstraints();
		lim.gridx = 0;
		lim.gridy = 0;
		lim.insets = new Insets(10, 10, 10, 10);
		// lim.anchor = GridBagConstraints.ABOVE_BASELINE;

		super.getContentPane().setLayout(new BorderLayout(50, 10));
		super.getContentPane().add(panel);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (dim.getWidth() - getWidth()) / 2,
				(int) (dim.getHeight() - getHeight()) / 2);
		super.setVisible(true);
		pack();
	}

	public LoginUI getLoginUI() {
		return loginUI;
	}

	public RegUI getRegUI() {
		return regUI;
	}

	public void refresh() {
		// super.getContentPane().invalidate();
		super.getContentPane().revalidate();
		// super.getContentPane().repaint();
		super.revalidate();
		// super.repaint();
	}

	// @Override
	// public void update(Observable o, Object arg) {
	// refresh();
	// //salva credenziali
	// }

}
