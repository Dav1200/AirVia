import java.sql.*;

/** class to create a connection to the database
 *
 *
 */
//Db Conn
public class DBConnection {
    /**
     *  create a connection City's database server
     * @return conn
     * @throws ClassNotFoundException
     */
    public static Connection getConnection() throws ClassNotFoundException {
            Connection conn = null;
        try {
                // Load the MySQL JDBC driver
                // Establish a connection to the MySQL database
                conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g04","in2018g04_d","1Lzc6IUm");
                // Do something with the connection
                // ...
            }  catch (SQLException e) {

                //if connection fails handle the exception
                e.printStackTrace();
            }


            //return the connection to the database.
        return conn;
    }
}
