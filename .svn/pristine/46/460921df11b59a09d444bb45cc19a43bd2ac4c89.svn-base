<%-- 
    Document   : addCountry
    Created on : Nov 20, 2017, 8:22:06 PM
    Author     : Cori 5
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
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
        $('#countryId').change(function () {
            getCity();
        }).trigger('change');
        $('#cityId').change(function () {
            getCityArea();
        });
        displayData();
    });
    function getCity() {
        //Find all characters
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
        if ($('#cityId').val() !== '') {
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
    }
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="40%">').html('Hospital Name'),
                $('<th class="center" width="40%">').html('Address'),
                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getHospital',
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_HOSPITAL_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_HOSPITAL_ID + '\');"></i>';
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
        if ($.trim($('#title').val()) === '') {
            $('#title').notify('Hospital Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#title').focus();
            return false;
        }
        if ($.trim($('#cityId').val()) === '') {
            $('#cityId').notify('City is Required Field', 'error', {autoHideDelay: 15000});
            $('#cityId').focus();
            return false;
        }
        var obj = {
            hospitalId: $('#hospitalId').val(),
            hospitalName: $('#title').val(),
            hospitalAddress: $('#address').val(),
            countryId: $('#countryId').val(),
            cityId: $('#cityId').val(),
            areaId: $('#areaId').val()
        };
        $.post('setup.htm?action=saveHospital', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Hospital Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#hospitalId').val('');
                $('#addCountry').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Hospital. Please try again later.", {
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
                    $.post('setup.htm?action=deleteHospital', {id: id}, function (res) {
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


    function addHospitalDialog() {
        $('#hospitalId').val('');
        $('#title').val('');
        $('#addCountry').modal('show');
    }
    function editRow(id) {
        $('#hospitalId').val(id);
        $.get('setup.htm?action=getHospitalById', {hospitalId: id},
                function (obj) {
                    $('#title').val(obj.TITLE);
                    $('#editCity').val(obj.CITY_ID);
                    $('#editArea').val(obj.CITY_AREA_ID);
                    $('#countryId').val(obj.COUNTRY_ID);
                    $('#countryId').trigger('change');
                    $('#address').val(obj.ADDRESS);
                    $('#addCountry').modal('show');
                }, 'json');
    }

</script>
<input type="hidden" name="editCity" id="editCity" >
<input type="hidden" name="editArea" id="editArea" >
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Hospital</h1>
    </div>
</div>
<div class="modal fade" id="addCountry">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Hospital</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="hospitalId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Hospital Name *</label>
                                <div>
                                    <input type="text" class="form-control" id="title" placeholder="Hospital Name" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Country</label>
                                <select id="countryId" class="form-control" data-placeholder="Select...">
                                    <c:forEach items="${requestScope.refData.country}" var="obj">
                                        <option value="${obj.COUNTRY_ID}">${obj.COUNTRY_NME}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>City</label>
                                <select id="cityId" class="form-control" data-placeholder="Select...">

                                </select>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Area</label>
                                <select id="areaId" class="form-control" data-placeholder="Select...">

                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Address</label>
                                <div>
                                    <input type="text" class="form-control" id="address" placeholder="Address" >
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
                    Hospital
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px;">
                            <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                <button type="button" class="btn blue" onclick="addHospitalDialog();"><i class="fa fa-plus-circle"></i> New Hospital</button>
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



