package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import connection.UserConnection;

/**
 * Servlet implementation class Signin
 */
@WebServlet("/Signin")
@MultipartConfig
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String file = request.getParameter("file");
		JSONObject profile = new UserConnection().createProfile(email, nickname, password, file);
		if (profile == null) {
			response.getWriter().append("");
		} else {
			response.getWriter().append(profile.toString());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String path = "http://159.203.128.152:8080/YellitServer/Images/"+ email +".png";
		/** create image and add here*/
		Part filePart = request.getPart("file");
		InputStream fileContent = filePart.getInputStream();
		BufferedImage bi = ImageIO.read(fileContent);
		String filePath = "/var/lib/tomcat8/webapps/YellitServer/Images/" + email +".png";
		File outputfile = new File(filePath);
		ImageIO.write(bi, "png", outputfile);
		JSONObject profile = new UserConnection().createProfile(email, nickname, password, "http://159.203.128.152:8080/YellitServer/Images/"+ email +".png");
		if (profile == null) {
			response.getWriter().append("");
		} else {
			response.getWriter().append(profile.toString());
		}
	}

}
