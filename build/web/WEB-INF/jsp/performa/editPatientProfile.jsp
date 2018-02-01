<%-- 
    Document   : editPatientProfile
    Created on : Feb 1, 2018, 4:39:18 AM
    Author     : Ali Zaidi
--%>
<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('#bloodGroup').select2();
        $('#cityId').select2();
        $('#dob-picker').datepicker({
            format: 'dd-mm-yyyy',
            autoclose: true
        });
        $('#dob-picker').datepicker()
                .on('changeDate', function (e) {
                    var a = moment();
                    var b = moment($('#dob').val(), "DD-MM-YYYY");
                    var age = a.diff(b, 'years'); // 1
                    $('#age').val(age);
                });
    });
    function saveData() {
        if ($.trim($('#patientName').val()) === '') {
            $('#patientName').notify('Patient Name is Required Field', 'error', {autoHideDelay: 15000});
            $('#patientName').focus();
            return false;
        }
        if ($.trim($('#contactNo').val()) === '') {
            $('#contactNo').notify('Contact is Required Field', 'error', {autoHideDelay: 15000});
            $('#contactNo').focus();
            return false;
        }
        if ($.trim($('#contactNo').val()).length < 11) {
            $('#contactNo').notify('Enter correct contact no.', 'error', {autoHideDelay: 15000});
            $('#contactNo').focus();
            return false;
        }
        var obj = {patientId: $('#patientId').val(), email: $('#email').val(),
            patientName: $('#patientName').val(), contactNo: $('#contactNo').val(), age: $('#age').val(),
            patientWeight: $('#patientWeight').val(), patientHeight: $('#patientHeight').val(),
            patientAddress: $('#patientAddress').val(), gender: $('input[name=gender]:checked').val(),
            cityId: $('#cityId').val(), dob: $('#dob').val(), referredBy: $('#referredBy').val(),
            profession: $('#profession').val(), bloodGroupId: $('#bloodGroup').val()
        };
        $.post('setup.htm?action=savePatient', obj, function (obj) {
            if (obj.result === 'save_success') {
                $('#addPatient').modal('hide');
                $.bootstrapGrowl("Profile Updated.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
                // displayData();
//                if ($('#patientId').val() === '') {
//                    inTakeForm(obj.patientId);
//                }
                return false;
            } else {
                $.bootstrapGrowl("Error in Updating Profile.", {
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
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Edit Profile</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet-body">
            <div class="portlet box green">
                <div class="portlet-title">
                    <div class="caption">
                        Edit Profile
                    </div>
                </div>
                <div class="portlet-body">
                    <input type="hidden" id="patientId" value="${requestScope.refData.patientInfo.TW_PATIENT_ID}" >
                    <div class="row">
                        <div class="col-md-8">
                            <div class="form-group">
                                <label>Patient Name*</label>
                                <div>
                                    <input type="text" value="${requestScope.refData.patientInfo.PATIENT_NME}" class="form-control" id="patientName" placeholder="Patient Name" >
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Contact No.*</label>
                                <div>
                                    <input type="text" value="${requestScope.refData.patientInfo.MOBILE_NO}" class="form-control" id="contactNo" placeholder="Contact No." onkeyup="onlyInteger(this);" maxlength="11" onblur="Util.validatePatientNo(this);">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Email</label>
                                <div>
                                    <input type="text" value="${requestScope.refData.patientInfo.EMAIL}" class="form-control" id="email" placeholder="Email" >
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Date of Birth</label>
                                <div class="input-group input-medium date" id="dob-picker">
                                    <input type="text" id="dob" value="${requestScope.refData.patientInfo.DOB}" class="form-control" readonly="">
                                    <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Age</label>
                                <input type="text" class="form-control" value="${requestScope.refData.patientInfo.AGE}" id="age" onkeyup="onlyInteger(this);">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Weight (KG)</label>
                                <input type="text" class="form-control" value="${requestScope.refData.patientInfo.WEIGHT}" id="patientWeight" onkeyup="onlyInteger(this);">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Height (Feet)</label>
                                <input type="text" class="form-control"  value="${requestScope.refData.patientInfo.HEIGHT}" id="patientHeight" onkeyup="onlyInteger(this);">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label>Blood Group</label>
                            <select id="bloodGroup" class="form-control" data-placeholder="Choose a Blood Group">
                                <c:forEach items="${requestScope.refData.bloodGroup}" var="obj">
                                    <option value="${obj.TW_BLOOD_GROUP_ID}"
                                            <c:if test="${obj.TW_BLOOD_GROUP_ID== requestScope.refData.patientInfo.TW_BLOOD_GROUP_ID}">
                                                selected="selected"
                                            </c:if>
                                            >${obj.TITLE}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div> 
                    <div class="row">
                        <div class="col-md-8">
                            <label>City</label>
                            <select id="cityId" class="form-control" data-placeholder="Choose a City">
                                <c:forEach items="${requestScope.refData.cities}" var="obj">
                                    <option value="${obj.CITY_ID}"
                                            <c:if test="${obj.CITY_ID== requestScope.refData.patientInfo.CITY_ID}">
                                                selected="selected"
                                            </c:if>
                                            >${obj.CITY_NME}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Gender</label>
                                <div class="input-group">
                                    <div class="icheck-inline">
                                        <label>
                                            <input type="radio" name="gender" value="M" id="genderM" class="icheck" <c:if test="${requestScope.refData.patientInfo.GENDER == 'M'}">
                                                   checked
                                                </c:if>> Male </label>
                                        <label>
                                            <input type="radio" name="gender" value="F" id="genderF" class="icheck" <c:if test="${requestScope.refData.patientInfo.GENDER == 'F'}">
                                                   checked
                                                </c:if>> Female</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Referred by</label>
                                <input type="text" value="${requestScope.refData.patientInfo.REFERRED_BY}" class="form-control" id="referredBy" >
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Profession</label>
                                <input type="text" value="${requestScope.refData.patientInfo.PROFESSION}" class="form-control" id="profession" >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Address</label>
                                <textarea class="form-control"id="patientAddress" rows="3" cols="63">${requestScope.refData.patientInfo.ADDRESS}</textarea>
                            </div>
                        </div>   
                    </div>
                    <button type="button" class="btn btn-primary" id="save" onclick="saveData();">Save</button>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>

