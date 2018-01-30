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
public class BankBranch implements Serializable, RowMapper {

    private BigDecimal branchId;
    private String branchCode;
    private String branchName;
    private String branchManager;
    private String address;
    private String bankName;
    private String city;
    private BigDecimal bankId;
    private BigDecimal cityId;
    private BigDecimal companyId;
    private String mobileNbr;
    private String phoneNbr;
    private String faxNbr;    

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        BankBranch branch = new BankBranch();
        branch.setBankId(rs.getBigDecimal("BANK_ID"));
        branch.setBranchId(rs.getBigDecimal("BANK_BRANCH_ID"));
        branch.setCityId(rs.getBigDecimal("CITY_ID"));
        branch.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        branch.setBranchCode(rs.getString("BRANCH_CDE"));
        branch.setBranchManager(rs.getString("BRANCH_MGR"));
        branch.setAddress(rs.getString("ADDRESS_TXT"));
        branch.setCity(rs.getString("CITY_NME"));
        branch.setBankName(rs.getString("BANK_NME"));
        branch.setBranchName(rs.getString("BRANCH_NME"));
        branch.setMobileNbr(rs.getString("MOBILE_NBR"));
        branch.setPhoneNbr(rs.getString("PHONE_NBR"));
        branch.setFaxNbr(rs.getString("FAX_NBR"));        
        return branch;
    }

    /**
     * @return the branchId
     */
    public BigDecimal getBranchId() {
        return branchId;
    }

    /**
     * @param branchId the branchId to set
     */
    public void setBranchId(BigDecimal branchId) {
        this.branchId = branchId;
    }

    /**
     * @return the branchCode
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * @param branchCode the branchCode to set
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * @return the branchManager
     */
    public String getBranchManager() {
        return branchManager;
    }

    /**
     * @param branchManager the branchManager to set
     */
    public void setBranchManager(String branchManager) {
        this.branchManager = branchManager;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return the cityId
     */
    public BigDecimal getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
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
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName the branchName to set
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * @return the mobileNbr
     */
    public String getMobileNbr() {
        return mobileNbr;
    }

    /**
     * @param mobileNbr the mobileNbr to set
     */
    public void setMobileNbr(String mobileNbr) {
        this.mobileNbr = mobileNbr;
    }

    /**
     * @return the phoneNbr
     */
    public String getPhoneNbr() {
        return phoneNbr;
    }

    /**
     * @param phoneNbr the phoneNbr to set
     */
    public void setPhoneNbr(String phoneNbr) {
        this.phoneNbr = phoneNbr;
    }

    /**
     * @return the faxNbr
     */
    public String getFaxNbr() {
        return faxNbr;
    }

    /**
     * @param faxNbr the faxNbr to set
     */
    public void setFaxNbr(String faxNbr) {
        this.faxNbr = faxNbr;
    }
}
