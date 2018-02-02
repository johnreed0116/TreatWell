<%-- 
    Document   : medicineUsage
    Created on : Jan 30, 2018, 12:49:09 AM
    Author     : Ali Zaidi
--%>

<%@include file="../header.jsp"%>
<script language='javascript' src="js/jquery.UrduEditor.js" type="text/javascript"></script>
<script>
    $(function () {
        $('#specialityId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $("#titleUrdu").css('height', '45px');
        $("#titleUrdu").UrduEditor("40px");
        displayData();
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Usage Title (E)'),
                $('<th class="center" width="25%">').html('Usage Title (U)'),
                $('<th class="center" width="10%" colspan="2">').html('&nbsp;')
                )));
        $.get('performa.htm?action=getMedicineUsage', {specialityId: $('#specialityId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_DOSE_USAGE_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_DOSE_USAGE_ID + '\');"></i>';
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
                                    $('<td>').html(list[i].TITLE_URDU),
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
    }
    function addMedicineUsage() {
        $('#doseUsageId').val('');
        $('#titleEnglish').val('');
        $('#titleUrdu').val('');
        $('#addMedicineUsage').modal('show');
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
                    $.post('performa.htm?action=deleteMedicineUsage', {id: id}, function (res) {
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
        $('#doseUsageId').val(id);
        $.get('performa.htm?action=getMedicineUsageById', {id: id},
                function (obj) {
                    $('#titleEnglish').val(obj.TITLE);
                    $('#titleUrdu').val(obj.TITLE_URDU);
                    $('#addMedicineUsage').modal('show');
                }, 'json');
    }
    function saveDoseUsage() {
        if ($.trim($('#titleEnglish').val()) === '') {
            $('#titleEnglish').notify('Title in English is Required Field', 'error', {autoHideDelay: 15000});
            $('#titleEnglish').focus();
            return false;
        }
        $.post('performa.htm?action=saveMedicineUsage', {specialityId: $('#specialityId').val(),
            titleEnglish: $('#titleEnglish').val(), titleUrdu: $('#titleUrdu').val(), doseUsageId: $('#doseUsageId').val()}, function (res) {
            if (res) {
                if (res.result === 'save_success') {
                    $.bootstrapGrowl("Dose Usage Save successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    displayData();
                    $('#addMedicineUsage').modal('hide');

                } else {
                    $.bootstrapGrowl("Error in Saving Dose Usage.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#addMedicineUsage').modal('hide');
                }
            }
        }, 'json');
//        var data = new FormData(document.getElementById('medicineUsage'));
//        data.append('specialityId', $('#specialityId').val());
//        $.ajax({
//            url: 'performa.htm?action=saveMedicineUsage',
//            type: "POST",
//            data: data,
//            cache: false,
//            dataType: 'json',
//            processData: false, // tell jQuery not to process the data
//            contentType: 'application/x-www-form-urlencoded; charset=UTF-8'   // tell jQuery not to set contentType
//
//        }).done(function (data) {
//            if (data) {
//                if (data.result === 'save_success') {
//                    $.bootstrapGrowl("Dose Usage Save successfully.", {
//                        ele: 'body',
//                        type: 'success',
//                        offset: {from: 'top', amount: 80},
//                        align: 'right',
//                        allow_dismiss: true,
//                        stackup_spacing: 10
//
//                    });
//                    displayData();
//                    $('#addMedicineUsage').modal('hide');
//
//                } else {
//                    $.bootstrapGrowl("Error in Saving Dose Usage.", {
//                        ele: 'body',
//                        type: 'danger',
//                        offset: {from: 'top', amount: 80},
//                        align: 'right',
//                        allow_dismiss: true,
//                        stackup_spacing: 10
//                    });
//                    $('#addMedicineUsage').modal('hide');
//                }
//            }
//        });
    }

</script>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Medicine Usage</h1>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="addMedicineUsage">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Medicine Usage</h3>
            </div>
            <div class="modal-body">
                <form action="#" role="form" id="medicineUsage" method="post" >
                    <input type="hidden" name="doseUsageId" id="doseUsageId" value="">
                    <div class="row">                    
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Title In (English) *</label>
                                <input type="text" class="form-control" id="titleEnglish" name="titleEnglish">
                            </div>
                        </div>
                    </div>
                    <div class="row"> 
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Title In (Urdu) *</label>
                                <input type="text" class="form-control" id="titleUrdu" name="titleUrdu"  >
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveDoseUsage();">Save</button>
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
                        Medicine Usage
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
                            <button type="button" class="btn blue" onclick="addMedicineUsage();"><i class="fa fa-plus-circle"></i> Add Medicine Usage</button>
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
