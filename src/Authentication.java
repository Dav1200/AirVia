import com.mysql.cj.log.Log;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

    public Login getLogin() {
        return login;
    }

    private JTextField codeTextField;



    private JButton sMSButton;
    private JButton emailButton;

    public JLabel getCodeSentTxt() {
        return codeSentTxt;
    }

    private JLabel codeSentTxt;

    public JButton getsMSButton() {
        return sMSButton;
    }

    public JButton getEmailButton() {
        return emailButton;
    }
    public Authentication() {
        // Doesn't show the code
        codeSentTxt.setVisible(false);
        
        //remove this AT THE END
        codeTextField.setText(Login.getCodestr());


        codeTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Allows the user to submit the code
                    submitButton.doClick();
                }}

        });
    }
}



