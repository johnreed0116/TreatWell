<%-- 
    Document   : saleHealthCards
    Created on : Oct 28, 2017, 2:32:52 PM
    Author     : Cori 5
--%>


<%@include file="../header.jsp"%>
<script type="text/javascript" src="assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
<script>
    $(function () {
        $("#healthCardNo").inputmask("mask", {
            "mask": "9999-9999-9999-9999"
        });
        $('#patientId').select2();
        $('#cardExpiryDatePicker').datepicker({
            format: 'dd-mm-yyyy',
            autoclose: true
        });
        $('#cardExpiry').val(moment().add(1, 'y').format('DD-MM-YYYY'));
        displayCardsData();
    });
    function saveData() {
        if ($.trim($('#healthCardNo').val()) === '') {
            $('#healthCardNo').notify('Please enter health card serial no.', 'error');
            $('#healthCardNo').focus();
            return false;
        }
        if ($.trim($('#cardExpiry').val()) === '') {
            $('#cardExpiry').notify('Please enter expiry date.', 'error');
            return false;
        }
        var data = new FormData(document.getElementById('saleCardForm'));
        $.ajax({
            url: 'setup.htm?action=savePatientHealthCard',
            type: "POST",
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType

        }).done(function (obj) {
            if (obj.msg === 'saved') {
                $.bootstrapGrowl("Health Card data saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                $('#cardSaleMasterId').val('');
                $('#cardExpiry').val('');
                $('#healthCardNo').val('');
                $('#patientNameId').val('');
                $('#patientName').val('');
                $('#dependentDiv').find('tr').remove();
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
        });
        return false;
    }


    function editRow(id) {
        $.get('setup.htm?action=getPatientHealthCardById', {healthCardId: id}, function (obj) {
            if (obj !== null) {
                $('#cardExpiry').val(obj.EXPIRY_DTE);
                $('#healthCardNo').val(obj.CARD_NO);
                $('#cardSaleMasterId').val(obj.TW_CARD_SALE_MASTER_ID);
                $('input[name=healthCardId][value="' + obj.TW_HEALTH_CARD_ID + '"]').iCheck('check');
                getDetails();
            }
        }, 'json');
        return false;
    }
    function getDetails() {
        $.get('setup.htm?action=getPatientHealthCardDtlById', {cardSaleId: $('#cardSaleMasterId').val()}, function (data) {
            if (data !== null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    addDenpendentPatient(data[i].TW_PATIENT_ID, data[i].PATIENT_NME + '[' + data[i].MOBILE_NO + ']');
                }
            }

        },'json');
    }
    function displayCardsData() {
        var $tbl = $('<table class="table table-striped table-bordered table-hover">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="15%">').html('Select'),
                $('<th class="center" width="50%">').html('Card Name'),
                $('<th class="center" width="25%">').html('Available For'),
                $('<th class="center" width="10%">').html('Price')
                )));
        $.get('setup.htm?action=getHealthCards', {},
                function (list) {
                    if (list !== null && list.length > 0) {
                        $tbl.append($('<tbody>'));
                        for (var i = 0; i < list.length; i++) {
                            var check = '';
                            if (i === 0) {
                                check = ' checked="checked"';
                            }
                            $tbl.append(
                                    $('<tr>').append(
                                    $('<td  align="center">').html('<input type="radio" name="healthCardId" value="' + list[i].TW_HEALTH_CARD_ID + '" class="icheck" "' + check + '" >'),
                                    $('<td>').html(list[i].CARD_NME),
                                    $('<td>').html(list[i].AVAILABLE_FOR),
                                    $('<td>').html(list[i].PRICE)
                                    ));
                        }
                        $('#displayDiv').html('');
                        $('#displayDiv').append($tbl);
                    } else {
                        $('#displayDiv').html('');
                        $tbl.append(
                                $('<tr>').append(
                                $('<td  colspan="4">').html('<b>No data found.</b>')
                                ));
                        $('#displayDiv').append($tbl);
                    }
                    $('.icheck').iCheck({
                        checkboxClass: 'icheckbox_square',
                        radioClass: 'iradio_square',
                        increaseArea: '20%' // optional
                    });
                }, 'json');
    }
    var ajax_load = "<img src='images/loader.gif' alt='loading...' />";
    function getPatientsForDoctor(type) {
        $('#addType').val(type);
        $('#searchPatientModal').modal('show');

    }
    function searchForPatientsByMobile() {
        var num = $('#searchFieldMobileNo').val();
        var name = $('#searchFieldName').val();
        if (num === '' && name === '') {
            $('#searchFieldMobileNo').notify('Please Enter MOBILE NO. to Serach', 'error');
            return false;
        } else if (num !== '' && num.length < 6) {
            $('#searchFieldMobileNo').notify('Please Enter valid MOBILE NO. to Serach', 'error');
            return false;
        }
        var $tbl = $('<table class="table table-striped table-bordered" id="serchPatientTable">');
        $tbl.append($('<thead>').append($('<tr>').append(
                $('<th class="center" width="15%">').html('Select'),
                $('<th class="center" width="35%">').html('Patient Name'),
                $('<th class="center" width="20%">').html('Mobile No.'),
                $('<th class="center" width="30%">').html('Address')
                )));
        var url = "";
        if ($('#addType').val() === 'PARENT') {
            url = 'setup.htm?action=searchPatientsBySaleCardParent';
        }else {
            url = 'setup.htm?action=searchPatientsBySaleCardChild';
        }
        $.get(url, {contactNoSearch: num,
            contactNameSearch: name, doctorId: $('#doctorId').val()}, function (data) {
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    var val = data[i].TW_PATIENT_ID + '_' + data[i].PATIENT_NME + ' [' + data[i].MOBILE_NO + ']' + '_' + data[i].TW_CARD_SALE_MASTER_ID;
                    $tbl.append(
                            $('<tr>').append(
                            $('<td  align="center">').html('<input type="radio" name="selectedPatientId" value="' + val + '" class="icheck" >'),
                            $('<td>').html(data[i].PATIENT_NME),
                            $('<td>').html(data[i].MOBILE_NO),
                            $('<td>').html(data[i].ADDRESS)
                            ));
                }
                $('#dvTable').html('');
                $('#dvTable').append($tbl);
            } else {
                $('#dvTable').html('');
                $tbl.append(
                        $('<tr>').append(
                        $('<td  colspan="4">').html('<b>No data found.</b><button type="button" class="btn btn-info btn-sm" onclick="addPatientDialog();">Add Patient</button>')
                        ));
                $('#dvTable').append($tbl);
            }
            $('.icheck').iCheck({
                checkboxClass: 'icheckbox_square',
                radioClass: 'iradio_square',
                increaseArea: '20%' // optional
            });
        }, 'json');

    }
    function  removeRow(param) {
        $(param).closest('tr').remove();
        checkDependent();
    }
    function selectSearchedPatient() {
        var selectedId = $('input[name=selectedPatientId]:checked').val();
        if ($('#addType').val() === 'PARENT') {
            if (!selectedId) {
                $('#searchFieldMobileNo').notify('First search for patients then press OK.', 'error');
                return false;
            } else {
                $('#cardSaleMasterId').val('');
                $('#cardExpiry').val('');
                $('#healthCardNo').val('');
                $('#patientNameId').val('');
                $('#patientName').val('');
                $('#dependentDiv').find('tr').remove();
                var patientName = selectedId.split('_')[1];
                var patientId = selectedId.split('_')[0];
                $('#patientNameId').val(patientId);
                $('#patientName').val(patientName);
                $('#searchFieldMobileNo').val('');
                $('#dvTable').html('');
                $('#searchPatientModal').modal('hide');
                var cardSaleMasterId = selectedId.split('_')[2];
                if (cardSaleMasterId !== null && cardSaleMasterId !== '') {
                    editRow(cardSaleMasterId);
                }
            }
        } else {
            var patientName = selectedId.split('_')[1];
            var patientId = selectedId.split('_')[0];
            addDenpendentPatient(patientId, patientName);

        }
    }
    function addDenpendentPatient(patientId, patientName) {
        var $tr = $('<tr>');
        $tr.append(
                $('<td class="center" style="visibility: hiiden;" >').html('<div class="icheckbox checked disabled"><input type="checkbox" name="dependentPatientId" value="' + patientId + '" checked>'),
                $('<td>').html(patientName + '<input name="dependentPatientName" value="' + patientName + '" type="hidden" />'),
                $('<td>').html('<i class="fa fa-minus" onclick="removeRow(this);" color="text-danger" style="cursor:pointer;"></i>')
                );
        $('#dependentDiv').append($tr);
        $('#searchFieldMobileNo').val('');
        $('#dvTable').html('');
        $('#searchPatientModal').modal('hide');
        $('.icheckbox').iCheck({
            checkboxClass: 'icheckbox_square',
            disabledCheckboxClass: 'disabled',
            increaseArea: '20%' // optional
        });
        checkDependent();
    }
    function checkDependent() {
        var tr = [];
        tr = $('#dependentDiv').find('tr');
        if (tr !== null && tr.length >= 4) {
            $('#denpendentBtn').addClass("disabled");
        } else {
            $('#denpendentBtn').removeClass("disabled");
        }
    }
