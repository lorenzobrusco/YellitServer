package servlet;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.ws.RequestWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import connection.PostConnection;
import connection.UserConnection;

/**
 * Servlet implementation class NewPost
 */
@WebServlet("/Posts")
@MultipartConfig
public class Posts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Posts() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String mode = request.getParameter("mode");

		if (mode != null) {
			if (mode.equals("getAll")) {
				System.out.println("Getting all posts");
				JSONArray posts = new PostConnection().getAllPost("");
				if (posts == null) {
					response.getWriter().append("");
				} else {
					response.getWriter().append(posts.toString());
				}

			} else if (mode.equals("adding")) {
				JSONObject create = this.createPost(request);
				response.getWriter().append(create.toString());
			} else {
				System.out.println("Other actions");
			}
		}

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

	private JSONObject createPost(HttpServletRequest request) {
		final JSONObject serviceManager = new JSONObject();

		try {
			String comment = request.getParameter("comment");
			String place = request.getParameter("place");
			String category = request.getParameter("category");
			String email = request.getParameter("email");
			String lat = request.getParameter("lat");
			String longi = request.getParameter("longi");

			Part filePart = request.getPart("file");
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			InputStream fileContent = filePart.getInputStream();
			System.out.println(request.getRequestURI().toString() + "&" + email + "&" + comment + "&" + place + "&"
					+ category + "&" + lat + "&" + longi + "&" + fileName);
			serviceManager.put("message", "Sto per creare");

			BufferedImage bi = ImageIO.read(fileContent);
			String filePath = "/var/lib/tomcat8/webapps/YellitServer/Images/" + fileName;
			String databasePath = "http://159.203.128.152:8080/YellitServer/Images/" + fileName;

			File outputfile = new File(filePath);
			ImageIO.write(bi, "png", outputfile);

			boolean messageCreated = new PostConnection().createPost(category, place, databasePath, email, comment, lat,
					longi);

			serviceManager.put("success", messageCreated);
			if (messageCreated) {
				serviceManager.put("message", "creazione OK");
			} else {
				serviceManager.put("message", "creazione FALLITA");
			}

		} catch (IOException e) {

			e.printStackTrace();

		} catch (JSONException e) {

			e.printStackTrace();

		} finally {

			return serviceManager;

		}
	}

}
