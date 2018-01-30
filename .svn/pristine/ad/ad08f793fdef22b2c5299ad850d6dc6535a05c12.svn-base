<%-- 
    Document   : assignBusinessUnit
    Created on : Feb 27, 2014, 12:47:00 PM
    Author     : Faraz
--%>
<%@include file="../header.jsp"%>
<script type="text/javascript">
    var ajax_load = "<img src='images/loading.gif' alt='loading...' />";
    $.ajaxSetup({
        cache: false
    });

    $(document).ready(function() {
        $("#user").change(function() {
            $("#unitList").html(ajax_load);
            $.get('ums.htm?action=getUserBusinessUnit', {userId: $("#user").val()}, function(data) {
                $("#unitList").html(data);
            }, 'html');
        }).trigger('change');

    });
    function selectAll(param) {
        var units = document.getElementsByName("businessUnitId");
        if (param.checked) {
            for (var i = 0; i < units.length; i++) {
                units[i].checked = true;
            }
        } else {
            for (var i = 0; i < units.length; i++) {
                units[i].checked = false;
            }
        }
    }
</script>
<div id="mainBody">
    <h1>Assign Business Unit</h1>
    <form method="post" action="ums.htm?action=processAssignProjects">
        <table >
            <tr>
                <td colspan="2">
                    <div style="height: 20px;">
                        ${requestScope.refData.msg}
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    User
                </td>
                <td>
                    <select name="user" id="user">
                        <c:forEach items="${requestScope.refData.users}" var="user"> 
                            <c:set var="userName" value="${user.username}"></c:set>
                            <option value="${userName}">${userName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <h2>Business Unit</h2>
        <div id="unitList" style='font: 12px  sans-serif;'>

        </div>
    </form>
</div>
<%@include file="../footer.jsp"%>
