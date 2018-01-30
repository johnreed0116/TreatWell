<%-- 
    Document   : viewDoctor
    Created on : Oct 11, 2017, 6:40:32 PM
    Author     : Cori 5
--%>
<%@include file="../header.jsp"%>
<script>
    $(function () {

    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Doctor Name'),
                $('<th class="center" width="30%">').html('Doctor Type'),
                $('<th class="center" width="20%">').html('Contact No'),
                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getDoctor', {doctorName: $('#doctorName').val(), contactNo: $('#contactNo').val(),
            doctorType: $('#doctorType').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].DOCTOR_NME),
                                    $('<td>').html(list[i].TITLE),
                                    $('<td >').html(list[i].MOBILE_NO),
                                    $('<td align="center">').html('<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_DOCTOR_ID + '\',\'' + list[i].TW_DOCTOR_ID + '\');"></i>'),
                                    $('<td align="center">').html('<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_DOCTOR_ID + '\');"></i>')
                                    ));
                        }
                        $('#displayDiv').html('');
                        $('#displayDiv').append($tbl);
                        return false;
                    } else {
                        $('#displayDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td class="center aligned negative" colspan="7">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }
</script>
<div class="page-head">
    <div class="page-title">
        <h1>Doctor Listing</h1>
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
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Doctor Name</label>
                                    <div>
                                        <input type="text" class="form-control" id="doctorName" placeholder="Doctor Name" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Contact No.</label>
                                    <div>
                                        <input type="text" class="form-control" id="contactNo" placeholder="Contact No." >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Doctor Type</label>
                                    <select id="doctorType" class="form-control">
                                        <option value="">ALL</option>
                                        <c:forEach items="${requestScope.refData.categories}" var="obj">
                                            <option value="${obj.TW_DOCTOR_CATEGORY_ID}">${obj.TITLE}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>&nbsp;</label>
                                    <div class="input-group input-medium date date-picker">
                                        <button id="saveBtn" onclick="displayData();" class="btn blue"><i class="fa fa-search"></i> Search</button>
                                    </div>
                                </div>
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

