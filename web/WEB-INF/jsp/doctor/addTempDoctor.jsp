<%-- 
    Document   : addTempDoctor
    Created on : Apr 9, 2018, 6:28:11 PM
    Author     : farazahmad
--%>
<%@include file="../header.jsp"%>
<script language='javascript' src="js/md5.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css"/>
<script type="text/javascript" src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
<script type="text/javascript" src="assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
<style>
    .superscript { position: relative; top: -0.5em; font-size: 90%;font-weight: bold;margin-left: 2px;color: #F00; }
</style>
<script>
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="25%">').html('Doctor Name'),
                $('<th class="center" width="20%">').html('PMDC No.'),
                $('<th class="center" width="20%">').html('Contact No'),
                $('<th class="center" width="10%">').html('Expiry Date'),
                $('<th class="center" width="20%" colspan="3">').html('&nbsp;')
                )));
        $.get('doctor.htm?action=getTempDoctors', {doctorNameSearch: $('#doctorNameSearch').val(), contactNoSearch: $('#contactNoSearch').val(),
            doctorTypeSearch: $('#doctorTypeSearch').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_DOCTOR_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_DOCTOR_ID + '\');"></i>';
                            var renewHtm = '<i class="fa fa-refresh" aria-hidden="true" title="Click to Renew" style="cursor: pointer;" onclick="renewDoctorAccount(\'' + list[i].TW_DOCTOR_ID + '\',\'' + list[i].EXPIRY_DTE + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                                renewHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            var totalAttachments = eval(list[i].TOTAL_ATTACHMENTS);
                            //var uploadAttachmentHtm = '<i class="fa fa-cloud-upload" aria-hidden="true" title="Upload Attachments" style="cursor: pointer;" onclick="uploadDoctorAttachements(\'' + list[i].TW_DOCTOR_ID + '\');"></i>';

                            if (totalAttachments === 5) {
                                //  uploadAttachmentHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].DOCTOR_NME),
                                    $('<td>').html(list[i].PMDC_NO),
                                    $('<td >').html(list[i].MOBILE_NO),
                                    $('<td >').html(list[i].EXPIRY_DTE),
                                    $('<td align="center">').html(renewHtm),
                                    $('<td align="center">').html(editHtm),
                                    $('<td align="center">').html(delHtm)
                                    ));
                        }
                        $('#displayDiv').html('');
                        $('#displayDiv').append($tbl);
                        return false;
                    } else {
                        $('#displayDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td class="center aligned negative" colspan="8">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }
    function addDoctorDialog() {
        $('#editDoctorId').val('');
        $('#doctorName').val('');
        $('#cellNo').val('');
        $('#experience').val('');
        $('#consultancyFee').val('');
        $('#procedureFeeId').val('');
        $('#attachmentRow').show();
        $('#addDoctor').modal('show');
    }
    function uploadDoctorAttachements(id) {
        $('#doctorId').val(id);
        $('#addAttachements').modal('show');
    }
    function renewDoctorAccount(id, date) {
        $('#accountRenewelDialog').modal('show');
        $('#renewlDoctorId').val(id);
        $('#doctorExpiryDateNew').val(date);
    }
    function saveRenewDoctorAccount() {
        bootbox.confirm({
            message: "Do you want to renew expiry date for doctor?",
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
                    if ($('#doctorExpiryDateNew').val() === '') {
                        $('#doctorExpiryDateNew').notify('Please select date', 'error');
                        return false;
                    }
                    $.post('setup.htm?action=updateDoctorExpiry', {doctorId: $('#renewlDoctorId').val(), expiryDate: $('#doctorExpiryDateNew').val()}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl("Expiry Date updated successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            $('#accountRenewelDialog').modal('hide');
                            displayData();
                        } else {
                            $.bootstrapGrowl("Expiry date can not be updated. Please contact system admin.", {
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
    function displayDoctorAttachements(id) {
        var $tbl = $('<table class="table table-striped table-bordered table-hover" width="100%">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="10%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Attachment'),
                $('<th class="center" width="55%">').html('Description'),
                $('<th class="center" width="5%">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getDoctorActtachementsById', {doctorId: id, attachType: 'WEB ATTACHMENT'},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html('<a href="upload/doctor/doctorAttachments/' + list[i].TW_DOCTOR_ID + '/' + list[i].FILE_NME + '" target="_blank"><img src="images/attach-icon.png" alt="Att" width="20" height="20"></a>'),
                                    $('<td>').html(list[i].FILE_DESC),
                                    $('<td align="center">').html('<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteDoctorAttachement(\'' + list[i].TW_DOCTOR_ATTACHMENT_ID + '\',\'' + list[i].TW_DOCTOR_ID + '\');"></i>')
                                    ));
                        }
                        $('#dvTable').html('');
                        $('#dvTable').append($tbl);
                    } else {
                        $('#dvTable').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td  colspan="4">').html('<b>No data found.</b>')
                                ));
                        $('#dvTable').append($tbl);
                    }
                    $('#viewAttachmentsDialog').modal('show');
                }, 'json');
    }

    function featuredDoctor(id, status) {
        var title = "", msgHead = "";
        if (status === 'N') {
            title = "Do you want to featured this doctor?";
            status = "Y";
            msgHead = "Featured";
        } else {
            title = "Do you want to Un-featured this doctor?";
            status = "N";
            msgHead = "Un-Featured";
        }
        bootbox.confirm({
            message: title,
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
                    $.post('setup.htm?action=doctorFeatured', {id: id, status: status}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl(msgHead + ' successfully.', {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            displayData();
                        } else {
                            $.bootstrapGrowl('Doctor can not be ' + msgHead, {
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
    function deleteDoctorAttachement(id, doctorId) {
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
                    $.post('setup.htm?action=deleteDoctorAttachement', {id: id}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl("Record deleted successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            $('#viewAttachmentsDialog').modal('hide');
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
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Doctor's Database</h1>
    </div>
</div>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="modal fade" id="accountRenewelDialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Renew Expiry</h3>
            </div>
            <div class="modal-body">
                <div class="portlet-body">
                    <form action="#" role="form" method="post" >
                        <input type="hidden" id="renewlDoctorId" value="">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>New Expiry*</label>
                                    <div class="input-group input-medium date" id="newExpiryDatePicker">
                                        <input type="text" id="doctorExpiryDateNew" class="form-control" readonly="">
                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveRenewDoctorAccount();">Save</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="viewAttachmentsDialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">View Attachments</h3>
            </div>
            <div class="modal-body">
                <div class="portlet-body">
                    <form action="#" role="form" method="post" >
                        <input type="hidden" name="viewAttachmentsDocotId" id="viewAttachmentsDocotId" value="">
                        <div class="row">
                            <div class="col-md-12">
                                <div id="dvTable">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="addDoctor">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Doctor</h3>
            </div>
            <div class="modal-body" id="blockui_sample_1_portlet_body">
                <form method="post" id="addDoctorForm" action="#" enctype="multipart/form-data" onsubmit="return false;">
                    <input type="hidden" id="editDoctorId" value="">
                    <div class="portlet box green">
                        <div class="portlet-body">
                            <input type="hidden" name="procedureFeeId" id="procedureFeeId">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Full Name*</label>
                                        <input type="text" class="form-control" id="doctorName" name="doctorName" placeholder="Doctor Name" >
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Cell No.*</label>
                                        <input type="text" class="form-control" id="cellNo" name="cellNo" placeholder="0300xxxxxxx" onkeyup="onlyInteger(this);" maxlength="11" onblur="Util.validateDoctorNo(this);">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Email*</label>
                                        <input type="text" class="form-control" id="doctorEmail" name="doctorEmail" placeholder="Email" >
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>PMDC No.</label>
                                        <input type="text" class="form-control" id="pmdcNo" name="pmdcNo" placeholder="PMDC No." >
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Total Experience</label>
                                        <input type="text"   class="form-control" id="experience" name="totalExperience" placeholder="In Years" onkeyup="onlyInteger(this);" maxlength="2" >
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="form-group">
                                            <label>Specialities</label>
                                            <select id="speciality" class="form-control" name="speciality" multiple="multiple">
                                                <option value="">Select Speciality</option>
                                                <c:forEach items="${requestScope.refData.speciality}" var="obj">
                                                    <option value="${obj.TW_MEDICAL_SPECIALITY_ID}">${obj.TITLE}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="form-group">
                                            <label>Provide Services</label>
                                            <select id="doctorService"   class="form-control" name="doctorService" multiple="multiple">
                                                <option value="">Select Services</option>
                                                <c:forEach items="${requestScope.refData.services}" var="obj">
                                                    <option value="${obj.TW_MEDICAL_SERVICE_ID}">${obj.TITLE}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row" id="attachmentRow">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Profile Picture</label>
                                        <input type="file" class="form-control" id="profileImage" name="profileImage" placeholder="Profile Picture" >
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Visiting Card</label>
                                        <input type="file" class="form-control" id="visitingCardImage" name="visitingCardImage" placeholder="Visting Card" >
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Doctor Type</label>
                                        <select id="doctorType" name="doctorType" class="form-control">
                                            <c:forEach items="${requestScope.refData.categories}" var="obj">
                                                <option value="${obj.TW_DOCTOR_CATEGORY_ID}">${obj.TITLE}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Consultancy Fee</label>
                                        <input type="text" placeholder="Consultancy Fee" onkeyup="onlyInteger(this);" class="form-control" id="consultancyFee" name="consultancyFee">
                                    </div>
                                </div>
                            </div>
                            <div class="row" id="clinicNameRow">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Clinic Name</label>
                                        <select id="clinicId" name="clinicId" class="form-control" data-placeholder="Choose a Clinic">       
                                            <c:forEach items="${requestScope.refData.clinics}" var="obj">
                                                <option value="${obj.TW_CLINIC_ID}">${obj.CLINIC_NME}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Clinic Time From</label>
                                        <div class="input-group bootstrap-timepicker  timepicker input-medium" id="timeFromPicker">
                                            <input id="timeFrom" name="timeFrom" type="text" class="form-control input-medium" readonly="">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>To</label>
                                        <div class="input-group bootstrap-timepicker timepicker input-medium" id="timeToPicker">
                                            <input id="timeTo" name="timeTo" type="text" class="form-control input-medium" readonly="">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    Available Days
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <input id="mon" value="MON" name="weekDaysCheck" type="checkbox" class="icheck">
                                        <label for="mon">MON</label>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <input id="tue" value="TUE" name="weekDaysCheck" type="checkbox" class="icheck">
                                        <label>TUE</label>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <input id="wed" value="WED" name="weekDaysCheck" type="checkbox" class="icheck">
                                        <label for="wed">WED</label>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <input id="thu" value="THU" name="weekDaysCheck" type="checkbox" class="icheck">
                                        <label for="thur">THU</label>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <input id="fri" value="FRI" name="weekDaysCheck" type="checkbox" class="icheck">
                                        <label for="fri">FRI</label>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <input id="sat" value="SAT" name="weekDaysCheck" type="checkbox" class="icheck">
                                        <label for="sat">SAT</label>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <input id="sun" value="SUN" name="weekDaysCheck" type="checkbox" class="icheck">
                                        <label for="sun">SUN</label>
                                    </div>
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
        <div class="portlet-body">
            <form name="doctorform" action="#" role="form" onsubmit="return false;" method="post">
                <div class="portlet box green">
                    <div class="portlet-title">
                        <div class="caption">
                            Doctor Info
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Doctor Type</label>
                                    <select id="doctorTypeSearch" class="form-control" data-placeholder="Select...">
                                        <option value="">ALL</option>
                                        <c:forEach items="${requestScope.refData.categories}" var="obj">
                                            <option value="${obj.TW_DOCTOR_CATEGORY_ID}">${obj.TITLE}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Doctor Name</label>
                                    <div>
                                        <input type="text" class="form-control" id="doctorNameSearch" placeholder="Doctor Name" onchange="displayData(this.value);">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Contact No.</label>
                                    <div>
                                        <input type="text" class="form-control" id="contactNoSearch" placeholder="Contact No." >
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class=row">
                            <div class="col-md-6" style="padding-top: 23px; margin-bottom: 23px; ">
                                <button type="button" class="btn green" onclick="displayData();"><i class="fa fa-search"></i> Search Doctor</button>
                            </div>
                            <div class="col-md-6 text-right" style="padding-top: 23px; margin-bottom: 23px;">
                                <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                    <button type="button" class="btn blue" onclick="addDoctorDialog();"><i class="fa fa-plus-circle"></i> New Doctor</button>
                                </c:if>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
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
<script>
    function editRow(id) {
        $('#editDoctorId').val(id);
        $('#attachmentRow').hide();
        $.get('doctor.htm?action=getTempDoctorById', {doctorId: id},
                function (obj) {
                    $('#doctorName').val(obj.DOCTOR_NME);
                    $('#doctorType').val(obj.DOCTOR_CATEGORY_ID);
                    $('#experience').val(obj.EXPERIENCE);
                    $('#cellNo').val(obj.MOBILE_NO);
                    $('#consultancyFee').val(obj.FEE);
                    $('#pmdcNo').val(obj.PMDC_NO);
                    $('#doctorEmail').val(obj.EMAIL);
                    $('#clinicId').val(obj.TW_CLINIC_ID);
                    $('#timeFrom').val(obj.TIME_FROM);
                    $('#timeTo').val(obj.TIME_TO);
                    if (obj.TW_MEDICAL_SPECIALITY_ID !== '') {
                        var t = obj.TW_MEDICAL_SPECIALITY_ID;
                        if (t.indexOf(',') > -1) {
                            var arr = t.split(',');
                            $('#speciality').val(arr);
                            $('#speciality').trigger('change');
                        } else {
                            $('#speciality').val(t);
                            $('#speciality').trigger('change');
                        }
                    }
                    if (obj.TW_DOCTOR_SERVICE_ID !== '') {
                        var t = obj.TW_DOCTOR_SERVICE_ID;
                        if (t.indexOf(',') > -1) {
                            var arr = t.split(',');
                            $('#doctorService').val(arr);
                            $('#doctorService').trigger('change');
                        } else {
                            $('#doctorService').val(t);
                            $('#doctorService').trigger('change');
                        }
                    }

                    if (obj.WEEK_DAY !== '') {
                        var t = obj.WEEK_DAY;
                        if (t.indexOf(',') > -1) {
                            var arr = t.split(',');
                            for (var i = 0; i < arr.length; i++) {
                                $("input[name='weekDaysCheck'][value='" + arr[i] + "']").iCheck('check');
                            }
                        } else {
                            $("input[name='weekDaysCheck'][value='" + t + "']").iCheck('check');
                        }
                    }

                    $('#addDoctor').modal('show');
                }, 'json');
    }
    $(function () {

//        $('#countryId').change(function () {
//            getCity();
//        }).trigger('change');
        $('#clinicId').select2({
            formatNoMatches: function () {
                return '<i style="float: right;" class="btn btn-info btn-sm" id="addNewClinicBtn" onClick="addClinicDialog();">Add Clinic</i>';
            }
        });
        $('#speciality').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#doctorService').select2({
            placeholder: "Select an option",
            allowClear: true
        });

        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square',
            radioClass: 'iradio_square',
            increaseArea: '20%' // optional
        });
        $("#contactNoSearch").inputmask("mask", {
            "mask": "99999999999"
        });
        $('#newExpiryDatePicker').datepicker({
            format: 'dd-mm-yyyy',
            autoclose: true,
            startDate: new Date()
        });

        $('#timeFrom').timepicker({minuteStep: 5, showMeridian: false});
        $('#timeTo').timepicker({minuteStep: 5, showMeridian: false});
    });
    function getCity() {
        //Find all Citys
        $('#cityId').find('option').remove();
        $.get('setup.htm?action=getCityByCountryId', {countryId: $('#countryId').val()}, function (data) {
            if (data !== null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $('<option />', {value: data[i].CITY_ID, text: data[i].CITY_NME}).appendTo($('#cityId'));
                }
            } else {
                $('<option />', {value: '', text: 'No city found.'}).appendTo($('#cityId'));
            }
        }, 'json');
    }
    jQuery.fn.getCheckboxVal = function () {
        var vals = [];
        var i = 0;
        this.each(function () {
            vals[i++] = jQuery(this).val();
        });
        return vals;
    };
    function saveData() {
        var data = new FormData(document.getElementById('addDoctorForm'));
        var weekDays = $("input[name='weekDaysCheck']:checked").getCheckboxVal();
        if (weekDays.length > 0) {
            for (var i = 0; i < weekDays.length; i++) {
                data.append('weekDays', weekDays[i]);
            }
        }
        var doctorServiceArr = $("#doctorService").val();
        if (doctorServiceArr.length > 0) {
            for (var i = 0; i < doctorServiceArr.length; i++) {
                data.append('services', doctorServiceArr[i]);
            }
        }
        var speciality = $("#speciality").val();
        if (speciality.length > 0) {
            for (var i = 0; i < speciality.length; i++) {
                data.append('specility', speciality[i]);
            }
        }
        if ($.trim($('#doctorName').val()) === '') {
            $('#doctorName').notify('Doctor Name is Required.', 'error', {autoHideDelay: 15000});
            $('#doctorName').focus();
            return false;
        }
        if ($.trim($('#cellNo').val()) === '') {
            $('#cellNo').notify('Cell No. is Required.', 'error', {autoHideDelay: 15000});
            $('#cellNo').focus();
            return false;
        }
        data.append("doctorInd", "T");
        if ($('#editDoctorId').val() !== '') {
            data.append('doctorId', $('#editDoctorId').val());
        }
        Metronic.blockUI({
            target: '#blockui_sample_1_portlet_body',
            boxed: true,
            message: 'Saving...'
        });
        $.ajax({
            url: 'doctor.htm?action=saveDoctorInDatabase',
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (data) {
            Metronic.unblockUI();
            if (data) {
                if (data.result === 'save_success') {
                    $.bootstrapGrowl("Doctor data saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#editDoctorId').val('');
                    $('#addDoctor').modal('hide');
                    //displayData();
                } else {
                    $.bootstrapGrowl("Error in saving doctor data. Please try again later.", {
                        ele: 'body',
                        type: 'error',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#addDoctor').modal('hide');
                }
            }
        });

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
                    $.post('setup.htm?action=deleteDoctor', {id: id}, function (res) {
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
</script>

<%@include file="../footer.jsp"%>