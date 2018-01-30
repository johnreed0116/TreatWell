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
public class ChequePrintStatus implements Serializable, RowMapper {

    private BigDecimal voucherMasterId;
    private String statusInd;
    private BigDecimal printCount;
    private BigDecimal companyId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        ChequePrintStatus chqPrintStatus = new ChequePrintStatus();
        chqPrintStatus.setVoucherMasterId(rs.getBigDecimal("VOUCHER_MASTER_ID"));
        chqPrintStatus.setStatusInd(rs.getString("STATUS_IND"));
        chqPrintStatus.setPrintCount(rs.getBigDecimal("PRINT_COUNT"));
        chqPrintStatus.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return chqPrintStatus;
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
     * @return the statusInd
     */
    public String getStatusInd() {
        return statusInd;
    }

    /**
     * @param statusInd the statusInd to set
     */
    public void setStatusInd(String statusInd) {
        this.statusInd = statusInd;
    }

    /**
     * @return the printCount
     */
    public BigDecimal getPrintCount() {
        return printCount;
    }

    /**
     * @param printCount the printCount to set
     */
    public void setPrintCount(BigDecimal printCount) {
        this.printCount = printCount;
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
