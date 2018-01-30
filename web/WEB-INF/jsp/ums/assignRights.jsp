<%-- 
    Document   : AssignRights
    Created on : Jul 5, 2011, 9:14:16 PM
    Author     : Faraz
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        if ($('#res_msg').val() !== '') {
            if ($('#res_msg').val() === 'saved') {
                $.bootstrapGrowl("User rights saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
            } else {
                $.bootstrapGrowl("Error in saving user rights. ", {
                    ele: 'body',
                    type: 'danger',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
            }
        }
        $('#userList').select2();

        $('#accountType').change(function () {
            getUsers();
        });
        getUsers();
        //displayRights();
    });

    function getUsers() {
        $('#userList').find('option').remove();
        $.get('ums.htm?action=getAllUsers', {accountType: $('#accountType').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        for (var i = 0; i < list.length; i++) {
                            var newOption = new Option(list[i].USER_NME + ' [' + list[i].USER_NME + ']', list[i].USER_NME, false, false);
                            $('#userList').append(newOption);
                        }
                    } else {
                        var newOption = new Option('No user found.', '', true, false);
                        $('#userList').append(newOption);
                    }
                    $('#userList').trigger('change');
                }, 'json');
    }
    function displayRights() {
        $.get('ums.htm?action=getUserRights', {userName: $('#userList').val()}, function (htm) {
            $('#displayDiv').html(htm);
            $('.selectAllRightsBtn').on('ifChecked', function (event) {
                $("input.assignRight").each(function (i, o) {
                    $(o).iCheck('check');
                });
            });
            $('.selectAllRightsBtn').on('ifUnchecked', function (event) {
                $("input.assignRight").each(function (i, o) {
                    $(o).iCheck('uncheck');
                });
            });

            $('.selectAllAddBtn').on('ifChecked', function (event) {
                $("input.canAdd").each(function (i, o) {
                    $(o).iCheck('check');
                });
            });
            $('.selectAllAddBtn').on('ifUnchecked', function (event) {
                $("input.canAdd").each(function (i, o) {
                    $(o).iCheck('uncheck');
                });
            });

            $('.selectAllEditBtn').on('ifChecked', function (event) {
                $("input.canEdit").each(function (i, o) {
                    $(o).iCheck('check');
                });
            });
            $('.selectAllEditBtn').on('ifUnchecked', function (event) {
                $("input.canEdit").each(function (i, o) {
                    $(o).iCheck('uncheck');
                });
            });

            $('.selectAllDeleteBtn').on('ifChecked', function (event) {
                $("input.canDelete").each(function (i, o) {
                    $(o).iCheck('check');
                });
            });
            $('.selectAllDeleteBtn').on('ifUnchecked', function (event) {
                $("input.canDelete").each(function (i, o) {
                    $(o).iCheck('uncheck');
                });
            });
            $('input:checkbox').iCheck({
                checkboxClass: 'icheckbox_minimal',
                radioClass: 'iradio_minimal',
                increaseArea: '20%' // optional
            });
        }, 'html');
    }
    function saveUserRights() {
        $('#form_1').submit();
    }
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Rights Management</h1>
    </div>
</div>
<input type="hidden" id="res_msg" value="${requestScope.refData.msg}">
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    User Rights
                </div>
            </div>
            <div class="portlet-body">
                <form action="ums.htm?action=processAssignRights" role="form" method="post" id="form_1">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-12">
                                <c:choose>
                                    <c:when test="${sessionScope.userType=='ADMIN'}">
                                        <div class="form-group">
                                            <label>Account Type</label>
                                            <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                                <option value="Admin">Administrator</option> 
                                                <option value="Doctor">Doctor</option>
                                                <option value="Patient">Patient</option>
                                                <option value="Hospital">Hospital</option>
                                                <option value="Pharmaceutical">Pharmaceutical</option> 
                                                <option value="Pharmacy">Pharmacy</option> 
                                                <option value="Lab">Lab</option>
                                            </select>
                                        </div>
                                    </c:when>
                                    <c:when test="${sessionScope.userType=='DOCTOR'}">
                                        <div class="form-group">
                                            <label>Account Type</label>
                                            <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                                <option value="Doctor">Doctor</option>
                                            </select>
                                        </div>
                                    </c:when>
                                    <c:when test="${sessionScope.userType=='PHARMA'}">
                                        <div class="form-group">
                                            <label>Account Type</label>
                                            <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                                <option value="Pharmaceutical">Pharmaceutical</option>
                                            </select>
                                        </div>
                                    </c:when>
                                    <c:when test="${sessionScope.userType=='LAB'}">
                                        <div class="form-group">
                                            <label>Account Type</label>
                                            <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                                <option value="Lab">Lab</option>
                                            </select>
                                        </div>
                                    </c:when>
                                    <c:when test="${sessionScope.userType=='MEDICAL_STORE'}">
                                        <div class="form-group">
                                            <label>Account Type</label>
                                            <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                                <option value="Pharmacy">Pharmacy</option>
                                            </select>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>User Name</label>
                                    <select id="userList" name="userList" class="form-control" onchange="displayRights();">
                                        <option value="">Select User</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div id="displayDiv">

                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>