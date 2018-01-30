<%-- 
    Document   : addDoctorCompany
    Created on : Nov 7, 2017, 5:49:42 PM
    Author     : Cori 5
--%>

<%@include file="../header.jsp"%>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Panel's Company</h1>
    </div>
</div>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="modal fade" id="addPanelCompnay">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Assign Panel Company</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="activeIndicator" value="n">
                <input type="hidden" id="panelCompanyId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Company*</label>
                                <select id="panelId" class="form-control select2_category" data-placeholder="Choose a Panel Company">
                                    <option value="">Select Company</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Expiry Date*</label>
                                <div class="input-group input-medium date date-picker">
                                    <input type="text" id="expiryDate" class="form-control" readonly="">
                                    <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
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
        <div class="portlet-body">
            <form name="doctorform" action="#" role="form" onsubmit="return false;" method="post">
                <div class="portlet box green">
                    <div class="portlet-title">
                        <div class="caption">
                            Assign Panel Clinics
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label>Doctor Name *</label>
                                    <select id="doctorId" class="form-control select2_category" data-placeholder="Choose a Doctor" onchange="displayData();">
                                        <c:forEach items="${requestScope.refData.doctorsList}" var="obj">
                                            <option value="${obj.TW_DOCTOR_ID}">${obj.DOCTOR_NME}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4" style="padding-top: 23px;">
                                <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                    <button type="button" class="btn blue" onclick="addPanelDialog();"><i class="fa fa-plus-circle"></i> Assign Company</button>
                                </c:if>
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
<style>
    .alert {
        padding: 3px !important;
    }
</style>
<script>
    $(function () {
        $('#doctorId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#panelId').select2({
            placeholder: "Select an option",
            allowClear: true
        });

        $('.date-picker').datepicker({
            format: 'dd-mm-yyyy',
            autoclose: true
        });
        displayData();
    });
    function addPanelDialog() {
        $('#panelCompanyId').val('');
        $('#panelId').find('option').remove();
        $.get('setup.htm?action=getAvailablePanelCompanies', {doctorId: $('#doctorId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        for (var i = 0; i < list.length; i++) {
                            $('<option />', {value: list[i].TW_COMPANY_ID, text: list[i].COMPANY_NME}).appendTo($('#panelId'));

                        }
                    }
                    $('#addPanelCompnay').modal('show');
                }, 'json');
    }
    function saveData() {
        if ($.trim($('#panelId').val()) === '') {
            $('#panelId').notify('Select company first.', 'error', {autoHideDelay: 15000});
            $('#panelId').focus();
            return false;
        }
        if ($.trim($('#expiryDate').val()) === '') {
            $('#expiryDate').notify('Expiry Date is Required Field', 'error', {autoHideDelay: 15000});
            $('#expiryDate').focus();
            return false;
        }

        var obj = {
            panelCompanyId: $('#panelCompanyId').val(), panelId: $('#panelId').val(), doctorId: $('#doctorId').val(),
            expiryDate: $('#expiryDate').val()
        };
        $.post('setup.htm?action=savePanelCompany', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Panel Company assigned to doctor saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#panelCompanyId').val();
                $('#addPanelCompnay').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Panel Company. Please try again later.", {
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
                    $.post('setup.htm?action=deleteAssignPanelCompany', {id: id}, function (res) {
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
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="10%">').html('Sr. #'),
                $('<th class="center" width="50%">').html('Company Name'),
                $('<th class="center" width="20%">').html('Expiry Date'),
                $('<th class="center" width="20%">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getPanelCompaniesForDoctors', {doctorId: $('#doctorId').val()},
                function (list) {
                    var userStatus;
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            if (list[i].ACTIVE_IND === 'Y' && $('#can_delete').val() === 'Y') {
                                userStatus = $('<td align="center">').html('<i class="fa fa-ban" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_DOCTOR_COMPANY_ID + '\');"></i>');
                            } else if (list[i].ACTIVE_IND !== 'Y') {
                                userStatus = $('<td class="alert alert-danger">').html("Cancelled");

                            }

                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].COMPANY_NME),
                                    $('<td>').html(list[i].EXPIRY_DTE),
                                    $('<td align="center">').html(userStatus)
                                    // $('<td  align="center">').html(delHtm)
                                    ));
                        }
                        $('#displayDiv').html('');
                        $('#displayDiv').append($tbl);
                        return false;
                    } else {
                        $('#displayDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td  colspan="4">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }







    function editRow(id) {
        $.post('setup.htm?action=updateDoctorPanelCompanyIndicator', {panelCompanyId: id, activeIndicator: $('#activeIndicator').val()}, function (obj) {
            if (obj.msg === 'saved') {
                $.bootstrapGrowl("Doctor's Panel Company data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#healthCardId').val('');
                $('#addCards').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Doctor's Panel Company Data. Please try again later.", {
                    ele: 'body',
                    type: 'gander',
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
</script>

<%@include file="../footer.jsp"%>




