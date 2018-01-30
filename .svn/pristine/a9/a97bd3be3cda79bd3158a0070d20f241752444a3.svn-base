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
 * @author Faraz
 */
public class COAType implements Serializable, RowMapper {
    private Integer coaTypeId;
    private String description;

    /**
     * @return the coaTypeId
     */
    public Integer getCoaTypeId() {
        return coaTypeId;
    }

    /**
     * @param coaTypeId the coaTypeId to set
     */
    public void setCoaTypeId(Integer coaTypeId) {
        this.coaTypeId = coaTypeId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        COAType coaType = new COAType();
        coaType.setCoaTypeId(rs.getInt("COA_TYPE_ID"));
        coaType.setDescription(rs.getString("DESCRIPTION"));
        return coaType;
    }
}
