/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Faraz
 */
public class Bank implements Serializable, RowMapper {

    private BigDecimal bankId;
    private String bankName;
    private String bankAbbrev;
    private BigDecimal companyId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Bank bank = new Bank();
        bank.setBankId(rs.getBigDecimal("BANK_ID"));
        bank.setBankName(rs.getString("BANK_NME"));
        bank.setBankAbbrev(rs.getString("BANK_ABBREV"));
        bank.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return bank;
    }

    /**
     * @return the bankId
     */
    public BigDecimal getBankId() {
        return bankId;
    }

    /**
     * @param bankId the bankId to set
     */
    public void setBankId(BigDecimal bankId) {
        this.bankId = bankId;
    }

    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return the bankAbbrev
     */
    public String getBankAbbrev() {
        return bankAbbrev;
    }

    /**
     * @param bankAbbrev the bankAbbrev to set
     */
    public void setBankAbbrev(String bankAbbrev) {
        this.bankAbbrev = bankAbbrev;
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
