import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Login extends JFrame {

        private JTextField asdasdTextField;
        private JPasswordField passwordField1;
        private JPanel Hello;

        private JButton resetButton;
        private JButton loginButton;
        private AdminMenu ad;


    public Login() {
        this.setContentPane(this.Hello);

        this.setVisible(true);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

// testing Taha
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // if (Objects.equals(asdasdTextField.getText(), "Admin") && Objects.equals(passwordField1.getText(), "Admin")) {

                    AdminMenu ad = new AdminMenu();
                    ad.setContentPane(ad.getAplane());
                    ad.setVisible(true);
                    ad.setSize(800, 600);
                    ad.setLocationRelativeTo(null);
                    ad.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    dispose();

              //  }
            }

        });
    }


    public static void main(String[] args )  {
/*
        try (//Get connection to the database
             Connection con = DBConnection.getConnection();
        ){

            PreparedStatement ps = con.prepareStatement("SELECT * FROM `Staff`");

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                System.out.println(  rs.getString(1));
            }
        }
        catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }


 */


        Login h = new Login();


}
}
