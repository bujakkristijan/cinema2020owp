package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.HallDAO;
import dao.MovieDAO;
import dao.ProjectionDAO;
import dao.ProjectionTypeDAO;
import dao.SeatDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Hall;
import model.Movie;
import model.Projection;
import model.ProjectionType;
import model.Seat;
import model.Ticket;
import model.User;

/**
 * Servlet implementation class ProjectionServlet
 */
public class ProjectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectionServlet() {
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
		
		
		if(action.equals("projectionPage")){
			
			int id = Integer.parseInt(request.getParameter("projectionId"));
			ProjectionDAO projectionDAO = new ProjectionDAO();
			MovieDAO movieDAO = new MovieDAO();
			HallDAO hallDAO = new HallDAO();
			ProjectionTypeDAO projectionTypeDAO = new ProjectionTypeDAO();
			Movie movie = new Movie();
			Hall hall = new Hall();
			ProjectionType projectionType = new ProjectionType();
			
			Projection projection = projectionDAO.getProjectionById(id);
			JSONObject obj = new JSONObject();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			
			if(projection != null) {
				
				movie = movieDAO.getMovieById((int)(projection.getMovieId()));
				projectionType = projectionTypeDAO.getProjectionTypeById((int)projection.getProjectionTypeId());
				hall = hallDAO.getHallById((int)(projection.getHallId()));
				
				// uzima ukupna broj sedista koje postoje za taj hallId i onda od toga oduzima se broj karata koje su prodate za tu projekciju
				// na primer ako hala ima 10 sedista a za neku projekciju je prodato 3 karte, znaci da je broj slobodnih sedista 7
				int numberOfSeat = 0;
				int numberOfTicket = 0;
				
				SeatDAO seatDAO = new SeatDAO();
				UserDAO userDAO = new UserDAO();
				ArrayList<Seat> seatList = new ArrayList<Seat>();
				seatList = seatDAO.getSeatByHallId((int)(projection.getHallId()));
				numberOfSeat = seatList.size();
				
				TicketDAO ticketDAO = new TicketDAO();
				ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
				ticketList = ticketDAO.getTicketByProjectionId((int)projection.getId());
				numberOfTicket = ticketList.size();
				
				int unsoldSeat = numberOfSeat - numberOfTicket;
				//kraj prebrojvanja karata
				
				//ubacivanje karata u tabelu
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
				
				obj.put("movieId", movie.getId());
				obj.put("movieName", movie.getName());
				obj.put("projectionTypeName", projectionType.getName());
				obj.put("hallName", hall.getName());
				
				obj.put("id", projection.getId());
				obj.put("price", projection.getPrice());
				obj.put("date", dateFormat.format(projection.getDate()));
				
				obj.put("unsoldSeat", unsoldSeat); 
				obj.put("deleted", projection.getDeleted());
				obj.put("info", "success");
			}
			else {
				obj.put("info", "error");
			}
			response.getWriter().print(obj);	
		}
		else if(action.equals("projectionInsertPrepare")) {
			
			ProjectionTypeDAO projectionTypeDAO = new ProjectionTypeDAO();
			MovieDAO movieDAO = new MovieDAO();
			HallDAO hallDAO = new HallDAO();
			
			ArrayList<JSONObject> objListMovie = new ArrayList<JSONObject>();
			ArrayList<JSONObject> objListHall = new ArrayList<JSONObject>();
			ArrayList<JSONObject> objListType = new ArrayList<JSONObject>();
			JSONObject objTemp = new JSONObject();
			JSONObject obj = new JSONObject();
			
			ArrayList<Movie> movieList = new ArrayList<Movie>();
			ArrayList<ProjectionType> projectionTypeList = new ArrayList<ProjectionType>();
			ArrayList<Hall> hallList = new ArrayList<Hall>();
			
			movieList = movieDAO.getAllMovie();
			projectionTypeList = projectionTypeDAO.getAllProjectionType();
			hallList = hallDAO.getAllHall();
			
			for(Movie movie: movieList) {
				objTemp = new JSONObject();
				objTemp.put("movieId", movie.getId());
				objTemp.put("movieName", movie.getName());
				objTemp.put("deleted", movie.getDeleted());
				objListMovie.add(objTemp);
			}
			for(Hall hall: hallList) {
				objTemp = new JSONObject();
				objTemp.put("hallId", hall.getId());
				objTemp.put("hallName", hall.getName());
				objListHall.add(objTemp);
			}
			
			for(ProjectionType projectionType: projectionTypeList) {
				objTemp = new JSONObject();
				objTemp.put("projectionTypeId", projectionType.getId());
				objTemp.put("projectionTypeName", projectionType.getName());
				objListType.add(objTemp);	
			}
			
			if(objListMovie.size() != 0 && objListType.size() != 0 && objListHall.size() != 0) {
				obj.put("objListMovie", objListMovie);
				obj.put("objListType", objListType);
				obj.put("objListHall", objListHall);
				obj.put("info", "success");
	
			}
			else {
				obj.put("info", "error");
			}
			response.getWriter().print(obj);
			
		}else if(action.equals("checkHall")) {
			String hallId = request.getParameter("hallId");
		
			HallDAO hallDAO = new HallDAO();
			Hall hall = new Hall();
			ArrayList<JSONObject> objListType = new ArrayList<JSONObject>();
			
			JSONObject objTemp = new JSONObject();
			JSONObject obj = new JSONObject();
			
			hall = hallDAO.getHallById(Integer.parseInt(hallId));
			for(ProjectionType projectionType: hall.getProjectionTypeList()) {
				objTemp = new JSONObject();
				objTemp.put("projectionTypeId", projectionType.getId());
				objTemp.put("projectionTypeName", projectionType.getName());
				objListType.add(objTemp);
			}
			if( objListType.size() != 0) {			
				obj.put("objListType", objListType);
				obj.put("info", "success");
	
			}
			else {
				obj.put("info", "error");
			}
			response.getWriter().print(obj);
		
		}
		
