<%-- 
    Document   : addPrescription
    Created on : Oct 6, 2017, 12:25:54 PM
    Author     : farazahmad
--%>
<%@include file="../header.jsp"%>
<link rel="stylesheet" type="text/css" href="assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css"/>
<script type="text/javascript" src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
<script type="text/javascript" src="js/prescription.js?id=3.5"></script>
<script>
    var medicines = [];
    var global = {
        masterId: [],
        detail: []
    };
    $(function () {
//        $('#vaccinationDatePicker').datepicker({
//            format: 'dd-mm-yyyy',
//            autoclose: true,
//            startDate: new Date()
//        });
        $('#prescriptionDate').html(moment().format("DD-MMM-YYYY"));
        //$("#vaccinationDatePicker").datepicker("update", new Date());
        $('#patientId').select2({
            placeholder: "Select a patient",
            allowClear: true
        });
        $('.select2_category').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#appointmentDiv').hide();
        $('#addApointment').click(function () {
            if ($(this).is(':checked')) {
                $('#appointmentDiv').show();
                getAppointmentDates();
            } else {
                $('#appointmentDiv').hide();
            }
        });
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square',
            radioClass: 'iradio_square'
        });
        $('.icheck').iCheck('disable');
        $('#diagnosticsPanel').hide();
        $('#panelPatient').hide();
        $('#balanceInfo').hide();


        $('#patientId').on('change', function (e) {
            var selected = $(this).find('option:selected');
            if (selected.attr('companyId').length && selected.attr('companyId') !== '') {
                $('#panelPatient').show();
                $('#diagnosticsPanel').show();
            } else {
                $('#panelPatient').hide();
            }
            //(selected.attr('companyId') !== '' ? $('#panelPatient').show() : $('#panelPatient').hide());
            if (selected.attr('balance').length && selected.attr('balance') !== '' && selected.attr('balance') !== '0') {
                $('#balanceInfo').show();
                $('#balanceInfo').html('Balance: PKR ' + selected.attr('balance'));
                $('#panelPatient').show();
                $('#diagnosticsPanel').show();
            } else {
                $('#balanceInfo').hide();
            }
            if ($('#patientId').val() !== '') {
                getDiseases();
                //getPatientReading();
                displayVaccination();
                getNextPrescriptionNumber();
                getPrescription();
            }
        });
        getAppointedPatientsForDoctor();
        //displayExaminationQuestions();
        //$('#saveExaminationBtn').hide();

        $('#addMedicineBtn').click(function () {
            $('#addMedicineDialog').modal('show');
        });

        $('#addMedicineInstBtn').click(function () {
            $('#addMedicineInstructionDialog').modal('show');
        });
        Diagnostics.getDiagnostics();
        //getNextPrescriptionNumber();
    });
    function getDetails(param, id, title) {
        $(param).parent().parent().parent().parent().find('tr').find('.selectedTile').removeClass('selectedTile');
        $(param).addClass('selectedTile');
        $('#questionCategory').val(id);
        $('#examinationTitleDiv').html('<h2>' + title + '</h2>');
        $('#examQuestionsDiv').html('');
        displayExaminationQuestions();
    }
</script>
<style>
    .tiles .tile{
        height: 100px !important;
        width: 100px !important;
    }
    .tiles .tile .tile-object > .name{
        font-size: 11px !important;
        color: #DF0101 !important;
    }
    .selectedTile{
        background-color: gray;
    }
</style>

