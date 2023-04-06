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

        textField1.setText("Unused");
        textField1.setEditable(false);

        addToComboBoxId();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try(Connection con = DBConnection.getConnection()) {

                        String AdvisorId = idcombo.getSelectedItem().toString().substring(0,3);
                        String Name = idcombo.getSelectedItem().toString().substring(4);
                        String Date = dateTxt.getText();
                        long End;
                        long Start = Long.parseLong(blankstxt.getText());
                        if(Endblank.getText().isEmpty()){
                                End = Start;
                        }
                        else {
                             End = Long.parseLong(Endblank.getText());
                        }

                        for(long a = Start; a <= End;a++  ) {
                            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM advisor_blanks WHERE blanks = ?");
                            ps1.setString(1, String.valueOf(a));
                            ResultSet rs = ps1.executeQuery();


                            PreparedStatement ps2 = con.prepareStatement("SELECT blanks_received FROM blank_stock WHERE blanks_received = ?");
                            ps2.setString(1, String.valueOf(a));
                            ResultSet rs1 = ps2.executeQuery();


                            PreparedStatement ps4= con.prepareStatement("SELECT status FROM blank_stock WHERE blanks_received = ?");
                            ps4.setString(1, String.valueOf(a));
                            ResultSet rs4 = ps4.executeQuery();


                            if(rs.next()) {
                                System.out.println("Data exists in the table");
                               error();
                            } else {

                                if (rs1.next()) {
                                    System.out.println("exists");

                                    if(rs4.next()){
                                    PreparedStatement ps = con.prepareStatement("INSERT INTO advisor_blanks(advisor_id,advisor_name, date, blanks,status) VALUES(?,?,?,?,?)");
                                    ps.setString(1, AdvisorId);
                                    ps.setString(2, Name);
                                    ps.setString(3, Date);
                                    ps.setString(4, String.valueOf(a));
                                    ps.setString(5,textField1.getText());

                                    PreparedStatement ps3 = con.prepareStatement("Update blank_stock SET status = ? WHERE blanks_received = ?");
                                    ps3.setString(1,"assigned");
                                    ps3.setString(2, String.valueOf(a));
                                    ps3.executeUpdate();
                                        ps.executeUpdate();

                                }}

                            }
                        }
                    }
                    catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                        clear();

                }

        });}

    public void clear(){
        dateTxt.setText(null);
        blankstxt.setText(null);
        Endblank.setText(null);
    }


    public void addToComboBoxId()
    {
        try(Connection con = DBConnection.getConnection()) {

            PreparedStatement ps1 = con.prepareStatement("SELECT StaffID,FirstName,LastName FROM Staff WHERE role = ?");
            ps1.setString(1,"Travel Advisor");
            ResultSet rs = ps1.executeQuery();

            while(rs.next()){
                idcombo.addItem(rs.getString("StaffID")+" " +rs.getString("FirstName") +" "+ rs.getString("LastName"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void error(){
        JOptionPane.showMessageDialog(this, "Error: Blank Already Assigned ");
    }


        public JPanel getPane() {
        return pane;
    }
    }







