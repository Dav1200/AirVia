import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdminMenu extends JFrame {

    //Fields
    private JPanel aplane;
    private JButton workButton;
    private JTabbedPane logsjTable;
    private JTable DB;
    private JButton restoreDatabaseButton;
    private JButton backupDatabaseButton;
    private JButton updateDatabaseButton;
    private JTextField fnameTf;
    private JTextField lnameTf;
    private JTextField emailTf;
    private JTextField addressTf;
    private JRadioButton adminRadioButton;
    private JRadioButton officeManagerRadioButton;
    private JRadioButton travelAdvisorRadioButton;
    private JTextField passwordTf;
    private JButton registerStaffBtn;
    private JButton clearButton;
    private JLabel errorLabel;
    private JTable logsTable;

    private DefaultTableModel dTable;
    private DBConnection db;

    //Constructor
    public AdminMenu() {
        //manual input




        //createTable();
        showStaff();
        registerMember();
        errorLabel.setVisible(false);

        //logout button functionality
        workButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login a = new Login();
                dispose();
            }
        });

        //Restore DB functionality
        restoreDatabaseButton.addActionListener(new ActionListener() {
            //running a script which works in terminal, to do it.  script is below i just made it into a processbuilder
            // Get-Content backup.sql | mysql -h smcse-stuproj00.city.ac.uk -u in2018g04_a -pbx5jmkL5 in2018g04
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean cancel = false;
                //Jfilechooser
                String filePath = "";
                JFileChooser fileChooser = new JFileChooser();
                File defaultDirectory = new File(System.getProperty("user.dir"));
                fileChooser.setCurrentDirectory(defaultDirectory);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();

                }
                if(result == JFileChooser.CANCEL_OPTION){
                    cancel = true;
                }

                String fileName = filePath;
                String host = "smcse-stuproj00.city.ac.uk";
                String username = "in2018g04_a";
                String password = "bx5jmkL5";
                String database = "in2018g04";

// Construct command for executing mysql
                List<String> command = new ArrayList<>();
                command.add("mysql");
                command.add("-h" + host);
                command.add("-u" + username);
                command.add("-p" + password);
                command.add(database);

// Create a process builder to run the command

                if(!cancel) {
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.redirectInput(ProcessBuilder.Redirect.from(new File(fileName)));

                try {
                    // Start the process
                    Process p = pb.start();

                    // Wait for the process to finish
                    int exitCode = p.waitFor();

                    // Check if the process completed successfully
                    if (exitCode == 0) {
                        System.out.println("Database restored successfully.");
                        JFrame frame = new JFrame("Example Frame");
                        frame.setSize(300, 200);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        showStaff();

                        JOptionPane.showMessageDialog(frame, "Successful");


                    } else {
                        System.out.println("Failed to restore database.");
                        JFrame frame = new JFrame("Example Frame");
                        frame.setSize(300, 200);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        JOptionPane.showMessageDialog(frame, "Failed");
                    }

                } catch (IOException ev) {
                    System.err.println("Error starting the process: " + ev.getMessage());
                } catch (InterruptedException ez) {
                    Thread.currentThread().interrupt();
                    System.err.println("Process interrupted: " + ez.getMessage());
                } catch (Exception ex) {
                    System.err.println("An unexpected error occurred: " + ex.getMessage());
                }
            }}
        });

        //backup DB functionality
        backupDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean cancel = false;
                String filePath = "";
                JFileChooser fileChooser = new JFileChooser();
                File defaultDirectory = new File(System.getProperty("user.dir"));
                fileChooser.setCurrentDirectory(defaultDirectory);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();

                }

                if(result == JFileChooser.CANCEL_OPTION){
                    cancel = true;
                }
                //get date/time to add to file path

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh.mm.ss");
                String formattedDateTime = now.format(formatter);
                Path path = Paths.get(filePath + formattedDateTime);


                String host = "smcse-stuproj00.city.ac.uk";
                int port = 3306;
                String username = "in2018g04_a";
                String password = "bx5jmkL5";
                String database = "in2018g04";
                String filepath = filePath + "_" + formattedDateTime + ".sql";

                // Construct the command string
                if(!cancel) {
                    String[] cmd = new String[]{"mysqldump", "--skip-column-statistics", "-h" + host, "-P" + port, "-u" + username, "-p" + password, database, "-r" + filepath};

                try {
                    // Execute the command
                    Process process = Runtime.getRuntime().exec(cmd);

                    // Wait for the command to complete
                    int exitCode = process.waitFor();

                    // Check the exit code
                    if (exitCode == 0) {
                        System.out.println("Database backup completed successfully.");
                        JFrame frame = new JFrame("Example Frame");
                        frame.setSize(300, 200);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        JOptionPane.showMessageDialog(frame, "Successful");
                    } else {
                        System.err.println("Database backup failed. Exit code: " + exitCode);
                        System.out.println("Failed to restore database.");
                        JFrame frame = new JFrame("Example Frame");
                        frame.setSize(300, 200);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        JOptionPane.showMessageDialog(frame, "Failed");
                    }
                } catch (IOException | InterruptedException a) {
                    a.printStackTrace();
                }
                }
