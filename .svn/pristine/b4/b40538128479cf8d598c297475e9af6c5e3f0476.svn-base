<%-- 
    Document   : viewPrescription
    Created on : Nov 1, 2017, 4:57:04 PM
    Author     : farazahmad
--%>

<html>
    <head>
        <meta charset="utf-8"/>
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
        <script>
            function getMarginsByDoctorId() {
                $.get('performa.htm?action=getMarginsByDoctorId', {doctorId: $('#doctorId').val()},
                        function (obj) {
//                            $('#main').css("margin-top", obj.TOP_MARGIN + 'in');
//                            $('#main').css("margin-bottom", obj.BOTTOM_MARGIN + 'in');
                            var topImg = obj.TOP_IMAGE;
                            var bottomImg = obj.BOTTOM_IMAGE;
                            if (topImg !== '') {
                                var path = 'upload/doctor/latterPad/' + obj.TW_DOCTOR_ID + '/' + bottomImg + '';
                                $('#main').prepend('<img src="' + path + '" alt="Top Image" width="100%">');
                            }
                            //$("html, body").animate({scrollTop: $("#main").offset().top}, 20);
                        }, 'json');
            }
        </script>
        <style>
            .invoice table {
                margin: 30px 0 30px 0;
            }

            .invoice .invoice-logo {
                margin-bottom: 20px;
            }

            .invoice .invoice-logo p {
                padding: 5px 0;
                font-size: 26px;
                line-height: 28px;
                text-align: right;
            }

            .invoice .invoice-logo p span {
                display: block;
                font-size: 14px;
            }

            .invoice .invoice-logo-space {
                margin-bottom: 15px;
            }

            .invoice .invoice-payment strong {
                margin-right: 5px;
            }

            .invoice .invoice-block {
                text-align: right;
            }

            .invoice .invoice-block .amounts {
                margin-top: 20px;
                font-size: 14px;
            }
        </style>
    </head>
    <body class="page-md">
        <div class="page-container">
            <div class="page-content ">
                <div id="main">
                    <div class="portlet light">
                        <div class="portlet-body">
                            <input type="hidden" id="doctorId" value="${requestScope.refData.doctorId}">
                            <div class="invoice">
                                <div class="row ">
                                    <div class="col-xs-6 text-left">

                                        <span style="font-weight: bold;font-size: large">Patient Name: ${requestScope.refData.master.PATIENT_NME}</span>
                                    </div>
                                    <div class="col-xs-6 text-right">
                                        <h4> Dated: ${requestScope.refData.master.CURR_DTE}</h4>
                                    </div>
                                </div>
                                <c:if test="${not empty requestScope.refData.medicines}">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <h4 style="font-weight: bold; padding-top: 3%">Medicines List</h4>
                                            <table class="table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>
                                                            #
                                                        </th>
                                                        <th>
                                                            Medicine Name
                                                        </th>
                                                        <th>
                                                            Quantity
                                                        </th>
                                                        <th>
                                                            Days
                                                        </th>
                                                        <th>
                                                            Frequency
                                                        </th>
                                                        <th>
                                                            Usage Instructions
                                                        </th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.refData.medicines}" var="obj" varStatus="i">
                                                        <tr>
                                                            <td>
                                                                ${i.count}
                                                            </td>
                                                            <td>
                                                                ${obj.MEDICINE_NME}
                                                            </td>
                                                            <td>
                                                                ${obj.QTY}
                                                            </td>
                                                            <td>
                                                                ${obj.DAYS}
                                                            </td>
                                                            <td>
                                                                ${obj.FREQUENCY}
                                                            </td>
                                                            <td>
                                                                ${obj.DOSE_USAGE}
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${not empty requestScope.refData.tests}">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <h4 style="font-weight: bold;">Medical Tests List</h4>
                                            <table class="table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>
                                                            #
                                                        </th>
                                                        <th>
                                                            Test Name
                                                        </th>
                                                        <th>
                                                            Recommended Laboratory
                                                        </th>
                                                        <th>
                                                            Collection Center
                                                        </th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.refData.tests}" var="obj" varStatus="i">
                                                        <tr>
                                                            <td>
                                                                ${i.count}
                                                            </td>
                                                            <td>
                                                                ${obj.LAB_TEST_NME}
                                                            </td>
                                                            <td>
                                                                ${obj.LAB_NME}
                                                            </td>
                                                            <td>
                                                                ${obj.CENTER_NME}
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </c:if>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <c:if test="${not empty requestScope.refData.master.REMARKS}">
                                            <h4 style="font-weight: bold;">Doctor Remarks</h4>
                                            <p> ${requestScope.refData.master.REMARKS}</p>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-8">

                                    </div>
                                    <div class="col-xs-4 invoice-block">
                                        <br/>
                                        <br/>
                                        <a class="btn btn-lg blue hidden-print margin-bottom-5" onclick="javascript:window.print();">
                                            Print <i class="fa fa-print"></i>
                                        </a>
                                    </div>
                                </div>    
                            </div>
                        </div>
                    </div>
                </div>
            </div>

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
        <script src="assets/admin/layout4/scripts/layout.js" type="text/javascript"></script>
        <script>
                                            $(function () {
                                                getMarginsByDoctorId();
                                            });

                                            jQuery(document).ready(function () {
                                                Metronic.init(); // init metronic core components
                                                Layout.init(); // init current layout
                                            });
        </script>
    </body>
</html>
