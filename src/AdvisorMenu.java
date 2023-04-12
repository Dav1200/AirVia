
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * class for advisor staff
 */
public class AdvisorMenu extends JFrame {

    public JPanel getAdPlane() {
        return adPlane;
    }

    //fields used throughout AdvisorMenu
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
    private JTextField totalTicketsTf;
    private JTextField customerTypeTf;
    private JTextField discountTypeTf;
    private JTextField departureTextField;
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
    private JComboBox latePayment;
    private JTextField exchangeRateField;
    private JTextField ticketDateField;
    private JTextField customerIDField;
    private JTextField staffIDField;
    private JTextField blankIdtxt;
    private JTextField discountTxt;
    private JLabel discountLabel;
    private JComboBox customerComboBox;
    private JComboBox blankComboBox;
    private JTextField cardtxt;
    private JTextField payDateTxt;
    private JButton DomesticReportButton;
    private JComboBox ticketListCombo;
    private JTextField rName;
    private JTextField rtotal;
    private JButton refundButton;
    private JButton updateButton;
    private JComboBox mcoComboBox;
    private JPanel test;


    /**
     * calls functions needed to display the advisor menu
     */

    //Constructor
    public AdvisorMenu() {
        //function calls when constructor is called
        showCustomer();
        registerCustomer();
        registerSalesReport();
        addtoRefundCombo();
        showCombobox();
        //set default variables
        commissionAmountField.setEditable(false);
        // commissionAmountField.setText(getCommissionRate(ticketType.getSelectedItem().toString()));
        commissionAmountField.setText("0");
        errorLabel.setVisible(false);
        incompleteEntry.setVisible(false);
        discountTxt.setEditable(false);
        cardtxt.setEditable(false);
        discType.setEnabled(false);
        discountAmountTf.setText("0");
        discountAmountTf.setEditable(false);

        //get local date set it for a paydatetxt field
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = now.format(formatter);
        payDateTxt.setText(formattedDateTime);


        /**
         * if logout button is pressed
         */
        logoutButton.addActionListener(new ActionListener() {
            /**
             * if the logout button is pressed, close the panel and return to the login screen
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //close the current form and open a login form.
                Login a = new Login();
                dispose();
            }
        });


        /**
         * select the appropriate commission rate for the blank type selected.
         */
        ticketType.addActionListener(new ActionListener() {
            /**
             * choosing blank type
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {


                //selects the correct commission rate for each type of blank through series of switch cases
                switch (ticketType.getSelectedItem().toString()) {
                    case "444":
                        blankComboBox.removeAllItems();
                        showBlankComboBox();
                        commissionAmountField.setText(getCommissionRate("444"));
                        reportType.setSelectedIndex(0);
                        break;

                    case "440":
                        blankComboBox.removeAllItems();
                        showBlankComboBox();
                        commissionAmountField.setText(getCommissionRate("440"));
                        reportType.setSelectedIndex(0);
                        break;
                    case "420":
                        blankComboBox.removeAllItems();
                        showBlankComboBox();
                        commissionAmountField.setText(getCommissionRate("420"));
                        reportType.setSelectedIndex(0);
                        break;
                    case "101":
                        blankComboBox.removeAllItems();
                        showBlankComboBox();
                        commissionAmountField.setText(getCommissionRate("101"));
                        reportType.setSelectedIndex(1);
                        break;

                    case "201":
                        blankComboBox.removeAllItems();
                        showBlankComboBox();
                        commissionAmountField.setText(getCommissionRate("201"));
                        reportType.setSelectedIndex(1);
                        break;


                    //default blank type is set to 444
                    default:
                        blankComboBox.removeAllItems();
                        showBlankComboBox();
                        commissionAmountField.setText(getCommissionRate("444"));
                        reportType.setSelectedIndex(0);


                }

            }
        });

        /**
         * output the row selected in customer table
         */

        CustomerTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                System.out.println(e.getFirstRow());
            }
        });

        /**
         * List all the customers registered in the system.
         * selects the discount associated with the customer
         */

        customerComboBox.addActionListener(new ActionListener() {
            /**
             * make sure that fields can not be empty
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                //avoid miscellaneous types
                if (customerComboBox.getSelectedItem().equals("Casual") || customerComboBox.getSelectedItem().equals("Select Type")) {
                    discountTxt.setText("0");

                }
                if (!customerComboBox.getSelectedItem().equals("Select Type") && !customerComboBox.getSelectedItem().equals("Casual")) {

                    //connect to Db
                    try (Connection con = DBConnection.getConnection()
                    ) {
                        //select the customemr id and alias from db
                        PreparedStatement ps1 = con.prepareStatement("SELECT ID,Alias FROM Customer");
                        //store in result set
                        ResultSet rs1 = ps1.executeQuery();

                        String s = (String) customerComboBox.getSelectedItem();
                        char a = s.charAt(s.length() - 1);
                        //select the discount amount
                        PreparedStatement ps = con.prepareStatement("SELECT DiscountAmount FROM Customer WHERE ID =?");
                        ps.setString(1, String.valueOf(a));

                        //store discount for each customer in result set
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            //set the discount table to the result set in db
                            discountTxt.setText(rs.getString("DiscountAmount"));
                        }
                        //handle exceptions
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);

                    }
                }
            }
        });

        /**
         * format card txt to 16 digits
         */

        ((AbstractDocument) cardtxt.getDocument()).setDocumentFilter(new DocumentFilter() {
            /**
             * used to insert string into specific index
             * @param fb
             * @param offset
             * @param string
             * @param attr
             * @throws BadLocationException
             */
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                // Allow only numeric characters and "-" to be entered
                String filteredString = string.replaceAll("[^\\d]", "");

                // Only allow "-" to be inserted at positions  2 and 5
                if (filteredString.equals("/") && (offset != 2 && offset != 5)) {
                    return;
                }

                super.insertString(fb, offset, filteredString, attr);
            }

            /**
             * used to replace the string index
             * @param fb
             * @param offset
             * @param length
             * @param string
             * @param attr
             * @throws BadLocationException
             */
            @Override
            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
                // Allow only numeric characters and "-" to be entered
                String filteredString = string.replaceAll("[^\\d]", "");

                // Only allow "-" to be inserted at positions 2 and 5
                if (filteredString.equals("/") && (offset != 2 && offset != 5)) {
                    return;
                }

                super.replace(fb, offset, length, filteredString, attr);
            }
        });


        /**
         * date format dd/mm/yyyy
         * only allows digits to be inputted
         */
        ((AbstractDocument) ticketDateField.getDocument()).setDocumentFilter(new DocumentFilter() {
            /**
             * used to format the ticket date of field
             * @param fb
             * @param offset
             * @param string
             * @param attr
             * @throws BadLocationException
             */
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                // Allow only numeric characters and "-" to be entered
                String filteredString = string.replaceAll("[^\\d/]", "");

                // Only allow "-" to be inserted at positions  2 and 5
                if (filteredString.equals("/") && (offset != 2 && offset != 5)) {
                    return;
                }

                super.insertString(fb, offset, filteredString, attr);
            }

            //

            /**
             * replace the appropriate characters and only allows digits to be inputted
             * @param fb
             * @param offset
             * @param length
             * @param string
             * @param attr
             * @throws BadLocationException
             */
            @Override
            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
                // Allow only numeric characters and "-" to be entered
                String filteredString = string.replaceAll("[^\\d/]", "");

                // Only allow "-" to be inserted at positions 2 and 5
                if (filteredString.equals("/") && (offset != 2 && offset != 5)) {
                    return;
                }

                super.replace(fb, offset, length, filteredString, attr);
            }
        });



