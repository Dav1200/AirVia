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

    AssignBlanks(){

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
                            if(rs.next()) {
                                System.out.println("Data exists in the table");
                            } else {
                                PreparedStatement ps = con.prepareStatement("INSERT INTO advisor_blanks(advisor_id,advisor_name, date, blanks) VALUES(?,?,?,?)");
                                ps.setString(1, AdvisorId);
                                ps.setString(2, Name);
                                ps.setString(3, Date);
                                ps.setString(4, String.valueOf(a));
                                ps.executeUpdate();
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



        public JPanel getPane() {
        return pane;
    }
    }







