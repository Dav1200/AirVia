import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame{
    public AdminMenu() {

        workButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login a = new Login();
                dispose();
            }
        });
        userlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hi");
            }
        });
    }

    public JPanel getAplane() {
        return aplane;
    }

    public void setAplane(JPanel aplane) {
        this.aplane = aplane;
    }

    private JPanel aplane;
    private JButton workButton;
    private JTabbedPane TabMenu;
    private JButton userlistButton;
    private JTable DB;



}
