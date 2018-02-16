<%-- 
    Document   : addHospitalStaff
    Created on : Feb 15, 2018, 4:56:40 PM
    Author     : Ali Zaidi
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
                $('<th class="center" width="25%">').html('User Name'),
                $('<th class="center" width="25%">').html('Full Name'),
                $('<th class="center" width="15%">').html('Email'),
                $('<th class="center" width="20%">').html('Clinic Name'),
                $('<th class="center" width="10%" colspan="2">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getHospitalEmployee', {clinicId: $('#clinicId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].USER_NME + '\');"></i>';
                            var passwordHtm = '<i class="fa fa-key" aria-hidden="true" title="Click to Change Password" style="cursor: pointer;" onclick="changePassword(\'' + list[i].USER_NME + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                passwordHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].USER_NME),
                                    $('<td>').html(list[i].FIRST_NME),
                                    $('<td>').html(list[i].EMAIL),
                                    $('<td>').html(list[i].CLINIC_NME),
                                    $('<td align="center">').html(editHtm),
                                    $('<td  align="center">').html(passwordHtm)
                                    ));
                        }
                        $('#displayDiv').html('');
                        $('#displayDiv').append($tbl);
                        return false;
                    } else {
                        $('#displayDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td  colspan="7">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }

    function saveData() {
        if ($.trim($('#employeeId').val()) === '') {
            if ($.trim($('#fullName').val()) === '') {
                $('#fullName').notify('Full Name is Required Field', 'error', {autoHideDelay: 15000});
                $('#fullName').focus();
                return false;
            }
            if ($.trim($('#loginId').val()) === '') {
                $('#loginId').notify('Login Id is Required Field', 'error', {autoHideDelay: 15000});
                $('#loginId').focus();
                return false;
            }
        }
        if ($.trim($('#contactNo').val()) === '') {
            $('#contactNo').notify('Contact No is Required Field', 'error', {autoHideDelay: 15000});
            $('#contactNo').focus();
            return false;
        }

        var obj = {
            fullName: $('#fullName').val(),
            email: $('#email').val(),
            clinicId: $('#clinicId').val(),
            employeeId: $('#employeeId').val(),
            contactNo: $('#contactNo').val(),
            loginId: $('#loginId').val()
        };
        $.post('clinic.htm?action=saveHospitalEmployee', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Employee Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#employeeId').val('');
                $('#addStaff').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Employee. Please try again later.", {
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
        return false;
    }

    function deleteRow(id) {
        bootbox.confirm({
            message: "Do you want to delete record?",
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
                    $.post('clinic.htm?action=deleteHospitalStaff', {id: id}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl("Record deleted successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            displayData();
                        } else {
                            $.bootstrapGrowl("Record can not be deleted.", {
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
        });
    }
    function changePassword(id) {
        $('#userId').val(id);
        $.get('ums.htm?action=getUserById', {userId: id},
                function (obj) {
                    $('#nameOfUser').val(obj.USER_NME);
                    $('#changePassword').modal('show');
                }, 'json');
    }

    function addStaffDialog() {
        $('#employeeId').val('');
        $('#fullName').val('');
        $('#loginId').val('');
        $('#email').val('');
        $('#contactNo').val('');
        $('#loginId').prop('disabled', false);
        $('#addStaff').modal('show');
    }
    function editRow(id) {
        $('#employeeId').val(id);
        $.get('clinic.htm?action=getHospitalEmployeeById', {employeeId: id},
                function (obj) {
                    $('#fullName').val(obj.FIRST_NME);
                    $('#email').val(obj.EMAIL);
                    $('#contactNo').val(obj.CONTACT_NO);
                    $('#loginId').val(obj.USER_NME);
                    $('#loginId').prop('disabled', true);
                    $('#addStaff').modal('show');
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

</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Hospital Staff </h1>
    </div>
</div>
<div class="modal fade" id="addStaff">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Employee</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="employeeId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Full Name *</label>
                                <div>
                                    <input type="text" class="form-control" id="fullName" placeholder="Full Name" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Email</label>
                                <div>
                                    <input type="text" class="form-control" id="email" placeholder="Email" >
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Contact No *</label>
                                <div>
                                    <input type="text" class="form-control" onkeyup="onlyInteger(this);" id="contactNo" placeholder="Contact no" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Login ID*</label>
                                <div>
                                    <input type="text" class="form-control" onkeyup="onlyCharForLoginId(this);" onblur="Util.validateUser(this);" id="loginId" placeholder="Login ID" >
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
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
                    Hospital Staff
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-10">
                            <div class="form-group">
                                <label>Clinic Name</label>
                                <select id="clinicId" class="form-control select2_category" onChange="displayData();" data-placeholder="Choose a Clinic">       
                                    <c:forEach items="${requestScope.refData.clinic}" var="obj">
                                        <option value="${obj.TW_CLINIC_ID}">${obj.CLINIC_NME}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-2" style="padding-top: 23px;">
                            <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                <button type="button" class="btn blue" onclick="addStaffDialog();"><i class="fa fa-plus-circle"></i> Add Employee</button>
                            </c:if>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-12">
                            <div id="displayDiv"></div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
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
<%@include file="../footer.jsp"%>

