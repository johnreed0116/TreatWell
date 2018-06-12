/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.login;

import com.alberta.dao.DAO;
import com.alberta.model.Rights;
import com.alberta.model.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Faraz
 */
public interface LoginService {

    User verifyLogin(String userName, String password);

    List<Rights> getParentMenu(String userName, String moduleId);

    DAO getDao();

    void setDao(DAO dao);

    List<Rights> getUserRights(String userName, String password, String moduleId);

    boolean changePassword(String userName, String oldPassword, String newPassword);

    String insertUserSession(String userName, String ip, String action, String id);

    List<Rights> getRightsByParentId(String userName, String moduleId, String parentId);

    List<Map> getDoctorClinics(String doctorId);

    List<Map> getParentMenu();

    List<Map> getChildMenu();

    List<Map> getDashBoardDataForDoctors(String doctorId, String clinicId);

    List<Map> getDashBoardDataForPatient(String patient);

    List<Map> getCollectedFeeForDoctorsByMonth(String doctorId, String clinicId);

    boolean resetPassword(String mobileNo, String userType);
}
