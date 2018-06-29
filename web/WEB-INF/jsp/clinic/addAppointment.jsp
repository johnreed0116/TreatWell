<%-- 
    Document   : addAppointment
    Created on : Oct 4, 2017, 2:30:05 PM
    Author     : farazahmad
--%>
<%@include file="../header.jsp"%>
<link href="assets/global/plugins/fullcalendar/fullcalendar.min.css" rel="stylesheet" type="text/css"/>
<script src="assets/global/plugins/fullcalendar/fullcalendar.min.js"></script>
<script>
    $(document).ready(function () {
        $('#patientName').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#patientName').select2({
            formatNoMatches: function () {
                return '<i style="float: right;" class="btn btn-info btn-sm" id="addNewPatientBtn" onClick="addPatientDialog();">Add</i>';
            }
        });
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
        if ($('#userType').val() === 'ADMIN') {
            $('#clinicId').select2({
                placeholder: "Select an option",
                allowClear: true
            });
            $('#doctorId').select2({
                placeholder: "Select an option",
                allowClear: true
            });
            $('#clinicId').on('change', function (e) {
                getDoctors();
            }).trigger('change');

        } else if ($('#userType').val() === 'CLINIC') {
            $('#doctorId').select2({
                placeholder: "Select an option",
                allowClear: true
            });
            getDoctors();
        }

        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek'
            },
            defaultView: 'agendaWeek',
            editable: true,
            slotDuration: '00:15:00',
            minTime: ($('#clinicOpeningTime').val() !== '' ? $('#clinicOpeningTime').val() : '09:00:00'),
            maxTime: ($('#clinicCloseTime').val() !== '' ? $('#clinicCloseTime').val() : '23:00:00'),
            slotLabelFormat: 'H(:mm)',
            viewRender: function (view, element) {
                var start = view.start.format('DD-MM-YYYY');
                var end = view.end.format('DD-MM-YYYY');
                loadEvents(start, end);
            },
            dayClick: function (date, jsEvent, view) {
                if (view.name === 'agendaWeek') {
                    if (date.isSameOrAfter(moment(), 'day')) {
                        getPatients($('#appointmentDate').val());
                        $('#appointmentDate').val('');
                        $('#appointmentDate').val(date.format("DD-MM-YYYY"));
                        $('#appointmentTime').val('');
                        $('#appointmentTime').val(date.format("HH:mm "));
                        $('#addModal').modal('show');
                    }
                }
            }, eventClick: function (calEvent, jsEvent, view) {
                if (calEvent.status_ind !== 'C') {
                    $('#editModal').modal('show');
                    var dt = moment(calEvent.start);
                    $('#appointmentId').val(calEvent.id);
                    $('#editAppointmentDate').val(dt.format("DD-MM-YYYY"));
                    $('#editAppointmentTime').val(dt.format("HH:mm "));
                    $('#editPatientName').val(calEvent.title);
                    $('#editRemarks').val(calEvent.description);
                    if (calEvent.status_ind !== 'P') {
                        $('#cancelAppointmentBtn').addClass('disabled');
                    } else {
                        if (dt.isSameOrAfter(moment(), 'day')) {
                            $('#cancelAppointmentBtn').removeClass('disabled');
                        } else {
                            $('#cancelAppointmentBtn').addClass('disabled');
                        }
                    }
                }
            }, eventDrop: function (event, delta, revertFunc) {
                var date = moment(event.start);
                if (date.isSameOrAfter(moment(), 'day')) {
                    bootbox.confirm({
                        message: "Do you want to change appointment?",
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
                                $.post('performa.htm?action=updateAppointmentDateTime', {appointmentId: event.id,
                                    time: date.format("HH:mm "), date: date.format("DD-MM-YYYY")}, function (res) {
                                    if (res.msg === 'saved') {
                                        $.bootstrapGrowl("Appointment updated successfully.", {
                                            ele: 'body',
                                            type: 'success',
                                            offset: {from: 'top', amount: 80},
                                            align: 'right',
                                            allow_dismiss: true,
                                            stackup_spacing: 10
                                        });
                                        var start = $('#calendar').fullCalendar('getView').start.format('DD-MM-YYYY');
                                        var end = $('#calendar').fullCalendar('getView').end.format('DD-MM-YYYY');
                                        loadEvents(start, end);
                                    } else {
                                        $.bootstrapGrowl("Appointment can not be updated.", {
                                            ele: 'body',
                                            type: 'error',
                                            offset: {from: 'top', amount: 80},
                                            align: 'right',
                                            allow_dismiss: true,
                                            stackup_spacing: 10
                                        });
                                        revertFunc();
                                    }
                                }, 'json');
                            } else {
                                revertFunc();
                            }
                        }
                    });
                } else {
                    revertFunc();
                }
            }
        });
    });
    function getDoctors() {
        $('#doctorId').find('option').remove();
        $.get('setup.htm?action=getDoctorsForClinic', {clinicId: $('#clinicId').val()}, function (list) {
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    var newOption = new Option(list[i].DOCTOR_NME, list[i].TW_DOCTOR_ID, false, false);
                    $('#doctorId').append(newOption);
                }
            } else {
                var newOption = new Option('No Doctor available.', '', true, false);
                $('#doctorId').append(newOption);
                $('#calendar').fullCalendar('removeEvents');
            }

            $('#doctorId').on('change', function (e) {
                var start = $('#calendar').fullCalendar('getView').start.format('DD-MM-YYYY');
                var end = $('#calendar').fullCalendar('getView').end.format('DD-MM-YYYY');
                loadEvents(start, end);
            });

            $('#doctorId').trigger('change');
        }, 'json');
    }
    function addPatientDialog() {
        $('#patientName').select2('close');
        $('#addModal').modal('hide');
        $('#patientId').val('');
        $('#patientNameInput').val('');
        $('#email').val('');
        $('#contactNo').val('');
        $('#age').val('');
        $('#dob').val('');
        $('#bloodGroup').val('');
        $('#expiryDate').val('');
        $('#patientWeight').val('');
        $('#patientHeight').val('');
        $('#patientAddress').val('');
        $('#referredBy').val('');
        $('#profession').val('');
        $('#addPatient').modal('show');
    }
    function savePatient() {
        if ($.trim($('#patientNameInput').val()) === '') {
            $('#patientNameInput').notify('Patient Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#patientNameInput').focus();
            return false;
        }
        if ($.trim($('#contactNo').val()) === '') {
            $('#contactNo').notify('Contact is Required Field', 'error', {autoHideDelay: 15000});
            $('#contactNo').focus();
            return false;
        }

        // var password = calcMD5($('#password').val());
        var obj = {patientId: $('#patientId').val(), email: $('#email').val(),
            patientName: $('#patientNameInput').val(), contactNo: $('#contactNo').val(), age: $('#age').val(),
            patientWeight: $('#patientWeight').val(), patientHeight: $('#patientHeight').val(),
            patientAddress: $('#patientAddress').val(), gender: $('input[name=gender]:checked').val(),
            cityId: $('#cityId').val(), dob: $('#dob').val(), referredBy: $('#referredBy').val(),
            profession: $('#profession').val(), bloodGroupId: $('#bloodGroup').val()
        };
        $.post('setup.htm?action=savePatient', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Patient Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('#patientId').val('');
                $('#patientName').val('');
                $('#email').val('');
                $('#contactNo').val('');
                $('#age').val('');
                $('#dob').val('');
                $('#expiryDate').val('');
                $('#bloodGroup').val('');
                $('#patientWeight').val('');
                $('#patientHeight').val('');
                $('#patientAddress').val('');
                $('#addPatient').modal('hide');
                //getPatients($('#appointmentDate').val());
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Patient.", {
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


    function getPatients(date) {
        $('#patientName').find('option').remove();
        $.get('performa.htm?action=getAvailablePatientsForAppointment', {clinicId: $('#clinicId').val(),
            doctorId: $('#doctorId').val(), date: date}, function (list) {
            if (list.length > 0) {
                var newOption1 = new Option('Search by name or mobile number.', '', true, false);
                $('#patientName').append(newOption1);
                for (var i = 0; i < list.length; i++) {
                    var newOption = new Option(list[i].PATIENT_NME + ' [' + list[i].MOBILE_NO + ']', list[i].TW_PATIENT_ID, false, false);
                    $('#patientName').append(newOption);
                }
            } else {
                var newOption = new Option('No patient found. Please add patient first?', '', true, false);
                $('#patientName').append(newOption);
            }
            //$('#patientName').select2('container').append('<hr style="margin:5px"><a href="javascript:void(0)" onclick="add_new_option()"> Add New</a>');
            $('#patientName').trigger('change');
        }, 'json');
    }
    function saveAppointment() {
        if ($.trim($('#patientName').val()) === '') {
            $('#patientName').notify('Please select patient.', 'error', {autoHideDelay: 15000});
            $('#patientName').focus();
            return false;
        }
        if ($.trim($('#doctorId').val()) === '') {
            $('#doctorId').notify('Please select doctor.', 'error', {autoHideDelay: 15000});
            $('#doctorId').focus();
            return false;
        }
        var obj = {
            appointmentDate: $('#appointmentDate').val(), appointmentTime: $('#appointmentTime').val(),
            patientId: $('#patientName').val(), remarks: $('#remarks').val(), doctorId: $('#doctorId').val(),
            clinicId: $('#clinicId').val()
        };
        $.post('performa.htm?action=saveAppointment', obj, function (obj) {
            $('#addModal').modal('hide');
            if (obj.msg === 'saved') {
                $.bootstrapGrowl("Appointment saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('#remarks').val('');
                var start = $('#calendar').fullCalendar('getView').start.format('DD-MM-YYYY');
                var end = $('#calendar').fullCalendar('getView').end.format('DD-MM-YYYY');
                loadEvents(start, end);
                return false;
            } else if (obj.msg === 'error') {
                $.bootstrapGrowl("Error in saving data. please try again later.", {
                    ele: 'body',
                    type: 'danger',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
            } else if (obj.msg === 'no_clinic') {
                $.bootstrapGrowl("Doctor dont have any clinic registered. Please contact system administrator.", {
                    ele: 'body',
                    type: 'danger',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
            }
        }, 'json');
        return false;
    }

    function loadEvents(startDate, endDate) {
        if ($.trim($('#doctorId').val()) !== '') {
            var events = [];
            Metronic.blockUI({
                boxed: true,
                message: 'Loading...'
            });
            $.get('performa.htm?action=getAppointmentsForDoctor', {doctorId: $('#doctorId').val(),
                clinicId: $('#clinicId').val(), startDate: startDate, endDate: endDate}, function (list) {
                Metronic.unblockUI();
                if (list.length > 0) {
                    for (var i = 0; i < list.length; i++) {
                        var start = moment(list[i].APPOINTMENT_TIME, "DD-MM-YYYY HH:mm");
                        var end = moment(list[i].APPOINTMENT_TIME, "DD-MM-YYYY HH:mm").add(15, 'minutes');
                        if (list[i].STATUS_IND === 'P') {
                            var obj = {id: list[i].TW_APPOINTMENT_ID, title: list[i].PATIENT_NME, allDay: false,
                                start: start, end: end, editable: true, overlap: false, description: list[i].REMARKS,
                                status_ind: list[i].STATUS_IND, appointmentNo: list[i].APPOINTMENT_NO};
                            events.push(obj);
                        } else if (list[i].STATUS_IND === 'C') {
                            var obj = {id: list[i].TW_APPOINTMENT_ID, title: list[i].PATIENT_NME, allDay: false,
                                start: start, end: end, editable: false, overlap: false, description: list[i].REMARKS,
                                status_ind: list[i].STATUS_IND, color: 'red', textColor: 'white', appointmentNo: list[i].APPOINTMENT_NO};
                            events.push(obj);
                        } else if (list[i].STATUS_IND === 'A') {
                            var obj = {id: list[i].TW_APPOINTMENT_ID, title: list[i].PATIENT_NME, allDay: false,
                                start: start, end: end, editable: false, overlap: false, description: list[i].REMARKS,
                                status_ind: list[i].STATUS_IND, color: 'green', textColor: 'white', appointmentNo: list[i].APPOINTMENT_NO};
                            events.push(obj);
                        } else if (list[i].STATUS_IND === 'D') {
                            var obj = {id: list[i].TW_APPOINTMENT_ID, title: list[i].PATIENT_NME, allDay: false,
                                start: start, end: end, editable: false, overlap: false, description: list[i].REMARKS,
                                status_ind: list[i].STATUS_IND, color: 'blue', textColor: 'white', appointmentNo: list[i].APPOINTMENT_NO};
                            events.push(obj);
                        }
                    }
                    $('#calendar').fullCalendar('removeEvents');
                    $('#calendar').fullCalendar('addEventSource', events);
                }
            }, 'json');
        }
    }
    var Appintment = {
        cancelAppointment: function () {
            var id = $('#appointmentId').val();
            bootbox.confirm({
                message: "Do you want to cancel appointment?",
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
                        $('#editModal').modal('hide');
                        $.post('performa.htm?action=updateAppointmentStatus', {appointmentId: id, status: 'C'}, function (res) {
                            if (res.msg === 'saved') {
                                $.bootstrapGrowl("Appointment cancelled successfully.", {
                                    ele: 'body',
                                    type: 'success',
                                    offset: {from: 'top', amount: 80},
                                    align: 'right',
                                    allow_dismiss: true,
                                    stackup_spacing: 10
                                });
                                var start = $('#calendar').fullCalendar('getView').start.format('DD-MM-YYYY');
                                var end = $('#calendar').fullCalendar('getView').end.format('DD-MM-YYYY');
                                loadEvents(start, end);
                            }
                        }, 'json');
                    }
                }
            });
        }
    };</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Patient Appointment </h1>
    </div>
</div>
<input type="hidden" id="clinicOpeningTime" value="${requestScope.refData.timeFrom}" >
<input type="hidden" id="clinicCloseTime" value="${requestScope.refData.timeTo}" >
<input type="hidden" id="userType" value="${requestScope.refData.userType}">
<div class="modal fade" id="addPatient">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Patient</h3>
            </div>
            <div class="modal-body">
                <input type="hidden" id="patientId" value="">
                <div class="portlet box green">
                    <div class="portlet-title tabbable-line">
                        <div class="caption">
                            Personal Info 
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label>Patient Name*</label>
                                    <div>
                                        <input type="text" class="form-control" id="patientNameInput" placeholder="Patient Name" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Contact No.*</label>
                                    <div>
                                        <input type="text" class="form-control" id="contactNo" placeholder="Contact No." onkeyup="onlyInteger(this);" maxlength="11" onblur="Util.validatePatientNo(this);">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Email</label>
                                    <div>
                                        <input type="text" class="form-control" id="email" placeholder="Email" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Date of Birth</label>
                                    <div class="input-group input-medium date" id="dob-picker">
                                        <input type="text" id="dob" class="form-control" readonly="">
                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Age</label>
                                    <input type="text" class="form-control" id="age" onkeyup="onlyInteger(this);">
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Weight (KG)</label>
                                    <input type="text" class="form-control" id="patientWeight" onkeyup="onlyInteger(this);">
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Height (Feet)</label>
                                    <input type="text" class="form-control" id="patientHeight" onkeyup="onlyInteger(this);">
                                </div>
                            </div>
                            <div class="col-md-3">
                                <label>Blood Group</label>
                                <select id="bloodGroup" class="form-control" data-placeholder="Choose a Blood Group">
                                    <c:forEach items="${requestScope.refData.bloodGroup}" var="obj">
                                        <option value="${obj.TW_BLOOD_GROUP_ID}">${obj.TITLE}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div> 
                        <div class="row">
                            <div class="col-md-8">
                                <label>City</label>
                                <select id="cityId" name="cityId" class="form-control" data-placeholder="Choose a City">
                                    <c:forEach items="${requestScope.refData.cities}" var="obj">
                                        <option value="${obj.CITY_ID}"
                                                <c:if test="${obj.CITY_NME=='Lahore'}">
                                                    selected="selected"
                                                </c:if>
                                                >${obj.CITY_NME}</option>
                                    </c:forEach>
                                </select>
                            </div>
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
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Referred by</label>
                                    <input type="text" class="form-control" id="referredBy" >
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Profession</label>
                                    <input type="text" class="form-control" id="profession" >
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Address</label>
                                    <textarea class="form-control" id="patientAddress" rows="3" cols="63"></textarea>
                                </div>
                            </div>   
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save" onclick="savePatient();">Save</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="addModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Appointment</h3>
            </div>
            <div class="modal-body">
                <form action="#" role="form" method="post" >
                    <div class="form-group">
                        <label  for="patientName" >Patient Name</label>
                        <select class="select2_category form-control" name="patientName" id="patientName" data-placeholder="Choose a Patient" tabindex="1">
                            <option value="">Search by name or mobile number.</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Appointment Date</label>
                        <input type="text" class="form-control" id="appointmentDate" readonly=""  >
                    </div>
                    <div class="form-group">
                        <label>Appointment Time</label>
                        <input type="text" class="form-control" id="appointmentTime" readonly=""  >
                    </div>
                    <div class="form-group">
                        <label>Remarks</label>
                        <textarea rows="2" cols="40" class="form-control" id="remarks"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveAppointment();">Save</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">View Appointment</h3>

            </div>
            <div class="modal-body">
                <form action="#" role="form" method="post" >
                    <input type="hidden" id="appointmentId" value="">
                    <div class="form-group">
                        <label>Appointment Date</label>
                        <input type="text" class="form-control" id="editAppointmentDate" readonly=""  >
                    </div>
                    <div class="form-group">
                        <label>Appointment Time</label>
                        <input type="text" class="form-control" id="editAppointmentTime" readonly=""  >
                    </div>
                    <div class="form-group">
                        <label  for="patientName" >Patient Name</label>
                        <input type="text" class="form-control" id="editPatientName" readonly=""  >
                    </div>
                    <div class="form-group">
                        <label>Remarks</label>
                        <textarea rows="2" cols="40" class="form-control" id="editRemarks"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="cancelAppointmentBtn" onclick="Appintment.cancelAppointment();">Cancel Appointment</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="portlet box green">

    <div class="portlet-body">
        <c:choose>
            <c:when test="${requestScope.refData.userType=='ADMIN'}">
                <div class="row">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label>Select Clinic</label>
                            <select id="clinicId" class="select2_category form-control" data-placeholder="Choose a Clinic">
                                <c:forEach items="${requestScope.refData.clinics}" var="obj" varStatus="i">
                                    <option value="${obj.TW_CLINIC_ID}">${obj.CLINIC_NME}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label>Select Doctor</label>
                            <select id="doctorId" class="select2_category form-control" data-placeholder="Choose a Doctor">
                                <option value="">Select Doctor</option>
                            </select>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:when test="${requestScope.refData.userType=='DOCTOR'}">
                <input type="hidden" id="doctorId" value="${requestScope.refData.doctorId}">
                <input type="hidden" id="clinicId" value="${requestScope.refData.clinicId}">
            </c:when>
            <c:when test="${requestScope.refData.userType=='CLINIC'}">
                <div class="row">
                    <input type="hidden" id="clinicId" value="${requestScope.refData.clinicId}">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label>Select Doctor</label>
                            <select id="doctorId" class="select2_category form-control" data-placeholder="Choose a Doctor">
                                <option value="">Select Doctor</option>
                            </select>
                        </div>
                    </div>
                </div>
            </c:when>
        </c:choose>

        <div class="row">
            <div class="col-md-12">
                <div id='calendar'></div>
            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>

