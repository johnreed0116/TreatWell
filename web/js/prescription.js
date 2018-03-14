function getCollectionCenter(param) {
    //Find all areas
    var tr = $(param).closest('tr');
    var select = tr.find('select[name=collectionCenterId]');
    $(select).find('option').remove();
    if ($(param).val() !== '') {
        $.get('performa.htm?action=getLabCollectionCenter', {medicalLabId: $(param).val()}, function (data) {
            $('<option />', {value: '', text: 'Please select center'}).appendTo($(select));
            if (data !== null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $('<option />', {value: data[i].TW_LAB_DETAIL_ID, text: data[i].CENTER_NME}).appendTo($(select));
                }
            } else {
                $('<option />', {value: '', text: 'No Center Found'}).appendTo($(select));
            }
            $(select).select2("val", "").trigger('change');
        }, 'json');
    }
}
function getAppointmentDates() {
    $('#dates').find('option').remove();
    $.get('performa.htm?action=getAppointmentDates', {}, function (data) {
        $('<option />', {value: '', text: 'Please select Date'}).appendTo($('#dates'));
        if (data !== null && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                $('<option />', {value: data[i].DTE, text: data[i].DTE}).appendTo($('#dates'));
            }
        } else {
            $('<option />', {value: '', text: 'No Date Found'}).appendTo($('#dates'));
        }
    }, 'json');
}
function getAppointedTime() {
    $('#time').find('option').remove();
    if ($('#dates').val() !== '') {
        $.get('performa.htm?action=getAppointedTime', {date: $('#dates').val()}, function (data) {
            $('<option />', {value: '', text: 'Please select center'}).appendTo($('#time'));
            var a = moment($('#timeFrom').val(), "HH:mm");
            var b = moment($('#timeTo').val(), "HH:mm");
            var duration = moment.duration(b.diff(a));
            var mins = duration.asMinutes();
            var interval = Math.floor(mins / 15);
            for (var i = 0; i <= interval; i++) {
                var islocked = false;
                if (data.length > 0) {
                    for (var z = 0; z < data.length; z++) {
                        if (data[z].APPOINTED_TIME === a.format("HH:mm")) {
                            islocked = true;
                            break;
                        }
                    }
                }
                if (!islocked) {
                    $('<option />', {value: moment(a).format("HH:mm"), text: moment(a).format("HH:mm")}).appendTo($('#time'));
                }
                a.add(15, 'minutes');
            }
        }, 'json');
    } else {
        $('<option />', {value: '', text: 'Please Select Time'}).appendTo($('#time')).trigger('change');
    }
}
function saveData() {
    var medicineName = [], medicineDays = [], medicineQty = [], medicineFrequency = [], medicineInstructions = [];
    var labIdArr = [], labCenterIdArr = [], occurrenceArr = [];
    var labTestIdArr = [];
    var tr = $('#medicineTable').find('tbody').find('tr');
    var flag = true;
    if ($(tr).length === 1) {
        var td = $(tr).find('td');
        var field = null;
        var fl = false;
        $.each(td, function (index, innerObj) {
            if (index === 0) {
                if ($(innerObj).find('select').val() !== '') {
                    medicineName.push($(innerObj).find('select').val());
                    fl = true;
                }
            }
            if (index === 1 && fl) {
                medicineDays.push($(innerObj).find('input:text').val());
            }
            if (index === 2 && fl) {
                medicineQty.push($(innerObj).find('input:text').val());
            }
            if (index === 3 && fl) {
                medicineFrequency.push($(innerObj).find('select').val());
            }
            if (index === 4 && fl) {
                medicineInstructions.push($(innerObj).find('select').val());
            }
        });
    } else if ($(tr).length > 1) {
        $.each(tr, function (index, obj) {
            var td = $(obj).find('td');
            var field = null;
            $.each(td, function (index, innerObj) {
                if (index === 0) {
                    medicineName.push($(innerObj).find('select').val());
                    if ($(innerObj).find('select').val() === '') {
                        flag = false;
                        field = $(innerObj).find('select');
                    }
                }
                if (index === 1) {
                    medicineDays.push($(innerObj).find('input:text').val());
                    if ($(innerObj).find('input:text').val() === '') {
                        flag = false;
                        field = $(innerObj).find('input:text');
                    }
                }
                if (index === 2) {
                    medicineQty.push($(innerObj).find('input:text').val());
                    if ($(innerObj).find('input:text').val() === '') {
                        flag = false;
                        field = $(innerObj).find('input:text');
                    }
                }
                if (index === 3) {
                    medicineFrequency.push($(innerObj).find('select').val());
                }
                if (index === 4) {
                    medicineInstructions.push($(innerObj).find('select').val());
                }
            });

        });
    }
    flag = true;
    tr = $('#testTable').find('tbody').find('tr');
    if ($(tr).length === 1) {
        var td = $(tr).find('td');
        var field = null;
        $.each(td, function (index, innerObj) {
            if (index === 0) {
                if ($(innerObj).find('select').val() !== '') {
                    labTestIdArr.push($(innerObj).find('select').val());
                }
            } else if (index === 1) {
                if ($(innerObj).find('select').val() !== '') {
                    labIdArr.push($(innerObj).find('select').val());
                }
            } else if (index === 2) {
                if ($(innerObj).find('select').val() !== '') {
                    labCenterIdArr.push($(innerObj).find('select').val());
                }
            } else if (index === 3) {
                occurrenceArr.push($(innerObj).find('input:text').val());
            }
        });
    } else if ($(tr).length > 1) {
        var flg = true;
        $.each(tr, function (index, obj) {
            var td = $(obj).find('td');
            var field = null;
            $.each(td, function (index, innerObj) {
                if (index === 0 && flg) {
                    labTestIdArr.push($(innerObj).find('select').val());
                    if ($(innerObj).find('select').val() === '') {
                        flg = false;
                        field = $(innerObj).find('select');
                    }
                } else if (index === 1 && flg) {
                    if ($(innerObj).find('select').val() !== '') {
                        labIdArr.push($(innerObj).find('select').val());
                    }
                } else if (index === 2 && flg) {
                    if ($(innerObj).find('select').val() !== '') {
                        labCenterIdArr.push($(innerObj).find('select').val());
                    }
                } else if (index === 3 && flg) {
                    occurrenceArr.push($(innerObj).find('input:text').val());
                }
            });

        });
        if (!flg) {
            $(field).notify('Select a test or remove this row.', 'error');
            return false;
        }
    }
    if (flag) {
        if ($.trim($('#patientId').val()) === '') {
            $('#patientId').notify('Select a patient to save prescription.', 'error');
            $('#patientId').focus();
            return false;
        } else {
            if ($('#addApointment').is(':checked')) {
                if ($.trim($('#dates').val()) === '') {
                    $('#dates').notify('Select a Date to save Next Appointment.', 'error');
                    $('#dates').focus();
                    return false;
                }
                if ($.trim($('#time').val()) === '') {
                    $('#time').notify('Select a Time to save Next Appointment.', 'error');
                    $('#time').focus();
                    return false;
                }
                var obj = {
                    appointmentDate: $('#dates').val(), appointmentTime: $('#time').val(),
                    patientId: $('#patientId').val(), remarks: ''
                };
                $.post('performa.htm?action=saveAppointment', obj, function (obj) {
                    if (obj.msg === 'saved') {
                        $.bootstrapGrowl("Appointment saved successfully.", {
                            ele: 'body',
                            type: 'success',
                            offset: {from: 'top', amount: 80},
                            align: 'right',
                            allow_dismiss: true,
                            stackup_spacing: 10
                        });
                        $('#addApointment').attr('checked', '').trigger('click');
                        $('#dates').val('').trigger('change');

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
            }
            $.post('performa.htm?action=savePrescription', {
                patientId: $('#patientId').val(), remarks: $('#comments').val(), 'medicineIdArr[]': medicineName,
                'daysArr[]': medicineDays, 'qtyArr[]': medicineQty, 'frequencyIdArr[]': medicineFrequency,
                'usageIdArr[]': medicineInstructions, 'labIdArr[]': labIdArr, 'labTestIdArr[]': labTestIdArr,
                'labCenterIdArr[]': labCenterIdArr, 'occurrenceArr[]': occurrenceArr, date: $('#dates').val(),
                time: $('#time').val()
            }, function (obj) {
                if (obj.msg === 'saved') {
                    $.bootstrapGrowl("Prescription saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    getAppointedPatientsForDoctor();
                    $('#comments').val('');
                    document.getElementById("prescForm").action = 'performa.htm?action=printPrescription&id=' + obj.masterId;
                    document.getElementById("prescForm").target = '_blank';
                    document.getElementById("prescForm").submit();
                    $('#medicineTable').find('tbody').find('tr').not(':eq(0)').remove();
                } else if (obj.msg === 'error') {
                    $.bootstrapGrowl("Error in saving prescription.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                } else {
                    $.bootstrapGrowl("Doctor do not have a clinic defined. Please contact system administrator.", {
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
    return false;
}
function fillValue(param) {
    if ($.trim(param.value) !== null && $.trim(param.value) !== '') {
        var nextTd = $(param).parent().next();
        $(nextTd).find('input:text').val('1');
        var nextTd = $(param).parent().next().next();
        $(nextTd).find('input:text').val('1');
    }
}
function addRow(param) {
    var tr = $(param).parent().parent().clone();
    tr.find('input:text').val('1');
    var select = tr.find('select');
    select.removeClass('select2-offscreen');
    tr.find('td:eq(0)').html('');
    tr.find('td:eq(0)').html(select[0]);
    tr.find('td:eq(3)').html('');
    tr.find('td:eq(3)').html(select[1]);
    tr.find('td:eq(4)').html('');
    tr.find('td:eq(4)').html($(select[2]).select2("val", ""));
    tr.find('td:last').html('');
    tr.find('td:last').html('<button type="button" class="btn btn-sm red" onclick="removeRow(this);" ><i class="fa fa-minus-circle" aria-hidden="true"></i></button>');
    var tbody = $(param).parent().parent().parent();
    tr.appendTo(tbody);
    tr.find('select').select2({
        placeholder: "Select an option",
        allowClear: true
    });
}
function addLabTestRow(param) {
    var tr = $(param).parent().parent().clone();
    tr.find('input:text').val('');
    var select = tr.find('select');
    select.removeClass('select2-offscreen');
    tr.find('td:eq(0)').html('');
    tr.find('td:eq(0)').html(select[0]);
    tr.find('td:eq(1)').html('');
    tr.find('td:eq(1)').html(select[1]);
    tr.find('td:eq(2)').html('');
    tr.find('td:eq(2)').html(select[2]);
    tr.find('td:last').html('');
    tr.find('td:last').html('<button type="button" class="btn btn-sm red" onclick="removeRow(this);" ><i class="fa fa-minus-circle" aria-hidden="true"></i></button>');
    var tbody = $(param).parent().parent().parent();
    tr.appendTo(tbody);
    tr.find('select').select2({
        placeholder: "Select an option",
        allowClear: true
    });
}
function removeRow(param) {
    $(param).closest('tr').remove();
}
function getPrescription() {
    if ($('#patientId').val() !== '') {
        $.get('setup.htm?action=getPatientById', {patientId: $('#patientId').val()},
                function (obj) {
                    $('#patientName').val(obj.PATIENT_NME);
                    $('#cityId').val(obj.CITY_NME);
                    $('#contactNo').val(obj.MOBILE_NO);
                    $('#address').val(obj.ADDRESS);
                    $('#referredBy').val(obj.REFERRED_BY);

                    //get prescriptions
                    var $tbl = $('<table class="table table-striped table-condensed">');
                    $tbl.append($('<thead>').append($('<tr>').append(
                            $('<th class="center" width="10%">').html('Sr. #'),
                            $('<th class="center" width="50%">').html('Patient Name'),
                            $('<th class="center" width="20%">').html('Date'),
                            $('<th class="center" width="20%" colspan="2">').html('&nbsp;')
                            )));
                    $.get('clinic.htm?action=getPrescriptionListing', {patientId: $('#patientId').val(), dateFrom: null,
                        dateTo: null},
                            function (list) {
                                //get intake form
                                $('#intakeFormQuestions').html('&nbsp;');
                                $.get('performa.htm?action=getIntakeFormData', {patientId: $('#patientId').val()},
                                        function (data) {
                                            var htm = '<div class="row"><div class="col-md-12">';
                                            htm += '<table class="table table-striped table-condensed" id="displayPatientAttachTbl">';
                                            htm += '<thead><tr><th>Sr#</th><th>Question</th><th>Answer</th></tr></thead><tbody>';
                                            if (data.length > 0) {
                                                for (var z = 0; z < data.length; z++) {
                                                    htm += '<tr>';
                                                    htm += '<td>' + eval(z + 1) + '</td>';
                                                    htm += '<td>' + data[z].QUESTION_TXT + '</td>';
                                                    htm += '<td>' + data[z].ANSWER_TXT + '</td>';
                                                    htm += '</tr>';
                                                }
                                            } else {
                                                htm += '<tr>';
                                                htm += '<td><b>Patient did not fill intake form.</b></td>';
                                                htm += '</tr>';
                                            }
                                            htm += '</tbody>';
                                            htm += '</table>';
                                            htm += '</div></div>';
                                            $('#intakeFormQuestions').append(htm);
                                            $('#inTakeForm').modal('show');
                                        }, 'json');

                                if (list !== null && list.length > 0) {
                                    $tbl.append($('<tbody>'));
                                    for (var i = 0; i < list.length; i++) {
                                        $tbl.append(
                                                $('<tr>').append(
                                                $('<td align="center">').html(eval(i + 1)),
                                                $('<td>').html(list[i].PATIENT_NME),
                                                $('<td >').html(list[i].PREPARED_DTE),
                                                $('<td align="center">').html('<i class="fa fa-print" aria-hidden="true" title="Click to Print" style="cursor: pointer;" onclick="printPrescription(\'' + list[i].TW_PRESCRIPTION_MASTER_ID + '\');"></i>')
                                                ));
                                    }
                                    $('#prescriptionDiv').html('');
                                    $('#prescriptionDiv').append($tbl);
                                } else {
                                    $('#prescriptionDiv').html('');
                                    $tbl.append(
                                            $('<tr>').append(
                                            $('<td  colspan="4">').html('<b>No prescrption found.</b>')
                                            ));
                                    $('#prescriptionDiv').append($tbl);
                                }
                            }, 'json');
                }, 'json');
    }
}
function getDiseases() {
    var htm = '<div class="row"><div class="col-md-12">';
    htm += '<i class="fa fa-check danger"></i>&nbsp;&nbsp;';

    $.get('setup.htm?action=getPatientDiseasesById', {patientId: $('#patientId').val()},
            function (obj) {
                if (obj !== null && obj.length > 0) {
                    $('#diagnosticsPanel').show();
                    for (var i = 0; i < obj.length; i++) {
                        htm += obj[i].TITLE + ", ";
                    }
                } else {
                    if ($('#panelPatient').is(':hidden')) {
                        $('#diagnosticsPanel').hide();
                        htm += 'No disease found.';
                    }
                }
                htm += '</div></div>';
                $('#diseaseDiv').html('');
                $('#diseaseDiv').append(htm);
            }, 'json');
}
function attachReport() {
    displayReportAttachements();
    $('#attachModal').modal('show');
}
function saveReading() {
    var data = new FormData(document.getElementById('reading'));
    if ($.trim($('#patientId').val()) !== '') {

        data.append('patientId', $('#patientId').val());
        $.ajax({
            url: "performa.htm?action=saveReadings",
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (data) {
            if (data) {
                if (data.result === 'save_success') {
                    $.bootstrapGrowl("Reading saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                } else {
                    $.bootstrapGrowl("Error in saving data. please try again.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                }
            }
        });
    }
}
function saveReports() {
    var data = new FormData(document.getElementById('reportAttachmentFrom'));
    data.append('patientId', $('#patientId').val());
    $.ajax({
        url: "setup.htm?action=savePatientReports",
        type: "POST",
        data: data,
        cache: false,
        dataType: 'json',
        processData: false, // tell jQuery not to process the data
        contentType: false   // tell jQuery not to set contentType

    }).done(function (data) {
        if (data) {
            if (data.result === 'save_success') {
                $.bootstrapGrowl("Report Upload successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10

                });
                displayReportAttachements();

            } else {
                $.bootstrapGrowl("Error in Report Uploading.", {
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
function getPatientReading() {
    $.get('performa.htm?action=getReading', {patientId: $('#patientId').val()},
            function (obj) {
                $('#sugar').val(obj.SUGAR);
                $('#fever').val(obj.FEVER);
                $('#bloodPressure').val(obj.BLOOD_PRESSURE);
            }, 'json');
}
function getAppointedPatientsForDoctor() {
    $('#patientId').find('option').remove();
    var showAll = 'N';
    if ($('#showAllPatients').is(':checked')) {
        showAll = 'Y';
    }
    $.get('performa.htm?action=getAppointedPatientsForDoctor', {showAll: showAll}, function (data) {
        if (data !== null && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                $('<option />', {value: data[i].TW_PATIENT_ID, text: data[i].PATIENT_NME + " [" + data[i].MOBILE_NO + "]"
                    , companyId: data[i].TW_COMPANY_ID, balance: data[i].BALANCE}).appendTo($('#patientId'));
            }
        } else {
            $('<option />', {value: '', text: 'No patiend is available.'
                , companyId: '', balance: '0'}).appendTo($('#patientId'));
        }
        $('#patientId').trigger('change');
    }, 'json');
}
function displayReportAttachements() {
    var $tbl = $('<table class="table table-striped table-bordered table-hover">');
    $tbl.append($('<thead>').append($('<tr>').append(
            $('<th class="center" width="10%">').html('Sr. #'),
            $('<th class="center" width="60%">').html('Description'),
            $('<th class="center" width="20%">').html('Attachment'),
            $('<th class="center" width="10%">').html('&nbsp;')
            )));
    $.get('setup.htm?action=getReportActtachementsById', {doctorId: $('#doctorId').val(), patientId: $('#patientId').val()},
            function (list) {
                if (list !== null && list.length > 0) {
                    $tbl.append($('<tbody>'));
                    for (var i = 0; i < list.length; i++) {
                        $tbl.append(
                                $('<tr>').append(
                                $('<td align="center">').html(eval(i + 1)),
                                $('<td>').html(list[i].FILE_DESC),
                                $('<td align="center">').html('<a href="upload/patient/prescription/' + list[i].TW_PATIENT_ID + '/' + list[i].FILE_NME + '" target="_blank"><img src="images/attach-icon.png" width="20" height="20"></a>'),
                                $('<td align="center">').html('<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteReportAttachement(\'' + list[i].TW_PATIENT_ATTACHMENT_ID + '\');"></i>')
                                ));
                    }
                    $('#reportDiv').html('');
                    $('#reportDiv').append($tbl);
                } else {
                    $('#reportDiv').html('');
                    $tbl.append(
                            $('<tr>').append(
                            $('<td  colspan="4">').html('<b>No data found.</b>')
                            ));
                    $('#reportDiv').append($tbl);
                }
            }, 'json');
}
function deleteReportAttachement(attachmentId) {
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
                $.post('setup.htm?action=deleteReportAttachement', {attachmentId: attachmentId}, function (res) {
                    if (res.result === 'save_success') {
                        $.bootstrapGrowl("Record deleted successfully.", {
                            ele: 'body',
                            type: 'success',
                            offset: {from: 'top', amount: 80},
                            align: 'right',
                            allow_dismiss: true,
                            stackup_spacing: 10
                        });
                        displayReportAttachements();
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
function printPrescription(masterId) {
    document.getElementById("prescForm").action = 'performa.htm?action=printPrescription&id=' + masterId;
    document.getElementById("prescForm").target = '_blank';
    document.getElementById("prescForm").submit();
}
function markExamination() {
    if ($.trim($('#patientId').val()) !== '') {
        document.getElementById("prescForm").action = 'setup.htm?action=addPatientExamination&patientId=' + $('#patientId').val();
        document.getElementById("prescForm").target = '_blank';
        document.getElementById("prescForm").submit();
    } else {
        $.bootstrapGrowl("Please Select Patient First.", {
            ele: 'body',
            type: 'danger',
            offset: {from: 'top', amount: 80},
            align: 'right',
            allow_dismiss: true,
            stackup_spacing: 10
        });
    }
}
function getExaminationRevision() {
    if ($.trim($('#revisionNo').val()) !== '' && $.trim($('#patientId').val()) !== '') {
        $.get('setup.htm?action=getExaminationRevision', {patientId: $('#patientId').val(),
            revisionNo: $('#revisionNo').val()},
                function (data) {
                    $('.examinitionICheck').iCheck('uncheck');
                    $('textarea').val('');
                    if (data !== null && data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].REMARKS === '') {
                                $('input[name=question_' + data[i].TW_QUESTION_MASTER_ID + '][value=' + data[i].TW_QUESTION_DETAIL_ID + ']').iCheck('check');
                            } else {
                                $('input[name=question_' + data[i].TW_QUESTION_MASTER_ID + '_other][value=' + data[i].TW_QUESTION_DETAIL_ID + ']').iCheck('check');
                                $('textarea[name=other_' + data[i].TW_QUESTION_MASTER_ID + "]").val(data[i].REMARKS);
                            }
                        }
                    }
                }, 'json');
    }
}
function saveMarkedQuestion() {
    global.detail.length = 0;
    if (global.masterId.length) {
        for (var i = 0; i < global.masterId.length; i++) {
            var input = $('input[name=question_' + global.masterId[i] + ']');
            var value = "";
            $.each(input, function (index, obj) {
                if ($(obj).is(':checked')) {
                    value += $(obj).val() + ",";
                }
            });
            if ($('input[name=question_' + global.masterId[i] + '_other]').is(':checked')) {
                value += $('input[name=question_' + global.masterId[i] + '_other]').val() + "_" + $('textarea[name=other_' + global.masterId[i] + "]").val();
            }
            global.detail.push(value);
        }
        var obj = {
            patientId: $('#patientId').val(),
            'questionarr[]': global.masterId, 'answerarr[]': global.detail,
            questionCategory: $('#questionCategory').val()
        };
        $.post('setup.htm?action=saveExamination', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Answers saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                global.detail.length = 0;

            } else {
                $.bootstrapGrowl("Error in saving data. Please try again later.", {
                    ele: 'body',
                    type: 'danger',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                global.detail.length = 0;
            }
        }, 'json');
    }
    return false;
}

function getPatientRevisionNo() {
    $('#revisionNo').find('option').remove();
    $.get('setup.htm?action=getPatientRevisions', {patientId: $('#patientId').val()}, function (data) {
        if (data !== null && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                $('<option />', {value: data[i].REVISION_NO, text: data[i].REVISION_NO}).appendTo($('#revisionNo'));
            }
        } else {
            $('<option />', {value: '1', text: '1'}).appendTo($('#revisionNo'));
        }
        getExaminationRevision();
    }, 'json');
    return false;
}

function displayExaminationQuestions() {
    var htm = '';
    global.masterId.length = 0;
    $.get('setup.htm?action=getExaminationQuestion', {categoryId: $('#questionCategory').val()}, function (data) {
        if (data !== null && data.length > 0) {
            $.get('setup.htm?action=getAnswerByCategory', {categoryId: $('#questionCategory').val()}, function (detailData) {
                if (detailData !== null && detailData.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        global.masterId.push(data[i].TW_QUESTION_MASTER_ID);
                        htm += '<div class="portlet box green-meadow">';
                        htm += '<div class="portlet-title"><div class="caption" >' + eval(i + 1) + '. ' + data[i].QUESTION_TXT + '</div></div>';
                        htm += '<div class="portlet-body">';
                        for (var j = 0; j < detailData.length; j++) {
                            if (data[i].TW_QUESTION_MASTER_ID === detailData[j].TW_QUESTION_MASTER_ID) {
                                htm += '<div class="row"><div class="col-md-12">';
                                if (detailData[j].ANSWER_TXT === 'Others') {
                                    //onClick="enableTextAreaForInput(this,\'' + detailData[j].TW_QUESTION_MASTER_ID + '\');"
                                    htm += ' <input type="checkbox" name="question_' + data[i].TW_QUESTION_MASTER_ID + '_other" value="' + detailData[j].TW_QUESTION_DETAIL_ID + '" class="examinitionICheck" >';
                                    htm += detailData[j].ANSWER_TXT + '<br/><br/>';
                                    htm += '<textarea class="form-control" readonly="" rows="2"  name="other_' + data[i].TW_QUESTION_MASTER_ID + '" id="other_' + data[i].TW_QUESTION_MASTER_ID + '"></textarea>';
                                } else {
                                    htm += ' <input type="checkbox" name="question_' + data[i].TW_QUESTION_MASTER_ID + '" value="' + detailData[j].TW_QUESTION_DETAIL_ID + '" class="examinitionICheck" >';
                                    htm += detailData[j].ANSWER_TXT;
                                }
                                htm += '</div></div>';
                            }
                        }
                        htm += '</div></div>';
                    }
                }
                $('#examQuestionsDiv').html(htm);
                $('.examinitionICheck').iCheck({
                    checkboxClass: 'icheckbox_square',
                    radioClass: 'iradio_square'
                });
                $('.examinitionICheck').on('ifChecked', function (event) {
                    //question_' + data[i].TW_QUESTION_MASTER_ID + '_other
                    var name = $(this).attr('name');
                    var arr = name.split('_');
                    if (arr.length && arr.length > 2) {
                        var objId = arr[1];
                        var id = 'other_' + objId;
                        $('#' + id).prop('readonly', false);
                    }
                });
                $('.examinitionICheck').on('ifUnchecked', function (event) {
                    var name = $(this).attr('name');
                    var arr = name.split('_');
                    if (arr.length && arr.length > 2) {
                        var objId = arr[1];
                        var id = 'other_' + objId;
                        $('#' + id).prop('readonly', true);
                    }
                });
                getExaminationRevision();
            }, 'json');
        }

    }, 'json');
}
function displayVaccinationDetail() {
    if ($('#vaccinationMasterId').val() !== '') {
        var $tbl = $('<table class="table table-striped table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('&nbsp;'),
                $('<th class="center" width="70%">').html('Medicine Name'),
                $('<th class="center" width="20%">').html('Dose Usage')
                )));
        $.get('setup.htm?action=getVaccinationDetail', {vaccinationId: $('#vaccinationMasterId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var addHtm = '<input title="Click to Add" name="vaccinationDetail" checked="checked" type="checkbox"  value="' + list[i].TW_VACCINATION_DETAIL_ID + '">';
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(addHtm),
                                    $('<td>').html(list[i].MEDICINE_NME),
                                    $('<td>').html(list[i].TOTAL_DOSE)
                                    ));
                        }
                        $('#detailDiv').html('');
                        $('#detailDiv').append($tbl);
                        return false;
                    } else {
                        $('#detailDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td  colspan="4">').html('<b>No data found.</b>')
                                ));
                        $('#detailDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    } else {
        $('#detailDiv').html('');
    }
}
jQuery.fn.getCheckboxVal = function () {
    var vals = [];
    var i = 0;
    this.each(function () {
        vals[i++] = jQuery(this).val();
    });
    return vals;
};
function saveVaccination() {
    if ($.trim($('#patientId').val()) === '') {
        $('#patientId').notify('Patient Name is Required Field', 'error', {autoHideDelay: 15000});
        $('#patientId').focus();
        return false;
    }
    var selectedVaccination = [];
    var selectVaccination = $('input[name="selectVaccination"]');
    for (var i = 0; i < selectVaccination.length; i++) {
        if ($(selectVaccination[i]).is(':checked')) {
            selectedVaccination.push($(selectVaccination[i]).val());
        }
    }
    if (selectedVaccination.length === 0) {
        $.notify('Select Vaccination to apply.', 'error', {autoHideDelay: 15000});
        return false;
    }
//    var vaccinationMasterId = $('input[name=vaccinationDetail]:checked').getCheckboxVal();
//    if (vaccinationMasterId.length === 0) {
//        $.bootstrapGrowl("Please Select atleast one medicine.", {
//            ele: 'body',
//            type: 'danger',
//            offset: {from: 'top', amount: 80},
//            align: 'right',
//            allow_dismiss: true,
//            stackup_spacing: 10
//        });
//        return false;
//    }
    $.post('performa.htm?action=saveVaccination', {vaccinationDate: $('#vaccinationDate').val(),
        'vaccinationMasterId[]': selectedVaccination, patientId: $('#patientId').val()},
            function (res) {
                if (res.msg === 'saved') {
                    $.bootstrapGrowl("Vaccination saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    displayVaccination();
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
            }, 'json');
}
function displayVaccination() {
    var $tbl = $('<table class="table table-striped table-hover table-condensed">');
    $tbl.append($('<thead>').append($('<tr>').append(
            $('<th class="center" width="20%">').html('Sr.#'),
            $('<th class="center" width="40%">').html('Vaccination Name'),
            $('<th class="center" width="20%">').html('Abbrev'),
            $('<th class="center" width="20%">').html('Vaccination Date')
            )));
    $.get('performa.htm?action=getPateintVaccination', {patientId: $('#patientId').val()},
            function (list) {
                if (list !== null && list.length > 0) {
                    $tbl.append($('<tbody>'));
                    for (var i = 0; i < list.length; i++) {
                        var addHtm = '<i class="fa fa-eye" aria-hidden="true" title="Click to View" style="cursor: pointer;" onclick="showHistory(\'' + list[i].TW_VACCINATION_MASTER_ID + '\',\'' + list[i].VACCINATION_DTE + '\');"></i>';
                        $tbl.append(
                                $('<tr>').append(
                                $('<td>').html(eval(i + 1)),
                                $('<td>').html(list[i].VACCINATION_NME),
                                $('<td>').html(list[i].ABBREV),
                                $('<td>').html(list[i].VACCINATION_DTE)
                                ));
                    }
                    $('#vaccinationDiv').html('');
                    $('#vaccinationDiv').append($tbl);
                    return false;
                } else {
                    $('#vaccinationDiv').html('');
                    $tbl.append(
                            $('<tr>').append(
                            $('<td  colspan="4">').html('<b>No vaccination added.</b>')
                            ));
                    $('#vaccinationDiv').append($tbl);
                    return false;
                }
            }, 'json');
}

function showHistory(id, dte) {
    var $tbl = $('<table class="table table-striped table-hover">');
    $tbl.append($('<thead>').append($('<tr>').append(
            $('<th class="center" width="5%">').html('Sr.#'),
            $('<th class="center" width="65%">').html('Medicine Name'),
            $('<th class="center" width="20%">').html('Total Dose')
            )));
    $.get('performa.htm?action=getPateintVaccinationMedicine', {masterId: id, date: dte},
            function (list) {
                if (list !== null && list.length > 0) {
                    $tbl.append($('<tbody>'));
                    for (var i = 0; i < list.length; i++) {
                        $tbl.append(
                                $('<tr>').append(
                                $('<td  align="center">').html(eval(i + 1)),
                                $('<td>').html(list[i].MEDICINE_NME),
                                $('<td>').html(list[i].TOTAL_DOSE)
                                ));
                    }
                    $('#medicineDiv').html('');
                    $('#medicineDiv').append($tbl);
                    $('#viewMedicine').modal('show');
                }
            }, 'json');
}