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
 * @author abbas
 */
public class BankAccountType implements Serializable, RowMapper {
    private BigDecimal bankAccTypId;
    private String bankAccTypName;
    private BigDecimal companyId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        BankAccountType bankAccTyp = new BankAccountType();
        bankAccTyp.setBankAccTypId(rs.getBigDecimal("BANK_ACC_TYP_ID"));
        bankAccTyp.setBankAccTypName(rs.getString("BANK_ACC_TYP_NME"));
        bankAccTyp.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return bankAccTyp;
    }

    /**
     * @return the bankAccTypId
     */
    public BigDecimal getBankAccTypId() {
        return bankAccTypId;
    }

    /**
     * @param bankAccTypId the bankAccTypId to set
     */
    public void setBankAccTypId(BigDecimal bankAccTypId) {
        this.bankAccTypId = bankAccTypId;
    }

    /**
     * @return the bankAccTypName
     */
    public String getBankAccTypName() {
        return bankAccTypName;
    }

    /**
     * @param bankAccTypName the bankAccTypName to set
     */
    public void setBankAccTypName(String bankAccTypName) {
        this.bankAccTypName = bankAccTypName;
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
