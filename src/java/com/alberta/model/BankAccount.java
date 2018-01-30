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
public class BankAccount implements Serializable, RowMapper {

    private BigDecimal bankAccountId;
    private BigDecimal bankBranchId;
    private String bankBranchCode;
    private String bankBranchName;
    private String accountTitle;
    private String accountNbr;
    private String accountDesc;
    private String coaCode;
    private String coaTitle;
    private BigDecimal runFinLimit;
    private BigDecimal bankAccTypeId;
    private String bankAccTypeName;
    private BigDecimal companyId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountId(rs.getBigDecimal("BANK_ACCOUNT_ID"));
        bankAccount.setBankBranchId(rs.getBigDecimal("BANK_BRANCH_ID"));
        bankAccount.setAccountTitle(rs.getString("ACCOUNT_TITLE"));
        bankAccount.setAccountNbr(rs.getString("ACCOUNT_NBR"));
        bankAccount.setAccountDesc(rs.getString("ACCOUNT_DESC"));
        bankAccount.setCoaCode(rs.getString("COA_CDE"));
        bankAccount.setRunFinLimit(rs.getBigDecimal("RUN_FIN_LIMIT"));
        bankAccount.setBankAccTypeId(rs.getBigDecimal("BANK_ACC_TYP_ID"));
        bankAccount.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return bankAccount;
    }

    /**
     * @return the bankAccountId
     */
    public BigDecimal getBankAccountId() {
        return bankAccountId;
    }

    /**
     * @param bankAccountId the bankAccountId to set
     */
    public void setBankAccountId(BigDecimal bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    /**
     * @return the bankBranchId
     */
    public BigDecimal getBankBranchId() {
        return bankBranchId;
    }

    /**
     * @param bankBranchId the bankBranchId to set
     */
    public void setBankBranchId(BigDecimal bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    /**
     * @return the accountTitle
     */
    public String getAccountTitle() {
        return accountTitle;
    }

    /**
     * @param accountTitle the accountTitle to set
     */
    public void setAccountTitle(String accountTitle) {
        this.accountTitle = accountTitle;
    }

    /**
     * @return the accountNbr
     */
    public String getAccountNbr() {
        return accountNbr;
    }

    /**
     * @param accountNbr the accountNbr to set
     */
    public void setAccountNbr(String accountNbr) {
        this.accountNbr = accountNbr;
    }

    /**
     * @return the accountDesc
     */
    public String getAccountDesc() {
        return accountDesc;
    }

    /**
     * @param accountDesc the accountDesc to set
     */
    public void setAccountDesc(String accountDesc) {
        this.accountDesc = accountDesc;
    }

    /**
     * @return the coaCode
     */
    public String getCoaCode() {
        return coaCode;
    }

    /**
     * @param coaCode the coaCode to set
     */
    public void setCoaCode(String coaCode) {
        this.coaCode = coaCode;
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
     * @return the bankBranchCode
     */
    public String getBankBranchCode() {
        return bankBranchCode;
    }

    /**
     * @param bankBranchCode the bankBranchCode to set
     */
    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    /**
     * @return the bankBranchName
     */
    public String getBankBranchName() {
        return bankBranchName;
    }

    /**
     * @param bankBranchName the bankBranchName to set
     */
    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
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

    /**
     * @return the runFinLimit
     */
    public BigDecimal getRunFinLimit() {
        return runFinLimit;
    }

    /**
     * @param runFinLimit the runFinLimit to set
     */
    public void setRunFinLimit(BigDecimal runFinLimit) {
        this.runFinLimit = runFinLimit;
    }

    /**
     * @return the bankAccTypeId
     */
    public BigDecimal getBankAccTypeId() {
        return bankAccTypeId;
    }

    /**
     * @param bankAccTypeId the bankAccTypeId to set
     */
    public void setBankAccTypeId(BigDecimal bankAccTypeId) {
        this.bankAccTypeId = bankAccTypeId;
    }

    /**
     * @return the bankAccTypeName
     */
    public String getBankAccTypeName() {
        return bankAccTypeName;
    }

    /**
     * @param bankAccTypeName the bankAccTypeName to set
     */
    public void setBankAccTypeName(String bankAccTypeName) {
        this.bankAccTypeName = bankAccTypeName;
    }
}
