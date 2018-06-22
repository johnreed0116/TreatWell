/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.email;

import com.alberta.dao.DAO;
import com.alberta.model.SendNotification;
import org.springframework.core.task.TaskExecutor;

/**
 *
 * @author Faraz
 */
public class EmailServiceImpl implements EmailService {

    private TaskExecutor taskExecutor;
    private String sendGridkey;
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

    @Override
    public boolean sentSignupEmail(String text, String receiver) {
        taskExecutor.execute(new SendNotification(text, receiver, this.sendGridkey));
        return true;
    }

    /**
     * @return the taskExecutor
     */
    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    /**
     * @param taskExecutor the taskExecutor to set
     */
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * @return the sendGridkey
     */
    @Override
    public String getSendGridkey() {
        return sendGridkey;
    }

    /**
     * @param sendGridkey the sendGridkey to set
     */
    @Override
    public void setSendGridkey(String sendGridkey) {
        this.sendGridkey = sendGridkey;
    }

}
