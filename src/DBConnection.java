import java.sql.*;

 public class DBConnection {


     //create connection
     Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g04");

     //create statements
     Statement stm = con.createStatement();

     //execute statement
     //insert statements
     String s = "INSERT INTO STUDENT VALUES(/* data from sql */)";

     //update statements
     String a = "UPDATE STUDENT SET  /*blah blah */ WHERE /*blah blah*/ ";

     //delete statements
     String x = "DELETE STUDENT */ WHERE /*blah blah*/ ";


     //close connection
     con.close();

     public DBConnection() throws SQLException {
     }
 }

