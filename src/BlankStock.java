import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BlankStock extends JFrame {
    public JPanel getPanel() {
        return Panel;
    }

    private JPanel Panel;
    private JTextField datetxt;
    private JTextField startTxt;
    private JTextField endTxt;
    private JButton saveButton;
    private JTextField unassingedTextField;
    private JComboBox ticketTypeBox;
    private JLabel ticketType;

    public BlankStock()  {
        unassingedTextField.setText("unassigned");
        unassingedTextField.setEditable(false);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = now.format(formatter);
        datetxt.setText(formattedDateTime);

        saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try(Connection con = DBConnection.getConnection()) {


                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDateTime = now.format(formatter);


               datetxt.setText(formattedDateTime);
             String  Date = datetxt.getText();

                long End;
                long Start = Long.parseLong(startTxt.getText());
                if(endTxt.getText().isEmpty()){
                    End = Start;
                }
                else {
                    End = Long.parseLong(endTxt.getText());
                }


                PreparedStatement ps2 = con.prepareStatement("SELECT MAX(blanks_received) FROM blank_stock WHERE blanks_received LIKE ?");
                ps2.setString(1, ticketTypeBox.getSelectedItem().toString()+"%");
                ResultSet rs1 = ps2.executeQuery();


                if(rs1.next()){
                    if (rs1.getString(1) == null) {

                    Start = Long.parseLong(startTxt.getText());
                    End = Start + Long.parseLong(endTxt.getText());}

                }
                else{
                    //start from 000001
                    String blankstart = ticketTypeBox.getSelectedItem().toString() + "00000001";
                    Start = Long.parseLong(blankstart);
                    End = Start + Long.parseLong(endTxt.getText()) ;
                }



                for(long a = Start; a < End;a++  ) {
                    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM blank_stock WHERE blanks_received = ?");
                    ps1.setString(1, String.valueOf(a));
                    ResultSet rs = ps1.executeQuery();
                    if(rs.next()) {
                        System.out.println("Data exists in the table");
                    } else {
                        PreparedStatement ps = con.prepareStatement("INSERT INTO blank_stock(date,blanks_received,status) VALUES(?,?,?)");
                        ps.setString(1, Date);
                        ps.setString(2, String.valueOf(a));
                        ps.setString(3,unassingedTextField.getText());
                        ps.executeUpdate();
                    }



                }
                dialog("Successful");
            }

            catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            clear();

        }

    });
        ticketTypeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection con = DBConnection.getConnection()) {
                    startTxt.setText("");

                    PreparedStatement ps2 = con.prepareStatement("SELECT MAX(blanks_received) FROM blank_stock WHERE blanks_received LIKE ? ");
                    ps2.setString(1, ticketTypeBox.getSelectedItem().toString() + "%");
                    ResultSet rs1 = ps2.executeQuery();
/*
                    if(!rs1.next()){
                        startTxt.setText(ticketTypeBox.getSelectedItem().toString() + "00000001");
                    String s = startTxt.getText();
                        System.out.println(s);

                    }

                    else {

 */
                        if(rs1.next()) {
                            if (rs1.getString(1) == null) {
                                startTxt.setText(ticketTypeBox.getSelectedItem().toString() + "00000001");
                                return;
                            }
                            startTxt.setText(String.valueOf(Long.parseLong(rs1.getString(1)) + 1));

                        }



                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }});
    }

    public void clear(){
        datetxt.setText(null);
        startTxt.setText(null);
        endTxt.setText(null);
        }

        public void dialog(String s){
        JOptionPane.showMessageDialog(this,s);
        }


}
