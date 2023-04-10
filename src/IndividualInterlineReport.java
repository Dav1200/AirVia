import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class IndividualInterlineReport extends JFrame {
    //fields
    private JPanel panel1;
    private JButton printReportButton;
    private JTable interlineIndividual;
    private JScrollPane Pane1;
    private JButton generateReportButton;
    private JTextField netAmount;
    private JTextField totalComAmount;
    private JTextField totalPaid;
    private JTextField subTotal;
    private JTextField startDate;
    private JTextField endDate;
    private JButton search;


    //constructor
    public IndividualInterlineReport() {

        //display report on table
        showReport();

        //functionality for printing report
        printReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Printing ");
            }
        });

//format date
        ((AbstractDocument) startDate.getDocument()).setDocumentFilter(new DocumentFilter() {
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
//format date
        ((AbstractDocument) endDate.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        //check each key inputted by the user, add / where appropriate for date format
        endDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
//do not allow then length of the date to be more than 10
                if (endDate.getText().toString().length() > 10) {
                    e.consume();
                }//dont allow backspace to be registered as a key
                if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {

                    if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
//put slash at appropriate positions
                        if (endDate.getText().toString().length() == 2) {

                            endDate.setText(endDate.getText() + "/");
                        }
                        //put slash at appropriate positions
                        if (endDate.getText().toString().length() == 5) {
                            endDate.setText(endDate.getText() + "/");
                        }
                    }
                }
            }

        });

        //check each key inputted by the user, add / where appropriate for date format
        startDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
