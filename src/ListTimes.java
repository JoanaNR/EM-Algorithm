//STEP 1. Import required packages
import java.sql.*;

public class ListTimes {
 // JDBC driver name and database URL
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
 static final String DB_URL = "jdbc:mysql://godel.math.ist.utl.pt/proj";

 //  Database credentials
 static final String USER = "amc";
 static final String PASS = "amc2016";
 
 public static void main(String[] args) {
 Connection conn = null;
 Statement stmt = null;
 try{
    //STEP 2: Register JDBC driver
    Class.forName("com.mysql.jdbc.Driver");

    //STEP 3: Open a connection
    System.out.println("Connecting to database...");
    conn = DriverManager.getConnection(DB_URL,USER,PASS);

    //STEP 4: Execute a query
    System.out.println("Creating statement...");
    stmt = conn.createStatement();
    String sql;
    sql = "SELECT * from  Table1;";
    //sql = "SELECT * from  Teste where PatientID=3;";
    //sql = "SELECT * from  Teste where PatientID<3;";
    ResultSet rs = stmt.executeQuery(sql);
    int n_cols = rs.getMetaData().getColumnCount();
    Amostra a = new Amostra();
    while (rs.next()) {
        double [] v = new double[n_cols];
        for(int i = 0;i<v.length; i++){
            v[i] = rs.getFloat(i+1);
        }
        a.add(v);
        System.out.println(
                "Patient n: " + rs.getInt(1) + 
                        " X1=" + rs.getFloat(2) +
                        " X2=" + rs.getFloat(3) +
                        " X3=" + rs.getFloat(4) +
                        " X4=" + rs.getFloat(5) +
                        " X5=" + rs.getFloat(6) +
                        " X6=" + rs.getFloat(7) +
                        " X7=" + rs.getFloat(8));
    }
    //STEP 6: Clean-up environment
    rs.close();
    stmt.close();
    conn.close();
 }catch(SQLException se){
    //Handle errors for JDBC
    se.printStackTrace();
 }catch(Exception e){
    //Handle errors for Class.forName
    e.printStackTrace();
 }finally{
    //finally block used to close resources
    try{
       if(stmt!=null)
          stmt.close();
    }catch(SQLException se2){
    }// nothing we can do
    try{
       if(conn!=null)
          conn.close();
    }catch(SQLException se){
       se.printStackTrace();
    }//end finally try
 }//end try
 System.out.println("Goodbye!");
}//end main
}