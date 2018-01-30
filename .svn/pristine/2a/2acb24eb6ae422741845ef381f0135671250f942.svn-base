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
public class VoucherDetail implements Serializable, RowMapper {

    private BigDecimal voucherDetailId;
    private BigDecimal voucherMasterId;
    private String projectCode;
    private String costCenterCode;
    private String coaCode;
    private String lineNarration;
    private BigDecimal amount;
    private BigDecimal taxId;
    private String taxRate;
    private BigDecimal taxAmount;
    private BigDecimal refDetailId;
    private String chequeClearanceDate;
    private String projectName;
    private String coaTitle;
    private String bankCoa;
    private String bankTitle;
    private String costCenterTitle;
    private String taxCoa;
    private String taxTitle;
    private String accountType;
    private String siteName;
    private String financialYearName;
    private String creditAmount;
    private String debitAmount;
    private String billNo;
    private String billDate;
    private java.util.Date billDte;
    private BigDecimal rate;
    private BigDecimal quantity;
    private String purchaseUnit;
    private String saleUnit;
    private BigDecimal costPrice;
    private BigDecimal retailPrice;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        VoucherDetail vd = new VoucherDetail();
        vd.setVoucherDetailId(rs.getBigDecimal("VOUCHER_DETAIL_ID"));
        vd.setVoucherMasterId(rs.getBigDecimal("VOUCHER_MASTER_ID"));
        vd.setProjectCode(rs.getString("PROJECT_CDE"));
        vd.setCostCenterCode(rs.getString("COST_CENTER_CDE"));
        vd.setCoaCode(rs.getString("COA_CDE"));
        vd.setLineNarration(rs.getString("LINE_NARRATION"));
        vd.setChequeClearanceDate(rs.getString("CHQ_CLEARANCE_DTE"));
        vd.setAmount(rs.getBigDecimal("AMNT"));
        vd.setTaxId(rs.getBigDecimal("TAX_ID"));
        vd.setRefDetailId(rs.getBigDecimal("REF_VOUCHER_DETAIL_ID"));
        return vd;
    }

    /**
     * @return the voucherDetailId
     */
    public BigDecimal getVoucherDetailId() {
        return voucherDetailId;
    }

    /**
     * @param voucherDetailId the voucherDetailId to set
     */
    public void setVoucherDetailId(BigDecimal voucherDetailId) {
        this.voucherDetailId = voucherDetailId;
    }

    /**
     * @return the voucherMasterId
     */
    public BigDecimal getVoucherMasterId() {
        return voucherMasterId;
    }

    /**
     * @param voucherMasterId the voucherMasterId to set
     */
    public void setVoucherMasterId(BigDecimal voucherMasterId) {
        this.voucherMasterId = voucherMasterId;
    }

    /**
     * @return the costCenterCode
     */
    public String getCostCenterCode() {
        return costCenterCode;
    }

    /**
     * @param costCenterCode the costCenterCode to set
     */
    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
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
     * @return the lineNarration
     */
    public String getLineNarration() {
        return lineNarration;
    }

    /**
     * @param lineNarration the lineNarration to set
     */
    public void setLineNarration(String lineNarration) {
        this.lineNarration = lineNarration;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the taxId
     */
    public BigDecimal getTaxId() {
        return taxId;
    }

    /**
     * @param taxId the taxId to set
     */
    public void setTaxId(BigDecimal taxId) {
        this.taxId = taxId;
    }

    /**
     * @return the refDetailId
     */
    public BigDecimal getRefDetailId() {
        return refDetailId;
    }

    /**
     * @param refDetailId the refDetailId to set
     */
    public void setRefDetailId(BigDecimal refDetailId) {
        this.refDetailId = refDetailId;
    }

    /**
     * @return the chequeClearanceDate
     */
    public String getChequeClearanceDate() {
        return chequeClearanceDate;
    }

    /**
     * @param chequeClearanceDate the chequeClearanceDate to set
     */
    public void setChequeClearanceDate(String chequeClearanceDate) {
        this.chequeClearanceDate = chequeClearanceDate;
    }

    /**
     * @return the projectCode
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * @param projectCode the projectCode to set
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * @return the taxRate
     */
    public String getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
     * @return the bankCoa
     */
    public String getBankCoa() {
        return bankCoa;
    }

    /**
     * @param bankCoa the bankCoa to set
     */
    public void setBankCoa(String bankCoa) {
        this.bankCoa = bankCoa;
    }

    /**
     * @return the bankTitle
     */
    public String getBankTitle() {
        return bankTitle;
    }

    /**
     * @param bankTitle the bankTitle to set
     */
    public void setBankTitle(String bankTitle) {
        this.bankTitle = bankTitle;
    }

    /**
     * @return the costCenterTitle
     */
    public String getCostCenterTitle() {
        return costCenterTitle;
    }

    /**
     * @param costCenterTitle the costCenterTitle to set
     */
    public void setCostCenterTitle(String costCenterTitle) {
        this.costCenterTitle = costCenterTitle;
    }

    /**
     * @return the taxCoa
     */
    public String getTaxCoa() {
        return taxCoa;
    }

    /**
     * @param taxCoa the taxCoa to set
     */
    public void setTaxCoa(String taxCoa) {
        this.taxCoa = taxCoa;
    }

    /**
     * @return the taxTitle
     */
    public String getTaxTitle() {
        return taxTitle;
    }

    /**
     * @param taxTitle the taxTitle to set
     */
    public void setTaxTitle(String taxTitle) {
        this.taxTitle = taxTitle;
    }

    /**
     * @return the accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * @return the taxAmount
     */
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    /**
     * @param taxAmount the taxAmount to set
     */
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * @return the siteName
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName the siteName to set
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
     * @return the creditAmount
     */
    public String getCreditAmount() {
        return creditAmount;
    }

    /**
     * @param creditAmount the creditAmount to set
     */
    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    /**
     * @return the debitAmount
     */
    public String getDebitAmount() {
        return debitAmount;
    }

    /**
     * @param debitAmount the debitAmount to set
     */
    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
    }

    /**
     * @return the billNo
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * @param billNo the billNo to set
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * @return the billDate
     */
    public String getBillDate() {
        return billDate;
    }

    /**
     * @param billDate the billDate to set
     */
    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    /**
     * @return the billDte
     */
    public java.util.Date getBillDte() {
        return billDte;
    }

    /**
     * @param billDte the billDte to set
     */
    public void setBillDte(java.util.Date billDte) {
        this.billDte = billDte;
    }

    /**
     * @return the rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * @return the quantity
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the purchaseUnit
     */
    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    /**
     * @param purchaseUnit the purchaseUnit to set
     */
    public void setPurchaseUnit(String purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    /**
     * @return the saleUnit
     */
    public String getSaleUnit() {
        return saleUnit;
    }

    /**
     * @param saleUnit the saleUnit to set
     */
    public void setSaleUnit(String saleUnit) {
        this.saleUnit = saleUnit;
    }

    /**
     * @return the costPrice
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * @param costPrice the costPrice to set
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * @return the retailPrice
     */
    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    /**
     * @param retailPrice the retailPrice to set
     */
    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }
}