/*



                Properties properties = new Properties();
                properties.setProperty("user", "in2018g04_d");
                properties.setProperty("password", "1Lzc6IUm");
                properties.setProperty("useSSL", "false");

                // Set up backup file path and name
                String backupFolderPath = formattedDateTime+"\\";

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2018g04", properties)) {
                    // Create a ZipOutputStream to write the backup data to a zip file
                    String query = "SHOW TABLES";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(query)) {
                        while (rs.next()) {
                            String tableName = rs.getString(1);

                            // Write the table data to a CSV file in the backup folder
                            String backupFilePath = backupFolderPath + tableName + ".csv";
                            try (FileWriter writer = new FileWriter(new File(backupFilePath))) {
                                String selectQuery = "SELECT * FROM " + tableName;
                                try (Statement selectStmt = conn.createStatement();
                                     ResultSet selectRs = selectStmt.executeQuery(selectQuery)) {
                                    while (selectRs.next()) {
                                        // Write each row to the CSV file
                                        for (int i = 1; i <= selectRs.getMetaData().getColumnCount(); i++) {
                                            writer.write("\"" + selectRs.getString(i) + "\",");
                                        }
                                        writer.write("\n");
                                    }
                                }
                            }
                        }
                    }
                    System.out.println("Backup created successfully");
                } catch (SQLException | IOException a) {
                    a.printStackTrace();
                }

 */

            }
        });

       showLog();


        // Read the login information from the file and add it to the table model

    }

    //manual input
    /*
    private void createTable(){
        Object[][] data = {{111, "Frank", "Furter", "frankfurter@ttechttonic.com", "17 Church Road, Blackpool", "Travel advisor", "adv123"},
                {112, "Holly", "Harper", "hollyharper@ttechttonic.com", "44 Victoria Road, London", "Travel advisor", "adv123"},
                {113, "India", "Iris", "indiairis@ttechttonic.com", "51 Green Lane, Birmingham", "Travel advisor", "adv123"}};
        DB.setModel(new DefaultTableModel(data, new String[]{"StaffID", "Firstname", "Lastname", "Email", "Address", "Role", "Password"}));
    }

     */

    public void showLog(){
        String[] columnNames = {"User", "Status", "Time"};
        dTable = new DefaultTableModel(columnNames, 0);
        dTable.setColumnIdentifiers(columnNames);
        logsTable.setRowHeight(25);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("login_records.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String userName = parts[1];
                String status = parts[2];
                String time = parts[4] + " " + parts[5] + " " + parts[6] + " " + parts[7] + " " + parts[8];
                dTable.addRow(new Object[]{userName, status, time});
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        logsTable.setModel(dTable);
    }
    //automated input

    //get connection
    //Automate Jtable With Database Values
    public void showStaff() {

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM Staff");
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) DB.getModel();
            DB.setRowHeight(25);

            //getting column names
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }

            model.setColumnIdentifiers(colName);

            //getting data
            String StaffID, Firstname, Lastname, Email, Address, Role, Password;
            while (resultSet.next()) {
                StaffID = resultSet.getString(1);
                Firstname = resultSet.getString(2);
                Lastname = resultSet.getString(3);
                Email = resultSet.getString(4);
                Address = resultSet.getString(5);
                Role = resultSet.getString(6);
                Password = resultSet.getString(7);

                String[] row = {StaffID, Firstname, Lastname, Email, Address, Role, Password};
                model.addRow(row);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
//Clear table fields
    private void clearRegisterStaffField() {
        fnameTf.setText(null);
        lnameTf.setText(null);
        emailTf.setText(null);
        addressTf.setText(null);
        passwordTf.setText(null);
    }

    //Register Customer
    public void registerMember() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRegisterStaffField();
            }
        });
        registerStaffBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String firstName = fnameTf.getText();
                    String lastName = lnameTf.getText();
                    String email = emailTf.getText();
                    String address = addressTf.getText();
                    String password = passwordTf.getText();
                    password = Login.encryptString(password);
                    // Establishes a connection
                    Connection con = DBConnection.getConnection();
                    // INSERT INTO statement with values from JTextFields
                    PreparedStatement ps = con.prepareStatement("INSERT INTO Staff(FirstName, LastName, Email, Address," + " Role, Password) VALUES(?,?,?,?,?,?)");

                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO Admin(StaffID) VALUES (?)");
                    PreparedStatement ps3 = con.prepareStatement("INSERT INTO TravelAdvisor(StaffID) VALUES (?)");
                    PreparedStatement ps4 = con.prepareStatement("INSERT INTO OfficeManager(StaffID) VALUES (?)");

                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SHOW TABLE STATUS WHERE Name='Staff'");

                    // Retrieve the next auto-increment value from the result set
                    int nextAutoIncrement = -1;
                    if (rs.next()) {
                        nextAutoIncrement = rs.getInt("Auto_increment");

                    }
                    System.out.println(nextAutoIncrement);

                    ps.setString(1, firstName);
                    ps.setString(2, lastName);
                    ps.setString(3, email);
                    ps.setString(4, address);
                    if (adminRadioButton.isSelected()) {
                        ps.setString(5, adminRadioButton.getText());
                        ps2.setInt(1, nextAutoIncrement);


                    } else if (officeManagerRadioButton.isSelected()) {
                        ps.setString(5, officeManagerRadioButton.getText());
                        ps4.setInt(1, nextAutoIncrement);

                    } else {
                        ps.setString(5, travelAdvisorRadioButton.getText());
                        ps3.setInt(1, nextAutoIncrement);
                    }
                    //showing error message if any of the text fields are empty
                    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || address.isEmpty() || !adminRadioButton.isSelected() && !officeManagerRadioButton.isSelected() && !travelAdvisorRadioButton.isSelected() || password.isEmpty()) {
                        errorLabel.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Staff Member Added");
                        ps.setString(6, password);

                        //exectue SQL queries
                        ps.executeUpdate();
                        if (adminRadioButton.isSelected()) ps2.executeUpdate();
                        else if (officeManagerRadioButton.isSelected()) ps4.executeUpdate();
                        else ps3.executeUpdate();

                        //Clears once form is submitted
                        clearRegisterStaffField();
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                showStaff();
            }
        });
    }

    //Getters and Setters
    public JPanel getAplane() {
        return aplane;
    }

    public void setAplane(JPanel aplane) {
        this.aplane = aplane;
    }

}