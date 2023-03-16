import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminMenu extends JFrame{
    public AdminMenu() throws SQLException, ClassNotFoundException {

        //manual input
        //createTable();

                workButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login a = new Login();
                dispose();
            }
        });
        userlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hi");
            }
        });
    }

    public JPanel getAplane() {
        return aplane;
    }

    public void setAplane(JPanel aplane) {
        this.aplane = aplane;
    }

    private JPanel aplane;
    private JButton workButton;
    private JTabbedPane TabMenu;
    private JButton userlistButton;
    private JTable DB;


    //manual input
    /*
    private void createTable(){
        Object[][] data = {{111, "Frank", "Furter", "frankfurter@ttechttonic.com", "17 Church Road, Blackpool", "Travel advisor", "adv123"},
                {112, "Holly", "Harper", "hollyharper@ttechttonic.com", "44 Victoria Road, London", "Travel advisor", "adv123"},
                {113, "India", "Iris", "indiairis@ttechttonic.com", "51 Green Lane, Birmingham", "Travel advisor", "adv123"}};
        DB.setModel(new DefaultTableModel(data, new String[]{"StaffID", "Firstname", "Lastname", "Email", "Address", "Role", "Password"}));
    }

     */

    //automated input

    //get connection
    try(Connection con = DBConnection.getConnection();)

    {

    Statement stm = con.createStatement();
    String query = "SELECT * FROM `STAFF`";
    ResultSet resultSet = stm.executeQuery(query);
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    DefaultTableModel model = (DefaultTableModel) DB.getModel();

    //getting column names
    int col = resultSetMetaData.getColumnCount();
    String[] colName = new String[col];
    for(int i = 1; i< col; i++){
        colName[i] = resultSetMetaData.getColumnName(col);

    }
    model.setColumnIdentifiers(colName);

    //getting data
    String StaffID, Firstname, Lastname, Email, Address, Password;
    while(resultSet.next()) {
        StaffID = resultSet.getString(1);
        Firstname = resultSet.getString(1);
        Lastname = resultSet.getString(1);
        Email = resultSet.getString(1);
        Address = resultSet.getString(1);
        Password = resultSet.getString(1);

        String[] row = {StaffID, Firstname, Lastname, Email, Address, Password};
        model.addRow(row);
    }
    catch(ClassNotFoundException | SQLException e){
            System.out.println(e);

        }

    }

}