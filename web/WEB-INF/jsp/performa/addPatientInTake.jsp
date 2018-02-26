<%-- 
    Document   : addPatientInTake
    Created on : Feb 24, 2018, 2:24:53 PM
    Author     : farazahmad
--%>
<!DOCTYPE html>
<html lang="en" class="no-js">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->
    <head>
        <%@ page contentType="text/html; charset=UTF-8" %>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport"/>
        <meta content="" name="description"/>
        <meta content="Faraz Ahmad" name="author"/>
        <meta http-equiv="cache-control" content="no-cache"/>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
        <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
        <link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
        <link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
        <!-- END GLOBAL MANDATORY STYLES -->
        <link rel="stylesheet" type="text/css" href="assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>
        <!-- DOC: To use 'rounded corners' style just load 'components-rounded.css' stylesheet instead of 'components.css' in the below style tag -->
        <link href="assets/global/css/components-md.css" id="style_components" rel="stylesheet" type="text/css"/>
        <link href="assets/global/css/plugins-md.css" rel="stylesheet" type="text/css"/>
        <link href="assets/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
        <link href="assets/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css" id="style_color"/>
        <link rel="stylesheet" type="text/css" href="assets/global/plugins/icheck/skins/all.css">
        <link rel="stylesheet" type="text/css" href="assets/global/plugins/select2/select2.css"/>
        <!-- END THEME STYLES -->
        <title>Treat Well Services</title>
        <link rel="shortcut icon" href="manifest/favicon.ico" type="image/x-icon">
        <link rel="icon" href="manifest/favicon.ico" type="image/x-icon">
        <link rel="manifest" href="manifest/manifest.json">
        <meta name="msapplication-TileColor" content="#ffffff">
        <meta name="msapplication-TileImage" content="manifest/ms-icon-144x144.png">
        <meta name="theme-color" content="#ffffff">
        <meta name="description" content="Treat Well Services">

        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
        <!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
        <script src="assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/moment.min.js" type="text/javascript"></script>
        <script src="assets/global/scripts/metronic.js" type="text/javascript"></script>
        <script src="assets/admin/layout4/scripts/layout.js" type="text/javascript"></script>
        <script src="assets/admin/layout4/scripts/quick-sidebar.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/icheck/icheck.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
        <script type="text/javascript" src="assets/global/plugins/select2/select2.min.js"></script>
        <script type="text/javascript" src="js/notify.min.js"></script>
        <script type="text/javascript" src="js/script.js"></script>
        <script src="assets/global/plugins/bootstrap-growl/jquery.bootstrap-growl.min.js"></script>

        <script>
            var global = {
                masterId: [],
                detail: []
            };
            $(function () {
                Metronic.init(); // init metronic core components
                Layout.init(); // init current layout
                $('input:checkbox').iCheck({
                    checkboxClass: 'icheckbox_square',
                    increaseArea: '20%' // optional
                });
                loadPreviousData();
            });
        </script>
    </head>
    <body class="page-md">
        <div class="page-container">
            <div class="page-content ">
                <div class="page-head">
                    <!-- BEGIN PAGE TITLE -->
                    <div class="page-title">
                        <h1>Intake Form</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="portlet box green">
                            <div class="portlet-title">
                                <div class="caption">
                                    Patient Info
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <label for="patientName">Patient Name</label>
                                        <input type="text" readonly="" class="form-control" id="patientName" value="${requestScope.refData.patientName}">
                                        <input type="hidden" value="${requestScope.refData.patientId}" id="patientId" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="portlet box red">
                            <div class="portlet-title">
                                <div class="caption">
                                    Diseases 
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group" id="diseases">
                                            <table class="table table-condensed" width="100%">
                                                <tbody>
                                                    <c:forEach items="${requestScope.refData.diseases}" var="obj" varStatus="i">
                                                        <c:if test="${i.count==1}">
                                                            <tr>
                                                            </c:if>
                                                            <td>
                                                                <input type="checkbox" name="patientDiseases" class="icheck"  value="${obj.TW_DISEASE_ID}">${obj.TITLE}
                                                            </td>
                                                            <c:if test="${i.count%3==0}">
                                                            </tr>
                                                            <tr>
                                                            </c:if>
                                                        </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div> 
                            </div>
                        </div>
                        <c:forEach items="${requestScope.refData.question}" var="obj" varStatus="i">
                            <script>
                                global.masterId.push('${obj.TW_INTAKE_MASTER_ID}');
                            </script>
                            <div class="portlet box blue">
                                <div class='portlet-title'><div class="caption">${i.count}.&nbsp; ${obj.QUESTION_TXT}</div></div>
                                <div class="portlet-body">
                                    <c:forEach items="${requestScope.refData.answer}" var="innerObj">
                                        <c:if test="${obj.TW_INTAKE_MASTER_ID == innerObj.TW_INTAKE_MASTER_ID}">
                                            <input type="radio" name="question_${obj.TW_INTAKE_MASTER_ID}" value="${innerObj.TW_INTAKE_DETAIL_ID}" class="icheck"
                                                   <c:if test="${innerObj.ANSWER_TXT=='No'}">
                                                       checked="checked"
                                                   </c:if>
                                                   />&nbsp;&nbsp;&nbsp;
                                            ${innerObj.ANSWER_TXT}&nbsp;&nbsp;&nbsp;
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <button type="button" class="btn btn-primary" onclick="saveMarkedQuestion();">Save</button>
            </div>
        </div>
        <div class="page-footer">
            <div class="page-footer-inner">
                2018 &copy; Treat Well Services
            </div>
            <div class="scroll-to-top">
                <i class="icon-arrow-up"></i>
            </div>
        </div>
        <script>
            function saveMarkedQuestion() {
                global.detail.length = 0;
                for (var i = 0; i < global.masterId.length; i++) {
                    var input = $('input[name=question_' + global.masterId[i] + ']');
                    $.each(input, function (index, obj) {
                        if ($(obj).is(':checked')) {
                            global.detail.push($(obj).val());
                        }
                    });
                }
                var obj = {
                    patientId: $('#patientId').val(),
                    'questionarr[]': global.masterId,
                    'answerarr[]': global.detail,
                    'diseases[]': $("input[name='patientDiseases']:checked").getCheckboxVal()
                };
                $.post('performa.htm?action=savePatientIntake', obj, function (obj) {
                    if (obj.result === 'save_success') {
                        $.bootstrapGrowl("Intake form saved successfully.", {
                            ele: 'body',
                            type: 'success',
                            offset: {from: 'top', amount: 80},
                            align: 'right',
                            allow_dismiss: true,
                            stackup_spacing: 10
                        });
                    } else {
                        $.bootstrapGrowl("Error in Saving intake form. Please try again.", {
                            ele: 'body',
                            type: 'danger',
                            offset: {from: 'top', amount: 80},
                            align: 'right',
                            allow_dismiss: true,
                            stackup_spacing: 10
                        });
                    }
                }, 'json');
                return false;
            }
            function loadPreviousData() {
                $('input:checkbox[name="patientDiseases"]').iCheck('uncheck');
                $.get('setup.htm?action=getPatientDisease', {patientId: $('#patientId').val()},
                        function (list) {
                            if (list !== null && list.length > 0) {
                                for (var i = 0; i < list.length; i++) {
                                    $('input:checkbox[name="patientDiseases"][value="' + list[i].TW_DISEASE_ID + '"]').iCheck('check');
                                }
                            }
                            $.get('performa.htm?action=getIntakeFormData', {patientId: $('#patientId').val()},
                                    function (data) {
                                        if (data && data.length > 0) {
                                            for (var i = 0; i < data.length; i++) {
                                                var input = $('input[name=question_' + data[i].TW_INTAKE_MASTER_ID + '][value=' + data[i].TW_INTAKE_DETAIL_ID + ']');
                                                if (input.length) {
                                                    $(input).iCheck('check');
                                                }

                                            }
                                        }
                                    }, 'json');
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
    </body>
</html>
