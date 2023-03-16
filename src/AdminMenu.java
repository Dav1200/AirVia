import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminMenu extends JFrame{
    public AdminMenu()  {

        //manual input
        //createTable();
        shows();

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
private DBConnection db;

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
    //Automate Jtable With Database Values
    public void shows() {

        try (
                Connection con = DBConnection.getConnection();
                ) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM `Staff`");
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) DB.getModel();

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i-1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String StaffID, Firstname, Lastname, Email, Address,Role, Password;
            while (resultSet.next()) {
                StaffID = resultSet.getString(1);
                Firstname = resultSet.getString(2);
                Lastname = resultSet.getString(3);
                Email = resultSet.getString(4);
                Address = resultSet.getString(5);
                Role = resultSet.getString(6);
                Password = resultSet.getString(7);

                String[] row = {StaffID, Firstname, Lastname, Email, Address, Role ,Password};
                model.addRow(row);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}