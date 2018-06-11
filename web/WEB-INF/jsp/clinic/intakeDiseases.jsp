<%-- 
    Document   : intakeDiseases
    Created on : Jun 9, 2018, 4:10:58 PM
    Author     : Ali Zaidi
--%>
<%@include file="../header.jsp"%>
<script>
    $(function () {
        getData();
    });
    function getData() {
        Metronic.blockUI({
            boxed: true,
            message: 'Loading...'
        });
        $.get('clinic.htm?action=getIntakeDiseases', {specialityId: $('#specialityId').val()},
                function (obj) {
                    Metronic.unblockUI();
                    $('input[name="diseaseInput"]').iCheck('uncheck');
                    if (obj !== null && obj.length > 0) {
                        for (var i = 0; i < obj.length; i++) {
                            $('input[name="diseaseInput"][value="' + obj[i].TW_DISEASE_ID + '"]').iCheck('check');
                        }
                    }
                }, 'json');
    }
    function saveData() {
        Metronic.blockUI({
            boxed: true,
            message: 'Saving...'
        });
        $.post('clinic.htm?action=saveIntakeDisease', {specialityId: $('#specialityId').val(),
            'diseasesId[]': $('input[name=diseaseInput]:checked').getCheckboxVal()},
                function (res) {
                    Metronic.unblockUI();
                    if (res.result === 'save_success') {
                        $.bootstrapGrowl("Diseases save successfully.", {
                            ele: 'body',
                            type: 'success',
                            offset: {from: 'top', amount: 80},
                            align: 'right',
                            allow_dismiss: true,
                            stackup_spacing: 10
                        });
                    } else {
                        $.bootstrapGrowl("Error in saving diseases", {
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
    jQuery.fn.getCheckboxVal = function () {
        var vals = [];
        var i = 0;
        this.each(function () {
            vals[i++] = jQuery(this).val();
        });
        return vals;
    };
</script>
<input type="hidden" id="can_edit" value="${requestScope.refData.CAN_EDIT}">
<input type="hidden" id="can_delete" value="${requestScope.refData.CAN_DELETE}">
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Intake Diseases</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet-body">
            <div class="portlet box green">
                <div class="portlet-title">
                    <div class="caption">
                        Intake Diseases
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Medical Speciality</label>
                                <select id="specialityId" class="form-control" onchange="getData();">
                                    <c:forEach items="${requestScope.refData.speciality}" var="obj">
                                        <option value="${obj.TW_MEDICAL_SPECIALITY_ID}">${obj.TITLE}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12" style="padding-top: 20px;">
                            <table class="table table-striped table-bordered table-condensed">
                                <thead>
                                    <tr>
                                        <th>
                                            Disease
                                        </th>
                                        <th style="text-align: center;">
                                            Select
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.refData.diseases}" var="obj">
                                        <tr>
                                            <td>
                                                ${obj.TITLE}
                                            </td>
                                            <td align="center">
                                                <input name="diseaseInput" type="checkbox" class="icheck" value="${obj.TW_DISEASE_ID}" />
                                            </td>
                                        </tr>
                                    </c:forEach>

                                </tbody>
                            </table>
                            <button type="button" class="btn btn-primary" onclick="saveData();">Save <i class="fa fa-floppy-o"></i></button>
                        </div>
                    </div>
                </div>
            </div>

            </form>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>