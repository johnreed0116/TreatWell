<%-- 
    Document   : viewExaminationQuestion
    Created on : Feb 9, 2018, 4:32:16 PM
    Author     : Ali Zaidi
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('#specialityId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#categoryId').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        displayCategories();
    });
    function displayCategories() {
        $('#categoryId').find('option').remove();
        $('#toCategoryId').find('option').remove();
        $.get('setup.htm?action=getQuestionCategories', {specialityId: $('#specialityId').val()}, function (data) {
            if (data !== null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $('<option />', {value: data[i].TW_QUESTION_CATEGORY_ID, text: data[i].CATEGORY_NME}).appendTo($('#categoryId'));
                    $('<option />', {value: data[i].TW_QUESTION_CATEGORY_ID, text: data[i].CATEGORY_NME}).appendTo($('#toCategoryId'));

                }
            } else {
                $('<option />', {value: '', text: 'No category defined.'}).appendTo($('#categoryId'));
                $('<option />', {value: '', text: 'No category defined.'}).appendTo($('#toCategoryId'));
            }
            $('#categoryId').trigger('change');
            displayData();
        }, 'json');
    }
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="85%">').html('Question'),
                $('<th class="center" width="10%" colspan="3">').html('&nbsp;')
                )));
        if ($('#categoryId').val() !== '') {
            $.get('setup.htm?action=getExaminationQuestion', {specialityId: $('#specialityId').val(), categoryId: $('#categoryId').val()},
                    function (list) {
                        if (list !== null && list.length > 0) {
                            $tbl.append($('<tbody>'));
                            for (var i = 0; i < list.length; i++) {
                                var addHtm = '<i class="fa fa-plus" aria-hidden="true" title="Click to Add" style="cursor: pointer;" onclick="addAnswer(\'' + list[i].TW_QUESTION_MASTER_ID + '\');"></i>';
                                var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_QUESTION_MASTER_ID + '\');"></i>';
                                var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_QUESTION_MASTER_ID + '\');"></i>';
                                if ($('#can_edit').val() !== 'Y') {
                                    editHtm = '&nbsp;';
                                }
                                if ($('#can_delete').val() !== 'Y') {
                                    delHtm = '&nbsp;';
                                }
                                $tbl.append(
                                        $('<tr>').append(
                                        $('<td  align="center">').html(eval(i + 1)),
                                        $('<td>').html(list[i].QUESTION_TXT),
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
                                    $('<td  colspan="4">').html('<b>No data found.</b>')
                                    ));
                            $('#displayDiv').append($tbl);
                            return false;
                        }
                    }, 'json');
        } else {
            $('#displayDiv').html('');
            $tbl.append(
                    $('<tr>').append(
                    $('<td  colspan="4">').html('<b>No data found.</b>')
                    ));
            $('#displayDiv').append($tbl);
        }
    }
    function displayAnswerData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="90%">').html('Answers'),
                $('<th class="center" width="5%" colspan="3">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getAnswer', {questionMasterId: $('#questionMasterId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteAnsRow(\'' + list[i].TW_QUESTION_DETAIL_ID + '\');"></i>';
                            if ($('#can_delete').val() !== 'Y') {
                                delHtm = '&nbsp;';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].ANSWER_TXT),
                                    $('<td  align="center">').html((list[i].ANSWER_TXT !== 'Others' ? delHtm : '&nbsp;'))
                                    ));
                        }
                        $('#displayAnsDiv').html('');
                        $('#displayAnsDiv').append($tbl);
                        return false;
                    } else {
                        $('#displayAnsDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td  colspan="4">').html('<b>No data found.</b>')
                                ));
                        $('#displayAnsDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }
    function addExaminationQuestion() {
        $('#questionMasterId').val('');
        $('#question').val('');
        $('#addExaminationQuestion').modal('show');
    }
    function addAnswer(id) {
        $('#questionMasterId').val(id);
        $('#answer').val('');
        $('#questionDetailId').val('');
        displayAnswerData();
        $('#addAnswer').modal('show');
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
                    $.post('setup.htm?action=deleteExaminationQuestion', {id: id}, function (res) {
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
    function deleteAnsRow(id) {
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
                    $.post('setup.htm?action=deleteAnswer', {id: id}, function (res) {
                        if (res.result === 'save_success') {
                            $.bootstrapGrowl("Record deleted successfully.", {
                                ele: 'body',
                                type: 'success',
                                offset: {from: 'top', amount: 80},
                                align: 'right',
                                allow_dismiss: true,
                                stackup_spacing: 10
                            });
                            displayAnswerData();
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
        $('#questionMasterId').val(id);
        $.get('setup.htm?action=getExaminationQuestionById', {id: id},
                function (obj) {
                    $('#question').val(obj.QUESTION_TXT);
                    $('#addExaminationQuestion').modal('show');
                }, 'json');
    }
    function saveQuestion() {
        if ($.trim($('#question').val()) === '') {
            $('#question').notify('Question is Required Field', 'error', {autoHideDelay: 15000});
            $('#question').focus();
            return false;
        }
        $.post('setup.htm?action=saveExaminationQuestion', {specialityId: $('#specialityId').val(),
            question: $('#question').val(), questionMasterId: $('#questionMasterId').val(), categoryId: $('#categoryId').val()}, function (res) {
            if (res) {
                if (res.result === 'save_success') {
                    $.bootstrapGrowl("Question Save successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    displayData();
                    $('#addExaminationQuestion').modal('hide');

                } else {
                    $.bootstrapGrowl("Error in Saving Question.", {
                        ele: 'body',
                        type: 'danger',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10
                    });
                    $('#addExaminationQuestion').modal('hide');
                }
            }
        }, 'json');
    }
    function saveAnswer() {
        if ($.trim($('#answer').val()) === '') {
            $('#answer').notify('Answer is Required Field', 'error', {autoHideDelay: 15000});
            $('#answer').focus();
            return false;
        }
        $.post('setup.htm?action=saveAnswer', {
            answer: $('#answer').val(), questionMasterId: $('#questionMasterId').val()}, function (res) {
            if (res) {
                if (res.result === 'save_success') {
                    $.bootstrapGrowl("Answer Save successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                    $('#answer').val('');
                    displayAnswerData();

                } else {
                    $.bootstrapGrowl("Error in Saving Answer.", {
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
    function copyExaminationData() {
        $('#sourceCategoryVal').val($('#categoryId option:selected').text());
        $('#toCategoryId').find('option').remove();
        $.get('setup.htm?action=getQuestionCategories', {specialityId: $('#specialityId').val()}, function (data) {
            if (data !== null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $('<option />', {value: data[i].TW_QUESTION_CATEGORY_ID, text: data[i].CATEGORY_NME}).appendTo($('#toCategoryId'));
                }
            } else {
                $('<option />', {value: '', text: 'No category defined.'}).appendTo($('#toCategoryId'));
            }
            var opt = $('#toCategoryId').find('option');
            $.each(opt, function (i, o) {
                if ($(o).val() === $('#categoryId').val()) {
                    $(o).remove();
                }
            });
            $('#selectSourceDialog').modal('show');
        }, 'json');


    }
    function processCopyExaminationData() {
        if ($('#categoryId').val() !== '' && $('#toCategoryId').val() !== '') {
            $.post('setup.htm?action=copyExaminationQuestions', {fromCategoryId: $('#categoryId').val(),
                toCategoryId: $('#toCategoryId').val(), specialityId: $('#specialityId').val()}, function (res) {
                if (res.result === 'save_success') {
                    $('#selectSourceDialog').modal('hide');
                    $.bootstrapGrowl("Questions copied successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                } else {
                    $.bootstrapGrowl("Error in copying questions to destination.", {
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
</script>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Examination Question</h1>
    </div>
</div>
<div class="modal fade bd-example-modal-lg" id="addExaminationQuestion">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Examination Question</h3>
            </div>
            <div class="modal-body">
                <form action="#" role="form" id="examinationQuestion" method="post" >
                    <input type="hidden" name="questionMasterId" id="questionMasterId" value="">
                    <div class="row">                    
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Question *</label>
                                <input type="text" class="form-control" id="question" name="question">
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
<div class="modal fade bd-example-modal-lg" id="addAnswer">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Answer(s)</h3>
            </div>
            <div class="modal-body">
                <div id="displayAnsDiv">

                </div>
            </div>
            <div class="modal-footer">
                <div class="row">  
                    <div class="col-md-1" style="text-align: left !important;">
                        <label>Answer*</label>
                    </div>
                    <div class="col-md-9">
                        <div class="form-group">
                            <div class="input-group">  
                                <input type="text" class="form-control" id="answer" name="answer" />
                                <span class="input-group-btn">
                                    <button type="button" class="btn btn-primary" onclick="saveAnswer();">Save</button>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<div class="modal fade bd-example-modal-sm" id="selectSourceDialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Copy Examination Question</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label>Source Category</label>
                            <input class="form-control" id="sourceCategoryVal" readonly="">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label>Destination Category</label>
                            <select id="toCategoryId" class="form-control">
                                <option value="">Select Category</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="processCopyExaminationData();">Copy</button>
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
                        Examination Question
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Medical Speciality</label>
                                <select id="specialityId" class="form-control" onchange="displayCategories();">
                                    <c:forEach items="${requestScope.refData.speciality}" var="obj">
                                        <option value="${obj.TW_MEDICAL_SPECIALITY_ID}">${obj.TITLE}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Category</label>
                                <select id="categoryId" class="form-control" onchange="displayData();">
                                    <option value="">Select Category</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 text-left" style="padding-top: 23px; " >
                            <button type="button" class="btn red" onclick="copyExaminationData();"><i class="fa fa-copy"></i> Copy Examination Questions</button>
                        </div>
                        <div class="col-md-6 text-right" style="padding-top: 23px; " >
                            <button type="button" class="btn blue" onclick="addExaminationQuestion();"><i class="fa fa-plus-circle"></i> Add Examination Question</button>
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