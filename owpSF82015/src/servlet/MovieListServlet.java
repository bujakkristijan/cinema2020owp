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

import dao.MovieDAO;
import dao.UserDAO;
import model.Movie;
import model.User;

/**
 * Servlet implementation class MovieListServlet
 */
public class MovieListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieListServlet() {
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
		if(action.equals("movieList")){
			
			JSONObject obj = new JSONObject();
			MovieDAO movieDAO = new MovieDAO();
			ArrayList<Movie> list = new ArrayList<Movie>();
			list = movieDAO.getAllMovie();
			ArrayList<JSONObject>objList = new ArrayList<JSONObject>();
			
			for(Movie movie: list ) {
				JSONObject objTemp = new JSONObject();
				objTemp.put("name", movie.getName());
				objTemp.put("director", movie.getDirector());
				objTemp.put("id", movie.getId());
				objTemp.put("actors", movie.getActors());
				objTemp.put("genre", movie.getGenres());
				objTemp.put("duration", movie.getDuration());
				objTemp.put("distributor", movie.getDistributor());
				objTemp.put("country", movie.getCountry());	
				objTemp.put("year", movie.getYear());
				objTemp.put("description", movie.getDescription());
				objTemp.put("deleted", movie.getDeleted());
				
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
		else if(action.equals("search")) {
			String nameSearch = request.getParameter("nameSearch");
			String genreSearch = request.getParameter("genreSearch");
			String distributorSearch = request.getParameter("distributorSearch");
			String countrySearch = request.getParameter("countrySearch");
			String maxDurationSearch = request.getParameter("maxDurationSearch");
			String minDurationSearch = request.getParameter("minDurationSearch");
			String maxYearSearch = request.getParameter("maxYearSearch");
			String minYearSearch = request.getParameter("minYearSearch");
			
			if(minDurationSearch.equals("")) {
				minDurationSearch = "0";
				
			}
			if(maxDurationSearch.equals("")) {
				maxDurationSearch = "10000001";
				
			}
			if(minYearSearch.equals("")) {
				minYearSearch = "0";
				
			}
			if(maxYearSearch.equals("")) {
				maxYearSearch = "10000001";
				
			}
			
			MovieDAO movieDAO = new MovieDAO();
			ArrayList<Movie> list = new ArrayList<Movie>();
			ArrayList<Movie> listSearched = new ArrayList<Movie>();
			
			list = movieDAO.getAllMovie();
			
			for(Movie movie:list)
			{
				if(movie.getName().toLowerCase().contains(nameSearch.toLowerCase())){
					if(movie.getGenres().toLowerCase().contains(genreSearch.toLowerCase())){
						if(movie.getDistributor().toLowerCase().contains(distributorSearch.toLowerCase())){
							if(movie.getCountry().toLowerCase().contains(countrySearch.toLowerCase())){
								if(movie.getDuration() < (Integer.parseInt(maxDurationSearch))){
									if(movie.getDuration() > (Integer.parseInt(minDurationSearch))){
										if(movie.getYear() < (Integer.parseInt(maxYearSearch))){
											if(movie.getYear() > (Integer.parseInt(minYearSearch))){
												listSearched.add(movie);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			ArrayList<JSONObject> objList = new ArrayList<JSONObject>();
			JSONObject obj = new JSONObject();
			for(Movie movie: listSearched) {
				
				JSONObject objTemp = new JSONObject();
				objTemp.put("name", movie.getName());
				objTemp.put("director", movie.getDirector());
				objTemp.put("id", movie.getId());
				objTemp.put("actors", movie.getActors());
				objTemp.put("genre", movie.getGenres());
				objTemp.put("duration", movie.getDuration());
				objTemp.put("distributor", movie.getDistributor());
				objTemp.put("country", movie.getCountry());	
				objTemp.put("year", movie.getYear());
				objTemp.put("description", movie.getDescription());
				objTemp.put("deleted", movie.getDeleted());
				
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
