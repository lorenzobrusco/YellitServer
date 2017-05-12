package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import connection.UserConnection;

/**
 * Servlet implementation class UploadInfo
 */
@WebServlet("/UploadInfo")
public class UploadInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String nickname = request.getParameter("nickname");
		String fullname = request.getParameter("fullname");
		String sesso = request.getParameter("sesso");
		String altezza = request.getParameter("altezza");
		String peso = request.getParameter("peso");
		String città = request.getParameter("città");
		String birthday = request.getParameter("birthday");
		String relazione = request.getParameter("relazione");
		JSONObject updated = new UserConnection().updateInfo(email, nickname, fullname, sesso, altezza, peso, città, birthday, relazione);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
