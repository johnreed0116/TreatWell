<%-- 
    Document   : editRole
    Created on : Jul 5, 2011, 8:50:24 PM
    Author     : Faraz
--%>

<%@include file="../header.jsp"%>
<div id="mainBody">
    <h1>Edit Role</h1>
    <p>${requestScope.refData.msg}</p>
    <table cellspacing="2" cellpadding="2" class="mainTable">
        <tr>
            <th>Role Name</th>
            <th>Option</th>
        </tr>
        <c:forEach items="${requestScope.refData.list}" var="role">
            <tr>
                <td>${role.roleName}</td>
                <td><a href="ums.htm?action=editRole&roleId=${role.roleId}">Edit</a></td>
            </tr>
        </c:forEach>
    </table>
    <hr/>   

</div>
<%@include file="../footer.jsp"%>