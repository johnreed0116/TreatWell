/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.login;

import com.alberta.dao.DAO;
import com.alberta.model.Encryption;
import com.alberta.model.Rights;
import com.alberta.model.User;
import com.alberta.utility.MD5;
import com.alberta.utility.Util;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Faraz
 */
public class LoginServiceImpl implements LoginService {

    private DAO dao;

    /**
     * @return the dao
     */
    @Override
    public DAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    @Override
    public void setDao(DAO dao) {
        this.dao = dao;
    }

    @Override
    public User verifyLogin(String userName, String password) {
        User user = null;
        try {
            List<Map> list = this.getDao().getData("SELECT WU.* FROM TW_WEB_USERS WU "
                    + " WHERE UPPER(WU.USER_NME)='" + userName.toUpperCase() + "' "
                    + " AND WU.USER_PASSWORD='" + password + "' AND WU.ACTIVE_IND='Y' ");
            if (list != null && list.size() > 0) {
                user = new User();
                Map map = list.get(0);
                user.setUsername(map.get("USER_NME") != null ? map.get("USER_NME").toString() : "");
                user.setFirstName(map.get("FIRST_NME") != null ? map.get("FIRST_NME").toString() : "");
                user.setEmail(map.get("EMAIL") != null ? map.get("EMAIL").toString() : "");
                user.setDoctorId(map.get("TW_DOCTOR_ID") != null ? map.get("TW_DOCTOR_ID").toString() : "");
                user.setPatientId(map.get("TW_PATIENT_ID") != null ? map.get("TW_PATIENT_ID").toString() : "");
                user.setPharmaId(map.get("TW_PHARMACEUTICAL_ID") != null ? map.get("TW_PHARMACEUTICAL_ID").toString() : "");
                user.setMedicalPharmacyId(map.get("TW_PHARMACY_ID") != null ? map.get("TW_PHARMACY_ID").toString() : "");
                user.setMedicalStoreId(map.get("TW_PHARMACY_STORE_ID") != null ? map.get("TW_PHARMACY_STORE_ID").toString() : "");
                user.setLabMasterId(map.get("TW_LAB_MASTER_ID") != null ? map.get("TW_LAB_MASTER_ID").toString() : "");
                user.setLabDetailId(map.get("TW_LAB_DETAIL_ID") != null ? map.get("TW_LAB_DETAIL_ID").toString() : "");
                user.setClinicId(map.get("TW_CLINIC_ID") != null ? map.get("TW_CLINIC_ID").toString() : "");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public String insertUserSession(String userName, String ip, String action, String id) {
        boolean flag = false;
        String sessionId = "";
        try {
            String query = "";
            if (action != null && action.equalsIgnoreCase("LogIn")) {
                List list = this.getDao().getJdbcTemplate().queryForList("SELECT SEQ_USER_SESSION_ID.NEXTVAL SESSIONID FROM DUAL");
                if (list != null && list.size() > 0) {
                    Map map = (Map) list.get(0);
                    sessionId = map.get("SESSIONID").toString();
                }
                query = "INSERT INTO USER_SESSION(USER_SESSION_ID,LOGIN_TIME,IP_ADDRESS,USER_NME) "
                        + " VALUES(" + sessionId + ",SYSDATE,'" + ip + "','" + userName + "') ";
            } else if (id != null && !id.isEmpty()) {
                query = "UPDATE USER_SESSION SET LOGOUT_TIME=SYSDATE WHERE USER_SESSION_ID=" + id + "";
            } //                query = "INSERT INTO USER_SESSION(USER_SESSION_ID,LOGOUT_TIME,IP_ADDRESS,USER_NME) VALUES"
            //                        + "(SEQ_USER_SESSION_ID.NEXTVAL,SYSDATE,'" + ip + "','" + userName + "')";

            int i = this.getDao().getJdbcTemplate().update(query);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (flag) {
            return sessionId;
        } else {
            return "";
        }
    }

    @Override
//    public List<Rights> getParentMenu(String userName, String moduleId) {
//        List<Rights> list = null;
//        try {
//            String canView = "";
//            if (!userName.equalsIgnoreCase("super_user")) {
//                canView = " AND RI.CAN_VIEW='Y' ";
//            }
//            String query = "SELECT DISTINCT RI.RIGHT_ID,RI.RIGHT_NME,RI.PARENT_ID,RI.URL,RI.SORT,RI.RIGHT_TXT "
//                    + " FROM RIGHTS RI WHERE RI.RIGHT_ID IN (SELECT DISTINCT RGH.PARENT_ID "
//                    + "     FROM WEB_USERS U,RIGHTS RGH,ROLE ROL,USER_ROLE UR,ROLE_RIGHTS RR "
//                    + "     WHERE U.USER_NME=UR.USER_NME "
//                    + "         AND UR.ROLE_ID=ROL.ROLE_ID "
//                    + "         AND ROL.ROLE_ID=RR.ROLE_ID"
//                    + "         AND RR.RIGHT_NME=RGH.RIGHT_NME AND RR.MODULE_ID=RGH.MODULE_ID "
//                    + "         AND RGH.PARENT_ID <>0 "
//                    + "         AND UPPER(U.USER_NME)='" + userName.toUpperCase() + "'  " + canView + " "
//                    + "         AND RGH.MODULE_ID=" + moduleId + ") ORDER BY RI.SORT ";
//            list = this.getDao().getJdbcTemplate().query(query, new Rights());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

    public List<Rights> getParentMenu(String userName, String moduleId) {
        List<Rights> list = null;
        try {
            String canView = "";
            if (!userName.equalsIgnoreCase("super_user")) {
                canView = " AND RGH.CAN_VIEW='Y' ";
            }
            String query = "SELECT DISTINCT RI.RIGHT_ID,RI.RIGHT_NME,RI.PARENT_ID,RI.URL,RI.SORT,RI.RIGHT_TXT, "
                    + " RI.ICON_NME,RI.BG_COLOR FROM RIGHTS RI "
                    + " WHERE RI.RIGHT_ID IN (SELECT DISTINCT RGH.PARENT_ID "
                    + " FROM WEB_USERS U,RIGHTS RGH,ROLE ROL,USER_ROLE UR,ROLE_RIGHTS RR "
                    + " WHERE U.USER_NME=UR.USER_NME "
                    + " AND UR.ROLE_ID=ROL.ROLE_ID "
                    + " AND ROL.ROLE_ID=RR.ROLE_ID "
                    + " AND RR.RIGHT_NME=RGH.RIGHT_NME "
                    + " AND RGH.PARENT_ID<>0 AND UR.MODULE_ID=RGH.MODULE_ID "
                    + " AND UPPER(U.USER_NME)='" + userName.toUpperCase() + "' " + canView + " "
                    + " AND RGH.MODULE_ID=" + moduleId + ")"
                    + " AND RI.PARENT_ID=0 "
                    + " ORDER BY RI.SORT ";

            list = this.getDao().getJdbcTemplate().query(query, new Rights());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getParentMenu() {
        List<Map> list = null;
        try {
            String query = "SELECT DISTINCT RI.RIGHT_ID,RI.RIGHT_NME,RI.PARENT_ID,RI.URL,RI.SORT,RI.RIGHT_TXT, "
                    + " RI.ICON_NME FROM TW_RIGHTS RI "
                    + " WHERE RI.PARENT_ID=0 "
                    + " ORDER BY RI.SORT ";

            list = this.getDao().getData(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getChildMenu() {
        List<Map> list = null;
        try {
            String query = "SELECT DISTINCT RI.RIGHT_ID,RI.RIGHT_NME,RI.PARENT_ID,RI.URL,RI.SORT,RI.RIGHT_TXT, "
                    + " RI.ICON_NME FROM TW_RIGHTS RI "
                    + " WHERE RI.PARENT_ID<>0 "
                    + " ORDER BY RI.SORT  ";

            list = this.getDao().getData(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Rights> getRightsByParentId(String userName, String moduleId, String parentId) {
        List<Rights> list = null;
        try {
            String canView = "";
            if (!userName.equalsIgnoreCase("super_user")) {
                canView = " AND RI.CAN_VIEW='Y' ";
            }
            String query = "SELECT DISTINCT RI.RIGHT_ID,RI.RIGHT_NME,RI.PARENT_ID,RI.URL,RI.SORT,RI.RIGHT_TXT, "
                    + " RI.ICON_NME,RI.BG_COLOR "
                    + " FROM WEB_USERS U,RIGHTS RI,ROLE ROL,USER_ROLE UR,ROLE_RIGHTS RR "
                    + " WHERE U.USER_NME=UR.USER_NME "
                    + " AND UR.ROLE_ID=ROL.ROLE_ID "
                    + " AND ROL.ROLE_ID=RR.ROLE_ID "
                    + " AND RR.RIGHT_NME=RI.RIGHT_NME AND RR.MODULE_ID=RI.MODULE_ID "
                    + " AND UPPER(U.USER_NME)='" + userName.toUpperCase() + "' " + canView + " "
                    + " AND RI.MODULE_ID=" + moduleId + " "
                    + " AND RI.PARENT_ID=" + parentId + " "
                    + " ORDER BY RI.SORT ";
            list = this.getDao().getJdbcTemplate().query(query, new Rights());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Rights> getUserRights(String userName, String password, String moduleId) {
        List<Rights> list = null;
        try {
            String canView = "";
            if (!userName.equalsIgnoreCase("super_user")) {
                canView = " AND RI.CAN_VIEW='Y' ";
            }
            String query = "SELECT DISTINCT RI.RIGHT_ID,RI.RIGHT_NME,RI.PARENT_ID,RI.URL,RI.SORT,RI.RIGHT_TXT "
                    + " FROM WEB_USERS U,RIGHTS RI,ROLE ROL,USER_ROLE UR,ROLE_RIGHTS RR "
                    + " WHERE U.USER_NME=UR.USER_NME "
                    + "     AND UR.ROLE_ID=ROL.ROLE_ID "
                    + "     AND ROL.ROLE_ID=RR.ROLE_ID "
                    + "     AND RR.RIGHT_NME=RI.RIGHT_NME AND RR.MODULE_ID=RI.MODULE_ID "
                    + "     AND UPPER(U.USER_NME)='" + userName.toUpperCase() + "' " + canView + " "
                    + "     AND U.USER_PASSWORD='" + password + "' AND RI.MODULE_ID=" + moduleId + " ORDER BY RI.SORT ";

            list = this.getDao().getJdbcTemplate().query(query, new Rights());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        boolean flag = false;
        try {
            Encryption pswdSec = new Encryption();
            newPassword = pswdSec.encrypt(newPassword);
            String query = "UPDATE TW_WEB_USERS SET USER_PASSWORD='" + newPassword + "' WHERE UPPER(USER_NME)='" + userName.toUpperCase() + "'  ";
            int i = this.dao.getJdbcTemplate().update(query);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getDoctorClinics(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT * FROM TW_CLINIC ORDER BY CLINIC_NME";
            list = this.getDao().getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDashBoardDataForDoctors(String doctorId, String clinicId) {
        List<Map> list = null;
        try {
            String query = "SELECT COUNT(TW_PATIENT_ID) TOTAL,'REGISTERED_PATIENT' TITLE FROM TW_PATIENT"
                    + " UNION ALL"
                    + " SELECT COUNT(TW_PATIENT_ID) TOTAL,'TOTAL_CHECKED' TITLE "
                    + " FROM TW_PRESCRIPTION_MASTER "
                    + " WHERE TW_DOCTOR_ID=" + doctorId + " AND TW_CLINIC_ID=" + clinicId + " "
                    + " UNION ALL"
                    + " SELECT COUNT(TW_PRESCRIPTION_DETAIL_ID) TOTAL,'TOTAL_RECOMMANDED' TITLE"
                    + " FROM TW_PRESCRIPTION_DETAIL WHERE TW_LAB_TEST_ID IS NOT NULL AND TW_PRESCRIPTION_MASTER_ID IN(SELECT TW_PRESCRIPTION_MASTER_ID FROM TW_PRESCRIPTION_MASTER"
                    + " WHERE TW_DOCTOR_ID=" + doctorId + " AND TW_CLINIC_ID=" + clinicId + ") "
                    + " UNION ALL"
                    + " SELECT COUNT(TW_APPOINTMENT_ID) TOTAL,'TOTAL_APPOINTMENTS' TITLE "
                    + " FROM TW_APPOINTMENT "
                    + " WHERE TW_DOCTOR_ID=" + doctorId + ""
                    + " AND TW_CLINIC_ID= " + clinicId + ""
                    + " AND  APPOINTMENT_DTE=TO_DATE(TO_CHAR(SYSDATE,'DD-MM-YYYY'),'DD-MM-YYYY') "
                    + " AND STATUS_IND IN ('P','A')";
            list = this.getDao().getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getCollectedFeeForDoctorsByMonth(String doctorId, String clinicId) {
        List<Map> list = null;
        try {
            String query = " SELECT EXTRACT(MONTH FROM DTE) MONTH,SUM(AMNT) TOTAL FROM EZIMEDIC.TW_COLLECTED_FEE "
                    + " WHERE TW_APPOINTMENT_ID IN (SELECT TW_APPOINTMENT_ID FROM TW_APPOINTMENT"
                    + " WHERE TW_DOCTOR_ID=" + doctorId + " AND TW_CLINIC_ID=" + clinicId + " AND TO_CHAR(APPOINTMENT_DTE,'YYYY')=TO_CHAR(SYSDATE,'YYYY'))"
                    + " GROUP BY EXTRACT(month FROM DTE)";
            list = this.getDao().getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getDashBoardDataForPatient(String patient) {
        List<Map> list = null;
        try {
            String query = "SELECT TO_CHAR(MIN(APPOINTMENT_DTE),'DD-MON-YY') NEXT_APP,0 AS TOTAL,'NEXT_APPOINTMENT' TITLE "
                    + " FROM TW_APPOINTMENT WHERE TW_PATIENT_ID=" + patient + " AND APPOINTMENT_DTE>=TO_DATE(TO_CHAR(SYSDATE,'DD-MM-YYYY'),'DD-MM-YYYY')"
                    + " GROUP BY TW_PATIENT_ID"
                    + " UNION ALL"
                    + " SELECT NULL AS NEXT_APP, COUNT(*) TOTAL,'TOTAL_PRESC' TITLE "
                    + " FROM TW_PRESCRIPTION_MASTER WHERE TW_PATIENT_ID=" + patient + "";
            list = this.getDao().getData(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean resetPassword(String mobileNo) {
        boolean flag = false;
        try {
            List<Map> list = this.getDao().getData("SELECT USER_NME FROM TW_WEB_USERS "
                    + " WHERE (USER_NME='" + mobileNo + "' OR TW_DOCTOR_ID IN (SELECT TW_DOCTOR_ID FROM TW_DOCTOR WHERE MOBILE_NO='" + mobileNo + "'))"
                    + " AND ACTIVE_IND='Y'");
            if (list != null && list.size() > 0) {
                Map map = list.get(0);
                if (!map.get("USER_NME").toString().isEmpty()) {
                    MD5 md = new MD5();
                    String password = Util.generatePassword();
                    String mdStr = md.calcMD5(password);
                    Encryption pswdSec = new Encryption();
                    String generatedPassword = pswdSec.encrypt(mdStr);
                    String updateQuery = "UPDATE TW_WEB_USERS SET USER_PASSWORD='" + generatedPassword + "' WHERE USER_NME='" + map.get("USER_NME").toString() + "'";
                    int num = this.dao.getJdbcTemplate().update(updateQuery);
                    if (num > 0) {
                        flag = true;
                        this.sentResetPassword(mobileNo, map.get("USER_NME").toString(), password);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    private boolean sentResetPassword(String mobileNo, String userName, String password) throws IOException {
        boolean flag = false;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String message = "Your password has been reset for ezimedic. Please login  by entering: UserName: " + userName + " Password: " + password + "";
            String url = "http://pk.eocean.us/APIManagement/API/RequestAPI?user=TWS&pwd=ANreowHdVt%2fbvT6ubUCK01SuOXWcxjM5H2QOUH1MUdnBh1fhqiq4kWFJjPctIAFSlA%3d%3d&sender=TWS&response=string";
            HttpGet httpGet = new HttpGet(url);
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("reciever", mobileNo));
            nvps.add(new BasicNameValuePair("msg-data", message));
            URI uri = new URIBuilder(httpGet.getURI()).addParameters(nvps).build();
            httpGet.setURI(uri);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpclient.close();
        }
        return flag;
    }
}
