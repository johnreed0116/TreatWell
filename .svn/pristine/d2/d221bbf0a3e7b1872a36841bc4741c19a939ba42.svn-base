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
 * @author Administrator
 */
public class PaymentMode implements Serializable, RowMapper {

    private BigDecimal paymentModeId;
    private String paymentModeName;
    private String paymentModeAbbrev;
    private BigDecimal companyId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        PaymentMode pm = new PaymentMode();
        pm.setPaymentModeId(rs.getBigDecimal("PAYMENT_MODE_ID"));
        pm.setPaymentModeName(rs.getString("PAYMENT_MODE_NME"));
        pm.setPaymentModeAbbrev(rs.getString("PAYMENT_MODE_ABBREV"));
        pm.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        return pm;
    }

    /**
     * @return the paymentModeId
     */
    public BigDecimal getPaymentModeId() {
        return paymentModeId;
    }

    /**
     * @param paymentModeId the paymentModeId to set
     */
    public void setPaymentModeId(BigDecimal paymentModeId) {
        this.paymentModeId = paymentModeId;
    }

    /**
     * @return the paymentModeName
     */
    public String getPaymentModeName() {
        return paymentModeName;
    }

    /**
     * @param paymentModeName the paymentModeName to set
     */
    public void setPaymentModeName(String paymentModeName) {
        this.paymentModeName = paymentModeName;
    }

    /**
     * @return the paymentModeAbbrev
     */
    public String getPaymentModeAbbrev() {
        return paymentModeAbbrev;
    }

    /**
     * @param paymentModeAbbrev the paymentModeAbbrev to set
     */
    public void setPaymentModeAbbrev(String paymentModeAbbrev) {
        this.paymentModeAbbrev = paymentModeAbbrev;
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
