<%-- 
    Document   : addDoctorArticle
    Created on : Mar 24, 2018, 5:34:02 PM
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
                $('<th class="center" width="45%">').html('Article Title'),
                $('<th class="center" width="45%">').html('Description'),
                $('<th class="center" width="5%" colspan="3">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getDoctorArticle', {},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var addHtm = '<i class="fa fa-plus" aria-hidden="true" title="Click to Add" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_DOCTOR_ARTICLE_ID + '\');"></i>';
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_DOCTOR_ARTICLE_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_DOCTOR_ARTICLE_ID + '\');"></i>';
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
                                    $('<td>').html(list[i].DESCRIPTION),
                                    $('<td align="center">').html(addHtm),
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
    function addDoctorArticle() {
        $('#doctorArticleId').val('');
        $('#title').val('');
        $('#description').val('');
        $('#addDoctorArticleDialog').modal('show');
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
                    $.post('setup.htm?action=deleteDoctorArticle', {id: id}, function (res) {
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
        $('#doctorArticleId').val(id);
        $.get('setup.htm?action=getDoctorArticleById', {id: id},
                function (obj) {
                    $('#title').val(obj.TITLE);
                    $('#description').val(obj.DESCRIPTION);
                    $('#addDoctorArticleDialog').modal('show');
                }, 'json');
    }
    function saveDoctorArticle() {
        if ($.trim($('#title').val()) === '') {
            $('#title').notify('Article Title is Required Field', 'error', {autoHideDelay: 15000});
            $('#title').focus();
            return false;
        }
        if ($.trim($('#description').val()) === '') {
            $('#description').notify('Description is Required Field', 'error', {autoHideDelay: 15000});
            $('#description').focus();
            return false;
        }
        $.post('setup.htm?action=saveDoctorArticle', {
            title: $('#title').val(), description: $('#description').val(),
            doctorArticleId: $('#doctorArticleId').val()}, function (res) {
            if (res) {
                if (res.result === 'save_success') {
                    $.bootstrapGrowl("Article saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    $('#addDoctorArticleDialog').modal('hide');
                    displayData();
                } else {
                    $.bootstrapGrowl("Error in Saving Article. Please try again.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                }
            }
        }, 'json');
    }
</script>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Add Article</h1>
    </div>
</div>
<div class="modal fade " id="addDoctorArticleDialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Article</h3>
            </div>
            <div class="modal-body">
                <form action="#" role="form" id="vaccinationForm" method="post" >
                    <input type="hidden" name="doctorArticleId" id="doctorArticleId" value="">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Title*</label>
                                <input type="text" class="form-control" id="title" placeholder="Article Title" >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Description*</label>
                                <textarea class="form-control" id="description" rows="3" cols="63"></textarea>
                            </div>
                        </div>   
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveDoctorArticle();">Save</button>
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
                        Add Article
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px; " >
                            <button type="button" class="btn blue" onclick="addDoctorArticle();"><i class="fa fa-plus-circle"></i> Add Article</button>
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
