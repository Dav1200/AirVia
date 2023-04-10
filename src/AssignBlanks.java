import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignBlanks extends JFrame {
    private JTextField Idtxt;
    private JTextField nameTxt;
    private JTextField dateTxt;
    private JTextField blankstxt;
    private JButton saveButton;
    private JTextField Endblank;



    private JPanel pane;
    private JComboBox idcombo;
    private JTextField textField1;

    AssignBlanks(){

        // Sets the text to unused and the user cannot edit the field
        textField1.setText("Unused");
        textField1.setEditable(false);

        addToComboBoxId();

        // if save button is pressed perform the following actions of
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // establishes a connection the database
                    try(Connection con = DBConnection.getConnection()) {
                        // gets the selected options from the combo boxes and converts into text format
                        String AdvisorId = idcombo.getSelectedItem().toString().substring(0,3);
                        String Name = idcombo.getSelectedItem().toString().substring(4);
                        // date
                        String Date = dateTxt.getText();
                        long End;
                        long Start = Long.parseLong(blankstxt.getText());
                        if(Endblank.getText().isEmpty()){
                            // checks if the end blank is the same as the start
                                End = Start;
                        }
                        else {
                            // Assigns the input to the end variable
                             End = Long.parseLong(Endblank.getText());
                        }

                        // goes through the start blank to the end blank
                        for(long a = Start; a <= End;a++  ) {
                            // prepared statement select everything from the table advisor blank according to the input
                            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM advisor_blanks WHERE blanks = ?");
                            ps1.setString(1, String.valueOf(a));
                            // store executed query
                            ResultSet rs = ps1.executeQuery();

                            // second prepared statement which selects blanks received according to input of blanks received in the blank_stock table
                            PreparedStatement ps2 = con.prepareStatement("SELECT blanks_received FROM blank_stock WHERE blanks_received = ?");
                            ps2.setString(1, String.valueOf(a));
                            ResultSet rs1 = ps2.executeQuery();

                            // third prepared statement which selects status from the blank_stock table where the user inputs blanks received
                            PreparedStatement ps4= con.prepareStatement("SELECT status FROM blank_stock WHERE blanks_received = ?");
                            ps4.setString(1, String.valueOf(a));
                            ResultSet rs4 = ps4.executeQuery();

                            if(rs.next()) {
                                // Displays an error pop up message
                                System.out.println("Data exists in the table");
                               error();
                            } else {
                                //otherwise
                                if (rs1.next()) {
                                    //prints to the console that data exists
                                    System.out.println("exists");
                                    if(rs4.next()){
                                    // prepared statement which allows the user to enter values into the database
                                    PreparedStatement ps = con.prepareStatement("INSERT INTO advisor_blanks(advisor_id,advisor_name, date, blanks,status) VALUES(?,?,?,?,?)");
                                    ps.setString(1, AdvisorId);
                                    ps.setString(2, Name);
                                    ps.setString(3, Date);
                                    ps.setString(4, String.valueOf(a));
                                    ps.setString(5,textField1.getText());

                                    // prepared statement that updates the blank_stock table in the database depending on the set status and blank received
                                    PreparedStatement ps3 = con.prepareStatement("Update blank_stock SET status = ? WHERE blanks_received = ?");
                                    ps3.setString(1,"assigned");
                                    ps3.setString(2, String.valueOf(a));
                                    ps3.executeUpdate();
                                        ps.executeUpdate();

                                }}

                            }
                        }
                    }
                    //handles exceptions
                    catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                        // clears all the fields
                        clear();

                }

        });}

    public void clear(){
        // clears all the fields after a certain action is performed
        dateTxt.setText(null);
        blankstxt.setText(null);
        Endblank.setText(null);
    }


    public void addToComboBoxId()
    {
        // establishes a connection to the database
        try(Connection con = DBConnection.getConnection()) {
            // selects StaffID, first name and last name depending on what role the user enters
            PreparedStatement ps1 = con.prepareStatement("SELECT StaffID,FirstName,LastName FROM Staff WHERE role = ?");
            ps1.setString(1,"Travel Advisor");
            //stores executed query
            ResultSet rs = ps1.executeQuery();

            while(rs.next()){
                // goes through all the fields
                idcombo.addItem(rs.getString("StaffID")+" " +rs.getString("FirstName") +" "+ rs.getString("LastName"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    // displays an error message if the blanks are already assigned
    public void error(){
        JOptionPane.showMessageDialog(this, "Error: Blank Already Assigned ");
    }

        //Getters for fields
        public JPanel getPane() {
        return pane;
    }
    }







