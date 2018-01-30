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
public class FinancialYear implements Serializable, RowMapper {

    private BigDecimal financialYearId;
    private String financialYearName;
    private BigDecimal companyId;
    private Date startDate;
    private Date endDate;
    private String status;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        FinancialYear year = new FinancialYear();
        year.setFinancialYearId(rs.getBigDecimal("FIN_YEAR_ID"));
        year.setFinancialYearName(rs.getString("FIN_YEAR_NME"));
        year.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        year.setStartDate(rs.getDate("START_DTE"));
        year.setEndDate(rs.getDate("END_DTE"));
        year.setStatus(rs.getString("STATUS"));
        return year;
    }

    /**
     * @return the financialYearId
     */
    public BigDecimal getFinancialYearId() {
        return financialYearId;
    }

    /**
     * @param financialYearId the financialYearId to set
     */
    public void setFinancialYearId(BigDecimal financialYearId) {
        this.financialYearId = financialYearId;
    }

    /**
     * @return the financialYearName
     */
    public String getFinancialYearName() {
        return financialYearName;
    }

    /**
     * @param financialYearName the financialYearName to set
     */
    public void setFinancialYearName(String financialYearName) {
        this.financialYearName = financialYearName;
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
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
