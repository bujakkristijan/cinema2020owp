package servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
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
		if(action.equals("userPage")){
			String idUserPage = request.getParameter("idUserPage");
			JSONObject obj = new JSONObject();
			UserDAO userDAO = new UserDAO();
			if(request.getSession().getAttribute("userId")!= null) {
				
				
				User user = userDAO.getUserById(Integer.parseInt(idUserPage));
				if(user!= null) {
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					TicketDAO ticketDAO = new TicketDAO();
					ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
					ticketList = ticketDAO.getTicketByUserId(Integer.parseInt(idUserPage));
					
					ArrayList<JSONObject> objListTicket = new ArrayList<JSONObject>();
					
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
			
		}else if(action.equals("changeRole")) {
			String changeRole = request.getParameter("newRole");
			String userId = request.getParameter("userId");
			String role = request.getParameter("role");
			JSONObject obj = new JSONObject();
			
			if(role.equals("admin")) {

				UserDAO userDAO = new UserDAO();
				User user = null;
				user = userDAO.getUserById(Integer.parseInt(userId));
				
					if(  changeRole.equals("") || changeRole == null  ) {
		
						obj.put("info", "error");	
						response.getWriter().print(obj);					
					}
					else if(user == null) {
						System.out.println("No user in database");
						obj.put("info", "error");	
						response.getWriter().print(obj);
					}
					else {			
						user.setRole(changeRole);				
						userDAO.updateUser(user);
						obj.put("info", "success");	
						response.getWriter().print(obj);			
					
					}
			}
			else {
				obj.put("info", "errorNotAuthorized");	
				response.getWriter().print(obj);
			}
		}else if(action.equals("deleteUser")) {
			
			String role = request.getParameter("role");
			
			JSONObject obj = new JSONObject();
			
			
			if(role.equals("admin")) {
				
				UserDAO userDAO = new UserDAO();
				int idL = Integer.parseInt(request.getParameter("userId"));
				User user = userDAO.getUserById(idL);
				TicketDAO ticketDAO = new TicketDAO();
				if(user!=null) {
					if(ticketDAO.getTicketByUserId(idL).isEmpty() == false) {
						userDAO.deleteUser(idL);
						obj.put("info", "success");	
						response.getWriter().print(obj);
					}else {
						userDAO.deleteUserFromDB(idL);
						obj.put("info", "success");	
						response.getWriter().print(obj);
					}		
				}
				else{				
					obj.put("info", "error");	
					response.getWriter().print(obj);
				}
			}else {
				obj.put("info", "errorNotAuthorized");	
				response.getWriter().print(obj);
			}
		}
		else if(action.equals("userList")) {
			
			JSONObject obj = new JSONObject();
			UserDAO userDAO = new UserDAO();
			ArrayList<User> list = new ArrayList<User>();
			list = userDAO.getAllUser();
			ArrayList<JSONObject>objList = new ArrayList<JSONObject>();
			
			for(User user: list ) {
				JSONObject objTemp = new JSONObject();
				objTemp.put("username", user.getUsername());
				objTemp.put("role", user.getRole());	
				objTemp.put("id", user.getId());
				objTemp.put("password", user.getPassword());
				objTemp.put("deleted", user.getDeleted());
				
				DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				objTemp.put("date", dateFormat.format(user.getDate()));
				objList.add(objTemp);
			}
			if(objList.size() != 0) {
				//System.out.println("User list success...");
			 	obj.put("info", "success");
				obj.put("objList", objList);
			}else {
				obj.put("info", "error");
				
			}					
			
			response.getWriter().print(obj);
			
		}
		else if(action.equals("usernameSerach")) { // odnosi se i na role search!
			
			String usernameSerach = request.getParameter("usernameSerach");
			String rolesSearch = request.getParameter("rolesSearch");
			
			System.out.println("Username Serach..." + rolesSearch);
			UserDAO userDAO = new UserDAO();
			ArrayList<User> list = new ArrayList<User>();
			ArrayList<User> listSearched = new ArrayList<User>();
			list = userDAO.getAllUser();
			JSONObject obj = new JSONObject();
			//prvo pretraga pa tretvaranje u JSON
			for(User user: list) {
				if(user.getUsername().toLowerCase().contains(usernameSerach.toLowerCase())) {
					
					if(rolesSearch.equals("admin")) {
						if(user.getRole().equals("admin")) {
							listSearched.add(user);	
						}
						
					}else if(rolesSearch.equals("member")) {
						if(user.getRole().equals("member")) {
							listSearched.add(user);	
						}
					}else {
						listSearched.add(user);	
					}
								
				}
			}
			
			ArrayList<JSONObject>objList = new ArrayList<JSONObject>();
			for(User user: listSearched ) {
				JSONObject objTemp = new JSONObject();
				objTemp.put("username", user.getUsername());
				objTemp.put("role", user.getRole());	
				objTemp.put("id", user.getId());
				objTemp.put("password", user.getPassword());
				objTemp.put("deleted", user.getDeleted());
				
				DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				objTemp.put("date", dateFormat.format(user.getDate()));
				objList.add(objTemp);
			}
			if(objList.size() != 0) {
				//System.out.println("User list success...");
			 	obj.put("info", "success");
				obj.put("objList", objList);
			}else {
				obj.put("info", "error");
				
			}					
			
			response.getWriter().print(obj);
		}
		else {
			System.out.println("No action choose?");
		}
	}

}
