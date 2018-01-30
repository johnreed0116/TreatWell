<%-- 
    Document   : addProduct
    Created on : Oct 13, 2017, 3:53:05 PM
    Author     : Cori 5
--%>

<%@include file="../header.jsp"%>
<script language='javascript' src="js/md5.js" type="text/javascript"></script>
<script>
    $(function () {
        $('#selectDiseases').select2({
            placeholder: "Select an option",
            allowClear: true
        });
        displayData();
    });
    function displayData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="5%">').html('Sr. #'),
                $('<th class="center" width="30%">').html('Company Name'),
                $('<th class="center" width="20%">').html('Product Name'),
                $('<th class="center" width="20%">').html('Product Type'),
                $('<th class="center" width="20%">').html('Generic Name'),
                $('<th class="center" width="15%" colspan="2">').html('&nbsp;')
                )));
        $.get('setup.htm?action=getPharmaProducts', {pharmaProductList: $('#pharmaCompanyId').val()},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var editHtm = '<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_PHARMA_PRODUCT_ID + '\');"></i>';
                            var delHtm = '<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_PHARMA_PRODUCT_ID + '\');"></i>';
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
                                    $('<td>').html(list[i].PRODUCT_NME),
                                    $('<td>').html(list[i].PRODUCT_TYPE),
                                    $('<td>').html(list[i].GENERIC_NME),
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

</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Product Registration</h1>
    </div>
</div>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="modal fade" id="addProduct">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Product</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="productId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Product Type</label>
                                <select id="productType" class="form-control">
                                    <option value="TABLET">TABLET</option>
                                    <option value="CAPSULE">CAPSULE</option>
                                    <option value="SYRUP">SYRUP</option>
                                    <option value="INJECTION">INJECTION</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Product Name</label>
                                <div>
                                    <input type="text" class="form-control" id="productName" placeholder="Product Name" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Product Generic Name</label>
                                <div>
                                    <input type="text" class="form-control" id="productGenericName" placeholder="Product Generic Name" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Recommendations For Diseases</label>
                                <select id="selectDiseases"  class=" select2_category form-control" name="recommendationDiseases[]" multiple="multiple">
                                    <option value="">Select Recommended diseases</option>
                                    <c:forEach items="${requestScope.refData.diseases}" var="obj">
                                        <option value="${obj.TW_DISEASE_ID}">${obj.TITLE}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>                  
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Additional Features</label>
                                <textarea class="form-control" id="productFeatures" rows="2" cols="30"></textarea>
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
        <div class="portlet-body">
            <form name="doctorform" action="#" role="form" onsubmit="return false;" method="post">
                <div class="portlet box green">
                    <div class="portlet-title">
                        <div class="caption">
                            Products
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label>Pharmaceutical Company</label>
                                    <select id="pharmaCompanyId" class="form-control" onchange="displayData();">
                                        <c:forEach items="${requestScope.refData.companies}" var="obj">
                                            <option value="${obj.TW_PHARMACEUTICAL_ID}">${obj.COMPANY_NME}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4" style="padding-top: 23px;">
                                <c:if test="${requestScope.refData.CAN_ADD=='Y'}">
                                    <button type="button" class="btn blue" onclick="addProductDialog();"><i class="fa fa-plus-circle"></i> New Product</button>
                                </c:if>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
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
<script>

    function saveData() {

        if ($.trim($('#productName').val()) === '') {
            $('#productName').notify('Product Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#productName').focus();
            return false;
        }
        if ($.trim($('#productGenericName').val()) === '') {
            $('#productGenericName').notify('Product Generic Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#productGenericName').focus();
            return false;
        }
        console.log($('#selectDiseases').val());
        var obj = {
            productId: $('#productId').val(),
            productName: $('#productName').val(),
            productType: $('#productType').val(),
            productGenericName: $('#productGenericName').val(),
            'selectDiseasesArr[]': $('#selectDiseases').val(),
            productFeatures: $('#productFeatures').val(),
            pharmaCompanyId: $('#pharmaCompanyId').val()
        };
        $.post('setup.htm?action=saveProduct', obj, function (obj) {
            if (obj.result === 'save_success') {
                $.bootstrapGrowl("Product saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('textarea').val('');
                $('productId').val('');
                $('#addProduct').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving product. Please try again later.", {
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


    function addProductDialog() {
        $('#productId').val('');
        $('#productName').val('');
        $('#productGenericName').val('');

        $('#productFeatures').val('');
        $('#addProduct').modal('show');
    }
    function editRow(id) {
        $('#productId').val(id);
        $.get('setup.htm?action=getPharmaProductById', {productId: id},
                function (obj) {
                    $('#productName').val(obj.PRODUCT_NME);
                    $('#productType').val(obj.PRODUCT_TYPE);
                    $('#productGenericName').val(obj.GENERIC_NME);
                    var diseases = obj.DISEASES;
                    if (diseases !== null && diseases.length > 0) {
                        var arr = diseases.split(',');
                        $('#selectDiseases').val(arr);
                        $('#selectDiseases').trigger('change');
                    }
                    $('#productFeatures').val(obj.REMARKS);
                    $('#addProduct').modal('show');
                }, 'json');
    }

</script>

<%@include file="../footer.jsp"%>