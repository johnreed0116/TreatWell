/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.Serializable;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author abbas
 */
public class MonthWeeks implements Serializable, RowMapper {
    
    private BigDecimal monthWeeksId;
    private String monthName;
    private BigDecimal week;
    private String startDate;
    private String endDate;
    private BigDecimal companyId;
    
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        MonthWeeks mw = new MonthWeeks();
        mw.setMonthWeeksId(rs.getBigDecimal("MONTH_WEEKS_ID"));
        mw.setMonthName(rs.getString("MONTH_NME"));
        mw.setWeek(rs.getBigDecimal("WEEK"));
        mw.setStartDate(rs.getString("START_DATE"));
        mw.setEndDate(rs.getString("END_DATE"));
        mw.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return mw;
    }
    /**
     * @return the monthWeeksId
     */
    public BigDecimal getMonthWeeksId() {
        return monthWeeksId;
    }

    /**
     * @param monthWeeksId the monthWeeksId to set
     */
    public void setMonthWeeksId(BigDecimal monthWeeksId) {
        this.monthWeeksId = monthWeeksId;
    }

    /**
     * @return the monthName
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * @param monthName the monthName to set
     */
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    /**
     * @return the week
     */
    public BigDecimal getWeek() {
        return week;
    }

    /**
     * @param week the week to set
     */
    public void setWeek(BigDecimal week) {
        this.week = week;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the companyId
     */
    public BigDecimal getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(BigDecimal companyId) {
        this.companyId = companyId;
    }
}
