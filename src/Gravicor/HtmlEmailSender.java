/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gravicor;

/**
 *
 * @author Daniel
 */
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class HtmlEmailSender {
    
    private String host;
    private String port;
    private String mailFrom;
    private String password;
    private String mailTo;
    private String subject;
    private String message;
    private String getUltimoError;
    
    public HtmlEmailSender(){
        this.host = "smtp.gmail.com";
        this.port = "587";
        this.mailFrom = "luis.rivero4@aiesec.net";
        this.password = "ksdlcxma";
        this.getUltimoError = "";
 
        // outgoing message information
        this.mailTo = "";
        this.subject = "";
 
        // message contains HTML markups
        this.message = "";
    }
    public HtmlEmailSender(String mailTo, String subject, String message){
        this.host = "smtp.gmail.com";
        this.port = "587";
        this.mailFrom = "luis.rivero4@aiesec.net";
        this.password = "ksdlcxma";
        this.getUltimoError = "";
        
        //agregando los valores
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
    }
    
    public boolean sendHtmlEmailWithConfirmation(){
        
        try {
            sendHtmlEmail();
            
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
 
    public void sendHtmlEmail() throws AddressException,
            MessagingException {
 
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", this.host);
        properties.put("mail.smtp.port", this.port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailFrom, password);
            }
        };
 
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(this.mailFrom));
        InternetAddress[] toAddresses = { new InternetAddress(this.mailTo) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(this.subject);
        msg.setSentDate(new Date());
        // set plain text message
        msg.setContent(this.message, "text/html");
 
        // sends the e-mail
        Transport.send(msg);
 
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGetUltimoError() {
        return getUltimoError;
    }

    
 
    
}


