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
import model.Ticket;
import model.User;

public class TicketDAO {
	
	public ArrayList<Ticket> getAllTicket(){
			
			ArrayList<Ticket> list = new ArrayList<Ticket>();
			String sqlite = "SELECT * FROM TICKET";
			Connection connection = null;
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			
			try {
				connection = ConnectionManager.getInstance();
				statement = connection.prepareStatement(sqlite);
				resultSet = statement.executeQuery();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = null;
				
				while(resultSet.next())
				{
					Ticket obj = new Ticket(
							resultSet.getLong("ID"),
							resultSet.getLong("PROJECTION_ID"),
							resultSet.getLong("SEAT_NUMBER"),
							format.parse(resultSet.getString("DATE")),
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
	
	public Ticket insertTicket(Ticket ticket)
	{
		String sqlite = "INSERT INTO TICKET (PROJECTION_ID, SEAT_NUMBER, DATE, USER_ID, DELETED) VALUES (?,?,?,?,?)";
		Ticket obj = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, (int)ticket.getProjectionId());
			statement.setInt(2, (int)ticket.getSeatNumber());
			statement.setString(3, dateFormat.format(ticket.getDate()));
			statement.setInt(4, (int)ticket.getUserId());
			statement.setInt(5, ticket.getDeleted());
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
	

	public Ticket getTicketById(int id) {
		
		String sqlite = "SELECT * FROM TICKET WHERE ID = ? ";
		Ticket obj = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, id);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				 obj = new Ticket(
						resultSet.getLong("ID"),
						resultSet.getLong("PROJECTION_ID"),
						resultSet.getLong("SEAT_NUMBER"),
						format.parse(resultSet.getString("DATE")),
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
	
	public ArrayList<Ticket> getTicketByUserId(int userId){
		
		ArrayList<Ticket> list = new ArrayList<Ticket>();
		String sqlite = "SELECT * FROM TICKET WHERE USER_ID = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			
			while(resultSet.next())
			{
				Ticket obj = new Ticket(
						resultSet.getLong("ID"),
						resultSet.getLong("PROJECTION_ID"),
						resultSet.getLong("SEAT_NUMBER"),
						format.parse(resultSet.getString("DATE")),
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
	
public ArrayList<Ticket> getTicketByProjectionId(int projectionId){
		
		ArrayList<Ticket> list = new ArrayList<Ticket>();
		String sqlite = "SELECT * FROM TICKET WHERE PROJECTION_ID = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, projectionId);
			resultSet = statement.executeQuery();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			
			while(resultSet.next())
			{
				Ticket obj = new Ticket(
						resultSet.getLong("ID"),
						resultSet.getLong("PROJECTION_ID"),
						resultSet.getLong("SEAT_NUMBER"),
						format.parse(resultSet.getString("DATE")),
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

	public void deleteTicket(int id) {
			
			String sqlite = "UPDATE TICKET SET DELETED = 1  WHERE ID = ?";
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
	
	public void deleteTicketFromDB(int id) {
		
		String sqlite = "DELETE FROM TICKET WHERE ID = ?";
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
