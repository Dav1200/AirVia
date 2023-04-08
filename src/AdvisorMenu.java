
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


public class AdvisorMenu extends JFrame {

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
    private JComboBox blankComboBox;
    private JTextField cardtxt;
    private JTextField payDateTxt;
    private JButton DomesticReportButton;
    private JComboBox ticketListCombo;
    private JTextField rName;
    private JTextField rtotal;
    private JButton refundButton;
    private JButton updateButton;

    public AdvisorMenu() {

        showCustomer();
        registerCustomer();
        registerSalesReport();
        addtoRefundCombo();
        commissionAmountField.setEditable(false);
        // commissionAmountField.setText(getCommissionRate(ticketType.getSelectedItem().toString()));
        commissionAmountField.setText("0");
        showCombobox();
        errorLabel.setVisible(false);
        incompleteEntry.setVisible(false);
        discountTxt.setEditable(false);
        cardtxt.setEditable(false);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = now.format(formatter);
        payDateTxt.setText(formattedDateTime);


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


                    default:
                        blankComboBox.removeAllItems();
                        showBlankComboBox();
                        commissionAmountField.setText(getCommissionRate("444"));
                        reportType.setSelectedIndex(0);


                }


                if (ticketType.getSelectedItem().toString().equals("444")) {
                    System.out.println("hi");
                }

