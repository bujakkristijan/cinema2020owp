package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.ConnectionManager;
import model.Projection;

public class ProjectionDAO {
	
	public ArrayList<Projection> getAllProjections(){
		
		ArrayList<Projection> list = new ArrayList<Projection>();
		String sqlite = "SELECT * FROM PROJECTION";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			resultSet = statement.executeQuery();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			
			while(resultSet.next())
			{
				Projection obj = new Projection(
						resultSet.getLong("ID"),
						resultSet.getLong("MOVIE_ID"),
						resultSet.getLong("PROJECTION_TYPE_ID"),
						resultSet.getLong("HALL_ID"),
						dateFormat.parse(resultSet.getString("DATE")),
						resultSet.getDouble("PRICE"),
						resultSet.getLong("USER_ID"),				
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
	
public ArrayList<Projection> getAllProjectionForToday(){
		
		ArrayList<Projection> list = new ArrayList<Projection>();
		//Sortiranje po nazivu filma radimo na frontu a ovde sortiranje po datumu pri uzimanjy iz tabele
		String sqlite = "SELECT * FROM PROJECTION WHERE DATE BETWEEN ? AND ? ORDER BY DATE ASC";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			

			Date dateStart = new Date();
			Date dateEnd = new Date();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Calendar c = Calendar.getInstance();
			c.setTime(dateStart);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			//c.add(Calendar.DATE, 1); // dodaje jedan dan na trenutni datum
			dateStart = c.getTime();
			
			Calendar c2 = Calendar.getInstance();
			c2.setTime(dateEnd);
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			c2.set(Calendar.SECOND, 59);
			dateEnd = c2.getTime();
			//dateEnd = c.getTime();
			
			statement.setString(1, dateFormat.format(dateStart).toString());
			statement.setString(2, dateFormat.format(dateEnd).toString());
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				Projection obj = new Projection(
						resultSet.getLong("ID"),
						resultSet.getLong("MOVIE_ID"),
						resultSet.getLong("PROJECTION_TYPE_ID"),
						resultSet.getLong("HALL_ID"),
						dateFormat.parse(resultSet.getString("DATE")),
						resultSet.getDouble("PRICE"),
						resultSet.getLong("USER_ID"),				
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

public ArrayList<Projection> getAllProjectionByMovieId(int id){
	
	ArrayList<Projection> list = new ArrayList<Projection>();
	//Sortiranje po nazivu filma radimo na frontu a ovde sortiranje po datumu pri uzimanjy iz tabele
	String sqlite = "SELECT * FROM PROJECTION WHERE MOVIE_ID = ?";
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	
	try {
		connection = ConnectionManager.getInstance();
		statement = connection.prepareStatement(sqlite);
		statement.setInt(1, id);
		resultSet = statement.executeQuery();

		Date dateStart = new Date();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		while(resultSet.next())
		{
			Projection obj = new Projection(
					resultSet.getLong("ID"),
					resultSet.getLong("MOVIE_ID"),
					resultSet.getLong("PROJECTION_TYPE_ID"),
					resultSet.getLong("HALL_ID"),
					dateFormat.parse(resultSet.getString("DATE")),
					resultSet.getDouble("PRICE"),
					resultSet.getLong("USER_ID"),				
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

public ArrayList<Projection> getAllProjectionByMovieIdAndDate(int id){
	
	ArrayList<Projection> list = new ArrayList<Projection>();
	//Sortiranje po nazivu filma radimo na frontu a ovde sortiranje po datumu pri uzimanjy iz tabele
	String sqlite = "SELECT * FROM PROJECTION WHERE MOVIE_ID = ? AND DATE > ?";
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	
	try {
		connection = ConnectionManager.getInstance();
		statement = connection.prepareStatement(sqlite);
		Date currentDateAndTime = new Date(); //Tue Jul 12 18:35:37 IST 2016
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(currentDateAndTime); //2020-08-14 23:15:05
		statement.setInt(1, id);
		statement.setString(2, date);
		resultSet = statement.executeQuery();

		
		
		
		
		while(resultSet.next())
		{
			Projection obj = new Projection(
					resultSet.getLong("ID"),
					resultSet.getLong("MOVIE_ID"),
					resultSet.getLong("PROJECTION_TYPE_ID"),
					resultSet.getLong("HALL_ID"),
					dateFormat.parse(resultSet.getString("DATE")), //2020-08-14 23:15:05 -> //Tue Jul 12 18:35:37 IST 2016
					resultSet.getDouble("PRICE"),
					resultSet.getLong("USER_ID"),				
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
	
	public Projection getProjectionById(int id) {
		
		String sqlite = "SELECT * FROM PROJECTION WHERE ID = ? ";
		Projection obj = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				obj= new Projection(
						resultSet.getLong("ID"),
						resultSet.getLong("MOVIE_ID"),
						resultSet.getLong("PROJECTION_TYPE_ID"),
						resultSet.getLong("HALL_ID"),
						dateFormat.parse(resultSet.getString("DATE")),
						resultSet.getDouble("PRICE"),
						resultSet.getLong("USER_ID"),				
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
	
	public Projection insertProjection(Projection projection)
	{
		String sqlite = "INSERT INTO PROJECTION (MOVIE_ID, PROJECTION_TYPE_ID, HALL_ID, DATE, PRICE, USER_ID, DELETED) VALUES (?,?,?,?,?,?,?)";
		Projection obj = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			Date date = new Date();
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, (int)projection.getMovieId());
			statement.setInt(2, (int)projection.getProjectionTypeId());
			statement.setInt(3, (int)projection.getHallId());
			date = projection.getDate();
			statement.setString(4, dateFormat.format(date));
			statement.setDouble(5, projection.getPrice());
			statement.setInt(6, (int)projection.getUserId());			
			statement.setInt(7, projection.getDeleted());
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
	

	public void updateProjection(Projection projection)
	{
		String sqlite = "UPDATE PROJECTION SET MOVIE_ID = ?, PROJECTION_TYPE_ID = ?, HALL_ID = ?, DATE = ?, PRICE = ?, USER_ID = ?, DELETED = ? WHERE ID = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = new Date();
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, (int)projection.getMovieId());
			statement.setInt(2, (int)projection.getProjectionTypeId());
			statement.setInt(3, (int)projection.getHallId());
			date = projection.getDate();
			statement.setString(4, dateFormat.format(date));
			statement.setDouble(5, projection.getPrice());
			statement.setInt(6, (int)projection.getUserId());			
			statement.setInt(7, projection.getDeleted());
			statement.setInt(8, (int)projection.getId());
			
			statement.execute();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try { if(resultSet != null) resultSet.close(); } catch(Exception e) {};
			try { if(statement != null) statement.close(); } catch(Exception e) {};
			try { if(connection != null) connection.close(); } catch(Exception e) {};
		}
		
	}
	
	public void deleteProjection(int id) {
		
		String sqlite = "UPDATE PROJECTION SET DELETED = 1  WHERE ID = ?";
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
	
	public void deleteProjectionFromDB(int id) {
		
		String sqlite = "DELETE FROM PROJECTION WHERE ID = ?";
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
