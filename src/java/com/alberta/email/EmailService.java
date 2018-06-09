/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.email;

import com.alberta.dao.DAO;
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

    boolean sentSignupEmail(String text, String receiver);

}
