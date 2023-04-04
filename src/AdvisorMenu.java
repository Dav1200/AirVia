import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdvisorMenu extends JFrame {
    public AdvisorMenu() {


        showCustomer();
        registerCustomer();
        registerSalesReport();
        commissionAmountField.setEditable(false);
       // commissionAmountField.setText(getCommissionRate(ticketType.getSelectedItem().toString()));
        commissionAmountField.setText("0");
        showCombobox();
        errorLabel.setVisible(false);
        incompleteEntry.setVisible(false);
        discountTxt.setEditable(false);
        

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login a = new Login();
                dispose();
            }
        });

        ticketType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (ticketType.getSelectedItem().toString()){
                    case "444":
                        commissionAmountField.setText(getCommissionRate("444"));
                        reportType.setSelectedIndex(0);
                        break;

                    case "440":
                        commissionAmountField.setText(getCommissionRate("440"));
                        reportType.setSelectedIndex(0);
                        break;
                    case "420":
                        commissionAmountField.setText(getCommissionRate("420"));
                        reportType.setSelectedIndex(0);
                        break;
                    case "101":
                        commissionAmountField.setText(getCommissionRate("101"));
                        reportType.setSelectedIndex(1);
                        break;

                    case "201":
                        commissionAmountField.setText(getCommissionRate("201"));
                        reportType.setSelectedIndex(1);
                        break;


                    default:
                        commissionAmountField.setText(getCommissionRate("444"));
                        reportType.setSelectedIndex(0);



                }


                if(ticketType.getSelectedItem().toString().equals("444")){
                    System.out.println("hi");
                }

                if(ticketType.getSelectedItem().toString().equals("440")){
                    System.out.println("hi2");
                }
            }
        });

        CustomerTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                System.out.println(e.getFirstRow());
            }
        });
        customerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(customerComboBox.getSelectedItem().equals("Casual")|| customerComboBox.getSelectedItem().equals("Select Type")){
                    discountTxt.setText("0");

                }
                if(!customerComboBox.getSelectedItem().equals("Select Type") && !customerComboBox.getSelectedItem().equals("Casual")){
                    try (Connection con = DBConnection.getConnection()
                    ){
                        String s  = (String) customerComboBox.getSelectedItem();
                        char a = s.charAt(3);

                        PreparedStatement ps = con.prepareStatement("SELECT DiscountAmount FROM Customer WHERE ID =?");
                        ps.setString(1, String.valueOf(a));


                        ResultSet rs = ps.executeQuery();
                        rs.next();
                        discountTxt.setText(rs.getString("DiscountAmount"));





                    }catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);

                }
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
    private JTextField taxTotalField;
    private JTextField grandTotalField;
    private JComboBox latePayment;
    private JTextField exchangeRateField;
    private JTextField ticketDateField;
    private JTextField customerIDField;
    private JTextField staffIDField;
    private JTextField blankIdtxt;
    private JTextField discountTxt;
    private JLabel discountLabel;
    private JComboBox customerComboBox;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }




    public void showCombobox(){
        try (Connection con = DBConnection.getConnection()
        ){
            PreparedStatement ps = con.prepareStatement("SELECT Alias FROM Customer");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                customerComboBox.addItem(rs.getString("Alias"));
            }




        }  catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException(e);
    }

    }

    public void showCustomer() {
        try (
                Connection con = DBConnection.getConnection()
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
            String ID, Firstname, Lastname, Email, CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID,Alias;
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
                Alias = resultSet.getString(11);

                String[] row = {ID, Firstname, Lastname, Email, CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID,Alias};
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
                showCustomer();
            }
        });

    }

    public String getCommissionRate(String blankType){
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT CommissionRate FROM CommissionRate WHERE BlankType = ?");
            ps.setString(1, blankType);
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            return resultSet.getString(1);

             } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    public void registerSalesReport(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String formattedDateTime = now.format(formatter);
        ticketDateField.setText(formattedDateTime);
        staffIDField.setText(Login.getUserId());

        registerTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try (
                        Connection con = DBConnection.getConnection()
                ) {

                    String s  = (String) customerComboBox.getSelectedItem();
                    char a = s.charAt(3);

                    //column names
                    String ticType = ticketType.getSelectedItem().toString();
                    String blanktype = blankIdtxt.getText();
                    String payType = paymentType.getSelectedItem().toString();
                    String repType = reportType.getSelectedItem().toString();
                    String departure = departureField.getText();
                    String destination = destinationTextField.getText();
                    String comAmount = commissionAmountField.getText();
                    String customer = customerComboBox.getSelectedItem().toString();

                    String discount = discountTxt.getText();
                    String ticketQuantity = ticketQuantityTextField.getText();
                    String ticketPrice = ticketPriceField.getText();
                    String TaxTotal = taxTotalField.getText();

                    String latePay = latePayment.getSelectedItem().toString();
                    String exchangeRate = exchangeRateField.getText();
                    String ticketDate = ticketDateField.getText();
                    String staffID = staffIDField.getText();
                    if(departure.isEmpty() || destination.isEmpty() || comAmount.isEmpty() || ticketQuantity.isEmpty() || ticketPrice.isEmpty() || TaxTotal.isEmpty()|| exchangeRate.isEmpty()|| ticketDate.isEmpty()
                            || staffID.isEmpty()){
                        JOptionPane.showMessageDialog(null,"Fill Details");

                    }
                    String grandTotal = String.valueOf((Integer.parseInt(ticketPrice) * Integer.parseInt(ticketQuantity) ));


                    // INSERT INTO statement with values from JTextFields
                    PreparedStatement ps = con.prepareStatement("INSERT INTO ticket_sales ( ticket_type, blank_id, payment_type, report_type, departure, destination, commission_amount, customer, discount, ticket_quantity, ticket_price, tax_total, grand_total, late_payment, exchange_rate, ticket_date, StaffID)\n" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ");







                    String dis = String.valueOf(Integer.parseInt(grandTotal) * Float.parseFloat(discount));
                    grandTotal = String.valueOf(Float.parseFloat(grandTotal) - Float.parseFloat(dis));
                    grandTotal = String.valueOf(Float.parseFloat(grandTotal) + + Float.parseFloat(TaxTotal));



                    ps.setString(1, ticType);
                    ps.setString(2, blanktype);
                    ps.setString(3, payType);
                    ps.setString(4, repType);
                    ps.setString(5, departure);
                    ps.setString(6, destination);
                    ps.setString(7, comAmount);
                    ps.setString(8, customer);
                    ps.setString(9, discount);
                    ps.setString(10, ticketQuantity);
                    ps.setString(11, ticketPrice);
                    ps.setString(12, TaxTotal);
                    ps.setString(13, grandTotal);
                    ps.setString(14, latePay);
                    ps.setString(15, exchangeRate);
                    ps.setString(16, ticketDate);
                    ps.setString(17, staffID);


                    //showing error message if any of the text fields are empty


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






