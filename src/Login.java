import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Login extends JFrame {

    private JTextField userNameText;
    private JPasswordField passwordField1;
    private JPanel Hello;
    private JLabel error;

    private JButton resetButton;
    private JButton loginButton;
    private AdminMenu ad;


    public Login() {
        error.setVisible(false);
        this.setContentPane(this.Hello);

        this.setVisible(true);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

// testing Taha
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = passwordField1.getText();
                String userName = userNameText.getText();
                String encrpPass = "";
                try (Connection con = DBConnection.getConnection();) {
                    //SQL QUERY WHICH WILL BE USED GET ROLE FROM LOGIN

                    if (!userName.isEmpty()) {
                        PreparedStatement ps1 = con.prepareStatement("SELECT Password FROM `Staff` WHERE StaffID = ?");

                        ps1.setString(1, userName);

                        ResultSet rs1 = ps1.executeQuery();
                        rs1.next();
                        try {
                            encrpPass = encryptString(rs1.getString(1));
                        } catch (NoSuchAlgorithmException ex) {
                            System.out.println(ex);
                        }

                    }

                    PreparedStatement ps = con.prepareStatement("SELECT Role FROM `Staff` WHERE Password = ? and StaffID = ?");
                    //assigns the userName and Password for the Password and userName field in SQL

                    ps.setString(1, password);

                    ps.setString(2, userName);
                    //Gets the result from QUERY
                    ResultSet rs = ps.executeQuery();


                    if (rs.next() && encryptString(password).equals(encrpPass)) {
                        switch (rs.getString("role")) {
                            case "admin":
                                AdminMenu admin = new AdminMenu();
                                admin.setContentPane(admin.getAplane());
                                admin.setVisible(true);
                                admin.setSize(800, 600);
                                admin.setLocationRelativeTo(null);
                                admin.setDefaultCloseOperation(EXIT_ON_CLOSE);
                                dispose();
                                break;
                            case "travel advisor":
                                AdvisorMenu advisor = new AdvisorMenu();
                                advisor.setContentPane(advisor.getAdPlane());
                                advisor.setVisible(true);
                                advisor.setSize(800, 600);
                                advisor.setLocationRelativeTo(null);
                                advisor.setDefaultCloseOperation(EXIT_ON_CLOSE);
                                dispose();
                                break;

                            case "office manager":
                                OfficeManagerMenu manager = new OfficeManagerMenu();

                        }

                    } else {
                        if (!error.isVisible()) {
                            error.setVisible(true);
                        }
                        //Add error code for when you username or password is wrong
                        //add in GUI manner, so it should be displayed on the form


                    }


                } catch (SQLException | NoSuchAlgorithmException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }


            // if (Objects.equals(asdasdTextField.getText(), "Admin") && Objects.equals(passwordField1.getText(), "Admin")) {


            /*


             */
            //  }


        });
    }

    public String encryptString(String input) throws NoSuchAlgorithmException {

        try {
            //messageDigest works with MD2, MD5, SHA-1, SHA-224, SHA-256, SHA-384, SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger bigint = new BigInteger(1, messageDigest);
            return bigint.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
        return null;
    }


    public static void main(String[] args) {
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
        /*



         */
    }
}
