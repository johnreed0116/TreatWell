<%-- 
    Document   : viewUsers
    Created on : Jan 3, 2012, 10:16:25 AM
    Author     : Faraz
--%>

<%@include file="../header.jsp"%>
<script language='javascript' src="js/md5.js" type="text/javascript"></script>
<script>
    $(function () {
        displayData();
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="20%">').html('Login ID'),
                $('<th class="center" width="20%">').html('Full Name'),
                $('<th class="center" width="10%">').html('Active'),
                $('<th class="center" width="30%">').html('Account Type'),
                $('<th class="center" width="15%" colspan="3">').html('Options')
                )));
        $.get('ums.htm?action=getAllUsers', {accountType: $('#accountType').val()},
                function (list) {
                    var userType;
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].USER_NME + '\');"></i>';
                            var passwordHtm = '<i class="fa fa-key" aria-hidden="true" title="Click to Change Password" style="cursor: pointer;" onclick="changePassword(\'' + list[i].USER_NME + '\');"></i>';
                            var banHtm = '<i class="fa fa-ban" aria-hidden="true" title="Click to ban user" style="cursor: pointer;" onclick="inactiveUser(\'' + list[i].USER_NME + '\');"></i>';
                            if (list[i].TW_DOCTOR_ID !== '') {
                                userType = "Doctor";
                                editHtm = '&nbsp;';
                            } else if (list[i].TW_PATIENT_ID !== '') {
                                userType = "Patient";
                                editHtm = '&nbsp;';
                            } else if (list[i].TW_PHARMACEUTICAL_ID !== '') {
                                userType = list[i].COMPANY_NME;
                                editHtm = '&nbsp;';
                            } else if (list[i].TW_CLINIC_ID !== '') {
                                userType = list[i].CLINIC_NME;
                                editHtm = '&nbsp;';
                            } else {
                                userType = "Admin";
                            }
                            if (list[i].ACTIVE_IND !== 'Y') {
                                editHtm = '&nbsp;';
                                passwordHtm = '&nbsp;';
                                banHtm = '<span class="label label-sm label-danger">Banned</span>';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].USER_NME),
                                    $('<td>').html(list[i].FIRST_NME),
                                    $('<td>').html((list[i].ACTIVE_IND === 'Y' ? 'Active' : 'InActive')),
                                    $('<td>').html(userType),
                                    $('<td align="center">').html(editHtm),
                                    $('<td  align="center">').html(passwordHtm),
                                    $('<td  align="center">').html(banHtm)
                                    ));
                        }
                        $('#displayDiv').html('');
                        $('#displayDiv').append($tbl);
                        return false;
                    } else {
                        $('#displayDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td  colspan="8">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }
    function saveData() {
        if ($.trim($('#userId').val()) === '') {
            if ($.trim($('#username').val()) === '') {
                $('#username').notify('User Name is Required Field', 'error', {autoHideDelay: 15000});
                $('#username').focus();
                return false;
            }
            if ($.trim($('#password').val()) === '') {
                $('#password').notify('Password is Required Field', 'error', {autoHideDelay: 15000});
                $('#password').focus();
                return false;
            }
            if ($.trim($('#reTypePassword').val()) === '') {
                $('#reTypePassword').notify('ReType Password Field is Required', 'error', {autoHideDelay: 15000});
                $('#reTypePassword').focus();
                return false;
            }
            if ($.trim($('#password').val()) !== $.trim($('#reTypePassword').val())) {
                $('#reTypePassword').notify('Please corfirm the same password', 'error', {autoHideDelay: 15000});
                $('#reTypePassword').focus();
                return false;
            }
        }
        var password = calcMD5($('#password').val());
        var newPassword = calcMD5($('#newPassword').val());
        if ($('#processType').val() === 'Edit') {
            var obj = {
                userId: $('#userId').val(),
                firstName: $('#fullName').val(),
                nameOfUser: $('#nameOfUser').val(),
                email: $('#email').val(),
                isNewAdminUser: $('#isNewAdminUser').val()
            };
        } else {
            var obj = {
                userId: $('#userId').val(),
                newPassword: newPassword,
                firstName: $('#fullName').val(),
                username: $('#username').val(),
                nameOfUser: $('#nameOfUser').val(),
                email: $('#email').val(),
                password: password,
                isNewAdminUser: $('#isNewAdminUser').val()

            };
        }
        $.post('ums.htm?action=saveUser', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("User Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#userId').val('');
                $('#username').val('');
                $('#newPassword').val('');
                displayData();
                $('#addUser').modal('hide');
                return false;
            } else {
                $.bootstrapGrowl("Error in saving User. Please try again later.", {
                    ele: 'body',
                    type: 'error',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                return false;
            }
        }, 'json');
        return false;
    }

    function addUserDialog() {
        $('#userId').val('');
        $('#fullName').val('');
        $('#nameOfUser').val('');
        $('#username').val('');
        $('#email').val('');
        $('#password').val('');
        $('#reTypePassword').val('');
        $('#newPassword').val('');
        $('#reTypeNewPassword').val('');
        $('#loginDetails').show();
        $('#processType').val('Add');
        $('#addUser').modal('show');
    }
    function editRow(id) {
        $('#userId').val(id);
        $('#loginDetails').hide();
        $('#processType').val('Edit');
        $('#info').show();
        $.get('ums.htm?action=getUserById', {userId: id},
                function (obj) {
                    $('#fullName').val(obj.FIRST_NME);
                    $('#email').val(obj.EMAIL);
                    $('#addUser').modal('show');
                }, 'json');
    }

    function changePassword(id) {
        $('#userId').val(id);
        $.get('ums.htm?action=getUserById', {userId: id},
                function (obj) {
                    $('#nameOfUser').val(obj.USER_NME);
                    $('#changePassword').modal('show');
                }, 'json');
    }
    function updatePassword() {
        if ($.trim($('#newPassword').val()) === '') {
            $('#newPassword').notify('Password is Required Field', 'error', {autoHideDelay: 15000});
            $('#newPassword').focus();
            return false;
        }
        if ($.trim($('#reTypeNewPassword').val()) === '') {
            $('#reTypeNewPassword').notify('ReType Password Field is Required', 'error', {autoHideDelay: 15000});
            $('#reTypeNewPassword').focus();
            return false;
        }
        if ($.trim($('#newPassword').val()) !== $.trim($('#reTypeNewPassword').val())) {
            $('#reTypeNewPassword').notify('Please corfirm the same password', 'error', {autoHideDelay: 15000});
            $('#reTypeNewPassword').focus();
            return false;
        }

        var newPassword = calcMD5($('#newPassword').val());
        var obj = {
            userId: $('#userId').val(),
            newPassword: newPassword
        };
        $.post('ums.htm?action=updatePassword', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("User Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#userId').val('');
                $('#newPassword').val('');
                $('#reTypeNewPassword').val('');
                $('#changePassword').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving User. Please try again later.", {
                    ele: 'body',
                    type: 'error',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                return false;
            }
        }, 'json');
        return false;
    }
    function inactiveUser(userNme) {
        bootbox.confirm({
            message: "Do you want to in active user? User will not be able to access the system?",
            buttons: {
                confirm: {
                    label: 'Yes',
                    className: 'btn-success'
                },
                cancel: {
                    label: 'No',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if (result) {
                    $.post('ums.htm?action=updateUserStatus', {uerName: userNme, statusInd: 'N'}, function (obj) {
                        if (obj.result === 'save_success') {
                            $.bootstrapGrowl("User Data updated successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            displayData();
                            return false;
                        } else {
                            $.bootstrapGrowl("Error in updating User. Please try again later.", {
                                ele: 'body',
                                type: 'danger',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            return false;
                        }
                    }, 'json');

                }
            }
        });

        return false;
    }

</script>
<input type="hidden" id="processType">
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Users</h1>
    </div>
</div>
<div class="modal fade" id="changePassword">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Change Password</h3>
            </div>
            <div class="modal-body">
                <input type="hidden" id="userId" value=""> 
                <div class="portlet box green">
                    <div class="portlet-title tabbable-line">
                        <div class="caption">
                            New Password 
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>User Name</label>
                                    <input type="text" class="form-control" id="nameOfUser" placeholder="User Name" readonly>
                                </div>
                            </div>
                        </div>  
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Password</label>
                                    <input type="password" class="form-control" id="newPassword" placeholder="Password" >
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Re-Type Password</label>
                                    <input type="password" class="form-control" id="reTypeNewPassword" placeholder="Re-Type Password" >
                                </div>
                            </div>
                        </div>
                    </div>   
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="updatePassword();">Update </button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="addUser">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add User</h3>
            </div>
            <div class="modal-body">
                <input type="hidden" id="userId" value="">
                <div class="portlet box green" id="loginDetails">
                    <div class="portlet-title tabbable-line">
                        <div class="caption">
                            Login Details
                        </div>
                    </div>  
                    <div class="portlet-body">
                        <input type="hidden" id="isNewAdminUser" value="N">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group required">
                                    <label>Login ID*</label>
                                    <div>
                                        <input type="text" class="form-control" id="username" placeholder="Login ID" onblur="Util.validateUser(this);" onkeyup="onlyCharForLoginId(this);" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Password*</label>
                                    <div>
                                        <input type="password" class="form-control" id="password" placeholder="Password" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Re-Type Password*</label>
                                    <div>
                                        <input type="password" class="form-control" id="reTypePassword" placeholder="Re-Type Password" >
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="portlet box green">
                    <div class="portlet-title tabbable-line">
                        <div class="caption">
                            Personal Info 
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Full Name</label>
                                    <div>
                                        <input type="text" class="form-control" id="fullName" placeholder="Full Name" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Email</label>
                                    <div>
                                        <input type="text" class="form-control" id="email" placeholder="Email" >
                                    </div>
                                </div>
                            </div>
                        </div>   
                    </div>
                </div>       
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveData();">Save</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>

</div>

<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Searching Criteria
                </div>
            </div>
            <div class="portlet-body">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <c:choose>
                            <c:when test="${sessionScope.userType=='ADMIN'}">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>Account Type</label>
                                        <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                            <option value="Admin">Administrator</option> 
                                            <option value="Doctor">Doctor</option>
                                            <option value="Patient">Patient</option>
                                            <option value="Clinic">Hospital</option>
                                            <option value="Pharmaceutical">Pharmaceutical</option>
                                            <option value="Pharmacy">Pharmacy</option> 
                                            <option value="Lab">Lab</option>
                                        </select>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.userType=='DOCTOR'}">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>Account Type</label>
                                        <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                            <option value="Doctor">Doctor</option>
                                        </select>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.userType=='PHARMA'}">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>Account Type</label>
                                        <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                            <option value="Pharmaceutical">Pharmaceutical</option>
                                        </select>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.userType=='LAB'}">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>Account Type</label>
                                        <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                            <option value="Lab">Lab</option>
                                        </select>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.userType=='MEDICAL_STORE'}">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>Account Type</label>
                                        <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                            <option value="Pharmacy">Pharmacy</option>
                                        </select>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.userType=='CLINIC'}">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>Account Type</label>
                                        <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                            <option value="Clinic">Hospital</option>
                                        </select>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                        <div class="col-md-4" style="padding-top: 23px;">
                            <c:if test="${sessionScope.userType=='ADMIN'}">
                                <button type="button" onclick="addUserDialog();" class="btn blue"><i class="fa fa-plus-circle"></i>Admin User</button>
                            </c:if>
                            <c:if test="${sessionScope.userType!='ADMIN'}">
                                <c:if test="${sessionScope.userType!='LAB' and sessionScope.userType!='MEDICAL_STORE'}">
                                    <button type="button"  onclick="addUserDialog();" class="btn blue"><i class="fa fa-plus-circle"></i>Add User</button>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div id="displayDiv">

                            </div>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="../footer.jsp"%>