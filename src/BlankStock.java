import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * the GUI to show blank stock
 */
public class BlankStock extends JFrame {
    public JPanel getPanel() {
        return Panel;
    }


    //Fields used throughout blankstock
    private JPanel Panel;
    private JTextField datetxt;
    private JTextField startTxt;
    private JTextField endTxt;
    private JButton saveButton;
    private JTextField unassingedTextField;
    private JComboBox ticketTypeBox;
    private JLabel ticketType;


    //constructor
    /**
     * calls the functions needed for the blank stock GUI
     */
    public BlankStock()  {

        //predefined variables
        unassingedTextField.setText("unassigned");
        unassingedTextField.setEditable(false);


        //set local date
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = now.format(formatter);
        datetxt.setText(formattedDateTime);


        /**
         * calls the functions needed for the blank stock GUI
         */
        saveButton.addActionListener(new ActionListener() {
            /**
             *  the process for saving blanks assigned
             * @param e
             */
        @Override
        public void actionPerformed(ActionEvent e) {
            //coonect to database
            try(Connection con = DBConnection.getConnection()) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDateTime = now.format(formatter);
               datetxt.setText(formattedDateTime);
             String  Date = datetxt.getText();

                //if user only inputted data in start column
                //only want to assign one blank
                //make end the beginning as well
                long End;
                long Start = Long.parseLong(startTxt.getText());
                if(endTxt.getText().isEmpty()){
                    End = Start;
                }
                else {
                    End = Long.parseLong(endTxt.getText());
                }

                //sql query to be executed
                //select blank that is not assigned to any advisor

                PreparedStatement ps2 = con.prepareStatement("SELECT MAX(blanks_received) FROM blank_stock WHERE blanks_received LIKE ?");
                ps2.setString(1, ticketTypeBox.getSelectedItem().toString()+"%");
                ResultSet rs1 = ps2.executeQuery();


                //check if result set is produced for database
                if(rs1.next()){
                    if (rs1.getString(1) == null) {
                    //get the values
                    Start = Long.parseLong(startTxt.getText());
                    End = Start + Long.parseLong(endTxt.getText());}

                }

                //set the default value if no result set is produced
                else{
                    //start from 000001
                    //set the text field
                    String blankstart = ticketTypeBox.getSelectedItem().toString() + "00000001";
                    Start = Long.parseLong(blankstart);
                    End = Start + Long.parseLong(endTxt.getText()) ;
                }


                    //check if the blanks are not assinged, if they are, do not assign them.
                for(long a = Start; a < End;a++  ) {
                    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM blank_stock WHERE blanks_received = ?");
                    ps1.setString(1, String.valueOf(a));
                    ResultSet rs = ps1.executeQuery();

                    //check if blanks exist
                    if(rs.next()) {
                        System.out.println("Data exists in the table");
                        //else insert the newly received blanks into the database
                    } else {
                        //sql query to add blanks to database
                        PreparedStatement ps = con.prepareStatement("INSERT INTO blank_stock(date,blanks_received,status) VALUES(?,?,?)");
                        ps.setString(1, Date);
                        ps.setString(2, String.valueOf(a));
                        ps.setString(3,unassingedTextField.getText());
                        ps.executeUpdate();
                    }

                }
                //prompts the user saying the blanks were registered in the database successfully
                dialog("Successful");
            }

            //handle exceptions
            catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            //clear the text fields
            clear();

        }

    });

        /**
         * checking the ticket type in the combo box
         */
        ticketTypeBox.addActionListener(new ActionListener() {
            /**
             * when reading the ticket type from the combo box,
             * it adds the rest of the ID to the end of the blank
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection con = DBConnection.getConnection()) {
                    startTxt.setText("");

                    PreparedStatement ps2 = con.prepareStatement("SELECT MAX(blanks_received) FROM blank_stock WHERE blanks_received LIKE ? ");
                    ps2.setString(1, ticketTypeBox.getSelectedItem().toString() + "%");
                    ResultSet rs1 = ps2.executeQuery();

                    //if there are no blanks in the database
                    //start the database with the blank id + 8 digits
                        if(rs1.next()) {
                            if (rs1.getString(1) == null) {
                                startTxt.setText(ticketTypeBox.getSelectedItem().toString() + "00000001");
                                return;
                            }
                            startTxt.setText(String.valueOf(Long.parseLong(rs1.getString(1)) + 1));

                        }



                        //handle exceptions
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }});
    }


    /**
     * clear the text fields
     */
    public void clear(){
        datetxt.setText(null);
        startTxt.setText(null);
        endTxt.setText(null);
        }

    /**
     * prompt dialog with custom fields
     * @param s
     */
    public void dialog(String s){
        JOptionPane.showMessageDialog(this,s);
        }


}
