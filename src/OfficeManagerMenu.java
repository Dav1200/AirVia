import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OfficeManagerMenu extends JFrame {

    private JTabbedPane tabbedPane1;
    private JTextField tfSearchBlank;
    private JButton searchButton;
    private JTable dbBlanks;
    private JTextField dsetDiscount;
    private JButton reassignButton;
    private JTextField tfReassignBlank;
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
    private JButton generateReportButton2;
    private JTable salesReport;
    private JButton setRateButton;
    private JButton setRateButton1;
    private JTabbedPane tabbedPane2;
    private JButton generateReportButton1;
    private JTable table1;
    private JButton generateReportButton3;
    private JTable table2;
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

    public OfficeManagerMenu() {
        showTicketTurnoverReport();
        showCombobox();
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
            String ID, ticket_type, blank_id, payment_type, report_type, departure, destination, commission,customer, discount,quantity,ticketprice,tax,total,exchange_rate,date,staffid;
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




                String[] row = {ID, ticket_type, blank_id, payment_type, report_type, departure, destination, commission,customer, discount,quantity,ticketprice,tax,total,exchange_rate,date,staffid};
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
