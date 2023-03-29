import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;

public class AdvisorMenu extends JFrame {
    public AdvisorMenu() {


        showCustomer();
        registerCustomer();
        registerSalesReport();

        errorLabel.setVisible(false);
        incompleteEntry.setVisible(false);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login a = new Login();
                dispose();
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
    private JTextField recordPaymentTextField;
    private JTextField commissionAmountField;
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
    private JTextField departureTextField;
    private JTextField ticketQuantityTextField;
    private JLabel errorLabel;
    private JLabel incompleteEntry;
    private JComboBox discType;
    private JComboBox cusType;
    private JComboBox ticketType;
    private JComboBox paymentType;
    private JComboBox reportType;
    private JTextField departureField;
    private JTextField destinationTextField;
    private JTextField ticketPriceField;
    private JTextField subTotalField;
    private JTextField grandTotalField;
    private JComboBox latePayment;
    private JTextField exchangeRateField;
    private JTextField ticketDateField;
    private JTextField customerIDField;
    private JTextField staffIDField;

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

    private void clearRegisterCustomer(){
        firstNameTextField.setText(null);
        lastNameTextField.setText(null);
        emailTextField.setText(null);
        discountAmountTf.setText(null);
        addressTf.setText(null);
        totalTicketsTf.setText(null);
        staffIDTf.setText(null);
    }

    public void registerCustomer(){
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRegisterCustomer();
            }
        });

        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    //set discount amount to 0

                    // Takes in what the advisor has entered
                    String firstName =  firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String email =  emailTextField.getText();
                    String customerType = cusType.getSelectedItem().toString();
                    String discountType = discType.getSelectedItem().toString();
                    String discountAmount = discountAmountTf.getText();
                    String address = addressTf.getText();
                    String totalTickets = totalTicketsTf.getText();
                    String staffID = staffIDTf.getText();

                    // Establishes a connection the database
                    Connection con = DBConnection.getConnection();
                    // INSERT INTO statement with values from JTextFields
                    PreparedStatement ps = con.prepareStatement("INSERT INTO Customer(FirstName, LastName, Email, " +
                            "CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID) VALUES(?,?,?,?,?,?,?,?,?)");
                    ps.setString(1,firstName);
                    ps.setString(2,lastName);
                    ps.setString(3,email);
                    ps.setString(4,customerType);
                    ps.setString(5,discountType);
                    ps.setString(6,discountAmount);
                    ps.setString(7,address);
                    ps.setString(8,totalTickets);
                    ps.setString(9,staffID);

                    System.out.println(ticketType);

                    //showing error message if any of the text fields are empty
                    if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || customerType.isEmpty() || totalTickets.isEmpty() || staffID.isEmpty()){

                        errorLabel.setVisible(true);
                    }


                    // When Button is pressed PHP MY ADMIN should update
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Customer Successfully Registered");


                    //Clears form once submitted
                    clearRegisterCustomer();

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }


    public void registerSalesReport(){
        registerTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (
                        Connection con = DBConnection.getConnection();
                ) {

                    //column names
                    String ticType = ticketType.getSelectedItem().toString();
                    String payType = paymentType.getSelectedItem().toString();
                    String repType = reportType.getSelectedItem().toString();
                    String departure = departureField.getText();
                    String destination = destinationTextField.getText();
                    String comAmount = commissionAmountField.getText();
                    String ticketQuantity = ticketQuantityTextField.getText();
                    String ticketPrice = ticketPriceField.getText();
                    String subTotal = subTotalField.getText();
                    String grandTotal = grandTotalField.getText();
                    String latePay = latePayment.getSelectedItem().toString();
                    String exchangeRate = exchangeRateField.getText();
                    String ticketDate = ticketDateField.getText();
                    String customerID = customerIDField.getText();
                    String staffID = staffIDField.getText();


                    // INSERT INTO statement with values from JTextFields
                    PreparedStatement ps = con.prepareStatement("INSERT INTO SalesReport(TicketType, PaymentType, ReportType, departure, destination, CommissionAmount, " +
                            "TicketQuantity, ticketPrice, subTotal, grandTotal, LatePayment, exchangeRate, ticketDate, ID, staffID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setString(1,ticType);
                    ps.setString(2,payType);
                    ps.setString(3, repType);
                    ps.setString(4,departure);
                    ps.setString(5,destination);
                    ps.setString(6,comAmount);
                    ps.setString(7,ticketQuantity);
                    ps.setString(8,ticketPrice);
                    ps.setString(9,subTotal);
                    ps.setString(10,grandTotal);
                    ps.setString(11, latePay);
                    ps.setString(12,exchangeRate);
                    ps.setString(13,ticketDate);
                    ps.setString(14,customerID);
                    ps.setString(15,staffID);


                    //showing error message if any of the text fields are empty
                    if(departure.isEmpty() || destination.isEmpty() || comAmount.isEmpty() || ticketQuantity.isEmpty() || ticketPrice.isEmpty() || subTotal.isEmpty()|| grandTotal.isEmpty()|| exchangeRate.isEmpty()|| ticketDate.isEmpty()
                            || customerID.isEmpty()|| staffID.isEmpty()){

                        incompleteEntry.setVisible(true);
                    }

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Ticket added to sales report");



                } catch (ClassNotFoundException | SQLException exc) {
                    exc.printStackTrace();
                }


            }
        });
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






