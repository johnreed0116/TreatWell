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
public class GraphVO implements Serializable, RowMapper {

    private String voucherDate;
    private BigDecimal purchaseCount;
    private BigDecimal saleCount;
    private String sourceCol;
    private BigDecimal perc;
    private BigDecimal amount;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        GraphVO vo = new GraphVO();
        vo.setVoucherDate(rs.getString("VOUCHER_DTE"));
        vo.setPurchaseCount(rs.getBigDecimal("PUR_QTY"));
        vo.setSaleCount(rs.getBigDecimal("SAL_QTY"));
        return vo;
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
     * @return the purchaseCount
     */
    public BigDecimal getPurchaseCount() {
        return purchaseCount;
    }

    /**
     * @param purchaseCount the purchaseCount to set
     */
    public void setPurchaseCount(BigDecimal purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    /**
     * @return the saleCount
     */
    public BigDecimal getSaleCount() {
        return saleCount;
    }

    /**
     * @param saleCount the saleCount to set
     */
    public void setSaleCount(BigDecimal saleCount) {
        this.saleCount = saleCount;
    }

    /**
     * @return the perc
     */
    public BigDecimal getPerc() {
        return perc;
    }

    /**
     * @param perc the perc to set
     */
    public void setPerc(BigDecimal perc) {
        this.perc = perc;
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
     * @return the sourceCol
     */
    public String getSourceCol() {
        return sourceCol;
    }

    /**
     * @param sourceCol the sourceCol to set
     */
    public void setSourceCol(String sourceCol) {
        this.sourceCol = sourceCol;
    }
}
