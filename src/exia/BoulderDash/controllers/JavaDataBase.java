package exia.BoulderDash.controllers;

import java.sql.*; 
// Using 'Connection', 'Statement' and 'ResultSet' classes in java.sql package
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JavaDataBase {
	
	public static void databasetest(String level0X) throws IOException {
		System.out.println("started");
		String levelInXml ="";
		String pathTodataStore = "./res/levels";
		try {
	           Class.forName("com.mysql.jdbc.Driver");

	            // connect way #1
	            String url1 = "jdbc:mysql://localhost:3306/jpublankproject?autoReconnect=true&useSSL=false";
	            String user = "root";
	            String password = "";

	            Connection conn = DriverManager.getConnection(url1, user, password);
	            if (conn != null) {
	                System.out.println("Connected to the database");
	            }
	            
	            // prepare the query 
	            String query = "{call GetLevel(?)}";
	            
	            //callable statement
	            CallableStatement stmt = conn.prepareCall(query);
	            
	            // set input for the stored procedure
	            stmt.setNString(1,level0X);
	            
	            ResultSet rs = stmt.executeQuery();
	            
	            
	            
	            while (rs.next()) {
	                levelInXml =  String.format("%s",
	                        rs.getString("levelValues")
	                       );
	            }
	            conn.close();
	            
	            /*
	             * from here now we will just store the level in pathTodataStore
	             */
	            
	            String fileSeparator = System.getProperty("file.separator");
	            pathTodataStore = pathTodataStore + fileSeparator + level0X +".xml" ;
	            File file = new File(pathTodataStore);
	            
	            if(file.createNewFile()){
	                System.out.println(pathTodataStore+" File Created");
	                FileWriter fw = new FileWriter(pathTodataStore,true); //the true will append the new data
		            fw.write(levelInXml); //appends the string to the file
		            fw.close();
	            }else 
	            {
	            	System.out.println("File "+pathTodataStore+" already exists");
	            }
	            
	   } catch (SQLException ex) {
	            ex.printStackTrace();
	   } catch (ClassNotFoundException e) {
	    e.printStackTrace();
	   }
	}
	
}