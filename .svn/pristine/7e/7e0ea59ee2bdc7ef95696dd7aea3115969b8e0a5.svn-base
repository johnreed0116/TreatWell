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
public class Rights implements Serializable, RowMapper {

    private String rightName;
    private BigDecimal parentId;
    private String targetUrl;
    private BigDecimal sort;
    private String rightText;
    private String target;
    private BigDecimal rightId;
    private String canAdd;
    private String canEdit;
    private String canDelete;
    private String iconeName;
    private String bgColor;

    public Rights() {
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public BigDecimal getParentId() {
        return parentId;
    }

    public void setParentId(BigDecimal parentId) {
        this.parentId = parentId;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targerUrl) {
        this.targetUrl = targerUrl;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Rights rights = new Rights();
        rights.setRightName(rs.getString("RIGHT_NME"));
        rights.setTargetUrl(rs.getString("URL"));
        rights.setParentId(rs.getBigDecimal("PARENT_ID"));
        rights.setSort(rs.getBigDecimal("SORT"));
        rights.setRightText(rs.getString("RIGHT_TXT"));
        rights.setRightId(rs.getBigDecimal("RIGHT_ID"));
        rights.setIconeName(rs.getString("ICON_NME"));
       // rights.setBgColor(rs.getString("BG_COLOR"));
        return rights;
    }

    /**
     * @return the rightText
     */
    public String getRightText() {
        return rightText;
    }

    /**
     * @param rightText the rightText to set
     */
    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    /**
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return the rightId
     */
    public BigDecimal getRightId() {
        return rightId;
    }

    /**
     * @param rightId the rightId to set
     */
    public void setRightId(BigDecimal rightId) {
        this.rightId = rightId;
    }

    /**
     * @return the canAdd
     */
    public String getCanAdd() {
        return canAdd;
    }

    /**
     * @param canAdd the canAdd to set
     */
    public void setCanAdd(String canAdd) {
        this.canAdd = canAdd;
    }

    /**
     * @return the canEdit
     */
    public String getCanEdit() {
        return canEdit;
    }

    /**
     * @param canEdit the canEdit to set
     */
    public void setCanEdit(String canEdit) {
        this.canEdit = canEdit;
    }

    /**
     * @return the canDelete
     */
    public String getCanDelete() {
        return canDelete;
    }

    /**
     * @param canDelete the canDelete to set
     */
    public void setCanDelete(String canDelete) {
        this.canDelete = canDelete;
    }

    /**
     * @return the iconeName
     */
    public String getIconeName() {
        return iconeName;
    }

    /**
     * @param iconeName the iconeName to set
     */
    public void setIconeName(String iconeName) {
        this.iconeName = iconeName;
    }

    /**
     * @return the bgColor
     */
    public String getBgColor() {
        return bgColor;
    }

    /**
     * @param bgColor the bgColor to set
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}
