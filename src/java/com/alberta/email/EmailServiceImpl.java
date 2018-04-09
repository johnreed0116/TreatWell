/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.email;

import com.alberta.dao.DAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author Faraz
 */
public class EmailServiceImpl implements EmailService {

    //  private MailSender mailSender;
    private JavaMailSender mailSender;
    private DAO dao;

    /**
     * @return the dao
     */
    @Override
    public DAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    @Override
    public void setDao(DAO dao) {
        this.dao = dao;
    }

    /**
     * @return the mailSender
     */
    @Override
    public JavaMailSender getMailSender() {
        return mailSender;
    }

    /**
     * @param mailSender the mailSender to set
     */
    @Override
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean sentSignupEmail(String text, String receiver) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            String sender = "TreatWell Services";
            String senderEmail = "info@treatwellservices.com";
            helper.setFrom(senderEmail, sender);

            helper.setTo(receiver);
            helper.setSubject("Login details for Ezimedic.");
            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>");
            sb.append(text);
            sb.append("<br/><br/>");
            sb.append("<p>Do not reply to this email as this is a system generated message.</p></body></html>");

            helper.setText(sb.toString(), true);
            mailSender.send(message);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
