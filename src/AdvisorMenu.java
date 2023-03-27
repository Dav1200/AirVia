import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;

public class AdvisorMenu extends JFrame {
    public AdvisorMenu() {


        showCustomer();

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login a = new Login();
                dispose();
            }
        });


        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Takes in what the advisor has entered
                    String customerID = customerIDTf.getText();
                    String firstName =  firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String email =  emailTextField.getText();
                    String customerType = customerTypeTf.getText();
                    String discountType = discountTypeTf.getText();
                    String discountAmount = discountAmountTf.getText();
                    String address = addressTf.getText();
                    String totalTickets = totalTicketsTf.getText();
                    String staffID = staffIDTf.getText();
                    // Establishes a connection the database
                    Connection con = DBConnection.getConnection();
                    // INSERT INTO statement with values from JTextFields
                    PreparedStatement ps = con.prepareStatement("INSERT INTO Customer(ID, FirstName, LastName, Email, " +
                            "CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID) VALUES(?,?,?,?,?,?,?,?,?,?)");
                    ps.setString(1,customerID);
                    ps.setString(2,firstName);
                    ps.setString(3,lastName);
                    ps.setString(4,email);
                    ps.setString(5,customerType);
                    ps.setString(6,discountType);
                    ps.setString(7,discountAmount);
                    ps.setString(8,address);
                    ps.setString(9,totalTickets);
                    ps.setString(10,staffID);
                    // When Button is pressed PHP MY ADMIN should update
                    ps.executeUpdate();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public JPanel getAdPlane() {
        return adPlane;
    }

    private JPanel adPlane;
    private JTabbedPane TabMenu;
    private JButton logoutButton;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField emailTextField;
    private JButton registerButton;
    private JButton clearButton;
    private JLabel searchCustomerLabel;
    private JTextField searchCustomerTextField;
    private JButton searchCustomerButton;
    private JTextField ticketTypeTextField;
    private JTextField customerTypeTextField;
    private JTextField commissionAmountTextField;
    private JTextField reportTypeTextField;
    private JTextField recordPaymentTextField;
    private JTextField recordDiscountAmountTextField;
    private JButton registerTicketButton;
    private JButton createIndividualReportButton;
    private JLabel registerTicketLabel;
    private JTable CustomerTable;
    private JTextField discountAmountTf;
    private JTextField addressTf;
    private JTextField staffIDTf;
    private JButton printButton;
    private JTextField totalTicketsTf;
    private JTextField customerTypeTf;
    private JTextField discountTypeTf;
    private JTextField customerIDTf;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void showCustomer() {
        try (
                Connection con = DBConnection.getConnection();
        ) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM Customer");
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) CustomerTable.getModel();
            CustomerTable.setRowHeight(25);

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String ID, Firstname, Lastname, Email, CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID;
            while (resultSet.next()) {
                ID = resultSet.getString(1);
                Firstname = resultSet.getString(2);
                Lastname = resultSet.getString(3);
                Email = resultSet.getString(4);
                CustomerType = resultSet.getString(5);
                DiscountType = resultSet.getString(6);
                DiscountAmount = resultSet.getString(7);
                Address = resultSet.getString(8);
                TotalTickets = resultSet.getString(9);
                StaffID = resultSet.getString(10);

                String[] row = {ID, Firstname, Lastname, Email, CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID};
                model.addRow(row);


                // row filter
                TableRowSorter tableRowSorter = new TableRowSorter(model);
                CustomerTable.setRowSorter(tableRowSorter);

                //search customer
                searchCustomerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String searchText = searchCustomerTextField.getText();
                        tableRowSorter.setRowFilter(new myRowFilter(searchText));
                    }
                });

            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    //search customer
    public void searchCustomer(String query){
        //connect to DB
        try (
                Connection con = DBConnection.getConnection();
        ) {



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

     */
    //}
}






