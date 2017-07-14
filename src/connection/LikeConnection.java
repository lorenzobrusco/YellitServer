package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import tools.BaseUrl;

public class LikeConnection extends AbstractDBManager {

	public LikeConnection() {
		super();
	}

	public JSONObject isLike(final int idPost, final String email) {
		JSONObject updatedLikes = new JSONObject();
		final String query = "SELECT count(*) as total FROM " + BaseUrl.DB + "like WHERE user_email=? and post_idpost = ?; ";
		int result = 0;
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, email);
			mPreparedStatement.setInt(2, idPost);
			mPreparedStatement.execute();
			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			while (mResultSet.next()) {
				result = mResultSet.getInt("total");
			}
			updatedLikes.put("isLike", result);
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return updatedLikes;
	}

	/**
	 * Add one like
	 * 
	 * @param idPost
	 * @param email
	 * @param emailPost
	 * @return
	 */
	public JSONObject addLike(final int idPost, final String email, final String emailPost) {
		JSONObject updatedLikes = new JSONObject();
		final String query = "INSERT INTO " + BaseUrl.DB + "like (`user_email`, `post_idpost` , `post_user_email`) VALUES (?, ?, ?);";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, email);
			mPreparedStatement.setInt(2, idPost);
			mPreparedStatement.setString(3, emailPost);
			mPreparedStatement.execute();
			int updated = getLikesPost(idPost, mConnection);
			updatedLikes.put("likes", updated);
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return updatedLikes;
	}

	/**
	 * Remove like
	 * 
	 * @param idPost
	 * @param email
	 * @param emailPost
	 * @return
	 */
	public JSONObject removeLike(final int idPost, final String email, final String emailPost) {
		JSONObject updatedLikes = new JSONObject();
		final String query = "DELETE FROM " + BaseUrl.DB + "like WHERE user_email=? and post_idpost = ?;";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, email);
			mPreparedStatement.setInt(2, idPost);
			mPreparedStatement.execute();
			int updated = getLikesPost(idPost, mConnection);
			updatedLikes.put("likes", updated);
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return updatedLikes;
	}

	/**
	 * Return all likes of one post
	 * 
	 * @param idPost
	 * @param mConnection
	 * @return
	 */
	public int getLikesPost(final int idPost, Connection mConnection) {

		final String query = "select count(*) as total from " + BaseUrl.DB + "like where post_idpost = ?; ";
		int result = 0;
		try {

			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setInt(1, idPost);
			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			while (mResultSet.next()) {

				result = mResultSet.getInt("total");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
