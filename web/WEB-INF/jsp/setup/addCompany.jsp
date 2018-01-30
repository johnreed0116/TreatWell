<%-- 
    Document   : addCompanies
    Created on : Nov 7, 2017, 3:46:26 PM
    Author     : Cori 5
--%>

<%@include file="../header.jsp"%>
<script language='javascript' src="js/md5.js" type="text/javascript"></script>
<script>
    $(function () {
        displayData();
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Company Name'),
                $('<th class="center" width="20%">').html('Contact Person'),
                $('<th class="center" width="20%">').html('Mobile No'),
                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getCompanies', {companyName: $('#pharmacyName').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_COMPANY_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_COMPANY_ID + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].COMPANY_NME),
                                    $('<td>').html(list[i].CONTACT_PERSON),
                                    $('<td>').html(list[i].PHONE_NO),
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
        if ($.trim($('#companyName').val()) === '') {
            $('#companyName').notify('Company Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#companyName').focus();
            return false;
        }
        if ($.trim($('#contactPerson').val()) === '') {
            $('#contactPerson').notify('Contact Person is Required Field', 'error', {autoHideDelay: 15000});
            $('#contactPerson').focus();
            return false;
        }
        if ($.trim($('#cellNo').val()) === '') {
            $('#cellNo').notify('Cell No is Required Field', 'error', {autoHideDelay: 15000});
            $('#cellNo').focus();
            return false;
        }

        var obj = {
            companiesId: $('#companyId').val(),
            companyName: $('#companyName').val(),
            contactPerson: $('#contactPerson').val(),
            companyAddress: $('#companyAddress').val(), cellNo: $('#cellNo').val(),
            email: $('#email').val()
       
        };

        $.post('setup.htm?action=saveCompany', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Company Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });

                $('input:text').val('');
                $('#companyId').val('');
                $('textarea').val('');
                $('#addCompany').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Data. Please try again later.", {
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
                    $.post('setup.htm?action=deleteCompany', {id: id}, function (res) {
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

    function addCompanyDialog() {
        $('#companyId').val('');
        $('#companyName').val('');
        $('#contactPerson').val('');
        $('#email').val('');
        $('#cellNo').val('');
        $('#companyAddress').val('');
        $('#addCompany').modal('show');
    }
    function editRow(id) {
        $('#companyId').val(id);
        $.get('setup.htm?action=getCompanyById', {companyId: id},
                function (obj) {
                    $('#companyName').val(obj.COMPANY_NME);
                    $('#contactPerson').val(obj.CONTACT_PERSON);
                    $('#email').val(obj.EMAIL);
                    $('#cellNo').val(obj.PHONE_NO);
                    $('#companyAddress').val(obj.ADDRESS);
                    $('#addCompany').modal('show');

                }, 'json');
    }

</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Add Companies</h1>
    </div>
</div>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="modal fade" id="addCompany">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Company</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="companyId" value="">
                <div class="portlet box green">
                    <div class="portlet-title tabbable-line">
                        <div class="caption">
                            Company Info
                        </div>
                    </div>
                    <div class="portlet-body">      
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Company Name *</label>
                                    <div>
                                        <input type="text" class="form-control" id="companyName" placeholder="Company Name" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Contact Person *</label>
                                    <div>
                                        <input type="text" class="form-control" id="contactPerson" placeholder="Contact Person" >
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Cell No.</label>
                                    <div>
                                        <input type="text"   class="form-control" id="cellNo" placeholder="0300xxxxxxx" onkeyup="onlyInteger(this);" maxlength="16" >
                                    </div>
                                </div>
                            </div> 
                             <div class="col-md-6">
                                <div class="form-group">
                                    <label>Email</label>
                                    <div>
                                        <input type="text" class="form-control" id="email" placeholder="Email">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Company Address</label>
                                    <textarea class="form-control" id="companyAddress" rows="2" cols="30"></textarea>
                                </div>
                            </div>   
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="saveData();">Save</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Registered Companies
                </div>
            </div>
            <div class="portlet-body">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px;">
                            <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                <button type="button" class="btn blue" onclick="addCompanyDialog();"><i class="fa fa-plus-circle"></i> New Company</button>
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

