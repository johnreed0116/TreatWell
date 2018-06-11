/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import com.alberta.email.EmailServiceImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author farazahmad
 */
public class SendNotification implements Runnable {

    private JavaMailSender mailSender;
    private String text;
    private String receiver;

    public SendNotification(JavaMailSender mailSender_, String text_, String receiver_) {
        this.mailSender = mailSender_;
        this.text = text_;
        this.receiver = receiver_;
    }

    @Override
    public void run() {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            String sender = "Ezimedic";
            String senderEmail = "info@treatwellservices.com";
            helper.setFrom(senderEmail, sender);

            helper.setTo(getReceiver());
            helper.setSubject("Ezimedic Login Details");
            StringBuilder sb = new StringBuilder();
            sb.append("<html><head><title>Ezimedic Login Details</title></head><body>");
            sb.append("<p>" + getText() + "</p>");
            sb.append("<br/><br/><a href='https://play.google.com/store/apps/details?id=com.fabsol.ezimedic' target='_blank'>Download Ezimedic Mobile Application</a>");
            sb.append("<br/><br/><br/><br/>");
            sb.append("<p>Do not reply to this email as this is a system generated message.</p></body></html>");
            helper.setText(sb.toString(), true);
            mailSender.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the mailSender
     */
    public JavaMailSender getMailSender() {
        return mailSender;
    }

    /**
     * @param mailSender the mailSender to set
     */
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

}
