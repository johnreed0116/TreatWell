<%-- 
    Document   : viewLabPatient
    Created on : Jan 26, 2018, 5:40:47 PM
    Author     : dell
--%>

<%@include file="../header.jsp"%>
<script>
    function uploadDoctorAttachements(patientId, doctorId, prescDetailId) {
        $('#doctorId').val(doctorId);
        $('#patientId').val(patientId);
        $('#prescDetailId').val(prescDetailId);
        $('#addAttachements').modal('show');
    }
    function saveAttachment() {
        var data = new FormData(document.getElementById('doctorAttachment'));
        $.ajax({
            url: "performa.htm?action=saveLabAttachment",
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
    function displayDoctorAttachements(patientId, doctorId, prescId) {
        var $tbl = $('<table class="table table-striped table-bordered table-hover" width="100%">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="10%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Attachment'),
                $('<th class="center" width="55%">').html('Description'),
                $('<th class="center" width="5%">').html('&nbsp;')
                )));
        $.get('performa.htm?action=getLabActtachementsByPrescId', {doctorId: doctorId, patientId: patientId, prescDetailId: prescId},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var delHtm = '&nbsp;';
                            if ($('#can_delete').val() === 'Y') {
                                delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteLabAttachement(\'' + list[i].TW_LAB_ATTACHMENT_ID + '\');"></i>';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html('<a href="upload/patient/labReports/' + list[i].TW_PATIENT_ID + '/' + list[i].FILE_NME + '" target="_blank"><img src="images/attach-icon.png" alt="Att" width="20" height="20"></a>'),
                                    $('<td>').html(list[i].FILE_DESC),
                                    $('<td align="center">').html(delHtm)
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


    function deleteLabAttachement(id) {
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
                    $.post('performa.htm?action=deleteLabAttachement', {id: id}, function (res) {
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
        <h1>Lab Patient </h1>
    </div>
</div>
<div class="modal fade" id="addAttachements">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Attach Report</h3>
            </div>
            <div class="modal-body">
                <form action="#" id="doctorAttachment" role="form" method="post" >
                    <input type="hidden" name="doctorId" id="doctorId" value="">
                    <input type="hidden" name="patientId" id="patientId" value="">
                    <input type="hidden" name="prescDetailId" id="prescDetailId" value="">
                    <div class="portlet box green">
                        <div class="portlet-title">
                            <div class="caption">
                                Report Attachment
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
                                <div id="dvTable"></div>
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
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Lab Patient
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th class="center" width="5%">Sr.#</th>
                                        <th class="center" width="25%">Patient Name</th>
                                        <th class="center" width="10%">Contact#</th>
                                        <th class="center" width="25%">Test Name</th>
                                        <th class="center" width="25%">Referred By</th>
                                        <th class="center" width="10%" colspan="2">&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.refData.patientList}" var="list" varStatus="i">
                                        <tr>
                                            <td>${i.count}</td>
                                            <td>${list.PATIENT_NME}</td>
                                            <td>${list.MOBILE_NO}</td>
                                            <td>${list.TITLE}</td>
                                            <td>${list.DOCTOR_NME}</td>
                                            <td><i class="fa fa-cloud-upload" aria-hidden="true" title="Upload Attachments" style="cursor: pointer;" onclick="uploadDoctorAttachements('${list.TW_PATIENT_ID}', '${list.TW_DOCTOR_ID}', '${list.TW_PRESCRIPTION_DETAIL_ID}');"></i></td>
                                            <td><i class="fa fa-paperclip" aria-hidden="true" title="Click to view attachments" style="cursor: pointer;" onclick="displayDoctorAttachements('${list.TW_PATIENT_ID}', '${list.TW_DOCTOR_ID}', '${list.TW_PRESCRIPTION_DETAIL_ID}');"></i></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="../footer.jsp"%>
