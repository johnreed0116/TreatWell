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
public class ChequeTemplateDetail implements Serializable, RowMapper {

    private BigDecimal chqTempDetId;
    private BigDecimal chqTempMstrId;
    private String fieldNme;
    private BigDecimal fieldSize;
    private BigDecimal fieldWidth;
    private BigDecimal fieldX;
    private BigDecimal fieldY;
    private String fieldPrefix;
    private String fieldPostfix;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        ChequeTemplateDetail chqDetail = new ChequeTemplateDetail();
        chqDetail.setChqTempDetId(rs.getBigDecimal("CHQ_TEMP_DET_ID"));
        chqDetail.setChqTempMstrId(rs.getBigDecimal("CHQ_TEMP_MSTR_ID"));
        chqDetail.setFieldNme(rs.getString("FIELD_NME"));
        chqDetail.setFieldSize(rs.getBigDecimal("FIELD_SIZE"));
        chqDetail.setFieldWidth(rs.getBigDecimal("FIELD_WIDTH"));
        chqDetail.setFieldX(rs.getBigDecimal("FIELD_XAXIS"));
        chqDetail.setFieldY(rs.getBigDecimal("FIELD_YAXIS"));
        chqDetail.setFieldPrefix(rs.getString("FIELD_PREFIX"));
        chqDetail.setFieldPostfix(rs.getString("FIELD_POSTFIX"));
        return chqDetail;
    }

    /**
     * @return the chqTempDetId
     */
    public BigDecimal getChqTempDetId() {
        return chqTempDetId;
    }

    /**
     * @param chqTempDetId the chqTempDetId to set
     */
    public void setChqTempDetId(BigDecimal chqTempDetId) {
        this.chqTempDetId = chqTempDetId;
    }

    /**
     * @return the chqTempMstrId
     */
    public BigDecimal getChqTempMstrId() {
        return chqTempMstrId;
    }

    /**
     * @param chqTempMstrId the chqTempMstrId to set
     */
    public void setChqTempMstrId(BigDecimal chqTempMstrId) {
        this.chqTempMstrId = chqTempMstrId;
    }

    /**
     * @return the fieldNme
     */
    public String getFieldNme() {
        return fieldNme;
    }

    /**
     * @param fieldNme the fieldNme to set
     */
    public void setFieldNme(String fieldNme) {
        this.fieldNme = fieldNme;
    }

    /**
     * @return the fieldSize
     */
    public BigDecimal getFieldSize() {
        return fieldSize;
    }

    /**
     * @param fieldSize the fieldSize to set
     */
    public void setFieldSize(BigDecimal fieldSize) {
        this.fieldSize = fieldSize;
    }

    /**
     * @return the fieldWidth
     */
    public BigDecimal getFieldWidth() {
        return fieldWidth;
    }

    /**
     * @param fieldWidth the fieldWidth to set
     */
    public void setFieldWidth(BigDecimal fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    /**
     * @return the fieldX
     */
    public BigDecimal getFieldX() {
        return fieldX;
    }

    /**
     * @param fieldX the fieldX to set
     */
    public void setFieldX(BigDecimal fieldX) {
        this.fieldX = fieldX;
    }

    /**
     * @return the fieldY
     */
    public BigDecimal getFieldY() {
        return fieldY;
    }

    /**
     * @param fieldY the fieldY to set
     */
    public void setFieldY(BigDecimal fieldY) {
        this.fieldY = fieldY;
    }

    /**
     * @return the fieldPrefix
     */
    public String getFieldPrefix() {
        return fieldPrefix;
    }

    /**
     * @param fieldPrefix the fieldPrefix to set
     */
    public void setFieldPrefix(String fieldPrefix) {
        this.fieldPrefix = fieldPrefix;
    }

    /**
     * @return the fieldPostfix
     */
    public String getFieldPostfix() {
        return fieldPostfix;
    }

    /**
     * @param fieldPostfix the fieldPostfix to set
     */
    public void setFieldPostfix(String fieldPostfix) {
        this.fieldPostfix = fieldPostfix;
    }
}
