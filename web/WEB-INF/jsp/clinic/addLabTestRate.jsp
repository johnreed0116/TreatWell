<%-- 
    Document   : addLabTestRate
    Created on : Dec 12, 2018, 6:37:40 PM
    Author     : Ali Zaidi
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('#medicalLabId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        displayData();
        $('#medicalLabId').change(function () {
            getLabTestRate();
        });
    });
    function getLabTestRate() {
        $.get('clinic.htm?action=getLabTestRate',{id:$('#medicalLabId').val()},function (data) {
                if (data !== null && data.length>0) {
                    for(var i =0;i<data.length;i++){
                        $('#rateOf_' + data[i].TW_LAB_TEST_ID).val(data[i].RATE);
                    }
                }
            },'json');
    }
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="35%">').html('Test Name'),
                $('<th class="center" width="35%">').html('Test Group'),
                $('<th class="center" width="25%">').html('Test Rate')
                )));
        $.get('clinic.htm?action=getLabTests', {labTestNameSearch: $('#labTestNameSearch').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html('<input type="hidden" name="labTestIds" value="' + list[i].TW_LAB_TEST_ID + '" />' +eval(i + 1)),
                                    $('<td>').html(list[i].TITLE),
                                    $('<td>').html(list[i].TEST_GROUP),
                                    $('<td align="center">').html('<input type="text" name="labTestRate" class="form-control"  id="rateOf_' + list[i].TW_LAB_TEST_ID + '"  onkeyup="onlyInteger(this);" />')
                                    ));
                        }
                        $('#displayDiv').html('');
                        $('#displayDiv').append($tbl);
                        getLabTestRate();
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
        var data = new FormData(document.getElementById('testRateForm'));
        $.ajax({
            url: 'clinic.htm?action=saveLabTestRate',
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Rate data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                return false;
            } else {
                $.bootstrapGrowl("Error in saving rates. Please try again later.", {
                    ele: 'body',
                    type: 'danger',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });

                return false;
            }
        });
        return false;
    }

</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Add Test Rate</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Test Rate
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" id="testRateForm" method="post">
                    <div class="row">
                        <div class="col-md-5">
                            <div class="form-group">
                                <label>Labs</label>
                                <select id="medicalLabId" name="medicalLabId" class="form-control">
                                    <c:forEach items="${requestScope.refData.lab}" var="obj">
                                        <option value="${obj.TW_LAB_MASTER_ID}">${obj.LAB_NME}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-10">&nbsp;</div>
                        <div class="col-md-2"><button type="button" class="btn btn-success" onclick="saveData();"><i class="fa fa-floppy-o"></i> Save</button></div>
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




