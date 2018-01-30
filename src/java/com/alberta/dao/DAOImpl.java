/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.dao;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 *
 * @author Administrator
 */
public class DAOImpl implements DAO {

    private JdbcTemplate jdbcTemplate;
    private DataSourceTransactionManager transactionManager;

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int getCount(String query) {
        return this.jdbcTemplate.queryForInt(query);
    }

    @Override
    public boolean insertAll(String[] query, String userName) {
        boolean flag = false;

        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // this.jdbcTemplate.update("call ALBERTA.SET_WEB_CTX(?)", userName);
            for (int i = 0; i < query.length; i++) {
                this.jdbcTemplate.update(query[i]);
            }
            //int[] i = this.jdbcTemplate.batchUpdate(query);
            transactionManager.commit(status);
            flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transactionManager.rollback(status);
        }
        return flag;
    }

    @Override
    public boolean insertAll(List<String> query, String userName) {
        boolean flag = false;

        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //   this.jdbcTemplate.update("call SET_WEB_CTX(?)", userName);
            for (int i = 0; i < query.size(); i++) {
                this.jdbcTemplate.update(query.get(i));
            }
            //int[] i = this.jdbcTemplate.batchUpdate(query);
            transactionManager.commit(status);
            flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transactionManager.rollback(status);
        }
        return flag;
    }

    @Override
    public boolean insertAllRows(String[] query, String userName) throws Exception {
        boolean flag = false;

        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // this.jdbcTemplate.update("call ALBERTA.SET_WEB_CTX(?)", userName);
            for (int i = 0; i < query.length; i++) {
                this.jdbcTemplate.update(query[i]);
            }
            //int[] i = this.jdbcTemplate.batchUpdate(query);
            transactionManager.commit(status);
            flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            transactionManager.rollback(status);
            throw new Exception("Error Occured" + ex.getMessage());
        }
        return flag;
    }

    @Override
    public List getData(String query) {
        return this.jdbcTemplate.queryForList(query);
    }

    /**
     * @return the transactionManager
     */
    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * @param transactionManager the transactionManager to set
     */
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