</script>

<c:if test="${requestScope.refData.userType=='DOCTOR'}">
    <input type="hidden" id="doctorId" value="${requestScope.refData.doctorId}">
</c:if>

<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Health Cards</h1>
    </div>
</div>
<input type="hidden" id="addType" />
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">

<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Registered Cards
                </div>
            </div>
            <div class="portlet-body">
                <form action="#" onsubmit="return false;" id="saleCardForm" role="form" method="post">
                    <input type="hidden" id="cardSaleMasterId" name="cardSaleMasterId"/>
                    <input type="hidden" id="activeIndicator" value="n">
                    <div class="row">
                        <div class="col-md-10">
                            <label  for="patientName" >Patient Name</label>
                            <input type="text" readonly="" class="form-control" id="patientName" placeholder="Patient" autocomplete="off" name="patientName">
                            <input type="hidden" id="patientNameId" value="" name="patientId" >
                        </div>
                        <div class="col-md-2">
                            <div style="padding-top: 23px;">
                                <button type="button" class="btn blue" onclick="getPatientsForDoctor('PARENT');"><i class="fa fa-search"></i> Search Patient</button>

                            </div>
                        </div>                   
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label for="healthCardNo">Card No.*</label>
                                <input type="text" id="healthCardNo" name="cardNo" class="form-control" placeholder="1234567890" maxlength="20"  >
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Expiry Date*</label>
                                <div class="input-group input-medium date" id="cardExpiryDatePicker">
                                    <input type="text" id="cardExpiry" name="cardExpiry" class="form-control" readonly="" placeholder="01-03-2018">
                                    <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                        </div> 
                    </div>
                    <br/>
                    <div class="portlet box grey">
                        <div class="portlet-title tabbable-line">                

                            <div class="caption">
                                Add Dependent
                            </div>
                            <button style="float: right; margin-top: 3px;" id="denpendentBtn" type="button" class="btn green icon btn" onclick="getPatientsForDoctor('DEPENDENT');"><i class="fa fa-plus-circle"></i></button>

                        </div>
                        <div class="portlet-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <table class="table table-striped table-bordered">
                                        <thead>
                                            <tr>
                                                <th class="center" style="visibility: hiiden;" width="5%">Select</th>
                                                <th class="center" width="90%">Patient Name</th>
                                                <th class="center" width="5%">Remove</th>
                                            </tr>
                                        </thead>
                                        <tbody id="dependentDiv">
                                        </tbody>
                                    </table>
                                    <div id="dependentDiv"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="portlet box grey">
                        <div class="portlet-title tabbable-line">                

                            <div class="caption">
                                Select Card
                            </div>


                        </div>
                        <div class="portlet-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div id="displayDiv"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </form>
                <button type="button" class="btn green icon btn" onclick="saveData();"><i class="fa fa-floppy-o"></i> Save</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="searchPatientModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h3 class="modal-title">Search Patient</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-5">
                        <input type="text" id="searchFieldName"  value="" class="form-control" placeholder="Enter patient name!">
                    </div>
                    <div class="col-md-5">
                        <input type="text" id="searchFieldMobileNo" onkeyup="onlyInteger(this);" value="" class="form-control" placeholder="Enter Mobile No. for search record!">
                    </div>
                    <div class="col-md-2" >
                        <button type="button" class="btn btn-primary" onclick="searchForPatientsByMobile();">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <hr/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12" style="max-height: 250px;overflow: auto;">
                        <div id="dvTable"></div>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="selectSearchedPatient();">OK</button>
            </div>
        </div>
    </div>
</div>

<%@include file="../footer.jsp"%>

