import java.sql.*;
//Db Conn
public class DBConnection {



        public static void main(String[] args) {
            Connection conn = null;

            try {
                // Load the MySQL JDBC driver
                // Establish a connection to the MySQL database
                conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g04","in2018g04_d","1Lzc6IUm");
                System.out.println("wokred");
                // Do something with the connection
                // ...
            }  catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the connection
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }