/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Faraz
 */
public interface DAO {

    int getCount(String query);

    JdbcTemplate getJdbcTemplate();

    //  List<User> loadAll();
    void setDataSource(DataSource dataSource);

    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    boolean insertAll(String[] query, String userName);

    List getData(String query);

    boolean insertAllRows(String[] query, String userName) throws Exception;

    boolean insertAll(List<String> query, String userName);
}
