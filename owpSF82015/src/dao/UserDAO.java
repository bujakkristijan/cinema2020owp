package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.ConnectionManager;
import model.User;

public class UserDAO {
	
	public ArrayList<User> getAllUser(){
		
		ArrayList<User> list = new ArrayList<User>();
		String sqlite = "SELECT * FROM USER";
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
				User obj = new User(
						resultSet.getLong("ID"),
						resultSet.getString("USERNAME"),
						resultSet.getString("PASSWORD"),
						format.parse(resultSet.getString("DATE")),
						resultSet.getString("ROLE"),
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
	
	public User getUserById(int id) {
		
		String sqlite = "SELECT * FROM USER WHERE ID = ? ";
		User obj = null;
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
				obj= new User(
						resultSet.getLong("ID"),
						resultSet.getString("USERNAME"),
						resultSet.getString("PASSWORD"),
						format.parse(resultSet.getString("DATE")),
						resultSet.getString("ROLE"),
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
	
	public User insertUser(User user)
	{
		String sqlite = "INSERT INTO USER (USERNAME, PASSWORD, DATE, ROLE, DELETED) VALUES (?,?,?,?,?)";
		User obj = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			statement.setString(3, dateFormat.format(date));
			statement.setString(4, user.getRole());
			statement.setInt(5, user.getDeleted());
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
	
	public User getUserByUsername(String username) {
			
			String sqlite = "SELECT * FROM USER WHERE USERNAME = ? AND DELETED = 0";
			User obj = null;
			Connection connection = null;
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			
			try {
				connection = ConnectionManager.getInstance();
				statement = connection.prepareStatement(sqlite);
				statement.setString(1, username);
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = null;
				resultSet = statement.executeQuery();
				
				while(resultSet.next())
				{
					obj= new User(
							resultSet.getLong("ID"),
							resultSet.getString("USERNAME"),
							resultSet.getString("PASSWORD"),
							format.parse(resultSet.getString("DATE")),
							resultSet.getString("ROLE"),
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

	public void updateUser(User user)
	{
		String sqlite = "UPDATE USER SET USERNAME = ?, PASSWORD = ?, DATE = ?, ROLE = ?, DELETED = ? WHERE ID = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			statement.setString(3, dateFormat.format(date));
			statement.setString(4, user.getRole());
			statement.setInt(5, user.getDeleted());
			statement.setInt(6, (int)user.getId());
			statement.execute();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try { if(resultSet != null) resultSet.close(); } catch(Exception e) {};
			try { if(statement != null) statement.close(); } catch(Exception e) {};
			try { if(connection != null) connection.close(); } catch(Exception e) {};
		}
		
	}
	
	public void deleteUser(int id) {
		
		String sqlite = "UPDATE USER SET DELETED = 1  WHERE ID = ?";
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
	
public void deleteUserFromDB(int id) {
		
		String sqlite = "DELETE FROM USER WHERE ID = ?";
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
