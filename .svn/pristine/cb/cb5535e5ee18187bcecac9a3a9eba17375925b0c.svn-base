/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Faraz
 */
public class MonthlyStock implements Serializable, RowMapper {

    private BigDecimal id;
    private String coacode;
    private BigDecimal opening;
    private BigDecimal week1;
    private BigDecimal week2;
    private BigDecimal week3;
    private BigDecimal week4;
    private BigDecimal week5;
    private Date month;
    private BigDecimal siteId;
    private BigDecimal companyId;
    private String chartOfAccount;
    private String coaTitle;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        MonthlyStock stock = new MonthlyStock();
        stock.setId(rs.getBigDecimal("MONTHLY_STOCK_ID"));
        stock.setOpening(rs.getBigDecimal("OPENING"));
        stock.setWeek1(rs.getBigDecimal("CLOSING_WEEK1"));
        stock.setWeek2(rs.getBigDecimal("CLOSING_WEEK2"));
        stock.setWeek3(rs.getBigDecimal("CLOSING_WEEK3"));
        stock.setWeek4(rs.getBigDecimal("CLOSING_WEEK4"));
        stock.setWeek5(rs.getBigDecimal("CLOSING_WEEK5"));
        stock.setMonth(rs.getDate("INVENTORY_MONTH"));
        stock.setCoacode(rs.getString("INVENTORY_COA_CDE"));
        stock.setSiteId(rs.getBigDecimal("SITE_ID"));
        stock.setChartOfAccount(rs.getString("COA_CDE"));
        stock.setCoaTitle(rs.getString("TITLE"));
        return stock;
    }

    /**
     * @return the id
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * @return the coacode
     */
    public String getCoacode() {
        return coacode;
    }

    /**
     * @param coacode the coacode to set
     */
    public void setCoacode(String coacode) {
        this.coacode = coacode;
    }

    /**
     * @return the opening
     */
    public BigDecimal getOpening() {
        return opening;
    }

    /**
     * @param opening the opening to set
     */
    public void setOpening(BigDecimal opening) {
        this.opening = opening;
    }

    /**
     * @return the week1
     */
    public BigDecimal getWeek1() {
        return week1;
    }

    /**
     * @param week1 the week1 to set
     */
    public void setWeek1(BigDecimal week1) {
        this.week1 = week1;
    }

    /**
     * @return the week2
     */
    public BigDecimal getWeek2() {
        return week2;
    }

    /**
     * @param week2 the week2 to set
     */
    public void setWeek2(BigDecimal week2) {
        this.week2 = week2;
    }

    /**
     * @return the week3
     */
    public BigDecimal getWeek3() {
        return week3;
    }

    /**
     * @param week3 the week3 to set
     */
    public void setWeek3(BigDecimal week3) {
        this.week3 = week3;
    }

    /**
     * @return the week4
     */
    public BigDecimal getWeek4() {
        return week4;
    }

    /**
     * @param week4 the week4 to set
     */
    public void setWeek4(BigDecimal week4) {
        this.week4 = week4;
    }

    /**
     * @return the week5
     */
    public BigDecimal getWeek5() {
        return week5;
    }

    /**
     * @param week5 the week5 to set
     */
    public void setWeek5(BigDecimal week5) {
        this.week5 = week5;
    }

    /**
     * @return the month
     */
    public Date getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(Date month) {
        this.month = month;
    }

    /**
     * @return the siteId
     */
    public BigDecimal getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(BigDecimal siteId) {
        this.siteId = siteId;
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

    /**
     * @return the chartOfAccount
     */
    public String getChartOfAccount() {
        return chartOfAccount;
    }

    /**
     * @param chartOfAccount the chartOfAccount to set
     */
    public void setChartOfAccount(String chartOfAccount) {
        this.chartOfAccount = chartOfAccount;
    }

    /**
     * @return the coaTitle
     */
    public String getCoaTitle() {
        return coaTitle;
    }

    /**
     * @param coaTitle the coaTitle to set
     */
    public void setCoaTitle(String coaTitle) {
        this.coaTitle = coaTitle;
    }
}