                if (ticketType.getSelectedItem().toString().equals("440")) {
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
                if (customerComboBox.getSelectedItem().equals("Casual") || customerComboBox.getSelectedItem().equals("Select Type")) {
                    discountTxt.setText("0");

                }
                if (!customerComboBox.getSelectedItem().equals("Select Type") && !customerComboBox.getSelectedItem().equals("Casual")) {
                    try (Connection con = DBConnection.getConnection()
                    ) {
                        PreparedStatement ps1 = con.prepareStatement("SELECT ID,Alias FROM Customer");

                        ResultSet rs1 = ps1.executeQuery();

                        String s = (String) customerComboBox.getSelectedItem();
                        char a = s.charAt(s.length() - 1);

                        PreparedStatement ps = con.prepareStatement("SELECT DiscountAmount FROM Customer WHERE ID =?");
                        ps.setString(1, String.valueOf(a));


                        ResultSet rs = ps.executeQuery();
                        rs.next();
                        discountTxt.setText(rs.getString("DiscountAmount"));


                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);

                    }
                }
            }
        });

        ((AbstractDocument) cardtxt.getDocument()).setDocumentFilter(new DocumentFilter() {
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


        ((AbstractDocument) ticketDateField.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        ticketDateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                super.keyTyped(e);
                if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {

                    if (ticketDateField.getText().toString().length() == 2) {

                        ticketDateField.setText(ticketDateField.getText() + "/");
                    }
                    if (ticketDateField.getText().toString().length() == 5) {
                        ticketDateField.setText(ticketDateField.getText() + "/");
                    }
                }
            }
        });


        paymentType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paymentType.getSelectedItem().toString().equals("Card")) {
                    cardtxt.setEditable(true);
                } else {
                    cardtxt.setEditable(false);
                }

                if (paymentType.getSelectedItem().toString().equals("LatePayment")) {
                    payDateTxt.setEditable(false);
                    payDateTxt.setText("");
                } else {
                    payDateTxt.setEditable(true);
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedDateTime = now.format(formatter);
                    payDateTxt.setText(formattedDateTime);
                }
            }
        });
        cardtxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);


                if (cardtxt.getText().toString().length() > 19) {
                    //dont allow the user to input after length is more than 16
                    e.consume();
                }


            }
        });
        createIndividualReportButton.addActionListener(new ActionListener() {
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
        ticketListCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection con = DBConnection.getConnection()
                ) {

    if(ticketListCombo.getItemCount() != 0){
                    PreparedStatement ps = con.prepareStatement("SELECT customer,grand_total FROM ticket_sales WHERE blank_id = ? AND refund = 'No' ");
                    ps.setString(1, ticketListCombo.getSelectedItem().toString());

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        rName.setText(rs.getString(1));
                        rtotal.setText(rs.getString(2));
                    }}
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        refundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection con = DBConnection.getConnection()
                ) {

                    PreparedStatement ps = con.prepareStatement("UPDATE advisor_blanks SET status = 'Unused' WHERE blanks = ?");
                    ps.setString(1, ticketListCombo.getSelectedItem().toString());
                    ps.executeUpdate();

                    PreparedStatement ps1 = con.prepareStatement("UPDATE ticket_sales SET refund = 'Yes' WHERE blank_id = ?");
                    ps1.setString(1, ticketListCombo.getSelectedItem().toString());
                    ps1.executeUpdate();

                    PreparedStatement ps2 = con.prepareStatement("SELECT grand_total, customer FROM ticket_sales WHERE blank_id = ?");
                    ps2.setString(1, ticketListCombo.getSelectedItem().toString());
                    ResultSet rs = ps2.executeQuery();
                    rs.next();
                    String customer_id = rs.getString(2);
                    customer_id = customer_id.substring(customer_id.length() - 1);
                    double grandtotal = Double.parseDouble(rs.getString(1));

                    PreparedStatement ps3 = con.prepareStatement("SELECT TotalTickets, TotalPayment FROM Customer WHERE ID = ?");
                    ps3.setString(1, customer_id);
                    ResultSet rs1 = ps3.executeQuery();
                    rs1.next();

                    double customerTotal = Double.parseDouble(rs1.getString(2));
                    int customerTotalTickets = Integer.parseInt(rs1.getString(1));

                    Double finalTotal = customerTotal - grandtotal;
                    customerTotalTickets--;

                    PreparedStatement ps4 = con.prepareStatement("UPDATE Customer SET TotalTickets = ?, TotalPayment = ? WHERE ID = ?");
                    ps4.setString(1, String.valueOf(customerTotalTickets));
                    ps4.setString(2, String.valueOf(finalTotal));
                    ps4.setString(3, customer_id);
                    ps4.executeUpdate();

                    rName.setText("");
                    rtotal.setText("");
                    ticketListCombo.removeAllItems();

                    addtoRefundCombo();

                    dialog("Successfully Refunded");


                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticketListCombo.removeAllItems();

                addtoRefundCombo();
            }
        });
        DomesticReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GlobalInterlineReport Gil = new GlobalInterlineReport();
                Gil.setContentPane(Gil.getPanel1());
                Gil.setVisible(true);
                Gil.setSize(1000, 600);
                Gil.setLocationRelativeTo(null);
                Gil.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    public void showBlankComboBox() {

        try (Connection con = DBConnection.getConnection()
        ) {
            PreparedStatement ps = con.prepareStatement("SELECT blanks FROM advisor_blanks WHERE advisor_id = ? AND status = ? AND blanks LIKE ?");
            ps.setString(1, Login.getUserId());
            ps.setString(2, "Unused");
            ps.setString(3, ticketType.getSelectedItem().toString() + "%");
            System.out.println(ticketType.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                showerrorblanks();
            } else {
                blankComboBox.addItem(rs.getString("blanks"));
                while (rs.next()) {
                    blankComboBox.addItem(rs.getString("blanks"));
                }
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void showCombobox() {
        try (Connection con = DBConnection.getConnection()
        ) {
            PreparedStatement ps = con.prepareStatement("SELECT ID,Alias FROM Customer");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customerComboBox.addItem(rs.getString("Alias") + " " + rs.getString("ID"));
            }


        } catch (SQLException | ClassNotFoundException e) {
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
            String ID, Firstname, Lastname, Email, CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID, Alias;
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

                String[] row = {ID, Firstname, Lastname, Email, CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID, Alias};
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

    private void clearRegisterCustomer() {
        firstNameTextField.setText(null);
        lastNameTextField.setText(null);
        emailTextField.setText(null);
        discountAmountTf.setText(null);
        addressTf.setText(null);
        totalTicketsTf.setText(null);
        staffIDTf.setText(null);
    }

    public void registerCustomer() {
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
                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String email = emailTextField.getText();
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
                    ps.setString(1, firstName);
                    ps.setString(2, lastName);
                    ps.setString(3, email);
                    ps.setString(4, customerType);
                    ps.setString(5, discountType);
                    ps.setString(6, discountAmount);
                    ps.setString(7, address);
                    ps.setString(8, totalTickets);
                    ps.setString(9, staffID);

                    System.out.println(ticketType);

                    //showing error message if any of the text fields are empty
                    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || customerType.isEmpty() || totalTickets.isEmpty() || staffID.isEmpty()) {

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
                showCustomer();
            }
        });

    }

    public void dialog(String s) {
        JOptionPane.showMessageDialog(this, s
        );
    }

    public String getCommissionRate(String blankType) {
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

    public void showerrorblanks() {
        JOptionPane.showMessageDialog(this, "No Assigned Blanks");
    }

    public void registerSalesReport() {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = now.format(formatter);
        ticketDateField.setText(formattedDateTime);
        staffIDField.setText(Login.getUserId());

        registerTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try (
                        Connection con = DBConnection.getConnection()
                ) {

                    String s = (String) customerComboBox.getSelectedItem();
                    char a = s.charAt(3);

                    if (customerComboBox.getSelectedItem().toString().equals("Casual") && paymentType.getSelectedItem().toString().equals("LatePayment")) {
                        dialog("Casual Customer cannot have Late Payment");
                        return;
                    }

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

                    if (departure.isEmpty() || destination.isEmpty() || comAmount.isEmpty() || ticketQuantity.isEmpty() || ticketPrice.isEmpty() || TaxTotal.isEmpty() || exchangeRate.isEmpty() || ticketDate.isEmpty()
                            || staffID.isEmpty() || blanktype.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Fill Details");

                    }


                    // INSERT INTO statement with values from JTextFields
                    PreparedStatement ps = con.prepareStatement("INSERT INTO ticket_sales ( ticket_type, blank_id, payment_type, report_type, departure, destination, commission_amount, customer, discount, refund, ticket_price, tax_total, grand_total, exchange_rate, ticket_date, StaffID,card_detail,payment_date)\n" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?); ");

                    String grandTotal = String.valueOf((Integer.parseInt(ticketPrice)));
                    String dis = String.valueOf(Integer.parseInt(grandTotal) * Float.parseFloat(discount));
                    grandTotal = String.valueOf(Float.parseFloat(grandTotal) - Float.parseFloat(dis));
                    grandTotal = String.valueOf(Float.parseFloat(grandTotal) + Float.parseFloat(TaxTotal));


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


                } catch (ClassNotFoundException | SQLException exc) {
                    exc.printStackTrace();
                }


            }

        });

        clearRegisterCustomer();
    }

    public void addtoRefundCombo() {

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






