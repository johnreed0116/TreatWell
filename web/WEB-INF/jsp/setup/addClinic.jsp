<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('#countryId').change(function () {
            getCity();
        }).trigger('change');
        $('#cityId').change(function () {
            getCityArea();
        }).trigger('change');
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
                $('<th class="center" width="10%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Clinic Name'),
                $('<th class="center" width="20%">').html('Phone No'),
                $('<th style="text-align:center;" width="10%">').html('Type'),
                $('<th style="text-align:center;" width="10%">').html('Status'),
                $('<th class="center" width="20%" colspan="6">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getClinics', {searchCityId: $('#searchCity').val(), accountInd: $('#accountInd').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var activeInd = list[i].ACTIVE_IND;
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_CLINIC_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_CLINIC_ID + '\');"></i>';
                            var viewAtthHtm = '<i class="fa fa-paperclip" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="displayAttachements(\'' + list[i].TW_CLINIC_ID + '\');"></i>';
                            var uploadAttachmentHtm = '<i class="fa fa-cloud-upload" aria-hidden="true" title="Upload Attachments" style="cursor: pointer;" onclick="uploadClinicAttachements(\'' + list[i].TW_CLINIC_ID + '\');"></i>';
                            var featuredHtm = '<i class="fa ' + (list[i].FEATURED_IND === 'Y' ? 'fa-star' : 'fa-star-o') + '" aria-hidden="true" title="Click to view Featured" style="cursor: pointer;" onclick="featuredClinic(\'' + list[i].TW_CLINIC_ID + '\',\'' + list[i].FEATURED_IND + '\');"></i>';
                            var activationHtm = '&nbsp;';
                            if (activeInd === 'Y') {
                                activationHtm = '<i class="fa fa-ban danger" aria-hidden="true" title="Click to Inactive!" style="cursor: pointer;" onclick="activateAccount(\'' + list[i].TW_CLINIC_ID + '\',\'N\');"></i>';
                            } else if (activeInd === 'N') {
                                activationHtm = '<i class="fa fa-check" aria-hidden="true" title="Click to activate!" style="cursor: pointer;" onclick="activateAccount(\'' + list[i].TW_CLINIC_ID + '\',\'Y\');"></i>';
                            }
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            var statusInd = '&nbsp;';
                            if (activeInd === 'Y') {
                                statusInd = '<span class="label label-sm label-success">Active </span>';
                            } else {
                                statusInd = '<span class="label label-sm label-danger">Blocked </span>';
                                featuredHtm = '&nbsp;';
                                editHtm = '&nbsp;';
                                delHtm = '&nbsp;';
                                uploadAttachmentHtm = '&nbsp;';
                                viewAtthHtm = '&nbsp;';
                            }
                            var isHospital = '';
                            if (list[i].IS_HOSPITAL === 'Y') {
                                isHospital = '<a href="#0" onclick="updateClinicType(\'' + list[i].TW_CLINIC_ID + '\',\'' + list[i].IS_HOSPITAL + '\');"><span class="label label-danger">Hospital</span></a>';
                            } else {
                                isHospital = '<a href="#0" onclick="updateClinicType(\'' + list[i].TW_CLINIC_ID + '\',\'' + list[i].IS_HOSPITAL + '\');"><span class="label label-default">Clinic</span></a>';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].CLINIC_NME),
                                    $('<td>').html(list[i].PHONE_NO),
                                    $('<td align="center">').html(isHospital),
                                    $('<td align="center">').html(statusInd),
                                    $('<td align="center">').html(featuredHtm),
                                    $('<td align="center">').html(editHtm),
                                    $('<td  align="center">').html(delHtm),
                                    $('<td  align="center">').html(uploadAttachmentHtm),
                                    $('<td  align="center">').html(viewAtthHtm),
                                    $('<td align="center">').html(activationHtm)
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
    function activateAccount(id, status) {
        bootbox.confirm({
            message: "Do you want to activate doctor account?",
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
                    $.post('setup.htm?action=activeClinicAccount', {id: id, status: status}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl("Clinic account activated successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            displayData();
                        } else {
                            $.bootstrapGrowl("Error in activating account. please try again later.", {
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
    function featuredClinic(id, status) {
        var title = "", msgHead = "";
        if (status === 'N') {
            title = "Do you want to featured this clinic?";
            status = "Y";
            msgHead = "Featured";
        } else {
            title = "Do you want to Un-featured this clinic?";
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
                    $.post('setup.htm?action=clinicFeatured', {id: id, status: status}, function (res) {
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
                            $.bootstrapGrowl('clinic can not be ' + msgHead, {
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
    function saveAttachment() {
        var data = new FormData(document.getElementById('doctorAttachment'));
        data.append("clinicId", $('#clinicId').val());
        $.ajax({
            url: "setup.htm?action=saveClinicAttachment",
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (data) {
            if (data) {
                if (data.result === 'save_success') {
                    $.bootstrapGrowl("Attachment Uploaded successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    $('#clinicId').val('');
                    $('#addAttachements').modal('hide');
                } else {
                    $.bootstrapGrowl("Error in Uploading Attachment. Please try again later.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#addAttachements').modal('hide');
                }
            }
        });
    }
    function saveData() {
        if ($.trim($('#clinicName').val()) === '') {
            $('#clinicName').notify('Clinic/Hospital Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#clinicName').focus();
            return false;
        }
        if ($.trim($('#phoneNo1').val()) === '') {
            $('#phoneNo1').notify('Phone No.1 is Required Field', 'error', {autoHideDelay: 15000});
            $('#phoneNo1').focus();
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
        var data = new FormData(document.getElementById('saveClinicForm'));
//        Metronic.blockUI({
//            target: '#blockui_sample_1_portlet_body',
//            boxed: true,
//            message: 'Saving...'
//        });

        $.ajax({
            url: 'setup.htm?action=saveClinic',
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (data) {
            if (data) {
                //   Metronic.unblockUI();
                if (data.result === 'save_success') {
                    $.bootstrapGrowl("Clinic/Hospital Data saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#addClinic').modal('hide');
                    displayData();
                } else {
                    $.bootstrapGrowl("Error in saving Clinic/Hospital. Please try again later.", {
                        ele: 'body',
                        type: 'error',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#addClinic').modal('hide');
                }
            }
        });
        return false;
    }
    function uploadClinicAttachements(id) {
        $('#clinicId').val(id);
        $('#addAttachements').modal('show');
    }
    function displayAttachements(id) {
        var $tbl = $('<table class="table table-striped table-bordered table-hover" width="100%">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="10%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Attachment'),
                $('<th class="center" width="55%">').html('Description'),
                $('<th class="center" width="5%">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getClinicActtachementsById', {clinicId: id},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td align="center">').html(eval(i + 1)),
                                    $('<td>').html('<a href="upload/clinic/attachments/' + list[i].TW_CLINIC_ID + '/' + list[i].FILE_NME + '" target="_blank"><img src="images/attach-icon.png" alt="Att" width="20" height="20"></a>'),
                                    $('<td>').html(list[i].FILE_DESC),
                                    $('<td align="center">').html('<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteClinicAttachement(\'' + list[i].TW_CLINIC_ATTACHMENT_ID + '\',\'' + list[i].TW_CLINIC_ID + '\');"></i>')
                                    ));
                        }
                        $('#dvTable').html('');
                        $('#dvTable').append($tbl);
                    } else {
                        $('#dvTable').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td  colspan="4">').html('<b>No data found.</b>')
                                ));
                        $('#dvTable').append($tbl);
                    }
                    $('#viewAttachmentsDialog').modal('show');
                }, 'json');
    }
    function deleteClinicAttachement(id, clinicId) {
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
                    $.post('setup.htm?action=deleteClinicAttachement', {id: id}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl("Record deleted successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            $('#viewAttachmentsDialog').modal('hide');
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
        $('#phoneNo1').val('');
        $('#phoneNo2').val('');
        $('#clinicAddress').val('');
        $('#aboutUs').val('');
        $('input:text[name="discountPerc"]').val('');
        $('#profileImageRow').show();
        $('#addClinic').modal('show');
    }
    function editRow(id) {
        $('#clinicId').val(id);
        $.get('setup.htm?action=getClinicById', {clinicId: id},
                function (obj) {
                    $('#clinicName').val(obj.CLINIC_NME);
                    $('#mapQuardinates').val(obj.MAP_COORDINATES);
                    $('#phoneNo1').val(obj.PHONE_NO);
                    $('#editCity').val(obj.CITY_ID);
                    $('#editArea').val(obj.CITY_AREA_ID);
                    $('#clinicAddress').val(obj.ADDRESS);
                    $('#phoneNo2').val(obj.PHONE_NO2);
                    $('#aboutUs').val(obj.ADDRESS);
                    $('#videoUrl').val(obj.VIDEO_URL);
                    $('#profileImageRow').hide();
                    getCityArea();
                    $.get('setup.htm?action=getClinicDiscounts', {clinicId: id}, function (list) {
                        if (list.length > 0) {
                            for (var i = 0; i < list.length; i++) {
                                $('#discountPerc_' + list[i].TW_DISCOUNT_CATEGORY_ID).val(list[i].DISCOUNT_RATIO);
                            }
                        } else {
                            $('input:text[name="discountPerc"]').val('');
                        }
                    }, 'json');
                    $('#addClinic').modal('show');
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

    function updateClinicType(id, status) {
        var msgStr = '';
        var changeStatus = '';
        if (status === 'Y') {
            msgStr = 'Do you want to change status from Hospital to Clinic?';
            changeStatus = 'N';
        } else {
            msgStr = 'Do you want to change status from Clinic to Hospital?';
            changeStatus = 'Y';
        }
        bootbox.confirm({
            message: msgStr,
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
                    $.post('setup.htm?action=updateClinicStatus', {id: id, status: changeStatus}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl("Record updated successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            displayData();
                        } else {
                            $.bootstrapGrowl("Record can not be updated.", {
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
<input type="hidden" name="editCity" id="editCity" >
<input type="hidden" name="editArea" id="editArea" >
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">

    </div>
</div>
<div class="modal fade" id="addClinic">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Clinic / Hospital</h3>

            </div>
            <div class="modal-body" id="blockui_sample_1_portlet_body">
                <form action="#" role="form" method="post" id="saveClinicForm" enctype="multipart/form-data" onsubmit="return false;" >
                    <input type="hidden" id="clinicId" value="" name="clinicId">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Clinic/Hospital Name *</label>
                                <div>
                                    <input type="text" class="form-control" id="clinicName" name="clinicName" placeholder="Clinic/Hospital Name" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row" id="profileImageRow">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Profile Picture</label>
                                <input type="file" class="form-control" id="profileImage" name="profileImage" placeholder="Profile Picture" >
                            </div>
                        </div>
                    </div>
                    <div class="row" >
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Profile Video URL</label>
                                <input type="text" class="form-control" id="videoUrl" name="videoUrl" placeholder="Profile Video URL" >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Phone No. 1</label>
                                <div>
                                    <input type="text"   class="form-control" id="phoneNo1" name="phoneNo1" placeholder="0300xxxxxxx" onkeyup="onlyInteger(this);" maxlength="16" >
                                </div>
                            </div>
                        </div> 
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Phone No. 2</label>
                                <div>
                                    <input type="text"   class="form-control" id="phoneNo2" name="phoneNo2" placeholder="0300xxxxxxx" onkeyup="onlyInteger(this);" maxlength="16" >
                                </div>
                            </div>
                        </div> 
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>City</label>
                                <div>
                                    <select class="form-control select2-default" id="cityId" name="cityId">
                                        <c:forEach items="${requestScope.refData.cities}" var="obj">
                                            <option value="${obj.CITY_ID}"
                                                    <c:if test="${obj.CITY_NME=='Lahore'}">
                                                        selected="selected"
                                                    </c:if>
                                                    >${obj.CITY_NME}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div> 
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Area</label>
                                <div>
                                    <select class="form-control select2-default" id="areaId" name="areaId">
                                    </select>
                                </div>
                            </div>
                        </div> 
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Map Coordinates</label>
                                <div>
                                    <input type="text" class="form-control" id="mapQuardinates" name="mapQuardinates" placeholder="Map Coordinates" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>About Clinic/Hospital</label>
                                <textarea class="form-control" id="aboutUs" rows="2" cols="30" name="aboutUs"></textarea>
                            </div>
                        </div>                        
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Address</label>
                                <textarea class="form-control" id="clinicAddress" rows="2" cols="30" name="clinicAddress"></textarea>
                            </div>
                        </div>                        
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <h3>Discount</h3>
                            <table class="table table-striped table-condensed table-bordered" id="discountTable">
                                <thead>
                                    <tr>
                                        <td>Sr. #</td>
                                        <td>Category</td>
                                        <td>% of Discount</td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.refData.discounts}" var="obj" varStatus="i">
                                        <tr>
                                            <td>${i.count}</td>
                                            <td>${obj.CATEGORY_NME}</td>
                                            <td>
                                                <input type="hidden" name="discountPercId" value="${obj.TW_DISCOUNT_CATEGORY_ID}">
                                                <input type="text" class="form-control input-sm" name="discountPerc" id="discountPerc_${obj.TW_DISCOUNT_CATEGORY_ID}" onkeyup="onlyDouble(this);">
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
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
                    Clinics / Hospital
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>City</label>
                                <div>
                                    <select class="form-control select2-default" id="searchCity" name="searchCity" onchange="displayData();">
                                        <c:forEach items="${requestScope.refData.cities}" var="obj">
                                            <option value="${obj.CITY_ID}"
                                                    <c:if test="${obj.CITY_NME=='Lahore'}">
                                                        selected="selected"
                                                    </c:if>
                                                    >${obj.CITY_NME}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Account Status</label>
                                <select id="accountInd" class="form-control">
                                    <option value="Y">Active</option>
                                    <option value="N">InActive</option>
                                    <option value="">All</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-5 text-right" style="padding-top: 23px;">
                            <button type="button" class="btn green" onclick="displayData();"><i class="fa fa-search"></i> Search Clinic</button>
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
    <div class="modal fade" id="viewAttachmentsDialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h3 class="modal-title">View Attachments</h3>
                </div>
                <div class="modal-body">
                    <div class="portlet-body">
                        <form action="#" role="form" method="post" >
                            <input type="hidden" name="viewAttachmentsDocotId" id="viewAttachmentsDocotId" value="">
                            <div class="row">
                                <div class="col-md-12">
                                    <div id="dvTable">
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="addAttachements">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h3 class="modal-title">Attachment</h3>
                </div>
                <div class="modal-body">
                    <form action="#" id="doctorAttachment" role="form" method="post" >
                        <div class="portlet box green">
                            <div class="portlet-title">
                                <div class="caption">
                                    Upload Attachment
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group" >
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="form-group">
                                                        <label>&nbsp;</label>
                                                        <div>
                                                            <input id="filebutton" name="file" class="input-file" type="file">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="form-group">
                                                        <label>Description</label>
                                                        <div>
                                                            <input id="attachDescription" class="form-control" name="attachDescription" type="text">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="saveAttachment();">Save</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <%@include file="../footer.jsp"%>

