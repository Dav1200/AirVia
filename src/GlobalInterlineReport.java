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


public class GlobalInterlineReport extends JFrame {

    //fields
    private JPanel panel1;
    private JButton printReportButton;
    private JTable interlineGlobal;
    private JTextField netAmount;
    private JTextField totalComAmount;
    private JTextField totalPaid;
    private JTextField subTotal;
    private JTextField startDate;
    private JTextField endDate;
    private JButton search;
    private JButton generateReportButton;


    //constructor
    public GlobalInterlineReport() {

        //showReport();
        printReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Printing ");
            }
        });


        //formating text field
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
//formate for end date text field
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


        //format end date field
        endDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);

                if (endDate.getText().toString().length() > 10){
                    e.consume();
                }

                //dont allow backspace to registered as a character
                if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {

                    if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {

                        if (endDate.getText().toString().length() == 2) {
                            //add / where required
                            endDate.setText(endDate.getText() + "/");
                        } //add / where required
                        if (endDate.getText().toString().length() == 5) {
                            endDate.setText(endDate.getText() + "/");
                        }
                    }
                }
            }

        });

        //format start date text field
        startDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    //add / where required
                    if (startDate.getText().toString().length() == 2) {

                        startDate.setText(startDate.getText() + "/");
                    } //add / where required
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
                        //connect to db
                        Connection con = DBConnection.getConnection()
                ) {


                    //sql query to get data from various tables to generate global interline report
                    PreparedStatement ps = con.prepareStatement("SELECT ticket_sales.StaffID, Staff.FirstName, " +
                            "SUM(ticket_sales.tax_total) as total_tax, SUM(ticket_sales.grand_total * ticket_sales.exchange_rate) as Total_New, " +
                            "SUM(ticket_sales.grand_total - tax_total) as total_price, SUM(ticket_sales.commission_amount/100 * (ticket_sales.grand_total - tax_total)) as total_commission " +
                            "FROM ticket_sales LEFT JOIN Staff ON ticket_sales.StaffID = Staff.StaffID WHERE Staff.Role = 'Travel Advisor' AND ticket_sales.report_type = 'Interline' " +
                            "AND STR_TO_DATE(ticket_sales.ticket_date, '%d/%m/%Y') BETWEEN ? AND ?  " +
                            "GROUP BY ticket_sales.StaffID, Staff.FirstName");

                    //do not allow date field to be empty
                    //show blank table if no date is inputted
                    if(startDate.getText().isEmpty() && endDate.getText().isEmpty()){
                        JTable table = interlineGlobal;
                        DefaultTableModel dm = (DefaultTableModel)table.getModel();
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
                    ps.setString(1,outputDateString);


                    //format date for sql for end date text field
                    String inputDateString2 = endDate.getText();
                    DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate inputDate2 = LocalDate.parse(inputDateString2, inputFormatter2);
//format date for sql. from dd/mm/yyyy to yyyy-mm--dd
                    DateTimeFormatter outputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String outputDateString2 = outputFormatter2.format(inputDate2);
                    System.out.println(outputDateString2);
                    //set the correct value for sql query statement
                    ps.setString(2, outputDateString2);

                    //refresh the db //repaint it
                    JTable table = interlineGlobal;
                    DefaultTableModel dm = (DefaultTableModel)table.getModel();
                    dm.setRowCount(0);
                    ResultSet resultSet = ps.executeQuery();
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    DefaultTableModel model = (DefaultTableModel) interlineGlobal.getModel();
                    interlineGlobal.setRowHeight(25);

                    //getting column names
                    int col = resultSetMetaData.getColumnCount();
                    String[] colName = new String[col];
                    for (int i = 1; i <= col; i++) {
                        colName[i - 1] = resultSetMetaData.getColumnName(i);
                    }

                    model.setColumnIdentifiers(colName);

                    //getting data
                    String StaffID, FirstName, total_tax, Total_New,  totalPrice,total_commission;
                    while (resultSet.next()) {
                        StaffID = resultSet.getString(1);
                        FirstName = resultSet.getString(2);
                        total_tax = resultSet.getString(3);
                        Total_New = resultSet.getString(4);
                        totalPrice = resultSet.getString(5);
                        total_commission = resultSet.getString(6);
                        System.out.println(total_commission);


                        String[] row = {StaffID, FirstName, total_tax, Total_New, totalPrice, total_commission};
                        model.addRow(row);
                    }
//initial values to be used in report
                    double total_price = 0.0;
                    double totalCommAmount = 0.0;
                    double total_grand_total = 0.0;
                    double price_min_tax = 0.0;



                    double commission = 0.0;
                    double tax = 0.0;
                    int columnIndex = 6-1; //total comm
                    int columnIndex2 = 5-1;// totalprice
                    int columnIndex3 = 4-1; // total_ new
                    int columnIndex4 = 3-1; // total tax
                    int rowCount = model.getRowCount(); // Get the number of rows in the table

                    for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                        Object value = model.getValueAt(rowIndex, columnIndex);// Get the value at the current row and column
                        Object value2 = model.getValueAt(rowIndex, columnIndex2);
                        Object value3 = model.getValueAt(rowIndex, columnIndex3);
                        Object value4 = model.getValueAt(rowIndex, columnIndex4);

                        totalCommAmount += Double.parseDouble(value.toString());
                        total_grand_total += Double.parseDouble(value2.toString());
                        tax += Double.parseDouble(value4.toString());

                    }
                    //set text field with correct data for summary report
                    //shows total commission, total spent by customers, total amount sent to airvia

                    double roundedNum = Math.round(totalCommAmount * 100.0) / 100.0;
                    //set to two decimal places

                    netAmount.setText(String.valueOf(total_grand_total - roundedNum));
                    totalPaid.setText(String.valueOf(total_grand_total));
                    subTotal.setText(String.valueOf(total_grand_total-tax));
                    totalComAmount.setText(String.valueOf(totalCommAmount));

                    //handle exceptions
                } catch (SQLException | ClassNotFoundException throwables) {
                    dialog(throwables.toString());
                    throwables.printStackTrace();
                }

            }
        });
    }

    public void dialog(String s){
        JOptionPane.showMessageDialog(this,s);
    }
    public void showReport() {
        try (
                Connection con = DBConnection.getConnection()
        ) {
//sql query to get data from various tables to generate global interline report
            //combines data fromm various database tables and puts it into a organised table
            PreparedStatement ps = con.prepareStatement("SELECT ticket_sales.StaffID, Staff.FirstName, " +
                    "SUM(ticket_sales.tax_total) as total_tax, SUM(ticket_sales.grand_total * ticket_sales.exchange_rate) as Total_New, " +
                    "SUM(ticket_sales.grand_total - tax_total) as total_price, SUM(ticket_sales.commission_amount/100 * (ticket_sales.grand_total - tax_total)) as total_commission " +
                    "FROM ticket_sales LEFT JOIN Staff ON ticket_sales.StaffID = Staff.StaffID WHERE Staff.Role = 'Travel Advisor' AND ticket_sales.report_type = 'Interline' " +
                    "GROUP BY ticket_sales.StaffID, Staff.FirstName");

            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) interlineGlobal.getModel();
            interlineGlobal.setRowHeight(25);

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String StaffID, FirstName, total_tax, Total_New, total_commission, total_price;
            while (resultSet.next()) {
                StaffID = resultSet.getString(1);
                FirstName = resultSet.getString(2);
                total_tax = resultSet.getString(3);
                Total_New = resultSet.getString(4);
                total_price = resultSet.getString(5);
                total_commission = resultSet.getString(6);

                String[] row = {StaffID, FirstName, total_tax, Total_New, total_price, total_commission};
                model.addRow(row);
            }

            double totalPrice = 0.0;
            double totalCommAmount = 0.0;
            double total_grand_total = 0.0;
            int columnIndex = 5; // Index of the seventh column (column indices start at 0)
            int columnIndex2 = 4;
            int columnIndex3 = 3;
            int rowCount = model.getRowCount(); // Get the number of rows in the table

            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                Object value = model.getValueAt(rowIndex, columnIndex);// Get the value at the current row and column
                Object value2 = model.getValueAt(rowIndex, columnIndex2);
                Object value3 = model.getValueAt(rowIndex, columnIndex3);

                totalPrice += Double.parseDouble(value.toString());
                totalCommAmount += Double.parseDouble(value.toString()) * (Double.parseDouble(value2.toString())/100);
                total_grand_total += Double.parseDouble(value3.toString());



                // Do something with the value (e.g. print it to the console)
                System.out.println(totalPrice);
            }
            //round to two decimal places
            double roundedNum = Math.round(totalCommAmount * 100.0) / 100.0;

            //set text field with correct data for summary report
            //shows total commission, total spent by customers, total amount sent to airvia
            netAmount.setText(String.valueOf(total_grand_total - roundedNum));
            totalPaid.setText(String.valueOf(total_grand_total));
            subTotal.setText(String.valueOf(totalPrice));
            totalComAmount.setText(String.valueOf(resultSet.getString(5)));

            //handle exceptions

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }





    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getPanel1() {
        return panel1;
    }
}


