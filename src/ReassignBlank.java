import com.sun.net.httpserver.Authenticator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReassignBlank  extends JFrame{
    private JComboBox oldAdvisor;
    private JComboBox blankType;
    private JComboBox blankList;
    private JComboBox newAdvisor;
    private JButton saveButton;

    public JPanel getPane() {
        return Pane;
    }

    private JPanel Pane;

    ReassignBlank(){
        addAdvisor();


        blankType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (blankType.getSelectedItem().toString()){
                    case "444":
                        blankList.removeAllItems();
                        addBlankList();
                        break;

                    case "440":
                        blankList.removeAllItems();
                        addBlankList();
                        break;
                    case "420":
                        blankList.removeAllItems();
                        addBlankList();
                        break;
                    case "101":
                        blankList.removeAllItems();
                        addBlankList();
                        break;

                    case "201":
                        blankList.removeAllItems();
                        addBlankList();
                        break;


                    default:
                        blankList.removeAllItems();
                        addBlankList();


                }
            }
        });
        oldAdvisor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blankList.removeAllItems();
                blankType.setSelectedIndex(0);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
reAssign();
            }
        });
    }

    public void addAdvisor(){
        try(Connection con = DBConnection.getConnection()) {

            PreparedStatement ps1 = con.prepareStatement("SELECT StaffID,FirstName,LastName FROM Staff WHERE role = ?");
            ps1.setString(1,"Travel Advisor");
            ResultSet rs = ps1.executeQuery();

            while(rs.next()){
                oldAdvisor.addItem(rs.getString("StaffID")+" " +rs.getString("FirstName") +" "+ rs.getString("LastName"));
                newAdvisor.addItem(rs.getString("StaffID")+" " +rs.getString("FirstName") +" "+ rs.getString("LastName"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public void addBlankList(){

        try (Connection con = DBConnection.getConnection()
        ){
            PreparedStatement ps = con.prepareStatement("SELECT blanks FROM advisor_blanks WHERE advisor_id = ? AND status = ? AND blanks LIKE ?");
            ps.setString(1, oldAdvisor.getSelectedItem().toString().substring(0,3));
            ps.setString(2,"Unused");
            ps.setString(3,blankType.getSelectedItem().toString()+"%");
            System.out.println(blankType.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                blankList.addItem(rs.getString("blanks"));

            }



        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void reAssign(){

//add a range specific for reassigning  blanks.
        try (Connection con = DBConnection.getConnection()
        ){

            if (oldAdvisor.getSelectedItem() == null||  blankType.getSelectedItem() == null|| blankList.getSelectedItem() == null|| newAdvisor.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,"Incomplete input");
            } else {
                String old = oldAdvisor.getSelectedItem().toString();
                String blankid = blankList.getSelectedItem().toString();
                String New = newAdvisor.getSelectedItem().toString();


                PreparedStatement ps = con.prepareStatement("UPDATE advisor_blanks SET advisor_id =?, advisor_name = ? WHERE blanks = ?");
                ps.setString(1, New.substring(0, 3));
                ps.setString(2, New.substring(4));
                ps.setString(3, blankid);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Successfully Reassigned");
                clear();

            }


        }  catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,e);
            throw new RuntimeException(e);


        }

    }


public void clear(){

        oldAdvisor.setSelectedIndex(0);
        blankType.setSelectedIndex(0);
        blankList.removeAllItems();
        newAdvisor.setSelectedIndex(0);
}




}
