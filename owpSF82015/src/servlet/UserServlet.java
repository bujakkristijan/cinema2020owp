package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.TicketDAO;
import dao.UserDAO;
import model.Ticket;
import model.User;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String action = request.getParameter("action");
		if(action.equals("registration")){
			
			String role = request.getParameter("role");
			JSONObject obj = new JSONObject();
			
			if(role.equals("admin") || role.equals("member")) {
				obj.put("info", "errorNotAuthorized");
				response.getWriter().print(obj);	
			}else{
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				User user = new User(username, password, new Date(), "member", 0);
				
				//Validacija
				String regex ="^[a-zA-Z0-9]+$";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcherU = pattern.matcher(username);
				Matcher matcherP = pattern.matcher(password);
				
				User checkUsername = null; // ako korisnicko ime postoji uradi proveru
				UserDAO userDAO = new UserDAO();
				checkUsername = userDAO.getUserByUsername(username);
				
				if(username.equals("") || username == null || password.equals("") || password == null || matcherU.equals("false") || matcherP.equals("false")) {
					System.out.println("errorUsernamePassword");
					
					obj.put("info", "errorUsernamePassword");
					response.getWriter().print(obj);
				}else if(checkUsername != null) {
					System.out.println("errorUserExist");
					
					obj.put("info", "errorUserExist");
					response.getWriter().print(obj);
				}else {
					userDAO.insertUser(user);
					obj.put("info", "success");
					response.getWriter().print(obj);
				}
			}
			
		
		
			
			
			
		}else if(action.equals("login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			//Validacija
			String regex ="^[a-zA-Z0-9]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcherU = pattern.matcher(username);
			Matcher matcherP = pattern.matcher(password);
			
			
			JSONObject obj = new JSONObject();
			User user = null; 
			if(username.equals("") || username == null || password.equals("") || password == null || matcherU.equals("false") || matcherP.equals("false")) {
				
				
				
				obj.put("info", "errorUsernamePassword");
			//	response.getWriter().print(obj);
					
			}
			else {
			UserDAO userDAO = new UserDAO();
			user = userDAO.getUserByUsername(username);
			if(user == null) {
				obj.put("info", "errorUsernamePassword");
				//response.getWriter().print(obj);
			}
			else if(!user.getPassword().equals(password)) {
				obj.put("info", "errorPasswordFalse");
				//response.getWriter().print(obj);
				
			}else if(user.getDeleted() != 0){
				obj.put("info", "errorUserDeleted");
			}else {
				
				request.getSession().setAttribute("username", user.getUsername());
				request.getSession().setAttribute("password", user.getPassword());
				request.getSession().setAttribute("role", user.getRole());
				request.getSession().setAttribute("deleted", user.getDeleted());
				request.getSession().setAttribute("userId", user.getId());
				System.out.println("Uspesan login");
				obj.put("info", "success");
				}
			}
			
			response.getWriter().print(obj);
			
		}else if(action.equals("session")) {
			//Treba da vrati info o ulogovanom korisniku
			
			JSONObject obj = new JSONObject();
			if((request.getSession().getAttribute("username")) != null ) {
				String username = (request.getSession().getAttribute("username")).toString();
				String password = (request.getSession().getAttribute("password")).toString();
				String role = (request.getSession().getAttribute("role")).toString();
				String deleted = (request.getSession().getAttribute("deleted")).toString();
				String userId = (request.getSession().getAttribute("userId")).toString();
				obj.put("username", username);
				obj.put("password", password);
				obj.put("role", role);
				obj.put("deleted", deleted);
				obj.put("userId",userId);
				obj.put("info", "success");
			}
			else {
				
				obj.put("username", "");
				obj.put("password", "");
				obj.put("role", "");
				obj.put("deleted", "");
				obj.put("userId","");
				obj.put("info", "error");
			}
			response.getWriter().print(obj);
			
		}else if(action.equals("logout")) {
			request.getSession().invalidate();
			JSONObject obj = new JSONObject();
			obj.put("info", "success");
		
			response.getWriter().print(obj);
		}
		else if(action.equals("profile")) {
			
			JSONObject obj = new JSONObject();
			UserDAO userDAO = new UserDAO();
			if(request.getSession().getAttribute("userId")!= null) {
				
				int idLong = Integer.parseInt(request.getSession().getAttribute("userId").toString());
				User user = userDAO.getUserById(idLong);
				if(user!= null) {
					
					TicketDAO ticketDAO = new TicketDAO();
					ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
					ticketList = ticketDAO.getTicketByUserId((int)idLong);
					
					ArrayList<JSONObject> objListTicket = new ArrayList<JSONObject>();
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					for(Ticket ticket: ticketList) {
						JSONObject objTemp = new JSONObject();
						User ticketUser = new User();
						ticketUser = userDAO.getUserById((int)ticket.getUserId());
						
						objTemp.put("ticketUserId", ticket.getUserId());
						objTemp.put("ticketUsername", ticketUser.getUsername());
						objTemp.put("date", dateFormat.format(ticket.getDate()));
						objTemp.put("deleted", ticket.getDeleted());
						objTemp.put("id", ticket.getId());
						
						objListTicket.add(objTemp);
					}
					obj.put("objListTicket", objListTicket);
					obj.put("username", user.getUsername());
					obj.put("role", user.getRole());
					obj.put("password", user.getPassword());					
					obj.put("date", dateFormat.format(user.getDate()));
					obj.put("info", "success");
					response.getWriter().print(obj);
				
				}
				else {
					obj.put("info", "error");
					response.getWriter().print(obj);
				}
			}
			else {
				obj.put("info", "error");
				response.getWriter().print(obj);
			}
		}
		
		else if(action.equals("changePassword")) {
					String role = request.getParameter("role");
					JSONObject obj = new JSONObject();
					UserDAO userDAO = new UserDAO();
					
					String username = request.getParameter("username");
					String newPassword = request.getParameter("newPassword");
					
					String regex ="^[a-zA-Z0-9]+$";
					Pattern pattern = Pattern.compile(regex);
					Matcher matcherP = pattern.matcher(newPassword);
					
					User user = null; 
					user = userDAO.getUserByUsername(username);
					if(username.equals("") || username == null || newPassword.equals("") || newPassword == null ||  matcherP.equals("false")) {
				
						obj.put("info", "error");
						response.getWriter().print(obj);				
					}
					else if(user == null) {
						obj.put("info", "error");
						response.getWriter().print(obj);
					}
					else {
						//menjamo sifru u bazi
						user.setPassword(newPassword);
						userDAO.updateUser(user);
						request.getSession().setAttribute("password", user.getPassword());
						
						obj.put("info", "success");
						obj.put("newPassword", newPassword);
						response.getWriter().print(obj);
					}
						
				}
		
		else {
			System.out.println("No action choose?");
		}
	}

}
