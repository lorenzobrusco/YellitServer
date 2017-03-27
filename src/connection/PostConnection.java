package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostConnection extends AbstractDBManager {

	public PostConnection() {
		super();
	}

	public JSONArray getAllPost(final String data) {
		final JSONArray posts = new JSONArray();
		final String query = "select * from hobbiesDB.post where date = ?;";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, data);

			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			while (mResultSet.next()) {
				final JSONObject post = new JSONObject();
				post.put("id_post", mResultSet.getString("idPost"));
				post.put("user_nick", mResultSet.getString("user"));
				post.put("user_image", new UserConnection().getProfileImage(mResultSet.getString("user")));
				post.put("type", mResultSet.getString("type"));
				post.put("date", mResultSet.getString("date"));
				post.put("location", mResultSet.getString("location"));
				post.put("post_image", mResultSet.getString("image"));
				post.put("post_video", mResultSet.getString("video"));
				post.put("likes", mResultSet.getString("likes"));		
				posts.put(post);
			}
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return posts;
	}

	public JSONObject createPost(final String title, final String subtitle, final String type, final String date,
			final String location, final String text) {
		final JSONObject createPost = new JSONObject();
		final String query = "INSERT INTO `hobbiesdb`.`post` (`data`, `title`, `subtitle`, `type`, `text`, `location`) VALUES (?, ?, ?, ?, ?, ?, ?);";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, date);
			mPreparedStatement.setString(2, title);
			mPreparedStatement.setString(3, subtitle);
			mPreparedStatement.setString(4, type);
			mPreparedStatement.setString(5, text);
			mPreparedStatement.setString(6, location);
			mPreparedStatement.execute();
			createPost.put("post", "post created correctly");
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return createPost;
	}

	public JSONObject deletePost(final int id) {
		final JSONObject deletePost = new JSONObject();
		final String query = "delete from hobbiesDB.post where idPost = ?;";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setInt(1, id);
			deletePost.put("post", "post deleted correctly");
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return deletePost;
	}

}
