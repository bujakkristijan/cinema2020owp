package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.ConnectionManager;
import model.Hall;
import model.Movie;
import model.Projection;
import model.ProjectionType;

public class HallDAO {
	
	public ArrayList<Hall> getAllHall(){
		
		ProjectionTypeDAO projectionTypeDAO = new ProjectionTypeDAO();
		ArrayList<Hall> list = new ArrayList<Hall>();
		Hall obj = null;
		
		ArrayList<ProjectionType> projectionTypeList = new ArrayList<ProjectionType>();
		long hallId = 0;
		String hallName = null;
		
		String sqlite = "SELECT * FROM HALL";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				hallId = resultSet.getLong("ID");
				hallName = resultSet.getString("NAME");
				String [] projectionTypeStr = resultSet.getString("PROJECTION_TYPE_LIST").split(",");// {"1","3"}
				projectionTypeList = new ArrayList<ProjectionType>();
				
				for(String projectionType: projectionTypeStr) {
					ProjectionType p = projectionTypeDAO.getProjectionTypeById(Integer.valueOf(projectionType));
					if(p!=null) {
						projectionTypeList.add(p);
					}
					
				}
				obj = new Hall(hallId, hallName, projectionTypeList);
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
	
	public String checkIsAcceptable(Date startProjectionInputDate, Date endProjectionInputDate, int id) {
		
		ProjectionTypeDAO projectionTypeDAO = new ProjectionTypeDAO();
		String sqlite = "SELECT * FROM PROJECTION WHERE HALL_ID = ?";
		Hall obj = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String retVal = null;
		MovieDAO movieDAO = new MovieDAO();
		HallDAO hallDAO = new HallDAO();
		ArrayList<ProjectionType> projectionTypeList = new ArrayList<ProjectionType>();
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			/*if(resultSet.first() == false) {
				retVal = "yes";
			}else {*/
				while(resultSet.next()) {
					String startProjectionDB = resultSet.getString("DATE");
					int movieIdDB = resultSet.getInt("MOVIE_ID"); // uzimam movieid da bih znao koja je duzina filma jer iz projekcije to ne znam
					Movie movie = movieDAO.getMovieById(movieIdDB);
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date startProjectionDateDB = new Date();
					Date endProjectionDateDB = new Date();
					
					Calendar cal = Calendar.getInstance();
					startProjectionDateDB = format.parse(startProjectionDB);
					cal.setTime(startProjectionDateDB);
					cal.add(Calendar.MINUTE, movie.getDuration()); // dodaje minute na pocetak projekcije
					endProjectionDateDB = cal.getTime();
					
					if(startProjectionDateDB.before(endProjectionInputDate) && endProjectionDateDB.after(startProjectionInputDate)) {
						retVal = "no";
						break;
					}else {
						retVal = "yes";
					}
		
				}
			//}
			

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try { if(resultSet != null) resultSet.close(); } catch(Exception e) {};
			try { if(statement != null) statement.close(); } catch(Exception e) {};
			try { if(connection != null) connection.close(); } catch(Exception e) {};
		}
		if(retVal == null) {
			retVal = "yes"; // ako nema projekcija vezanih za tu halu znaci da moze da se doda projekcija u bilo koje vreme
		}
		return retVal;
	}
	
public Hall getHallById(int id) {
		
		ProjectionTypeDAO projectionTypeDAO = new ProjectionTypeDAO();
		String sqlite = "SELECT * FROM HALL WHERE ID = ? ";
		Hall obj = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		ArrayList<ProjectionType> projectionTypeList = new ArrayList<ProjectionType>();
		long hallId = 0;
		String hallName = null;
		
		try {
			connection = ConnectionManager.getInstance();
			statement = connection.prepareStatement(sqlite);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			
			
			while(resultSet.next())
			{

						hallId = resultSet.getLong("ID");
						hallName = resultSet.getString("NAME");
						String [] projectionTypeStr = resultSet.getString("PROJECTION_TYPE_LIST").split(",");// {"1","3"}
						projectionTypeList = new ArrayList<ProjectionType>();
						
						for(String projectionType: projectionTypeStr) {
							ProjectionType p = projectionTypeDAO.getProjectionTypeById(Integer.valueOf(projectionType));
							if(p!=null) {
								projectionTypeList.add(p);
							}
						}
									
						
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try { if(resultSet != null) resultSet.close(); } catch(Exception e) {};
			try { if(statement != null) statement.close(); } catch(Exception e) {};
			try { if(connection != null) connection.close(); } catch(Exception e) {};
		}
		
		obj = new Hall(hallId, hallName, projectionTypeList);
		
		return obj;
		
	}

}
