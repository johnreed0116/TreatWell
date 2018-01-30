<%-- 
    Document   : addCityArea
    Created on : Nov 20, 2017, 8:48:03 PM
    Author     : Cori 5
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('#cityId').change(function () {
            displayData();
        }).trigger('change');
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Area Name'),
                $('<th class="center" width="30%">').html('City Name'),
                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getCityArea',{cityId:$('#cityId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].CITY_AREA_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].CITY_AREA_ID + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].AREA_NME),
                                    $('<td>').html(list[i].CITY_NME),
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

    function saveData() {
        if ($.trim($('#title').val()) === '') {
            $('#title').notify('Area Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#title').focus();
            return false;
        }

        var obj = {
            areaId: $('#areaId').val(),
            cityId: $('#cityId').val(),
            areaName: $('#title').val()
        };
        $.post('clinic.htm?action=saveCityArea', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Area Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('#title').val('');
                $('#areaId').val('');
                $('#addCityArea').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Area. Please try again later.", {
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
                    $.post('clinic.htm?action=deleteCityArea', {id: id}, function (res) {
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


    function addCityAreaDialog() {
        $('#areaId').val('');
        $('#title').val('');
        $('#addCityArea').modal('show');
    }
    function editRow(id) {
        $('#areaId').val(id);
        $.get('clinic.htm?action=getCityAreaById', {areaId: id},
                function (obj) {
                    $('#title').val(obj.AREA_NME);
                    $('#cityId').val(obj.CITY_ID);
                    $('#addCityArea').modal('show');
                }, 'json');
    }

</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Area</h1>
    </div>
</div>
<div class="modal fade" id="addCityArea">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Area</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="areaId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Area Name *</label>
                                <div>
                                    <input type="text" class="form-control" id="title" placeholder="Area Name" >
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
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Area
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label>City*</label>
                            <select id="cityId" name="cityId" class="form-control">
                                <c:forEach items="${requestScope.refData.cityList}" var="obj">
                                    <option value="${obj.CITY_ID }">${obj.CITY_NME}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px;">
                            <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                <button type="button" class="btn blue" onclick="addCityAreaDialog();"><i class="fa fa-plus-circle"></i> New Area</button>
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


