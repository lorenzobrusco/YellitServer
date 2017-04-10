package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendConnection extends AbstractDBManager{

	public FriendConnection() {
		super();
	}

	public JSONArray getFriends(final String email) {
		final JSONArray arrayFriends = new JSONArray();
		final String query = "select * from yellit.friends where user_email = ?;";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection
					.prepareStatement(query);
			mPreparedStatement.setString(1, email);
			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			while (mResultSet.next()) {
				final JSONObject friend = new JSONObject();
				friend.put("friend",mResultSet.getString("user_email1"));
				arrayFriends.put(friend);
			}
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arrayFriends;	
	}
	

	

}
