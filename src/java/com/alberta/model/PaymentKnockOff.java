/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Faraz1
 */
public class PaymentKnockOff {

    private BigDecimal voucherMasterId;
    private BigDecimal paymentKnockOffId;
    private BigDecimal adjustedVoucherMasterId;
    private BigDecimal invoiceAmount;
    private BigDecimal adjustedAmount;
    private BigDecimal TaxPerc;
    private BigDecimal chequeAmount;
    private BigDecimal taxDebit;
    private BigDecimal taxCredit;
    private BigDecimal subTypeId;
    private BigDecimal financialYearId;
    private BigDecimal siteId;
    private BigDecimal companyId;
    private String voucherNo;
    private String voucherDate;
    private String chequeNo;
    private String chequeDate;
    private String siteName;
    private String financialYearName;
    private String voucherSubTypeDesc;
    private BigDecimal chequeAmnt;
    private BigDecimal taxAmnt;
    private BigDecimal amount;
    private String coaCode;
    private String coaTitle;
    private String projectCode;
    private String projectTitle;

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
     * @return the subTypeId
     */
    public BigDecimal getSubTypeId() {
        return subTypeId;
    }

    /**
     * @param subTypeId the subTypeId to set
     */
    public void setSubTypeId(BigDecimal subTypeId) {
        this.subTypeId = subTypeId;
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
     * @return the voucherNo
     */
    public String getVoucherNo() {
        return voucherNo;
    }

    /**
     * @param voucherNo the voucherNo to set
     */
    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    /**
     * @return the voucherDate
     */
    public String getVoucherDate() {
        return voucherDate;
    }

    /**
     * @param voucherDate the voucherDate to set
     */
    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    /**
     * @return the chequeNo
     */
    public String getChequeNo() {
        return chequeNo;
    }

    /**
     * @param chequeNo the chequeNo to set
     */
    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    /**
     * @return the chequeDate
     */
    public String getChequeDate() {
        return chequeDate;
    }

    /**
     * @param chequeDate the chequeDate to set
     */
    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
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
     * @return the voucherSubTypeDesc
     */
    public String getVoucherSubTypeDesc() {
        return voucherSubTypeDesc;
    }

    /**
     * @param voucherSubTypeDesc the voucherSubTypeDesc to set
     */
    public void setVoucherSubTypeDesc(String voucherSubTypeDesc) {
        this.voucherSubTypeDesc = voucherSubTypeDesc;
    }

    /**
     * @return the chequeAmnt
     */
    public BigDecimal getChequeAmnt() {
        return chequeAmnt;
    }

    /**
     * @param chequeAmnt the chequeAmnt to set
     */
    public void setChequeAmnt(BigDecimal chequeAmnt) {
        this.chequeAmnt = chequeAmnt;
    }

    /**
     * @return the taxAmnt
     */
    public BigDecimal getTaxAmnt() {
        return taxAmnt;
    }

    /**
     * @param taxAmnt the taxAmnt to set
     */
    public void setTaxAmnt(BigDecimal taxAmnt) {
        this.taxAmnt = taxAmnt;
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
     * @return the paymentKnockOffId
     */
    public BigDecimal getPaymentKnockOffId() {
        return paymentKnockOffId;
    }

    /**
     * @param paymentKnockOffId the paymentKnockOffId to set
     */
    public void setPaymentKnockOffId(BigDecimal paymentKnockOffId) {
        this.paymentKnockOffId = paymentKnockOffId;
    }

    /**
     * @return the adjustedVoucherMasterId
     */
    public BigDecimal getAdjustedVoucherMasterId() {
        return adjustedVoucherMasterId;
    }

    /**
     * @param adjustedVoucherMasterId the adjustedVoucherMasterId to set
     */
    public void setAdjustedVoucherMasterId(BigDecimal adjustedVoucherMasterId) {
        this.adjustedVoucherMasterId = adjustedVoucherMasterId;
    }

    /**
     * @return the invoiceAmount
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * @param invoiceAmount the invoiceAmount to set
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * @return the adjustedAmount
     */
    public BigDecimal getAdjustedAmount() {
        return adjustedAmount;
    }

    /**
     * @param adjustedAmount the adjustedAmount to set
     */
    public void setAdjustedAmount(BigDecimal adjustedAmount) {
        this.adjustedAmount = adjustedAmount;
    }

    /**
     * @return the TaxPerc
     */
    public BigDecimal getTaxPerc() {
        return TaxPerc;
    }

    /**
     * @param TaxPerc the TaxPerc to set
     */
    public void setTaxPerc(BigDecimal TaxPerc) {
        this.TaxPerc = TaxPerc;
    }

    /**
     * @return the chequeAmount
     */
    public BigDecimal getChequeAmount() {
        return chequeAmount;
    }

    /**
     * @param chequeAmount the chequeAmount to set
     */
    public void setChequeAmount(BigDecimal chequeAmount) {
        this.chequeAmount = chequeAmount;
    }

    /**
     * @return the taxDebit
     */
    public BigDecimal getTaxDebit() {
        return taxDebit;
    }

    /**
     * @param taxDebit the taxDebit to set
     */
    public void setTaxDebit(BigDecimal taxDebit) {
        this.taxDebit = taxDebit;
    }

    /**
     * @return the taxCredit
     */
    public BigDecimal getTaxCredit() {
        return taxCredit;
    }

    /**
     * @param taxCredit the taxCredit to set
     */
    public void setTaxCredit(BigDecimal taxCredit) {
        this.taxCredit = taxCredit;
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
     * @return the projectTitle
     */
    public String getProjectTitle() {
        return projectTitle;
    }

    /**
     * @param projectTitle the projectTitle to set
     */
    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
}
