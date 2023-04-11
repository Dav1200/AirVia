import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * class to connect to SMTP to send an email
 */
public class GEmailSender {

    /**
     * send email to reciepent, from sender, with subject text and information
     * @param to
     * @param from
     * @param subject
     * @param text
     * @return flag
     */
    public boolean sendEmail(String to, String from, String subject, String text) {
        boolean flag = false;
        //logic
        //smtp properties to connect to the Gmail SMTP server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        //Credentials for the sender
        //must provide email and password for it to send mail
        String username = "davsuper4@Gmail.com";
        String password = "kmgleqbsqbaccivt";

        //starts a SMTTP session to send mail
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


        //try to send the email otherwise catch the error resulted.
        try {

            //details provided for the recipient in parameter list.
            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setFrom(new InternetAddress(from));
            //add your email content here
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            //confirm if message is sent.
            flag = true;
        } catch (Exception e) {
            //handle exceptions
            e.printStackTrace();
        }

        return flag;
    }

}