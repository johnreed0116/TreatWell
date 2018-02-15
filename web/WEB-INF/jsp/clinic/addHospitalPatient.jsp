<%-- 
    Document   : addHospitalPatinet
    Created on : Feb 14, 2018, 6:42:21 PM
    Author     : Ali Zaidi
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('#roomDiv').hide();
        $('#clinicId').change(function () {
            loadWard();
            loadRoom();
        }).trigger('change');
        $('#dischargeDate').datepicker({
            format: 'dd-mm-yyyy',
            autoclose: true
        });
        $('#dischargeDate').datepicker('setDate', new Date());
        $('.icheck').iCheck({
            radioClass: 'iradio_square',
            increaseArea: '20%' // optional
        });
        $('input[name=statusInd]').on('ifChecked', function (event) {
            displayData();
        }).trigger('ifChecked');
        $('input[name=admitTo]').on('ifChecked', function (event) {
            if ($(event.target).val() === 'W') {
                $('#wardDiv').show();
                $('#roomDiv').hide();
                $('#roomId').val('').trigger('change');
            } else {
                $('#roomDiv').show();
                $('#wardDiv').hide();
                $('#wardId').val('').trigger('change');
            }
        });
        $('#wardId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#roomId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#patientId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#clinicId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
    });
    function loadWard() {
        $('#wardId').find('option').remove();
        $.get('clinic.htm?action=getHospitalWard', {clinicId: $('#clinicId').val()}, function (data) {
            if (data !== null && data.length > 0) {
                $('<option />', {value: '', text: 'Please Select Ward'}).appendTo($('#wardId'));
                for (var i = 0; i < data.length; i++) {
                    $('<option />', {value: data[i].TW_CLINIC_WARD_ID, text: data[i].WARD_NME}).appendTo($('#wardId'));
                }
            } else {
                $('<option />', {value: '', text: 'No Ward found.'}).appendTo($('#wardId'));
            }
        }, 'json');
    }
    function loadRoom() {
        $('#roomId').find('option').remove();
        $.get('clinic.htm?action=getHospitalRoom', {clinicId: $('#clinicId').val()}, function (data) {
            if (data !== null && data.length > 0) {
                $('<option />', {value: '', text: 'Please Select Room'}).appendTo($('#roomId'));
                for (var i = 0; i < data.length; i++) {
                    $('<option />', {value: data[i].TW_CLINIC_ROOM_ID, text: data[i].ROOM_NME}).appendTo($('#roomId'));
                }
            } else {
                $('<option />', {value: '', text: 'No Room found.'}).appendTo($('#roomId'));
            }
        }, 'json');
    }
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="35%">').html('Patient Name'),
                $('<th class="center" width="25%">').html('Clinic Name'),
                $('<th class="center" width="25%">').html('Ward / Room Name'),
                $('<th class="center" width="10%" colspan="2">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getHospitalPatient', {clinicId: $('#clinicId').val(), statusInd: $('input[name=statusInd]:checked').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_CLINIC_PATIENT_ID + '\');"></i>';
                            var dischargeHtm = '<i class="fa fa-sign-out" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="dichargePatient(\'' + list[i].TW_CLINIC_PATIENT_ID + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('input[name=statusInd]:checked').val() === 'D') {
                                editHtm = '&nbsp;';
                                dischargeHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].PATIENT_NME),
                                    $('<td>').html(list[i].CLINIC_NME),
                                    $('<td>').html((list[i].WARD_NME !== '' ? list[i].WARD_NME : list[i].ROOM_NME)),
                                    $('<td align="center">').html(editHtm),
                                    $('<td  align="center">').html(dischargeHtm)
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
    function dichargePatient(id) {
        $('#dischargeModal').modal('show');
        $('#hospitalPatientId').val(id);
    }
    function saveData() {
        if ($('#patientId').val() === '') {
            $('#patientId').notify('Patient Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#patientId').focus();
            return false;
        }
        if ($('#clinicId').val() === '') {
            $('#clinicId').notify('Clinic Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#clinicId').focus();
            return false;
        }
        if ($('input[name=admitTo]:checked').val() === 'W' && $('#wardId').val() === '') {
            $('#wardId').notify('Ward Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#wardId').focus();
            return false;
        }
        if ($('input[name=admitTo]:checked').val() === 'R' && $('#roomId').val() === '') {
            $('#roomId').notify('Room Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#roomId').focus();
            return false;
        }

        var obj = {
            roomId: $('#roomId').val(),
            clinicId: $('#clinicId').val(),
            wardId: $('#wardId').val(),
            patientId: $('#patientId').val(),
            hospitalPatientId: $('#hospitalPatientId').val(),
            bedNo: $('#bedNo').val()
        };
        $.post('clinic.htm?action=saveHospitalPatient', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Patient Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('#hospitalPatientId').val('');
                $('#addPatient').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Patient. Please try again later.", {
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

    function saveDischargeData() {

        var obj = {
            hospitalPatientId: $('#hospitalPatientId').val(),
            dischargeDate: $('#dischargeDate input').val(),
            remarks: $('#remarks').val()
        };
        $.post('clinic.htm?action=saveDischargeData', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Discharged successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('#remarks').val('');
                $('#hospitalPatientId').val('');
                $('#dischargeModal').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in Discharging Patient.", {
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


    function addPatientDialog() {
        $('#hospitalPatientId').val('');
        $('#bedNo').val('');
        $('#roomName').val('');
        $('#addPatient').modal('show');
    }
    function editRow(id) {
        $('#hospitalPatientId').val(id);
        $.get('clinic.htm?action=getHospitalPatientById', {hospitalPatientId: id},
                function (obj) {
                    if (obj.TW_CLINIC_ROOM_ID !== '') {
                        $('input[name=admitTo][value=R]').iCheck('check');
                        $('#roomId').val(obj.TW_CLINIC_ROOM_ID).trigger('change');
                    }
                    if (obj.TW_CLINIC_WARD_ID !== '') {
                        $('input[name=admitTo][value=W]').iCheck('check');
                        $('#wardId').val(obj.TW_CLINIC_WARD_ID).trigger('change');
                    }
                    $('#patientId').val(obj.TW_PATIENT_ID).trigger('change');
                    $('#bedNo').val(obj.TOTAL_BEDS);
                    $('#addPatient').modal('show');
                }, 'json');
    }

</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Hospital Patient </h1>
    </div>
</div>
<div class="modal fade" id="addPatient">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Hospital Patient</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="hospitalPatientId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Where To Admit ?</label>
                                <div class="input-group">
                                    <div class="icheck-inline">
                                        <label>
                                            <input type="radio" name="admitTo" value="W" class="icheck" checked> Ward </label>
                                        <label>
                                            <input type="radio"  name="admitTo" value="R"  class="icheck"> Room</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-8" id="wardDiv">
                            <div class="form-group">
                                <label>Ward Name *</label>
                                <select id="wardId" class="form-control select2_category" data-placeholder="Choose a Ward"> 
                                    <option value="">Please Select Ward</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-8" id="roomDiv">
                            <div class="form-group">
                                <label>Room Name *</label>
                                <select id="roomId" class="form-control select2_category" data-placeholder="Choose a Room">
                                    <option value="">Please Select Room</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-9">
                            <div class="form-group">
                                <label>Patient Name *</label>
                                <select id="patientId" class="form-control select2_category" data-placeholder="Choose a Patient">       
                                    <c:forEach items="${requestScope.refData.patients}" var="obj">
                                        <option value="${obj.TW_PATIENT_ID}">${obj.PATIENT_NME}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Bed No</label>
                                <div>
                                    <input type="text" class="form-control" id="bedNo" onkeyup="onlyInteger(this);" placeholder="Bed No" >
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
                    Hospital Patient
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-10">
                            <div class="form-group">
                                <label>Clinic Name *</label>
                                <select id="clinicId" class="form-control select2_category" onchange="displayData();" data-placeholder="Choose a Clinic">       
                                    <c:forEach items="${requestScope.refData.clinic}" var="obj">
                                        <option value="${obj.TW_CLINIC_ID}">${obj.CLINIC_NME}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-2" style="padding-top: 23px;">
                            <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                <button type="button" class="btn blue" onclick="addPatientDialog();"><i class="fa fa-plus-circle"></i> New Patient</button>
                            </c:if>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <div class="input-group">
                                    <div class="icheck-inline">
                                        <label>
                                            <input type="radio" name="statusInd" value="A" class="icheck" checked> Admitted </label>
                                        <label>
                                            <input type="radio"  name="statusInd" value="D"  class="icheck"> Discharged</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
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

<div class="modal fade" id="dischargeModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Discharge Patient</h3>

            </div>
            <div class="modal-body">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Date</label>
                                <div class="input-group input-medium date date-picker" id="dischargeDate">
                                    <input type="text" class="form-control" readonly="">
                                    <div class="input-group-addon"><i  class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Remarks</label>
                                <div>
                                    <input type="text" class="form-control" id="remarks"  placeholder="Remarks" >
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveDischargeData();">Save</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>

<%@include file="../footer.jsp"%>
