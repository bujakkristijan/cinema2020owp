package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	   
    private static Connection connection1;
    
    // C:\Users\cilee\git\sf82015\owpSF82015\src\database\schema.db
    
       public static final String FS = System.getProperty("file.separator");

      
         public static final String dbPath = "C:" + FS + "Users" + FS + "cilee" +
         FS + "git" + FS + "sf82015" + FS + "owpSF82015"  + FS + "src"+ FS + "database" + FS +
         "schema.db";
         public static final String fullPath ="jdbc:sqlite:" + "C:" + FS + "Users" + FS + "cilee" +
                 FS + "git" + FS + "sf82015" + FS + "owpSF82015"  + FS + "src"+ FS + "database" + FS +
                 "schema.db";
         
         //u linux putanji nema "C:" vec ispred ide "/" pa home verovatno
    
         public static void ConnectionManager() {
           try {
               Class.forName("org.sqlite.JDBC");
               connection1 = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

               System.out.println("Successfully connected.");
           } catch (ClassNotFoundException e) {
               System.out.println("Unsuccessfully connected.");
               e.printStackTrace();
           } catch (SQLException e) {
               System.out.println("Unsuccessfully connected.");
               e.printStackTrace();
           } catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }finally {
               try {
                   if (connection1 != null) {
                       connection1.close();
                   }
               } catch (SQLException ex) {
                   System.out.println(ex.getMessage());
               }
           }

       }

       public static Connection getInstance() {
           try {
                Connection connection1 = DriverManager.getConnection(fullPath);               
               return connection1;
                          
           } catch (Exception e) {
               e.printStackTrace();
           }
           return null;
       }

}
