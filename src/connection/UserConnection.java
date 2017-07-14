package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.BaseUrl;

public class UserConnection extends AbstractDBManager {

	public UserConnection() {
		super();
	}

	/**
	 * Login user
	 * @param email
	 * @param password
	 * @return
	 */
	public JSONObject loginUser(final String email, final String password) {
		final JSONObject user = new JSONObject();
		final String query = "select * from " + BaseUrl.DB + "user where email = ? and password = ?;";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, email);
			mPreparedStatement.setString(2, password);
			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			while (mResultSet.next()) {
				user.put("email", mResultSet.getString("email"));
				user.put("nickname", mResultSet.getString("nickname"));
				user.put("fullname", mResultSet.getString("fullname"));
				user.put("password", mResultSet.getString("password"));
				user.put("userimage", mResultSet.getString("userimage"));
			}
			closeConnection();
//			JSONArray friends = new FriendConnection().getFriends(email);
//			user.put("friends", friends);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Login when user doesn't contain password, like user's facebook
	 * @param email
	 * @return
	 */
	public JSONObject loginUser(final String email) {
		final JSONObject user = new JSONObject();
		final String query = "select * from " + BaseUrl.DB + "user where email = ?;";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, email);
			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			while (mResultSet.next()) {
				user.put("email", mResultSet.getString("email"));
				user.put("nickname", mResultSet.getString("nickname"));
				user.put("fullname", mResultSet.getString("fullname"));
				user.put("userimage", mResultSet.getString("userimage"));
			}
			closeConnection();
			JSONArray friends = new FriendConnection().getFriends(email);
			user.put("friends", friends);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Useless for now
	 * @param email
	 * @param nickname
	 * @param fullname
	 * @param sesso
	 * @param altezza
	 * @param peso
	 * @param città
	 * @param birthday
	 * @param relazione
	 * @return
	 */
	public JSONObject updateInfo(final String email, final String nickname, final String fullname, final String sesso, final String altezza, final String peso, final String città, final String birthday, final String relazione) {
		final JSONObject user = new JSONObject();
		final String query = "update " + BaseUrl.DB + "user set nickname=?, fullname=?, sesso=?, altezza=?, peso=?, città=?, birthday=?, relazione=? where email = ?;";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, nickname);
			mPreparedStatement.setString(2, fullname);
			mPreparedStatement.setString(3, sesso);
			mPreparedStatement.setString(4, altezza);
			mPreparedStatement.setString(5, peso);
			mPreparedStatement.setString(6, città);
			mPreparedStatement.setString(7, birthday);
			mPreparedStatement.setString(8, relazione);
			mPreparedStatement.setString(8, email);
			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			while (mResultSet.next()) {
				user.put("email", mResultSet.getString("email"));
				user.put("nickname", mResultSet.getString("nickname"));
				user.put("fullname", mResultSet.getString("fullname"));
				user.put("userimage", mResultSet.getString("userimage"));
			}
			closeConnection();
			JSONArray friends = new FriendConnection().getFriends(email);
			user.put("friends", friends);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Create a profile 
	 * @param email
	 * @param nickname
	 * @param password
	 * @param userimage
	 * @return
	 */
	public JSONObject createProfile(final String email, final String nickname, final String password, final String userimage) {
		JSONObject createProfile = new JSONObject();
		if (this.checkIfUserExist(email)) {
			System.out.println();
			return createProfile;
		} else {
			final String query = "INSERT INTO " + BaseUrl.DB + "user (`email`, `nickname`, `password`, `userimage`) VALUES (?, ?, ?, ?);";
			try {
				final Connection mConnection = createConnection();
				final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
				mPreparedStatement.setString(1, email);
				mPreparedStatement.setString(2, nickname);
				mPreparedStatement.setString(3, password);
				mPreparedStatement.setString(4, userimage);
				mPreparedStatement.execute();
				createProfile = loginUser(email, password);
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		return createProfile;
	}

	/**
	 * Check if user exists in database
	 * @param user
	 * @return
	 */
	private boolean checkIfUserExist(final String user) {
		final String query = "select * from " + BaseUrl.DB + "user where email = ?;";
		boolean exist = false;
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, user);
			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			if (mResultSet.isBeforeFirst()) {
				exist = true;
			}
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

	/**
	 *  Return user's image profile
	 * @param user
	 * @return
	 */
	public String getProfileImage(final String user) {
		if (checkIfUserExist(user)) {
			String image = null;
			final String query = "select path_image from user where nick = ?;";
			try {
				final Connection mConnection = createConnection();
				final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
				mPreparedStatement.setString(1, user);
				final ResultSet mResultSet = mPreparedStatement.executeQuery();
				while (mResultSet.next()) {
					image = mResultSet.getString("path_image");
				}
				closeConnection();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return image;
		}
		return null;
	}
	
}
