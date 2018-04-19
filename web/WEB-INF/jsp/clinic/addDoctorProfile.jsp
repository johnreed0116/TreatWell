<%-- 
    Document   : addDoctorProfile
    Created on : Nov 17, 2017, 5:15:37 PM
    Author     : farazahmad
--%>

<%@include file="../header.jsp"%>
<link rel="stylesheet" type="text/css" href="assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css"/>
<script type="text/javascript" src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
<style>
    .portlet > .portlet-title > .nav-tabs {
        float: left !important; 
    }
</style>
<script>
    var global = {diseases: [], isExist: false};
    $(function () {
    <c:forEach items="${requestScope.refData.diseases}" var="obj">
        global.diseases.push({id: '${obj.TW_DISEASE_ID}', title: '${obj.TITLE}'});
    </c:forEach>
        $('#tooltip').tooltip();
        $('#isContinue').click(function () {
            if ($(this).is(':checked')) {
                $('#durationExpTo').prop('disabled', true);
                $('#durationExpTo').val('');
            } else {
                $('#durationExpTo').prop('disabled', false);
            }
        });
        $('#durationEduFrom').datepicker({
            format: 'mm-yyyy',
            viewMode: "months",
            minViewMode: "months",
            autoclose: true
        });
        $('#durationEduTo').datepicker({
            format: 'mm-yyyy',
            viewMode: "months",
            autoclose: true,
            minViewMode: "months"
        });
        $('#durationExpFrom').datepicker({
            format: 'mm-yyyy',
            viewMode: "months",
            minViewMode: "months",
            autoclose: true
        });
        $('#durationExpTo').datepicker({
            format: 'mm-yyyy',
            viewMode: "months",
            minViewMode: "months",
            autoclose: true
        });
        $('#patientDiseases').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#speciality').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#service').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#residentialCountryId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#residentialCityId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#countryId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#cityId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#videoTimeDiv').hide();
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square',
            radioClass: 'iradio_square',
            increaseArea: '20%' // optional
        });
        $('input[name=video]').on('ifChecked', function (event) {
            ($(event.target).val() === 'Y' ? $('#videoTimeDiv').show() : $('#videoTimeDiv').hide());
        });
        $('#videoCallFrom').timepicker({minuteStep: 5, showMeridian: false});
        $('#videoCallTo').timepicker({minuteStep: 5, showMeridian: false});
        editRow();
        displayEducationData();
        displayExperienceData();
        displayMembershipData();
        getDiseases();
        getServices();
        getSpeciality();
    });

    function getCity() {
        //Find all characters
        $('#residentialCityId').find('option').remove();
        $.get('setup.htm?action=getCityByCountryId', {countryId: $('#residentialCountryId').val()}, function (data) {
            if (data !== null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $('<option />', {value: data[i].CITY_ID, text: data[i].CITY_NME}).appendTo($('#residentialCityId'));
                }
            } else {
                $('<option />', {value: '', text: 'No City found.'}).appendTo($('#residentialCityId'));
            }
            if ($('#editResidentialCityId').val() !== '') {
                $('#residentialCityId').val($('#editResidentialCityId').val()).trigger('change');
                $('#editResidentialCityId').val('');
            } else {
                $('#residentialCityId').trigger('change');
            }
        }, 'json');
    }

    function getEducationCity() {
        //Find all characters
        $('#cityId').find('option').remove();
        $.get('setup.htm?action=getCityByCountryId', {countryId: $('#countryId').val()}, function (data) {
            if (data !== null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $('<option />', {value: data[i].CITY_ID, text: data[i].CITY_NME}).appendTo($('#cityId'));
                }
            } else {
                $('<option />', {value: '', text: 'No City found.'}).appendTo($('#cityId'));
            }
            $('#cityId').trigger('change');
        }, 'json');
    }
    function addEducationDialog() {
        $('#addEducation').modal('show');
    }

    function addExperienceDialog() {
        $('#addExperience').modal('show');
    }

    function addMembershipDialog() {
        $('#addMembership').modal('show');

    }

    function editRow() {
        //$('#loginDetails').hide();
        $.get('setup.htm?action=getDoctorById', {doctorId: $('#doctorId').val()},
                function (obj) {
                    $('#doctorName').val(obj.DOCTOR_NME);
                    $('#title').val(obj.DOCTOR_NME + '- Introduction');
                    $('#description').val('This video is introduction of ' + obj.DOCTOR_NME);
                    $('#email').val(obj.EMAIL);
                    $('#doctorType').val(obj.DOCTOR_CATEGORY_ID);
                    $('#pmdcNo').val(obj.PMDC_NO);
                    $('#cellNo').val(obj.MOBILE_NO);
                    if (obj.PROFILE_IMAGE === '') {
                        $('#profileImage').attr('src', 'images/nophoto.png');
                    } else {
                        $('#profileImage').attr('src', 'upload/doctor/profilePic/' + obj.TW_DOCTOR_ID + '/' + obj.PROFILE_IMAGE);
                    }
                    if (obj.VISITING_CARD === '') {
                        $('#visitingCardImage').attr('src', 'images/nophoto.png');
                    } else {
                        $('#visitingCardImage').attr('src', 'upload/doctor/visitingCard/' + obj.TW_DOCTOR_ID + '/' + obj.VISITING_CARD);
                    }

                    $('#videoFrame').attr('src', obj.VIDEO_LINK);
                    $('#editResidentialCityId').val(obj.CITY_ID);
                    $('#videoCallFrom').val(obj.VIDEO_CLINIC_FROM);
                    $('#videoCallTo').val(obj.VIDEO_CLINIC_TO);
                    $('input:radio[name="video"][value="' + obj.ALLOW_VIDEO + '"]').iCheck('check');
                    $('#residentialCountryId').val(obj.COUNTRY_ID).trigger('change');
                    $('#link').val(obj.LINKEDIN_URL);
                    $('#prescriptionLang').val(obj.PRESCRIPTION_LANG);
                    $('#totalExperience').val(obj.EXPERIENCE);
                    $('#addDoctor').modal('show');
                }, 'json');
    }
    function deleteDoctorEducation(id) {
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
                    $.post('clinic.htm?action=deleteDoctorEducation', {id: id}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl("Record deleted successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            displayEducationData();
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
    function displayEducationData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="15%">').html('Degree Name'),
                $('<th class="center" width="30%">').html('College Name'),
                $('<th class="center" width="18%">').html('From'),
                $('<th class="center" width="18%">').html('To'),
                $('<th class="center" width="4%">').html('&nbsp;')
//                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getDoctorEducation', {doctorId: $('#doctorId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteDoctorEducation(\'' + list[i].TW_DOCTOR_EDUCATION_ID + '\');"></i>';
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].DEGREETITLE),
                                    $('<td>').html(list[i].TITLE),
                                    $('<td>').html(list[i].DATE_FROM),
                                    $('<td >').html(list[i].DATE_TO),
                                    $('<td align="center">').html(delHtm)
                                    ));
                        }
                        $('#displayEduDiv').html('');
                        $('#displayEduDiv').append($tbl);
                        return false;
                    } else {
                        $('#displayEduDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td class="center aligned negative" colspan="7">').html('<b>No data found.</b>')
                                ));
                        $('#displayEduDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }



    function deleteDoctorExperience(id) {
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
                    $.post('clinic.htm?action=deleteDoctorExperience', {id: id}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl("Record deleted successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            global.isExist = false;
                            displayExperienceData();
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
    function displayExperienceData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="31%">').html('Hospital Name'),
                $('<th class="center" width="30%">').html('From'),
                $('<th class="center" width="30%">').html('To'),
                $('<th class="center" width="4%">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getDoctorExperience', {doctorId: $('#doctorId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteDoctorExperience(\'' + list[i].TW_DOCTOR_EXPERIENCE_ID + '\');"></i>';

                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            if (list[i].DATE_TO === '') {
                                global.isExist = true;
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].TITLE),
                                    $('<td>').html(list[i].DATE_FROM),
                                    $('<td >').html(list[i].DATE_TO),
                                    $('<td align="center">').html(delHtm)
                                    ));
                        }
                        $('#displayExpDiv').html('');
                        $('#displayExpDiv').append($tbl);
                        return false;
                    } else {
                        $('#displayExpDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td class="center aligned negative" colspan="7">').html('<b>No data found.</b>')
                                ));
                        $('#displayExpDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }

    function deleteDoctorAssociation(id) {
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
                    $.post('clinic.htm?action=deleteDoctorAssociation', {id: id}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl("Record deleted successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            displayMembershipData();
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

    function displayMembershipData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="91%">').html('Association Name'),
                $('<th class="center" width="4%">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getDoctorAssociation', {doctorId: $('#doctorId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteDoctorAssociation(\'' + list[i].TW_DOCTOR_ASSOCIATION_ID + '\');"></i>';
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].TITLE),
                                    $('<td align="center">').html(delHtm)
                                    ));
                        }
                        $('#displayMemDiv').html('');
                        $('#displayMemDiv').append($tbl);
                        return false;
                    } else {
                        $('#displayMemDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td class="center aligned negative" colspan="7">').html('<b>No data found.</b>')
                                ));
                        $('#displayMemDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }

    function saveDoctorAttachements(attachType) {
        $('#attachType').val(attachType);
        $('#addAttachements').modal('show');
    }
    function displayDoctorAttachements(attachType) {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="10%">').html('Sr. #'),
                $('<th class="center" width="60%">').html('Description'),
                $('<th class="center" width="20%">').html('Attachment'),
                $('<th class="center" width="10%">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getDoctorActtachementsById', {doctorId: $('#doctorId').val(), attachType: attachType},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].FILE_DESC),
                                    $('<td align="center">').html('<a href="upload/doctor/doctorAttachments/' + list[i].TW_DOCTOR_ID + '/' + list[i].FILE_NME + '" target="_blank"><img src="images/attach-icon.png" width="20" height="20"></a>'),
                                    $('<td align="center">').html('<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteDoctorAttachement(\'' + list[i].TW_DOCTOR_ATTACHMENT_ID + '\',\'' + list[i].ATTACHMENT_TYP + '\');"></i>')
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
                    $('#healthCardId').val('');
                    $('.icheck').iCheck({
                        checkboxClass: 'icheckbox_square',
                        radioClass: 'iradio_square',
                        increaseArea: '20%' // optional
                    });
                    $('#viewAttachements').modal('show');
                }, 'json');
    }
    function deleteDoctorAttachement(id, attachmentType) {
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
                            displayDoctorAttachements(attachmentType);
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
    function getDiseases() {
        $.get('setup.htm?action=getDoctorSpecialityDiseasesById', {doctorId: $('#doctorId').val()},
                function (obj) {
                    if (obj !== null && obj.length > 0) {
                        var arr = [];
                        for (var i = 0; i < obj.length; i++) {
                            arr.push(obj[i].TW_DISEASE_ID);
                            $('input:checkbox[name="patientDiseases"][value="' + obj[i].TW_DISEASE_ID + '"]').iCheck('check');
                        }
                        $('#patientDiseases').val(arr);
                        $('#patientDiseases').trigger('change');
                    }
                }, 'json');
    }
    function getServices() {
        $.get('setup.htm?action=getDoctorServiceById', {doctorId: $('#doctorId').val()},
                function (obj) {
                    if (obj !== null && obj.length > 0) {
                        var arr = [];
                        for (var i = 0; i < obj.length; i++) {
                            arr.push(obj[i].TW_MEDICAL_SERVICE_ID);
                            $('input:checkbox[name="service"][value="' + obj[i].TW_MEDICAL_SERVICE_ID + '"]').iCheck('check');
                        }
                        $('#service').val(arr);
                        $('#service').trigger('change');
                    }
                }, 'json');
    }
    function getSpeciality() {
        $.get('setup.htm?action=getDoctorSpecialityById', {doctorId: $('#doctorId').val()},
                function (obj) {
                    if (obj !== null && obj.length > 0) {
                        var arr = [];
                        for (var i = 0; i < obj.length; i++) {
                            arr.push(obj[i].TW_MEDICAL_SPECIALITY_ID);
                            $('input:checkbox[name="speciality"][value="' + obj[i].TW_MEDICAL_SPECIALITY_ID + '"]').iCheck('check');
                        }
                        $('#speciality').val(arr);
                        $('#speciality').trigger('change');
                    }
                }, 'json');
    }
    function saveDoctorSpecialityDisease() {
        if ($("#patientDiseases").val() !== null && $("#patientDiseases").val().length > 0) {
            var obj = {
                doctorId: $('#doctorId').val(),
                'diseasesarr[]': $("#patientDiseases").val()
            };
            Metronic.blockUI({
            boxed: true,
            message: 'Saving...'
        });
            $.post('setup.htm?action=saveDoctorSpecialityDisease', obj, function (obj) {
                Metronic.unblockUI();
                if (obj.result === 'save_success') {
                    $.bootstrapGrowl("Doctor Data saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    return false;
                } else {
                    $.bootstrapGrowl("Error in saving Doctor.", {
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

        } else {
            $.bootstrapGrowl("Please Select Minimum one Diease.", {
                ele: 'body',
                type: 'danger',
                offset: {from: 'top', amount: 80},
                align: 'right',
                allow_dismiss: true,
                stackup_spacing: 10
            });
            return false;
        }
    }
    function saveDoctorSpeciality() {
        if ($("#speciality").val() !== null && $("#speciality").val().length > 0) {
            var obj = {
                doctorId: $('#doctorId').val(),
                'specialityarr[]': $("#speciality").val()
            };
            Metronic.blockUI({
            boxed: true,
            message: 'Saving...'
        });
            $.post('setup.htm?action=saveDoctorSpeciality', obj, function (obj) {
                Metronic.unblockUI();
                if (obj.result === 'save_success') {
                    $.bootstrapGrowl("Doctor Data saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    return false;
                } else {
                    $.bootstrapGrowl("Error in saving Doctor.", {
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
        } else {
            $.bootstrapGrowl("Please Select Minimum one Speciality.", {
                ele: 'body',
                type: 'danger',
                offset: {from: 'top', amount: 80},
                align: 'right',
                allow_dismiss: true,
                stackup_spacing: 10
            });
            return false;
        }
    }
    function saveDoctorServices() {
        if ($("#service").val() !== null && $("#service").val().length > 0) {
            var obj = {
                doctorId: $('#doctorId').val(),
                'servicesarr[]': $("#service").val()
            };
            Metronic.blockUI({
            boxed: true,
            message: 'Saving...'
        });
            $.post('setup.htm?action=saveDoctorServices', obj, function (obj) {
                Metronic.unblockUI();
                if (obj.result === 'save_success') {
                    $.bootstrapGrowl("Doctor Data saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    return false;
                } else {
                    $.bootstrapGrowl("Error in saving Doctor.", {
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
        } else {
            $.bootstrapGrowl("Please Select Minimum one Service.", {
                ele: 'body',
                type: 'danger',
                offset: {from: 'top', amount: 80},
                align: 'right',
                allow_dismiss: true,
                stackup_spacing: 10
            });
            return false;
        }
    }
</script>
<input type="hidden" id="editResidentialCityId" >
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Doctor Personal Info </h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#home">Personal Info</a></li>
                    <li><a data-toggle="tab" href="#menu1">Education</a></li>
                    <li><a data-toggle="tab" href="#menu2">Experience</a></li> 
                    <li><a data-toggle="tab" href="#menu3">Memberships</a></li> 
                    <li><a data-toggle="tab" href="#menu4">Dieases</a></li>
                    <li><a data-toggle="tab" href="#menu5">Services</a></li>
                    <li><a data-toggle="tab" href="#menu6">Speciality</a></li>
                    <li><a data-toggle="tab" href="#menu7">Video</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="tab-content">
    <div id="home" class="tab-pane fade in active">
        <div class="portlet box green">
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-10">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Full Name</label>
                                    <div>
                                        <input type="text" class="form-control" id="doctorName" placeholder="Doctor Name" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Email</label>
                                    <div>
                                        <input type="text" class="form-control" id="email" placeholder="Email" >
                                    </div>
                                </div>
                            </div>
                        </div>    
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Doctor Type</label>
                                    <select id="doctorType" class="form-control">
                                        <c:forEach items="${requestScope.refData.categories}" var="obj">
                                            <option value="${obj.TW_DOCTOR_CATEGORY_ID}">${obj.TITLE}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>PMDC No.</label>
                                    <input type="text" class="form-control" id="pmdcNo" name="pmdcNo" placeholder="PMDC No." >
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Total Experience</label>
                                    <div>
                                        <input type="text"   class="form-control" id="totalExperience" placeholder="In Years" onkeyup="onlyInteger(this);" maxlength="2" >
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <picture>
                            <img width="130" height="175" src="" id="profileImage" class="img-fluid img-thumbnail" alt="Profile Picture">
                        </picture>
                        <a style="color: #7FB0DA; margin-left: 20px;" onclick="saveDoctorAttachements('profile');" >Change Picture</a>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-5">
                        <div class="form-group">
                            <label>Linked Profile</label>
                            <input type="text" class="form-control" id="link" placeholder="Url">
                        </div>
                    </div>
                    <div class="col-md-5">
                        <div class="form-group">
                            <label>Mobile No.</label>
                            <input type="text" onkeyup="onlyInteger(this);" class="form-control" id="cellNo" placeholder="Mobile No." maxlength="11">
                        </div>
                    </div>
                    <div class="col-md-2">
                        <picture>
                            <img width="130" height="100" src="" id="visitingCardImage" class="img-fluid img-thumbnail" alt="Visting Card">
                        </picture>
                        <a style="color: #7FB0DA; margin-left: 20px;" onclick="saveDoctorAttachements('visiting');" >Visiting Card</a>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Prescription Language</label>
                            <select id="prescriptionLang" class="form-control">
                                <option value="ENGLISH">English</option>
                                <option value="URDU">Urdu</option>
                            </select>
                        </div>
                    </div>  
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>Video Consultancy</label>
                            <div class="input-group">
                                <div class="icheck-inline">
                                    <label>
                                        <input type="radio" name="video" value="Y" class="icheck"> Yes </label>
                                    <label>
                                        <input type="radio"  name="video" value="N"  class="icheck"> No</label>
                                </div>
                            </div>
                        </div>
                    </div>                         
                </div>
                <div class="row" id="videoTimeDiv">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Time From</label>
                            <div class="input-group bootstrap-timepicker timepicker input-small" id="timeToPicker">
                                <input id="videoCallFrom" type="text" class="form-control input-small" readonly="">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                            </div>
                        </div>
                    </div> 
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>To</label>
                            <div class="input-group bootstrap-timepicker timepicker input-small" id="timeToPicker">
                                <input id="videoCallTo" type="text" class="form-control input-small" readonly="">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                            </div>
                        </div>
                    </div> 
                </div>
                <div class="form-actions right">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="row">
                                <div class="col-md-offset-9 col-md-3">
                                    <button type="button" onClick="saveDoctorPersonalInfo();" class="btn green">Save</button>
                                    <button type="button" class="btn default">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="menu1" class="tab-pane fade">
        <div class="modal fade" id="addEducation">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h3 class="modal-title">Add Education</h3>
                    </div>
                    <div class="modal-body">        
                        <div class="portlet box green">
                            <div class="portlet-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>Degree</label>
                                            <select id="medicalDegreeId" class="form-control">
                                                <c:forEach items="${requestScope.refData.degree}" var="obj">
                                                    <option value="${obj.TW_MEDICAL_DEGREE_ID}">${obj.ABBREV} (${obj.TITLE})</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>                
                                </div> 
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>Institute Where From</label>
                                            <select id="medicalCollegeId" class="form-control">
                                                <c:forEach items="${requestScope.refData.medicalColleges}" var="obj">
                                                    <option value="${obj.TW_MEDICAL_COLLEGE_ID}">${obj.TITLE}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>                  
                                </div> 
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>From</label>
                                            <div class="input-group input-medium date date-picker" id="durationEduFrom">
                                                <input type="text" class="form-control" readonly="">
                                                <div class="input-group-addon"><i  class="fa fa-calendar"></i></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>To</label>
                                            <div class="input-group input-medium date date-picker" id="durationEduTo">
                                                <input type="text" class="form-control" readonly="">
                                                <div class="input-group-addon"><i  class="fa fa-calendar"></i></div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Country</label>
                                            <select id="countryId" class="form-control" onchange="getEducationCity();">
                                                <c:forEach items="${requestScope.refData.countries}" var="obj">
                                                    <option value="${obj.COUNTRY_ID}">${obj.COUNTRY_NME}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>City</label>
                                            <select id="cityId" class="form-control">
                                                <option value="">Select City</option>
                                            </select>
                                        </div>
                                    </div> 
                                </div> 
                            </div>   
                        </div>
                    </div> 
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onclick="saveEducationData()">Save</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="portlet box green">
                    <div class="portlet-body">
                        <form action="#" onsubmit="return false;" role="form" method="post">
                            <div class="row">
                                <div class="col-md-12 text-right" style="padding-top: 23px;">
                                    <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                        <button type="button" class="btn red" onclick="displayDoctorAttachements('education');"><i class="fa fa-paperclip"></i> view Attachment</button>                          
                                        <button type="button" class="btn green" onclick="saveDoctorAttachements('education');"><i class="fa fa-upload"></i> Upload Attachment</button>                          
                                        <button type="button" class="btn blue" onclick="addEducationDialog();"><i class="fa fa-plus-circle"></i> Add Education</button>                          
                                    </c:if>
                                </div>
                            </div>
                            <br/>
                            <div class="row">
                                <div class="col-md-12">
                                    <div id="displayEduDiv"></div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>                   
    </div>
    <div id="menu2" class="tab-pane fade">
        <div class="modal fade" id="addExperience">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h3 class="modal-title">Add Experience</h3>
                    </div>
                    <div class="modal-body">

                        <div class="portlet box green">
                            <div class="portlet-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>Job Title</label>
                                            <div>
                                                <input type="text" id="jobTitle" class="form-control"  placeholder="Job Title" >
                                            </div>
                                        </div>
                                    </div>
                                </div>   
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>Hospital Name</label>
                                            <select id="hospitalId" class="form-control">
                                                <c:forEach items="${requestScope.refData.hospitals}" var="obj">
                                                    <option value="${obj.TW_HOSPITAL_ID }">${obj.TITLE}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <!--                 <div class="row">
                                                             <div class="col-md-12">
                                                            <div class="form-group">
                                                                <label>Affiliated With</label>
                                                                <select id="affiliation" class="form-control">
                                                                    <option value="Punjab">Punjab University</option>
                                                                    <option value="BDS">BDS</option>                                   
                                                                </select>
                                                            </div>
                                                        </div>                           
                                                      </div>-->
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Duration Of Service From</label>
                                            <div class="input-group input-medium date date-picker" id="durationExpFrom">
                                                <input type="text" class="form-control" readonly="">
                                                <div class="input-group-addon"><i  class="fa fa-calendar"></i></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>To  </label>
                                            <div class="input-group input-medium date date-picker" >
                                                <div class="input-group-addon" id="tooltip" data-toggle="tooltip" data-placement="top" title="Click if Continue"><input class="form-check-input position-static" type="checkbox" id="isContinue" value="Y"></div>
                                                <input type="text" class="form-control" id="durationExpTo" readonly="">
                                                <div class="input-group-addon"><i  class="fa fa-calendar"></i></div>
                                            </div>
                                        </div>
                                    </div> 
                                </div>
                            </div>  
                        </div>
                    </div> 
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onclick="saveExperienceData();">Save</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="portlet box green">
                    <div class="portlet-body">
                        <form action="#" onsubmit="return false;" role="form" method="post">
                            <div class="row">
                                <div class="col-md-12 text-right" style="padding-top: 23px;">
                                    <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                        <button type="button" class="btn red" onclick="displayDoctorAttachements('experience');"><i class="fa fa-paperclip"></i> view Attachment</button>                          
                                        <button type="button" class="btn green" onclick="saveDoctorAttachements('experience');"><i class="fa fa-upload"></i> Upload Attachment</button>                          
                                        <button type="button" class="btn blue" onclick="addExperienceDialog();"><i class="fa fa-plus-circle"></i> Add Experience</button>                           
                                    </c:if>
                                </div>
                            </div>
                            <br/>
                            <div class="row">
                                <div class="col-md-12">
                                    <div id="displayExpDiv"></div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>                   
    </div>
    <div id="menu3" class="tab-pane fade">
        <div class="modal fade" id="addMembership">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h3 class="modal-title">Add Memberships</h3>
                    </div>
                    <div class="modal-body">               
                        <div class="portlet box green">
                            <div class="portlet-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label>Associations</label>
                                            <select id="associationId" class="form-control">
                                                <c:forEach items="${requestScope.refData.associations}" var="obj">
                                                    <option value="${obj.TW_ASSOCIATION_ID}">${obj.TITLE}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>                        
                                </div>   
                            </div>  
                        </div>
                    </div> 
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onclick="saveAssociationData();">Save</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="portlet box green">
                    <div class="portlet-body">
                        <form action="#" onsubmit="return false;" role="form" method="post">
                            <div class="row">
                                <div class="col-md-12 text-right" style="padding-top: 23px;">
                                    <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                        <button type="button" class="btn red" onclick="displayDoctorAttachements('association');"><i class="fa fa-paperclip"></i> view Attachment</button>                          
                                        <button type="button" class="btn green" onclick="saveDoctorAttachements('association');"><i class="fa fa-upload"></i> Upload Attachment</button>
                                        <button type="button" class="btn blue" onclick="addMembershipDialog();"><i class="fa fa-plus-circle"></i> Add Membership</button>
                                    </c:if>
                                </div>
                            </div>
                            <br/>
                            <div class="row">
                                <div class="col-md-12">
                                    <div id="displayMemDiv"></div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>                   
    </div>
    <div id="menu4" class="tab-pane fade">
        <div class="row">
            <div class="col-md-12">
                <div class="portlet box green">
                    <div class="portlet-body">
                        <form action="#" onsubmit="return false;" role="form" method="post">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group" id="diseases">
                                        <div class="form-group">
                                            <label>Specialist For Diseases</label>
                                            <select id="patientDiseases"  class=" select2_category form-control" name="patientDiseases[]" multiple="multiple">
                                                <option value="">Select Recommended diseases</option>
                                                <c:forEach items="${requestScope.refData.diseases}" var="obj">
                                                    <option value="${obj.TW_DISEASE_ID}">${obj.TITLE}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <button type="button" class="btn blue" onclick="saveDoctorSpecialityDisease();"> Save</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>                   
    </div>
    <div id="menu5" class="tab-pane fade">
        <div class="row">
            <div class="col-md-12">
                <div class="portlet box green">
                    <div class="portlet-body">
                        <form action="#" onsubmit="return false;" role="form" method="post">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="form-group">
                                            <label>Provide Services</label>
                                            <select id="service"  class=" select2_category form-control" name="service[]" multiple="multiple">
                                                <option value="">Select Services</option>
                                                <c:forEach items="${requestScope.refData.services}" var="obj">
                                                    <option value="${obj.TW_MEDICAL_SERVICE_ID}">${obj.TITLE}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <button type="button" class="btn blue" onclick="saveDoctorServices();"> Save</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>                   
    </div>
    <div id="menu6" class="tab-pane fade">
        <div class="row">
            <div class="col-md-12">
                <div class="portlet box green">
                    <div class="portlet-body">
                        <form action="#" onsubmit="return false;" role="form" method="post">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="form-group">
                                            <label>Speciality</label>
                                            <select id="speciality" class=" select2_category form-control" name="speciality[]" multiple="multiple">
                                                <option value="">Select Speciality</option>
                                                <c:forEach items="${requestScope.refData.types}" var="obj">
                                                    <option value="${obj.TW_MEDICAL_SPECIALITY_ID}">${obj.TITLE}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <button type="button" class="btn blue" onclick="saveDoctorSpeciality();"> Save</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>                   
    </div>
    <div id="menu7" class="tab-pane fade">
        <div class="row">
            <div class="col-md-12">
                <div class="portlet box green">
                    <div class="portlet-body">
                        <div class="row">
                            <div class="col-md-10">
                                <div class="form-group">
                                    <label>Video Link</label>
                                    <input type="text" class="form-control" id="videoLink" placeholder="https://www.youtube.com/channel/UCv8kAznQrUXmHVTM5SRM03A" name="videoLink" />
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <br>
                                    <button style="margin-top: 6px;" type="button" class="btn btn-primary" onclick="saveVideoLink();" ><i class="fa fa-save"></i> Save</button>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <iframe src="" id="videoFrame" width="560" height="315" frameborder="0" allowfullscreen></iframe>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>                   
    </div>
</div>
<div class="modal fade" id="addAttachements">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Doctor's Attachment</h3>

            </div>
            <div class="modal-body">
                <form action="#" id="doctorAttachment" role="form" method="post"  >
                    <input type="hidden" name="doctorId" id="doctorId" value="${requestScope.refData.doctorId}">
                    <input type="hidden" name="attachType" id="attachType" value="">
                    <div class="portlet box green">
                        <div class="portlet-title">
                            <div class="caption">
                                Upload Attachment
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group" id="servicesOffered">
                                        <div class="row">
                                            <div class="col-md-4">
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
<div class="modal fade" id="viewAttachements">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Attachment (s)</h3>

            </div>
            <div class="modal-body">
                <form action="#" id="doctorAttachment" role="form" method="post" >
                    <div class="row">

                    </div> 
                    <div class="row">
                        <div class="col-md-12">
                            <div id="dvTable"></div>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>  

<script>
    function saveAttachment() {
        var url = "";
        if ($('#attachType').val() === 'profile') {
            url = "clinic.htm?action=saveDoctorProfile";
        } else if ($('#attachType').val() === 'visiting') {
            url = "clinic.htm?action=uploadDoctorVistingCard";
        } else {
            url = "clinic.htm?action=saveDoctorAttachment";
        }
        var data = new FormData(document.getElementById('doctorAttachment'));
        $.ajax({
            url: url,
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (data) {
            if (data) {
                if (data.result === 'save_success') {
                    $.bootstrapGrowl("Attachment Upload successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    ($('#attachType').val() === 'profile' ? editRow() : '');
                    $('#addAttachements').modal('hide');

                } else {
                    $.bootstrapGrowl("Error in Attachment Uploading.", {
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
    function saveEducationData() {
        if ($.trim($('#medicalDegreeId').val()) === '') {
            $('#medicalDegreeId').notify('Doctor Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#medicalDegreeId').focus();
            return false;
        }
        if ($.trim($('#countryId').val()) === '') {
            $('#countryId').notify('Contact is Required Field', 'error', {autoHideDelay: 15000});
            $('#countryId').focus();
            return false;
        }
        if ($.trim($('#durationEduFrom input').val()) === '') {
            $('#durationEduFrom input').notify('From Date is Required Field', 'error', {autoHideDelay: 15000});
            $('#durationEduFrom input').focus();
            return false;
        }
        if ($.trim($('#durationEduTo input').val()) === '') {
            $('#durationEduTo input').notify('To Date is Required Field', 'error', {autoHideDelay: 15000});
            $('#durationEduTo input').focus();
            return false;
        }
        if ($('#cityId').val() === '') {
            $('#cityId').notify('City is Required Field', 'error', {autoHideDelay: 15000});
            $('#cityId').focus();
            return false;
        }

        var obj = {
            doctorId: $('#doctorId').val(),
            medicalDegreeId: $('#medicalDegreeId').val(),
            medicalCollegeId: $('#medicalCollegeId').val(),
            durationEduFrom: $('#durationEduFrom input').val(),
            durationEduTo: $('#durationEduTo input').val(),
            doctorAddress: $('#doctorAddress').val(),
            countryId: $('#countryId').val(),
            cityId: $('#cityId').val()
        };
        Metronic.blockUI({
            boxed: true,
            message: 'Saving...'
        });
        $.post('clinic.htm?action=saveDoctorEducation', obj, function (obj) {
            Metronic.unblockUI();
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Doctor Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('#durationEduFrom input').val('');
                $('#durationEduTo input').val('');
                //                $('#addEducation').modal('hide');
                displayEducationData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Doctor. Please try again later.", {
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
    function saveExperienceData() {

        if ($.trim($('#hospitalId').val()) === '') {
            $('#hospitalId').notify('Hospital is Required Field', 'error', {autoHideDelay: 15000});
            $('#hospitalId').focus();
            return false;
        }
        if ($.trim($('#jobTitle').val()) === '') {
            $('#jobTitle').notify('Job Title is Required Field', 'error', {autoHideDelay: 15000});
            $('#jobTitle').focus();
            return false;
        }
        if ($.trim($('#durationExpFrom input').val()) === '') {
            $('#durationExpFrom input').notify('Experience From is Required Field', 'error', {autoHideDelay: 15000});
            $('#durationExpFrom input').focus();
            return false;
        }
        if (!$('#isContinue').is(':checked')) {
            if ($.trim($('#durationExpTo').val()) === '') {
                $('#durationExpTo').notify('Experience To is Required Field', 'error', {autoHideDelay: 15000});
                $('#durationExpTo').focus();
                return false;
            }
        } else {
            if (global.isExist) {
                $('#durationExpTo').notify('Already Entered a Continues Job.', 'error', {autoHideDelay: 15000});
                $('#durationExpTo').focus();
                return false;
            }
        }

        var obj = {
            doctorId: $('#doctorId').val(),
            jobTitle: $('#jobTitle').val(),
            hospitalId: $('#hospitalId').val(),
            durationExpFrom: $('#durationExpFrom  input').val(),
            durationExpTo: $('#durationExpTo').val()
        };
        Metronic.blockUI({
            boxed: true,
            message: 'Saving...'
        });
        $.post('clinic.htm?action=saveDoctorExperience', obj, function (obj) {
            Metronic.unblockUI();
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Doctor Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('#jobTitle').val('');
                $('#durationExpFrom  input').val('');
                $('#durationExpTo').val('');
                displayExperienceData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Doctor. Please try again later.", {
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





    function saveAssociationData() {

        //        if ($.trim($('#medicalDegreeId').val()) === '') {
        //            $('#medicalDegreeId').notify('Doctor Name is Required Field', 'error', {autoHideDelay: 15000});
        //            $('#medicalDegreeId').focus();
        //            return false;
        //        }
        if ($.trim($('#associationId').val()) === '') {
            $('#associationId').notify('Job Title is Required Field', 'error', {autoHideDelay: 15000});
            $('#associationId').focus();
            return false;
        }

        var obj = {
            doctorId: $('#doctorId').val(),
            associationId: $('#associationId').val()
        };
        Metronic.blockUI({
            boxed: true,
            message: 'Saving...'
        });
        $.post('clinic.htm?action=saveDoctorAssociation', obj, function (obj) {
            Metronic.unblockUI();
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Doctor Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('#addMembership').modal('hide');
                displayMembershipData();
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
    function saveVideoLink() {
        if ($.trim($('#videoLink').val()) === '') {
            $('#videoLink').notify('Video Link is Required Field', 'error', {autoHideDelay: 15000});
            $('#videoLink').focus();
            return false;
        }

        var obj = {
            doctorId: $('#doctorId').val(),
            videoLink: $('#videoLink').val()
        };
        Metronic.blockUI({
            boxed: true,
            message: 'Saving...'
        });
        $.post('setup.htm?action=saveVideoLink', obj, function (obj) {
            Metronic.unblockUI();
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Video Link saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                editRow();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Video Link.", {
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
    function saveDoctorPersonalInfo() {
        if ($.trim($('#doctorName').val()) === '') {
            $('#doctorName').notify('Doctor Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#doctorName').focus();
            return false;
        }
        if ($.trim($('#cellNo').val()) === '') {
            $('#cellNo').notify('Cell No. is Required Field', 'error', {autoHideDelay: 15000});
            $('#cellNo').focus();
            return false;
        }
        var obj = {
            doctorId: $('#doctorId').val(),
            doctorName: $('#doctorName').val(),
            doctorType: $('#doctorType').val(),
            cellNo: $('#cellNo').val(),
            doctorEmail: $('#email').val(),
            link: $('#link').val(),
            servicesAvail: $('input[name=video]:checked').val(),
            totalExperience: $('#totalExperience').val(),
            prescriptionLang: $('#prescriptionLang').val(),
            pmdcNo: $('#pmdcNo').val(),
            videoTimeFrom: $('#videoCallFrom').val(),
            videoTimeTo: $('#videoCallTo').val()
        };
        Metronic.blockUI({
            boxed: true,
            message: 'Saving...'
        });
        $.post('setup.htm?action=saveDoctor', obj, function (obj) {
            Metronic.unblockUI();
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Doctor Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
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
    jQuery.fn.getCheckboxVal = function () {
        var vals = [];
        var i = 0;
        this.each(function () {
            vals[i++] = jQuery(this).val();
        });
        return vals;
    };
    function getDoctorEducationalDetails() {
        var obj = {
            degree: $('#degree').val(),
            institute: $('#institute').val(),
            fileInput: $('#fileInput').val()};
        return false;
    }
    function getDoctorExperienceDetails() {
        var obj = {
            institute: $('#institute').val(),
            durationFrom: $('#durationFrom').val(),
            durationTo: $('#durationTo').val(),
            serving: $("input[name='serving']:checked").val()
        };
        return false;
    }
</script>
<%@include file="../footer.jsp"%>