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
public class VoucherAttachment implements Serializable, RowMapper {

    private BigDecimal id;
    private BigDecimal voucherMasterId;
    private String fileName;
    private String desc;
    private String userName;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        VoucherAttachment att = null;
        try {
            att = new VoucherAttachment();
            att.setFileName(rs.getString("FILE_NME"));
            att.setDesc(rs.getString("FILE_DESC"));
            att.setVoucherMasterId(rs.getBigDecimal("VOUCHER_MASTER_ID"));
            att.setId(rs.getBigDecimal("VOUCHER_ATTACHMENT_ID"));
            att.setUserName(rs.getString("PREPARED_BY"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return att;
    }

    /**
     * @return the id
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(BigDecimal id) {
        this.id = id;
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
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
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