        /**
         * listener for typing in date field, add / where appropriate dd/nn/yyyy
         */
        ticketDateField.addKeyListener(new KeyAdapter() {
            /**
             * putting / in date field, when numbers are entered
             * @param e
             */
            @Override
            public void keyPressed(KeyEvent e) {

                super.keyTyped(e);
                //dont allow backspace to part of inserting a slash
                if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {

                    //input slash at the correct position in the date field.
                    if (ticketDateField.getText().toString().length() == 2) {

                        ticketDateField.setText(ticketDateField.getText() + "/");
                    }
                    if (ticketDateField.getText().toString().length() == 5) {
                        ticketDateField.setText(ticketDateField.getText() + "/");
                    }
                }
            }
        });


        /**
         * drop down menu which allows the user to pick their payment type
         */

        paymentType.addActionListener(new ActionListener() {
            /**
             * chooses type of payment
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                cardtxt.setText("");

                //if card is selected unlock the card detail textfield.
                if (paymentType.getSelectedItem().toString().equals("Card")) {
                    cardtxt.setEditable(true);
                } else {
                    cardtxt.setEditable(false);
                }

                //if latepayment is seleceted date field is not editable as payment is not made
                if (paymentType.getSelectedItem().toString().equals("LatePayment")) {
                    payDateTxt.setEditable(false);
                    payDateTxt.setText("");
                } else {

                    //set the current date in the pay date field.
                    payDateTxt.setEditable(true);
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedDateTime = now.format(formatter);
                    payDateTxt.setText(formattedDateTime);
                }
            }
        });
        /**
         * only allow card length to be 16 digits
         */
        cardtxt.addKeyListener(new KeyAdapter() {
            /**
             * make sure to not get more than 16 chars including space
             * @param e
             */
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                if (cardtxt.getText().toString().length() > 15) {
                    //dont allow the user to input after length is more than 16
                    e.consume();
                }
            }
        });

        /**
         * create a new frame when individual report needs to be generated.
         */

        createIndividualReportButton.addActionListener(new ActionListener() {
            /**
             * making individual interline report page when the button is pressed
             * @param e
             */

            @Override
            public void actionPerformed(ActionEvent e) {
                IndividualInterlineReport Iir = new IndividualInterlineReport();
                Iir.setContentPane(Iir.getPanel1());
                Iir.setVisible(true);
                Iir.setSize(1000, 600);
                Iir.setLocationRelativeTo(null);
                Iir.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        });

        /**
         * list all the blanks available for refund
         */
        ticketListCombo.addActionListener(new ActionListener() {
            /**
             * check which type of blank is selected in the combobox and if it is able to be refunded.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //db connecttion
                try (Connection con = DBConnection.getConnection()
                ) {
                        //get all thinks which are available for refunds and put them in a result set
                    if (ticketListCombo.getItemCount() != 0) {
                        PreparedStatement ps = con.prepareStatement("SELECT customer,grand_total FROM ticket_sales WHERE blank_id = ? AND refund = 'No' ");
                        ps.setString(1, ticketListCombo.getSelectedItem().toString());

                        ResultSet rs = ps.executeQuery();

                        //if data exists in the database for sql query.
                        if (rs.next()) {
                            rName.setText(rs.getString(1));
                            rtotal.setText(rs.getString(2));
                        }
                    }

                    //handle exceptions
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        /**
         * process of processing customer refund.
         */
        refundButton.addActionListener(new ActionListener() {
            /**
             * choosing which tickets to refund and check if they are assigned or not
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //db connection
                try (Connection con = DBConnection.getConnection()
                ) {

                    //Update the blank to unassigned

                    PreparedStatement ps = con.prepareStatement("UPDATE advisor_blanks SET status = 'Unused' WHERE blanks = ?");
                    ps.setString(1, ticketListCombo.getSelectedItem().toString());
                    //excecute the query
                    ps.executeUpdate();

                    // update the ticket sales to refund status to yes, it can be assigned again.
                    PreparedStatement ps1 = con.prepareStatement("UPDATE ticket_sales SET refund = 'Yes' WHERE blank_id = ?");
                    ps1.setString(1, ticketListCombo.getSelectedItem().toString());
                    //excecute the query
                    ps1.executeUpdate();

                    //remove the ticket quantity and price from customer table as ticket's refunded
                    PreparedStatement ps2 = con.prepareStatement("SELECT grand_total, customer FROM ticket_sales WHERE blank_id = ?");
                    ps2.setString(1, ticketListCombo.getSelectedItem().toString());

                    //get reseult set
                    ResultSet rs = ps2.executeQuery();
                    rs.next();
                    String customer_id = rs.getString(2);
                    customer_id = customer_id.substring(customer_id.length() - 1);
                    double grandtotal = Double.parseDouble(rs.getString(1));

                    //remove the ticket quantity and price from customer table as ticket's refunded
                    PreparedStatement ps3 = con.prepareStatement("SELECT TotalTickets, TotalPayment FROM Customer WHERE ID = ?");
                    ps3.setString(1, customer_id);
                    ResultSet rs1 = ps3.executeQuery();
                    rs1.next();

                    double customerTotal = Double.parseDouble(rs1.getString(2));
                    int customerTotalTickets = Integer.parseInt(rs1.getString(1));

                    Double finalTotal = customerTotal - grandtotal;
                    customerTotalTickets--;
                    //set the ticket quantity and price from customer table as ticket's refunded
                    PreparedStatement ps4 = con.prepareStatement("UPDATE Customer SET TotalTickets = ?, TotalPayment = ? WHERE ID = ?");

                    //set variable values for the sql statement to desired values.
                    ps4.setString(1, String.valueOf(customerTotalTickets));
                    ps4.setString(2, String.valueOf(finalTotal));
                    ps4.setString(3, customer_id);
                    //execute the query
                    ps4.executeUpdate();

                    //reset the values
                    rName.setText("");
                    rtotal.setText("");

                    //refrresh the ticket combo box as a blank was refunded, should be removed.
                    ticketListCombo.removeAllItems();
                    addtoRefundCombo();
                    //show prompt saying the refund was sucessful
                    dialog("Successfully Refunded");

                    //handle Exceptions
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        /**
         * force update the combobox
         */
        updateButton.addActionListener(new ActionListener() {
            /**
             * removes all items then add them back
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ticketListCombo.removeAllItems();

                addtoRefundCombo();
            }
        });

        /**
         * when domestic report button is pressed make a new frame for appropriate class
         */
        DomesticReportButton.addActionListener(new ActionListener() {
            /**
             * removes all items then add them back
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                    //call consturctor and set essential values
                IndividualDomesticReport Idr = new IndividualDomesticReport();
                Idr.setContentPane(Idr.getPanel1());
                Idr.setVisible(true);
                //window size
                Idr.setSize(1000, 600);
                Idr.setLocationRelativeTo(null);
                Idr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        });
        cusType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cusType.getSelectedItem().toString().equals("Regular")){
                    discType.setEnabled(false);
                    discountAmountTf.setText("0");
                    discountAmountTf.setEditable(false);
                }
                else{
                    discType.setEnabled(true);
                    discountAmountTf.setEditable(true);
                }
            }
        });

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    /**
     * list all blanks available for the advisor to sell to customers
     */
    public void showBlankComboBox() {

        try (Connection con = DBConnection.getConnection()
        ) {
            //get all blanks which meet the conditions
            // id should be advisor that is logged in
            //status must be unused so it can used to sell the blank and register it in db
            PreparedStatement ps = con.prepareStatement("SELECT blanks FROM advisor_blanks WHERE advisor_id = ? AND status = ? AND blanks LIKE ?");
            ps.setString(1, Login.getUserId());
            ps.setString(2, "Unused");
            ps.setString(3, ticketType.getSelectedItem().toString() + "%");
            System.out.println(ticketType.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();

            //if no blanks show error
            if (!rs.next()) {
                showerrorblanks();
            } else {
                //otherwise add the blanks to the combo box
                blankComboBox.addItem(rs.getString("blanks"));
                while (rs.next()) {
                    blankComboBox.addItem(rs.getString("blanks"));
                }
            }
        //handle exceptions

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * add all customer in the db to combo box
     */

    public void showCombobox() {
        //connect to db
        try (Connection con = DBConnection.getConnection()
        ) {
            //sql query to be exectured
            PreparedStatement ps = con.prepareStatement("SELECT ID,Alias FROM Customer");

            ResultSet rs = ps.executeQuery();

            //update comobo box if result is returned
            while (rs.next()) {
                customerComboBox.addItem(rs.getString("Alias") + " " + rs.getString("ID"));
            }
            //handle exceptions
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * show all customers registered in the system in a table format
     */
    public void showCustomer() {
        try (//connect to to db
                Connection con = DBConnection.getConnection()
        ) {
            //select all customers from db
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
            String ID, Firstname, Lastname, Email, CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID, Alias,total;
            while (resultSet.next()) {
                //get the info and add it as a row in the jtable
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
                total = resultSet.getString(12);

                String[] row = {ID, Firstname, Lastname, Email, CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID, Alias,total};
                model.addRow(row);


                // row filter
                TableRowSorter tableRowSorter = new TableRowSorter(model);
                CustomerTable.setRowSorter(tableRowSorter);

                //search customer
                //if search button is pressed, search the customer via ID
                searchCustomerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String searchText = searchCustomerTextField.getText();
                        tableRowSorter.setRowFilter(new Search(searchText));
                    }
                });

            }

            //handle exceptions
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * clear the customer details after it has been registered, error handling for accident spam button
     */
    private void clearRegisterCustomer() {
        //set text field to empty
        firstNameTextField.setText(null);
        lastNameTextField.setText(null);
        emailTextField.setText(null);
        discountAmountTf.setText(null);
        addressTf.setText(null);
        //totalTicketsTf.setText(null);
        //staffIDTf.setText(null);
    }

    /**
     * register customer to the system
     */
    public void registerCustomer() {
        /**
         * if clear button pressed, clear all text fields
         */
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRegisterCustomer();
            }
        });

        /**
         * register a new customer in database
         */
        registerButton.addActionListener(new ActionListener() {
            /**
             * take in customer details from the text fields, and add them to the database
             * make sure that the text fields which cannot be empty are not empty
             * @param e
             */

            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    //set discount amount to 0

                    // Takes in what the advisor has entered
                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String email = emailTextField.getText();
                    String customerType = cusType.getSelectedItem().toString();
                    String discountType = discType.getSelectedItem().toString();
                    String discountAmount = discountAmountTf.getText();
                    String address = addressTf.getText();




                    // Establishes a connection the database
                    Connection con = DBConnection.getConnection();


                    // INSERT INTO statement with values from JTextFields
                    PreparedStatement ps = con.prepareStatement("INSERT INTO Customer(FirstName, LastName, Email, " +
                            "CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID,Alias,TotalPayment) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setString(1, firstName);
                    ps.setString(2, lastName);
                    ps.setString(3, email);
                    ps.setString(4, customerType);
                    ps.setString(5, discountType);
                    ps.setString(6, discountAmount);
                    ps.setString(7, address);
                    ps.setString(8, "0");
                    ps.setString(9, Login.getUserId());
                    ps.setString(10, staffIDTf.getText());
                    ps.setString(11,"0" );
                    System.out.println(ticketType);

                    //showing error message if any of the text fields are empty
                    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || customerType.isEmpty() || staffIDTf.getText().isEmpty()) {

                        errorLabel.setVisible(true);
                    }


                    // When Button is pressed PHP MY ADMIN should update
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Customer Successfully Registered");


                    //Clears form once submitted
                    clearRegisterCustomer();

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                //update the customers jtable as new customer is added
                showCustomer();
            }
        });

    }

    /**
     * general code for all prompts
     * called when prompt needs to be shown
     * @param s
     */

    public void dialog(String s) {
        JOptionPane.showMessageDialog(this, s
        );
    }

    /**
     * update the commission rate for advisor form
     * returns the commission rate for blank provided in parameter
     * @param blankType
     * @return commission amount
     */
    public String getCommissionRate(String blankType) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT CommissionRate FROM CommissionRate WHERE BlankType = ?");
            ps.setString(1, blankType);
            ResultSet resultSet = ps.executeQuery();
//return commission amount
            resultSet.next();
            return resultSet.getString(1);

            //handle expcetions
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    /**
     * error label
     */
    public void showerrorblanks() {
        JOptionPane.showMessageDialog(this, "No Assigned Blanks");
    }


    /**
     * register a sale in the database
     */
    public void registerSalesReport() {
        //get local date essential for recording when sale was recorded
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = now.format(formatter);
        //format the date
        ticketDateField.setText(formattedDateTime);
        staffIDField.setText(Login.getUserId());

        /**
         * button to register ticket
         */
        registerTicketButton.addActionListener(new ActionListener() {
            /**
             *  check if requirements are met and add to the database
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                try (
                        //connect to db
                        Connection con = DBConnection.getConnection()
                ) {

                    String s = (String) customerComboBox.getSelectedItem();
                    char a = s.charAt(3);

                    //check if requirements are met, valid options must be selected inorder to record a sale ticket
                    if (customerComboBox.getSelectedItem().toString().equals("Casual") && paymentType.getSelectedItem().toString().equals("LatePayment")) {
                        dialog("Casual Customer cannot have Late Payment");
                        return;
                    }
                    //if card payment is selected, cardtxt must be filled

                    if (paymentType.getSelectedItem().toString().equals("Card") && cardtxt.getText().isEmpty()) {
                        dialog("Enter Card Details");
                    }
                    //column names
                    String ticType = ticketType.getSelectedItem().toString();
                    String blanktype = blankComboBox.getSelectedItem().toString();
                    String payType = paymentType.getSelectedItem().toString();
                    String repType = reportType.getSelectedItem().toString();
                    String departure = departureField.getText();
                    String destination = destinationTextField.getText();
                    String comAmount = commissionAmountField.getText();
                    String customer = customerComboBox.getSelectedItem().toString();

                    String discount = discountTxt.getText();
                    String ticketQuantity = "No";
                    String ticketPrice = ticketPriceField.getText();
                    String TaxTotal = taxTotalField.getText();
                    String card_detail = cardtxt.getText();

                    //String latePay = latePayment.getSelectedItem().toString();
                    String exchangeRate = exchangeRateField.getText();
                    String ticketDate = ticketDateField.getText();
                    String staffID = staffIDField.getText();
                    String payment_date = payDateTxt.getText();


                    //ensure validity for empty text fields
                    if (departure.isEmpty() || destination.isEmpty() || comAmount.isEmpty() || ticketQuantity.isEmpty() || ticketPrice.isEmpty() || TaxTotal.isEmpty() || exchangeRate.isEmpty() || ticketDate.isEmpty()
                            || staffID.isEmpty() || blanktype.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Fill Details");

                    }


                    // INSERT INTO statement with values from JTextFields
                    PreparedStatement ps = con.prepareStatement("INSERT INTO ticket_sales ( ticket_type, blank_id, payment_type, report_type, departure, destination, commission_amount, customer, discount, refund, ticket_price, tax_total, grand_total, exchange_rate, ticket_date, StaffID,card_detail,payment_date)\n" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?); ");
                    //work out grand total, commission rate and etc
                    String grandTotal = String.valueOf((Integer.parseInt(ticketPrice)));
                    String dis = String.valueOf(Integer.parseInt(grandTotal) * Float.parseFloat(discount));
                    grandTotal = String.valueOf(Float.parseFloat(grandTotal) - Float.parseFloat(dis));
                    grandTotal = String.valueOf(Float.parseFloat(grandTotal) + Float.parseFloat(TaxTotal));
                    //setup variables/details which will be inserted into the database

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
                    //ps.setString(14, latePay);
                    ps.setString(14, exchangeRate);
                    ps.setString(15, ticketDate);
                    ps.setString(16, staffID);
                    ps.setString(17, card_detail);
                    ps.setString(18, payment_date);


                    PreparedStatement ps1 = con.prepareStatement("UPDATE advisor_blanks SET status = ? WHERE blanks =?");
                    ps1.setString(1, "Used");
                    ps1.setString(2, blanktype);
                    ps1.executeUpdate();
                    //showing error message if any of the text fields are empty

                    ps.executeUpdate();
                    int total_ticket = 0;
                    ResultSet rs;


                    //update the customer details including how many tickets they have bought and total spent

                    if (!customerComboBox.getSelectedItem().toString().equals("Casual")) {

                        PreparedStatement ps3 = con.prepareStatement("SELECT TotalTickets, TotalPayment FROM Customer WHERE ID = ?");
                        ps3.setString(1, customerComboBox.getSelectedItem().toString().substring(((String) customerComboBox.getSelectedItem()).length() - 1));
                        rs = ps3.executeQuery();
                        if (rs.next()) {

                            total_ticket = rs.getInt(1);
                            double tcprice = rs.getDouble(2);
                            //total_ticket += Integer.parseInt(ticketQuantity);
                            tcprice += Double.parseDouble(grandTotal);

                            PreparedStatement ps4 = con.prepareStatement("UPDATE Customer SET TotalTickets = TotalTickets + 1  , TotalPayment = ? WHERE ID = ?");

                            ps4.setDouble(1, tcprice);
                            ps4.setString(2, customerComboBox.getSelectedItem().toString().substring(((String) customerComboBox.getSelectedItem()).length() - 1));
                            ps4.executeUpdate();


                        }

                    }

                    ticketListCombo.removeAllItems();

                    addtoRefundCombo();
                    JOptionPane.showMessageDialog(null, "Ticket added to sales report");


                    //handle exception
                } catch (ClassNotFoundException | SQLException exc) {
                    exc.printStackTrace();
                }


            }

        });
        //update table

        clearRegisterCustomer();
    }


    /**
     * add to refund combo box, list all blanks which can be returned
     */

    public void addtoRefundCombo() {

        /**
         * adding item from combobox
         */
        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT blank_id FROM ticket_sales WHERE refund = ?");
            ps.setString(1, "No");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ticketListCombo.addItem(rs.getString(1));

            }


        } catch (ClassNotFoundException | SQLException exc) {
            exc.printStackTrace();
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
}






