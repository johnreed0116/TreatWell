<%-- 
    Document   : viewPrescription
    Created on : May 21, 2018, 2:14:54 PM
    Author     : farazahmad
--%>
<html>
    <head>
        <meta charset="UTF-8"/>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8">
        <meta content="Prescription Preview" name="description"/>
        <meta content="Faraz Ahmad" name="author"/>
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css">
        <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN THEME STYLES -->
        <link href="assets/global/css/components-md.css" id="style_components" rel="stylesheet" type="text/css"/>
        <link href="assets/global/css/plugins-md.css" rel="stylesheet" type="text/css"/>
        <link href="assets/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
        <link id="style_color" href="assets/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css"/>
        <!-- END THEME STYLES -->
        <title>Ezimedic</title>
        <link rel="shortcut icon" href="manifest/favicon.ico" type="image/x-icon">
        <link rel="icon" href="manifest/favicon.ico" type="image/x-icon">
        <link rel="manifest" href="manifest/manifest.json">
        <meta name="msapplication-TileColor" content="#ffffff">
        <meta name="msapplication-TileImage" content="manifest/ms-icon-144x144.png">
        <meta name="theme-color" content="#ffffff">
        <meta name="description" content="Treat Well Services">
        <script>

        </script>

    </head>
    <body class="page-md">
        <div class="page-container">
            <div class="page-content ">
                <div id="main">
                    <div class="portlet light">
                        <div class="portlet-body">
                            <input type="hidden" id="doctorId" value="${requestScope.refData.doctorId}">
                            <div class="invoice">
                                <div class="row" >
                                    <div class="col-xs-12">
                                        <div class="text-center">
                                            <img class="img-responsive" src="images/doc_header.png" alt="Prescription Header" style="height: 150px;width: 100%;">
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="row ">
                                    <div class="col-sm-12">
                                        <span style="font-weight: bold;font-size: medium">Name: ${requestScope.refData.master.PATIENT_NME}</span>
                                        &nbsp;&nbsp;&nbsp;
                                        <span style="font-weight: bold;font-size: medium">Date: ${requestScope.refData.master.CURR_DTE}&nbsp;&nbsp;&nbsp;&nbsp;Age: ${requestScope.refData.master.CURR_DTE}</span>
                                        
                                    </div>
                                </div>
                                <div class="row ">
                                    <div class="col-sm-2">
                                        <h3>Diagnostics</h3>

                                    </div>
                                    <div class="col-sm-10">
                                        <div class="panel panel-default" style="min-height: 400px;height: auto;">
                                            <div class="panel-body">
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
                                                                        <th>Frequency</th>
                                                                        <th >
                                                                            Usage Instructions
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <c:forEach items="${requestScope.refData.medicines}" var="obj" varStatus="i">
                                                                        <c:choose>
                                                                            <c:when test="${not empty obj.MEDICINE_NME}">
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
                                                                                        <c:choose>
                                                                                            <c:when test="${requestScope.refData.prescriptionLang == 'ENGLISH'}">
                                                                                                ${obj.DOSE_USAGE}
                                                                                            </c:when>
                                                                                            <c:otherwise>
                                                                                                ${obj.TITLE_URDU}
                                                                                            </c:otherwise>
                                                                                        </c:choose>
                                                                                    </td>
                                                                                </tr>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <tr>
                                                                                    <td>
                                                                                        ${i.count}
                                                                                    </td>
                                                                                    <td colspan="5" align="center" >
                                                                                        <c:choose>
                                                                                            <c:when test="${requestScope.refData.prescriptionLang == 'ENGLISH'}">
                                                                                                <b>${obj.DOSE_USAGE}</b>
                                                                                            </c:when>
                                                                                            <c:otherwise>
                                                                                                <b>${obj.TITLE_URDU}</b>
                                                                                            </c:otherwise>
                                                                                        </c:choose>
                                                                                    </td>
                                                                                </tr>

                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:forEach>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>

                                </div>

                                <div class="row" >
                                    <div class="col-xs-12">
                                        <div class="text-center">
                                            <img class="img-responsive" src="images/doc_footer.png" alt="Prescription Header" style="height: 100px;width: 100%;">
                                        </div>
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
        <script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
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
