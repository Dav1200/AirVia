import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdvisorMenu extends JFrame {
    public AdvisorMenu() {

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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
