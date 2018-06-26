/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import com.alberta.email.EmailServiceImpl;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author farazahmad
 */
public class SendNotification implements Runnable {

    private String text;
    private String receiver;
    private String title;
    private String subject;
    private String key;

    public SendNotification(String title_, String subject_, String text_, String receiver_, String key_) {
        this.text = text_;
        this.receiver = receiver_;
        this.title = title_;
        this.subject = subject_;
        this.key = key_;
    }

    @Override
    public void run() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("<html><head><title>" + this.getTitle() + "</title></head><body>");
            sb.append("<p>" + this.getText() + "</p>");
            sb.append("<br/><br/><a href='https://play.google.com/store/apps/details?id=com.fabsol.ezimedic' target='_blank'>Download Ezimedic Mobile Application</a>");
            sb.append("<br/><br/><br/><br/>");
            sb.append("<p>Do not reply to this email as this is a system generated message.</p></body></html>");

            Email from = new Email("info@treatwellservices.com", "Ezimedic");
            
            Email to = new Email(getReceiver());
            Content content = new Content("text/html", sb.toString());
            Mail mail = new Mail(from, this.getSubject(), to, content);
            SendGrid sg = new SendGrid(this.key);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

}
