import java.sql.*;
//Db Conn
 public class DBConnection {


     //create connection
     Connection con = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g04");

     //create statements
     Statement stm = con.createStatement();

     //insert statements
     String s = "INSERT INTO STUDENT VALUES(/* data from sql */)";

     //update statements
     String a = "UPDATE STUDENT SET  /*blah blah */ WHERE /*blah blah*/ ";

     //delete statements
     String x = "DELETE STUDENT */ WHERE /*blah blah*/ ";

     //execute statement
     //stm.execute(s);
     //stm.execute(a);
     //stm.execute(x);

     //close connection
     //con.close();

     public DBConnection() throws SQLException {
     }
 }

