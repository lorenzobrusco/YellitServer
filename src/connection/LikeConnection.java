package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.xml.internal.fastinfoset.util.StringArray;

public class LikeConnection extends AbstractDBManager {

	public LikeConnection() {
		super();
	}
	
	public JSONObject getLikeByPost(final int idPost) {
		return null;
	}
	
	public JSONArray getTips(final String mail) {
		final JSONArray tips = new JSONArray();
		final StringArray likes = new StringArray();
		final String query1 = "select post.type from post join like where like.user_email = ? and post.idPost = like.Post_idPost;";
		try {
			final Connection mConnection = createConnection();
			final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query1);
			mPreparedStatement.setString(1, mail);
			final ResultSet mResultSet = mPreparedStatement.executeQuery();
			
			while(mResultSet.next())
			{
				likes.add(mResultSet.getString("type"));
			}
			closeConnection();
			} catch (SQLException e) {
			e.printStackTrace();
			}
		
		
			int pos=0;
			int max = 0;
			for(int i=0; i != likes.getSize(); i++)
			{
				
				int count=1;
				for(int j=0; j != likes.getSize(); j++)
				{
					if(j != i && likes.get(i) == likes.get(j))
					{
						count++;
					}
				}
				if(count>max)
				{
					max=count;
					pos=i;
				}
			}
			
			final String query = "select * from post join like where type = ? and post.idPost NOT IN (select idPost from post join like where user_email = ? and post.idPost = Post_idPost); ";
			try {
				final Connection mConnection = createConnection();
				final PreparedStatement mPreparedStatement = mConnection.prepareStatement(query);
				mPreparedStatement.setString(1, likes.get(pos));
				mPreparedStatement.setString(2, mail);
				final ResultSet mResultSet = mPreparedStatement.executeQuery();
				
				while(mResultSet.next())
				{
					final JSONObject tip = new JSONObject();
					tip.put("id", mResultSet.getString("idPost"));
					tip.put("title", mResultSet.getString("title"));
					tip.put("subtitle", mResultSet.getString("subtitle"));
					tip.put("type", mResultSet.getString("type"));
					tip.put("date", mResultSet.getString("date"));
					tip.put("location", mResultSet.getString("location"));
					tip.put("text", mResultSet.getString("text"));
					tips.put(tip);
				}
				closeConnection();
			} catch (SQLException e) {
			e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		return null;		
	}

}
