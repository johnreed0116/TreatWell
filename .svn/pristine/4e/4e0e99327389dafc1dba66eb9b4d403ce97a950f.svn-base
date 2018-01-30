<%-- 
    Document   : addRole
    Created on : Jul 5, 2011, 6:07:17 PM
    Author     : Faraz
--%>

<%@include file="../header.jsp"%>
<script>
    function validateForm(){
        if(!validate('roleName','req','roleNameLabel')){
            return false;
        }
       
        
        return true;
    }
    
</script>
<div id="mainBody">
    <form method="post" action="ums.htm?action=addRole" onsubmit="return validateForm();">
        <input type="hidden" name="roleId" value="${requestScope.refData.role.roleId}"></input>
        <h1>Role</h1>
        <table >
            <tr>
                <td colspan="3">
                    <div style="height: 20px;">
                        ${requestScope.refData.msg}
                    </div>
                </td>
            </tr>
            <tr>
                <td>Role Name*</td>
                <td>
                    <input type="text" name="roleName" id="roleName" value="${requestScope.refData.role.roleName}" maxlength="150">
                </td>
                   <td ><div id="roleNameLabel" style="height: 20px;"></div></td>
            </tr>
            <tr>
                <td>Role Description</td>
                <td colspan="2">
                    <textarea id="roleDesc" name="roleDesc" cols="40">${requestScope.refData.role.roleDesc}</textarea>
                </td>
                
            </tr>
            <tr>
                <td colspan="3" style="height: 15px;"></td>
            </tr>
            <tr>
                <td colspan="3" align="center">
                    <input type="submit" value="Save"></input>
                </td>
            </tr>
        </table>
    </form>
</div>
<%@include file="../footer.jsp"%>
