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
public class VoucherMaster implements Serializable, RowMapper {

    private BigDecimal voucherMasterId;
    private BigDecimal subTypeId;
    private BigDecimal financialYearId;
    private BigDecimal siteId;
    private BigDecimal companyId;
    private BigDecimal businessUnitId;
    private String businessUnitName;
    private String voucherNo;
    private String voucherDate;
    private String chequeNo;
    private String chequeDate;
    private String chequeFavour;
    private String narration;
    private String preparedBy;
    private Date preparedDate;
    private String postInd;
    private String postedBy;
    private Date postedDate;
    private Date voucherDte;
    private Date chqDate;
    private Date chqClearanceDate;
    private String siteName;
    private String financialYearName;
    private String voucherSubTypeDesc;
    private String postedRight;
    private String unpostedRight;
    private String cancelledBy;
    private Date cancelledDate;
    private BigDecimal refVoucherMasterId;
    private BigDecimal chequeAmnt;
    private String chequePrintInd;
    private BigDecimal chequePrintCount;
    private String chequeStatus;
    private String paymentMode;
    private String postPending;
    private String dueDate;
    private String supplierCode;
    private String billNo;
    private String billDate;
    private String userName;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        VoucherMaster vm = new VoucherMaster();
        vm.setVoucherMasterId(rs.getBigDecimal("VOUCHER_MASTER_ID"));
        vm.setSubTypeId(rs.getBigDecimal("VOUCHER_SUB_TYP_ID"));
        vm.setFinancialYearId(rs.getBigDecimal("FIN_YEAR_ID"));
        vm.setSiteId(rs.getBigDecimal("SITE_ID"));
        vm.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        vm.setVoucherNo(rs.getString("VOUCHER_NBR"));
        vm.setVoucherDte(rs.getDate("VOUCHER_DTE"));
        vm.setChequeNo(rs.getString("CHEQUE_NBR"));
        vm.setChqDate(rs.getDate("CHEQUE_DTE"));
        vm.setChequeFavour(rs.getString("CHEQUE_FAVOUR"));
        vm.setNarration(rs.getString("NARRATION"));
        vm.setPreparedBy(rs.getString("PREPARED_BY"));
        vm.setPreparedDate(rs.getDate("PREPARED_DTE"));
        vm.setPostInd(rs.getString("POSTED_IND"));
        vm.setPostedDate(rs.getDate("POSTED_DTE"));
        return vm;
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
     * @return the chequeFavour
     */
    public String getChequeFavour() {
        return chequeFavour;
    }

    /**
     * @param chequeFavour the chequeFavour to set
     */
    public void setChequeFavour(String chequeFavour) {
        this.chequeFavour = chequeFavour;
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
     * @return the preparedBy
     */
    public String getPreparedBy() {
        return preparedBy;
    }

    /**
     * @param preparedBy the preparedBy to set
     */
    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    /**
     * @return the preparedDate
     */
    public Date getPreparedDate() {
        return preparedDate;
    }

    /**
     * @param preparedDate the preparedDate to set
     */
    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    /**
     * @return the postInd
     */
    public String getPostInd() {
        return postInd;
    }

    /**
     * @param postInd the postInd to set
     */
    public void setPostInd(String postInd) {
        this.postInd = postInd;
    }

    /**
     * @return the postedDate
     */
    public Date getPostedDate() {
        return postedDate;
    }

    /**
     * @param postedDate the postedDate to set
     */
    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    /**
     * @return the voucherDte
     */
    public Date getVoucherDte() {
        return voucherDte;
    }

    /**
     * @param voucherDte the voucherDte to set
     */
    public void setVoucherDte(Date voucherDte) {
        this.voucherDte = voucherDte;
    }

    /**
     * @return the chqDate
     */
    public Date getChqDate() {
        return chqDate;
    }

    /**
     * @param chqDate the chqDate to set
     */
    public void setChqDate(Date chqDate) {
        this.chqDate = chqDate;
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
     * @return the postedRight
     */
    public String getPostedRight() {
        return postedRight;
    }

    /**
     * @param postedRight the postedRight to set
     */
    public void setPostedRight(String postedRight) {
        this.postedRight = postedRight;
    }

    /**
     * @return the unpostedRight
     */
    public String getUnpostedRight() {
        return unpostedRight;
    }

    /**
     * @param unpostedRight the unpostedRight to set
     */
    public void setUnpostedRight(String unpostedRight) {
        this.unpostedRight = unpostedRight;
    }

    /**
     * @return the cancelledBy
     */
    public String getCancelledBy() {
        return cancelledBy;
    }

    /**
     * @param cancelledBy the cancelledBy to set
     */
    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    /**
     * @return the refVoucherMasterId
     */
    public BigDecimal getRefVoucherMasterId() {
        return refVoucherMasterId;
    }

    /**
     * @param refVoucherMasterId the refVoucherMasterId to set
     */
    public void setRefVoucherMasterId(BigDecimal refVoucherMasterId) {
        this.refVoucherMasterId = refVoucherMasterId;
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
     * @return the chequePrintInd
     */
    public String getChequePrintInd() {
        return chequePrintInd;
    }

    /**
     * @param chequePrintInd the chequePrintInd to set
     */
    public void setChequePrintInd(String chequePrintInd) {
        this.chequePrintInd = chequePrintInd;
    }

    /**
     * @return the chequePrintCount
     */
    public BigDecimal getChequePrintCount() {
        return chequePrintCount;
    }

    /**
     * @param chequePrintCount the chequePrintCount to set
     */
    public void setChequePrintCount(BigDecimal chequePrintCount) {
        this.chequePrintCount = chequePrintCount;
    }

    /**
     * @return the postedBy
     */
    public String getPostedBy() {
        return postedBy;
    }

    /**
     * @param postedBy the postedBy to set
     */
    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    /**
     * @return the chqClearanceDate
     */
    public Date getChqClearanceDate() {
        return chqClearanceDate;
    }

    /**
     * @param chqClearanceDate the chqClearanceDate to set
     */
    public void setChqClearanceDate(Date chqClearanceDate) {
        this.chqClearanceDate = chqClearanceDate;
    }

    /**
     * @return the chequeStatus
     */
    public String getChequeStatus() {
        return chequeStatus;
    }

    /**
     * @param chequeStatus the chequeStatus to set
     */
    public void setChequeStatus(String chequeStatus) {
        this.chequeStatus = chequeStatus;
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
     * @return the cancelledDate
     */
    public Date getCancelledDate() {
        return cancelledDate;
    }

    /**
     * @param cancelledDate the cancelledDate to set
     */
    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    /**
     * @return the postPending
     */
    public String getPostPending() {
        return postPending;
    }

    /**
     * @param postPending the postPending to set
     */
    public void setPostPending(String postPending) {
        this.postPending = postPending;
    }

    /**
     * @return the dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the businessUnitId
     */
    public BigDecimal getBusinessUnitId() {
        return businessUnitId;
    }

    /**
     * @param businessUnitId the businessUnitId to set
     */
    public void setBusinessUnitId(BigDecimal businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    /**
     * @return the businessUnitName
     */
    public String getBusinessUnitName() {
        return businessUnitName;
    }

    /**
     * @param businessUnitName the businessUnitName to set
     */
    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    /**
     * @return the supplierCode
     */
    public String getSupplierCode() {
        return supplierCode;
    }

    /**
     * @param supplierCode the supplierCode to set
     */
    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
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
}
