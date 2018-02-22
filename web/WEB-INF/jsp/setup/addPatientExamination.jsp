<%-- 
    Document   : addPatientExamination
    Created on : Feb 10, 2018, 12:27:22 PM
    Author     : Ali Zaidi
--%>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Treat Well</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8">
        <meta content="" name="description"/>
        <meta content="" name="author"/>
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css">
        <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css">
        <link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="assets/global/plugins/icheck/skins/all.css">
        <link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN THEME STYLES -->
        <link href="assets/global/css/components-md.css" id="style_components" rel="stylesheet" type="text/css"/>
        <link href="assets/global/css/plugins-md.css" rel="stylesheet" type="text/css"/>
        <link href="assets/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
        <link id="style_color" href="assets/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css"/>
        <!-- END THEME STYLES -->
        <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="icon" href="images/favicon.ico" type="image/x-icon">
    </head>
    <body class="page-md">
        <div class="page-container">
            <div class="page-content ">
                <div class="page-head">
                    <!-- BEGIN PAGE TITLE -->
                    <div class="page-title">
                        <h1>Examination Question</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class='portlet-body'>
                            <div class='portlet box'>
                                <div class='portlet-body'>
                                    <div class="row">
                                        <div class="col-md-1">
                                            <label>Revision No</label>
                                        </div>
                                        <div class="col-md-1">
                                            <select class="form-control" id="revisionNo">
                                                <c:if test="${not empty requestScope.refData.revision}">
                                                    <c:forEach items="${requestScope.refData.revision}" var="obj">
                                                        <option value="${obj.REVISION_NO}">${obj.REVISION_NO}</option>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${empty requestScope.refData.revision}">
                                                    <option value="1">1</option>
                                                </c:if>
                                            </select>  
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <script>
                            var global = {
                                masterId: [],
                                detail: []
                            };
                        </script>
                        <c:forEach items="${requestScope.refData.question}" var="obj">
                            <div class='portlet-body'>
                                <div class='portlet box'>
                                    <div class='portlet-title'><div class='caption' style='color: #333 !important;'>${obj.QUESTION_TXT}</div></div>
                                    <div class='portlet-body'>
                                        <table class="table table-hover">
                                            <tbody>
                                            <script>
                                                global.masterId.push(${obj.TW_QUESTION_MASTER_ID});
                                            </script>
                                            <c:forEach items="${requestScope.refData.answer}" var="innerObj">
                                                <c:if test="${obj.TW_QUESTION_MASTER_ID == innerObj.TW_QUESTION_MASTER_ID}">
                                                    <tr>
                                                        <c:choose>
                                                            <c:when test="${innerObj.ANSWER_TXT == 'Others'}">
                                                                <td width="3%">
                                                                    <input type="checkbox" name="question_${obj.TW_QUESTION_MASTER_ID}_other" value="${innerObj.TW_QUESTION_DETAIL_ID}" class="icheck" />
                                                                </td>
                                                                <td>
                                                                    ${innerObj.ANSWER_TXT}
                                                                </td>
                                                                <td>
                                                                    <input type="text" name="other_${obj.TW_QUESTION_MASTER_ID}" class="form-control"  />
                                                                </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td width="3%">
                                                                    <input type="checkbox" name="question_${obj.TW_QUESTION_MASTER_ID}" value="${innerObj.TW_QUESTION_DETAIL_ID}" class="icheck"  />
                                                                </td>
                                                                <td colspan="2">
                                                                    ${innerObj.ANSWER_TXT}
                                                                </td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <button type="button" class="btn btn-primary" onclick="saveMarkedQuestion();">Save</button>
            </div>
            <input type="hidden" value="${requestScope.refData.patientId}" id="patientId" />
        </div>
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
        <!-- END CORE PLUGINS -->
        <script src="assets/global/scripts/metronic.js" type="text/javascript"></script>
        <script src="assets/global/plugins/icheck/icheck.min.js" type="text/javascript"></script>
        <script src="assets/admin/layout4/scripts/layout.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-growl/jquery.bootstrap-growl.min.js"></script>
        <script>

                    $(function () {
                        $('input:checkbox').iCheck({
                            checkboxClass: 'icheckbox_square',
                            increaseArea: '20%' // optional
                        });
                        $('#revisionNo').change(function () {
                            getExaminationRevision();
                        }).trigger('change');
                        Metronic.init(); // init metronic core components
                        Layout.init(); // init current layout 
                    });
                    function getExaminationRevision() {
                        if ($.trim($('#revisionNo').val()) !== '') {
                            $.get('setup.htm?action=getExaminationRevision', {patientId: $('#patientId').val(),
                                revisionNo: $('#revisionNo').val()},
                                    function (data) {
                                        if (data !== null && data.length > 0) {
                                            $('input').iCheck('uncheck');
                                            $('input:text').val('');
                                            for (var i = 0; i < data.length; i++) {
                                                if (data[i].REMARKS === '') {
                                                    $('input[name=question_' + data[i].TW_QUESTION_MASTER_ID + '][value=' + data[i].TW_QUESTION_DETAIL_ID + ']').iCheck('check');
                                                } else {
                                                    $('input[name=question_' + data[i].TW_QUESTION_MASTER_ID + '_other][value=' + data[i].TW_QUESTION_DETAIL_ID + ']').iCheck('check');
                                                    $('input[name=other_' + data[i].TW_QUESTION_MASTER_ID + "]").val(data[i].REMARKS);
                                                }
                                            }

                                        }
                                    }, 'json');
                        }
                    }
                    function saveMarkedQuestion() {
                        for (var i = 0; i < global.masterId.length; i++) {
                            var input = $('input[name=question_' + global.masterId[i] + ']');
                            var value = "";
                            $.each(input, function (index, obj) {
                                if ($(obj).is(':checked')) {
                                    value += $(obj).val() + ",";
                                }
                            });
                            if ($('input[name=question_' + global.masterId[i] + '_other]').is(':checked')) {
                                value += $('input[name=question_' + global.masterId[i] + '_other]').val() + "_" + $('input[name=other_' + global.masterId[i] + "]").val();
                            }
                            global.detail.push(value);
                        }
                        var obj = {
                            patientId: $('#patientId').val(),
                            'questionarr[]': global.masterId,
                            'answerarr[]': global.detail
                        };
                        $.post('setup.htm?action=saveExamination', obj, function (obj) {
                            if (obj.result === 'save_success') {
                                $.bootstrapGrowl("Saved successfully.", {
                                    ele: 'body',
                                    type: 'success',
                                    offset: {from: 'top', amount: 80},
                                    align: 'right',
                                    allow_dismiss: true,
                                    stackup_spacing: 10
                                });
                                global.detail.length = 0;
                                return false;
                            } else {
                                $.bootstrapGrowl("Error in Saving.", {
                                    ele: 'body',
                                    type: 'danger',
                                    offset: {from: 'top', amount: 80},
                                    align: 'right',
                                    allow_dismiss: true,
                                    stackup_spacing: 10
                                });
                                global.detail.length = 0;
                                return false;
                            }
                        }, 'json');
                        return false;


                    }
        </script>
    </body>
</html>
