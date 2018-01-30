<%-- 
    Document   : assignRole
    Created on : Jul 7, 2011, 5:33:05 PM
    Author     : Faraz
--%>


<%@include file="../header.jsp"%>
<script type="text/javascript">
    var ajax_load = "<img src='images/loading.gif' alt='loading...' />";  
    $.ajaxSetup ({  
        cache: false  
    }); 
    $(document).ready(function(){
        $("#user").change(function () {
            var str = $("#user").val();
            var loadUrl = "ums.htm?action=getUserRoles&userId="+str;   
            $("#roleList").html(ajax_load).load(loadUrl );  
        })
        .trigger('change');           
    });
</script>
<div id="mainBody">
    <h1>Assign Roles</h1>
    <form method="post" action="ums.htm?action=processAssignRoles">
        <table cellspacing="0" cellpadding="0" width="30%" >
            <tr>
                <td colspan="2">
                    <div style="height: 20px;">
                        ${requestScope.refData.msg}
                    </div>
                </td>
            </tr>
            <tr>
                <td>Select User*&nbsp;&nbsp;
                    <select name="user" id="user">
                        <c:forEach items="${requestScope.refData.users}" var="user"> 
                            <c:set var="userName" value="${user.username}"></c:set>
                            <c:if test="${userName != 'super_user'}">
                                <option value="${userName}">${userName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <h2>Roles</h2>
        <div id="roleList" style='font: 12px  sans-serif;'>

        </div>
    </form>
</div>
<%@include file="../footer.jsp"%>