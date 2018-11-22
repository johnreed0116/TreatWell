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
    function gotoPage(pageNo) {
        if (pageNo !== null && pageNo !== '') {
            var startIndex = eval((pageNo - 1) * 10) + 1;
            var endIndex = eval(pageNo * 10);
            displayData(startIndex, endIndex, pageNo);
        }
    }
    function displayData(startIndex, endIndex, pageNo) {
        var totalRows = 0;
        if (!startIndex) {
            startIndex = 1;
        }
        if (!endIndex) {
            endIndex = 10;
        }
        if (!pageNo) {
            pageNo = 1;
        } else {
            $('body,html').animate({
                scrollTop: 0
            }, 500);
        }
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="25%">').html('Doctor Name'),
                $('<th class="center" width="20%">').html('PMDC No.'),
                $('<th class="center" width="20%">').html('Contact No'),
                $('<th style="text-align:center;" width="10%">').html('Status'),
                $('<th class="center" width="20%" colspan="5">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getDoctor', {doctorNameSearch: $('#doctorNameSearch').val(), contactNoSearch: $('#contactNoSearch').val(),
            doctorTypeSearch: $('#doctorTypeSearch').val(), accountInd: $('#accountInd').val(), startRowNo: startIndex, endRowNo: endIndex},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var obj = list[i];
                            totalRows = obj.TOTAL_ROWS;
                            var activeInd = obj.ACTIVE_IND;
                            var editHtm = '<i class="fa fa-pencil-square-o"  aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_DOCTOR_ID + '\');"></i>';
                            var delHtm = '&nbsp;';
                            if (activeInd === 'Y') {
                                delHtm = '<i class="fa fa-ban danger" aria-hidden="true" title="Click to Inactive!" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_DOCTOR_ID + '\');"></i>';
                            } else if (activeInd === 'N') {
                                delHtm = '<i class="fa fa-check" aria-hidden="true" title="Click to activate!" style="cursor: pointer;" onclick="activateAccount(\'' + list[i].TW_DOCTOR_ID + '\');"></i>';
                            }
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
                            var statusInd = '&nbsp;';
                            if (activeInd === 'Y') {
                                statusInd = '<span class="label label-sm label-success">Active </span>';
                            } else {
                                statusInd = '<span class="label label-sm label-danger">Blocked </span>';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].DOCTOR_NME),
                                    $('<td align="left">').html(list[i].PMDC_NO),
                                    $('<td >').html(list[i].MOBILE_NO),
                                    $('<td align="center">').html(statusInd),
                                    $('<td align="center">').html(featuredHtm),
                                    $('<td align="center">').html(viewAttachmentHtm + '<span class="superscript">' + totalAttachments + '</span>'),
                                    $('<td align="center">').html(uploadAttachmentHtm),
                                    //$('<td align="center">').html(renewHtm),
                                    $('<td align="center">').html(editHtm),
                                    $('<td align="center">').html(delHtm)
                                    ));
                        }
                        //


                        var info = '<div class="btn-group">';
                        var totalChunks = Math.ceil(eval(totalRows) / 10);
                        for (var j = 0; j < totalChunks; j++) {
                            if (eval(pageNo) === eval(j + 1)) {
                                info += '<button type="button" class="btn blue" onclick="gotoPage(\'' + eval(j + 1) + '\');"><span class="md-click-circle md-click-animate" style="height: 35px; width: 35px; top: -8.28334px; left: 1px;" ></span>' + eval(j + 1) + '</button> ';
                            } else {
                                info += '<button type="button" class="btn btn-default" onclick="gotoPage(\'' + eval(j + 1) + '\');"><span class="md-click-circle md-click-animate" style="height: 35px; width: 35px; top: -8.28334px; left: 1px;" ></span>' + eval(j + 1) + '</button> ';
                            }
                        }
                        info += '</div>';
                        $('#displayDiv').html('');
                        $('#displayDiv').append($tbl);
                        $('#dataInfoDiv').html(info);
                        $('#titlePaginationDiv').html('<h4><strong>Displaying Page ' + pageNo + ' of ' + totalChunks + ' </strong></h4>');
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
        $('#consultancyFee').val('');
        $('#procedureFeeId').val('');
        $('#doctorLoginId').prop('readOnly', false);
        $('#attachmentRow').show();
        $('#addDoctor').modal('show');
    }
    function editRow(id) {
        $('#editDoctorId').val(id);
        $('#doctorLoginId').prop('readOnly', true);
        $('#attachmentRow').hide();
        Metronic.blockUI({
            boxed: true,
            message: 'Loading...'
        });

        $.get('setup.htm?action=getDoctorById', {doctorId: id},
                function (obj) {
                    Metronic.unblockUI();
                    getSpecialityForDoctor(id);
                    $('#doctorName').val(obj.DOCTOR_NME);
                    $('#doctorType').val(obj.DOCTOR_CATEGORY_ID);
                    //$('#speciality').val(obj.TW_DOCTOR_TYPE_ID);
                    $('#videoCallFrom').val(obj.VIDEO_CLINIC_FROM);
                    $('#videoCallTo').val(obj.VIDEO_CLINIC_TO);
                    //$('#speciality').val(obj.TW_DOCTOR_TYPE_ID).trigger('change.select2');
                    $('input[name="video"][value="' + obj.ALLOW_VIDEO + '"]').iCheck('check');
                    $('#countryId').val(obj.COUNTRY_ID);
                    $('#cityId').val(obj.CITY_ID);
                    $('#cellNo').val(obj.MOBILE_NO);
                    $('#consultancyFee').val(obj.FEE);
                    $('#pracrticeFrom').val(obj.EXPERIENCE_FROM);
                    $('#procedureFeeId').val(obj.TW_PROCEDURE_FEE_ID);
                    $('#pmdcNo').val(obj.PMDC_NO);
                    $('#doctorEmail').val(obj.EMAIL);
                    $('#aboutDoc').val(obj.ABOUT_DOC);
                    $.get('setup.htm?action=getDoctorDiscounts', {doctorId: id}, function (list) {
                        if (list.length > 0) {
                            for (var i = 0; i < list.length; i++) {
                                $('#discountPerc_' + list[i].TW_DISCOUNT_CATEGORY_ID).val(list[i].DISCOUNT_RATIO);
                            }
                        } else {
                            $('input:text[name="discountPerc"]').val('');
                        }
                    }, 'json');
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
            <div class="modal-body" id="blockui_sample_1_portlet_body">
                <form method="post" id="addDoctorForm" action="#" enctype="multipart/form-data" onsubmit="return false;">
                    <input type="hidden" id="editDoctorId" value="">
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
                                        <input type="text" class="form-control" id="doctorName" name="doctorName" placeholder="Doctor Name" >
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Cell No</label>
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
                                        <label>Practice From</label>
                                        <div class="input-group input-medium date" id="pracrticeFromDatePicker">
                                            <input type="text" id="pracrticeFrom" name="pracrticeFrom" class="form-control" readonly="">
                                            <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
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
                                        <input type="file" class="form-control" id="visitingCardImage" name="visitingCardImage" placeholder="Visting Card Picture" >
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Country</label>
                                        <select id="countryId" class="form-control" name="countryId" data-placeholder="Select...">
                                            <c:forEach items="${requestScope.refData.country}" var="obj">
                                                <option value="${obj.COUNTRY_ID}"
                                                        <c:if test="${obj.COUNTRY_NME=='Pakistan'}">
                                                            selected="selected"
                                                        </c:if>
                                                        >${obj.COUNTRY_NME}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>City</label>
                                        <select id="cityId" name="cityId" class="form-control" data-placeholder="Select...">

                                        </select>
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
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="form-group">
                                            <label>Specialities</label>
                                            <select id="specility" class="form-control" name="specility" multiple="multiple">
                                                <c:forEach items="${requestScope.refData.services}" var="obj">
                                                    <option value="${obj.TW_MEDICAL_SPECIALITY_ID}">${obj.TITLE}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Consultancy Fee</label>
                                        <input type="text" placeholder="Consultancy Fee" onkeyup="onlyInteger(this);" class="form-control" id="consultancyFee" name="consultancyFee">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label>Login ID</label>
                                    <input type="text" class="form-control" id="doctorLoginId" name="newUserName" placeholder="Login ID"  onblur="Util.validateDoctorLoginId(this);" onkeyup="onlyCharForLoginId(this);">
                                </div>
                            </div>
                            <div class="row" id="videoTimeDiv">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Time From</label>
                                        <div class="input-group bootstrap-timepicker timepicker input-small" id="timeToPicker">
                                            <input id="videoCallFrom" name="videoTimeFrom" type="text" class="form-control input-small" readonly="">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                        </div>
                                    </div>
                                </div> 
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>To</label>
                                        <div class="input-group bootstrap-timepicker timepicker input-small" id="timeToPicker">
                                            <input id="videoCallTo" name="videoTimeTo" type="text" class="form-control input-small" readonly="">
                                            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                        </div>
                                    </div>
                                </div> 
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="form-group">
                                            <label>About Doctor</label>
                                            <textarea id="aboutDoc" name="aboutDoc" rows="2" cols="40" class="form-control"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <h3>Discount</h3>
                                    <table class="table table-striped table-condensed table-bordered" id="discountTable">
                                        <thead>
                                            <tr>
                                                <td>Sr. #</td>
                                                <td>Category</td>
                                                <td>% of Discount</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${requestScope.refData.discounts}" var="obj" varStatus="i">
                                                <tr>
                                                    <td>${i.count}</td>
                                                    <td>${obj.CATEGORY_NME}</td>
                                                    <td>
                                                        <input type="hidden" name="discountPercId" value="${obj.TW_DISCOUNT_CATEGORY_ID}">
                                                        <input type="text" class="form-control input-sm" name="discountPerc" id="discountPerc_${obj.TW_DISCOUNT_CATEGORY_ID}" onkeyup="onlyDouble(this);">
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
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
                            <div class="col-md-3">
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
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Doctor Name</label>
                                    <div>
                                        <input type="text" class="form-control" id="doctorNameSearch" placeholder="Doctor Name" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Contact No.</label>
                                    <div>
                                        <input type="text" class="form-control" id="contactNoSearch" placeholder="Contact No." >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Account Status</label>
                                    <select id="accountInd" class="form-control">
                                        <option value="Y">Active</option>
                                        <option value="N">InActive</option>
                                        <option value="">All</option>
                                    </select>
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
                                <div id="titlePaginationDiv"></div>
                                <div id="displayDiv"></div>
                                <div id="dataInfoDiv"></div>
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
        $('#pracrticeFromDatePicker').datepicker({
            format: 'yyyy',
            autoclose: true,
            minViewMode: 2
        });
        $('#newExpiryDatePicker').datepicker({
            format: 'dd-mm-yyyy',
            autoclose: true,
            startDate: new Date()
        });
        $('#specility').select2({
            placeholder: "Select an option",
            allowClear: true
        });
    });
    function getSpecialityForDoctor(id) {
        $.get('setup.htm?action=getDoctorSpecialityById', {doctorId: id},
                function (obj) {
                    if (obj !== null && obj.length > 0) {
                        var arr = [];
                        for (var i = 0; i < obj.length; i++) {
                            arr.push(obj[i].TW_MEDICAL_SPECIALITY_ID);
                            //   $('input:checkbox[name="speciality"][value="' + obj[i].TW_MEDICAL_SPECIALITY_ID + '"]').iCheck('check');
                        }
                        $('#specility').val(arr);
                        $('#specility').trigger('change');
                    }
                }, 'json');
    }
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
        if ($.trim($('#doctorEmail').val()) === '') {
            $('#doctorEmail').notify('Email Address is Required.', 'error', {autoHideDelay: 15000});
            $('#doctorEmail').focus();
            return false;
        }
        if ($.trim($('#editDoctorId').val()) === '') {
            if ($.trim($('#doctorLoginId').val()) === '') {
                $('#doctorLoginId').notify('Login ID is Required.', 'error', {autoHideDelay: 15000});
                $('#doctorLoginId').focus();
                return false;
            }
        }

        var videoServices = $('input[name=video]:checked').val();
        var data = new FormData(document.getElementById('addDoctorForm'));
        data.append('servicesAvail', videoServices);
        if ($('#editDoctorId').val() !== '') {
            data.append('doctorId', $('#editDoctorId').val());
        }
        $.ajax({
            url: 'setup.htm?action=saveDoctor',
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (data) {
            if (data) {
                if (data.result === 'save_success') {
                    $.bootstrapGrowl("Doctor account saved successfully. Please wait for email and sms for account information.", {
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
                    $.bootstrapGrowl("Error in creating doctor account. Please try again later.", {
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
    function activateAccount(id) {
        bootbox.confirm({
            message: "Do you want to activate doctor account?",
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
                    $.post('setup.htm?action=activeDoctorAccount', {id: id}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl("Doctor account activated successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            displayData();
                        } else {
                            $.bootstrapGrowl("Error in activating account. please try again later.", {
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
    function deleteRow(id) {
        bootbox.confirm({
            message: "Do you want to inactive doctor?",
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
                            $.bootstrapGrowl("Doctor account blocked successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            displayData();
                        } else {
                            $.bootstrapGrowl("Record can not be blocked. please try again later.", {
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
