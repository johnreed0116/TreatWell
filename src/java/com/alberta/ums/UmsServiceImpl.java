package com.alberta.ums;

import com.alberta.dao.DAO;
import com.alberta.model.*;
import com.alberta.utility.Util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Faraz
 */
public class UmsServiceImpl implements UmsService {

    private DAO dao;

    @Override
    public DAO getDao() {
        return dao;
    }

    @Override
    public void setDao(DAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean editUser(User user) {
        boolean flag = false;
        try {
            if (user != null && user.getUsername() != null && !user.getUsername().isEmpty()) {
                String query = "";
                Encryption pswdSec = new Encryption();
                String password = user.getPassword();
                password = pswdSec.encrypt(password);
                if (user.getUsername() != null) {
                    query = "UPDATE WEB_USERS SET USER_NME='" + user.getUsername() + "',USER_PASSWORD='" + password + "',ACTIVE_IND='" + user.getActive() + "' "
                            + " WHERE USER_NME='" + user.getUsername() + "'";
                }
                int i = this.getDao().getJdbcTemplate().update(query);
                if (i > 0) {
                    flag = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getAllUsers(String accountType) {
        List<Map> list = null;
        String query = null;
        try {
            if (accountType != null && accountType.equalsIgnoreCase("Doctor")) {
                query = "SELECT WB.USER_NME,WB.ACTIVE_IND,WB.FIRST_NME,WB.EMAIL,"
                        + "WB.TW_DOCTOR_ID,WB.TW_PATIENT_ID,DR.DOCTOR_NME"
                        + " FROM TW_WEB_USERS WB,TW_DOCTOR DR"
                        + " WHERE WB.TW_DOCTOR_ID=DR.TW_DOCTOR_ID";
            } else if (accountType != null && accountType.equalsIgnoreCase("Patient")) {
                query = "SELECT WB.USER_NME,WB.ACTIVE_IND,WB.FIRST_NME,WB.EMAIL,"
                        + " WB.TW_DOCTOR_ID,WB.TW_PATIENT_ID,PA.PATIENT_NME "
                        + " FROM TW_WEB_USERS WB,TW_PATIENT PA"
                        + " WHERE WB.TW_PATIENT_ID=PA.TW_PATIENT_ID";
            } else if (accountType != null && accountType.equalsIgnoreCase("Pharmaceutical")) {
                query = "SELECT WB.USER_NME,WB.ACTIVE_IND,WB.FIRST_NME,WB.EMAIL,"
                        + " WB.TW_DOCTOR_ID,WB.TW_PATIENT_ID,WB.TW_PHARMACEUTICAL_ID,PA.COMPANY_NME"
                        + " FROM TW_WEB_USERS WB,TW_PHARMACEUTICAL PA"
                        + " WHERE WB.TW_PHARMACEUTICAL_ID=PA.TW_PHARMACEUTICAL_ID";
            } else if (accountType != null && accountType.equalsIgnoreCase("Lab")) {
                query = "SELECT WB.USER_NME,WB.ACTIVE_IND,WB.FIRST_NME,WB.EMAIL,"
                        + " WB.TW_DOCTOR_ID,WB.TW_PATIENT_ID"
                        + " FROM TW_WEB_USERS WB,TW_LAB_DETAIL HP"
                        + " WHERE WB.TW_LAB_DETAIL_ID=HP.TW_LAB_DETAIL_ID";
            } else if (accountType != null && accountType.equalsIgnoreCase("Pharmacy")) {
                query = "SELECT WB.USER_NME,WB.ACTIVE_IND,WB.FIRST_NME,WB.EMAIL,"
                        + " WB.TW_DOCTOR_ID,WB.TW_PATIENT_ID"
                        + " FROM TW_WEB_USERS WB,TW_PHARMACY_STORE HP"
                        + " WHERE WB.TW_PHARMACY_STORE_ID=HP.TW_PHARMACY_STORE_ID";
            } else if (accountType != null && accountType.equalsIgnoreCase("CLINIC")) {
                query = "SELECT WB.USER_NME,WB.ACTIVE_IND,WB.FIRST_NME,WB.EMAIL,WB.TW_CLINIC_ID,"
                        + " HP.CLINIC_NME"
                        + " FROM TW_WEB_USERS WB,TW_CLINIC HP"
                        + " WHERE WB.TW_CLINIC_ID=HP.TW_CLINIC_ID";
            } else if (accountType != null && accountType.equalsIgnoreCase("Admin")) {
                query = "SELECT USER_NME,ACTIVE_IND,FIRST_NME,EMAIL,"
                        + " TW_DOCTOR_ID,TW_PATIENT_ID,TW_PHARMACEUTICAL_ID,TW_CLINIC_ID"
                        + " FROM TW_WEB_USERS"
                        + " WHERE TW_DOCTOR_ID IS NULL"
                        + " AND TW_PATIENT_ID IS NULL"
                        + " AND TW_CLINIC_ID IS NULL"
                        + " AND TW_PHARMACEUTICAL_ID IS NULL"
                        + " AND TW_PHARMA_RAP_ID IS NULL"
                        + " AND TW_LAB_MASTER_ID IS NULL"
                        + " AND TW_PHARMACY_ID IS NULL";
            } else {
                query = "SELECT USER_NME,ACTIVE_IND,FIRST_NME,EMAIL,"
                        + " TW_DOCTOR_ID,TW_PATIENT_ID,TW_CLINIC_ID,"
                        + " TW_PHARMACEUTICAL_ID,"
                        + " FROM TW_WEB_USERS"
                        + " WHERE TW_PATIENT_ID IS NULL";
            }

            list = this.getDao().getData(query + " ORDER BY USER_NME");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getUserForDoctor(String doctorId) {
        List<Map> list = null;
        try {
            String query = "SELECT WB.USER_NME,WB.ACTIVE_IND,WB.FIRST_NME,WB.EMAIL,"
                    + "WB.TW_DOCTOR_ID,WB.TW_PATIENT_ID,DR.DOCTOR_NME"
                    + " FROM TW_WEB_USERS WB,TW_DOCTOR DR"
                    + " WHERE WB.TW_DOCTOR_ID=DR.TW_DOCTOR_ID"
                    + " AND WB.TW_DOCTOR_ID=" + doctorId + "";
            list = this.getDao().getData(query + " ORDER BY WB.FIRST_NME");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getUserForPharma(String pharmaId) {
        List<Map> list = null;
        try {
            String query = "SELECT WB.USER_NME,WB.ACTIVE_IND,WB.FIRST_NME,WB.EMAIL,"
                    + "WB.TW_PHARMACEUTICAL_ID,DR.COMPANY_NME"
                    + " FROM TW_WEB_USERS WB,TW_PHARMACEUTICAL DR"
                    + " WHERE WB.TW_PHARMACEUTICAL_ID=DR.TW_PHARMACEUTICAL_ID"
                    + " AND WB.TW_PHARMACEUTICAL_ID=" + pharmaId + "";
            list = this.getDao().getData(query + " ORDER BY WB.FIRST_NME");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getUserForLab(String detailId) {
        List<Map> list = null;
        try {
            String query = "SELECT WB.USER_NME,WB.ACTIVE_IND,WB.FIRST_NME,WB.EMAIL,"
                    + "WB.TW_LAB_DETAIL_ID,DR.CENTER_NME COMPANY_NME"
                    + " FROM TW_WEB_USERS WB,TW_LAB_DETAIL DR"
                    + " WHERE WB.TW_LAB_DETAIL_ID=DR.TW_LAB_DETAIL_ID"
                    + " AND WB.TW_LAB_DETAIL_ID=" + detailId + "";
            list = this.getDao().getData(query + " ORDER BY WB.FIRST_NME");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getUserForClinic(String clinicId) {
        List<Map> list = null;
        try {
            String query = "SELECT WB.USER_NME,WB.ACTIVE_IND,WB.FIRST_NME,WB.EMAIL,"
                    + " WB.TW_CLINIC_ID,DR.CLINIC_NME COMPANY_NME"
                    + " FROM TW_WEB_USERS WB,TW_CLINIC DR"
                    + " WHERE WB.TW_CLINIC_ID=DR.TW_CLINIC_ID"
                    + " AND WB.TW_CLINIC_ID=" + clinicId + "";
            list = this.getDao().getData(query + " ORDER BY WB.FIRST_NME");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map> getUserForPharmacy(String pharmacyId) {
        List<Map> list = null;
        try {
            String query = "SELECT WB.USER_NME,WB.ACTIVE_IND,WB.FIRST_NME,WB.EMAIL,"
                    + " WB.TW_PHARMACY_STORE_ID,DR.STORE_NME COMPANY_NME"
                    + " FROM TW_WEB_USERS WB,TW_PHARMACY_STORE DR"
                    + " WHERE WB.TW_PHARMACY_STORE_ID=DR.TW_PHARMACY_STORE_ID"
                    + " AND WB.TW_PHARMACY_STORE_ID=" + pharmacyId + "";
            list = this.getDao().getData(query + " ORDER BY WB.FIRST_NME");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public User getUserById(String id) {
        User user = null;
        try {
            List list = this.getDao().getJdbcTemplate().query("SELECT * FROM WEB_USERS WHERE USER_NME='" + id + "'", new User());
            if (list != null && list.size() > 0) {
                user = (User) list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean saveRole(Role role, String companyId, String moduleId) {
        boolean flag = false;
        try {
            if (role != null && role.getRoleName() != null && role.getRoleName().length() > 0) {
                String query = "";
                if (role.getRoleId() != null && role.getRoleId() > 0) {
                    query = "UPDATE ROLE SET ROLE_NME='" + role.getRoleName().toUpperCase() + "', ROLE_DESC='" + role.getRoleDesc() + "' "
                            + " WHERE ROLE_ID=" + role.getRoleId() + "";
                } else {
                    query = "INSERT INTO ROLE(ROLE_ID,ROLE_NME,ROLE_DESC,COMPANY_ID,MODULE_ID) "
                            + " VALUES(SEQ_ROLE_ID.NEXTVAL,'" + role.getRoleName().toUpperCase() + "','" + role.getRoleDesc() + "'," + companyId + "," + moduleId + ")";
                }
                int i = this.getDao().getJdbcTemplate().update(query);
                if (i > 0) {
                    flag = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map> getAllRoles() {
        List<Map> map = null;
        try {
            map = this.dao.getData("SELECT * FROM TW_ROLE  "
                    + " ORDER BY TW_ROLE_ID");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public Role getRoleById(String id) {
        Role role = null;
        try {
            List list = this.dao.getJdbcTemplate().query("SELECT * FROM ROLE WHERE ROLE_ID=" + id + " ORDER BY ROLE_ID ", new Role());
            if (list != null && list.size() > 0) {
                role = (Role) list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return role;
    }

    @Override
    public boolean assignRights(String selectedUser, List<Rights> rightIds, String userName) {
        boolean flag = false;
        try {
            List<String> queryList = new ArrayList();
            String query = "DELETE FROM TW_USER_RIGHT WHERE UPPER(USER_NME)='" + selectedUser.toUpperCase() + "'";
            queryList.add(query);
            for (int i = 0; i < rightIds.size(); i++) {
                Rights r = (Rights) rightIds.get(i);
                query = "INSERT INTO TW_USER_RIGHT(TW_USER_RIGHT_ID,USER_NME,RIGHT_NME,CAN_ADD,CAN_EDIT,CAN_DELETE) "
                        + " VALUES (SEQ_TW_USER_RIGHT_ID.NEXTVAL,'" + selectedUser + "','" + r.getRightName() + "','" + r.getCanAdd() + "', "
                        + " '" + r.getCanEdit() + "','" + r.getCanDelete() + "')";
                queryList.add(query);
            }
            flag = this.dao.insertAll(queryList, userName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean assignRoleRights(String roleId, List<Rights> rightIds, String userName) {
        boolean flag = false;
        try {
            List<String> queryList = new ArrayList();
            String query = "DELETE FROM TW_ROLE_RIGHTS WHERE TW_ROLE_ID=" + roleId + "";
            queryList.add(query);
            for (int i = 0; i < rightIds.size(); i++) {
                Rights r = (Rights) rightIds.get(i);
                query = "INSERT INTO TW_ROLE_RIGHTS(TW_ROLE_RIGHTS_ID,TW_ROLE_ID,RIGHT_NME) "
                        + " VALUES (SEQ_TW_ROLE_RIGHTS_ID.NEXTVAL," + roleId + ",'" + r.getRightName() + "')";
                queryList.add(query);
            }
            flag = this.dao.insertAll(queryList, userName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Rights> getRightsForAdmin() {
        List<Rights> list = null;
        try {
            String query = "SELECT R.* FROM TW_RIGHTS R"
                    + " ORDER BY R.SORT";
            list = this.getDao().getJdbcTemplate().query(query, new Rights());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Rights> getRightsForNonAdminUsers(String userName) {
        List<Rights> list = null;
        try {
            String query = "SELECT R.* FROM TW_RIGHTS R,TW_USER_RIGHT UR"
                    + " WHERE R.RIGHT_NME=UR.RIGHT_NME"
                    + " AND UPPER(UR.USER_NME)='" + userName.toUpperCase() + "'"
                    + " ORDER BY R.SORT";
            list = this.getDao().getJdbcTemplate().query(query, new Rights());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Rights> getAllRights(String userName, String moduleId) {
        List<Rights> list = null;
        try {

            String query = "SELECT R.* FROM TW_RIGHTS R,TW_USER_RIGHT UR"
                    + " WHERE R.RIGHT_NME=UR.RIGHT_NME"
                    + " AND UPPER(UR.USER_NME)='" + userName.toUpperCase() + "'"
                    + " ORDER BY R.SORT";
            list = this.getDao().getJdbcTemplate().query(query, new Rights());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Rights> getParentRightsForAdmin() {
        List<Rights> list = new ArrayList();
        try {
            String query = "SELECT * FROM TW_RIGHTS WHERE  PARENT_ID=0 ORDER BY SORT ";
            list = this.getDao().getJdbcTemplate().query(query, new Rights());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Rights> getParentRightsForNonAdmin(String userName) {
        List<Rights> list = new ArrayList();
        try {
            String query = " SELECT * FROM TW_RIGHTS WHERE PARENT_ID=0 AND RIGHT_ID IN("
                    + " SELECT R.PARENT_ID FROM TW_RIGHTS R,TW_USER_RIGHT UR"
                    + " WHERE R.RIGHT_NME=UR.RIGHT_NME"
                    + " AND UPPER(UR.USER_NME)='" + userName.toUpperCase() + "')"
                    + " ORDER BY SORT";
            list = this.getDao().getJdbcTemplate().query(query, new Rights());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Rights> getAllParentRights(String moduleId) {
        List<Rights> list = new ArrayList();
        try {
            String query = "SELECT * FROM RIGHTS WHERE MODULE_ID=" + moduleId + " AND PARENT_ID=0 ORDER BY SORT ";
            list = this.getDao().getJdbcTemplate().query(query, new Rights());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List getAllChildRights(String userName) {
        String canView = "";
        List list = new ArrayList();
        try {
            if (!userName.equalsIgnoreCase("super_user")) {
                canView = " AND CAN_VIEW='Y' ";
            }
            String query = "SELECT * FROM RIGHTS WHERE PARENT_ID>0 " + canView + " ORDER BY SORT ";
            list = this.getDao().getJdbcTemplate().query(query, new Rights());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean assignUserRole(String userId, String[] role, String userName, String moduleId) {
        boolean flag = false;
        try {
            List<String> arr = new ArrayList<String>();
            if (userId != null) {
                String query = "DELETE FROM USER_ROLE WHERE USER_NME='" + userId + "' AND MODULE_ID=" + moduleId + " ";
                arr.add(query);
                if (role != null) {
                    for (int k = 0; k < role.length; k++) {
                        arr.add("INSERT INTO USER_ROLE(USER_ROLE_ID,ROLE_ID,USER_NME,MODULE_ID) "
                                + " VALUES (SEQ_USER_ROLE_ID.NEXTVAL," + role[k] + ",'" + userId + "'," + moduleId + ")");
                    }
                }
                flag = this.getDao().insertAll(arr, userName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean hasRightsOn(String userName, String url) {
        boolean flag = false;
        try {
            String sql = "SELECT RI.RIGHT_ID "
                    + " FROM WEB_USER U,RIGHTS RI,ROLE ROL,USER_ROLE UR,ROLE_RIGHTS RR "
                    + " WHERE U.ID=UR.USER_ID AND UR.ROLE_ID=ROL.ROLE_ID AND ROL.ROLE_ID=RR.ROLE_ID AND RR.RIGHT_ID=RI.RIGHT_ID "
                    + "     AND U.USERNAME='" + userName + "' "
                    + "     AND RI.TARGET_URL='" + url + "' ";
            List list = this.getDao().getData(sql);
            if (list != null && list.size() > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Role> getRolesByUserId(String userId, String moduleId) {
        List list = null;
        try {
            list = this.dao.getJdbcTemplate().query("SELECT * FROM ROLE ROL,USER_ROLE UR WHERE UR.ROLE_ID=ROL.ROLE_ID "
                    + " AND UPPER(UR.USER_NME)='" + userId.toUpperCase() + "' AND ROL.MODULE_ID=" + moduleId + " "
                    + " ORDER BY ROL.ROLE_ID ", new Role());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Rights> getRightsByRoleId(String roleId, String userName, String moduleId) {
        String canView = "";
        if (!userName.equalsIgnoreCase("super_user")) {
            canView = " AND CAN_VIEW='Y' ";
        }

        List<Rights> objList = new ArrayList<Rights>();
        try {
            List list = this.dao.getJdbcTemplate().queryForList("SELECT R.RIGHT_NME,R.URL,R.PARENT_ID,R.SORT, "
                    + " R.RIGHT_TXT,R.RIGHT_ID,RR.CAN_ADD,RR.CAN_EDIT,RR.CAN_DELETE "
                    + " FROM RIGHTS R,ROLE_RIGHTS RR WHERE R.RIGHT_NME=RR.RIGHT_NME AND R.MODULE_ID=RR.MODULE_ID "
                    + " AND RR.ROLE_ID=" + roleId + " AND RR.MODULE_ID=" + moduleId + " " + canView + " ORDER BY R.URL ");
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = (Map) list.get(i);
                    Rights r = new Rights();
                    r.setRightId((BigDecimal) map.get("RIGHT_ID"));
                    r.setRightName(map.get("RIGHT_NME").toString());
                    r.setTargetUrl(map.get("URL").toString());
                    r.setParentId((BigDecimal) map.get("PARENT_ID"));
                    r.setSort((BigDecimal) map.get("SORT"));
                    r.setRightText(map.get("RIGHT_TXT").toString());
                    r.setCanAdd(map.get("CAN_ADD").toString());
                    r.setCanEdit(map.get("CAN_EDIT").toString());
                    r.setCanDelete(map.get("CAN_DELETE").toString());
                    objList.add(r);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return objList;
    }

    @Override
    public boolean deleteUser(String userId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM WEB_USERS WHERE USER_NME='" + userId + "' ";
            int i = this.getDao().getJdbcTemplate().update(query);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteRole(String roleId) {
        boolean flag = false;
        try {
            String query = "DELETE FROM ROLE WHERE ROLE_ID=" + roleId;
            int i = this.getDao().getJdbcTemplate().update(query);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<SpecialRights> getAllSpecialRights(String userName, String moduleId, String companyId) {
        List list = null;
        try {
            String canView = "";
            if (!userName.equalsIgnoreCase("super_user")) {
                canView = "AND ACTIVE_IND='Y' ";
            }
            list = this.dao.getJdbcTemplate().query("SELECT SR.* FROM SPECIAL_RIGHTS SR "
                    + " WHERE SR.MODULE_ID=" + moduleId + " AND SR.COMPANY_ID=" + companyId + " " + canView + "", new SpecialRights());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public List<SpecialRights> getAllSpecialRightsByUser(String userName, String moduleId, String companyId) {
        List list = null;
        try {
            list = this.dao.getJdbcTemplate().query("SELECT SR.SPECIAL_RIGHTS_ID,SR.SPECIAL_RIGHT_NME "
                    + " FROM SPECIAL_RIGHTS SR, USER_SPECIAL_RIGHTS US "
                    + " WHERE US.SPECIAL_RIGHTS_ID=SR.SPECIAL_RIGHTS_ID AND US.USER_NME='" + userName + "' "
                    + "    AND SR.MODULE_ID=" + moduleId + " AND SR.COMPANY_ID=" + companyId + " ORDER BY SR.SPECIAL_RIGHT_NME ", new SpecialRights());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean assignSpecialRights(String userId, String[] rights, String moduleId, String userName, String companyId) {
//        boolean flag = false;
//        try {
//            List<String> queryList = new ArrayList<>();
//            if (userId != null) {
//                String query = "DELETE FROM USER_SPECIAL_RIGHTS USR "
//                        + "  WHERE UPPER(USR.USER_NME)='" + userId.toUpperCase() + "' AND USR.SPECIAL_RIGHTS_ID IN ( "
//                        + "    SELECT SR.SPECIAL_RIGHTS_ID FROM SPECIAL_RIGHTS SR WHERE SR.MODULE_ID=" + moduleId + " AND SR.COMPANY_ID=" + companyId + ") ";
//                queryList.add(query);
//                if (rights != null) {
//                    for (int k = 0; k < rights.length; k++) {
//                        queryList.add("INSERT INTO USER_SPECIAL_RIGHTS(USER_SPECIAL_RIGHTS_ID,SPECIAL_RIGHTS_ID,USER_NME) "
//                                + " VALUES (SEQ_USER_SPECIAL_RIGHTS_ID.NEXTVAL," + rights[k] + ",'" + userId + "')");
//                    }
//                }
//                flag = this.getDao().insertAll(queryList, userName);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return flag;
        return true;
    }
//User Sites

    @Override
    public List getAllSitesByUser(String userName, String companyId) {
        List list = null;
        try {
            String query = "SELECT S.* FROM  USER_SITE US,SITE S"
                    + " WHERE S.SITE_ID=US.SITE_ID"
                    + " AND US.USER_NME='" + userName + "' ORDER BY S.SITE_ID ";
            list = this.dao.getJdbcTemplate().query(query, new Site());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean assignUserSites(String userId, String[] sites, String userName) {
        boolean flag = false;
        try {
            if (userId != null) {
                String query = "DELETE FROM USER_SITE WHERE USER_NME='" + userId + "'";

                int i = this.getDao().getJdbcTemplate().update(query);
                flag = true;
                if (sites != null) {
                    String[] q = new String[sites.length];
                    if (i > -1) {
                        for (int k = 0; k < sites.length; k++) {
                            q[k] = "INSERT INTO USER_SITE(USER_SITE_ID,SITE_ID,USER_NME) VALUES (SEQ_USER_SITE_ID.NEXTVAL,"
                                    + " " + sites[k] + ",'" + userId + "')";
                        }
                        if (q.length > 0) {
                            flag = this.getDao().insertAll(q, userName);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean hasSpecialRightOn(String userName, String specialRightName) {
        boolean flag = false;
        try {
            List list = this.dao.getJdbcTemplate().queryForList("SELECT SR.SPECIAL_RIGHTS_ID,SR.SPECIAL_RIGHT_NME,SR.RIGHT_DESC FROM SPECIAL_RIGHTS SR, USER_SPECIAL_RIGHTS US "
                    + " WHERE US.SPECIAL_RIGHTS_ID=SR.SPECIAL_RIGHTS_ID AND US.USER_NME='" + userName + "' AND SR.SPECIAL_RIGHT_NME='" + specialRightName + "'");
            if (list != null && list.size() > 0) {
                java.util.Map map = (java.util.Map) list.get(0);
                String name = (String) map.get("SPECIAL_RIGHT_NME");
                if (name != null && !name.isEmpty()) {
                    flag = true;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean hasRightOn(String userName, String rightName, String action) {
        boolean flag = false;
        try {
            List list = this.dao.getJdbcTemplate().queryForList("SELECT RR.RIGHT_NME, RR.CAN_ADD, RR.CAN_EDIT, RR.CAN_DELETE FROM ROLE_RIGHTS RR, ROLE R, USER_ROLE UR, WEB_USERS WS "
                    + " WHERE RR.ROLE_ID = R.ROLE_ID AND R.ROLE_ID = UR.ROLE_ID AND UR.USER_NME = WS.USER_NME AND WS.USER_NME='" + userName + "' AND RR.RIGHT_NME='" + rightName + "' ");
            if (list != null && list.size() > 0) {
                java.util.Map map = (java.util.Map) list.get(0);
                String name = (String) map.get("CAN_" + action);
                if (name != null && !name.isEmpty() && name.equalsIgnoreCase("Y")) {
                    flag = true;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean isRoleExists(String roleName, String companyId) {
        boolean flag = true;
        try {
            List list = this.getDao().getJdbcTemplate().queryForList("select ROLE_NME from ROLE where ROLE_NME='" + roleName + "'");
            if (list != null && list.size() > 0) {
                Map map = (Map) list.get(0);
                String name = (String) map.get("ROLE_NME");
                if (name != null && !name.isEmpty()) {
                    flag = false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean assignUserVoucherSubTypes(String userId, String[] rights, String userName, String companyId) {
        boolean flag = false;
        try {
            if (userId != null) {
                String query = "delete from USER_VOUCHER_SUB_TYPE where USER_NME='" + userId + "'";

                int i = this.getDao().getJdbcTemplate().update(query);
                flag = true;
                if (rights != null) {
                    String[] q = new String[rights.length];
                    if (i > -1) {
                        for (int k = 0; k < rights.length; k++) {
                            q[k] = "insert into USER_VOUCHER_SUB_TYPE(USER_VOUCHER_SUB_TYPE_ID,VOUCHER_SUB_TYP_ID,USER_NME,COMPANY_ID) "
                                    + " values (SEQ_USER_VOUCHER_SUB_TYPE_ID.NEXTVAL," + rights[k] + ",'" + userId + "', " + companyId + ")";
                        }
                        if (q.length > 0) {
                            flag = this.getDao().insertAll(q, userName);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public List getAllVoucherSubTypesByUser(String userName, String companyId) {
        List list = null;
        try {
            list = this.dao.getJdbcTemplate().query("SELECT VST.* FROM VOUCHER_SUB_TYPE VST, USER_VOUCHER_SUB_TYPE UVST "
                    + " WHERE VST.VOUCHER_SUB_TYP_ID=UVST.VOUCHER_SUB_TYP_ID AND VST.VOUCHER_TYP='JV' AND VST.COMPANY_ID=" + companyId + " AND UVST.USER_NME='" + userName + "' ORDER BY VST.VOUCHER_SUB_TYP_ID ", new VoucherSubType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /*
     @User BusinessUnit
     */
    @Override
    public List<BusinessUnit> getBusinessUnitByUserId(String userId, String companyId) {
        List<BusinessUnit> list = null;
        try {
            String query = "SELECT BU.BUSINESS_UNIT_ID,BU.BU_NME,BU.BU_ABBREV,BU.COMPANY_ID "
                    + " FROM USER_BUSINESS_UNIT UBU,BUSINESS_UNIT BU"
                    + " WHERE UBU.BUSINESS_UNIT_ID=BU.BUSINESS_UNIT_ID"
                    + " AND UPPER(UBU.USER_NME)='" + userId.toUpperCase() + "'"
                    + " AND BU.COMPANY_ID=" + companyId + ""
                    + " ORDER BY BU.BU_NME";
            list = this.getDao().getJdbcTemplate().query(query, new BusinessUnit());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean assignUserBusinessUnit(String user, String[] units, String userName) {
        boolean flag = false;
        try {
            List<String> arr = new ArrayList<String>();
            if (user != null) {
                String query = "DELETE FROM USER_BUSINESS_UNIT WHERE UPPER(USER_NME)='" + user.toUpperCase() + "'";
                arr.add(query);
                if (units != null) {
                    for (int k = 0; k < units.length; k++) {
                        arr.add("INSERT INTO USER_BUSINESS_UNIT(USER_BUSINESS_UNIT_ID,BUSINESS_UNIT_ID,USER_NME) "
                                + " VALUES (SEQ_USER_BUSINESS_UNIT_ID.NEXTVAL, '" + units[k] + "','" + user + "')");
                    }
                }
                flag = this.getDao().insertAll(arr, userName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map getUserRights(String userName, String rightName) {
        Map map = new HashMap();
        try {
            String query = "SELECT CAN_ADD,CAN_EDIT,CAN_DELETE "
                    + " FROM TW_USER_RIGHT  "
                    + " WHERE UPPER(USER_NME)='" + userName.toUpperCase() + "' "
                    + " AND UPPER(RIGHT_NME)='" + rightName.toUpperCase() + "' ";
            List list = this.getDao().getJdbcTemplate().queryForList(query);
            if (list != null && list.size() > 0) {
                map = (Map) list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Rights> getRightsByUser(String userName, String moduleId, String companyId) {
        List<Rights> objList = new ArrayList();
        try {
            String query = "SELECT R.RIGHT_ID,R.RIGHT_NME,R.URL,R.PARENT_ID,R.SORT,R.RIGHT_TXT,"
                    + " UR.CAN_ADD,UR.CAN_EDIT,UR.CAN_DELETE "
                    + " FROM TW_RIGHTS R,TW_USER_RIGHT UR"
                    + " WHERE R.RIGHT_NME=UR.RIGHT_NME"
                    + " AND UPPER(UR.USER_NME)='" + userName.toUpperCase() + "'"
                    + " ORDER BY R.SORT";
            List list = this.dao.getJdbcTemplate().queryForList(query);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = (Map) list.get(i);
                    Rights r = new Rights();
                    r.setRightId((BigDecimal) map.get("RIGHT_ID"));
                    r.setRightName(map.get("RIGHT_NME").toString());
                    // System.out.println(map.get("RIGHT_NME").toString());
                    r.setTargetUrl(map.get("URL").toString());
                    r.setParentId((BigDecimal) map.get("PARENT_ID"));
                    r.setSort((BigDecimal) map.get("SORT"));
                    r.setRightText(map.get("RIGHT_TXT").toString());
                    r.setCanAdd(map.get("CAN_ADD").toString());
                    r.setCanEdit(map.get("CAN_EDIT").toString());
                    r.setCanDelete(map.get("CAN_DELETE").toString());
                    objList.add(r);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return objList;
    }

    public boolean hasSpecialRightOn(String userName, String specialRightName, String moduleId) {
        boolean flag = false;
        try {
            List list = this.dao.getJdbcTemplate().queryForList("SELECT SR.SPECIAL_RIGHTS_ID"
                    + " FROM SPECIAL_RIGHTS SR, USER_SPECIAL_RIGHTS US"
                    + " WHERE US.SPECIAL_RIGHTS_ID=SR.SPECIAL_RIGHTS_ID "
                    + " AND UPPER(US.USER_NME)='" + userName.toUpperCase() + "' "
                    + " AND UPPER(SR.SPECIAL_RIGHT_NME)='" + specialRightName.toUpperCase() + "'"
                    + " AND SR.MODULE_ID=" + moduleId + "");
            if (list != null && list.size() > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean isUserExists(String userName, String companyId) {
        boolean flag = false;
        try {
            String query = "SELECT * FROM TW_WEB_USERS "
                    + " WHERE UPPER(USER_NME)='" + userName.toUpperCase() + "'";
            List<Map> list = this.getDao().getData(query);
            if (list != null && list.size() > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean saveUsers(User u) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            Encryption pswdSec = new Encryption();
            if (u.getUserId() != null && !u.getUserId().isEmpty() && u.getNewPassword() == null) {
                query = "UPDATE TW_WEB_USERS SET FIRST_NME='" + Util.removeSpecialChar(u.getFirstName()) + "',"
                        + " EMAIL='" + Util.removeSpecialChar(u.getEmail()).trim() + "'"
                        + " WHERE USER_NME='" + u.getUserId() + "'";
                arr.add(query);
            } else if (u.getUserId() != null && !u.getUserId().isEmpty() && u.getNewPassword() != null) {
                String password = pswdSec.encrypt(u.getPassword());
                query = "UPDATE TW_WEB_USERS SET USER_PASSWORD='" + password + "'"
                        + " WHERE USER_NME='" + u.getUserId() + "'";
                arr.add(query);
            } else {
                String password = pswdSec.encrypt(u.getPassword());
                if (u.getUserType() != null && u.getUserType().equalsIgnoreCase("DOCTOR")) {
                    query = "INSERT INTO TW_WEB_USERS(USER_NME,USER_PASSWORD,ACTIVE_IND,FIRST_NME,"
                            + "EMAIL,TW_DOCTOR_ID)"
                            + " VALUES ('" + Util.removeSpecialChar(u.getUsername()).toLowerCase() + "',"
                            + "'" + password + "','Y',"
                            + "INITCAP('" + Util.removeSpecialChar(u.getFirstName()) + "'),"
                            + "'" + u.getEmail() + "','" + u.getDoctorId() + "' )";
                } else if (u.getUserType() != null && u.getUserType().equalsIgnoreCase("PHARMA")) {
                    query = "INSERT INTO TW_WEB_USERS(USER_NME,USER_PASSWORD,ACTIVE_IND,FIRST_NME,"
                            + "EMAIL,TW_PHARMACEUTICAL_ID)"
                            + " VALUES ('" + Util.removeSpecialChar(u.getUsername()).toLowerCase() + "',"
                            + "'" + password + "','Y',"
                            + "INITCAP('" + Util.removeSpecialChar(u.getFirstName()) + "'),"
                            + "'" + u.getEmail() + "','" + u.getPharmaId() + "' )";
                } else if (u.getUserType() != null && u.getUserType().equalsIgnoreCase("ADMIN")) {
                    query = "INSERT INTO TW_WEB_USERS(USER_NME,USER_PASSWORD,ACTIVE_IND,FIRST_NME,"
                            + "EMAIL)"
                            + " VALUES ('" + Util.removeSpecialChar(u.getUsername()).toLowerCase() + "',"
                            + "'" + password + "','Y',"
                            + "INITCAP('" + Util.removeSpecialChar(u.getFirstName()) + "'),"
                            + "'" + u.getEmail() + "' )";
                }

                arr.add(query);
            }

            flag = this.dao.insertAll(arr, u.getUserName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;

    }

    @Override
    public Map getUsersById(String userId) {
        Map map = null;
        try {
            String query = "SELECT USER_NME,FIRST_NME,EMAIL FROM TW_WEB_USERS WHERE USER_NME='" + userId + "'";
            List<Map> list = this.dao.getData(query);
            if (list != null && list.size() > 0) {
                map = list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public boolean updatePassword(User u) {
        boolean flag = false;
        List<String> arr = new ArrayList();
        try {
            String query = "";
            if (u.getUserId() != null && u.getNewPassword() != null && !u.getUserId().isEmpty()) {
                Encryption pswdSec = new Encryption();
                String newPassword = pswdSec.encrypt(u.getNewPassword());
                query = "UPDATE TW_WEB_USERS SET USER_PASSWORD='" + newPassword + "'"
                        + " WHERE UPPER(USER_NME)='" + u.getUserId().toUpperCase() + "'";
                arr.add(query);
            }
            flag = this.dao.insertAll(arr, u.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean updateUserStatus(String userName, String activeInd) {
        boolean flag = false;
        try {
            String query = "";
            query = "UPDATE TW_WEB_USERS SET ACTIVE_IND='" + activeInd + "' "
                    + " WHERE USER_NME='" + userName + "'";
            int i = this.getDao().getJdbcTemplate().update(query);
            if (i > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    public List<Rights> getRightsByRole(String roleId) {
        List<Rights> objList = new ArrayList();
        try {
            String query = "SELECT R.RIGHT_ID,R.RIGHT_NME,R.URL,R.PARENT_ID,R.SORT,R.RIGHT_TXT"
                    + " FROM TW_RIGHTS R,TW_ROLE_RIGHTS UR"
                    + " WHERE R.RIGHT_NME=UR.RIGHT_NME"
                    + " AND UR.TW_ROLE_ID=" + roleId + ""
                    + " ORDER BY R.SORT";
            List list = this.dao.getJdbcTemplate().queryForList(query);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = (Map) list.get(i);
                    Rights r = new Rights();
                    r.setRightId((BigDecimal) map.get("RIGHT_ID"));
                    r.setRightName(map.get("RIGHT_NME").toString());
                    // System.out.println(map.get("RIGHT_NME").toString());
                    r.setTargetUrl(map.get("URL").toString());
                    r.setParentId((BigDecimal) map.get("PARENT_ID"));
                    r.setSort((BigDecimal) map.get("SORT"));
                    r.setRightText(map.get("RIGHT_TXT").toString());
                    objList.add(r);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return objList;
    }
}
