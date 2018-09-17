<%-- 
    Document   : addDoctorArticle
    Created on : Mar 24, 2018, 5:34:02 PM
    Author     : Ali Zaidi
--%>

<%@include file="../header.jsp"%>
<link href="assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css" />
<script src="assets/global/plugins/bootstrap-summernote/summernote.js" type="text/javascript"></script>
<script>
    $(function () {
         $('#description').summernote();
        $('#fileType').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        $('#fileType').change(function () {
            if ($(this).val() === 'A') {
                $('#file').attr('accept', 'audio/*');
            } else {
                $('#file').attr('accept', 'video/*');
            }
        });
        displayData();
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="75%">').html('Article Title'),
                $('<th class="center" width="15%">').html('Date'),
                $('<th class="center" width="5%" colspan="4">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getDoctorArticle', {},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var DtlHtm = '<i class="fa fa-sticky-note-o" aria-hidden="true" title="Click to Add (Audio, Video)" style="cursor: pointer;" value=\'' + list[i].DESCRIPTION + '\' onclick="showDetails(this);"></i>';
                            var addHtm = '<i class="fa fa-plus" aria-hidden="true" title="Click to Add (Audio, Video)" style="cursor: pointer;" onclick="addFiles(\'' + list[i].TW_DOCTOR_ARTICLE_ID + '\');"></i>';
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
                                    $('<td>').html(list[i].PREDATE),
                                    $('<td align="center">').html(DtlHtm),
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
        $('#description').code("");
        $('#addDoctorArticleDialog').modal('show');
    }
    function showDetails (param) {
        $('#dataDiv').html($(param).attr("value"));
        $('#descDialog').modal('show');
        
    }
    function addFiles(id) {
        $('#doctorArticleId').val(id);
        $('#progress-wrp').hide();
        $('#progress-wrp .progress-bar').text('0%');
        $('#addAttachmentDialog').modal('show');
    }
    function saveDoctorArticleAttachments() {
        var data = new FormData(document.getElementById('attachmentForm'));
        data.append('doctorArticleId', $('#doctorArticleId').val());
        $('#progress-wrp').show();
        $.ajax({
            url: "setup.htm?action=saveDoctorArticleAttachment",
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false, // tell jQuery not to set contentType
            xhr: function () {
                var myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) {
                    myXhr.upload.addEventListener('progress', function (event) {
                        var percent = 0;
                        var position = event.loaded || event.position;
                        var total = event.total;
                        var progress_bar_id = "#progress-wrp";
                        if (event.lengthComputable) {
                            percent = Math.ceil(position / total * 100);
                        }
                        // update progressbars classes so it fits your code
                        $(progress_bar_id + " .progress-bar").css("width", +percent + "%");
                        $(progress_bar_id + " .progress-bar").text(percent + "%");
                    }, false);
                }
                return myXhr;
            },
            async: true,
            timeout: 60000

        }).done(function (data) {
            if (data) {
                if (data.result === 'save_success') {
                    $.bootstrapGrowl("Attachment saved successfully.", {
                        ele: 'body',
                        type: 'success',
                        offset: {from: 'top', amount: 80},
                        align: 'right',
                        allow_dismiss: true,
                        stackup_spacing: 10

                    });
                } else {
                    $.bootstrapGrowl("Error in saving Attachment. please try again.", {
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
                    $('#description').code(obj.DESCRIPTION);
                    $('#description').summernote({focus: true});
                    $('#addDoctorArticleDialog').modal('show');
                }, 'json');
    }
    function saveDoctorArticle() {
        if ($.trim($('#title').val()) === '') {
            $('#title').notify('Article Title is Required Field', 'error', {autoHideDelay: 15000});
            $('#title').focus();
            return false;
        }
        if ($.trim($('#description').code()) === '') {
            $('#description').notify('Description is Required Field', 'error', {autoHideDelay: 15000});
            $('#description').focus();
            return false;
        }
        $.post('setup.htm?action=saveDoctorArticle', {
            title: $('#title').val(), description: $('#description').code(),
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
                <form action="#" role="form" id="dataForm" method="post" >
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
                                <div id="description"></div>
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
<div class="modal fade" id="addAttachmentDialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Files</h3>
            </div>
            <div class="modal-body">
                <form action="#" role="form" id="attachmentForm" method="post" >
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>File Type*</label>
                                <select name="fileType" id="fileType" class="form-control">
                                    <option value="V">Video</option>
                                    <option value="A">Audio</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <label>Choose a File*</label>
                                <input type="file" name="file" class="form-control" id="file" accept="video/*" >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="progress" id="progress-wrp" style="height: 20px !important;">
                                <div class="progress-bar progress-bar-striped" role="progressbar " style="width: 0%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">0%</div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveDoctorArticleAttachments();">Save</button>
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
<div class="modal fade" id="descDialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Description</h3>
            </div>
            <div class="modal-body" id="dataDiv">
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<%@include file="../footer.jsp"%>
