<%-- 
    Document   : viewQuestionCategories
    Created on : Feb 22, 2018, 2:26:28 PM
    Author     : farazahmad
--%>
<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('#specialityId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        displayData();
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="10%">').html('Sr. #'),
                $('<th class="center" width="70%">').html('Category'),
                $('<th class="center" width="10%">').html('Attachment'),
                $('<th class="center" width="10%" colspan="2">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getQuestionCategories', {specialityId: $('#specialityId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_QUESTION_CATEGORY_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_QUESTION_CATEGORY_ID + '\');"></i>';
                            if ($('#can_edit').val() !== 'Y') {
                                editHtm = '&nbsp;';
                            }
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].CATEGORY_NME),
                                    $('<td>').html(list[i].FILE_NME !== '' ? '<a href="upload/examCategory/' + list[i].TW_QUESTION_CATEGORY_ID + '/' + list[i].FILE_NME + '" target="_blank">View</a>' : '&nbsp;'),
                                    $('<td align="center">').html(editHtm),
                                    $('<td  align="center">').html(delHtm)
                                    ));
                        }
                        $('#displayDiv').html('');
                        $('#displayDiv').append($tbl);
                    } else {
                        $('#displayDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td  colspan="5">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                    }
                }, 'json');
        return false;
    }
    function addQuestionCategory() {
        $('#questionCategoryId').val('');
        $('#categoryName').val('');
        $('#changeImageRow').hide();
        $('#addQuestionCategoryDialog').modal('show');
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
                    $.post('setup.htm?action=deleteQuestionCategory', {id: id}, function (res) {
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
        $('#questionCategoryId').val(id);
        $.get('setup.htm?action=getQuestionCategoryById', {id: id},
                function (obj) {
                    $('#categoryName').val(obj.CATEGORY_NME);
                    $('#changeImageRow').show();
                    $('#addQuestionCategoryDialog').modal('show');
                }, 'json');
    }
    function saveQuestion() {
        if ($.trim($('#categoryName').val()) === '') {
            $('#categoryName').notify('Category Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#categoryName').focus();
            return false;
        }
        ///////////
        var isChangeImage = 'N';
        if ($('#isChangeImage').is(':checked')) {
            isChangeImage = 'Y';
        }
        var data = new FormData(document.getElementById('examinationQuestionForm'));
        data.append('specialityId', $('#specialityId').val());
        data.append('canChangeImage', isChangeImage);
        $.ajax({
            url: "setup.htm?action=saveQuestionCategories",
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (data) {
            if (data) {
                if (data.result === 'save_success') {
                    $.bootstrapGrowl("Category saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    $('#categoryAttachment').val('');
                    $('#addQuestionCategoryDialog').modal('hide');
                    displayData();
                } else {
                    $.bootstrapGrowl("Error in Saving Category. Please try again.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
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
        <h1>Question Categories</h1>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="addQuestionCategoryDialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Category</h3>
            </div>
            <div class="modal-body">
                <form action="#" role="form" id="examinationQuestionForm" method="post" enctype="multipart/form-data" >
                    <input type="hidden" name="questionCategoryId" id="questionCategoryId" value="">
                    <div class="row">                    
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Category *</label>
                                <input type="text" class="form-control" id="categoryName" name="categoryName">
                            </div>
                        </div>
                    </div>
                    <div class="row">                    
                        <div class="col-md-12" id="changeImageRow">
                            <input type="checkbox" value="Y" id="isChangeImage"> Remove Image
                        </div>
                    </div>
                    <div class="row">                    
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="categoryAttachment">Attachment</label>
                                <input type="file" class="form-control" name="categoryAttachment" id="categoryAttachment">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveQuestion();">Save</button>
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
                        Question Categories
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Medical Speciality</label>
                                <select id="specialityId" class="form-control" onchange="displayData();">
                                    <c:forEach items="${requestScope.refData.speciality}" var="obj">
                                        <option value="${obj.TW_MEDICAL_SPECIALITY_ID}">${obj.TITLE}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px; " >
                            <button type="button" class="btn blue" onclick="addQuestionCategory();"><i class="fa fa-plus-circle"></i> Add Category</button>
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
