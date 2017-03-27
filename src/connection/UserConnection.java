package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserConnection extends AbstractDBManager {

	public UserConnection() {
		super();
	}

	public JSONObject loginUser(final String email, final String password) {
		final JSONObject user = new JSONObject();
		final String query = "select * from hobbiesDB.user where email = ? and password = ?;";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, email);
			mPreparedStatement.setString(2, password);
			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			while (mResultSet.next()) {
				user.put("email", mResultSet.getString("email"));
				user.put("name", mResultSet.getString("name"));
				user.put("surname", mResultSet.getString("surname"));
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

	public JSONObject createProfile(final String email, final String name, final String surname,
			final String password) {
		final JSONObject createProfile = new JSONObject();
		if (this.checkIfUserExist(email)) {
			return createProfile;
		} else {
			final String query = "INSERT INTO `hobbiesdb`.`user` (`email`, `name`, `surname`, `password`) VALUES (?, ?, ?, ?);";
			try {
				final Connection mConnection = createConnection();
				final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
				mPreparedStatement.setString(1, email);
				mPreparedStatement.setString(2, name);
				mPreparedStatement.setString(3, surname);
				mPreparedStatement.setString(4, password);
				mPreparedStatement.execute();
				createProfile.put("profile", "profile created correctly");
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return createProfile;
	}

	private boolean checkIfUserExist(final String user) {
		final String query = "select * from hobbiesDB.user where email = ?;";
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
