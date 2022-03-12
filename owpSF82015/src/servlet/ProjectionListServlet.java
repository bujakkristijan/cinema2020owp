package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

/**
 * Servlet implementation class ProjectionListServlet
 */
public class ProjectionListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectionListServlet() {
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
		
		if(action.equals("projectionList")){
			
			ProjectionDAO projectionDAO = new ProjectionDAO();
			MovieDAO movieDAO = new MovieDAO();
			HallDAO hallDAO = new HallDAO();
			ProjectionTypeDAO projectionTypeDAO = new ProjectionTypeDAO();
			
			Movie movie = new Movie();
			Hall hall = new Hall();
			ProjectionType projectionType = new ProjectionType();
			ArrayList<Projection> list = new ArrayList<Projection>();
			list = projectionDAO.getAllProjectionForToday();
			
			JSONObject obj = new JSONObject();
			ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
			
			for(Projection projection: list ) {
				
				JSONObject objTemp = new JSONObject();
				movie = movieDAO.getMovieById((int)(projection.getMovieId()));
				projectionType = projectionTypeDAO.getProjectionTypeById((int)projection.getProjectionTypeId());
				hall = hallDAO.getHallById((int)(projection.getHallId()));
				
				
				
				objTemp.put("movieId", movie.getId());
				objTemp.put("movieName", movie.getName());
				objTemp.put("projectionTypeName", projectionType.getName());	
				objTemp.put("hallName", hall.getName());
				
				objTemp.put("id", projection.getId());
				objTemp.put("price", projection.getPrice());
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
				objTemp.put("date", dateFormat.format(projection.getDate()));
				objTemp.put("deleted", projection.getDeleted());
				
				objList.add(objTemp);
			}
			if(objList.size() != 0) {
				
			 	obj.put("info", "success");
				obj.put("objList", objList);
			}else {
				obj.put("info", "error");
				
			}					
			
			response.getWriter().print(obj);
			
			
			
		}if(action.equals("projectionListBuyTicket")){ //na prodaji karte vraca se lista projekcija koje nisu rasprodane i kojima datum nije prosao i za odredjeni film
			
			String movieId = request.getParameter("movieId");
			
			ProjectionDAO projectionDAO = new ProjectionDAO();
			MovieDAO movieDAO = new MovieDAO();
			HallDAO hallDAO = new HallDAO();
			ProjectionTypeDAO projectionTypeDAO = new ProjectionTypeDAO();
			
			Movie movie = new Movie();
			Hall hall = new Hall();
			ProjectionType projectionType = new ProjectionType();
			ArrayList<Projection> list = new ArrayList<Projection>();
			list = projectionDAO.getAllProjectionByMovieIdAndDate(Integer.parseInt(movieId)); 
			
			JSONObject obj = new JSONObject();
			ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
			if(!list.isEmpty()) {
		
				for(Projection projection: list ) {
					
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
					
					if(unsoldSeat>0) {
					
						JSONObject objTemp = new JSONObject();
						movie = movieDAO.getMovieById((int)(projection.getMovieId()));
						projectionType = projectionTypeDAO.getProjectionTypeById((int)projection.getProjectionTypeId());
						hall = hallDAO.getHallById((int)(projection.getHallId()));
						
						
						
						objTemp.put("movieId", movie.getId());
						objTemp.put("movieName", movie.getName());
						objTemp.put("projectionTypeName", projectionType.getName());	
						objTemp.put("hallName", hall.getName());
						
						objTemp.put("id", projection.getId());
						objTemp.put("price", projection.getPrice());
						
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
						objTemp.put("date", dateFormat.format(projection.getDate()));
						objTemp.put("deleted", projection.getDeleted());
						
						objList.add(objTemp);
						}
					}
				if(!objList.isEmpty()) {	
				
					if(objList.size() != 0) {
						
						
					 	obj.put("info", "success");
						obj.put("objList", objList);
					}else {
						
						obj.put("info", "error");
						
					}	
				}else {
					obj.put("info", "error");
					
				}	
			}else {
				obj.put("info", "error");
				
			}	
		
			
			response.getWriter().print(obj);
			
			
			
		}
		else if(action.equals("search")) {
			
			String movieSearch = request.getParameter("movieSearch");
			String typeSearch = request.getParameter("typeSearch");
			String hallSearch = request.getParameter("hallSearch");
			String maxPriceSearch = request.getParameter("maxPriceSearch");
			String minPriceSearch = request.getParameter("minPriceSearch");
			String maxDateSearch = request.getParameter("maxDateSearch");
			String maxTimeSearch = request.getParameter("maxTimeSearch");
			String minDateSearch = request.getParameter("minDateSearch");
			String minTimeSearch = request.getParameter("minTimeSearch");
			
			if(minPriceSearch.equals("")) {
				minPriceSearch = "0";
				
			}
			if(maxPriceSearch.equals("")) {
				maxPriceSearch = "10000001";
				
			}
			
			
			MovieDAO movieDAO = new MovieDAO();
			ProjectionDAO projectionDAO = new ProjectionDAO();
			ProjectionTypeDAO projectionTypeDAO = new ProjectionTypeDAO();
			HallDAO hallDAO = new HallDAO();
			
			ArrayList<Movie> movieList = new ArrayList<Movie>();
			ArrayList<Movie> movieListSearched = new ArrayList<Movie>();
			ArrayList<Projection> projectionList = new ArrayList<Projection>();
			ArrayList<Projection> projectionListSearched = new ArrayList<Projection>();
			ArrayList<Projection> projectionListSearchedAndDate = new ArrayList<Projection>();
			ArrayList<ProjectionType> projectionTypeList = new ArrayList<ProjectionType>();
			ArrayList<ProjectionType> projectionTypeListSearched = new ArrayList<ProjectionType>();
			ArrayList<Hall> hallList = new ArrayList<Hall>();
			ArrayList<Hall> hallListSearched = new ArrayList<Hall>();
			
			movieList = movieDAO.getAllMovie();
			projectionList = projectionDAO.getAllProjections();
			projectionTypeList = projectionTypeDAO.getAllProjectionType();
			hallList = hallDAO.getAllHall();
			
			//pretraga po filmu
			for(Movie movie: movieList) {
				if(movie.getName().toLowerCase().contains(movieSearch.toLowerCase())){
					movieListSearched.add(movie);
				}
			}
			if(typeSearch.equals("all")) {
				for(ProjectionType projectionType: projectionTypeList) {
					
					projectionTypeListSearched.add(projectionType);
					
				}
			}else {
				for(ProjectionType projectionType: projectionTypeList) {
					if(projectionType.getName().toLowerCase().contains(typeSearch.toLowerCase())){
									
						projectionTypeListSearched.add(projectionType);
								
					}
							
				}
			}
				if(hallSearch.equals("all")) {
					for(Hall hall: hallList) {
						
						hallListSearched.add(hall);
						
					}
				}else {
					for(Hall hall: hallList) {
						if(hall.getName().toLowerCase().contains(hallSearch.toLowerCase())){
										
							hallListSearched.add(hall);
									
						}
								
					}
					
				}
			for(Projection projection : projectionList){	
				for(Movie movie: movieListSearched) {
					for(ProjectionType projectionType: projectionTypeListSearched){
						for(Hall hall: hallListSearched) {
							if(projection.getMovieId()==movie.getId()) {
								if(projection.getProjectionTypeId()==projectionType.getId()) {
									if(projection.getHallId()==hall.getId()) {
										if(projection.getPrice()<(Integer.parseInt(maxPriceSearch))) {
											if(projection.getPrice()>(Integer.parseInt(minPriceSearch))) {
												projectionListSearched.add(projection);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			//pretraga po datumu
							
			int flag = 0;
			Date startDate = new Date();
			Date endDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(!maxDateSearch.equals("") && !minDateSearch.equals("")) { // ako je uneto nesto za datum ulazi u ovaj if 
				if(maxTimeSearch.equals("")) { // ako je ukucao datume a nije ukucao max vreme onda mi dodeljujemo vreme i pretrazujemo po datumu
					maxTimeSearch = "23:59";
					try {
						endDate = dateFormat.parse(maxDateSearch + " " + maxTimeSearch);
						
					}catch(ParseException e) {
						e.printStackTrace();
					}
	
				}else {
					try {
						endDate = dateFormat.parse(maxDateSearch + " " + maxTimeSearch);
						
					}catch(ParseException e) {
						e.printStackTrace();
					}
				}
				if(minTimeSearch.equals("")) { // ako je ukucao datume a nije ukucao max vreme onda mi dodeljujemo vreme i pretrazujemo po datumu
					minTimeSearch = "00:00";
					try {
						startDate = dateFormat.parse(minDateSearch + " " + minTimeSearch);
						
					}catch(ParseException e) {
						e.printStackTrace();
					}
	
				}else {
					try {
						startDate = dateFormat.parse(minDateSearch + " " + minTimeSearch);
						
					}catch(ParseException e) {
						e.printStackTrace();
					}
				}
			
			
			for(Projection projection: projectionListSearched) {
				if(projection.getDate().before(endDate) && projection.getDate().after(startDate)) {
					if(!projectionListSearchedAndDate.contains(projection)) { // ako vec sadrzi projekciju da je ne stavlja opet
						projectionListSearchedAndDate.add(projection);
					}
					flag = 1; // oznacava da postoji pretraga i po datumu 
				}
			}
			projectionListSearched = new ArrayList<Projection>(); //prazni listu zato sto ce biti nova lista ukljucujuci pretragu po datumu, pa ce se opet puniti
			//projectionListSearchedAndDate moze biti null ako nije trazio po datumu
		}			
		if(flag == 1) {
			projectionListSearched = new ArrayList<Projection>();
			for(Projection projection : projectionListSearchedAndDate) {
				projectionListSearched.add(projection);
			}
			
		}
		
			
			ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
			JSONObject obj = new JSONObject();
			for(Projection projection: projectionListSearched ) {
				
		
					JSONObject objTemp = new JSONObject();
					Movie movie = movieDAO.getMovieById((int)(projection.getMovieId()));
					ProjectionType projectionType = projectionTypeDAO.getProjectionTypeById((int)projection.getProjectionTypeId());
					Hall hall = hallDAO.getHallById((int)(projection.getHallId()));
					
					
					
					objTemp.put("movieId", movie.getId());
					objTemp.put("movieName", movie.getName());
					objTemp.put("projectionTypeName", projectionType.getName());	
					objTemp.put("hallName", hall.getName());
					
					objTemp.put("id", projection.getId());
					objTemp.put("price", projection.getPrice());
					
					SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
					objTemp.put("date", dateFormat.format(projection.getDate()));
					objTemp.put("deleted", projection.getDeleted());
					
					objList.add(objTemp);
			}
			if(objList.size() != 0) {
				
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
