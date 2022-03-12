package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.ConnectionManager;
import model.Projection;
import model.Seat;

public class SeatDAO {
	
	public ArrayList<Seat> getAllSeat(){
		
		ArrayList<Seat> list = new ArrayList<Seat>();
		String sqlite = "SELECT * FROM SEAT";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				Seat obj = new Seat(
						resultSet.getLong("ID"),
						resultSet.getLong("NUMBER"),
						resultSet.getLong("HALL_ID"));
				list.add(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try { if (resultSet!=null) resultSet.close(); } catch (Exception e) {};
			try { if (statement!=null) statement.close(); } catch (Exception e) {};
			try { if (connection!=null) connection.close(); } catch (Exception e) {};
		}
		return list;
	}
	
public Seat getSeatById(int id) {
		
		String sqlite = "SELECT * FROM SEAT WHERE ID = ? ";
		Seat obj = null;
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
				obj= new Seat(
						resultSet.getLong("ID"),
						resultSet.getLong("NUMBER"),
						resultSet.getLong("HALL_ID"));
						
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

public ArrayList<Seat> getSeatByHallId(int hallId){
	
	ArrayList<Seat> list = new ArrayList<Seat>();
	String sqlite = "SELECT * FROM SEAT WHERE HALL_ID=?";
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	
	try {
		connection = ConnectionManager.getInstance();
		statement = connection.prepareStatement(sqlite);
		statement.setInt(1, hallId);
		resultSet = statement.executeQuery();
	
		
		while(resultSet.next())
		{
			Seat obj = new Seat(
					resultSet.getLong("ID"),
					resultSet.getLong("NUMBER"),
					resultSet.getLong("HALL_ID"));
			list.add(obj);
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		try { if (resultSet!=null) resultSet.close(); } catch (Exception e) {};
		try { if (statement!=null) statement.close(); } catch (Exception e) {};
		try { if (connection!=null) connection.close(); } catch (Exception e) {};
	}
	return list;
}


}
