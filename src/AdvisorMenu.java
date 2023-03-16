import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AdvisorMenu extends JFrame {
    public AdvisorMenu() {

        showCustomer();

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login a = new Login();
                dispose();
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
    private JTextField addressTextField;
    private JTextField emailTextField;
    private JButton registerButton;
    private JButton resetButton;
    private JLabel searchCustomerLabel;
    private JTextField searchCustomerTextField;
    private JButton searchCustomerButton;
    private JTextField ticketTypeTextField;
    private JTextField customerTypeTextField;
    private JTextField commissionAmountTextField;
    private JTextField reportTypeTextField;
    private JTextField recordPaymentTextField;
    private JTextField recordDiscountAmountTextField;
    private JButton registerTicketButton;
    private JButton createIndividualReportButton;
    private JLabel registerTicketLabel;
    private JTable CustomerTable;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void showCustomer() {
        try (
                Connection con = DBConnection.getConnection();
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
            String ID, Firstname, Lastname, Email, CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID;
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

                String[] row = {ID, Firstname, Lastname, Email, CustomerType, DiscountType, DiscountAmount, Address, TotalTickets, StaffID};
                model.addRow(row);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //search customer

    }
}
