<%-- 
    Document   : addPharmaCompany
    Created on : Jan 19, 2018, 4:52:26 PM
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
                $('<th class="center" width="30%">').html('Company Name'),
                $('<th class="center" width="20%">').html('Url'),
                $('<th class="center" width="15%" colspan="3">').html('&nbsp;')
                )));
        $.get('performa.htm?action=getPharmacyCompany', {},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_PHARMACY_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_PHARMACY_ID + '\');"></i>';
                            var featuredHtm = '<i class="fa ' + (list[i].FEATURED_IND === 'Y' ? 'fa-star' : 'fa-star-o') + '" aria-hidden="true" title="Click to view Featured" style="cursor: pointer;" onclick="featuredPharmacyCompany(\'' + list[i].TW_PHARMACY_ID + '\',\'' + list[i].FEATURED_IND + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].PHARMACY_NME),
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
    function featuredPharmacyCompany(id, status) {
        var title = "", msgHead = "";
        if (status === 'N') {
            title = "Do you want to featured this Pharmacy?";
            status = "Y";
            msgHead = "Featured";
        } else {
            title = "Do you want to Un-featured this Pharmacy?";
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
                    $.post('setup.htm?action=featuredPharmacyCompany', {id: id, status: status}, function (res) {
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
                            $.bootstrapGrowl('Pharmacy can not be ' + msgHead, {
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
    function addPharmaCompany() {
        $('#imageOld').html('');
        $('#pharmaCompanyId').val('');
        $('#companyUrl').val('');
        $('#companyName').val('');
        $('#addPharmaCompany').modal('show');
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
                    $.post('performa.htm?action=deletePharmacyCompany', {id: id}, function (res) {
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
        $('#pharmaCompanyId').val(id);
        $.get('performa.htm?action=getPharmacyCompanyById', {id: id},
                function (obj) {
                    $('#companyName').val(obj.PHARMACY_NME);
                    $('#companyUrl').val(obj.WEB_URL);
                    var picPath = "";
                    if (obj.LOGO_IMAGE === '') {
                        picPath = 'images/nophoto.png';
                    } else {
                        picPath = 'upload/pharmacy/logo/' + obj.TW_PHARMACY_ID + '/' + obj.LOGO_IMAGE;
                    }
                    $('#imageOld').append('<img src="' + picPath + '" width="150px" height="150px" />');
                    $('#addPharmaCompany').modal('show');
                }, 'json');
    }
    function saveCompany() {
        if ($.trim($('#companyName').val()) === '') {
            $('#companyName').notify('Company Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#companyName').focus();
            return false;
        }
        var data = new FormData(document.getElementById('pharmaCompany'));
        $.ajax({
            url: 'performa.htm?action=savePharmacyCompany',
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (data) {
            if (data) {
                if (data.result === 'save_success') {
                    $.bootstrapGrowl("Company Save successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    displayData();
                    $('#addPharmaCompany').modal('hide');

                } else {
                    $.bootstrapGrowl("Error in Saving Company.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#addPharmaCompany').modal('hide');
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
        <h1>Pharmacy Company</h1>
    </div>
</div>
<div class="modal fade" id="addPharmaCompany">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Pharmacy Company</h3>
            </div>
            <div class="modal-body">
                <form action="#" role="form" id="pharmaCompany" method="post" >
                    <input type="hidden" name="pharmaId" id="pharmaCompanyId" value="">
                    <div class="row">                    
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Company Name *</label>
                                <input type="text" class="form-control" id="companyName" name="companyName">
                            </div>
                        </div>
                    </div>
                    <div class="row"> 
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Url</label>
                                <input type="text" class="form-control" id="companyUrl" name="webUrl">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Company Logo</label>
                                <input type="file" class="form-control" id="companyLogo" name="logoFile">
                            </div>
                            <div id="imageOld"></div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveCompany();">Save</button>
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
                        Pharmacy Company
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px; " >
                            <button type="button" class="btn blue" onclick="addPharmaCompany();"><i class="fa fa-plus-circle"></i> Add Pharmacy Company</button>
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