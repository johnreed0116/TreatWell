/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.email;

import com.alberta.dao.DAO;
import java.io.File;
import java.util.List;
import java.util.Map;
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
    public boolean sendPostPerformaEmail(String sender, String senderEmail, String text, List<Map> attachments, String path, List<Map> receivers) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(senderEmail, sender);
            String[] to = new String[receivers.size()];
            for (int i = 0; i < receivers.size(); i++) {
                Map map = receivers.get(i);
                to[i] = map.get("EMAIL").toString();
            }
            helper.setTo(to);
            helper.setSubject(sender + " send you a Performa-A attachments.");
            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>");
            sb.append(text);
            sb.append("<br/><br/>");
            sb.append("</body></html>");
            for (Map map : attachments) {
                String fileName = map.get("FILE_NME").toString();
                String fileDesc = (map.get("FILE_DESC") != null ? map.get("FILE_DESC").toString() : "");
                if (fileDesc.isEmpty()) {
                    fileDesc = fileName;
                }
                String masterId = map.get("PERFORMA_MASTER_ID").toString();
                File f = new File(path + File.separator + masterId + File.separator + fileName);
                helper.addAttachment(fileDesc, f);
            }
            helper.setText(sb.toString(), true);
            mailSender.send(message);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean sendCompletedPerformaEmail(String sender, String senderEmail, Map performaObj, List<Map> receivers) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(senderEmail, sender);
            String[] to = new String[receivers.size()];
            for (int i = 0; i < receivers.size(); i++) {
                Map map = receivers.get(i);
                to[i] = map.get("EMAIL").toString();
            }
            helper.setTo(to);
            helper.setSubject(sender + " has completed Performa-A Art Work.");
            StringBuilder sb = new StringBuilder();
            sb.append("<html><body> Dear Sir/Madam <br/> Art work has been completed. Completaion details are as follows: <br/><br/>");
            sb.append("<b>Completed By: </b>").append(performaObj.get("ART_WORK_COMPLETED_BY").toString()).append("<br/><br/>");
            sb.append("<b>Completed Date: </b>").append(performaObj.get("ART_WORK_COMPLETED_DTE").toString()).append("<br/><br/>");
            sb.append("<b>Description: </b>").append(performaObj.get("ART_WORK_DESC").toString()).append("<br/><br/>");
            sb.append("<b>Art Work Path: </b>").append(performaObj.get("ART_WORK_PATH") != null ? performaObj.get("ART_WORK_PATH").toString() : "").append("<br/><br/>");
            sb.append("Thanks");
            sb.append("<br/><br/>");
            sb.append("</body></html>");
            helper.setText(sb.toString(), true);
            mailSender.send(message);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
