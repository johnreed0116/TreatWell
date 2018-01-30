<%-- 
    Document   : bloodDonor
    Created on : Oct 21, 2017, 12:00:31 PM
    Author     : Cori 5
--%>

<%@include file="../header.jsp"%>
<script language='javascript' src="js/md5.js" type="text/javascript"></script>
<script>
    $(function () {

    });
</script>
<script>
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Doctor Name'),
                $('<th class="center" width="30%">').html('Doctor Type'),
                $('<th class="center" width="20%">').html('Contact No'),
                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getDoctor', {doctorNameSearch: $('#doctorNameSearch').val(), contactNoSearch: $('#contactNoSearch').val(),
            doctorTypeSearch: $('#doctorTypeSearch').val()},
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
                                    $('<td align="center">').html('<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_DOCTOR_ID + '\');"></i>'),
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
    function addDonorDialog() {
        $('#doctorId').val('');
        $('#doctorName').val('');
        $('#cnic').val('');
        $('#doctorAddress').val('');
        $('#cellNo').val('');
        $('#email').val('');
        $('#loginDetails').show();
        $('#addDonor').modal('show');
    }
    function editRow(id) {
        $('#doctorId').val(id);
        $('#loginDetails').hide();
        $.get('setup.htm?action=getDoctorById', {doctorId: id},
                function (obj) {
                    $('#doctorName').val(obj.DOCTOR_NME);
                    $('#email').val(obj.EMAIL);
                    $('#doctorType').val(obj.DOCTOR_CATEGORY_ID);
                    $('#cnic').val(obj.CNIC);
                    $('#cellNo').val(obj.MOBILE_NO);
                    $('#doctorAddress').val(obj.ADDRESS);
                    $('#addDonor').modal('show');
                }, 'json');
    }
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Donor Registration</h1>
    </div>
</div>
<div class="modal fade" id="addDonor">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Donor</h3>
            </div>
            <div class="modal-body">
                <input type="hidden" id="donorId" value="">
                <div class="portlet box green">
                    <div class="portlet-title tabbable-line">
                        <div class="caption">
                            Personal Info 
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Donor Name</label>
                                    <div>
                                        <input type="text" class="form-control" id="donorName" placeholder="Donor Name" >
                                    </div>
                                </div>
                            </div>

                             <div class="col-md-6">
                                <div class="form-group">
                                    <label>Cell no.</label>
                                    <div>
                                        <input type="text" class="form-control" id="cellNo" placeholder="03500000000" onkeyup="onlyInteger(this);" maxlength="13">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Blood Type</label>
                                    <select id="doctorType" class="form-control">
                                        <c:forEach items="${requestScope.refData.categories}" var="obj">
                                            <option value="${obj.TW_DOCTOR_CATEGORY_ID}">${obj.TITLE}</option>
                                        </c:forEach>
                                    </select>
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
                              <div class="col-md-6">
                            <div class="form-group">
                                    <label>City</label>
                                    <div>
                                        <input type="text" class="form-control" id="city" placeholder="City" >
                                    </div>
                                </div>
                             </div>
                             <div class="col-md-6">
                            <div class="form-group">
                                    <label>Communication Way</label>
                                    <div class="input-group">
                                        <div class="icheck-inline">
                                            <label>
                                                <input type="radio" name="communication" value="DIRECT" class="icheck" checked> DIRECT </label>
                                            <label>
                                                <input type="radio" name="communication" value="SMS"  class="icheck"> SMS</label>
                                        </div>
                                    </div>
                                </div>
                             </div>
                        </div>
                    </div>
                </div>
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
        <div class="portlet-body">
            <form name="donorform" action="#" role="form" onsubmit="return false;" method="post">
                <div class="portlet box green">
                    <div class="portlet-title">
                        <div class="caption">
                            Donor Info
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="row">
<!--                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Doctor Type</label>
                                    <select id="doctorTypeSearch" class="form-control select2me" data-placeholder="Select...">
                                        <option value="">ALL</option>
                                        <c:forEach items="${requestScope.refData.categories}" var="obj">
                                            <option value="${obj.TW_DOCTOR_CATEGORY_ID}">${obj.TITLE}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>-->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Donor Name</label>
                                    <div>
                                        <input type="text" class="form-control" id="doctorNameSearch" placeholder="Doctor Name" onchange="displayData(this.value);">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Contact No.</label>
                                    <div>
                                        <input type="text" class="form-control" id="contactNoSearch" placeholder="Contact No." >
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class=row">
                            <div class="col-md-6" style="padding-top: 23px; margin-bottom: 23px; ">
                                <button type="button" class="btn green" onclick="displayData();"><i class="fa fa-search"></i> Search Donor</button>
                            </div>
                            <div class="col-md-6 text-right" style="padding-top: 23px; margin-bottom: 23px;">
                                <button type="button" class="btn blue" onclick="addDonorDialog();"><i class="fa fa-plus-circle"></i> New Donor</button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
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
<script>

    function saveData() {
if ($.trim($('#doctorId').val()) === '') {
        if ($.trim($('#userName').val()) === '') {
            $('#userName').notify('User Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#userName').focus();
            return false;
        }
        if ($.trim($('#password').val()) === '') {
            $('#password').notify('Password is Required Field', 'error', {autoHideDelay: 15000});
            $('#password').focus();
            return false;
        }
        if ($.trim($('#reTypePassword').val()) === '') {
            $('#reTypePassword').notify('ReType Password Field is Required', 'error', {autoHideDelay: 15000});
            $('#reTypePassword').focus();
            return false;
        }

        if ($.trim($('#password').val()) !== $.trim($('#reTypePassword').val())) {
            $('#reTypePassword').notify('Please corfirm the same password', 'error', {autoHideDelay: 15000});
            $('#reTypePassword').focus();
            return false;
        }
        }

        if ($.trim($('#doctorName').val()) === '') {
            $('#doctorName').notify('Doctor Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#doctorName').focus();
            return false;
        }
        if ($.trim($('#cellNo').val()) === '') {
            $('#cellNo').notify('Contact is Required Field', 'error', {autoHideDelay: 15000});
            $('#cellNo').focus();
            return false;
        }
        var password = calcMD5($('#password').val());
        var obj = {
            donorId: $('#donorId').val(),
            donorName: $('#donorName').val(),
            bloodType: $('#bloodType').val(),
            cellNo: $('#cellNo').val(),
            emergencyCellNo: $('#emergencyCellNo').val(),
            userName: $('#userName').val(),
            userPassword: password
        };

        $.post('setup.htm?action=saveDoctor', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Doctor Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#doctorAddress').val('');
                $('#doctorId').val('');
                $('#loginDetails').show();
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
                    $.post('setup.htm?action=deleteDoctor', {id: id}, function (res) {
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
</script>

<%@include file="../footer.jsp"%>