<div class="modal fade" id="viewPatientModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Patient Info</h3>
            </div>
            <div class="modal-body">
                <div class="portlet box green">
                    <div class="portlet-title tabbable-line">
                        <div class="caption">
                            Attachments 
                        </div>
                    </div>
                    <div class="portlet-body">
                        <table class="table table-striped table-condensed" id="displayPatientAttachTbl">
                            <thead>
                                <tr>
                                    <th>Sr#</th>
                                    <th>File</th>
                                    <th>Description</th>
                                    <th>&nbsp;</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="attachModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Attach Report</h3>
            </div>
            <div class="modal-body">
                <div id="reportDiv">

                </div>
                <div class="portlet box green">
                    <div class="portlet-title tabbable-line">
                        <div class="caption">
                            Attach Report
                        </div>
                    </div>
                    <div class="portlet-body">
                        <form action="#" id="reportAttachmentFrom" >
                            <input type="hidden" name="attachmentType" value="PRESCRIPTION" />
                            <div class="row">
                                <div class="col-md-6 form-group">
                                    <label>Select File</label>
                                    <input name="report" id="patientAttachmentReport" class="form-control" type="file">
                                </div>
                                <div class="col-md-6 form-group">
                                    <label>Description</label>
                                    <input class="form-control" name="reportDesc" id="reportDesc">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 form-group" style="text-align: center;">
                                    <button type="button" class="btn btn-secondary" onclick="saveReports();"><i class="fa fa-upload"></i> Upload</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="addMedicineDialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Medicine</h3>
            </div>
            <div class="modal-body">
                <form method="post" action="#" onsubmit="return false">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Medicine*</label>
                                <select  class="form-control select2_category" id="medicineId">
                                    <option value="">Select Medicine</option>
                                    <c:forEach items="${requestScope.refData.medicines}" var="obj">
                                        <option value="${obj.TW_MEDICINE_ID}">${obj.PRODUCT_NME}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Frequency</label>
                                <select class="select2_category form-control" id="medicineFrequency">
                                    <c:forEach items="${requestScope.refData.frequencies}" var="obj">
                                        <option value="${obj.TW_FREQUENCY_ID}">${obj.TITLE}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Days</label>
                                <input type="text" id="medicineDays" class="form-control input-sm" value="1" onkeyup="onlyInteger(this);">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Quantity</label>
                                <input type="text" id="medicineQty" class="form-control input-sm" value="1" onkeyup="onlyInteger(this);">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Instructions*</label>
                                <select class="form-control" id="medicineInstructions">
                                    <c:forEach items="${requestScope.refData.doseUsage}" var="obj">
                                        <option value="${obj.TW_DOSE_USAGE_ID}" langType="${requestScope.refData.prescriptionLang}"><c:choose>
                                                <c:when test="${requestScope.refData.prescriptionLang == 'ENGLISH'}">
                                                    ${obj.TITLE} 
                                                </c:when>
                                                <c:otherwise>
                                                    ${obj.TITLE_URDU} 
                                                </c:otherwise>
                                            </c:choose></option>
                                        </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="addMedicineRow();">Add</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="addMedicineInstructionDialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Instruction</h3>
            </div>
            <div class="modal-body">
                <form method="post" action="#" onsubmit="return false">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Instructions*</label>
                                <select class="form-control" id="rowInstruction">
                                    <c:forEach items="${requestScope.refData.doseUsage}" var="obj">
                                        <option value="${obj.TW_DOSE_USAGE_ID}"><c:choose>
                                                <c:when test="${requestScope.refData.prescriptionLang == 'ENGLISH'}">
                                                    ${obj.TITLE} 
                                                </c:when>
                                                <c:otherwise>
                                                    ${obj.TITLE_URDU} 
                                                </c:otherwise>
                                            </c:choose></option>
                                        </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="addInstructionsRow();">Add</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <form action="#" role="form" method="post" id="prescForm"></form>
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Patient Prescription
                </div>
            </div>
            <div class="portlet-body">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="bold">Prescription No.</label>
                            <span class="form-control-static bold" id="displayPrescNoDiv">
                                ???
                            </span>
                            <input type="hidden" id="prescriptionNo" value="1">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="bold">Prescription Date:</label>
                            <span class="form-control-static bold" id="prescriptionDate">
                                01-01-2018
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-9">
                        <div class="form-group">
                            <label class="control-label">Patient Name</label>
                            <select class=" form-control" name="patientId" id="patientId" tabindex="1">
                                <option value="">Select Patient</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3" style="padding-top: 28px;">
                        <input class="form-control" id="showAllPatients" type="checkbox" onclick="getAppointedPatientsForDoctor();" >
                        <label>Show all Patients</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="portlet box red" id="diagnosticsPanel">
                            <div class="portlet-title">
                                <div class="caption">
                                    Primary Diagnostics
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div  id="diseaseDiv">None</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="alert alert-info" id="panelPatient">
                            <i class="fa fa-check-circle"></i> Panel Patient
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="alert alert-warning" id="balanceInfo" role="alert">
                            Balance: 0
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <div class="portlet box">

            <div class="portlet-body">
                <div class="tabbable-custom ">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#tab_1_1" data-toggle="tab">
                                Medication </a>
                        </li>
                        <li>
                            <a href="#tab_1_5" data-toggle="tab">
                                Diagnostics</a>
                        </li>
                        <li>
                            <a href="#tab_1_2" data-toggle="tab">
                                Examination</a>
                        </li>
                        <li>
                            <a href="#tab_1_3" data-toggle="tab">
                                Vaccination</a>
                        </li>
                        <li>
                            <a href="#tab_1_4" data-toggle="tab">
                                Patient History</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane fade active in" id="tab_1_1">
                            <div class="portlet box red">
                                <div class="portlet-title tabbable-line">
                                    <div class="caption">
                                        Medicine
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form action="#" role="form" method="post" onsubmit="return false;">
                                        <table class="table" id="medicineTable">
                                            <thead>
                                                <tr>
                                                    <th width="30%">
                                                        Medicine
                                                    </th>
                                                    <th width="5%">
                                                        Days
                                                    </th>
                                                    <th width="5%">
                                                        Qty
                                                    </th>
                                                    <th width="20%">
                                                        Frequency
                                                    </th>
                                                    <th width="40%">
                                                        Instructions
                                                    </th>
                                                    <th width="5%">&nbsp;</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <select  class="form-control select2_category input-sm" name="medicineId">
                                                            <option value="">Select Medicine</option>
                                                            <c:forEach items="${requestScope.refData.medicines}" var="obj">
                                                                <option value="${obj.TW_MEDICINE_ID}">${obj.PRODUCT_NME}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <input type="text" name="medicineDays" class="form-control input-sm" value="7" onkeyup="onlyInteger(this);">
                                                    </td>
                                                    <td>
                                                        <select  class="form-control select2_category input-sm" name="medicineQty">
                                                            <c:forEach items="${requestScope.refData.doseUsages}" var="obj">
                                                                <option value="${obj.TW_MEDICINE_USAGE_ID}">${obj.TITLE}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <select class="select2_category form-control input-sm" name="medicineFrequency">
                                                            <c:forEach items="${requestScope.refData.frequencies}" var="obj">
                                                                <option value="${obj.TW_FREQUENCY_ID}">${obj.TITLE}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <select class="form-control select2_category input-sm" name="medicineInstructions">
                                                            <c:forEach items="${requestScope.refData.doseUsage}" var="obj">
                                                                <option value="${obj.TW_DOSE_USAGE_ID}"><c:choose>
                                                                        <c:when test="${requestScope.refData.prescriptionLang == 'ENGLISH'}">
                                                                            ${obj.TITLE} 
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            ${obj.TITLE_URDU} 
                                                                        </c:otherwise>
                                                                    </c:choose></option>
                                                                </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <button type="button" class="btn btn-sm green" onclick="addRow(this);" >
                                                            <i class="fa fa-plus-circle" aria-hidden="true"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                    </form>
                                </div>
                            </div>
                            <div class="portlet box blue">
                                <div class="portlet-title tabbable-line">
                                    <div class="caption">
                                        Lab Test
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form action="#" role="form" method="post">
                                        <table class="table" id="testTable">
                                            <thead>
                                                <tr>
                                                    <th width="20%">
                                                        Test Name
                                                    </th>
                                                    <th width="20%">
                                                        Recommended Lab
                                                    </th>
                                                    <th width="35%">
                                                        Collection Center
                                                    </th>
                                                    <th width="20%">
                                                        Occurrence
                                                    </th>
                                                    <th width="5%">&nbsp;</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <select  class="form-control select2_category" name="labTestId">
                                                            <option value="">No Lab Test</option>
                                                            <c:forEach items="${requestScope.refData.labTests}" var="obj">
                                                                <option value="${obj.TW_LAB_TEST_ID}">${obj.TITLE}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <select  class="form-control select2_category" name="labId" onchange="getCollectionCenter(this);">
                                                            <option value="">Please select lab</option>
                                                            <c:forEach items="${requestScope.refData.labs}" var="obj">
                                                                <option value="${obj.TW_LAB_MASTER_ID}">${obj.LAB_NME}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <!--                                    <select  class="form-control select2_category" name="labId">
                                                                                                <option value="">Please select lab</option>
                                                        <c:forEach items="${requestScope.refData.medicalLabs}" var="obj">
                                                            <option value="${obj.TW_LABORATORY_ID}">${obj.LABORATORY_NME}</option>
                                                        </c:forEach>
                                                    </select>-->
                                                    </td>
                                                    <td>
                                                        <select  class="form-control select2_category collectionCenterId" name="collectionCenterId">
                                                            <option value="">Please select center</option>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <input class="form-control input-sm" name="occurrence" class="occurrence" />
                                                    </td>
                                                    <td align="right">
                                                        <button type="button" class="btn btn-sm green" onclick="addLabTestRow(this);" >
                                                            <i class="fa fa-plus-circle" aria-hidden="true"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </div>
                            </div>

                            <div class="portlet box yellow">
                                <div class="portlet-title tabbable-line">
                                    <div class="caption">
                                        Remarks
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <label >Remarks</label>
                                            <textarea class="form-control" id="comments" name="comments" rows="3" cols="30"></textarea>
                                        </div>
                                    </div>
                                    <br/>
                                    <br/>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <input class="form-control" id="addApointment" type="checkbox" >
                                            <label>Future Appointment</label>
                                        </div>
                                        <div id="appointmentDiv">
                                            <div class="col-md-3">
                                                <div class="form-group">
                                                    <label>Date</label>
                                                    <select  class="form-control select2_category" onchange="getAppointedTime();" id="dates" name="dates">
                                                        <option value="">Please select Date</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-3">
                                                <div class="form-group">
                                                    <label>Time</label>
                                                    <select  class="form-control select2_category" id="time" name="time">
                                                        <option value="">Please select Time</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <button type="button" onclick="saveData();" class="btn blue"><i class="fa fa-floppy-o"></i> Save & Print</button>
                                </div>
                            </div> 
                        </div>
                        <div class="tab-pane fade" id="tab_1_5">
                            <div class="portlet box green">
                                <div class="portlet-title tabbable-line">
                                    <div class="caption">
                                        Diagnostics
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div id="diagnosticsDiv"></div>
                                    <br/><br/><br/>
                                    <button type="button" class="btn blue" onclick="Diagnostics.saveDiagnostics();">&nbsp;<i class="fa fa-save"></i> Save Diagnostics</button>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="tab_1_2">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="portlet box blue">

                                        <div class="portlet-body">
                                            <input type="hidden" id="questionCategory" value="">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <table width="100%" class="table table-condensed" id="examinationQuestionsTbl" >
                                                        <thead>
                                                            <tr>
                                                                <th width="20%">Category</th>
                                                                <th width="20%">Question</th>
                                                                <th width="35%">Answers</th>
                                                                <th width="20%">Remarks</th>
                                                                <th width="5%">&nbsp;</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                                <td>
                                                                    <select class="form-control select2_category input-sm" name="examCategory" onchange="Examination.getQuestions(this);">
                                                                        <option value="">Select</option>
                                                                        <c:forEach items="${requestScope.refData.categories}" var="obj" varStatus="i">
                                                                            <option value="${obj.TW_QUESTION_CATEGORY_ID}">${obj.CATEGORY_NME}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <select class="form-control select2_category input-sm" onchange="Examination.getAnswers(this);" >
                                                                        <option value="">Select</option>
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <select class="form-control select2_category input-sm">
                                                                        <option value="">Select</option>
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <input type="text" class="form-control input-sm">
                                                                </td>
                                                                <td>
                                                                    <button type="button" class="btn btn-sm green" onclick="addExaminationRow(this);" >
                                                                        <i class="fa fa-plus-circle" aria-hidden="true"></i>
                                                                    </button>
                                                                </td>

                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                            <br/>
                                            <br/>
                                            <button type="button" id="saveExaminationBtn" class="btn blue" onclick="Examination.saveExamination();"><i class="fa fa-list-alt" aria-hidden="true"></i>&nbsp;Save Examination</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="tab_1_3">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="portlet box blue">
                                        <div class="portlet-title">
                                            <div class="caption">
                                                <i class="fa fa-syringe" aria-hidden="true"></i>&nbsp;Add Vaccination
                                            </div>
                                        </div>
                                        <div class="portlet-body">
                                            <div class="row">         
                                                <div class="col-md-12">
                                                    <label>Vaccination Category</label>
                                                    <select class="form-control select2_category input-sm" name="vaccinationCategory" onchange="Vaccination.getVaccinations(this);">
                                                        <option value="">Select Category</option>
                                                        <c:forEach items="${requestScope.refData.vaccCategories}" var="obj" varStatus="i">
                                                            <option value="${obj.TW_VACCINATION_CATEGORY_ID}">${obj.CATEGORY_NME}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <br/><br/>
                                            <div class="row">         
                                                <div class="col-md-12">
                                                    <div id="vaccinationDiv"></div>
                                                </div>
                                            </div>
                                            <br/><br/><br/>
                                            <button type="button" class="btn blue" onclick="Vaccination.saveVaccination();">&nbsp;<i class="fa fa-save"></i> Save Vaccination</button>
                                        </div>
                                    </div>
                                    <div class="portlet box green">
                                        <div class="portlet-title">
                                            <div class="caption">
                                                <i class="fa fa-syringe" aria-hidden="true"></i>&nbsp;Vaccination History
                                            </div>
                                        </div>
                                        <div class="portlet-body">
                                            <div id="vaccinationDiv">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="tab_1_4">
                            <div class="portlet box green">
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label>Patient Name</label>
                                                <input type="text" class="form-control input-sm" id="patientName" placeholder="Patient Name" readonly="" >
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label>Contact No.</label>
                                                <div>
                                                    <input type="text" class="form-control input-sm" id="contactNo" placeholder="Contact No." onkeyup="onlyInteger(this);" maxlength="11" readonly="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4" style="margin-top: 23px;text-align: right;">
                                            <button id="addAttachmentBtn" class="btn btn-info btn-sm" onclick="attachReport()"><i class="fa fa-paperclip" aria-hidden="true"></i>&nbsp;Attachment</button>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>City</label>
                                                <div>
                                                    <input type="text" class="form-control input-sm" id="cityId" readonly="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Referred By</label>
                                                <input type="text" class="form-control input-sm"id="referredBy" readonly="" >
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>Address</label>
                                                <input type="text" class="form-control input-sm" id="address" readonly="" >
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="portlet box green">
                                <div class="portlet-title">
                                    <div class="caption">InTake Form Questions</div>
                                </div>
                                <div class="portlet-body">
                                    <div id="intakeFormQuestions"></div>
                                </div>   
                            </div>
                            <div class="portlet box blue">
                                <div class="portlet-title tabbable-line">
                                    <div class="caption">
                                        Prescription History 
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group" id="prescriptionDiv">&nbsp;</div>
                                        </div>
                                    </div> 
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="viewMedicine">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Medicine(s)</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12" id="medicineDiv">

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="timeFrom" value="${requestScope.refData.clinicTime.TIME_FROM}">
<input type="hidden" id="timeTo" value="${requestScope.refData.clinicTime.TIME_TO}">
<input type="hidden" id="doctorId" value="${requestScope.refData.doctorId}">
<%@include file="../footer.jsp"%>