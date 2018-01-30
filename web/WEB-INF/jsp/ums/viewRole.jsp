<%-- 
    Document   : viewUsers
    Created on : Jan 3, 2012, 10:16:25 AM
    Author     : Faraz
--%>

<%@include file="../header.jsp"%>
<div id="mainBody">
    <h1>Roles</h1>
    <form id="listform" action="ums.htm?action=addRole" method="post">
        <table>
            <tr>
                <td>
                    <div style="height: 20px;">
                        ${requestScope.refData.msg}
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Add Role" class="ui-button ui-button-text-only ui-widget ui-state-default ui-corner-all" ></input>
                </td>
            </tr>
        </table>

        <table width="100%" class="table-style" >
            <tr >
                <th align="center">Sr. #</th>
                <th align="center">Role Name</th>
                <th align="center" colspan="2" >Options</th>
            </tr>
            <tbody>
                <c:forEach items="${requestScope.refData.roles}" var="role" varStatus="i">
                    <c:if test="${role.roleName != 'super_role'}">
                        <tr >
                            <td align="center" >${i.count}</td>
                            <td align="left" >${role.roleName}</td>
                            <td align="right" >
                                <div class="ui-icon ui-icon-pencil" title="Click to Edit" onclick="editRecord('${role.roleId}', 'listform', 'ums.htm?action=editRole', 'roleId');"></div>
                            </td>
                            <td align="left" >
                                <div class="ui-icon ui-icon-trash" title="Click to Delete" onclick="deleteRecord('${role.roleId}', 'listform', 'ums.htm?action=deleteRole', 'roleId');"></div>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>   
    </form>
</div>

<%@include  file="../footer.jsp" %>