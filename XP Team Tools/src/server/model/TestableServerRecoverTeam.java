package server.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import string.formatter.Formatter;

/**
 * A testable server with message recovery functions (only beetween same team members)
 * 
 * @author Alberto
 *
 */
public class TestableServerRecoverTeam extends AbstractServer {

	private HashMap<String, List<Socket>> clientMap = new HashMap<String, List<Socket>>();

	private IChatStorer chatStorer;
	private Socket clientSocket;
	private IMessageRecover recover;
	private BufferedReader in;
	public static final int NUM_OF_MESSAGES = 10;

	public TestableServerRecoverTeam(IChatStorer chatStorer,
			IMessageRecover recover) {
		super();
		this.chatStorer = chatStorer;
		this.recover = recover;
	}

	public TestableServerRecoverTeam(IChatStorer messageStorer) {
		super();
		this.chatStorer = messageStorer;
	}

	public TestableServerRecoverTeam() {
		super();
	}

	@Override
	public void listenClients() {

		try {

			while (true) {
				clientSocket = serverSocket.accept();
				setInStream();
				String teamName = groupByTeam();
				
				try {
					alignClient(teamName);
				} catch (Exception e) {
					
				}
				
				Runnable runnable = getRunnable();
				Thread thread = new Thread(runnable);
				thread.start();
			}
		} catch (IOException e) {

		}
	}


	private String groupByTeam() throws IOException {
		String teamName = in.readLine(); //TODO to test if it works; if not, see testableserverrecover
		teamName = Formatter.removeSecretCode(teamName);
		System.out.println(teamName);
		if (clientMap.containsKey(teamName)) {
			clientMap.get(teamName).add(clientSocket);
		} else {
			List<Socket> socketList = new LinkedList<Socket>();
			socketList.add(clientSocket);
			clientMap.put(teamName, socketList);
		}
		return teamName;
	}

	private void setInStream() throws IOException {
		in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
	}

	private void alignClient(String teamName) throws Exception {
		String[] messages = recoverMessages(teamName);
		for (int i = 0; i < messages.length; i++) {
			propagateMessage(messages[i], clientSocket);
		}
	}

	private Runnable getRunnable() {
		Runnable runnable = new Runnable() {
			final BufferedReader input = in;


			@Override
			public void run() {
				while (true) {
					try {

						String line = input.readLine();
						if (line != null) {
							String[] lines = line.split(Formatter.SECRET_CODE);

							String teamName = lines[0];
							String message = lines[1];
							System.out.println("Il team è: " + teamName);
							System.out.println(message); // TODO
							propagateMessageToTeamClients(message,
									clientMap.get(teamName));
							chatStorer.storeMessage(teamName, message);// TODO tutto
																// compreso il
																// team o solo
																// il messaggio?
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}

		};
		return runnable;
	}

	private void propagateMessageToTeamClients(String message,
			List<Socket> clientSocketList) throws IOException {

		for (Socket socket : clientSocketList) {
			propagateMessage(message, socket);
		}
	}

	private void propagateMessage(String message, Socket socket)
			throws IOException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		out.write(Formatter.appendNewLine(message));
		out.flush();
	}

	private String[] recoverMessages(String teamName) throws Exception {
		int numOfMessages = NUM_OF_MESSAGES;
		String[] sentMessages;

		if (recover.getNumOfMessages(teamName) < numOfMessages) {
			numOfMessages = recover.getNumOfMessages(teamName);
		}
		sentMessages = recover.recoverLastMessages(teamName, numOfMessages);

		return sentMessages;
	}

}