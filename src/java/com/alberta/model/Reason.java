/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author abbas
 */
public class Reason implements Serializable, RowMapper {

    private String reasonId;
    private String title;
    private String abbrev;
    private String companyId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Reason reason = new Reason();
        reason.setReasonId(rs.getString("REASONS_ID"));
        reason.setTitle(rs.getString("TITLE"));
        reason.setAbbrev(rs.getString("ABBREVATION"));
        reason.setCompanyId(rs.getString("COMPANY_ID"));
        return reason;
    }

    /**
     * @return the reasonId
     */
    public String getReasonId() {
        return reasonId;
    }

    /**
     * @param reasonId the reasonId to set
     */
    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the abbrev
     */
    public String getAbbrev() {
        return abbrev;
    }

    /**
     * @param abbrev the abbrev to set
     */
    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }
}
