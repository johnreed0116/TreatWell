<%-- 
    Document   : assignMobileRights
    Created on : Feb 27, 2018, 6:39:35 PM
    Author     : farazahmad
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
        $.get('ums.htm?action=getMobileRights', {userName: $('#userList').val()}, function (htm) {
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
            $('input:checkbox').iCheck({
                checkboxClass: 'icheckbox_minimal',
                radioClass: 'iradio_minimal',
                increaseArea: '20%' // optional
            });
        }, 'html');
    }
    function saveUserRights() {
        if ($.trim($('#userList').val()) === '') {
            $('#userList').notify('User Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#userList').focus();
            return false;
        }
        var arr = [];
        var selectedRight = document.getElementsByName("selectedRight");
        for (var i = 0; i < selectedRight.length; i++) {
            if ($(selectedRight[i]).is(':checked')) {
                arr.push(selectedRight[i].value);
            }
        }
        $.post('ums.htm?action=processAssignMobileRights', {userName: $('#userList').val(), 'rightIdArr[]': arr}, function (res) {
            if (res.msg === 'saved') {
                $.bootstrapGrowl("Rights saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
            } else {
                $.bootstrapGrowl("Error in saving rights. Please try again later.", {
                    ele: 'body',
                    type: 'error',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
            }
        }, 'json');
        return false;
    }
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Mobile Rights Management</h1>
    </div>
</div>
<input type="hidden" id="res_msg" value="${requestScope.refData.msg}">
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Mobile Rights
                </div>
            </div>
            <div class="portlet-body">
                <form action="ums.htm?action=processAssignMobileRights" role="form" method="post" id="form_1" onsubmit="return false;">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-12">
                                <c:choose>
                                    <c:when test="${sessionScope.userType=='ADMIN'}">
                                        <div class="form-group">
                                            <label>Account Type</label>
                                            <select class="form-control" id="accountType" class="form-control select2_category" data-placeholder="Choose an Account Type" onchange="displayData();">
                                                <option value="Doctor">Doctor</option>
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
