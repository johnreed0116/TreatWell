<%-- 
    Document   : addPatient
    Created on : Oct 5, 2017, 3:54:15 PM
    Author     : farazahmad
--%>
<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('.date-picker').datepicker({
            format: 'dd-mm-yyyy',
            autoclose: true
        });
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Patient Name'),
                $('<th class="center" width="20%">').html('Contact No'),
                $('<th class="center" width="20%">').html('Age'),
                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getPatient', {patientName: $('#patientName').val(), contactNo: $('#contactNo').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].PATIENT_NME),
                                    $('<td>').html(list[i].MOBILE_NO),
                                    $('<td>').html(list[i].AGE),
                                    $('<td align="center">').html('<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_PATIENT_ID + '\',\'' + list[i].TW_PATIENT_ID + '\');"></i>'),
                                    $('<td  align="center">').html('<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_PATIENT_ID + '\');"></i>')
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
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Patient Listing</h1>
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
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-7">
                                <div class="form-group">
                                    <label>Patient Name</label>
                                    <div>
                                        <input type="text" class="form-control" id="patientName" placeholder="Patient Name" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Contact No.</label>
                                    <div>
                                        <input type="text" class="form-control" id="contactNo" placeholder="Contact No." >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>&nbsp;</label>
                                    <button id="saveBtn" onclick="displayData();" class="btn blue"><i class="fa fa-search"></i> Search</button>
                                </div>
                            </div>
                        </div>
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
