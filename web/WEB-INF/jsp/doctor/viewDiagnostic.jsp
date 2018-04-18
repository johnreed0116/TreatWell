<%-- 
    Document   : addDiagnostic
    Created on : Apr 18, 2018, 4:12:12 PM
    Author     : Ali Zaidi
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('input:checkbox').iCheck({
                            checkboxClass: 'icheckbox_square',
                            increaseArea: '20%' // optional
                        });
        $('#specialityId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        displayData();
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="85%">').html('Diagnostic'),
                $('<th class="center" width="10%" colspan="2">').html('&nbsp;')
                )));
        if ($('#specialityId').val() !== '') {
            $.get('doctor.htm?action=getDiagnostic', {specialityId: $('#specialityId').val()},
                    function (list) {
                        if (list !== null && list.length > 0) {
                            $tbl.append($('<tbody>'));
                            for (var i = 0; i < list.length; i++) {
                                var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_DIAGNOSTICS_ID + '\');"></i>';
                                var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_DIAGNOSTICS_ID + '\');"></i>';
                                if ($('#can_edit').val() !== 'Y') {
                                    editHtm = '&nbsp;';
                                }
                                if ($('#can_delete').val() !== 'Y') {
                                    delHtm = '&nbsp;';
                                }
                                $tbl.append(
                                        $('<tr>').append(
                                        $('<td  align="center">').html(eval(i + 1)),
                                        $('<td>').html(list[i].TITLE),
                                        $('<td align="center">').html(editHtm),
                                        $('<td  align="center">').html(delHtm)
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
        } else {
            $('#displayDiv').html('');
            $tbl.append(
                    $('<tr>').append(
                    $('<td  colspan="4">').html('<b>No data found.</b>')
                    ));
            $('#displayDiv').append($tbl);
        }
    }
    function addDiagnostic() {
        $('#diagnosticId').val('');
        $('#diagnosticTitle').val('');
        $('#addDiagnostic').modal('show');
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
                    $.post('doctor.htm?action=deleteDiagnostic', {id: id}, function (res) {
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
    
    function editRow(id) {
        $('#diagnosticId').val(id);
        $.get('doctor.htm?action=getDiagnosticById', {id: id},
                function (obj) {
                    $('#diagnosticTitle').val(obj.TITLE);
                    if(obj.INPUT_FIELD === 'Y') {
                        $('input[name=input]').iCheck('check');
                    }else {
                        $('input[name=input]').iCheck('unCheck');
                    }
                    $('#addDiagnostic').modal('show');
                }, 'json');
    }
    function saveDiagnostic() {
        if ($.trim($('#diagnosticTitle').val()) === '') {
            $('#diagnosticTitle').notify('Title is Required Field', 'error', {autoHideDelay: 15000});
            $('#diagnosticTitle').focus();
            return false;
        }
        $.post('doctor.htm?action=saveDiagnostic', {specialityId: $('#specialityId').val(),diagnosticInd: $('input[name=input]:checked').val(),
            diagnosticTitle: $('#diagnosticTitle').val(), diagnosticId: $('#diagnosticId').val()}, function (res) {
            if (res) {
                if (res.result === 'save_success') {
                    $.bootstrapGrowl("Diagnostic Save successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    displayData();
                    $('#addDiagnostic').modal('hide');

                } else {
                    $.bootstrapGrowl("Error in Saving Diagnostic.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#addDiagnostic').modal('hide');
                }
            }
        }, 'json');
    }

</script>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Add Diagnostic</h1>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="addDiagnostic">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Diagnostic</h3>
            </div>
            <div class="modal-body">
                <form action="#" role="form" id="diagnostic" method="post" >
                    <input type="hidden" name="diagnosticId" id="diagnosticId" value="">
                    <div class="row">                    
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Title *</label>
                                <input type="text" class="form-control" id="diagnosticTitle" name="diagnosticTitle">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Input Field</label>
                                <input type="checkbox" name="input" value="Y" class="icheck"  />
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveDiagnostic();">Save</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet-body">
            <div class="portlet box green">
                <div class="portlet-title">
                    <div class="caption">
                        Diagnostic
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Medical Speciality</label>
                                <select id="specialityId" class="form-control" onchange="displayData();">
                                    <c:forEach items="${requestScope.refData.speciality}" var="obj">
                                        <option value="${obj.TW_MEDICAL_SPECIALITY_ID}">${obj.TITLE}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px; " >
                            <button type="button" class="btn blue" onclick="addDiagnostic();"><i class="fa fa-plus-circle"></i> Add Diagnostic</button>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12" style="padding-top: 20px;">
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
<%@include file="../footer.jsp"%>

