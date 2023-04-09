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

    // field list used throughout the AdminMenu
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
    private JComboBox comboBox1;
    private JTextField stockAmount;
    private JTextField requestStock;
    private JTextField returnStock;
    private JButton saveButton;
    private JButton addBlankStockButton;

    private DefaultTableModel dTable;
    private DBConnection db;

    //Constructor

    /**
     * Constructs a new AdminMenu object.
     * The AdminMenu provides a graphical user interface (GUI) for managing staff, database backup and restore, and stock management.
     */
    public AdminMenu() {
        //manual input

        //createTable();
        //show tables and error labels
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
                //allows the user to pick from JfileChooser, so they are able to select the correct restore file
                String filePath = "";
                JFileChooser fileChooser = new JFileChooser();
                File defaultDirectory = new File(System.getProperty("user.dir"));
                fileChooser.setCurrentDirectory(defaultDirectory);
                int result = fileChooser.showOpenDialog(null);

                //If user selects the file get the path
                if (result == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();

                }
                //if user cancels the path should not be selected
                if (result == JFileChooser.CANCEL_OPTION) {
                    cancel = true;
                }


                //credentials needed to connect to admin Database server.
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
                // essentially combines the commmands and runs them in powershell

                //Check if user picked a restore file, otherwise dont restore.
                if (!cancel) {
                    ProcessBuilder pb = new ProcessBuilder(command);
                    pb.redirectInput(ProcessBuilder.Redirect.from(new File(fileName)));

                    try {
                        // Start the process
                        Process p = pb.start();

                        // Wait for the process to finish
                        int exitCode = p.waitFor();

                        // Check if the process completed successfully
                        // Shows a prompts
                        if (exitCode == 0) {
                            System.out.println("Database restored successfully.");
                            JFrame frame = new JFrame("Example Frame");
                            frame.setSize(300, 200);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            showStaff();
                            JOptionPane.showMessageDialog(frame, "Successful");


                        }
                        // show prommpt to user if tthe database restore was unsuccessful
                        else {
                            //Creating window to show Error as GUI prompt
                            System.out.println("Failed to restore database.");
                            JFrame frame = new JFrame("Example Frame");
                            frame.setSize(300, 200);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                            JOptionPane.showMessageDialog(frame, "Failed");
                        }


                        //Handle exceptions produced by try statement helps to debug software
                    } catch (IOException ev) {
                        //Print the error in output window to narrow the error list.
                        System.err.println("Error starting the process: " + ev.getMessage());
                    } catch (InterruptedException ez) {
                        Thread.currentThread().interrupt();
                        System.err.println("Process interrupted: " + ez.getMessage());
                    } catch (Exception ex) {
                        System.err.println("An unexpected error occurred: " + ex.getMessage());
                    }
                }
            }
        });

        //backup DB functionality
        backupDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Opens a JfileChooser to allow the user to pick the appropriate backup location
                boolean cancel = false;
                String filePath = "";
                JFileChooser fileChooser = new JFileChooser();
                File defaultDirectory = new File(System.getProperty("user.dir"));
                fileChooser.setCurrentDirectory(defaultDirectory);
                int result = fileChooser.showOpenDialog(null);

                //if the folder is chosen, the path is put in the filepath variable used later to store the backup file
                if (result == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();

                }

                //tells the software user did not pick the confirm dialog in JfileChooser
                if (result == JFileChooser.CANCEL_OPTION) {
                    cancel = true;
                }
                //get date/time to add to file path

                //get the current data to exact second, so when saving the backup file a timestamp is shown
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh.mm.ss");
                String formattedDateTime = now.format(formatter);
                Path path = Paths.get(filePath + formattedDateTime);


                //Connect to Admin Database Server
                String host = "smcse-stuproj00.city.ac.uk";
                int port = 3306;
                String username = "in2018g04_a";
                String password = "bx5jmkL5";
                String database = "in2018g04";
                String filepath = filePath + "_" + formattedDateTime + ".sql";

                // Construct the command string
                //if user picked a file location
                if (!cancel) {
                    String[] cmd = new String[]{"mysqldump", "--skip-column-statistics", "-h" + host, "-P" + port, "-u" + username, "-p" + password, database, "-r" + filepath};

                    try {
                        // Execute the command
                        Process process = Runtime.getRuntime().exec(cmd);

                        // Wait for the command to complete
                        int exitCode = process.waitFor();

                        // Check the exit code
                        if (exitCode == 0) {
                            //prompts a successful to notify the user the backup was fully completed.
                            //opens a new window
                            System.out.println("Database backup completed successfully.");
                            JFrame frame = new JFrame("Example Frame");
                            frame.setSize(300, 200);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                            JOptionPane.showMessageDialog(frame, "Successful");
                        } else {
                            //prompts a successful to notify the user the backup failed.
                            //opens a new window
                            System.err.println("Database backup failed. Exit code: " + exitCode);
                            System.out.println("Failed to restore database.");
                            JFrame frame = new JFrame("Example Frame");
                            frame.setSize(300, 200);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                            //show error of backup process failing.
                            JOptionPane.showMessageDialog(frame, "Failed");
                        }
                    } catch (IOException | InterruptedException a) {
                        a.printStackTrace();
                    }
                }

            }
        });

        showLog();


        // Read the login information from the file and add it to the table model
        addBlankStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                BlankStock a = new BlankStock();
                a.setContentPane(a.getPanel());
                a.setVisible(true);
                a.setSize(400, 400);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }

        });


        //this check which ticket type is selected in the combo box for registering blanks to the database
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stockAmount.setText("");
                //get a connection to the database
                try (Connection con = DBConnection.getConnection()) {


                    //get the total amount of blanks received for the specific blank type
                    PreparedStatement ps = con.prepareStatement("SELECT COUNT(blanks_received) FROM blank_stock WHERE status = ? AND blanks_received LIKE ?");

                    //parameter index for prepared statement
                    //assinging the correct variables
                    ps.setString(1, "unassigned");
                    System.out.println(comboBox1.getSelectedItem().toString());
                    ps.setString(2, comboBox1.getSelectedItem().toString() + "%");
                    ResultSet rs = ps.executeQuery();

                    //if result set is not empty
                    if (rs.next()) {
                        //update the text field with the remaining stock amount
                        stockAmount.setText(rs.getString(1));

                    }
                    //if result set is empty set stock text field to 0
                    else {
                        stockAmount.setText("0");
                    }
                    //handle exceptions
                } catch (SQLException | ClassNotFoundException xe) {
                    throw new RuntimeException(xe);
                }
            }
        });

        //save stocks returned and requested stock.
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //get connection to database
                try (Connection con = DBConnection.getConnection()) {

                    //check if there is enough stock available for return
                    if (!returnStock.getText().isEmpty() && !comboBox1.getSelectedItem().toString().equals("Select Type")) {
                        //statement returns the stock  and removes it from the database.
                        //select the unassigned blanks for validity
                        PreparedStatement ps = con.prepareStatement("DELETE FROM blank_stock WHERE blanks_received LIKE ? AND status = 'unassigned' AND blanks_received <= " +
                                "(SELECT MAX(blanks_received) FROM (SELECT blanks_received FROM blank_stock WHERE blanks_received LIKE ? ORDER BY blanks_received DESC LIMIT ?) AS subquery)ORDER BY blanks_received DESC LIMIT ?");

                        //parameter list for prepared statement
                        ps.setString(1, comboBox1.getSelectedItem().toString() + "%");
                        ps.setString(2, comboBox1.getSelectedItem().toString() + "%");


                        //if return amount is larger than total stock show dialog saying invalid
                        //maximum return can only be total unassigned blanks.
                        if (Integer.parseInt(returnStock.getText()) > Integer.parseInt(stockAmount.getText())) {
                            dialog("Enter Valid Amount");
                            returnStock.setText("");
                            return;
                        }
                        //parameter list for prepared statement providing correct variables.
                        ps.setInt(3, Integer.parseInt(returnStock.getText()));
                        ps.setInt(4, Integer.parseInt(returnStock.getText()));
                        ps.executeUpdate();
                        //prompt the user return was successful.
                        dialog("Success");
                        //reset the stock amount to empty.
                        returnStock.setText("");
                    }

                    //if invalid format chosen show a prompt to the user - invalid blank type selected
                    else {
                        dialog("Enter/Pick Valid Options please ");
                    }

                    //close db connection
                    DBConnection.getConnection().close();
                    //handle exceptions.
                } catch (SQLException | ClassNotFoundException xe) {
                    throw new RuntimeException(xe);
                }


            }
        });
    }


    //show logs of which user logged in in a table format
    public void showLog() {

        //create a table with appropriate columns
        String[] columnNames = {"User", "Status", "Time"};
        dTable = new DefaultTableModel(columnNames, 0);
        dTable.setColumnIdentifiers(columnNames);
        //adjust the size of jtable
        logsTable.setRowHeight(25);

        //reads from txt file and converts it into jtable format to be displayed on screen
        try {
            BufferedReader reader = new BufferedReader(new FileReader("login_records.txt"));
            String line;
            //While there is data add it to the jtable as a row
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String userName = parts[1];
                String status = parts[2];
                String time = parts[4] + " " + parts[5] + " " + parts[6] + " " + parts[7] + " " + parts[8];
                //add data as a new row in logs table
                dTable.addRow(new Object[]{userName, status, time});
            }

            //close the reader
            reader.close();
            //handle exceptions
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //update the logstable with new data.
        logsTable.setModel(dTable);
    }

    //shows all staff members in a table format with their corresponding details.
    public void showStaff() {
        //get connection to database
        try (Connection con = DBConnection.getConnection()) {
            //select all from staff table
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Staff");
            //store the result in a result set
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            //create a table schema to be displayed on jtable
            DefaultTableModel model = (DefaultTableModel) DB.getModel();
            //set the size of table
            DB.setRowHeight(25);


            //getting column names from database
            int col = resultSetMetaData.getColumnCount();
            String[] colName = new String[col];
            for (int i = 1; i <= col; i++) {
                colName[i - 1] = resultSetMetaData.getColumnName(i);
            }
            //set the jtable names with database column names
            model.setColumnIdentifiers(colName);

            //get all data which will be displayed in the jtable for each staff
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
                //add the data in the jtable
            }
            con.close();
            con.close();

            //handle exceptions
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    //convenience method for showing pop messages
    //prompts
    public void dialog(String s) {
        JOptionPane.showMessageDialog(this, s);
    }
//Clear table fields


    //clear the text fields after a new staff is registered to avoid duplication or spam press
    private void clearRegisterStaffField() {

        //clear each individual fields.
        fnameTf.setText(null);
        lnameTf.setText(null);
        emailTf.setText(null);
        addressTf.setText(null);
        passwordTf.setText(null);
    }

    //Register staff member to database.
    public void registerMember() {
        //if clear button is pressed remove details
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearRegisterStaffField();
            }
        });


        //if register button is pressed perform the follwing actions of
        registerStaffBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    //get the text fields and store them in a variable for easier access
                    String firstName = fnameTf.getText();
                    String lastName = lnameTf.getText();
                    String email = emailTf.getText();
                    String address = addressTf.getText();
                    String password = passwordTf.getText();
                    password = Login.encryptString(password);
                    // Establishes a connection
                    Connection con = DBConnection.getConnection();
                    // INSERT INTO statement with values from JTextFields

                    //prepared statement add staff members in the correct tables foreign key constraints
                    PreparedStatement ps = con.prepareStatement("INSERT INTO Staff(FirstName, LastName, Email, Address," + " Role, Password) VALUES(?,?,?,?,?,?)");
                    PreparedStatement ps2 = con.prepareStatement("INSERT INTO Admin(StaffID) VALUES (?)");
                    PreparedStatement ps3 = con.prepareStatement("INSERT INTO TravelAdvisor(StaffID) VALUES (?)");
                    PreparedStatement ps4 = con.prepareStatement("INSERT INTO OfficeManager(StaffID) VALUES (?)");

                    Statement stmt = con.createStatement();
                    //store executed query
                    ResultSet rs = stmt.executeQuery("SHOW TABLE STATUS WHERE Name='Staff'");

                    // Retrieve the next auto-increment value from the result set
                    int nextAutoIncrement = -1;
                    if (rs.next()) {
                        nextAutoIncrement = rs.getInt("Auto_increment");

                    }
                    System.out.println(nextAutoIncrement);
                    //appropriate variables for placeholders in sql statement.
                    ps.setString(1, firstName);
                    ps.setString(2, lastName);
                    ps.setString(3, email);
                    ps.setString(4, address);


                    //check which radiobutton is selected
                    //select the role for the new staff member
                    //select the appropriate if statement
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
                    //close db connection
                    con.close();
                    //handle exceptions
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                //refresh the staff table to display the new user.
                showStaff();

            }
        });
    }


    //Getters and Setters for fields
    public JPanel getAplane() {
        return aplane;
    }

    public void setAplane(JPanel aplane) {
        this.aplane = aplane;
    }

}