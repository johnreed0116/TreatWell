<%-- 
    Document   : addStudent
    Created on : Mar 24, 2018, 5:33:38 PM
    Author     : Ali Zaidi
--%>

<%@include file="../header.jsp"%>
<script language='javascript' src="js/md5.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css"/>
<script type="text/javascript" src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
<script type="text/javascript" src="assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
<script>
    $(function () {
        $('#dob-picker').datepicker({
            format: 'dd-mm-yyyy',
            autoclose: true
        });
        $('#dob-picker').datepicker()
                .on('changeDate', function (e) {
                    var a = moment();
                    var b = moment($('#dob').val(), "DD-MM-YYYY");
                    var age = a.diff(b, 'years'); // 1
                    $('#age').val(age);
                });
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_minimal',
            radioClass: 'iradio_minimal',
            increaseArea: '20%' // optional
        });
        displayData();
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="25%">').html('Student Name'),
                $('<th class="center" width="15%">').html('Mobile No.'),
                $('<th class="center" width="15%">').html('Date of Birth'),
                $('<th class="center" width="30%">').html('Address'),
                $('<th class="center" width="5%" colspan="2">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getStudent', {},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_STUDENT_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_STUDENT_ID + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].STUDENT_NME),
                                    $('<td>').html(list[i].MOBILE_NO),
                                    $('<td>').html(list[i].DOB),
                                    $('<td>').html(list[i].ADDRESS),
                                    $('<td align="center">').html(editHtm),
                                    $('<td  align="center">').html(delHtm)
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
    function addStudent() {
        $('#studentId').val('');
        $('#studentName').val('');
        $('#dob').val('');
        $('#cellNo').val('');
        $('#age').val('');
        $('#address').val('');
        $('#addStudentDialog').modal('show');
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
                    $.post('setup.htm?action=deleteStudent', {id: id}, function (res) {
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


    function editRow(id) {
        $('#studentId').val(id);
        $.get('setup.htm?action=getStudentById', {id: id},
                function (obj) {
                    $('#studentName').val(obj.STUDENT_NME);
                    $('#dob').val(obj.DOB);
                    $('#cellNo').val(obj.MOBILE_NO);
                    $('#age').val(obj.AGE);
                    $('input:radio[name="gender"][value="' + obj.GENDER + '"]').iCheck('check');
                    $('#address').val(obj.ADDRESS);
                    $('#addStudentDialog').modal('show');
                }, 'json');
    }
    function saveStudent() {
        if ($.trim($('#studentName').val()) === '') {
            $('#studentName').notify('Student Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#studentName').focus();
            return false;
        }
        if ($.trim($('#cellNo').val()) === '') {
            $('#cellNo').notify('Cell No is Required Field', 'error', {autoHideDelay: 15000});
            $('#cellNo').focus();
            return false;
        }
        $.post('setup.htm?action=saveStudent', {
            studentName: $('#studentName').val(), cellNo: $('#cellNo').val(),
            age: $('#age').val(), dob: $('#dob').val(), address: $('#address').val(),
            studentId: $('#studentId').val(), gender: $('input[name=gender]:checked').val()}, function (res) {
            if (res) {
                if (res.result === 'save_success') {
                    $.bootstrapGrowl("Student saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    $('#addStudentDialog').modal('hide');
                    displayData();
                } else {
                    $.bootstrapGrowl("Error in Saving Student. Please try again.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                }
            }
        }, 'json');
    }
</script>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Add Student</h1>
    </div>
</div>
<div class="modal fade " id="addStudentDialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Student</h3>
            </div>
            <div class="modal-body">
                <form action="#" role="form" id="vaccinationForm" method="post" >
                    <input type="hidden" name="studentId" id="studentId" value="">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Full Name*</label>
                                <input type="text" class="form-control" id="studentName" placeholder="Student Name" >
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Cell No.*</label>
                                <input type="text"   class="form-control" id="cellNo" placeholder="0300xxxxxxx" onkeyup="onlyInteger(this);" maxlength="11" onblur="Util.validateStudentNo(this);">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Date of Birth</label>
                                <div class="input-group input-medium date" id="dob-picker">
                                    <input type="text" id="dob" class="form-control" readonly="">
                                    <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Age</label>
                                <input type="text" class="form-control" id="age" onkeyup="onlyInteger(this);">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Gender</label>
                                <div class="input-group">
                                    <div class="icheck-inline">
                                        <label>
                                            <input type="radio" name="gender" value="M" id="genderM" class="icheck" checked> Male </label>
                                        <label>
                                            <input type="radio" name="gender" value="F" id="genderF" class="icheck"> Female</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Address</label>
                                <textarea class="form-control" id="address" rows="3" cols="63"></textarea>
                            </div>
                        </div>   
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveStudent();">Save</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet-body">
            <div class="portlet box green">
                <div class="portlet-title">
                    <div class="caption">
                        Add Student
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px; " >
                            <button type="button" class="btn blue" onclick="addStudent();"><i class="fa fa-plus-circle"></i> Add Student</button>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12" style="padding-top: 20px;">
                            <div id="displayDiv">

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            </form>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>
