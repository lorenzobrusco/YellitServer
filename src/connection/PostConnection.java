package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.BaseUrl;

public class PostConnection extends AbstractDBManager {

	public PostConnection() {
		super();
	}

	/**
	 * Return all posts
	 * @param data useless for now
	 * @return
	 */
	public JSONArray getAllPost(final String data) {
		final JSONArray postsList = new JSONArray();
		final String query = "select * from post;";
		try {

			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			while (mResultSet.next()) {

				final JSONObject post = new JSONObject();
				post.put("id_post", mResultSet.getString("idPost"));
				post.put("user_nick", mResultSet.getString("user_email"));
				post.put("type", mResultSet.getString("type"));
				post.put("date", mResultSet.getString("date"));
				post.put("position", mResultSet.getString("position"));
				post.put("post_image", mResultSet.getString("image"));
				post.put("comment", mResultSet.getString("text"));
				post.put("post_video", "null");
				post.put("likes", "null");		
				postsList.put(post);
			}

			for(int i = 0; i < postsList.length(); i++)
			{
				JSONObject ppp = (JSONObject) postsList.get(i);
				final String q = "select nickname,userimage from user where email = ?;";

				final PreparedStatement prepState = mConnection.prepareStatement(q);
				prepState.setString(1, ppp.getString("user_nick"));

				final ResultSet resSet = prepState.executeQuery();
				while (resSet.next()) {

					ppp.put("user_nick", resSet.getString("nickname"));
					ppp.put("user_image", resSet.getString("userimage"));

				}
			}
			
			for(int i = 0; i < postsList.length(); i++)
			{
				JSONObject ppp = (JSONObject) postsList.get(i);				
				String idPostString = (String) ppp.getString("id_post");
				int idPost = Integer.parseInt(idPostString);
				
				final int likes = new LikeConnection().getLikesPost(idPost, mConnection);
				ppp.put("likes", likes+"");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}


		closeConnection();


		return postsList;
	}

	/**
	 * Create a new post
	 * @param type
	 * @param position location
	 * @param image path
	 * @param userEmail
	 * @param text comment
	 * @param lat latitude
	 * @param longi longitude
	 * @return true if post is created
	 */
	public boolean createPost(final String type, final String position, final String image, final String userEmail,
			final String text, final String lat, final String longi) {

		final String query = "INSERT INTO post (post.type, post.position, post.image, post.user_email, post.text, post.lat, post.longi) VALUES (?, ?, ?, ?, ?, ?, ?);";
		try {

			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, type);
			mPreparedStatement.setString(2, position);
			mPreparedStatement.setString(3, image);
			mPreparedStatement.setString(4, userEmail);
			mPreparedStatement.setString(5, text);
			mPreparedStatement.setString(6, lat);
			mPreparedStatement.setString(7, longi);
			mPreparedStatement.execute();

			closeConnection();

			return true;

		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		} 		
	}

	/**
	 * Delete post
	 * @param id
	 * @return
	 */
	public JSONObject deletePost(final int id) {
		final JSONObject deletePost = new JSONObject();
		final String query = "delete from " + BaseUrl.DB + "post where idPost = ?;";
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

	/**
	 * Get all Post that user published
	 * @param email
	 * @return
	 */
	public JSONArray getPersonalPost(final String email) {
		final JSONArray posts = new JSONArray();
		final String query = "select count(*), type from post where user_email = ? group by type ;";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
			mPreparedStatement.setString(1, email);

			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			while (mResultSet.next()) {
				final JSONObject post = new JSONObject();
				post.put("", mResultSet.getString("count(*)"));
				post.put("", mResultSet.getString("type"));	

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

}