//put slash at appropriate positions
                    if (startDate.getText().toString().length() == 2) {

                        startDate.setText(startDate.getText() + "/");
                    }//put slash at appropriate positions
                    if (startDate.getText().toString().length() == 5) {
                        startDate.setText(startDate.getText() + "/");
                    }
                }
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (
                        Connection con = DBConnection.getConnection()
                ) {
                    //sql query which gets data from appropriate tables
                    //gets data which will be used to generate and display individual interline  report
                    PreparedStatement ps = con.prepareStatement("SELECT ticket_sales.StaffID, Staff.FirstName, ticket_sales.blank_id, ticket_sales.customer, " +
                            "ticket_sales.tax_total, ticket_sales.grand_total, ticket_sales.commission_amount, ticket_sales.grand_total - tax_total as Price , ticket_sales.ticket_date " +
                            "FROM ticket_sales LEFT JOIN Staff ON ticket_sales.StaffID = Staff.StaffID WHERE Staff.Role = 'Travel Advisor' AND ticket_sales.report_type = 'Interline' AND ticket_sales.StaffID = ? " +
                            "AND STR_TO_DATE(ticket_sales.ticket_date, '%d/%m/%Y') BETWEEN ? AND ?");

                    //set the appropriate values for sql query
                    ps.setString(1, Login.getUserId());
                    //do not allow date field to be empty
                    if (startDate.getText().isEmpty() && endDate.getText().isEmpty()) {
                        //if date text fields are empty do not display anything, clear the jtable
                        JTable table = interlineIndividual;
                        DefaultTableModel dm = (DefaultTableModel) table.getModel();
                        dm.setRowCount(0);
                        showReport();
                        return;
                    }

//format date for sql for start txt field
                    String inputDateString = startDate.getText();
                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate inputDate = LocalDate.parse(inputDateString, inputFormatter);
//format date for sql. from dd/mm/yyyy to yyyy-mm--dd
                    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String outputDateString = outputFormatter.format(inputDate);
                    System.out.println(outputDateString);
                    ps.setString(2, outputDateString);

                    //format date for sql for end date text field
                    String inputDateString2 = endDate.getText();
                    DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate inputDate2 = LocalDate.parse(inputDateString2, inputFormatter2);
                    //format date for sql from dd/mm/yyyy to yyyy-mm--dd
                    DateTimeFormatter outputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String outputDateString2 = outputFormatter2.format(inputDate2);
                    System.out.println(outputDateString2);
                    ps.setString(3, outputDateString2);

                    //refresh the db //repaint it
                    JTable table = interlineIndividual;
                    DefaultTableModel dm = (DefaultTableModel) table.getModel();
                    //refresh the jtable to avoid duplication of values
                    //set the row count to 0
                    dm.setRowCount(0);
                    ResultSet resultSet = ps.executeQuery();
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    //add the newly generatred dataset
                    DefaultTableModel model = (DefaultTableModel) interlineIndividual.getModel();
                    interlineIndividual.setRowHeight(25);

                    //getting column names
                    int col = resultSetMetaData.getColumnCount();
                    String[] colName = new String[col];
                    for (int i = 1; i <= col; i++) {
                        colName[i - 1] = resultSetMetaData.getColumnName(i);
                    }

                    model.setColumnIdentifiers(colName);

                    //getting data
                    String StaffID, FirstName, blank_id, customer, tax_total, grand_total, commission_amount, Price;
                    while (resultSet.next()) {
                        //retrieve the values and put them as placeholders to be used
                        StaffID = resultSet.getString(1);
                        FirstName = resultSet.getString(2);
                        blank_id = resultSet.getString(3);
                        customer = resultSet.getString(4);
                        tax_total = resultSet.getString(5);
                        grand_total = resultSet.getString(6);
                        commission_amount = resultSet.getString(7);
                        Price = resultSet.getString(8);

                        String[] row = {StaffID, FirstName, blank_id, customer, tax_total, grand_total, commission_amount, Price};
                        //add placeholder to jtable row
                        model.addRow(row);
                    }

                    double total_price = 0.0;
                    double totalCommAmount = 0.0;
                    double total_grand_total = 0.0;
                    int columnIndex = 7; // Index of the seventh column (column indices start at 0)
                    int columnIndex2 = 6;
                    int columnIndex3 = 5;
                    int rowCount = model.getRowCount(); // Get the number of rows in the table

                    //index through the table storing required data for report.
                    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                        Object value = model.getValueAt(rowIndex, columnIndex);// Get the value at the current row and column
                        Object value2 = model.getValueAt(rowIndex, columnIndex2);
                        Object value3 = model.getValueAt(rowIndex, columnIndex3);

                        total_price += Double.parseDouble(value.toString());
                        totalCommAmount += Double.parseDouble(value.toString()) * (Double.parseDouble(value2.toString()) / 100);
                        total_grand_total += Double.parseDouble(value3.toString());



                        // Do something with the value (e.g. print it to the console)
                        System.out.println(total_price);
                    }
                    //round to two decimal places
                    double roundedNum = Math.round(totalCommAmount * 100.0) / 100.0;


                    //set text field with correct data for summary report
                    //shows total commission, total spent by customers, total amount sent to airvia
                    netAmount.setText(String.valueOf(total_grand_total - roundedNum));
                    totalPaid.setText(String.valueOf(total_grand_total));
                    subTotal.setText(String.valueOf(total_price));
                    totalComAmount.setText(String.valueOf(roundedNum));

                    //handle exceptions
                } catch (SQLException | ClassNotFoundException throwables) {
                    dialog(throwables.toString());
                    throwables.printStackTrace();
                }

            }
        });
    }

    public void dialog(String s) {
        JOptionPane.showMessageDialog(this, s);
    }

    public void showReport() {
        try (
                Connection con = DBConnection.getConnection()
        ) {

            PreparedStatement ps = con.prepareStatement("SELECT ticket_sales.StaffID, Staff.FirstName, ticket_sales.blank_id, ticket_sales.customer, " +
                    "ticket_sales.tax_total, ticket_sales.grand_total, ticket_sales.commission_amount, ticket_sales.grand_total - tax_total as Price , ticket_sales.ticket_date " +
                    "FROM ticket_sales LEFT JOIN Staff ON ticket_sales.StaffID = Staff.StaffID WHERE Staff.Role = 'Travel Advisor' AND ticket_sales.report_type = 'Interline' AND ticket_sales.StaffID = ? ");
            ps.setString(1, Login.getUserId());
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) interlineIndividual.getModel();
            interlineIndividual.setRowHeight(25);

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String StaffID, FirstName, blank_id, customer, tax_total, grand_total, commission_amount, Price;
            while (resultSet.next()) {
                StaffID = resultSet.getString(1);
                FirstName = resultSet.getString(2);
                blank_id = resultSet.getString(3);
                customer = resultSet.getString(4);
                tax_total = resultSet.getString(5);
                grand_total = resultSet.getString(6);
                commission_amount = resultSet.getString(7);
                Price = resultSet.getString(8);

                String[] row = {StaffID, FirstName, blank_id, customer, tax_total, grand_total, commission_amount, Price};
                model.addRow(row);
            }

            double total_price = 0.0;
            double totalCommAmount = 0.0;
            double total_grand_total = 0.0;
            int columnIndex = 7; // Index of the seventh column (column indices start at 0)
            int columnIndex2 = 6;
            int columnIndex3 = 5;
            int rowCount = model.getRowCount(); // Get the number of rows in the table

            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                Object value = model.getValueAt(rowIndex, columnIndex);// Get the value at the current row and column
                Object value2 = model.getValueAt(rowIndex, columnIndex2);
                Object value3 = model.getValueAt(rowIndex, columnIndex3);

                total_price += Double.parseDouble(value.toString());
                totalCommAmount += Double.parseDouble(value.toString()) * (Double.parseDouble(value2.toString()) / 100);
                total_grand_total += Double.parseDouble(value3.toString());


                // Do something with the value (e.g. print it to the console)
                System.out.println(total_price);
            }
            double roundedNum = Math.round(totalCommAmount * 100.0) / 100.0;

            netAmount.setText(String.valueOf(total_grand_total - roundedNum));
            totalPaid.setText(String.valueOf(total_grand_total));
            subTotal.setText(String.valueOf(total_price));
            totalComAmount.setText(String.valueOf(roundedNum));


            //handle and catch exception
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    //getters and setters
    public JPanel getPanel1() {
        return panel1;
    }
}

