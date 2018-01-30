<%-- 
    Document   : addUser
    Created on : Jul 4, 2011, 5:23:37 PM
    Author     : Faraz
--%>
<%@include file="../header.jsp"%>
<script language='javascript' src="js/md5.js" type="text/javascript"></script>
<script>
    var ajax_load = "<img src='images/loading.gif' alt='loading...' />";  
    $.ajaxSetup ({  
        cache: false  
    });  
    function validateForm(){
        if(!validate('username','req','usernameLabel')){
            return false;
        }
        if(!validate('password','req','passwordLabel')){
            return false;
        }
        if(!validate('retypePassword','req','retypePasswordLabel')){
            return false;
        }
        if (!encryptPassword()){
            return false;
        }        
        if(document.getElementById("password").value != document.getElementById("retypePassword").value){
            document.getElementById("msg").innerHTML="<div class='ui-state-error ui-corner-all'><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span>Retype Password is not equal.</div>";
            return false;
        }        
        return true;
    }
    
    function encryptPassword(){
        var pass = document.getElementById('password').value;                
        var retypePass = document.getElementById('retypePassword').value;
        var flag=true;
        if(pass!='' && retypePass!=''){
            var encryptPass = calcMD5(pass);
            var encryptRetypePass = calcMD5(retypePass);
            document.getElementById('password').value = encryptPass;
            document.getElementById('retypePassword').value = encryptRetypePass;            
        }
        else {
            flag=false;
        }
        return flag;
    }

    function verifyUser(param){
        var val='';
        if(param != ''){
            var str = param.value;
            var loadUrl = "ums.htm?action=validateUserName&userName="+str;   
            $("#usernameLabel").load(loadUrl );  
            val=$("#usernameLabel").text();
            
        }else{
            document.getElementById("usernameLabel").innerHTML='';
            val='';
        }
        return val;
    }
    function toUppar(param){
        param.value=param.value.toUpperCase()
    }
</script>
<div id="mainBody">
    <form method="post" action="ums.htm?action=addUser" onsubmit="return validateForm();">
        <input type="hidden" name="id" value="${requestScope.refData.usr.username}"></input>
        <h1>User</h1>
        <table>
            <tr>
                <td colspan="3">
                    <div style="height: 20px;" id="msg">
                        ${requestScope.refData.msg}
                    </div>
                </td>
            </tr>
            <tr>
                <td>User Name*</td>
                <td><input type="text" name="username" id="username" value="${requestScope.refData.usr.username}" onkeyup="alphaNumeric(this);" onblur="verifyUser(this);toUppar(this);" maxlength="20"></td>
                <td ><div id="usernameLabel" style="height: 20px;"></div></td>
            </tr>
            <tr>
                <td>Password*</td>
                <td><input type="password" name="password" id="password" value="${requestScope.refData.usr.password}" maxlength="100"></td>
                <td ><div id="passwordLabel" style="height: 20px;"></div></td>
            </tr>
            <tr>
                <td>Re-type Password*</td>
                <td><input type="password" name="retypePassword" id="retypePassword" value="${requestScope.refData.usr.password}" maxlength="100"></td>
                <td ><div id="retypePasswordLabel" style="height: 20px;"></div></td>
            </tr>
            <tr>
                <td>Active*</td>
                <td><select id="active" name="active">
                        <option value="Y"
                                <c:if test="${requestScope.refData.usr.active == 'Y'}">
                                    selected
                                </c:if>
                                >YES</option>
                        <option value="N"
                                <c:if test="${requestScope.refData.usr.active == 'N'}">
                                    selected
                                </c:if>
                                >NO</option>
                    </select></td>
                <td></td>
            </tr>
            <tr>
                <td colspan="3" style="height: 15px;"></td>
            </tr>
            <tr>
                <td colspan="3" align="center">
                    <input type="submit" value="Save">
                </td>
            </tr>
        </table>
    </form>
</div>
<%@include file="../footer.jsp"%>
