import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Login extends JFrame {

    private JTextField userNameText;
    private JPasswordField passwordField1;
    private JPanel Hello;
    private JLabel error;
    private ImageIcon AirVia;
    private JLabel ImgLabel;

    private JButton resetButton;
    private JButton loginButton;
    private AdminMenu ad;
    private String role;
    private int code;
    private String codestr;



private boolean check;

    public Login() {

        GEmailSender gEmailSender = new GEmailSender();
        String to = "ttechttonic@gmail.com";
        String from = "davsuper4@gmaill.com";
        String subject = "2FA Code";
        AirVia = new ImageIcon("Airvia_image2.PNG");
        ImgLabel.setIcon(AirVia);
        ImgLabel.setSize(8, 6);



        Random rnd = new Random();
         code = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
         codestr = String.format("%06d", code);
        System.out.println(codestr);
        check = false;
        role = "";
        error.setVisible(false);

        this.setContentPane(this.Hello);

        this.setVisible(true);
        this.setSize(800, 600);
        //this.add(ImgLabel);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


// testing Taha
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = passwordField1.getText();
                String userName = userNameText.getText();
                String encrpPass = null;

                try (Connection con = DBConnection.getConnection();) {
                    //SQL QUERY WHICH WILL BE USED GET ROLE FROM LOGIN

                    try {
                        encrpPass = encryptString(password);
                    } catch (NoSuchAlgorithmException ex) {
                        System.out.println(ex);
                    }


//Encryption
                    PreparedStatement ps = con.prepareStatement("SELECT Role FROM `Staff` WHERE Password = ? and StaffID = ?");
                    //assigns the userName and Password for the Password and userName field in SQL

                    ps.setString(1, encrpPass);

                    ps.setString(2, userName);
                    //Gets the result from QUERY
                    ResultSet rs = ps.executeQuery();


                    if (rs.next() && encryptString(password).equals(encrpPass)) {
                        Authentication auth = new Authentication();
                        auth.setName("2FA");
                        role = (rs.getString("role"));
                        auth.setContentPane(auth.getAuthPanel());
                        auth.setVisible(true);
                        auth.setSize(300, 210);
                        auth.setLocationRelativeTo(null);
                        auth.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

                        auth.getEmailButton().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //to send email line below
                                boolean b = gEmailSender.sendEmail(to, from, subject, codestr);
                                auth.getCodeSentTxt().setVisible(true);
                            }
                        });

                        auth.getsMSButton().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                /* to send mobile text
                        Twilio.init("AC2b2098fa2669b835feb2b72bf50c01b0", "2628c89c04b3fc37188b2c927dd2c95a");
                        Message message = Message.creator(
                                        new com.twilio.type.PhoneNumber("+447487555892"),
                                        new com.twilio.type.PhoneNumber("+14752629030"),
                                        codestr)
                                .create();

                        System.out.println(message.getSid());

auth.getCodeSentTxt().setVisible(true);

 */
                            }
                        });









                        auth.getSubmitButton().addActionListener(new ActionListener() {
                             @Override
                           public void actionPerformed(ActionEvent e) {
                               if (auth.getCodeTextField().getText().equals(codestr)) {
                                   System.out.println(auth.getCodeTextField().getText());
                                   check = true;
                                   dispose();
                                   auth.dispose();

                               } else {
                                   //If 2fa code is wrong somethign happens?
                                   //CODE HERE

                               }

                               if (check) {
                                   switch (role) {

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
                                           manager.setContentPane(manager.getoPlane());
                                           manager.setVisible(true);
                                           manager.setSize(800, 600);
                                           manager.setLocationRelativeTo(null);
                                           manager.setDefaultCloseOperation(EXIT_ON_CLOSE);
                                           dispose();
                                           break;
                                   }


                               }

                           }
                       }

);

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

    public static String encryptString(String input) throws NoSuchAlgorithmException {

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

    public void onExit() {
        this.dispose();
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
