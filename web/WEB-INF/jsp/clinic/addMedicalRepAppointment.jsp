<%-- 
    Document   : addMedicalRepAppointment
    Created on : Dec 16, 2017, 1:07:27 PM
    Author     : Wasi
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('#brickId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#pharmaCompanyId').change(function () {
            displayData();
            getBrickByPharmaceuticalId();
        }).trigger('change');
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Full Name'),
                $('<th class="center" width="20%">').html('Contact No#'),
                $('<th class="center" width="15%">').html('Designation'),
                $('<th class="center" width="15%" colspan="3">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getMedicinesRep', {pharmaCompanyId: $('#pharmaCompanyId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var appointmentHtm = '<i class="fa fa-thumb-tack"" aria-hidden="true" title="Click to Mark Appointment" style="cursor: pointer;" onclick="markAppointment(\'' + list[i].TW_PHARMA_RAP_ID + '\');"></i>';
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].FULL_NME),
                                    $('<td>').html(list[i].CONTACT_NO),
                                    $('<td>').html(list[i].DESIGNITION),
                                    $('<td align="center">').html(appointmentHtm)
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

        if ($('#brickId').val() === null) {
            $('#brickId').notify('Please select Area.', 'error', {autoHideDelay: 15000});
            $('#brickId').focus();
            return false;
        }
        var obj = {
            pharmaRepId: $('#pharmaRepId').val(),
            'brickIdArr[]': $('#brickId').val()
        };
        $.post('clinic.htm?action=saveMedicalRepAppointment', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Medical Rep Appointment saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#pharmaRepId').val('');
                $('#addMedicalRepAppointment').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Medical Rep Appointment. Please try again later.", {
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
    function markAppointment(pharmaRepId) {
        $('#pharmaRepId').val(pharmaRepId);
        editRow();
        $('#addMedicalRepAppointment').modal('show');
    }
    function getBrickByPharmaceuticalId() {
        $('#brickId').find('option').remove();
        $.get('clinic.htm?action=getBrickByPharmaceuticalId', {pharmaCompanyId: $('#pharmaCompanyId').val()}, function (data) {
            if (data !== null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $('<option />', {value: data[i].TW_BRICK_MASTER_ID, text: data[i].TITLE}).appendTo($('#brickId'));
                }
            }
        }, 'json');

    }
    function editRow() {
        $.get('clinic.htm?action=getMedicalRepAppointmentById', {pharmaRepId: $('#pharmaRepId').val()},
                function (obj) {
                    if (!jQuery.isEmptyObject(obj)) {
                        var brickIds = obj.BRICKS;
                        arr = brickIds.split(',');
                        $('#brickId').val(arr).trigger('change'); 
                    }
                }, 'json');
    }

</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Medical Rep Appointment</h1>
    </div>
</div>
<div class="modal fade" id="addMedicalRepAppointment">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Medical Rep Appointment</h3>
            </div>
            <div class="modal-body">
                <input type="hidden" id="pharmaRepId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Brick</label>
                                <select id="brickId" name="brickId[]"  class="form-control select2_category" multiple="multiple">
                                </select>
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
                    Medical Rep Appointment
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Pharmaceutical Company</label>
                                <select id="pharmaCompanyId" class="form-control" onchange="displayData();">
                                    <c:forEach items="${requestScope.refData.companies}" var="obj">
                                        <option value="${obj.TW_PHARMACEUTICAL_ID}">${obj.COMPANY_NME}</option>
                                    </c:forEach>
                                </select>
                            </div>
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

<%@include file="../footer.jsp"%>

