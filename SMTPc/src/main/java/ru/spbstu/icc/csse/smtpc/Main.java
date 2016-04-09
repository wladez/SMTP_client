package ru.spbstu.icc.csse.smtpc;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Main {
    static Properties mailServerProperties;
    static Session mailSession;
    static MimeMessage generatedMailMessage;

    static String addressTo = "wladez95@gmail.com";
    static String addressFrom = "evbushin@yandex.ru";
    static String addressCC = "mailbox.a@yandex.ru";
    //static String addressBCC = "почта-скрытая копия";
    static String host = "smtp.yandex.ru"; //через Яндекс

    public static void main(String args[]) throws MessagingException {
        generateAndSendEmail();
        System.out.println("E-mail was sent successfully!\n");
    }

    public static void generateAndSendEmail() throws MessagingException {
        System.out.println("Setting up Mail Server Properties");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.host", host);
        mailServerProperties.put("mail.smtp.port", "25");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailServerProperties.put("mail.debug", "true");
        System.out.println("Mail Server Properties have been setup successfully.");

        System.out.println("Getting Mail Session");
        mailSession = Session.getDefaultInstance(mailServerProperties, null);
        generatedMailMessage = new MimeMessage(mailSession);
        generatedMailMessage.setFrom(new InternetAddress(addressFrom));
        generatedMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
        generatedMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(addressCC));
        //generatedMailMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress(addressBCC));
        generatedMailMessage.setSubject("JAVA TEST E-MAIL");
        String emailBody = "Test email";
        generatedMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully.");

        System.out.println("Getting Session and sending mail\n");
        Transport transport = mailSession.getTransport("smtp");

        transport.connect(host, "login", "password");
        transport.sendMessage(generatedMailMessage, generatedMailMessage.getAllRecipients());
        transport.close();
    }
}