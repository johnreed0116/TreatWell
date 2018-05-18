/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.doctor;

import com.alberta.dao.DAO;
import com.alberta.model.CategoryVO;
import com.alberta.model.DoctorVO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author farazahmad
 */
public interface DoctorService {

    /**
     * @return the dao
     */
    DAO getDao();

    /**
     * @param dao the dao to set
     */
    void setDao(DAO dao);

    boolean saveDoctor(DoctorVO vo);

    Map getTempDoctorById(String doctorId);

    List<Map> getTempDoctors(String doctorName, String mobileNbr, String doctorType);

    boolean saveDiagnostic(String diagnosticId, String specialityId, String title, String userName, String diagnosticInd);

    List<Map> getDiagnostic(String specialityId);

    Map getDiagnosticById(String diagnosticId);

    boolean deleteDiagnostic(String diagnosticId);

    boolean saveVaccinationCategory(CategoryVO vo);

    List<Map> getVaccinationCategories(String specialityId);

    Map getVaccinationCategoryById(String questionCategoryId);

    boolean deleteVaccinationCategory(String questionCategoryId);
    
    List<Map> getVaccinationCategoriesForDoctor(String doctorId);
}
