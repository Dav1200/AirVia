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


public class GlobalDomesticReport extends JFrame {
    private JPanel panel1;
    private JButton printReportButton;
    private JTable DomesticGlobal;
    private JScrollPane Pane1;
    private JButton generateReportButton;
    private JTextField netAmount;
    private JTextField totalComAmount;
    private JTextField totalPaid;
    private JTextField subTotal;
    private JTextField startDate;
    private JTextField endDate;
    private JButton search;


    public GlobalDomesticReport() {

        //showReport();
        printReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Printing ");
            }
        });


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


        endDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);

                if (endDate.getText().toString().length() > 10){
                    e.consume();
                }
                if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {

                    if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {

                        if (endDate.getText().toString().length() == 2) {

                            endDate.setText(endDate.getText() + "/");
                        }
                        if (endDate.getText().toString().length() == 5) {
                            endDate.setText(endDate.getText() + "/");
                        }
                    }
                }
            }

        });
        startDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {

                    if (startDate.getText().toString().length() == 2) {

                        startDate.setText(startDate.getText() + "/");
                    }
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

                    PreparedStatement ps = con.prepareStatement("SELECT ticket_sales.StaffID, Staff.FirstName, \n" +
                            "       SUM(ticket_sales.tax_total) as total_tax, \n" +
                            "       SUM(ticket_sales.grand_total * ticket_sales.exchange_rate) as Total_New, \n" +
                            "\t   SUM(ticket_sales.grand_total - tax_total) as total_price,\n" +
                            "       SUM(ticket_sales.commission_amount/100 * (ticket_sales.grand_total - tax_total)) as total_commission, \n" +
                            "       ticket_sales.ticket_date \n" +
                            "FROM ticket_sales \n" +
                            "LEFT JOIN Staff ON ticket_sales.StaffID = Staff.StaffID \n" +
                            "WHERE Staff.Role = 'Travel Advisor' AND ticket_sales.report_type = 'Domestic' AND STR_TO_DATE(ticket_sales.ticket_date, '%d/%m/%Y') BETWEEN ? AND ?\n" +
                            "GROUP BY ticket_sales.StaffID, Staff.FirstName, ticket_sales.ticket_date");


                    if(startDate.getText().isEmpty() && endDate.getText().isEmpty()){
                        JTable table = DomesticGlobal;
                        DefaultTableModel dm = (DefaultTableModel)table.getModel();
                        dm.setRowCount(0);
                        showReport();
                        return;
                    }


                    String inputDateString = startDate.getText();
                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate inputDate = LocalDate.parse(inputDateString, inputFormatter);

                    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String outputDateString = outputFormatter.format(inputDate);
                    System.out.println(outputDateString);
                    ps.setString(1,outputDateString);



                    String inputDateString2 = endDate.getText();
                    DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate inputDate2 = LocalDate.parse(inputDateString2, inputFormatter2);

                    DateTimeFormatter outputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String outputDateString2 = outputFormatter2.format(inputDate2);
                    System.out.println(outputDateString2);
                    ps.setString(2, outputDateString2);

                    //refresh the db //repaint it
                    JTable table = DomesticGlobal;
                    DefaultTableModel dm = (DefaultTableModel)table.getModel();
                    dm.setRowCount(0);
                    ResultSet resultSet = ps.executeQuery();
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    DefaultTableModel model = (DefaultTableModel) DomesticGlobal.getModel();
                    DomesticGlobal.setRowHeight(25);

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


                    double roundedNum = Math.round(totalCommAmount * 100.0) / 100.0;

                    netAmount.setText(String.valueOf(total_grand_total - roundedNum));
                    totalPaid.setText(String.valueOf(total_grand_total));
                    subTotal.setText(String.valueOf(total_grand_total-tax));
                    totalComAmount.setText(String.valueOf(totalCommAmount));

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

            PreparedStatement ps = con.prepareStatement("SELECT ticket_sales.StaffID, Staff.FirstName, \n" +
                    "       SUM(ticket_sales.tax_total) as total_tax, \n" +
                    "       SUM(ticket_sales.grand_total * ticket_sales.exchange_rate) as Total_New, \n" +
                    "\t   SUM(ticket_sales.grand_total - tax_total) as total_price,\n" +
                    "       SUM(ticket_sales.commission_amount/100 * (ticket_sales.grand_total - tax_total)) as total_commission, \n" +
                    "       ticket_sales.ticket_date \n" +
                    "FROM ticket_sales \n" +
                    "LEFT JOIN Staff ON ticket_sales.StaffID = Staff.StaffID \n" +
                    "WHERE Staff.Role = 'Travel Advisor' AND ticket_sales.report_type = 'Domestic' \n" +
                    "GROUP BY ticket_sales.StaffID, Staff.FirstName, ticket_sales.ticket_date");

            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) DomesticGlobal.getModel();
            DomesticGlobal.setRowHeight(25);

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
            double roundedNum = Math.round(totalCommAmount * 100.0) / 100.0;

            netAmount.setText(String.valueOf(total_grand_total - roundedNum));
            totalPaid.setText(String.valueOf(total_grand_total));
            subTotal.setText(String.valueOf(totalPrice));
            totalComAmount.setText(String.valueOf(resultSet.getString(5)));



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



