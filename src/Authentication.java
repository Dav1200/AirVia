import com.mysql.cj.log.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Authentication extends JFrame {

    private Login login;

    public JPanel getAuthPanel() {
        return authPanel;
    }

    public void setAuthPanel(JPanel authPanel) {
        this.authPanel = authPanel;
    }

    private JPanel authPanel;

    public JButton getSubmitButton() {
        return submitButton;
    }

    private JButton submitButton;

    public JTextField getCodeTextField() {
        return codeTextField;
    }

    private JTextField codeTextField;









    public Authentication() {



    }
}



