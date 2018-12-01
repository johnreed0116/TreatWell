<%-- 
    Document   : addMedicalSpeciality
    Created on : Feb 14, 2018, 4:46:53 PM
    Author     : Ali Zaidi
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        displayData();
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="10%">').html('Sr. #'),
                $('<th class="center" width="60%">').html('Speciality Name'),
                $('<th class="center" width="20%">').html('Show on Web'),
                $('<th class="center" width="10%" colspan="2">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getMedicalSpeciality', {specialityNameSearch: $('#specialityNameSearch').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_MEDICAL_SPECIALITY_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_MEDICAL_SPECIALITY_ID + '\');"></i>';
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
                                    $('<td align="center">').html(list[i].SHOW_ON_WEB === 'Y' ? 'Yes' : 'No'),
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
                                $('<td  colspan="5">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }

    function saveData() {
        if ($.trim($('#specialityName').val()) === '') {
            $('#specialityName').notify('Speciality Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#specialityName').focus();
            return false;
        }
        var obj = {
            specialityId: $('#specialityId').val(),
            specialityName: $('#specialityName').val(),
            showWebInd: $('#showWebInd').val()
        };
        $.post('clinic.htm?action=saveMedicalSpeciality', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#specialityId').val('');
                $('#showWebInd').val('N');
                $('#addSpeciality').modal('hide');
                displayData();
            } else {
                $.bootstrapGrowl("Error in saving Speciality. Please try again later.", {
                    ele: 'body',
                    type: 'danger',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
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
                    $.post('clinic.htm?action=deleteMedicalSpeciality', {id: id}, function (res) {
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


    function addSpecialityDialog() {
        $('#specialityId').val('');
        $('#specialityName').val('');
        $('#addSpeciality').modal('show');
    }
    function editRow(id) {
        $('#specialityId').val(id);
        $.get('clinic.htm?action=getMedicalSpecialityById', {specialityId: id},
                function (obj) {
                    $('#specialityName').val(obj.TITLE);
                    $('#showWebInd').val(obj.SHOW_ON_WEB);
                    $('#addSpeciality').modal('show');
                }, 'json');
    }

</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Medical Speciality </h1>
    </div>
</div>
<div class="modal fade" id="addSpeciality">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Medical Speciality</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="specialityId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Speciality Name *</label>
                                <div>
                                    <input type="text" class="form-control" id="specialityName" placeholder="Speciality Name" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Show on Web</label>
                                <select class="form-control" id="showWebInd" name="showWebInd">
                                    <option value="N">No</option>
                                    <option value="Y">Yes</option>
                                </select>
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
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Medical Speciality
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-8">
                            <div class="form-group">
                                <label>Medical Speciality Name</label>
                                <div>
                                    <input type="text" class="form-control" id="specialityNameSearch" placeholder="Medical Speciality Name" onchange="displayData(this.value);">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2 text-right" style="padding-top: 23px; margin-bottom: 23px; ">
                            <button type="button" class="btn green" onclick="displayData();"><i class="fa fa-search"></i> Search Speciality</button>
                        </div>
                        <div class="col-md-2" style="padding-top: 23px;">
                            <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                <button type="button" class="btn blue" onclick="addSpecialityDialog();"><i class="fa fa-plus-circle"></i> New Speciality</button>
                            </c:if>
                        </div>
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




