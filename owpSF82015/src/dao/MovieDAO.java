package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.ConnectionManager;
import model.Movie;

public class MovieDAO {
	
	public ArrayList<Movie> getAllMovie(){
		
		ArrayList<Movie> list = new ArrayList<Movie>();
		String sqlite = "SELECT * FROM MOVIE";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				Movie obj = new Movie(
						resultSet.getLong("ID"),
						resultSet.getString("NAME"),
						resultSet.getString("DIRECTOR"),
						resultSet.getString("ACTORS"),
						resultSet.getString("GENRES"),
						resultSet.getInt("DURATION"),
						resultSet.getString("DISTRIBUTOR"),
						resultSet.getString("COUNTRY"),
						resultSet.getInt("YEAR"),
						resultSet.getString("DESCRIPTION"),						
						resultSet.getInt("DELETED")
									);
				list.add(obj);
						
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try { if(resultSet != null) resultSet.close(); } catch(Exception e) {};
			try { if(statement != null) statement.close(); } catch(Exception e) {};
			try { if(connection != null) connection.close(); } catch(Exception e) {};
		}
		return list;
		
	}
	
	public Movie getMovieById(int id) {
		
		String sqlite = "SELECT * FROM MOVIE WHERE ID = ? ";
		Movie obj = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				obj= new Movie(
						resultSet.getLong("ID"),
						resultSet.getString("NAME"),
						resultSet.getString("DIRECTOR"),
						resultSet.getString("ACTORS"),
						resultSet.getString("GENRES"),
						resultSet.getInt("DURATION"),
						resultSet.getString("DISTRIBUTOR"),
						resultSet.getString("COUNTRY"),
						resultSet.getInt("YEAR"),
						resultSet.getString("DESCRIPTION"),						
						resultSet.getInt("DELETED")
									);
						
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try { if(resultSet != null) resultSet.close(); } catch(Exception e) {};
			try { if(statement != null) statement.close(); } catch(Exception e) {};
			try { if(connection != null) connection.close(); } catch(Exception e) {};
		}
		
		return obj;
		
	}
	
	public Movie insertMovie(Movie movie)
	{
		String sqlite = "INSERT INTO MOVIE (NAME, DIRECTOR, ACTORS, GENRES, DURATION, DISTRIBUTOR, COUNTRY, YEAR, DESCRIPTION, DELETED) VALUES (?,?,?,?,?,?,?,?,?,?)";
		Movie obj = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setString(1, movie.getName());
			statement.setString(2, movie.getDirector());
			statement.setString(3, movie.getActors());
			statement.setString(4, movie.getGenres());
			statement.setInt(5, movie.getDuration());
			statement.setString(6, movie.getDistributor());
			statement.setString(7, movie.getCountry());
			statement.setInt(8, movie.getYear());
			statement.setString(9, movie.getDescription());
			statement.setInt(10, movie.getDeleted());
			statement.execute();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try { if(resultSet != null) resultSet.close(); } catch(Exception e) {};
			try { if(statement != null) statement.close(); } catch(Exception e) {};
			try { if(connection != null) connection.close(); } catch(Exception e) {};
		}
		return obj;
	}
	

	public void updateMovie(Movie movie)
	{
		String sqlite = "UPDATE MOVIE SET NAME = ?, DIRECTOR = ?, ACTORS = ?, GENRES = ?, DURATION = ?, DISTRIBUTOR = ?, COUNTRY = ?, YEAR = ?, DESCRIPTION = ?, DELETED = ? WHERE ID = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setString(1, movie.getName());
			statement.setString(2, movie.getDirector());
			statement.setString(3, movie.getActors());
			statement.setString(4, movie.getGenres());
			statement.setInt(5, movie.getDuration());
			statement.setString(6, movie.getDistributor());
			statement.setString(7, movie.getCountry());
			statement.setInt(8, movie.getYear());
			statement.setString(9, movie.getDescription());
			statement.setInt(10, movie.getDeleted());
			statement.setInt(11, (int)movie.getId());
			
			statement.execute();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try { if(resultSet != null) resultSet.close(); } catch(Exception e) {};
			try { if(statement != null) statement.close(); } catch(Exception e) {};
			try { if(connection != null) connection.close(); } catch(Exception e) {};
		}
		
	}
	
	public void deleteMovie(int id) {
		
		String sqlite = "UPDATE MOVIE SET DELETED = 1  WHERE ID = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, id);
		
			statement.execute();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try { if(resultSet != null) resultSet.close(); } catch(Exception e) {};
			try { if(statement != null) statement.close(); } catch(Exception e) {};
			try { if(connection != null) connection.close(); } catch(Exception e) {};
		}
	}
	
public void deleteMovieFromDB(int id) {
		
		String sqlite = "DELETE FROM MOVIE WHERE ID = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, id);
		
			statement.execute();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try { if(resultSet != null) resultSet.close(); } catch(Exception e) {};
			try { if(statement != null) statement.close(); } catch(Exception e) {};
			try { if(connection != null) connection.close(); } catch(Exception e) {};
		}
	}

}
