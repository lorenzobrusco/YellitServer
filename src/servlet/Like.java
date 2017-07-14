package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import connection.LikeConnection;

/**
 * Servlet implementation class Like
 */
@WebServlet("/Like")
public class Like extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Like() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String typeLike = request.getParameter("typeLike");
		if (typeLike.equals("1") || typeLike.equals("0")) {
			String email = request.getParameter("email");
			String idPost = request.getParameter("idPost");
			String emailPost = request.getParameter("emailPost");
			JSONObject like = typeLike.equals("1")
					? new LikeConnection().addLike(Integer.parseInt(idPost), email, emailPost)
					: new LikeConnection().removeLike(Integer.parseInt(idPost), email, null);
			response.getWriter().append(like.toString());
		} else if (typeLike.equals("2")) {
			String email = request.getParameter("email");
			String idPost = request.getParameter("idPost");
			JSONObject isLike = new LikeConnection().isLike(Integer.parseInt(idPost), email);
			response.getWriter().append(isLike.toString());
		} else
			response.getWriter().append("some error occurred");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
