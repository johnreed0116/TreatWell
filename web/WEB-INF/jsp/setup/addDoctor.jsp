<%--    
   Document   : AddDoctor
   Created on : Oct 5, 2017, 8:25:43 PM
   Author     : Cori 5
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
                $('<th class="center" width="40%">').html('Doctor Name'),
                $('<th class="center" width="20%">').html('Contact No'),
                $('<th class="center" width="15%">').html('Expiry Date'),
                $('<th class="center" width="20%" colspan="6">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getDoctor', {doctorNameSearch: $('#doctorNameSearch').val(), contactNoSearch: $('#contactNoSearch').val(),
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
                            var uploadAttachmentHtm = '<i class="fa fa-cloud-upload" aria-hidden="true" title="Upload Attachments" style="cursor: pointer;" onclick="uploadDoctorAttachements(\'' + list[i].TW_DOCTOR_ID + '\');"></i>';
                            var viewAttachmentHtm = '<i class="fa fa-paperclip" aria-hidden="true" title="Click to view attachments" style="cursor: pointer;" onclick="displayDoctorAttachements(\'' + list[i].TW_DOCTOR_ID + '\');"></i>';                      
                            var featuredHtm = '<i class="fa ' + (list[i].FEATURED_IND === 'Y' ? 'fa-star' : 'fa-star-o') + '" aria-hidden="true" title="Click to view Featured" style="cursor: pointer;" onclick="featuredDoctor(\'' + list[i].TW_DOCTOR_ID + '\',\'' + list[i].FEATURED_IND + '\');"></i>';
                            if (totalAttachments === 5) {
                                uploadAttachmentHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].DOCTOR_NME),
                                    $('<td >').html(list[i].MOBILE_NO),
                                    $('<td >').html(list[i].EXPIRY_DTE),
                                    $('<td >').html(featuredHtm),
                                    $('<td align="center">').html(viewAttachmentHtm + '<span class="superscript">' + totalAttachments + '</span>'),
                                    $('<td align="center">').html(uploadAttachmentHtm),
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
        $('#doctorId').val('');
        $('#doctorName').val('');
        $('#cellNo').val('');
        $('#experience').val('');
        $('#consultancyFee').val('');
        $('#procedureFeeId').val('');
        $('#addDoctor').modal('show');
    }
    function editRow(id) {
        $('#doctorId').val(id);
        $('#loginDetails').hide();
        $.get('setup.htm?action=getDoctorById', {doctorId: id},
                function (obj) {
                    $('#doctorName').val(obj.DOCTOR_NME);
                    $('#doctorType').val(obj.DOCTOR_CATEGORY_ID);
                    $('#speciality').val(obj.TW_DOCTOR_TYPE_ID);
                    $('#experience').val(obj.EXPERIENCE);
                    $('#videoCallFrom').val(obj.VIDEO_CLINIC_FROM);
                    $('#videoCallTo').val(obj.VIDEO_CLINIC_TO);
                    $('#speciality').val(obj.TW_DOCTOR_TYPE_ID).trigger('change.select2');
                    $('input[name="video"][value="' + obj.ALLOW_VIDEO + '"]').iCheck('check');
                    $('#countryId').val(obj.COUNTRY_ID);
                    $('#cityId').val(obj.CITY_ID);
                    $('#cellNo').val(obj.MOBILE_NO);
                    $('#consultancyFee').val(obj.FEE);
                    $('#procedureFeeId').val(obj.TW_PROCEDURE_FEE_ID);

                    $('#addDoctor').modal('show');
                }, 'json');
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
        var title = "",msgHead = "";
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
                    $.post('setup.htm?action=doctorFeatured', {id: id,status:status}, function (res) {
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
        <h1>Doctor Registration</h1>
    </div>
</div>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="modal fade" id="addAttachements">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Attachment</h3>
            </div>
            <div class="modal-body">
                <form action="#" id="doctorAttachment" role="form" method="post" >
                    <input type="hidden" name="doctorId" id="doctorId" value="">
                    <input type="hidden" name="doctorAttachmentType" id="doctorAttachmentType" value="WEB ATTACHMENT">
                    <div class="portlet box green">
                        <div class="portlet-title">
                            <div class="caption">
                                Upload Attachment
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group" >
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label>&nbsp;</label>
                                                    <div>
                                                        <input id="filebutton" name="file" class="input-file" type="file">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label>Description</label>
                                                    <div>
                                                        <input id="attachDescription" class="form-control" name="attachDescription" type="text">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveAttachment();">Save</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
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
                <h3 class="modal-title">Add Doctor</h3>
            </div>
            <div class="modal-body">
                <div class="portlet box green">
                    <div class="portlet-title tabbable-line">
                        <div class="caption">
                            Personal Info 
                        </div>
                    </div>
                    <div class="portlet-body">
                        <input type="hidden" name="procedureFeeId" id="procedureFeeId">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Full Name*</label>
                                    <input type="text" class="form-control" id="doctorName" placeholder="Doctor Name" >
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Cell No.*</label>
                                    <input type="text"   class="form-control" id="cellNo" placeholder="0300xxxxxxx" onkeyup="onlyInteger(this);" maxlength="11" onblur="Util.validateDoctorNo(this);">
                                </div>
                            </div>
                        </div>
                        <div class="row">

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Total Experience</label>
                                    <input type="text"   class="form-control" id="experience" placeholder="In Years" onkeyup="onlyInteger(this);" maxlength="2" >
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Country</label>
                                    <select id="countryId" class="form-control" data-placeholder="Select...">
                                        <c:forEach items="${requestScope.refData.country}" var="obj">
                                            <option value="${obj.COUNTRY_ID}">${obj.COUNTRY_NME}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Doctor Type</label>
                                    <select id="doctorType" class="form-control">
                                        <c:forEach items="${requestScope.refData.categories}" var="obj">
                                            <option value="${obj.TW_DOCTOR_CATEGORY_ID}">${obj.TITLE}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>City</label>
                                    <select id="cityId" class="form-control" data-placeholder="Select...">

                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Consultancy Fee</label>
                                    <div class="input-group">
                                        <input type="text" placeholder="Consultancy Fee" onkeyup="onlyInteger(this);" class="form-control" id="consultancyFee" name="consultancyFee">
                                    </div>
                                </div>
                            </div> 
                            <div class="col-md-6">
                                <label>Login ID</label>
                                <input type="text" class="form-control" id="doctorLoginId" placeholder="Login ID"  onblur="Util.validateDoctorLoginId(this);" onkeyup="onlyCharForLoginId(this);">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Available for Video Consultancy</label>
                                    <div class="input-group">
                                        <div class="icheck-inline">
                                            <label>
                                                <input type="radio" name="video" value="Y" class="icheck"> Yes </label>
                                            <label>
                                                <input type="radio"  name="video" value="N" checked=""  class="icheck"> No</label>
                                        </div>
                                    </div>
                                </div>
                            </div> 
                        </div>
                        <div class="row" id="videoTimeDiv">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Time From</label>
                                    <div class="input-group bootstrap-timepicker timepicker input-small" id="timeToPicker">
                                        <input id="videoCallFrom" type="text" class="form-control input-small" readonly="">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                    </div>
                                </div>
                            </div> 
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>To</label>
                                    <div class="input-group bootstrap-timepicker timepicker input-small" id="timeToPicker">
                                        <input id="videoCallTo" type="text" class="form-control input-small" readonly="">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
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
    $(function () {
        $('#videoTimeDiv').hide();
        $('#countryId').change(function () {
            getCity();
        }).trigger('change');
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square',
            radioClass: 'iradio_square',
            increaseArea: '20%' // optional
        });
        $("#contactNoSearch").inputmask("mask", {
            "mask": "99999999999"
        });
        $('input[name=video]').on('ifChecked', function (event) {
            ($(event.target).val() === 'Y' ? $('#videoTimeDiv').show() : $('#videoTimeDiv').hide());
        });
        $('#videoCallFrom').timepicker({minuteStep: 5, showMeridian: false});
        $('#videoCallTo').timepicker({minuteStep: 5, showMeridian: false});
        $('#newExpiryDatePicker').datepicker({
            format: 'dd-mm-yyyy',
            autoclose: true,
            startDate: new Date()
        });
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
    function saveData() {
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
        var obj = {
            doctorId: $('#doctorId').val(), doctorName: $('#doctorName').val(), doctorType: $('#doctorType').val(),
            cellNo: $('#cellNo').val(), totalExperience: $('#experience').val(), speciality: $('#speciality').val(),
            cityId: $('#cityId').val(), countryId: $('#countryId').val(),
            servicesAvail: $('input[name=video]:checked').val(), newUserName: $('#doctorLoginId').val(),
            consultancyFee: $('#consultancyFee').val(), procedureFeeId: $('#procedureFeeId').val(),
            videoTimeFrom: $('#videoCallFrom').val(), videoTimeTo: $('#videoCallTo').val()
        };
        $.post('setup.htm?action=saveDoctor', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Doctor Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('#doctorId').val('');
                $('#addDoctor').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Doctor. Please try again later.", {
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
    function saveAttachment() {
        var data = new FormData(document.getElementById('doctorAttachment'));
        $.ajax({
            url: "setup.htm?action=saveDoctorAttachment",
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (data) {
            if (data) {
                if (data.result === 'save_success') {
                    $.bootstrapGrowl("Attachment Uploaded successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    $('#doctorId').val('');
                    $('#addAttachements').modal('hide');
                    displayData();
                } else {
                    $.bootstrapGrowl("Error in Uploading Attachment. Please try again later.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#addAttachements').modal('hide');
                }
            }
        });

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
