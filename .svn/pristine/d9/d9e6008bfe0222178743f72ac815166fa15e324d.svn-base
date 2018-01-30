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
public class ReportColumnsMaster implements Serializable, RowMapper {
    
    private BigDecimal reportColumnsMasterId;   
    private BigDecimal reportTemplateId;
    private String columnName;
    private String sourceTxt;
    private String groupName;
    private BigDecimal sort;
    private BigDecimal companyId;    
    
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        ReportColumnsMaster rcm = new ReportColumnsMaster();
        rcm.setReportColumnsMasterId(rs.getBigDecimal("REPORT_COLUMNS_MASTER_ID"));
        rcm.setReportTemplateId(rs.getBigDecimal("REPORT_TEMPLATE_ID"));
        rcm.setColumnName(rs.getString("COLUMN_NME"));
        rcm.setSourceTxt(rs.getString("SOURCE_TXT"));
        rcm.setGroupName(rs.getString("GROUP_NME"));
        rcm.setSort(rs.getBigDecimal("SORT"));
        rcm.setCompanyId(rs.getBigDecimal("COMPANY_ID"));
        
        return rcm;
    }

    /**
     * @return the reportColumnsMasterId
     */
    public BigDecimal getReportColumnsMasterId() {
        return reportColumnsMasterId;
    }

    /**
     * @param reportColumnsMasterId the reportColumnsMasterId to set
     */
    public void setReportColumnsMasterId(BigDecimal reportColumnsMasterId) {
        this.reportColumnsMasterId = reportColumnsMasterId;
    }

    /**
     * @return the reportTemplateId
     */
    public BigDecimal getReportTemplateId() {
        return reportTemplateId;
    }

    /**
     * @param reportTemplateId the reportTemplateId to set
     */
    public void setReportTemplateId(BigDecimal reportTemplateId) {
        this.reportTemplateId = reportTemplateId;
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the sourceTxt
     */
    public String getSourceTxt() {
        return sourceTxt;
    }

    /**
     * @param sourceTxt the sourceTxt to set
     */
    public void setSourceTxt(String sourceTxt) {
        this.sourceTxt = sourceTxt;
    }

    /**
     * @return the sort
     */
    public BigDecimal getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(BigDecimal sort) {
        this.sort = sort;
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
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
