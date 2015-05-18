package server.model.recover;


/**
 * Interface recovering the messages of the main conversation from the server
 * organisation 
 * 
 * @author alberto
 */
public interface IMessageRecover {

	/**
	 * Recover the last messages from the main conversation
	 * 
	 * @param teamName
	 *            TODO
	 * @param numOfMessages
	 *            number of messages to recover
	 * @return A string vector with the last messages
	 * @throws NoMessagesException
	 *             TODO
	 */
	public abstract String[] recoverLastMessages(int numOfMessages) throws NoMessagesException;

	

}