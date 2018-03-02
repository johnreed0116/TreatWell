<%-- 
    Document   : addVaccination
    Created on : Mar 2, 2018, 7:08:11 PM
    Author     : Ali Zaidi
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('#specialityId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        displayData();
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="10%">').html('Sr. #'),
                $('<th class="center" width="60%">').html('Vaccination'),
                $('<th class="center" width="10%">').html('Frequency'),
                $('<th class="center" width="10%">').html('Dose Usage'),
                $('<th class="center" width="10%" colspan="2">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getVaccination', {specialityId: $('#specialityId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_VACCINATION_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_VACCINATION_ID + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].VACCINATION_NME),
                                    $('<td>').html(list[i].FREQUENCY),
                                    $('<td>').html(list[i].TOTAL_DOSE),
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
                                $('<td  colspan="6">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }
    function addVaccination() {
        $('#vaccinationId').val('');
        $('#vaccinationName').val('');
        $('#doseUsage').val('');
        $('#frequency').val('');
        $('#addVaccinationDialog').modal('show');
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
                    $.post('setup.htm?action=deleteVaccination', {id: id}, function (res) {
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
        $('#vaccinationId').val(id);
        $.get('setup.htm?action=getVaccinationById', {id: id},
                function (obj) {
                    $('#vaccinationName').val(obj.VACCINATION_NME);
                    $('#frequency').val(obj.FREQUENCY);
                    $('#doseUsage').val(obj.TOTAL_DOSE);
                    $('#addVaccinationDialog').modal('show');
                }, 'json');
    }
    function saveVaccination() {
        if ($.trim($('#vaccinationName').val()) === '') {
            $('#vaccinationName').notify('Vaccination Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#vaccinationName').focus();
            return false;
        }
        if ($('#frequency').val() === '') {
            $('#frequency').notify('Frequency is Required Field', 'error', {autoHideDelay: 15000});
            $('#frequency').focus();
            return false;
        }
        if ($('#doseUsage').val() === '') {
            $('#doseUsage').notify('Dose is Required Field', 'error', {autoHideDelay: 15000});
            $('#doseUsage').focus();
            return false;
        }
        $.post('setup.htm?action=saveVaccination', {specialityId: $('#specialityId').val(),
            vaccinationName: $('#vaccinationName').val(),frequency:$('#frequency').val(),doseUsage:$('#doseUsage').val()
            , vaccinationId: $('#vaccinationId').val()}, function (res) {
            if (res) {
                if (res.result === 'save_success') {
                    $.bootstrapGrowl("Vaccination saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    $('#addVaccinationDialog').modal('hide');
                    displayData();
                } else {
                    $.bootstrapGrowl("Error in Saving Vaccination. Please try again.", {
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
        <h1>Add Vaccination</h1>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="addVaccinationDialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Vaccination</h3>
            </div>
            <div class="modal-body">
                <form action="#" role="form" id="vaccinationForm" method="post" >
                    <input type="hidden" name="vaccinationId" id="vaccinationId" value="">
                    <div class="row">                    
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Vaccination Name *</label>
                                <input type="text" class="form-control" id="vaccinationName" name="vaccinationName">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Frequency ( Days ) *</label>
                                <input type="text" class="form-control" onkeyup="onlyInteger(this);" id="frequency" name="frequency">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Dose Usage ( Number Times ) *</label>
                                <input type="text" class="form-control" onkeyup="onlyInteger(this);" id="doseUsage" name="doseUsage">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveVaccination();">Save</button>
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
                        Add Vaccination
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Medical Speciality</label>
                                <select id="specialityId" class="form-control" onchange="displayData();">
                                    <c:forEach items="${requestScope.refData.speciality}" var="obj">
                                        <option value="${obj.TW_MEDICAL_SPECIALITY_ID}">${obj.TITLE}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px; " >
                            <button type="button" class="btn blue" onclick="addVaccination();"><i class="fa fa-plus-circle"></i> Add Vaccination</button>
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