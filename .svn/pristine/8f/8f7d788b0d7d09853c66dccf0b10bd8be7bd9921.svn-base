<html>
    <head>
        <meta charset="utf-8"/>
        <title>TreatWell - Invoice</title>
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
        <link rel="shortcut icon" href="favicon.ico"/>


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
            <div class="page-content">
                <div class="portlet light">
                    <div class="portlet-body">
                        <div class="invoice">
                            <div class="row ">
                                <div class="col-xs-6 text-left">
                                    <c:forEach items="${requestScope.refData.fees}" var="obj" varStatus="i">
                                        <c:if test="${i.count==1}">
                                            <h3>Name: ${obj.PATIENT_NME}</h3>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <div class="col-xs-6 text-right">
                                    <c:forEach items="${requestScope.refData.fees}" var="obj" varStatus="i">
                                        <c:if test="${i.count==1}">
                                            <h4> ${obj.CURR_DTE}</h4>
                                            <span class="muted">Appointment# ${obj.APPOINTMENT_NO}</span>
                                        </c:if>
                                    </c:forEach>

                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-12">
                                    <table class="table table-striped table-hover">
                                        <thead>
                                            <tr>
                                                <th>
                                                    #
                                                </th>
                                                <th>
                                                    Procedure Name
                                                </th>
                                                <th>
                                                    Fee
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="totalAmnt" value="0"></c:set>
                                            <c:forEach items="${requestScope.refData.fees}" var="obj" varStatus="i">
                                                <c:set var="totalAmnt" value="${totalAmnt+obj.FEE_AMNT}"></c:set>
                                                    <tr>
                                                        <td>
                                                        ${i.count}
                                                    </td>
                                                    <td>
                                                        ${obj.PROCEDURE_NME}
                                                    </td>
                                                    <td>
                                                        ${obj.FEE_AMNT}
                                                    </td>
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-4">
                                    <div class="well">
                                        Powered By: 
                                        <address>
                                            <strong>TREAT WELL SERVICES (PVT) LTD.</strong><br/>
                                        </address>
                                    </div>
                                </div>
                                <div class="col-xs-8 invoice-block">
                                    <ul class="list-unstyled amounts">
                                        <li>
                                            <strong>Grand Total:</strong> ${totalAmnt}
                                        </li>
                                    </ul>
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
                                        jQuery(document).ready(function () {
                                            Metronic.init(); // init metronic core components
                                            Layout.init(); // init current layout
                                        });
        </script>
    </body>
</html>


