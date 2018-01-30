<%-- 
    Document   : viewUserRights
    Created on : Jun 21, 2016, 2:15:35 PM
    Author     : Faraz
--%>
<%@include file="../header.jsp"%>
<script type="text/javascript">
    var ajax_load = "<img src='images/loading_horizontal.gif' alt='loading...' />";
    $.ajaxSetup({
        cache: false
    });
    $(document).ready(function () {
        $("#user").change(function () {
            var str = $("#user").val();
            $("#rightList").html('<div style="text-align:left;width:100%;">' + ajax_load + '</div>');
            $.get('ums.htm?action=getUserRights', {user: str}, function (htm) {
                $("#rightList").html(htm);
            }, 'html');
            $.get('ums.htm?action=getUserAssignedSites', {user: str}, function (htm) {
                $("#sitesList").html(htm);
            }, 'html');
        }).trigger('change');
    });
</script>
<div id="mainBody">
    <h1>User Rights</h1>
    <form method="post" action="#" id="assignRightsToRole">
        <table>
            <tr>
                <td>Select User</td>
                <td>
                    <select name="user" id="user">
                        <c:forEach items="${requestScope.refData.users}" var="user">
                            <c:if test="${user.username!='super_user'}">
                                <option value="${user.username}">${user.username}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <div style="width: 100%;float: left;">
            <div style="width: 70%;float:left;">
                <h2>Rights</h2>
                <div id="rightList" style='font: 12px  sans-serif;'>

                </div>
            </div>
            <div style="width: 30%;float:right;">
                <h2>Sites</h2>
                <div id="sitesList" style='font: 12px  sans-serif;'>

                </div>
            </div>
        </div>

    </form>
</div>
<%@include file="../footer.jsp"%>