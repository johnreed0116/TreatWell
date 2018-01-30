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
public class ReconciliationAttachment implements Serializable, RowMapper {

    private BigDecimal id;
    private BigDecimal reconciliationId;
    private String fileName;
    private String desc;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        ReconciliationAttachment att = null;
        try {
            att = new ReconciliationAttachment();
            att.setFileName(rs.getString("FILE_NME"));
            att.setDesc(rs.getString("FILE_DESC"));
            att.setReconciliationId(rs.getBigDecimal("DAILY_SHIFT_RECON_ID"));
            att.setId(rs.getBigDecimal("DAILY_SHIFT_RECON_ATT_ID"));

        } catch (Exception ex) {
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
     * @return the reconciliationId
     */
    public BigDecimal getReconciliationId() {
        return reconciliationId;
    }

    /**
     * @param reconciliationId the reconciliationId to set
     */
    public void setReconciliationId(BigDecimal reconciliationId) {
        this.reconciliationId = reconciliationId;
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
}
