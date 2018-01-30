<%-- 
    Document   : viewHealthCards
    Created on : Oct 28, 2017, 11:51:35 AM
    Author     : farazahmad
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
                $('<th class="center" width="25%">').html('Card Name'),
                $('<th class="center" width="20%">').html('Available For'),
                $('<th class="center" width="10%">').html('Price'),
                $('<th class="center" width="10%">').html('No. of Visits'),
                $('<th class="center" width="10%">').html('Doctor Discount'),
                $('<th class="center" width="10%">').html('Product Discount'),
                $('<th class="center" width="10%" colspan="2">').html('&nbsp;')
                )));
        $.get('performa.htm?action=getHealthCards', {},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html(eval(i + 1)),
                                    $('<td>').html(list[i].CARD_NME),
                                    $('<td>').html(list[i].AVAILABLE_FOR),
                                    $('<td>').html(list[i].PRICE),
                                    $('<td>').html(list[i].VISITS_ALLOWED),
                                    $('<td>').html(list[i].DOCTOR_DISC),
                                    $('<td>').html(list[i].PRODUCT_DISC),
                                    $('<td align="center">').html('<i class="fa fa-pencil-square-o" aria-hidden="true" title="Click to Edit" style="cursor: pointer;" onclick="editRow(\'' + list[i].TW_HEALTH_CARD_ID + '\');"></i>'),
                                    $('<td  align="center">').html('<i class="fa fa-trash-o" aria-hidden="true" title="Click to Delete" style="cursor: pointer;" onclick="deleteRow(\'' + list[i].TW_HEALTH_CARD_ID + '\');"></i>')
                                    ));
                        }
                        $('#displayDiv').html('');
                        $('#displayDiv').append($tbl);
                        return false;
                    } else {
                        $('#displayDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td  colspan="9">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                        return false;
                    }
                }, 'json');
    }

    function saveData() {
        if ($.trim($('#cardName').val()) === '') {
            $('#cardName').notify('Card Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#cardName').focus();
            return false;
        }
        if ($.trim($('#salePrice').val()) === '') {
            $('#salePrice').notify('Sale price is Required Field', 'error', {autoHideDelay: 15000});
            $('#salePrice').focus();
            return false;
        }
        if ($.trim($('#doctorsDiscount').val()) === '') {
            $('#doctorsDiscount').notify('Doctors discount is Required Field', 'error', {autoHideDelay: 15000});
            $('#doctorsDiscount').focus();
            return false;
        }
        var obj = {
            healthCardId: $('#healthCardId').val(),
            cardName: $('#cardName').val(),
            salePrice: $('#salePrice').val(),
            doctorsDiscount: $('#doctorsDiscount').val(),
            productsDiscount: $('#productsDiscount').val(),
            noOfVisits: $('#noOfVisits').val(),
            additionalFeatures: $('#additionalFeatures').val(),
            availableFor: $('#availableFor').val()
        };
        $.post('performa.htm?action=saveHealthCard', obj, function (obj) {
            if (obj.msg === 'saved') {
                $.bootstrapGrowl("Health Card data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('input:text').val('');
                $('#healthCardId').val('');
                $('#addClinic').modal('hide');
                displayData();
                return false;
            } else {
                $.bootstrapGrowl("Error in saving card. Please try again later.", {
                    ele: 'body',
                    type: 'gander',
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
                    $.post('performa.htm?action=deleteHealthCard', {id: id}, function (res) {
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
        $('#healthCardId').val('');
        $('#cardName').val('');
        $('#salePrice').val('');
        $('#doctorsDiscount').val('');
        $('#productsDiscount').val('');
        $('#noOfVisits').val('');
        $('#additionalFeatures').val('');
        $('#addClinic').modal('show');
    }

    function editRow(id) {
        $('#healthCardId').val(id);
        $.get('performa.htm?action=getHealthCardById', {cardId: id},
                function (obj) {
                    $('#cardName').val(obj.CARD_NME);
                    $('#salePrice').val(obj.PRICE);
                    $('#doctorsDiscount').val(obj.DOCTOR_DISC);
                    $('#productsDiscount').val(obj.PRODUCT_DISC);
                    $('#noOfVisits').val(obj.VISITS_ALLOWED);
                    $('#additionalFeatures').val(obj.DESCRIPTION);
                    $('#availableFor').val(obj.AVAILABLE_FOR);
                    $('#addClinic').modal('show');
                }, 'json');
    }

</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Health Cards</h1>
    </div>
</div>
<div class="modal fade" id="addClinic">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Add Health Card</h3>

            </div>
            <div class="modal-body">
                <input type="hidden" id="healthCardId" value="">
                <form action="#" role="form" method="post" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Card Name *</label>
                                <div>
                                    <input type="text" class="form-control" id="cardName" placeholder="Card Name" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Available For</label>
                                <div>
                                    <select id="availableFor" class="form-control">
                                        <option value="INDIVIDUAL">Individual</option>
                                        <option value="COOPERATE">Cooperate</option>
                                        <option value="SCHOOL">School</option>
                                        <option value="COLLEGE">College</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Sale Price</label>
                                <div>
                                    <input type="text" class="form-control" id="salePrice" placeholder="Sale Price" onkeyup="onlyInteger(this);" >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Discount for Doctors</label>
                                <div>
                                    <input type="text"   class="form-control" id="doctorsDiscount" placeholder="Discount for Doctors" onkeyup="onlyInteger(this);" maxlength="16" >
                                </div>
                            </div>
                        </div> 
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Discount for Products</label>
                                <div>
                                    <input type="text"   class="form-control" id="productsDiscount" placeholder="Discount for Products" onkeyup="onlyInteger(this);" maxlength="16" >
                                </div>
                            </div>
                        </div> 
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>No. of Visits</label>
                                <div>
                                    <input type="text"   class="form-control" id="noOfVisits" placeholder="Total Visits Allowed" onkeyup="onlyInteger(this);" maxlength="16" >
                                </div>
                            </div>
                        </div> 
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Additional Features</label>
                                <textarea class="form-control" id="additionalFeatures" rows="2" cols="30"></textarea>
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
                    Registered Cards
                </div>
            </div>
            <div class="portlet-body">
                <form action="#" onsubmit="return false;" role="form" method="post">
                    <div class="row">
                        <div class="col-md-12 text-right" style="padding-top: 23px;">
                            <button type="button" class="btn blue" onclick="addClinicDialog();"><i class="fa fa-plus-circle"></i> New Card</button>
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
