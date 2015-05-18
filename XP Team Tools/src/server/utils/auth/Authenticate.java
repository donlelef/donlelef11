package server.utils.auth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * A class for the authentication of a client who requires the connection 
 * 
 * @author
 *
 */
public class Authenticate {

	private final static int ITERATION_NUMBER = 1000;
	private Hash hasher = new Hash();

	/**
	 * Authenticates the user and the validation of database through a series of controls 
	 * @param con
	 * @param login
	 * @param password
	 * @return true if the authentication is OK, false otherwise
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean authenticate(Connection con, String login, String password)
			throws SQLException, NoSuchAlgorithmException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			boolean userExist = true;
			/**
			 * input validation
			 */
			if (login == null || password == null) {
				
				userExist = false;
				login = "";
				password = "";
			}

			ps = con.prepareStatement("SELECT PASSWORD, SALT FROM CREDENTIAL WHERE USER = ?");
			ps.setString(1, login);
			rs = ps.executeQuery();
			String digest, salt;
			if (rs.next()) {
				digest = rs.getString("PASSWORD");
				salt = rs.getString("SALT");
				if (digest == null || salt == null) {
					throw new SQLException(
							"Database inconsistant Salt or Digested Password altered");
				}
				if (rs.next()) { 
					throw new SQLException(
							"Database inconsistent two CREDENTIALS with the same USER");
				}
			} else {
				digest = "000000000000000000000000000=";
				salt = "00000000000=";
				userExist = false;
			}

			byte[] bDigest = BaseToByteViceversa.base64ToByte(digest);
			byte[] bSalt = BaseToByteViceversa.base64ToByte(salt);

			
			byte[] proposedDigest = hasher.getHash(ITERATION_NUMBER, password,
					bSalt);

			return Arrays.equals(proposedDigest, bDigest) && userExist;
		} catch (IOException ex) {
			throw new SQLException(
					"Database inconsistant Salt or Digested Password altered");
		} finally {
			close(rs);
			close(ps);
		}
	}

	/**
	 * Useful for new deployments
	 * 
	 * @param con
	 * @throws SQLException
	 */
	public void createTable(Connection con) throws SQLException {
		Statement st = null;
		try {
			st = con.createStatement();
			st.execute("CREATE TABLE CREDENTIAL (USER VARCHAR(100) PRIMARY KEY, PASSWORD VARCHAR(32) NOT NULL, SALT VARCHAR(32) NOT NULL, DATUM DATE NOT NULL, COMMENTS VARCHAR(400) NOT NULL )");
		} finally {
			close(st);
		}
	}

	/**
	 * Closes the current statement
	 * 
	 * @param ps
	 *            Statement
	 */
	public void close(Statement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException ignore) {
			}
		}
	}

	/**
	 * Closes the current resultset
	 * 
	 * @param ps
	 *            Statement
	 */
	public void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ignore) {
			}
		}
	}

}