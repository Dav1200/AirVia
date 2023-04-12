import com.mysql.cj.log.Log;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * show authentication form for 2fa
 */
public class Authentication extends JFrame {

    private Login login;

    /**
     * getter for JPanel
     * @return
     */
    public JPanel getAuthPanel() {
        return authPanel;
    }

    /**
     * setter for JPanel
     * @param authPanel
     */
    public void setAuthPanel(JPanel authPanel) {
        this.authPanel = authPanel;
    }

    private JPanel authPanel;

    /**
     * getter for submit button
     * @return
     */
    public JButton getSubmitButton() {
        return submitButton;
    }

    private JButton submitButton;

    /**
     * getter for JTextField
     * @return
     */
    public JTextField getCodeTextField() {
        return codeTextField;
    }

    /**
     * getter for login
     * @return
     */
    public Login getLogin() {
        return login;
    }

    private JTextField codeTextField;



    private JButton sMSButton;
    private JButton emailButton;

    /**
     * getter for JLabel
     * @return
     */
    public JLabel getCodeSentTxt() {
        return codeSentTxt;
    }

    private JLabel codeSentTxt;

    /**
     * getter for JButton
     * @return
     */
    public JButton getsMSButton() {
        return sMSButton;
    }

    /**
     * getter for JButton
     * @return
     */
    public JButton getEmailButton() {
        return emailButton;
    }

    /**
     * holds all the functions needed to show authenticatio in 2fa
     */
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



