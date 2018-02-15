<%-- 
    Document   : addHospitalRoom
    Created on : Feb 14, 2018, 5:35:23 PM
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
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="75%">').html('Room Name'),
                $('<th class="center" width="10%">').html('Total Beds'),
                $('<th class="center" width="10%" colspan="2">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getHospitalRoom', {clinicId: $('#clinicId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_CLINIC_ROOM_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_CLINIC_ROOM_ID + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].ROOM_NME),
                                    $('<td>').html(list[i].TOTAL_BEDS),
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
        if ($.trim($('#roomName').val()) === '') {
            $('#roomName').notify('Room Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#roomName').focus();
            return false;
        }

        var obj = {
            roomId: $('#roomId').val(),
            clinicId: $('#clinicId').val(),
            roomName: $('#roomName').val(),
            beds: $('#beds').val()
        };
        $.post('clinic.htm?action=saveHospitalRoom', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Room Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#roomId').val('');
                $('#addRoom').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Room. Please try again later.", {
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
                    $.post('clinic.htm?action=deleteHospitalRoom', {id: id}, function (res) {
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


    function addRoomDialog() {
        $('#roomId').val('');
        $('#beds').val('');
        $('#roomName').val('');
        $('#addRoom').modal('show');
    }
    function editRow(id) {
        $('#roomId').val(id);
        $.get('clinic.htm?action=getHospitalRoomById', {roomId: id},
                function (obj) {
                    $('#roomName').val(obj.ROOM_NME);
                    $('#beds').val(obj.TOTAL_BEDS);
                    $('#addRoom').modal('show');
                }, 'json');
    }

</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Hospital Room </h1>
    </div>
</div>
<div class="modal fade" id="addRoom">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Hospital Room</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="roomId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Room Name *</label>
                                <div>
                                    <input type="text" class="form-control" id="roomName" placeholder="Room Name" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Total Beds</label>
                                <div>
                                    <input type="text" class="form-control" id="beds" onkeyup="onlyInteger(this);" placeholder="Total Beds" >
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
                    Hospital Room
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-10">
                            <div class="form-group">
                                <label>Clinic Name</label>
                                <select id="clinicId" class="form-control select2_category" onclick="displayData();" data-placeholder="Choose a Clinic">       
                                    <c:forEach items="${requestScope.refData.clinic}" var="obj">
                                        <option value="${obj.TW_CLINIC_ID}">${obj.CLINIC_NME}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-2" style="padding-top: 23px;">
                            <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                <button type="button" class="btn blue" onclick="addRoomDialog();"><i class="fa fa-plus-circle"></i> New Room</button>
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
