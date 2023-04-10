import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OfficeManagerMenu extends JFrame {

    private JTabbedPane tabbedPane1;
    private JTextField tfSearchBlank;
    private JButton searchButton;
    private JTable dbBlanks;
    private JTextField dsetDiscount;
    private JButton reassignButton;
    private JButton workButton;
    private JButton generateReportButton;
    private JTable turnoverReport;

    public JPanel getoPlane() {
        return oPlane;
    }

    public void setoPlane(JPanel oPlane) {
        this.oPlane = oPlane;
    }

    private JPanel oPlane;
    private JTextField tfComRate;
    private JButton setRateButton;
    private JButton setRateButton1;
    private JTable IntIndividualTable;
    private JComboBox blankComboBox;
    private JLabel blankJlabel;
    private JTable latePaymentTable;
    private JButton assignBlanksButton;
    private JComboBox dCustomer;
    private JComboBox dCustomerType;
    private JComboBox dDiscountType;
    private JComboBox idcomboBox;
    private JComboBox paymentComboBox;
    private JButton registerButton;
    private JTextField cardtxt;
    private JTabbedPane SalesTab;
    private JButton globalReportButton;
    private JButton individualReport;
    private JButton globalButton;
    private JButton individualButton;
    private JTable table1;
    private JScrollPane allocatedTable;
    private JTable table2;
    private JTable table3;
    private JTextField startDateTf;
    private JTextField endDateTf;
    private JTable table4;
    private JButton GenerateIntInd;

    public OfficeManagerMenu() {
       // showTicketTurnoverReport();
        showCombobox();
        showAllocatedBlanks();
        dDiscountType.setEnabled(false);
        showLatepaymentTable();
        addToLatePayment();
        cardtxt.setEditable(false);

        workButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login a = new Login();
                dispose();
            }
        });
        setRateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCommissionRate();
            }
        });
        assignBlanksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AssignBlanks a = new AssignBlanks();
                a.setContentPane(a.getPane());
                a.setVisible(true);
                a.setSize(400, 400);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        });
        reassignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReassignBlank a = new ReassignBlank();
                a.setContentPane(a.getPane());
                a.setVisible(true);
                a.setSize(400, 400);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        });
        setRateButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        dCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(dCustomer.getSelectedItem().toString());


            }
        });
        dCustomerType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(dCustomerType.getSelectedItem().toString().equals("Regular")){
                    dsetDiscount.setEditable(false);
                    dsetDiscount.setText("0.00");
                    dDiscountType.setSelectedIndex(0);
                    dDiscountType.setEnabled(false);

                }
                else{
                     if(dDiscountType.getSelectedItem().toString().equals("Fixed")){
                        dsetDiscount.setEditable(true);
                        dsetDiscount.setText("0.");

                    }
                     else {
                         dsetDiscount.setEditable(true);
                         dsetDiscount.setText("0");
                     }


                    dDiscountType.setSelectedIndex(1);
                    dDiscountType.setEnabled(true);//dDiscountType.setEditable(true);

                }
            }
        });
        paymentComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(paymentComboBox.getSelectedItem().toString().equals("Card")){
                    cardtxt.setEditable(true);
                }
                else {
                    cardtxt.setEditable(false);
                }
            }
        });

        globalReportButton.addActionListener(new ActionListener() {
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
        individualReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndividualInterlineReport Iil = new IndividualInterlineReport();
                Iil.setContentPane(Iil.getPanel1());
                Iil.setVisible(true);
                Iil.setSize(1000, 600);
                Iil.setLocationRelativeTo(null);
                Iil.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            }
        });

        globalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GlobalDomesticReport Gdr = new GlobalDomesticReport();
                Gdr.setContentPane(Gdr.getPanel1());
                Gdr.setVisible(true);
                Gdr.setSize(1000, 600);
                Gdr.setLocationRelativeTo(null);
                Gdr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            }
        });
        individualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndividualDomesticReport Idr = new IndividualDomesticReport();
                Idr.setContentPane(Idr.getPanel1());
                Idr.setVisible(true);
                Idr.setSize(1000, 600);
                Idr.setLocationRelativeTo(null);
                Idr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection con = DBConnection.getConnection()
                ){
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedDateTime = now.format(formatter);
                    String a = idcomboBox.getSelectedItem().toString().substring(4);
                    String b = paymentComboBox.getSelectedItem().toString();
                    System.out.println(a+" " + b);

                    if(paymentComboBox.getSelectedItem().toString().equals("Card") && cardtxt.getText().isEmpty() ){
                        dialog("Enter card Details");
                        return;
                    }
                    if(paymentComboBox.getSelectedItem().toString().equals("Card") && cardtxt.getText().length() < 16){
                        dialog("Enter card Details");
                    return;
                }

                    PreparedStatement ps = con.prepareStatement("UPDATE ticket_sales SET payment_date = ?, card_detail= ?, payment_type = ? WHERE ID = ?;");
                    ps.setString(1,formattedDateTime);
                    ps.setString(2,cardtxt.getText());
                    ps.setString(3,b);
                    ps.setString(4,a);
                    ps.executeUpdate();
                    dialog("Successful");
                    idcomboBox.removeAllItems();
                    cardtxt.setText("");
                    addToLatePayment();


                    //refresh the db //repaint it
                    JTable table = latePaymentTable;
                    DefaultTableModel dm = (DefaultTableModel)table.getModel();
                    dm.setRowCount(0);
                    showLatepaymentTable();

                    //exception handle
                }  catch (SQLException | ClassNotFoundException ex) {
                    dialog("Unsuccessful");
                    throw new RuntimeException(ex);
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



        cardtxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(cardtxt.getText().toString().length() > 19){
                    //dont allow the user to input after length is more than 16
                    e.consume();
                }
            }
        });
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultTableModel dm1 = (DefaultTableModel)table1.getModel();
                dm1.setRowCount(0);

                DefaultTableModel dm2 = (DefaultTableModel)table2.getModel();
                dm2.setRowCount(0);

                DefaultTableModel dm3 = (DefaultTableModel)table3.getModel();
                dm3.setRowCount(0);


                DefaultTableModel dm4 = (DefaultTableModel)table4.getModel();
                dm4.setRowCount(0);
                showTicketTurnoverReport2();
                showTicketTurnoverReport3();
                showTicketTurnoverReport4();
                showTicketTurnoverReport5();


            }
        });
    }


    public void showCombobox(){
        try (Connection con = DBConnection.getConnection()
        ){
            PreparedStatement ps = con.prepareStatement("SELECT ID,Alias FROM Customer");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                dCustomer.addItem(rs.getString("Alias")+" "+rs.getString("ID"));
            }




        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void showAllocatedBlanks() {
        try (//connect to to db
             Connection con = DBConnection.getConnection()
        ) {
            //select all customers from db
            PreparedStatement ps = con.prepareStatement("SELECT * FROM advisor_blanks");
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) dbBlanks.getModel();
            dbBlanks.setRowHeight(25);

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String ad_blank_id, advisor_id, advisor_name, date, blanks, status;
            while (resultSet.next()) {
                //get the info and add it as a row in the jtable
                ad_blank_id = resultSet.getString(1);
                advisor_id = resultSet.getString(2);
                advisor_name = resultSet.getString(3);
                date = resultSet.getString(4);
                blanks = resultSet.getString(5);
                status = resultSet.getString(6);


                String[] row = {ad_blank_id, advisor_id, advisor_name, date, blanks, status};
                model.addRow(row);


                // row filter
                TableRowSorter tableRowSorter = new TableRowSorter(model);
                dbBlanks.setRowSorter(tableRowSorter);

                //search customer
                //if search button is pressed, search the customer via ID



                searchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String searchText = tfSearchBlank.getText();
                        tableRowSorter.setRowFilter(new RowFilterBlanks(searchText));
                    }
                });

            }



                //handle exceptions

        } catch(SQLException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }



    public void dialog(String s){
        JOptionPane.showMessageDialog(this,s);
    }
    public void showTicketTurnoverReport() {

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM TicketTurnoverReport");
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) turnoverReport.getModel();
            turnoverReport.setRowHeight(25);

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String BlankID, BlankType, RecievedBlanks, BlanksAssigned, TotalBlanks, StartDate, AdvisorID, StaffID;
            while (resultSet.next()) {
                BlankID = resultSet.getString(1);
                BlankType = resultSet.getString(2);
                RecievedBlanks = resultSet.getString(3);
                BlanksAssigned = resultSet.getString(4);
                TotalBlanks = resultSet.getString(5);
                StartDate = resultSet.getString(6);
                AdvisorID = resultSet.getString(7);
                StaffID = resultSet.getString(8);

                String[] row = {BlankID, BlankType, RecievedBlanks, BlanksAssigned, TotalBlanks, StartDate, AdvisorID, StaffID};
                model.addRow(row);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    public void showTicketTurnoverReport2() {

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT \n" +
                    "  CASE \n" +
                    "    WHEN blanks_received LIKE '444%' THEN '444'\n" +
                    "    WHEN blanks_received LIKE '420%' THEN '420'\n" +
                    "    WHEN blanks_received LIKE '440%' THEN '440'\n" +
                    "    WHEN blanks_received LIKE '201%' THEN '201'\n" +
                    "    WHEN blanks_received LIKE '101%' THEN '101'\n" +
                    "  END AS blank_type,\n" +
                    "  CONCAT(MIN(blanks_received), '-', MAX(blanks_received)) AS blank_range\n" +
                    "FROM blank_stock\n" +
                    "WHERE (blanks_received LIKE '444%' \n" +
                    "  OR blanks_received LIKE '420%' \n" +
                    "  OR blanks_received LIKE '440%' \n" +
                    "  OR blanks_received LIKE '201%' \n" +
                    "  OR blanks_received LIKE '101%')\n" +
                    "  AND STR_TO_DATE(date, '%d/%m/%Y') BETWEEN ? AND ?\n" +
                    "GROUP BY blank_type\n" +
                    "ORDER BY blank_type DESC;");


            String inputDateString = startDateTf.getText();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inputDate = LocalDate.parse(inputDateString, inputFormatter);

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String outputDateString = outputFormatter.format(inputDate);
            System.out.println(outputDateString);
            ps.setString(1,outputDateString);

            String inputEndDateString = endDateTf.getText();
            DateTimeFormatter inputEndFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inputEndDate = LocalDate.parse(inputEndDateString, inputEndFormatter);

            DateTimeFormatter outputEndFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String outputEndDateString = outputEndFormatter.format(inputEndDate);
            System.out.println(outputEndDateString);
            ps.setString(2,outputEndDateString);

            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            table1.setRowHeight(25);

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String BlankType, RecievedBlanks;
            while (resultSet.next()) {

                BlankType = resultSet.getString(1);
                RecievedBlanks = resultSet.getString(2);


                String[] row = {BlankType, RecievedBlanks};
                model.addRow(row);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void showTicketTurnoverReport3() {

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT date, blanks_received, status\n" +
                    "FROM blank_stock\n" +
                    "WHERE status = 'unassigned' AND STR_TO_DATE(date, '%d/%m/%Y') BETWEEN ? AND ?\n" +
                    "ORDER BY date ASC;\n");


            String inputDateString = startDateTf.getText();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inputDate = LocalDate.parse(inputDateString, inputFormatter);

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String outputDateString = outputFormatter.format(inputDate);
            System.out.println(outputDateString);
            ps.setString(1,outputDateString);

            String inputEndDateString = endDateTf.getText();
            DateTimeFormatter inputEndFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inputEndDate = LocalDate.parse(inputEndDateString, inputEndFormatter);

            DateTimeFormatter outputEndFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String outputEndDateString = outputEndFormatter.format(inputEndDate);
            System.out.println(outputEndDateString);
            ps.setString(2,outputEndDateString);

            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table3.getModel();
            table3.setRowHeight(25);

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String date, blank_received, status;
            while (resultSet.next()) {

                date = resultSet.getString(1);
                blank_received = resultSet.getString(2);
                status = resultSet.getString(3);


                String[] row = {date, blank_received, status};
                model.addRow(row);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void showTicketTurnoverReport4() {

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT advisor_id, CONCAT(MIN(blanks), '-', MAX(blanks)) AS blanks_range\n" +
                    "FROM advisor_blanks \n" +
                    "WHERE STR_TO_DATE(date, '%d/%m/%Y') BETWEEN ? AND ? \n" +
                    "GROUP BY advisor_id;");

            String inputDateString = startDateTf.getText();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inputDate = LocalDate.parse(inputDateString, inputFormatter);

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String outputDateString = outputFormatter.format(inputDate);
            System.out.println(outputDateString);
            ps.setString(1,outputDateString);

            String inputEndDateString = endDateTf.getText();
            DateTimeFormatter inputEndFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inputEndDate = LocalDate.parse(inputEndDateString, inputEndFormatter);

            DateTimeFormatter outputEndFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String outputEndDateString = outputEndFormatter.format(inputEndDate);
            System.out.println(outputEndDateString);
            ps.setString(2,outputEndDateString);

            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table2.getModel();
            table2.setRowHeight(25);

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String advisor_id, blanks;
            while (resultSet.next()) {

                advisor_id = resultSet.getString(1);
                blanks = resultSet.getString(2);


                String[] row = {advisor_id, blanks};
                model.addRow(row);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void showTicketTurnoverReport5() {

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT advisor_id, COUNT(blanks) AS Final_Amount \n" +
                    "FROM advisor_blanks \n" +
                    "WHERE status = 'Unused'  AND STR_TO_DATE(date, '%d/%m/%Y') BETWEEN ? AND ?\n" +
                    "GROUP BY advisor_id;");

            String inputDateString = startDateTf.getText();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inputDate = LocalDate.parse(inputDateString, inputFormatter);

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String outputDateString = outputFormatter.format(inputDate);
            System.out.println(outputDateString);
            ps.setString(1,outputDateString);

            String inputEndDateString = endDateTf.getText();
            DateTimeFormatter inputEndFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inputEndDate = LocalDate.parse(inputEndDateString, inputEndFormatter);

            DateTimeFormatter outputEndFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String outputEndDateString = outputEndFormatter.format(inputEndDate);
            System.out.println(outputEndDateString);
            ps.setString(2,outputEndDateString);

            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table4.getModel();
            table4.setRowHeight(25);

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String advisor_id, blanks;
            while (resultSet.next()) {

                advisor_id = resultSet.getString(1);
                blanks = resultSet.getString(2);


                String[] row = {advisor_id, blanks};
                model.addRow(row);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void updateCommissionRate() {

        int blankType = 0;

        try (Connection con = DBConnection.getConnection()) {
            //Code for Updating Commission Rate
            //Casting from object reference to integer

            //ensure the txt field is not empty
            if (!tfComRate.getText().isEmpty()) {

                String blankTypes = blankComboBox.getSelectedItem().toString();
                blankType = Integer.parseInt(blankTypes);

                //Sql statement to update data.
                PreparedStatement ps = con.prepareStatement("UPDATE CommissionRate SET CommissionRate = ? WHERE BlankType = ?");
                ps.setString(1, tfComRate.getText());
                ps.setString(2, String.valueOf(blankType));
                //Execute query.
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Commission Rate Updated");
                //Reset the Commissioon rate field to empty
                tfComRate.setText("");

            }
            //If the txt field is empty display an error prompt
            else {

                JOptionPane.showMessageDialog(this, "Error: Please Enter the Commission Rate ");
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void showLatepaymentTable(){

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM ticket_sales WHERE payment_type = ?");
            ps.setString(1,"LatePayment");
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) latePaymentTable.getModel();
            latePaymentTable.setRowHeight(25);

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String ID, ticket_type, blank_id, payment_type, report_type, departure, destination, commission,customer, discount,quantity,ticketprice,tax,total,exchange_rate,date,staffid,cardd,payd;
            while (resultSet.next()) {
                ID = resultSet.getString(1);
                ticket_type = resultSet.getString(2);
                blank_id = resultSet.getString(3);
                payment_type = resultSet.getString(4);
                report_type = resultSet.getString(5);
                departure = resultSet.getString(6);
                destination = resultSet.getString(7);
                commission = resultSet.getString(8);
                customer = resultSet.getString(9);
                discount = resultSet.getString(10);
                quantity = resultSet.getString(11);
                ticketprice = resultSet.getString(12);
                tax = resultSet.getString(13);
                total = resultSet.getString(14);
                exchange_rate = resultSet.getString(15);
                date = resultSet.getString(16);
                staffid = resultSet.getString(17);
                cardd = resultSet.getString(18);
                payd = resultSet.getString(19);





                String[] row = {ID, ticket_type, blank_id, payment_type, report_type, departure, destination, commission,customer, discount,quantity,ticketprice,tax,total,exchange_rate,date,staffid,cardd,payd};
                model.addRow(row);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void addToLatePayment() {
        //adds all id which are in latepayment table to the jcombobox.

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT ID FROM ticket_sales WHERE payment_type = ?");
            ps.setString(1,"LatePayment");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                idcomboBox.addItem("ID: "+rs.getString("ID"));
            }



        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
