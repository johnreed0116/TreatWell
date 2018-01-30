<%@include file="../header.jsp"%>
<script>
    $(function () {
        displayData();
        $('#countryId').change(function () {
            getCity();
        }).trigger('change');
        $('#cityId').change(function () {
            getCityArea();
        });
        $('#countryId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#cityId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#areaId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Clinic Name'),
                $('<th class="center" width="20%">').html('Phone No'),
                $('<th class="center" width="20%">').html('Clinic Address'),
                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getClinics', {clinicName: $('#clinicName').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_CLINIC_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_CLINIC_ID + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].CLINIC_NME),
                                    $('<td>').html(list[i].PHONE_NO),
                                    $('<td>').html(list[i].ADDRESS),
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
        if ($.trim($('#clinicName').val()) === '') {
            $('#clinicName').notify('Clinic Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#clinicName').focus();
            return false;
        }
        if ($.trim($('#cityId').val()) === '') {
            $('#cityId').notify('City is Required Field', 'error', {autoHideDelay: 15000});
            $('#cityId').focus();
            return false;
        }
        if ($.trim($('#areaId').val()) === '') {
            $('#areaId').notify('Area is Required Field', 'error', {autoHideDelay: 15000});
            $('#areaId').focus();
            return false;
        }

        var obj = {
            clinicId: $('#clinicId').val(),
            clinicName: $('#clinicName').val(),
            mapQuardinates: $('#mapQuardinates').val(),
            phoneNo: $('#phoneNo').val(),
            clinicAddress: $('#clinicAddress').val(),
            countryId: $('#countryId').val(),
            cityId: $('#cityId').val(),
            areaId: $('#areaId').val()
        };
        $.post('setup.htm?action=saveClinic', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Clinic Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#clinicId').val('');
                $('#addClinic').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Clinic. Please try again later.", {
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
                    $.post('setup.htm?action=deleteClinic', {id: id}, function (res) {
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


    function addClinicDialog() {
        $('#clinicId').val('');
        $('#clinicName').val('');
        $('#mapQuardinates').val('');
        $('#phoneNo').val('');
        $('#clinicAddress').val('');
        $('#countryId').val('1').trigger('change');
        $('#addClinic').modal('show');
    }
    function editRow(id) {
        $('#clinicId').val(id);
        $.get('setup.htm?action=getClinicById', {clinicId: id},
                function (obj) {
                    $('#clinicName').val(obj.CLINIC_NME);
                    $('#mapQuardinates').val(obj.MAP_COORDINATES);
                    $('#phoneNo').val(obj.PHONE_NO);
                    $('#editCity').val(obj.CITY_ID);
                    $('#editArea').val(obj.CITY_AREA_ID);
                    $('#countryId').val(obj.COUNTRY_ID);
                    $('#countryId').trigger('change');
                    $('#clinicAddress').val(obj.ADDRESS);
                    $('#addClinic').modal('show');
                }, 'json');
    }
    function getCity() {
        //Find all Citys
        $('#cityId').find('option').remove();
        $.get('setup.htm?action=getCityByCountryId', {countryId: $('#countryId').val()}, function (data) {
            if (data !== null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $('<option />', {value: data[i].CITY_ID, text: data[i].CITY_NME}).appendTo($('#cityId'));
                }
            } else {
                $('<option />', {value: '', text: 'No City Found'}).appendTo($('#cityId'));
            }
            if ($('#editCity').val() !== '') {
                $('#cityId').val($('#editCity').val()).trigger('change');
                $('#editCity').val('');
            } else {
                $('#cityId').trigger('change');
            }
        }, 'json');
    }
    function getCityArea() {
        //Find all areas
        $('#areaId').find('option').remove();
        $.get('clinic.htm?action=getAreasByCityId', {cityId: $('#cityId').val()}, function (data) {
            if (data !== null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $('<option />', {value: data[i].CITY_AREA_ID, text: data[i].AREA_NME}).appendTo($('#areaId'));
                }
            } else {
                $('<option />', {value: '', text: 'No Area Found'}).appendTo($('#areaId'));
            }
            if ($('#editArea').val() !== '') {
                $('#areaId').val($('#editArea').val()).trigger('change');
                $('#editArea').val('');
            } else {
                $('#areaId').trigger('change');
            }
        }, 'json');
    }
</script>
<input type="hidden" name="editCity" id="editCity" >
<input type="hidden" name="editArea" id="editArea" >
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Clinics </h1>
    </div>
</div>
<div class="modal fade" id="addClinic">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Clinic</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="clinicId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Clinic Name *</label>
                                <div>
                                    <input type="text" class="form-control" id="clinicName" placeholder="Clinic Name" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Map Coordinates</label>
                                <div>
                                    <input type="text" class="form-control" id="mapQuardinates" placeholder="Map Coordinates" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Phone No</label>
                                <div>
                                    <input type="text"   class="form-control" id="phoneNo" placeholder="0300xxxxxxx" onkeyup="onlyInteger(this);" maxlength="16" >
                                </div>
                            </div>
                        </div> 
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Country</label>
                                <div>
                                    <select class="form-control select2-default" id="countryId">
                                        <c:forEach items="${requestScope.refData.country}" var="data">
                                            <option value="${data.COUNTRY_ID}">${data.COUNTRY_NME}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div> 
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>City</label>
                                <div>
                                    <select class="form-control select2-default" id="cityId">
                                    </select>
                                </div>
                            </div>
                        </div> 
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Area</label>
                                <div>
                                    <select class="form-control select2-default" id="areaId">
                                    </select>
                                </div>
                            </div>
                        </div> 
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Clinic Address</label>
                                <textarea class="form-control" id="clinicAddress" rows="2" cols="30"></textarea>
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
                    Registered Clinics
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px;">
                            <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                <button type="button" class="btn blue" onclick="addClinicDialog();"><i class="fa fa-plus-circle"></i> New Clinic</button>
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

