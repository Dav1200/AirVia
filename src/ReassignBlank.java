import com.sun.net.httpserver.Authenticator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReassignBlank  extends JFrame{

    //fields
    private JComboBox oldAdvisor;
    private JComboBox blankType;
    private JComboBox blankList;
    private JComboBox newAdvisor;
    private JButton saveButton;

    //getters setters
    public JPanel getPane() {
        return Pane;
    }

    private JPanel Pane;


    //constructor
    ReassignBlank(){
        addAdvisor();



        //check which blank type the user wants to reassign
        blankType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //if a type is selected list all the available blank they are able to reassign.
                //calls the addblanklist function to handle this
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


                        //default remove all blanks as nothing is selected
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


    //add all available advisors registered in the system with their corresponding assigned blanks
    public void addAdvisor(){
        try(Connection con = DBConnection.getConnection()) {

            PreparedStatement ps1 = con.prepareStatement("SELECT StaffID,FirstName,LastName FROM Staff WHERE role = ?");
            ps1.setString(1,"Travel Advisor");
            ResultSet rs = ps1.executeQuery();

            while(rs.next()){
                //populate the jcombobox with advisors
                oldAdvisor.addItem(rs.getString("StaffID")+" " +rs.getString("FirstName") +" "+ rs.getString("LastName"));
                newAdvisor.addItem(rs.getString("StaffID")+" " +rs.getString("FirstName") +" "+ rs.getString("LastName"));
            }


            //handle exceptions
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    //add the available blanks to jcomobbox dispalying it.
    public void addBlankList(){

        //connect to db to perform sql operations
        try (Connection con = DBConnection.getConnection()
        ){

            //select all blanks which are assigned to selected advisor and where blanks are unassigned
            PreparedStatement ps = con.prepareStatement("SELECT blanks FROM advisor_blanks WHERE advisor_id = ? AND status = ? AND blanks LIKE ?");
            ps.setString(1, oldAdvisor.getSelectedItem().toString().substring(0,3));
            ps.setString(2,"Unused");
            ps.setString(3,blankType.getSelectedItem().toString()+"%");
            System.out.println(blankType.getSelectedItem().toString());

            //store the reuslt from db
            ResultSet rs = ps.executeQuery();

            //populate the jcomobbox with available result set
            while (rs.next()) {
                blankList.addItem(rs.getString("blanks"));

            }



            //hand exceptions
        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    //reassign blanks
    //process of assignning it from one advisor to an another
    public void reAssign(){

//add a range specific for reassigning  blanks.

        //connect to db
        try (Connection con = DBConnection.getConnection()
        ){

            //if fields are empty return an error as empty fields cannot be reassigned

            if (oldAdvisor.getSelectedItem() == null||  blankType.getSelectedItem() == null|| blankList.getSelectedItem() == null|| newAdvisor.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,"Incomplete input");
            } else {
                String old = oldAdvisor.getSelectedItem().toString();
                String blankid = blankList.getSelectedItem().toString();
                String New = newAdvisor.getSelectedItem().toString();


                //sql qeury to change advisor id from old to new advisor as blank is reissued
                PreparedStatement ps = con.prepareStatement("UPDATE advisor_blanks SET advisor_id =?, advisor_name = ? WHERE blanks = ?");
                ps.setString(1, New.substring(0, 3));
                ps.setString(2, New.substring(4));
                ps.setString(3, blankid);

                //execute the update for sql database
                ps.executeUpdate();
                //prompt the user it was successful
                JOptionPane.showMessageDialog(this, "Successfully Reassigned");
                //reset jcomboboxes and textfields
                clear();

            }


            //handle exceptions
        }  catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,e);
            throw new RuntimeException(e);


        }

    }


public void clear(){

        //clear comboboxes
    //sets them as default values
    //clear all generated result set from db
        oldAdvisor.setSelectedIndex(0);
        blankType.setSelectedIndex(0);
        blankList.removeAllItems();
        newAdvisor.setSelectedIndex(0);
}




}
