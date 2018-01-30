/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.email;

import com.alberta.dao.DAO;
import java.util.List;
import java.util.Map;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author Faraz
 */
public interface EmailService {

    /**
     * @return the mailSender
     */
    
    DAO getDao();

    void setDao(DAO dao);

    JavaMailSender getMailSender();
    
    void setMailSender(JavaMailSender mailSender);

    boolean sendPostPerformaEmail(String sender, String senderEmail, String text, List<Map> attachments, String path, List<Map> receivers);

    boolean sendCompletedPerformaEmail(String sender, String senderEmail, Map performaObj, List<Map> receivers);
}
