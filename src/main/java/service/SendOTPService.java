package service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendOTPService {

    public static void sendOTP(String email, String genOTP) {

        String to = email;

        String host = "smtp.gmail.com";

        Properties properties = new Properties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.port", 587);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.starttls.enable", true);

        String from = "gurpartap1258@chitkara.edu.in";
        String password = "vubz ilbt jwfp aqtt\n";

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });


        try {
            MimeMessage message = new MimeMessage(session);

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("File OTP");

            message.setText("Your One time Password for File Enc app is " + genOTP);

            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}
