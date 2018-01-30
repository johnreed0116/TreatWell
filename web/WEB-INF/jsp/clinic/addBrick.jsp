<%-- 
    Document   : addBrick
    Created on : Jan 2, 2018, 2:57:38 PM
    Author     : dell
--%>

<%@include file="../header.jsp"%>
<script>
    var areaIdArr = null;
    $(function () {
        $('#areaId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#cityId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#pharmaCompanyId').change(function () {
            displayData();
        }).trigger('change');
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="80%">').html('Brick Name'),
                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getBricks', {pharmaCompanyId: $('#pharmaCompanyId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_BRICK_MASTER_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_BRICK_MASTER_ID + '\');"></i>';
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
                                $('<td  colspan="7">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }
    function addBrickDialog() {
        $('#brickId').val('');
        $('#brickName').val('');
        $('#cityId').val('').trigger('change');
        $('#areaId').val('').trigger('change');
        $('#addBrick').modal('show');
    }
    function editRow(id) {
        $('#brickId').val(id);
        $.get('clinic.htm?action=getBrickById', {brickId: id},
                function (obj) {
                    $('#brickName').val(obj.TITLE);
                    var cityId = obj.CITY_ID;
                    var cityIdArr = cityId.split(',');
                    $('#cityId').val(cityIdArr).trigger('change');
                    var areaId = obj.CITY_AREA_ID;
                    areaIdArr = areaId.split(',');
                    $('#addBrick').modal('show');
                }, 'json');
    }
    function saveData() {
        var areaIdArr = [];
        areaIdArr = $('#areaId').val();
        if ($.trim($('#brickName').val()) === '') {
            $('#brickName').notify('Brick Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#brickName').focus();
            return false;
        }
        if (areaIdArr === null) {
            $('#areaId').notify('Area is Required Field', 'error', {autoHideDelay: 15000});
            $('#areaId').focus();
            return false;
        }
        var obj = {brickId: $('#brickId').val(), brickName: $('#brickName').val(), pharmaCompanyId: $('#pharmaCompanyId').val(),
            'areaIdArr[]': $('#areaId').val()
        };
        if (areaIdArr.length > 0) {
            $.post('clinic.htm?action=saveBrick', obj, function (obj) {
                if (obj.result === 'save_success') {
                    $('#addBrick').modal('hide');
                    $.bootstrapGrowl("Brick Data saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#brickId').val('');
                    $('#brickName').val('');
                    $('#cityId').val('').trigger('change');
                    $('#areaId').val('').trigger('change');
                    displayData();
                    return false;
                } else {
                    $.bootstrapGrowl("Error in saving Brick.", {
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
        }
        return false;
    }
    function getArea() {
        //Find all areas
        if ($('#cityId').val() !== null) {
            $('#areaId').find('option').remove();
            $.get('clinic.htm?action=getAreaByCitys', {'cityIdArr[]': $('#cityId').val()}, function (data) {
                if (data !== null && data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        $('<option />', {value: data[i].CITY_AREA_ID, text: data[i].AREA_NME + " [ " + data[i].CITY_NME + " ]"}).appendTo($('#areaId'));
                    }
                } else {
                    $('<option />', {value: '', text: 'No Area Found'}).appendTo($('#areaId'));
                }
                if (areaIdArr !== null) {
                    $('#areaId').val(areaIdArr).trigger('change');
                    areaIdArr = null;
                }
            }, 'json');
        }
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
                    $.post('clinic.htm?action=deleteBrick', {brickId: id}, function (res) {
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
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Add Brick</h1>
    </div>
</div>
<input type="hidden" id="brickId" value="">
<div class="row">
    <div class="col-md-12">
        <div class="portlet-body">
            <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
            <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
            <form name="doctorform" action="#" role="form" onsubmit="return false;" method="post">
                <div class="portlet box green">
                    <div class="portlet-title">
                        <div class="caption">
                            Add Brick
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Pharmaceutical Company</label>
                                    <select id="pharmaCompanyId" class="form-control" onchange="displayData();">
                                        <c:forEach items="${requestScope.refData.companies}" var="obj">
                                            <option value="${obj.TW_PHARMACEUTICAL_ID}">${obj.COMPANY_NME}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-12 text-right" style="padding-top: 23px;">
                                <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                    <button type="button" class="btn blue" onclick="addBrickDialog();"><i class="fa fa-plus-circle"></i> New Brick</button>
                                </c:if>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 ">
                                <div id="displayDiv" style="padding-top: 20px;">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="addBrick">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Brick</h3>
            </div>
            <div class="modal-body">
                <input type="hidden" id="pharmaRepId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Brick Name *</label>
                                <div>
                                    <input type="text" class="form-control" id="brickName" placeholder="Brick Name" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>City</label>
                                <select id="cityId" onchange="getArea();" class="form-control" multiple>
                                    <c:forEach items="${requestScope.refData.cities}" var="obj">
                                        <option value="${obj.CITY_ID}">${obj.CITY_NME}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Area</label>
                                <select id="areaId"  class="form-control" multiple>
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

<%@include file="../footer.jsp"%>
