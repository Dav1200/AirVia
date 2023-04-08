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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class IndividualInterlineReport extends JFrame {
    private JPanel panel1;
    private JButton printReportButton;
    private JTable InterlineIndividual;
    private JScrollPane Pane1;
    private JButton generateReportButton;
    private JTextField netAmount;
    private JTextField totalComAmount;
    private JTextField totalPaid;
    private JTextField subTotal;
    private JTextField startDate;
    private JTextField endDate;
    private JButton search;


    public IndividualInterlineReport() {

        showReport();
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

                    PreparedStatement ps = con.prepareStatement("SELECT ticket_sales.StaffID, Staff.FirstName, ticket_sales.blank_id, ticket_sales.customer, " +
                            "ticket_sales.tax_total, ticket_sales.grand_total, ticket_sales.commission_amount, ticket_sales.grand_total - tax_total as Price , ticket_sales.ticket_date " +
                            "FROM ticket_sales LEFT JOIN Staff ON ticket_sales.StaffID = Staff.StaffID WHERE Staff.Role = 'Travel Advisor' AND ticket_sales.report_type = 'Interline' AND ticket_sales.StaffID = ? " +
                            "AND STR_TO_DATE(ticket_sales.ticket_date, '%d/%m/%Y') BETWEEN ? AND ?");

ps.setString(1,Login.getUserId());
if(startDate.getText().isEmpty() && endDate.getText().isEmpty()){
    JTable table = InterlineIndividual;
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
                    ps.setString(2,outputDateString);



                    String inputDateString2 = endDate.getText();
                    DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate inputDate2 = LocalDate.parse(inputDateString2, inputFormatter2);

                    DateTimeFormatter outputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String outputDateString2 = outputFormatter2.format(inputDate2);
                    System.out.println(outputDateString2);
                    ps.setString(3, outputDateString2);

                    //refresh the db //repaint it
                    JTable table = InterlineIndividual;
                    DefaultTableModel dm = (DefaultTableModel)table.getModel();
                    dm.setRowCount(0);
                    ResultSet resultSet = ps.executeQuery();
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    DefaultTableModel model = (DefaultTableModel) InterlineIndividual.getModel();
                    InterlineIndividual.setRowHeight(25);

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
                        totalCommAmount += Double.parseDouble(value.toString()) * (Double.parseDouble(value2.toString())/100);
                        total_grand_total += Double.parseDouble(value3.toString());



                        // Do something with the value (e.g. print it to the console)
                        System.out.println(total_price);
                    }
                    double roundedNum = Math.round(totalCommAmount * 100.0) / 100.0;

                    netAmount.setText(String.valueOf(total_grand_total - roundedNum));
                    totalPaid.setText(String.valueOf(total_grand_total));
                    subTotal.setText(String.valueOf(total_price));
                    totalComAmount.setText(String.valueOf(roundedNum));

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

            PreparedStatement ps = con.prepareStatement("SELECT ticket_sales.StaffID, Staff.FirstName, ticket_sales.blank_id, ticket_sales.customer, " +
                    "ticket_sales.tax_total, ticket_sales.grand_total, ticket_sales.commission_amount, ticket_sales.grand_total - tax_total as Price , ticket_sales.ticket_date " +
                    "FROM ticket_sales LEFT JOIN Staff ON ticket_sales.StaffID = Staff.StaffID WHERE Staff.Role = 'Travel Advisor' AND ticket_sales.StaffID = ? ");
                    ps.setString(1,Login.getUserId());
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) InterlineIndividual.getModel();
            InterlineIndividual.setRowHeight(25);

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
                totalCommAmount += Double.parseDouble(value.toString()) * (Double.parseDouble(value2.toString())/100);
                total_grand_total += Double.parseDouble(value3.toString());



                // Do something with the value (e.g. print it to the console)
                System.out.println(total_price);
            }
            double roundedNum = Math.round(totalCommAmount * 100.0) / 100.0;

            netAmount.setText(String.valueOf(total_grand_total - roundedNum));
            totalPaid.setText(String.valueOf(total_grand_total));
            subTotal.setText(String.valueOf(total_price));
            totalComAmount.setText(String.valueOf(roundedNum));



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

