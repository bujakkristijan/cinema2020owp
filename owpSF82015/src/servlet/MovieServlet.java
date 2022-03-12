package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.MovieDAO;
import dao.ProjectionDAO;
import dao.UserDAO;
import model.Movie;

/**
 * Servlet implementation class MovieServlet
 */
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieServlet() {
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
		if(action.equals("moviePage")){
			String movieId = request.getParameter("movieId");
			JSONObject obj = new JSONObject();
			MovieDAO movieDAO = new MovieDAO();
			
			Movie movie = new Movie();
			movie = movieDAO.getMovieById(Integer.parseInt(movieId));
			if(movie!=null) {
				obj.put("name", movie.getName());
				obj.put("director", movie.getDirector());
				obj.put("id", movie.getId());
				obj.put("actors", movie.getActors());
				obj.put("genres", movie.getGenres());
				obj.put("duration", movie.getDuration());
				obj.put("distributor", movie.getDistributor());
				obj.put("country", movie.getCountry());	
				obj.put("year", movie.getYear());
				obj.put("description", movie.getDescription());
				obj.put("deleted", movie.getDeleted());
				obj.put("info", "success");
				
				response.getWriter().print(obj);		
				}
			else {
				obj.put("info", "error");
				response.getWriter().print(obj);
			}
			
		}
		else if(action.equals("movieUpdate")) {
			
			MovieDAO movieDAO = new MovieDAO();
			JSONObject obj = new JSONObject();
			
			int movieId = Integer.parseInt(request.getParameter("movieId"));
			
			String name = request.getParameter("name");
			String director = request.getParameter("director");
			String actors = request.getParameter("actors");
			String genres = request.getParameter("genres");
			String durationString = request.getParameter("duration");
			String distributor = request.getParameter("distributor");
			String country = request.getParameter("country");
			String yearString = request.getParameter("year");
			String description = request.getParameter("description");
			
			if(name == null || name.equals("")  || durationString == null || durationString.equals("") ||
					distributor == null || distributor.equals("") || country == null || country.equals("") ||
					yearString == null || yearString.equals("")) {
				
				obj.put("info", "error");
				response.getWriter().print(obj);
				
			}
			else {
				try {
					int duration = Integer.parseInt(durationString);
					int year = Integer.parseInt(yearString);
					Movie movie = movieDAO.getMovieById(movieId);
					movie.setName(name);
					movie.setDirector(director);
					movie.setActors(actors);
					movie.setGenres(genres);
					movie.setDuration(duration);
					movie.setDistributor(distributor);
					movie.setCountry(country);
					movie.setYear(year);
					movie.setDescription(description);
					
					movieDAO.updateMovie(movie);
					
					obj.put("info", "success");
					response.getWriter().print(obj);
					
				}catch(Exception e){
					System.out.println("Error exception");
					obj.put("info", "error");
					response.getWriter().print(obj);
					e.printStackTrace();
				}
			}
			
		}else if(action.equals("deleteMovie")) {
			String role = request.getParameter("role");
			JSONObject obj = new JSONObject();
			if(role.equals("admin")) {
			
				MovieDAO movieDAO = new MovieDAO();
				
				
				int movieId = Integer.parseInt(request.getParameter("movieId"));
				Movie movie = movieDAO.getMovieById(movieId);
				
				if(movie!=null) {
					ProjectionDAO projectionDAO = new ProjectionDAO();
					if(projectionDAO.getAllProjectionByMovieId(movieId).size()!=0) {
						movieDAO.deleteMovie(movieId);
						obj.put("info", "success");
						response.getWriter().print(obj);
					}else {
						movieDAO.deleteMovieFromDB(movieId);
						obj.put("info", "success");
						response.getWriter().print(obj);
					}
					
					
				}else {
					obj.put("info", "error");
					response.getWriter().print(obj);
				}
			}
			else {
				obj.put("info", "errorNotAuthorized");
				response.getWriter().print(obj);
			}
		}
		else if(action.equals("movieAdd"))
		{
			String role = request.getParameter("role");
			JSONObject obj = new JSONObject();
			if(role.equals("admin")) {
	
				MovieDAO movieDAO = new MovieDAO();	
				String name = request.getParameter("name");
				String director = request.getParameter("director");
				String actors = request.getParameter("actors");
				String genres = request.getParameter("genres");
				String durationString = request.getParameter("duration");
				String distributor = request.getParameter("distributor");
				String country = request.getParameter("country");
				String yearString = request.getParameter("year");
				String description = request.getParameter("description");
				
				if(name == null || name.equals("")  || durationString == null || durationString.equals("") ||
						distributor == null || distributor.equals("") || country == null || country.equals("") ||
						yearString == null || yearString.equals("")) {
					
					obj.put("info", "error");
					response.getWriter().print(obj);
					
				}
				else {
					try {
						int duration = Integer.parseInt(durationString);
						int year = Integer.parseInt(yearString);
						Movie movie = new Movie(name, director, actors, genres, duration, distributor, country, year, description, 0);
						movieDAO.insertMovie(movie);
					
						obj.put("info", "success");
						response.getWriter().print(obj);
						
					}catch(Exception e){
						System.out.println("Error exception");
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
		else if(action.equals("getMovie")) {
			JSONObject obj = new JSONObject();
			MovieDAO movieDAO = new MovieDAO();
			
			
			int movieId = Integer.parseInt(request.getParameter("movieId"));
			Movie movie = movieDAO.getMovieById(movieId);
			if(movie!=null) {
				obj.put("info", "success");
				obj.put("movieName", movie.getName());
				response.getWriter().print(obj);
			}else {
				obj.put("info", "error");
				response.getWriter().print(obj);
			}
		}
		else {
			System.out.println("No action choose?");
		}
	}

}
