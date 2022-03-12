package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.HallDAO;
import dao.MovieDAO;
import dao.ProjectionDAO;
import dao.ProjectionTypeDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Hall;
import model.Movie;
import model.Projection;
import model.ProjectionType;
import model.Ticket;
import model.User;

/**
 * Servlet implementation class TicketServlet
 */
public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketServlet() {
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
		String action = request.getParameter("action");
		if(action.equals("ticketPage")){
			int id = Integer.parseInt(request.getParameter("ticketId"));
			
			ProjectionDAO projectionDAO = new ProjectionDAO();
			MovieDAO movieDAO = new MovieDAO();
			HallDAO hallDAO = new HallDAO();
			ProjectionTypeDAO projectionTypeDAO = new ProjectionTypeDAO();
			Movie movie = new Movie();
			Hall hall = new Hall();
			ProjectionType projectionType = new ProjectionType();
			TicketDAO ticketDAO = new TicketDAO();
			Ticket ticket = new Ticket();
			UserDAO userDAO = new UserDAO();
			
			ticket = ticketDAO.getTicketById(id);
			
			JSONObject obj = new JSONObject();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
			if(ticket != null) {
				
				User user = userDAO.getUserById((int)ticket.getUserId());
				Projection projection = projectionDAO.getProjectionById((int)ticket.getProjectionId());
				
				if(projection != null && user !=null) {
					movie = movieDAO.getMovieById((int)(projection.getMovieId()));
					hall = hallDAO.getHallById((int)(projection.getHallId()));
					projectionType = projectionTypeDAO.getProjectionTypeById((int)projection.getProjectionTypeId());
					
					obj.put("movieId", movie.getId());
					obj.put("movieName", movie.getName());
					obj.put("projectionTypeName", projectionType.getName());
					obj.put("hallName", hall.getName());
					obj.put("projectionId", projection.getId());
					obj.put("seat", ticket.getSeatNumber());
					obj.put("username", user.getUsername());
					obj.put("userId", ticket.getUserId());
					obj.put("price", projection.getPrice());
					obj.put("date", dateFormat.format(projection.getDate()));	
					obj.put("deleted", ticket.getDeleted());
					obj.put("info", "success");
				}else {
					obj.put("info", "error");
				}
				
				
				
			}
			else {
				obj.put("info", "error");
			}
			response.getWriter().print(obj);	
		
		}else if(action.equals("deleteTicket")) {
			String role = request.getParameter("role");
			JSONObject obj = new JSONObject();
			Date currentDate = new Date();
			Date projectionDateTime = new Date();
			ProjectionDAO projectionDAO = new ProjectionDAO();
			if(role.equals("admin")) {
				TicketDAO ticketDAO = new TicketDAO();
				
				
				int ticketId = Integer.parseInt(request.getParameter("ticketId"));
				Ticket ticket = ticketDAO.getTicketById(ticketId);
				if(ticket!=null) {
					projectionDateTime = (projectionDAO.getProjectionById((int)ticket.getProjectionId())).getDate() ;
					if(currentDate.after(projectionDateTime)) {
						obj.put("info", "errorProjectionInPast");
						response.getWriter().print(obj);
					}else {
						ticketDAO.deleteTicket(ticketId);
						obj.put("info", "success");
						response.getWriter().print(obj);
					}
					
				}else {
					obj.put("info", "error");
					response.getWriter().print(obj);
				}
			}else {
				obj.put("info", "errorNotAuthorized");
				response.getWriter().print(obj);
			}
			
			
		}else if(action.equals("buyTicket")){
			String role = request.getParameter("role");
			JSONObject obj = new JSONObject();
			if(role.equals("member")) {

				String movieId = request.getParameter("movieId");
				String projectionId = request.getParameter("projectionId");
				String listChecked = request.getParameter("listChecked");
				String userId = request.getParameter("userId");
				TicketDAO ticketDAO = new TicketDAO();
				
				Projection projection = new Projection();
				ProjectionDAO projectionDAO = new ProjectionDAO();
				
				
				
				if(movieId == null || movieId.equals("") || projectionId == null || projectionId.equals("") || listChecked == null || listChecked.equals("") || 
						userId == null || userId.equals("")) {
					obj.put("info", "error");
					response.getWriter().print(obj);
				}else {
					
					try {
						
						Date currentDate = new Date();
						Date projectionDate = new Date();
						projectionDate = projectionDAO.getProjectionById(Integer.parseInt(projectionId)).getDate();
						if(currentDate.after(projectionDate)) {
							obj.put("info", "errorDateTime");
							response.getWriter().print(obj);
						}else {
							String[] seatStr = listChecked.split(",");// "1,2,3" -> {"1", "2", "3"}
							List<String> seatList = new ArrayList<String>(Arrays.asList(seatStr));	
							for(String s: seatList) {
								Ticket ticket = new Ticket(Long.parseLong(projectionId), Long.parseLong(s), new Date(), Long.parseLong(userId), 0);
								ticketDAO.insertTicket(ticket);
							}
							obj.put("info", "success");
							response.getWriter().print(obj);
						}
						
						
						
					}catch (Exception e) {
						// TODO: handle exception
						obj.put("info", "error");
						response.getWriter().print(obj);
						e.printStackTrace();
					}
				}
			}
			else {
				obj.put("info", "errorNotAuthorized");
				response.getWriter().print(obj);
			}
		}
		else {
			System.out.println("No action choose?");
		}
	}

}
