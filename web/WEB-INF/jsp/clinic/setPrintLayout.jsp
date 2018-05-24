<%-- 
    Document   : setPrintLayout
    Created on : Nov 23, 2017, 7:41:09 PM
    Author     : Cori 5
--%>
<%@include file="../header.jsp"%>
<script>
    $(function () {
        getPrintLayouts();
    });
    function getExtension(filename) {
        var parts = filename.split('.');
        return parts[parts.length - 1];
    }

    function isImage(filename) {
        var ext = getExtension(filename);
        switch (ext.toLowerCase()) {
            case 'jpg':
                return true;
        }
        return false;
    }
    function uploadImages() {
        var data = new FormData(document.getElementById('layoutForm'));
        var headerLogo = $('#headerLogo');
        var footerLogo = $('#footerLogo');
        if (headerLogo.val() !== '' && !isImage(headerLogo.val())) {
            $.bootstrapGrowl("Please select header image in JPG format.", {
                ele: 'body',
                type: 'danger',
                offset: {from: 'top', amount: 80},
                align: 'right',
                allow_dismiss: true,
                stackup_spacing: 10
            });
            headerLogo.val('');
            return false;
        }
        if (footerLogo.val() !== '' && !isImage(footerLogo.val())) {
            $.bootstrapGrowl("Please select bottom image in JPG format.", {
                ele: 'body',
                type: 'danger',
                offset: {from: 'top', amount: 80},
                align: 'right',
                allow_dismiss: true,
                stackup_spacing: 10
            });
            footerLogo.val('');
            return false;
        }
        $.ajax({
            url: 'clinic.htm?action=savePrintLayout',
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (data) {
            if (data.result === 'save_success') {
                $.bootstrapGrowl("Print Layout Data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                getPrintLayouts();
            } else {
                $.bootstrapGrowl("Error in saving Print Layout. Please try again later.", {
                    ele: 'body',
                    type: 'error',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
            }
        });
        return false;
    }

    function getPrintLayouts() {
        $.get('clinic.htm?action=getPrintLayouts', {},
                function (obj) {
                    if (obj !== null) {
                        var path = 'upload/doctor/latterPad/' + $('#doctorId').val() + '/';
                        var headerImage = path + obj.TOP_IMAGE;
                        var bottomImage = path + obj.BOTTOM_IMAGE;
                        $('#headerImageDisplay').attr('src', headerImage);
                        $('#footerImageDisplay').attr('src', bottomImage);
                    }
                }, 'json');
    }
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Print Layout</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-body">
                <input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
                <input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
                <input type="hidden" id="doctorId" value="${requestScope.refData.doctorId}">
                <form action="#" onsubmit="return false;" role="form" method="post" id="layoutForm" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col-md-8">
                            <div class="form-group">
                                <label>Header Image*</label>
                                <input type="file" class="form-control" id="headerLogo" name="headerLogo" placeholder="Select Header Image" >
                            </div>
                        </div>
                        <div class="col-md-4">
                            <figure>
                                <img src="images/blank-wallpaper.jpg" id="headerImageDisplay" alt="no image" class="img-responsive" style="height: 100px;width: 100%;">
                                <figcaption style="text-align: center;">Header Image</figcaption>
                            </figure>
                        </div>
                    </div>
                    <br/><br/>
                    <div class="row">
                        <div class="col-md-8">
                            <div class="form-group">
                                <label>Footer Image*</label>
                                <div>
                                    <input type="file" class="form-control" id="footerLogo" name="footerLogo" placeholder="Select Footer Image" >
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <figure>
                                <img src="images/blank-wallpaper.jpg" id="footerImageDisplay" alt="no image" class="img-responsive" style="height: 100px;width: 100%;">
                                <figcaption style="text-align: center;">Footer Image</figcaption>
                            </figure>
                        </div>
                    </div>
                    <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                        <button type="button" class="btn blue" onclick="uploadImages();"><i class="fa fa-upload"></i> Upload</button>
                    </c:if>

                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="../footer.jsp"%>