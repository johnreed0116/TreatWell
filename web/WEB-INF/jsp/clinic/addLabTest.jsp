<%-- 
    Document   : addLabTest
    Created on : Nov 21, 2017, 4:37:40 PM
    Author     : Cori 5
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
                $('<th class="center" width="40%">').html('Test Name'),
                $('<th class="center" width="40%">').html('Test Group'),
                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('clinic.htm?action=getLabTests', {labTestNameSearch: $('#labTestNameSearch').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_LAB_TEST_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_LAB_TEST_ID + '\');"></i>';
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
                                    $('<td>').html(list[i].TEST_GROUP),
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
        if ($.trim($('#labTestName').val()) === '') {
            $('#labTestName').notify('Lab Test Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#labTestName').focus();
            return false;
        }
        if ($.trim($('#testGroupId').val()) === '') {
            $('#testGroupId').notify('Test Group is Required Field', 'error', {autoHideDelay: 15000});
            $('#testGroupId').focus();
            return false;
        }

        var obj = {
            labTestId: $('#labTestId').val(),
            labTestName: $('#labTestName').val(),
            testGroupId: $('#testGroupId').val()
        };
        $.post('clinic.htm?action=saveLabTest', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Lab Test Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#labTestId').val('');
                $('#addLabTest').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving Lab Test. Please try again later.", {
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
                    $.post('clinic.htm?action=deleteLabTest', {id: id}, function (res) {
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


    function addLabTestDialog() {

        $('#labTestId').val('');
        $('#labTestName').val('');
        $('#addLabTest').modal('show');
    }
    function editRow(id) {
        $('#labTestId').val(id);
        $.get('clinic.htm?action=getLabtestById', {labTestId: id},
                function (obj) {
                    $('#labTestName').val(obj.TITLE);
                    $('#testGroupId').val(obj.TW_TEST_GROUP_ID);
                    $('#addLabTest').modal('show');
                }, 'json');
    }

</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Lab Tests </h1>
    </div>
</div>
<div class="modal fade" id="addLabTest">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Lab Test</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="labTestId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Lab Test Name *</label>
                                <div>
                                    <input type="text" class="form-control" id="labTestName" placeholder="Lab Test Name" >
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Test Group *</label>
                                <div>
                                    <select id="testGroupId" name="testGroupId" class="form-control" data-placeholder="Choose a Group">
                                        <c:forEach items="${requestScope.refData.testGroups}" var="obj">
                                            <option value="${obj.TW_TEST_GROUP_ID}">${obj.TITLE}</option>
                                        </c:forEach>
                                    </select>
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
                    Lab Test
                </div>
            </div>
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-8">
                            <div class="form-group">
                                <label>Lab Test Name</label>
                                <div>
                                    <input type="text" class="form-control" id="labTestNameSearch" placeholder="Lab Test Name" onchange="displayData(this.value);">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2 text-right" style="padding-top: 23px; margin-bottom: 23px; ">
                            <button type="button" class="btn green" onclick="displayData();"><i class="fa fa-search"></i> Search Test</button>
                        </div>
                        <div class="col-md-2" style="padding-top: 23px;">
                            <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                <button type="button" class="btn blue" onclick="addLabTestDialog();"><i class="fa fa-plus-circle"></i> New Test</button>
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




