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
    private JTextField textField1;
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

    public OfficeManagerMenu() {
        showTicketTurnoverReport();


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





    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
