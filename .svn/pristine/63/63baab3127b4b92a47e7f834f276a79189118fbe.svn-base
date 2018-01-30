<%-- 
    Document   : editUser
    Created on : Jul 5, 2011, 4:36:39 PM
    Author     : Faraz
--%>

<%@include file="../header.jsp"%>
<script type="text/javascript">
    function editUser(id){
        
    }
</script>
<div id="mainBody">
    <h1>Edit User</h1>
    <p>${requestScope.refData.msg}</p>
    <table cellspacing="2" cellpadding="2" class="mainTable">
        <tr>
            <th>User Name</th>
            <th>Full Name</th>
            <th>Active</th>
            <th>Option</th>
        </tr>
        <c:forEach items="${requestScope.refData.list}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>${user.fullname}</td>
                <td>${user.active}</td>
                <td><a href="ums.htm?action=editUser&userId=${user.id}">Edit</a></td>
            </tr>
        </c:forEach>
    </table>
    <hr/>   

</div>
<%@include file="../footer.jsp"%>

