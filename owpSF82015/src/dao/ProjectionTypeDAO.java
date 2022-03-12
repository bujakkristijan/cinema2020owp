package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.ConnectionManager;
import model.Projection;
import model.ProjectionType;

public class ProjectionTypeDAO {
	
	public ArrayList<ProjectionType> getAllProjectionType(){
		
		ArrayList<ProjectionType> list = new ArrayList<ProjectionType>();
		String sqlite = "SELECT * FROM PROJECTION_TYPE";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				ProjectionType obj = new ProjectionType(
						resultSet.getLong("ID"),
						resultSet.getString("NAME"));
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
	
public ProjectionType getProjectionTypeById(int id) {
		
		String sqlite = "SELECT * FROM PROJECTION_TYPE WHERE ID = ? ";
		ProjectionType obj = null;
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
				obj= new ProjectionType(
						resultSet.getLong("ID"),
						resultSet.getString("NAME")
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

}
