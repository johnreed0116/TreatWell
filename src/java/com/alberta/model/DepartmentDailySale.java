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
 * @author abbas
 */
public class DepartmentDailySale implements Serializable, RowMapper {

    private BigDecimal deptDailySaleId;
    private BigDecimal finYearId;
    private BigDecimal siteId;
    private String siteTitle;
    private String coaCode;
    private String coaTitle;
    private String monthName;
    private Date voucherDate;
    private String dated;
    private BigDecimal openingQty;
    private BigDecimal closingQty;
    private BigDecimal purchaseQty;
    private BigDecimal returnQty;
    private BigDecimal saleQty;
    private BigDecimal companyId;
    private String supplierRef;
    private String comments;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        DepartmentDailySale dds = new DepartmentDailySale();
        dds.setDeptDailySaleId(rs.getBigDecimal("DEPT_DAILY_SALE_ID"));
        dds.setFinYearId(rs.getBigDecimal("FIN_YEAR_ID"));
        dds.setSiteId(rs.getBigDecimal("SITE_ID"));
        dds.setCoaCode(rs.getString("COA_CDE"));
        dds.setMonthName(rs.getString("MONTH_NME"));
        dds.setVoucherDate((java.util.Date) rs.getDate("VOUCHER_DTE"));
        dds.setPurchaseQty(rs.getBigDecimal("PUR_QTY"));
        dds.setSaleQty(rs.getBigDecimal("SAL_QTY"));
        dds.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return dds;
    }

    /**
     * @return the deptDailySaleId
     */
    public BigDecimal getDeptDailySaleId() {
        return deptDailySaleId;
    }

    /**
     * @param deptDailySaleId the deptDailySaleId to set
     */
    public void setDeptDailySaleId(BigDecimal deptDailySaleId) {
        this.deptDailySaleId = deptDailySaleId;
    }

    /**
     * @return the finYearId
     */
    public BigDecimal getFinYearId() {
        return finYearId;
    }

    /**
     * @param finYearId the finYearId to set
     */
    public void setFinYearId(BigDecimal finYearId) {
        this.finYearId = finYearId;
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
     * @return the siteTitle
     */
    public String getSiteTitle() {
        return siteTitle;
    }

    /**
     * @param siteTitle the siteTitle to set
     */
    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
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
     * @return the voucherDate
     */
    public Date getVoucherDate() {
        return voucherDate;
    }

    /**
     * @param voucherDate the voucherDate to set
     */
    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    /**
     * @return the dated
     */
    public String getDated() {
        return dated;
    }

    /**
     * @param dated the dated to set
     */
    public void setDated(String dated) {
        this.dated = dated;
    }

    /**
     * @return the openingQty
     */
    public BigDecimal getOpeningQty() {
        return openingQty;
    }

    /**
     * @param openingQty the openingQty to set
     */
    public void setOpeningQty(BigDecimal openingQty) {
        this.openingQty = openingQty;
    }

    /**
     * @return the closingQty
     */
    public BigDecimal getClosingQty() {
        return closingQty;
    }

    /**
     * @param closingQty the closingQty to set
     */
    public void setClosingQty(BigDecimal closingQty) {
        this.closingQty = closingQty;
    }

    /**
     * @return the purchaseQty
     */
    public BigDecimal getPurchaseQty() {
        return purchaseQty;
    }

    /**
     * @param purchaseQty the purchaseQty to set
     */
    public void setPurchaseQty(BigDecimal purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    /**
     * @return the saleQty
     */
    public BigDecimal getSaleQty() {
        return saleQty;
    }

    /**
     * @param saleQty the saleQty to set
     */
    public void setSaleQty(BigDecimal saleQty) {
        this.saleQty = saleQty;
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
     * @return the monthName
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * @param monthName the monthName to set
     */
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    /**
     * @return the returnQty
     */
    public BigDecimal getReturnQty() {
        return returnQty;
    }

    /**
     * @param returnQty the returnQty to set
     */
    public void setReturnQty(BigDecimal returnQty) {
        this.returnQty = returnQty;
    }

    /**
     * @return the supplierRef
     */
    public String getSupplierRef() {
        return supplierRef;
    }

    /**
     * @param supplierRef the supplierRef to set
     */
    public void setSupplierRef(String supplierRef) {
        this.supplierRef = supplierRef;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
}
