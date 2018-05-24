<%-- 
    Document   : prescriptionListing
    Created on : Nov 1, 2017, 6:42:09 PM
    Author     : farazahmad
--%>
<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('#fromDatePicker').datepicker({
            format: 'dd-M-yyyy',
            autoclose: true
        });
        $('#toDatePicker').datepicker({
            format: 'dd-M-yyyy',
            autoclose: true
        });
        $('#fromDatePicker').datepicker('setDate', new Date());
        $('#toDatePicker').datepicker('setDate', new Date());
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Patient Name'),
                $('<th class="center" width="40%">').html('Remarks'),
                $('<th class="center" width="10%">').html('Date'),
                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getPrescriptionListing', {patientId:$('#patientId').val(),dateFrom: $('#fromDate').val(),
            dateTo: $('#toDate').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].PATIENT_NME),
                                    $('<td>').html(list[i].REMARKS),
                                    $('<td >').html(list[i].PREPARED_DTE),
                                    $('<td align="center">').html('<i class="fa fa-print" aria-hidden="true" title="Click to Print" style="cursor: pointer;" onclick="printPrescription(\'' + list[i].TW_PRESCRIPTION_MASTER_ID + '\');"></i>')
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
    function printPrescription(masterId) {
        document.getElementById("prescForm").action = 'report.htm?action=reportPrescriptionById&prescriptionId=' + masterId;
        document.getElementById("prescForm").target = '_blank';
        document.getElementById("prescForm").submit();
    }
</script>
<div class="page-head">
    <div class="page-title">
        <h1>Prescription Listing</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Searching Criteria
                </div>
            </div>
            <div class="portlet-body">
                <form action="#" onsubmit="return false;" role="form" method="post" id="prescForm">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Patient</label>
                                    <select id="patientId" class="form-control select2me" data-placeholder="Select">
                                        <c:forEach items="${requestScope.refData.doctors}" var="obj">
                                            <option value="${obj.TW_PATIENT_ID}">${obj.PATIENT_NME}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Date From</label>
                                    <div class="input-group date" id="fromDatePicker">
                                        <input type="text" class="form-control" readonly="" id="fromDate" >
                                        <div class="input-group-addon">
                                            <span class="fa fa-calendar"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Date To</label>
                                    <div class="input-group date" id="toDatePicker">
                                        <input type="text" class="form-control" readonly="" id="toDate" >
                                        <div class="input-group-addon">
                                            <span class="fa fa-calendar"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2" style="padding-top: 22px;">
                                <button id="saveBtn" onclick="displayData();" class="btn blue"><i class="fa fa-search"></i> Search</button>
                            </div>
                        </div>


                        <!-- END FORM-->
                        <div class="row">
                            <div class="col-md-12">
                                <div id="displayDiv">

                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>

<%@include file="../footer.jsp"%>
