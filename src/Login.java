import javax.swing. * ;
import java.awt. * ;
import java.awt.event. * ;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

public class Login extends JFrame {

    //fields
    private static String codestr;
    private final ImageIcon AirVia;
    private final int code;
    private final FileWriter myWriter;
    private JTextField userNameText;
    private JPasswordField passwordField1;
    private JPanel Hello;
    private JLabel error;
    private JLabel ImgLabel;
    private JButton resetButton;
    private JButton loginButton;
    private JLabel uNameJLabel;
    private JLabel passJLabel;
    private AdminMenu ad;
    private String role;
    private boolean check;

    //getters and setters
    public static String getUserId() {
        return userId;
    }

    private static String userId;

    //constructors
    public Login() {

        //designing

        //
        //default constructor values to send mail
        GEmailSender gEmailSender = new GEmailSender();
        String to = "ttechttonic@gmail.com";
        String from = "davsuper4@gmaill.com";
        String subject = "2FA Code";

        //set logo for login screen
        AirVia = new ImageIcon("Images\\Airvia_image2.PNG");
        ImgLabel.setIcon(AirVia);
        ImgLabel.setSize(8, 6);


        //write to log files
        //write to text file for every login attempt
        try {
            myWriter = new FileWriter("login_records.txt", true);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }



        //generate a random 2fa password
        Random rnd = new Random();
        code = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
        codestr = String.format("%06d", code);
        System.out.println(codestr);
        check = false;
        role = "";



        //default start up for login frame/forms
        error.setVisible(false);
        this.setContentPane(this.Hello);
        this.setVisible(true);
        this.setSize(800, 600);
        //this.add(ImgLabel);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);



        //when login button is pressed perform the follwing actions
        //check for correct user
        //validate the user
        //open 2fa form
        //check the encrypted password
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //get the values from textfields
                //username
                //password
                String password = passwordField1.getText();
                String userName = userNameText.getText();
                String encrpPass = null;
                userId = userName;


                //db connectivity
                try (Connection con = DBConnection.getConnection()) {
                    //SQL QUERY WHICH WILL BE USED GET ROLE FROM LOGIN

                    try {
                        encrpPass = encryptString(password);
                    } catch(NoSuchAlgorithmException ex) {
                        System.out.println(ex);
                    }

                    //Encryption
                    PreparedStatement ps = con.prepareStatement("SELECT Role FROM `Staff` WHERE Password = ? and StaffID = ?");
                    //assigns the userName and Password for the Password and userName field in SQL

                    ps.setString(1, encrpPass);

                    ps.setString(2, userName);
                    //Gets the result from QUERY
                    ResultSet rs = ps.executeQuery();
                    System.out.println(encrpPass);


                    //check if the encrypted password matches the encrypted password in db
                    if (rs.next() && encryptString(password).equals(encrpPass)) {

                        //open the 2fa panel
                        Authentication auth = new Authentication();
                        auth.setName("2FA");
                        role = (rs.getString("role"));
                        //set default values of screensize and etc
                        auth.setContentPane(auth.getAuthPanel());
                        auth.setVisible(true);
                        auth.setSize(300, 210);
                        auth.setLocationRelativeTo(null);
                        auth.setDefaultCloseOperation(DISPOSE_ON_CLOSE);


                        //check which option the user selected to receive their 2fa code
                        //email
                        auth.getEmailButton().addActionListener(new ActionListener() {
                            @Override
                        public void actionPerformed(ActionEvent e) {
                            //to send email line below
                            boolean b = gEmailSender.sendEmail(to, from, subject, codestr);
                            auth.getCodeSentTxt().setVisible(true);
                        }
                        });


                        //phone sms
                        auth.getsMSButton().addActionListener(new ActionListener() {@Override
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


                        //when 2fa submit button is pressed
                        auth.getSubmitButton().addActionListener(new ActionListener() {@Override
                         public void actionPerformed(ActionEvent e) {
                             if (auth.getCodeTextField().getText().equals(codestr)) {
                                 //check if 2fa code mathces
                                 System.out.println(auth.getCodeTextField().getText());
                                 check = true;
                                 dispose();
                                 //close forms
                                 auth.dispose();

                             } else {
                                 //If 2fa code is wrong somethign happens?
                                 //CODE HERE

                             }
                             //find the appropriate role for login credentials
                             role = role.toLowerCase();
                            System.out.println(role);


                            //proceed to login as 2fa code is correct
                             if (check) {
                                 //switch to the appropriate role
                                 //switch and open the correct panel based on the role received from db
                                 switch (role.toLowerCase()) {

                                     //open admin form
                                     case "admin":
                                         try {
                                             //write to logs file that admin logged in
                                             myWriter.write("User " + userName + " successful " + new Date() + "\n");
                                             myWriter.flush();
                                             myWriter.close();
                                         } catch(IOException ex) {
                                             throw new RuntimeException(ex);
                                         }
                                         //create admin panel
                                         AdminMenu admin = new AdminMenu();
                                         admin.setContentPane(admin.getAplane());
                                         admin.setVisible(true);
                                         admin.setSize(800, 600);
                                         admin.setLocationRelativeTo(null);
                                         admin.setDefaultCloseOperation(EXIT_ON_CLOSE);
                                         dispose();
                                         break;
                                         //create advisor form
                                     case "travel advisor":
                                         try {
                                             //write to log file that adviosr logged in succesfully
                                             myWriter.write("User " + userName + " successful " + new Date() + "\n");
                                             myWriter.flush();
                                             myWriter.close();
                                         } catch(IOException ex) {
                                             throw new RuntimeException(ex);
                                         }

                                         //create advisor panel
                                         AdvisorMenu advisor = new AdvisorMenu();
                                         advisor.setContentPane(advisor.getAdPlane());
                                         advisor.setVisible(true);
                                         advisor.setSize(800, 600);
                                         advisor.setLocationRelativeTo(null);
                                         advisor.setDefaultCloseOperation(EXIT_ON_CLOSE);
                                         dispose();
                                         break;

                                         //create office manager form
                                     case "office manager":
                                         try {
                                             //write to logs file for successful login
                                             myWriter.write("User " + userName + " successful " + new Date() + "\n");
                                             myWriter.flush();
                                             myWriter.close();
                                         } catch(IOException ex) {
                                             throw new RuntimeException(ex);
                                         }
                                         //create panel
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


                        //if role doesnt match or id is incorrect write to log file saying failed
                    } else {

                        myWriter.write("User " + userName + " Failed " + new Date() + "\n");
                        myWriter.flush();


                        //show error message for incorrect credentials
                        if (!error.isVisible()) {

                            error.setVisible(true);

                        }
                        //Add error code for when you username or password is wrong
                        //add in GUI manner, so it should be displayed on the form

                    }

                    //handle exceptions
                } catch(SQLException | NoSuchAlgorithmException | ClassNotFoundException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            // if (Objects.equals(asdasdTextField.getText(), "Admin") && Objects.equals(passwordField1.getText(), "Admin")) {

            /*


             */
            //  }

        });


        //when user presses enter it should automatically press the login button
        //styling and layout

        passwordField1.addKeyListener(new KeyAdapter() {@Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                loginButton.doClick();
            }
        }
        });
        userNameText.addKeyListener(new KeyAdapter() {@Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                loginButton.doClick();
            }
        }
        });
        //styling and layout when hovering over it
        //set the font bigger and make the button larger
        userNameText.addFocusListener(new FocusAdapter() {@Override
        public void focusGained(FocusEvent e) {
            super.focusGained(e);
            //custom font and size
            uNameJLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
            uNameJLabel.setForeground(new Color(40, 40, 40));

        }
            //styling and layout when hovering over it
            //reset style when not hovered above it
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                //custom font and size
                uNameJLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
            }
        });
        //styling and layout when hovering over it
        //set the font bigger and make the button larger
        passwordField1.addFocusListener(new FocusAdapter() {@Override
        public void focusGained(FocusEvent e) {
            super.focusGained(e);
            //custom font and size
            passJLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
            passJLabel.setForeground(new Color(40, 40, 40));

        }
            //styling and layout when hovering over it
            //reset style when not hovered above it
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                //custom font and size
                passJLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));

            }
        }); //styling and layout when hovering over it
        //set the font bigger and make the button larger
        loginButton.addMouseListener(new MouseAdapter() {@Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            //custom font and size
            loginButton.setFont(new Font("Helvetica", Font.BOLD, 27));
        }
            //styling and layout when hovering over it
            //set the font bigger and make the button larger
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                //custom font and size
                loginButton.setFont((new Font("Helvetica ", Font.BOLD, 18)));

            }
        }); //styling and layout
        //set the font bigger and make the button larger
        resetButton.addMouseListener(new MouseAdapter() {@Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            //custom font and size
            resetButton.setFont(new Font("Helvetica", Font.BOLD, 27));
        }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                //custom font and size
                resetButton.setFont((new Font("Helvetica ", Font.BOLD, 18)));
            }
        });

        //will prompt the user locating their sqldump file on their hardware
        resetButton.addActionListener(new ActionListener() {@Override
        public void actionPerformed(ActionEvent e) {
            String[] commands = {
                    "cmd",
                    "/c",
                    "where",
                    "mysqldump.exe"
            };
            Process process = null;
            try {
                process = Runtime.getRuntime().exec(commands);

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = reader.readLine();

                if (line != null) {
                    String mysqldumpPath = line.trim();
                    String versionCommand = mysqldumpPath + " --version";
                    process = Runtime.getRuntime().exec(versionCommand);
                    reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    line = reader.readLine();

                    if (line != null) {
                        showDialog(mysqldumpPath);
                        //System.out.println(mysqldumpPath);
                    }
                } else {
                    System.err.println("Could not find mysqldump");
                }
            } catch(IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        });
    }

    //show custom prompt for user
    public void showDialog(String s){
        JOptionPane.showMessageDialog(this,s);
    }


    //get 2fa code
    public static String getCodestr() {
        return codestr;
    }

    //password encryption
    public static String encryptString(String input) throws NoSuchAlgorithmException {

        try {
            //messageDigest works with MD2, MD5, SHA-1, SHA-224, SHA-256, SHA-384, SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger bigint = new BigInteger(1, messageDigest);
            return bigint.toString(16);
        } catch(NoSuchAlgorithmException ex) {
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

        //being the login panel/form
        Login h = new Login();
        /*



         */
    }


    public void onExit() {
        this.dispose();
    }



}