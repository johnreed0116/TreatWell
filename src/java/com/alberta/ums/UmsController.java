/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alberta.ums;

import com.alberta.model.*;
import com.alberta.service.ServiceFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Faraz
 */
public class UmsController extends MultiActionController {
    
    private ServiceFactory serviceFactory;
    
    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }
    
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
    
    public ModelAndView viewUsers(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("rightName", "Users");
        return new ModelAndView("ums/viewUsers", "refData", map);
    }
    
    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String accountType = request.getParameter("accountType");
        List<Map> list = null;
        List<JSONObject> objList = new ArrayList();
        String userType = request.getSession().getAttribute("userType").toString();
        if (userType.equalsIgnoreCase("ADMIN")) {
            list = this.serviceFactory.getUmsService().getAllUsers(accountType);
        } else if (userType.equalsIgnoreCase("DOCTOR")) {
            list = this.serviceFactory.getUmsService().getUserForDoctor(user.getDoctorId());
        } else if (userType.equalsIgnoreCase("PHARMA")) {
            list = this.serviceFactory.getUmsService().getUserForPharma(user.getPharmaId());
        } else if (userType.equalsIgnoreCase("MEDICAL_STORE")) {
            list = this.serviceFactory.getUmsService().getUserForPharmacy(user.getMedicalStoreId());
        } else if (userType.equalsIgnoreCase("LAB")) {
            list = this.serviceFactory.getUmsService().getUserForLab(user.getLabDetailId());
        } else if (userType.equalsIgnoreCase("CLINIC")) {
            list = this.serviceFactory.getUmsService().getUserForClinic(user.getClinicId());
        }
        JSONObject obj = null;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                obj = new JSONObject();
                Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
                while (itr.hasNext()) {
                    String key = itr.next().getKey();
                    obj.put(key, map.get(key) != null ? map.get(key).toString() : "");
                }
                objList.add(obj);
            }
        }
        response.getWriter().write(objList.toString());
        
    }
    
    public ModelAndView editUser(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        String id = request.getParameter("userId");
        map.put("usr", this.serviceFactory.getUmsService().getUserById(id));
        return new ModelAndView("ums/addUser", "refData", map);
    }
    
    public ModelAndView assignRoleRights(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("rightName", "Role Rights");
        map.put("roles", this.serviceFactory.getUmsService().getAllRoles());
        return new ModelAndView("ums/assignRoleRights", "refData", map);
    }
    
    public void getRoleRights(HttpServletRequest request, HttpServletResponse response) {
        try {
            String roleId = request.getParameter("roleId");
            Company com = (Company) request.getSession().getAttribute("company");
            List rightList = null;
            List parentList = null;
            rightList = this.serviceFactory.getUmsService().getRightsForAdmin();
            parentList = this.serviceFactory.getUmsService().getParentRightsForAdmin();
            List roleRightList = this.serviceFactory.getUmsService().getRightsByRole(roleId);
            StringBuilder sb = new StringBuilder();
            sb.append("<table width='80%' class='table table-striped table-bordered'><thead><tr >"
                    + "<td align='center'><input type='checkbox'  class='selectAllRightsBtn'></td><th align='center'>Right Name</th></tr><t/head>");
            sb.append("<tbody>");
            for (int k = 0; k < parentList.size(); k++) {
                Rights parent = (Rights) parentList.get(k);
                sb.append("<tr><td>&nbsp;</td><td colspan='2'  ><b>");
                sb.append(parent.getRightName());
                sb.append("</b></td>");
                sb.append("</tr>");

                //Child Rights
                for (int i = 0; i < rightList.size(); i++) {
                    Rights right = (Rights) rightList.get(i);
                    if (right.getParentId().equals(parent.getRightId())) {
                        sb.append("<tr><td align='center' ><input type='checkbox' name='rightId" + (i + 1) + "' id='rightId" + (i + 1) + "' value='" + right.getRightName() + "' class='assignRight'");
                        for (int j = 0; j < roleRightList.size(); j++) {
                            Rights rr = (Rights) roleRightList.get(j);
                            if (rr.getRightName().equalsIgnoreCase(right.getRightName())) {
                                sb.append(" checked='checked'");
                            }
                        }
                        sb.append("/></td>");
                        sb.append("<td align='left'>" + right.getRightName() + "</td>");
                        
                        sb.append("</tr>");
                    }
                }
            }
            sb.append("</tbody>");
            sb.append("</table>");
            sb.append("<table width='80%' class='ui-widget'>");
            sb.append("<tr><td  ><button class='btn blue' onclick='saveRoleRights();'><i class='fa fa-floppy-o'></i> Save</button></td></tr>");
            sb.append("</table>");
            response.getWriter().write(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public ModelAndView processAssignRoleRights(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        User user = (User) request.getSession().getAttribute("user");
        List rightList = null;
        rightList = this.serviceFactory.getUmsService().getRightsForAdmin();
        List<Rights> rightIds = new ArrayList();
        for (int i = 1; i <= rightList.size(); i++) {
            if (request.getParameter("rightId" + i) != null) {
                Rights r = new Rights();
                r.setRightName(request.getParameter("rightId" + i));
                rightIds.add(r);
            }
        }
        String roleList = request.getParameter("roleList");
        if (roleList != null) {
            boolean flag = this.getServiceFactory().getUmsService().assignRoleRights(roleList, rightIds, user.getUsername());
            if (flag) {
                map.put("msg", "saved");
            } else {
                map.put("msg", "error");
            }
        }
        map.put("roles", this.serviceFactory.getUmsService().getAllRoles());
        map.put("rightName", "Role Rights");
        map.put("selectedUser", roleList);
        return new ModelAndView("ums/assignRoleRights", "refData", map);
    }
    
    public ModelAndView assignUserRights(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        User user = (User) request.getSession().getAttribute("user");
        map.put("rightName", "User Rights");
        String userType = request.getSession().getAttribute("userType").toString();
//        if (userType.equalsIgnoreCase("ADMIN")) {
//            map.put("users", this.serviceFactory.getUmsService().getAllUsers(null));
//        } else if (userType.equalsIgnoreCase("DOCTOR")) {
//            map.put("users", this.serviceFactory.getUmsService().getUserForDoctor(user.getDoctorId()));
//        }
        //map.put("roles", this.serviceFactory.getUmsService().getAllRoles(user.getUsername(), companyId, moduleId));

        return new ModelAndView("ums/assignRights", "refData", map);
    }
    
    public void getUserRights(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            String userName = request.getParameter("userName");
            Company com = (Company) request.getSession().getAttribute("company");
            String moduleId = request.getSession().getAttribute("moduleId") != null ? (String) request.getSession().getAttribute("moduleId") : "";
            String companyId = com.getCompanyId();
            
            List rightList = null;
            String userType = request.getSession().getAttribute("userType").toString();
            List parentList = null;
            if (userType.equalsIgnoreCase("ADMIN")) {
                rightList = this.serviceFactory.getUmsService().getRightsForAdmin();
                parentList = this.serviceFactory.getUmsService().getParentRightsForAdmin();
            } else {
                rightList = this.serviceFactory.getUmsService().getRightsForNonAdminUsers(user.getUsername());
                parentList = this.serviceFactory.getUmsService().getParentRightsForNonAdmin(user.getUsername());
            }
            List roleRightList = this.serviceFactory.getUmsService().getRightsByUser(userName, moduleId, companyId);
            StringBuilder sb = new StringBuilder();
            sb.append("<table width='80%' class='table table-striped table-bordered'><thead><tr >"
                    + "<td align='center'><input type='checkbox'  class='selectAllRightsBtn'></td><th align='center'>Right Name</th><th align='center'>Can Add &nbsp;&nbsp;<input type='checkbox' class='selectAllAddBtn'></th><th align='center'>Can Edit &nbsp;&nbsp;<input type='checkbox' class='selectAllEditBtn'></th><th align='center'>Can Delete &nbsp;&nbsp;<input type='checkbox' class='selectAllDeleteBtn'></th></tr><t/head>");
            sb.append("<tbody>");
            for (int k = 0; k < parentList.size(); k++) {
                Rights parent = (Rights) parentList.get(k);
                sb.append("<tr><td>&nbsp;</td><td colspan='4'  ><b>");
                sb.append(parent.getRightName());
                sb.append("</b></td>");
                sb.append("</tr>");

                //Child Rights
                for (int i = 0; i < rightList.size(); i++) {
                    Rights right = (Rights) rightList.get(i);
                    if (right.getParentId().equals(parent.getRightId())) {
                        sb.append("<tr><td align='center' ><input type='checkbox' name='rightId" + (i + 1) + "' id='rightId" + (i + 1) + "' value='" + right.getRightName() + "' class='assignRight'");
                        
                        for (int j = 0; j < roleRightList.size(); j++) {
                            Rights rr = (Rights) roleRightList.get(j);
                            
                            if (rr.getRightName().equalsIgnoreCase(right.getRightName())) {
                                sb.append(" checked='checked'");
                            }
                        }
                        sb.append("/></td>");
                        
                        sb.append("<td align='left'>" + right.getRightName() + "</td>");
                        
                        sb.append("<td align='center' ><input type='checkbox' name='canAdd" + (i + 1) + "' id='canAdd" + (i + 1) + "' value='Y' class='canAdd'");
                        for (int j = 0; j < roleRightList.size(); j++) {
                            Rights rr = (Rights) roleRightList.get(j);
                            if (rr.getRightName().equalsIgnoreCase(right.getRightName())) {
                                if (rr.getCanAdd() != null && rr.getCanAdd().equalsIgnoreCase("Y")) {
                                    sb.append(" checked='checked'");
                                }
                            }
                        }
                        sb.append("/></td>");
                        
                        sb.append("<td align='center' ><input type='checkbox' name='canEdit" + (i + 1) + "' id='canEdit" + (i + 1) + "' value='Y' class='canEdit'");
                        for (int j = 0; j < roleRightList.size(); j++) {
                            Rights rr = (Rights) roleRightList.get(j);
                            if (rr.getRightName().equalsIgnoreCase(right.getRightName())) {
                                if (rr.getCanEdit() != null && rr.getCanEdit().equalsIgnoreCase("Y")) {
                                    sb.append(" checked='checked'");
                                }
                            }
                        }
                        sb.append("/></td>");
                        
                        sb.append("<td align='center' ><input type='checkbox' name='canDelete" + (i + 1) + "' id='canDelete" + (i + 1) + "' value='Y' class='canDelete'");
                        for (int j = 0; j < roleRightList.size(); j++) {
                            Rights rr = (Rights) roleRightList.get(j);
                            if (rr.getRightName().equalsIgnoreCase(right.getRightName())) {
                                if (rr.getCanDelete() != null && rr.getCanDelete().equalsIgnoreCase("Y")) {
                                    sb.append(" checked='checked'");
                                }
                            }
                        }
                        sb.append("/></td>");
                        sb.append("</tr>");
                    }
                }
            }
            sb.append("</tbody>");
            sb.append("</table>");
            sb.append("<table width='80%' class='ui-widget'>");
            sb.append("<tr><td  ><button class='btn blue' onclick='saveUserRights();'><i class='fa fa-floppy-o'></i> Save</button></td></tr>");
            sb.append("</table>");
            response.getWriter().write(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public ModelAndView processAssignRights(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        User user = (User) request.getSession().getAttribute("user");
        List rightList = null;
        String userType = request.getSession().getAttribute("userType").toString();
        if (userType.equalsIgnoreCase("ADMIN")) {
            rightList = this.serviceFactory.getUmsService().getRightsForAdmin();
        } else {
            rightList = this.serviceFactory.getUmsService().getRightsForNonAdminUsers(user.getUserName());
        }
        List<Rights> rightIds = new ArrayList();
        Company com = (Company) request.getSession().getAttribute("company");
        String companyId = com.getCompanyId();
        for (int i = 1; i <= rightList.size(); i++) {
            if (request.getParameter("rightId" + i) != null) {
                Rights r = new Rights();
                r.setRightName(request.getParameter("rightId" + i));
                r.setCanAdd((request.getParameter("canAdd" + i) != null ? "Y" : "N"));
                r.setCanEdit((request.getParameter("canEdit" + i) != null ? "Y" : "N"));
                r.setCanDelete((request.getParameter("canDelete" + i) != null ? "Y" : "N"));
                rightIds.add(r);
            }
        }
        String userList = request.getParameter("userList");
        if (userList != null) {
            boolean flag = this.getServiceFactory().getUmsService().assignRights(userList, rightIds, user.getUsername());
            if (flag) {
                map.put("msg", "saved");
            } else {
                map.put("msg", "error");
            }
        }
        if (userType.equalsIgnoreCase("ADMIN")) {
            map.put("users", this.serviceFactory.getUmsService().getAllUsers(null));
        } else if (userType.equalsIgnoreCase("DOCTOR")) {
            map.put("users", this.serviceFactory.getUmsService().getUserForDoctor(user.getDoctorId()));
        }
        map.put("rightName", "User Rights");
        map.put("selectedUser", userList);
        return new ModelAndView("ums/assignRights", "refData", map);
    }
    
    public ModelAndView assignRoles(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("users", this.serviceFactory.getUmsService().getAllUsers("Y"));
        return new ModelAndView("ums/assignRole", "refData", map);
    }
    
    public ModelAndView processAssignRoles(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("users", this.serviceFactory.getUmsService().getAllUsers("Y"));
        User user = (User) request.getSession().getAttribute("user");
        String userId = request.getParameter("user");
        String[] role = request.getParameterValues("roleId");
        String moduleId = request.getSession().getAttribute("moduleId") != null ? (String) request.getSession().getAttribute("moduleId") : "";
        if (userId != null) {
            boolean flag = this.serviceFactory.getUmsService().assignUserRole(userId, role, user.getUsername(), moduleId);
            if (flag) {
                map.put("msg", "<div class='ui-state-highlight ui-corner-all'><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;'></span>Role Assigned Successfully</div>");
            } else {
                map.put("msg", "<div class='ui-state-error ui-corner-all'><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span>Error in Assigning Roles</div>");
            }
        }
        return new ModelAndView("ums/assignRole", "refData", map);
    }
    
    public ModelAndView deleteUser(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        String id = request.getParameter("userId");
        if (this.serviceFactory.getUmsService().deleteUser(id)) {
            map.put("msg", "<div class='ui-state-highlight ui-corner-all'><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;'></span>User Deleted Successfully</div>");
        } else {
            map.put("msg", "<div class='ui-state-error ui-corner-all'><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span>Error in Deleting User</div>");
        }
        map.put("users", this.serviceFactory.getUmsService().getAllUsers(null));
        return new ModelAndView("ums/viewUsers", "refData", map);
    }
//Special Rights

    public ModelAndView assignSpecialRights(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("users", this.serviceFactory.getUmsService().getAllUsers("Y"));
        return new ModelAndView("ums/assignSpecialRights", "refData", map);
    }
    
    public void getUserSpecialRights(HttpServletRequest request, HttpServletResponse response) {
        try {
            String userName = request.getParameter("userId");
            User user = (User) request.getSession().getAttribute("user");
            Company com = (Company) request.getSession().getAttribute("company");
            String companyId = com.getCompanyId();
            String moduleId = request.getSession().getAttribute("moduleId") != null ? (String) request.getSession().getAttribute("moduleId") : "";
            List userSpecialRights = this.serviceFactory.getUmsService().getAllSpecialRightsByUser(userName, moduleId, companyId);
            List specialRights = this.serviceFactory.getUmsService().getAllSpecialRights(user.getUsername(), moduleId, companyId);
            StringBuilder sb = new StringBuilder();
            sb.append("<table width='60%' class='ui-widget table-style'><tr><th align='center'>Right Name</th><th align='center'>Assign</th></tr>");
            sb.append("<tbody>");
            for (int i = 0; i < specialRights.size(); i++) {
                SpecialRights sr = (SpecialRights) specialRights.get(i);
                sb.append("<tr class='ui-widget-content'><td align='left' class='ui-widget-content'>" + sr.getRightName() + "</td>");
                sb.append("<td align='center' class='ui-widget-content'><input type='checkbox' name='rightId' id='rightId" + (i + 1) + "' value='" + sr.getRightId() + "' ");
                for (int j = 0; j < userSpecialRights.size(); j++) {
                    SpecialRights rr = (SpecialRights) userSpecialRights.get(j);
                    if (rr.getRightId().equals(sr.getRightId())) {
                        sb.append("checked='checked'");
                    }
                }
                sb.append("/></td></tr>");
            }
            sb.append("<tbody>");
            sb.append("</table>");
            sb.append("<table width='60%' class='ui-widget'>");
            sb.append("<tr><td align='center'><input type='submit' value='Save' class='ui-widget ui-button ui-button-text-only ui-widget ui-state-default ui-corner-all'></td></tr>");
            sb.append("</table>");
            response.getWriter().write(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public ModelAndView processAssignSpecialRights(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("users", this.serviceFactory.getUmsService().getAllUsers("Y"));
        User user = (User) request.getSession().getAttribute("user");
        String userId = request.getParameter("user");
        String[] rightId = request.getParameterValues("rightId");
        Company com = (Company) request.getSession().getAttribute("company");
        String companyId = com.getCompanyId();
        String moduleId = request.getSession().getAttribute("moduleId") != null ? (String) request.getSession().getAttribute("moduleId") : "";
        if (userId != null) {
            boolean flag = this.serviceFactory.getUmsService().assignSpecialRights(userId, rightId, moduleId, user.getUsername(), companyId);
            if (flag) {
                map.put("msg", "<div class='ui-state-highlight ui-corner-all'><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;'></span>Special Rights Assigned Successfully</div>");
            } else {
                map.put("msg", "<div class='ui-state-error ui-corner-all'><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span>Error in Assigning Special Rights</div>");
            }
        }
        return new ModelAndView("ums/assignSpecialRights", "refData", map);
    }

    //User Voucher Sub Types
    public ModelAndView assignUserVoucherSubType(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("users", this.serviceFactory.getUmsService().getAllUsers("Y"));
        return new ModelAndView("ums/assignUserVoucherSubType", "refData", map);
    }
    
    public void getUserVoucherSubTypes(HttpServletRequest request, HttpServletResponse response) {
        try {
            String userName = request.getParameter("userId");
            Company com = (Company) request.getSession().getAttribute("company");
            String companyId = com.getCompanyId();
            List userVoucherSubTypes = this.serviceFactory.getUmsService().getAllVoucherSubTypesByUser(userName, companyId);
            List voucherSubTypes = null;// this.serviceFactory.getFinanceService().getAllVoucherSubTypes("JV", companyId);
            StringBuilder sb = new StringBuilder();
            sb.append("<table width='60%' class='ui-widget table-style'><tr><th align='center'>Type Name</th><th align='center'>Assign</th></tr>");
            sb.append("<tbody>");
            for (int i = 0; i < voucherSubTypes.size(); i++) {
                VoucherSubType vst = (VoucherSubType) voucherSubTypes.get(i);
                sb.append("<tr ><td align='left' >" + vst.getSubTypeDesc() + "</td>");
                sb.append("<td align='center' ><input type='checkbox' name='rightId' id='rightId" + (i + 1) + "' value='" + vst.getSubTypeId() + "' ");
                for (int j = 0; j < userVoucherSubTypes.size(); j++) {
                    VoucherSubType userVST = (VoucherSubType) userVoucherSubTypes.get(j);
                    if (userVST.getSubTypeId().equals(vst.getSubTypeId())) {
                        sb.append("checked='checked'");
                    }
                }
                sb.append("/></td></tr>");
            }
            sb.append("</tbody>");
            sb.append("</table>");
            sb.append("<table width='60%' class='ui-widget'>");
            sb.append("<tr><td align='center'><input type='submit' value='Save' class='ui-widget ui-button ui-button-text-only ui-widget ui-state-default ui-corner-all'></td></tr>");
            sb.append("</table>");
            response.getWriter().write(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public ModelAndView processAssignUserVoucherSubType(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String companyId = com.getCompanyId();
        map.put("users", this.serviceFactory.getUmsService().getAllUsers("Y"));
        String userId = request.getParameter("user");
        String[] rightId = request.getParameterValues("rightId");
        if (userId != null) {
            boolean flag = this.serviceFactory.getUmsService().assignUserVoucherSubTypes(userId, rightId, user.getUsername(), companyId);
            if (flag) {
                map.put("msg", "<div class='ui-state-highlight ui-corner-all'><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;'></span>User Rights Assigned Successfully</div>");
            } else {
                map.put("msg", "<div class='ui-state-error ui-corner-all'><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span>Error in Assigning User Rights</div>");
            }
        }
        return new ModelAndView("ums/assignUserVoucherSubType", "refData", map);
    }

    /*
     @User Business unit
     */
    public ModelAndView assignBusinessUnit(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("users", this.serviceFactory.getUmsService().getAllUsers("Y"));
        return new ModelAndView("ums/assignBusinessUnit", "refData", map);
    }
    
    public ModelAndView processAssignProjects(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("users", this.serviceFactory.getUmsService().getAllUsers("Y"));
        User user = (User) request.getSession().getAttribute("user");
        String userId = request.getParameter("user");
        String[] businessUnitId = request.getParameterValues("businessUnitId");
        Company com = (Company) request.getSession().getAttribute("company");
        String companyId = com.getCompanyId();
        boolean flag = this.serviceFactory.getUmsService().assignUserBusinessUnit(userId, businessUnitId, user.getUsername());
        if (flag) {
            map.put("msg", "<div class='ui-state-highlight ui-corner-all'><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;'></span>Business Unit Assigned Successfully</div>");
        } else {
            map.put("msg", "<div class='ui-state-error ui-corner-all'><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span>Error in Assigning Business Unit.</div>");
        }
        return new ModelAndView("ums/assignBusinessUnit", "refData", map);
    }
    
    public ModelAndView viewUserRights(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        User user = (User) request.getSession().getAttribute("user");
        Company com = (Company) request.getSession().getAttribute("company");
        String moduleId = request.getSession().getAttribute("moduleId") != null ? (String) request.getSession().getAttribute("moduleId") : "";
        String companyId = com.getCompanyId();
        map.put("users", this.serviceFactory.getUmsService().getAllUsers("Y"));
        return new ModelAndView("ums/viewUserRights", "refData", map);
    }
    
    public void getUserAssignedSites(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            String userName = request.getParameter("user");
            Company com = (Company) request.getSession().getAttribute("company");
            String moduleId = request.getSession().getAttribute("moduleId") != null ? (String) request.getSession().getAttribute("moduleId") : "";
            String companyId = com.getCompanyId();
            List sitesList = this.serviceFactory.getUmsService().getAllSitesByUser(userName, companyId);
            StringBuilder sb = new StringBuilder();
            sb.append("<table width='100%' class='ui-widget table-style'><thead><tr >"
                    + "<th align='center'>Site Name</th></tr><t/head>");
            sb.append("<tbody>");
            for (int k = 0; k < sitesList.size(); k++) {
                Site site = (Site) sitesList.get(k);
                sb.append("<tr><td align='left' >");
                sb.append(site.getSiteName());
                sb.append("</td>");
                sb.append("</tr>");
                
            }
            sb.append("</tbody>");
            sb.append("</table>");
            response.getWriter().write(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void validateUserName(HttpServletRequest request, HttpServletResponse response) {
        try {
            String userName = request.getParameter("userName");
            Company com = (Company) request.getSession().getAttribute("company");
            String companyId = com.getCompanyId();
            boolean flag = this.serviceFactory.getUmsService().isUserExists(userName, companyId);
            JSONObject obj = new JSONObject();
            if (flag) {
                obj.put("msg", "invalid");
            } else {
                obj.put("msg", "valid");
            }
            response.getWriter().write(obj.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void saveUser(HttpServletRequest request, HttpServletResponse response, User po) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        po.setUserName(userName);
        String userType = request.getSession().getAttribute("userType").toString();
        po.setUserType(userType);
        boolean flag = this.serviceFactory.getUmsService().saveUsers(po);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void getUserById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        Company com = (Company) request.getSession().getAttribute("company");
        Map map = this.serviceFactory.getUmsService().getUsersById(userId);
        JSONObject obj = new JSONObject();
        if (map != null) {
            Iterator<Map.Entry<String, Object>> itr = map.entrySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next().getKey();
                obj.put(key, map.get(key) != null ? map.get(key).toString() : "");
            }
        }
        response.getWriter().write(obj.toString());
    }
    
    public void updatePassword(HttpServletRequest request, HttpServletResponse response, User po) throws IOException {
        Company com = (Company) request.getSession().getAttribute("company");
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        boolean flag = this.serviceFactory.getUmsService().updatePassword(po);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    
    public void updateUserStatus(HttpServletRequest request, HttpServletResponse response, User po) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = "";
        if (user != null) {
            userName = user.getUsername();
        }
        String userNme = request.getParameter("uerName");
        String statusInd = request.getParameter("statusInd");
        boolean flag = this.serviceFactory.getUmsService().updateUserStatus(userNme, statusInd);
        JSONObject obj = new JSONObject();
        if (flag) {
            obj.put("result", "save_success");
        } else {
            obj.put("result", "save_error");
        }
        response.getWriter().write(obj.toString());
    }
    //Mobile Application rights

    public ModelAndView assignUserMobileRights(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("rightName", "Mobile Rights");
        return new ModelAndView("ums/assignMobileRights", "refData", map);
    }
    
    public void getMobileRights(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            String userName = request.getParameter("userName");
            Company com = (Company) request.getSession().getAttribute("company");
            List<Map> rightList = this.serviceFactory.getUmsService().getAllMobileRights();
            List<Map> userRightsList = this.serviceFactory.getUmsService().getUserMobileRights(userName);
            StringBuilder sb = new StringBuilder();
            sb.append("<table width='80%' class='table table-striped table-bordered'><thead><tr >"
                    + "<th>Right Name</th><th align='center'><input type='checkbox'  class='selectAllRightsBtn'>&nbsp;&nbsp; Assign</th></tr></thead>");
            sb.append("<tbody>");
            for (int k = 0; k < rightList.size(); k++) {
                Map parent = (Map) rightList.get(k);
                String parentId = parent.get("TW_MOBILE_RIGHTS_ID").toString();
                sb.append("<tr><td>");
                sb.append(parent.get("RIGHT_NME").toString());
                sb.append("</td>");
                sb.append("<td>");
                sb.append("<input type='checkbox' name='selectedRight' class='assignRight' value='" + parent.get("TW_MOBILE_RIGHTS_ID").toString() + "' ");
                for (int j = 0; j < userRightsList.size(); j++) {
                    Map inner = userRightsList.get(j);
                    String id = inner.get("TW_MOBILE_RIGHTS_ID").toString();
                    if (parentId.equalsIgnoreCase(id)) {
                        sb.append(" checked='checked'");
                    }
                }
                sb.append(" >");
                sb.append("</td>");
                sb.append("</tr>");
            }
            sb.append("</tbody>");
            sb.append("</table>");
            sb.append("<table width='80%' class='ui-widget'>");
            sb.append("<tr><td  ><button type='button' class='btn blue' onclick='saveUserRights();'><i class='fa fa-floppy-o'></i> Save</button></td></tr>");
            sb.append("</table>");
            response.getWriter().write(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void processAssignMobileRights(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String userName = request.getParameter("userName");
        String[] rightIdArr = request.getParameterValues("rightIdArr[]");
        JSONObject obj = new JSONObject();
        boolean flag = this.getServiceFactory().getUmsService().assignMobileRights(userName, rightIdArr, user.getUsername());
        if (flag) {
            obj.put("msg", "saved");
        } else {
            obj.put("msg", "error");
        }
        response.getWriter().write(obj.toString());
    }
}
