<%-- 
    Document   : addMedicalLab
    Created on : Jan 25, 2018, 1:52:26 PM
    Author     : dell
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {

        displayData();
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Lab Name'),
                $('<th class="center" width="20%">').html('Url'),
                $('<th class="center" width="15%" colspan="3">').html('&nbsp;')
                )));
        $.get('performa.htm?action=getMedicalLab', {},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_LAB_MASTER_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_LAB_MASTER_ID + '\');"></i>';
                            var featuredHtm = '<i class="fa ' + (list[i].FEATURED_IND === 'Y' ? 'fa-star' : 'fa-star-o') + '" aria-hidden="true" title="Click to view Featured" style="cursor: pointer;" onclick="featuredMedicalLab(\'' + list[i].TW_LAB_MASTER_ID + '\',\'' + list[i].FEATURED_IND + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].LAB_NME),
                                    $('<td>').html(list[i].WEB_URL),
                                    $('<td align="center">').html(featuredHtm),
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
                                $('<td  colspan="7">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }
    function featuredMedicalLab(id, status) {
        var title = "", msgHead = "";
        if (status === 'N') {
            title = "Do you want to featured this Medical Lab?";
            status = "Y";
            msgHead = "Featured";
        } else {
            title = "Do you want to Un-featured this Medical Lab?";
            status = "N";
            msgHead = "Un-Featured";
        }
        bootbox.confirm({
            message: title,
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
                    $.post('setup.htm?action=featuredMedicalLab', {id: id, status: status}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl(msgHead + ' successfully.', {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            displayData();
                        } else {
                            $.bootstrapGrowl('Medical Lab can not be ' + msgHead, {
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
    function addMedicalLab() {
        $('#imageOld').html('');
        $('#medicalLabId').val('');
        $('#labUrl').val('');
        $('#labName').val('');
        $('#addMedicalLab').modal('show');
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
                    $.post('performa.htm?action=deleteMedicalLab', {id: id}, function (res) {
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
        $('#imageOld').html('');
        $('#medicalLabId').val(id);
        $.get('performa.htm?action=getMedicalLabById', {id: id},
                function (obj) {
                    $('#labName').val(obj.LAB_NME);
                    $('#labUrl').val(obj.WEB_URL);
                    var picPath = "";
                    if (obj.LOGO_IMAGE === '') {
                        picPath = 'images/nophoto.png';
                    } else {
                        picPath = 'upload/lab/logo/' + obj.TW_LAB_MASTER_ID + '/' + obj.LOGO_IMAGE;
                    }
                    $('#imageOld').append('<img src="' + picPath + '" width="150px" height="150px" />');
                    $('#addMedicalLab').modal('show');
                }, 'json');
    }
    function saveLab() {
        if ($.trim($('#labName').val()) === '') {
            $('#labName').notify('Lab Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#labName').focus();
            return false;
        }
        var data = new FormData(document.getElementById('medicalLab'));
        $.ajax({
            url: 'performa.htm?action=saveMedicalLab',
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (data) {
            if (data) {
                if (data.result === 'save_success') {
                    $.bootstrapGrowl("Lab Save successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    displayData();
                    $('#addMedicalLab').modal('hide');

                } else {
                    $.bootstrapGrowl("Error in Saving Lab.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#addMedicalLab').modal('hide');
                }
            }
        });
    }
</script>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Medical Lab</h1>
    </div>
</div>
<div class="modal fade" id="addMedicalLab">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Medical Lab</h3>
            </div>
            <div class="modal-body">
                <form action="#" role="form" id="medicalLab" method="post" >
                    <input type="hidden" name="medicalLabId" id="medicalLabId" value="">
                    <div class="row">                    
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Lab Name *</label>
                                <input type="text" class="form-control" id="labName" name="labName">
                            </div>
                        </div>
                    </div>
                    <div class="row"> 
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Url</label>
                                <input type="text" class="form-control" id="labUrl" name="labUrl">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Lab Logo</label>
                                <input type="file" class="form-control" id="labLogo" name="labLogo">
                            </div>
                            <div id="imageOld"></div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveLab();">Save</button>
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
                        Medical Lab
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px; " >
                            <button type="button" class="btn blue" onclick="addMedicalLab();"><i class="fa fa-plus-circle"></i> Add Medical Lab</button>
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