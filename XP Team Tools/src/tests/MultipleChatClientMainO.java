package tests;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.json.simple.parser.ParseException;

import protocol.JsonMaker;
import protocol.JsonParser;
import ui.TeamListUI;
import ui.login.LoginUI;
import ui.login.MainLoginUI;
import ui.login.RegUI;
import client.model.ClientConnectionDetails;
import client.model.StrategyClient1_1;

/**
 * This class, with clientMain and serverMain, tests the communication between 2
 * Clients and 1 Server
 * 
 * @author alberto
 *
 */
public class MultipleChatClientMainO {
	public static void main(String[] args) {

		final MainLoginUI ui = new MainLoginUI();
		final LoginUI login = ui.getLoginUI();

		final RegUI reg = ui.getRegUI();
		reg.setVisible(false);

		login.setEnterListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out
							.println("Fai la stessa cosa del setLoginButton ");
					// TODO estrarre tutto il launcher della chat
				}

			}
		});

		login.setLoginListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final StrategyClient1_1 client = new StrategyClient1_1(
						new ClientConnectionDetails(login.getLoginNick(),
								null, login.getPass()));

				client.openStreams("localhost", 9999);
				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						try {

							client.readFromSocket();

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};

				Thread thread = new Thread(runnable);
				thread.start();
				System.out.println("here " + MultipleChatClientMainO.class);
				client.sendMessageToServer(JsonMaker.teamsListRequest(client.getNickname()));
				System.out.println("here1 " + MultipleChatClientMainO.class);

				String response = client.waitServerResponse();
				System.out.println("here2 " + MultipleChatClientMainO.class);

				String[] teams;
				try {
					teams = JsonParser.parseMakeTeamMembs(response);
					final TeamListUI teamListUI = new TeamListUI(client, teams);
					teamListUI.setCreateListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							client.sendMessageToServer(JsonMaker.newTeamRequest(
									teamListUI.getTeamName(), client.getNickname()));
							final int index = JsonParser.parseChatIndexRequest(client
									.waitServerResponse());
							client.sendMessageToServer(JsonMaker.teamsListRequest(client.getNickname()));
							teamListUI.setIndex(index);
							teamListUI.removeTeamPanel();
							try {
								teamListUI.fillTeamPane(JsonParser.parseListOfTeamsRequest(client.waitServerResponse()));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							teamListUI.refresh();
						}
					});

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				ui.dispose();
			}
		});

		login.setRegisterListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				login.setVisible(false);
				reg.setVisible(true);
				ui.refresh();
			}
		});

		reg.setBackLoginListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				reg.setVisible(false);
				login.setVisible(true);
				ui.refresh();
			}
		});

	}
}
