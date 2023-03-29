import javax.swing.*;

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
        codeSentTxt.setVisible(false);

    }
}



