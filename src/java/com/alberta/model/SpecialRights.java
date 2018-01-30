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
public class SpecialRights implements Serializable, RowMapper {

    private String userName;
    private String rightName;
    private String rightDesc;
    private BigDecimal rightId;

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        SpecialRights sp = new SpecialRights();
        sp.setRightId(rs.getBigDecimal("SPECIAL_RIGHTS_ID"));
        sp.setRightName(rs.getString("SPECIAL_RIGHT_NME"));
       // sp.setRightDesc(rs.getString("RIGHT_DESC"));
        return sp;
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

    /**
     * @return the rightName
     */
    public String getRightName() {
        return rightName;
    }

    /**
     * @param rightName the rightName to set
     */
    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    /**
     * @return the rightDesc
     */
    public String getRightDesc() {
        return rightDesc;
    }

    /**
     * @param rightDesc the rightDesc to set
     */
    public void setRightDesc(String rightDesc) {
        this.rightDesc = rightDesc;
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
}
