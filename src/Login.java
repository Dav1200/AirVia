import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends JFrame {

    private JTextField asdasdTextField;
    private JPasswordField passwordField1;
    private JPanel Hello;

    private JButton resetButton;
    private JButton loginButton;
    private Admin ad;


    public Login() {
        this.setContentPane(this.Hello);

        this.setVisible(true
        );
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(asdasdTextField.getText(), "Admin") && Objects.equals(passwordField1.getText(), "Admin")) {
                    Admin ad = new Admin();
                    ad.setContentPane(ad.getAplane());
                    ad.setVisible(true);
                    ad.setSize(400, 400);
                    ad.setLocationRelativeTo(null);
                    ad.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    dispose();

                }
            }

        });
    }


    public static void main(String[] args) throws SQLException {
        Login h = new Login();


}
}
