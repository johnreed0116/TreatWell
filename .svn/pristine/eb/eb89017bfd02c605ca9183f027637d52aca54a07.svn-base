<%-- 
    Document   : assignUserVoucherSubType
    Created on : Apr 21, 2012, 6:12:30 PM
    Author     : abbas
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
            var loadUrl = "ums.htm?action=getUserVoucherSubTypes&userId="+str;   
            $("#typesList").html(ajax_load).load(loadUrl );              
        })
        .trigger('change');
           
    });
</script>
<div id="mainBody">
    <h1>Assign User Voucher Sub Types</h1>
    <form method="post" action="ums.htm?action=processAssignUserVoucherSubType">
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
                                <option value="${userName}">${userName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <h2>Voucher Sub Types</h2>
        <div id="typesList" style='font: 12px  sans-serif;'>

        </div>
    </form>
</div>
<%@include file="../footer.jsp"%>