		else if(action.equals("insertProjection")) {
			
			String role = request.getParameter("role");
			JSONObject obj = new JSONObject();
			
			if(role.equals("admin")) {

				ProjectionDAO projectionDAO = new ProjectionDAO();
				ProjectionTypeDAO projectionTypeDAO = new ProjectionTypeDAO();
				MovieDAO movieDAO = new MovieDAO();
				HallDAO hallDAO = new HallDAO();
				
				
				String movie = request.getParameter("movie");
				String hall = request.getParameter("hall");
				String projectionType = request.getParameter("projectionType");
				String price = request.getParameter("price");
				String date = request.getParameter("date"); // 2020-08-14 u ovom formatu primamo string
				String time = request.getParameter("time");
				String userId = request.getParameter("userId");
				
				if(movie == null || movie.equals("") || hall == null || hall.equals("") || projectionType == null || projectionType.equals("") || userId == null || userId.equals("") ||
						price == null || price.equals("") || date == null || date.equals("") || time == null || time.equals("")) {
					obj.put("info", "error");
					response.getWriter().print(obj);
				}
				else {
					try {
						Date startProjectionInputDate = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
						startProjectionInputDate = dateFormat.parse(date + " " + time + ":00");
						Date currentDate = new Date();
						double priceInput = Double.parseDouble(price);
						Movie movieInput = new Movie();
						Hall hallInput = new Hall();
						ProjectionType projectionTypeInput = new ProjectionType();
						
						movieInput = movieDAO.getMovieById(Integer.parseInt(movie));		
						projectionTypeInput = projectionTypeDAO.getProjectionTypeById(Integer.parseInt(projectionType));		
						hallInput = hallDAO.getHallById(Integer.parseInt(hall));		
						
						Date endProjectionInputDate = new Date();
						Calendar cal = Calendar.getInstance();
						
						cal.setTime(startProjectionInputDate);
						cal.add(Calendar.MINUTE, movieInput.getDuration()); // dodaje minute na pocetak projekcije za dodavanje
						endProjectionInputDate = cal.getTime();
						
						//String startDateString = dateFormat.format(startProjectionInputDate);
						//String endDateString = dateFormat.format(endProjectionInputDate);
						
						String accepted = hallDAO.checkIsAcceptable(startProjectionInputDate, endProjectionInputDate, Integer.parseInt(hall));
						
						//System.out.println("Accepted :" + accepted + "Start date: " + startDateString + "End date:" + endDateString );
						
						if(accepted.equals("yes") && startProjectionInputDate.after(currentDate)) {
							Projection projection = new Projection(Long.parseLong(movie), Long.parseLong(projectionType), Long.parseLong(hall), startProjectionInputDate, priceInput,  Long.parseLong(userId), 0 );
							projectionDAO.insertProjection(projection);
							obj.put("info", "success");
						}else{
							obj.put("info", "errorNotAvailable");
						}
						response.getWriter().print(obj);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else {
				obj.put("info", "errorNotAuthorized");
				response.getWriter().print(obj);
			}
			
			
		}else if(action.equals("deleteProjection")) { 
			
			String role = request.getParameter("role");
			JSONObject obj = new JSONObject();
			TicketDAO ticketDAO = new TicketDAO();
			if(role.equals("admin")) {
				ProjectionDAO projectionDAO = new ProjectionDAO();
				
				
				int projectionId = Integer.parseInt(request.getParameter("projectionId"));
				Projection projection = projectionDAO.getProjectionById(projectionId);
				if(projection!=null) {
					if(ticketDAO.getTicketByProjectionId(projectionId).isEmpty() == false) {
						projectionDAO.deleteProjection(projectionId);
						obj.put("info", "success");
						response.getWriter().print(obj);
					}else {
						projectionDAO.deleteProjectionFromDB(projectionId);
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
			
		}
		else if(action.equals("getProjection")) {
			
			int id = Integer.parseInt(request.getParameter("projectionId"));
			ProjectionDAO projectionDAO = new ProjectionDAO();
			MovieDAO movieDAO = new MovieDAO();
			HallDAO hallDAO = new HallDAO();
			ProjectionTypeDAO projectionTypeDAO = new ProjectionTypeDAO();
			Movie movie = new Movie();
			Hall hall = new Hall();
			ProjectionType projectionType = new ProjectionType();
			
			Projection projection = projectionDAO.getProjectionById(id);
			JSONObject obj = new JSONObject();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			
			if(projection != null) {
				
				movie = movieDAO.getMovieById((int)(projection.getMovieId()));
				projectionType = projectionTypeDAO.getProjectionTypeById((int)projection.getProjectionTypeId());
				hall = hallDAO.getHallById((int)(projection.getHallId()));
				
				
				SeatDAO seatDAO = new SeatDAO();
				UserDAO userDAO = new UserDAO();
				ArrayList<Seat> seatList = new ArrayList<Seat>();
				seatList = seatDAO.getSeatByHallId((int)(projection.getHallId()));
				TicketDAO ticketDAO = new TicketDAO();
				ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
				ticketList = ticketDAO.getTicketByProjectionId((int)projection.getId());
				
				ArrayList<JSONObject> objListTicket = new ArrayList<JSONObject>();
				ArrayList<JSONObject> objListSeat = new ArrayList<JSONObject>();
				
				
				
				for(Ticket ticket: ticketList) {
					
					JSONObject objTemp = new JSONObject();
					objTemp.put("deleted", ticket.getDeleted());
					objTemp.put("ticketSeatNumber", ticket.getSeatNumber()); // vraca se sediste koje ima prodatu kartu
					
					objListTicket.add(objTemp);
				}
				
				for(Seat seat: seatList) {
									
					JSONObject objTemp = new JSONObject();
					objTemp.put("seatNumber", seat.getNumber()); // vraca sva sedista za tu salu 
					
					objListSeat.add(objTemp);
				}
				if(objListSeat.size()!=0) {
					
					obj.put("objListTicket", objListTicket);
					obj.put("objListSeat", objListSeat );
					obj.put("projectionTypeName", projectionType.getName());
					obj.put("hallName", hall.getName());
					obj.put("id", projection.getId());
					obj.put("price", projection.getPrice());
					obj.put("date", dateFormat.format(projection.getDate()));
					obj.put("deleted", projection.getDeleted());
					obj.put("info", "success");
				}
				else {
					obj.put("info", "error");
				}
				
			}
			else {
				obj.put("info", "error");
			}
			response.getWriter().print(obj);	
		}
		else {
			System.out.println("No action choose?");
		}
		
	}

}
