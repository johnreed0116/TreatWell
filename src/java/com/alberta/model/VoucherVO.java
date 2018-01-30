/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Faraz
 */
public class VoucherVO {

    private MultipartFile[] file;
    private String[] fileDesc;
    private String voucherType;
    private String voucherDate;
    private String financialYearId;
    private String siteId;
    private String narration;
    private String accountCode;
    private String chequeNo;
    private String chequeDate;
    private String chequeFavor;
    private String creaditAmount;
    private String bankNarration;
    private String[] coaCode;
    private String[] projectCode;
    private String[] costCenter;
    private String[] lineNarration;
    private String[] debit;
    private String[] credit;
    private String[] tax;
    private String[] taxRate;
    private String[] billNo;
    private String[] billDate;
    private String paymentMode;
    private String accountCodeFrom;
    private String accountCodeTo;
    private String amount;
    private String companyId;
    private String userName;
    private String businessUnitId;
    private String id;
    private String address;

    private String path;
    private String masterId;
    private String attachmentType;

    /**
     * @return the file
     */
    public MultipartFile[] getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile[] file) {
        this.file = file;
    }

    /**
     * @return the fileDesc
     */
    public String[] getFileDesc() {
        return fileDesc;
    }

    /**
     * @param fileDesc the fileDesc to set
     */
    public void setFileDesc(String[] fileDesc) {
        this.fileDesc = fileDesc;
    }

    /**
     * @return the voucherType
     */
    public String getVoucherType() {
        return voucherType;
    }

    /**
     * @param voucherType the voucherType to set
     */
    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
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
     * @return the financialYearId
     */
    public String getFinancialYearId() {
        return financialYearId;
    }

    /**
     * @param financialYearId the financialYearId to set
     */
    public void setFinancialYearId(String financialYearId) {
        this.financialYearId = financialYearId;
    }

    /**
     * @return the siteId
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    /**
     * @return the narration
     */
    public String getNarration() {
        return narration;
    }

    /**
     * @param narration the narration to set
     */
    public void setNarration(String narration) {
        this.narration = narration;
    }

    /**
     * @return the accountCode
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * @param accountCode the accountCode to set
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
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
     * @return the chequeFavor
     */
    public String getChequeFavor() {
        return chequeFavor;
    }

    /**
     * @param chequeFavor the chequeFavor to set
     */
    public void setChequeFavor(String chequeFavor) {
        this.chequeFavor = chequeFavor;
    }

    /**
     * @return the creaditAmount
     */
    public String getCreaditAmount() {
        return creaditAmount;
    }

    /**
     * @param creaditAmount the creaditAmount to set
     */
    public void setCreaditAmount(String creaditAmount) {
        this.creaditAmount = creaditAmount;
    }

    /**
     * @return the bankNarration
     */
    public String getBankNarration() {
        return bankNarration;
    }

    /**
     * @param bankNarration the bankNarration to set
     */
    public void setBankNarration(String bankNarration) {
        this.bankNarration = bankNarration;
    }

    /**
     * @return the coaCode
     */
    public String[] getCoaCode() {
        return coaCode;
    }

    /**
     * @param coaCode the coaCode to set
     */
    public void setCoaCode(String[] coaCode) {
        this.coaCode = coaCode;
    }

    /**
     * @return the projectCode
     */
    public String[] getProjectCode() {
        return projectCode;
    }

    /**
     * @param projectCode the projectCode to set
     */
    public void setProjectCode(String[] projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * @return the costCenter
     */
    public String[] getCostCenter() {
        return costCenter;
    }

    /**
     * @param costCenter the costCenter to set
     */
    public void setCostCenter(String[] costCenter) {
        this.costCenter = costCenter;
    }

    /**
     * @return the lineNarration
     */
    public String[] getLineNarration() {
        return lineNarration;
    }

    /**
     * @param lineNarration the lineNarration to set
     */
    public void setLineNarration(String[] lineNarration) {
        this.lineNarration = lineNarration;
    }

    /**
     * @return the debit
     */
    public String[] getDebit() {
        return debit;
    }

    /**
     * @param debit the debit to set
     */
    public void setDebit(String[] debit) {
        this.debit = debit;
    }

    /**
     * @return the tax
     */
    public String[] getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(String[] tax) {
        this.tax = tax;
    }

    /**
     * @return the taxRate
     */
    public String[] getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(String[] taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * @return the credit
     */
    public String[] getCredit() {
        return credit;
    }

    /**
     * @param credit the credit to set
     */
    public void setCredit(String[] credit) {
        this.credit = credit;
    }

    /**
     * @return the billNo
     */
    public String[] getBillNo() {
        return billNo;
    }

    /**
     * @param billNo the billNo to set
     */
    public void setBillNo(String[] billNo) {
        this.billNo = billNo;
    }

    /**
     * @return the billDate
     */
    public String[] getBillDate() {
        return billDate;
    }

    /**
     * @param billDate the billDate to set
     */
    public void setBillDate(String[] billDate) {
        this.billDate = billDate;
    }

    /**
     * @return the paymentMode
     */
    public String getPaymentMode() {
        return paymentMode;
    }

    /**
     * @param paymentMode the paymentMode to set
     */
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     * @return the accountCodeFrom
     */
    public String getAccountCodeFrom() {
        return accountCodeFrom;
    }

    /**
     * @param accountCodeFrom the accountCodeFrom to set
     */
    public void setAccountCodeFrom(String accountCodeFrom) {
        this.accountCodeFrom = accountCodeFrom;
    }

    /**
     * @return the accountCodeTo
     */
    public String getAccountCodeTo() {
        return accountCodeTo;
    }

    /**
     * @param accountCodeTo the accountCodeTo to set
     */
    public void setAccountCodeTo(String accountCodeTo) {
        this.accountCodeTo = accountCodeTo;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the businessUnitId
     */
    public String getBusinessUnitId() {
        return businessUnitId;
    }

    /**
     * @param businessUnitId the businessUnitId to set
     */
    public void setBusinessUnitId(String businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the masterId
     */
    public String getMasterId() {
        return masterId;
    }

    /**
     * @param masterId the masterId to set
     */
    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the attachmentType
     */
    public String getAttachmentType() {
        return attachmentType;
    }

    /**
     * @param attachmentType the attachmentType to set
     */
    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }
}
