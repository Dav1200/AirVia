import javax.swing.*;

public class OfficeManagerMenu extends JFrame{

    private JTabbedPane tabbedPane1;
    private JTextField tfSearchBlank;
    private JButton searchButton;
    private JTable dbBlanks;
    private JTextField textField1;
    private JButton reassignButton;
    private JTextField tfReassignBlank;
    private JButton workButton;
    private JButton generateReportButton;
    private JTable turnoverReport;

    public JPanel getoPlane() {
        return oPlane;
    }

    public void setoPlane(JPanel oPlane) {
        this.oPlane = oPlane;
    }

    private JPanel oPlane;
    private JTextField tfComRate;
    private JButton generateReportButton2;
    private JTable salesReport;
    private JButton setRateButton;
    private JButton setRateButton1;
    private JTabbedPane tabbedPane2;
    private JButton generateReportButton1;
    private JTable table1;
    private JButton generateReportButton3;
    private JTable table2;

    public OfficeManagerMenu() {
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
