<%-- 
    Document   : addUser
    Created on : Jul 4, 2011, 5:23:37 PM
    Author     : Faraz
--%>
<%@include file="../header.jsp"%>
<script language='javascript' src="js/md5.js" type="text/javascript"></script>
<script>
    function updatePassword() {
        if ($('#userName').val() === '') {
            $('#userName').notify('Please enter user name.', 'error');
            $('#userName').focus();
            return false;
        }
        if ($('#oldPassword').val() === '') {
            $('#oldPassword').notify('Please enter current password.', 'error');
            $('#oldPassword').focus();
            return false;
        }
        if ($('#newPassword').val() === '') {
            $('#newPassword').notify('Please enter new password.', 'error');
            $('#newPassword').focus();
            return false;
        }
        if ($('#retypePassword').val() === '') {
            $('#retypePassword').notify('Please enter new password again.', 'error');
            $('#retypePassword').focus();
            return false;
        }
        if ($('#newPassword').val() !== '' && $('#retypePassword').val() !== '') {
            if ($('#newPassword').val() !== $('#retypePassword').val()) {
                $('#retypePassword').notify("Please enter password same as new password.", "error");
                return false;
            }
            var password = calcMD5($('#newPassword').val());
            var oldPassword = calcMD5($('#oldPassword').val());
            $.post('login.htm?action=processChangePassword', {userName: $('#userName').val(), password: password, oldPassword: oldPassword}, function (res) {
                if (res.msg === 'saved') {
                    $.bootstrapGrowl("Password changed successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#newPassword').val('');
                    $('#retypePassword').val('');
                    $('#oldPassword').val('');
                } else {
                    $.bootstrapGrowl("Error in changing password. Please contact with system administrator.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                }
            }, 'json');
        }
    }
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Change Password</h1>
    </div>
</div>
<div class="portlet box green">
    <div class="portlet-title">
        <div class="caption">
            Account Password
        </div>
    </div>
    <div class="portlet-body">
        <form method="post" action="login.htm?action=processChangePassword" onsubmit="return false;">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>User Name</label>
                        <input type="text" class="form-control" value="${sessionScope.userName}" id="userName" name="userName" readonly="">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Current Password</label>
                        <input type="password" class="form-control" value="" name="oldPassword" id="oldPassword" placeholder="Current Password" >
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>New Password</label>
                        <input type="password" class="form-control" value="" name="newPassword" id="newPassword" placeholder="New Password" >
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Retype Password</label>
                        <input type="password" class="form-control" value="" name="retypePassword" id="retypePassword" placeholder="Retype Password">
                    </div>
                </div>
            </div>
        </form>
        <button class="btn blue" onclick="updatePassword();">Update</button>
    </div>
</div>

<%@include file="../footer.jsp"%>
