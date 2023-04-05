import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlankStock extends JFrame {
    public JPanel getPanel() {
        return Panel;
    }

    private JPanel Panel;
    private JTextField datetxt;
    private JTextField startTxt;
    private JTextField endTxt;
    private JButton saveButton;
public BlankStock()  {

    saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try(Connection con = DBConnection.getConnection()) {

                String Date = datetxt.getText();
                long End;
                long Start = Long.parseLong(startTxt.getText());
                if(endTxt.getText().isEmpty()){
                    End = Start;
                }
                else {
                    End = Long.parseLong(endTxt.getText());
                }

                for(long a = Start; a <= End;a++  ) {
                    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM blank_stock WHERE blanks_received = ?");
                    ps1.setString(1, String.valueOf(a));
                    ResultSet rs = ps1.executeQuery();
                    if(rs.next()) {
                        System.out.println("Data exists in the table");
                    } else {
                        PreparedStatement ps = con.prepareStatement("INSERT INTO blank_stock(date,blanks_received) VALUES(?,?)");
                        ps.setString(1, Date);
                        ps.setString(2, String.valueOf(a));
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
        datetxt.setText(null);
        startTxt.setText(null);
        endTxt.setText(null);
        }



}
