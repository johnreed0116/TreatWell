<%-- 
    Document   : resetPassword
    Created on : Mar 28, 2018, 1:29:20 PM
    Author     : farazahmad
--%>

<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Ezimedic</title>
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
            function resetPassword() {
                if ($.trim($('#mobileNo').val()) === '') {
                    $('#mobileNo').notify('Provide a valid mobile no.', 'error', {autoHideDelay: 15000});
                    $('#mobileNo').focus();
                    return false;
                } else {
                    if ($('#mobileNo').val().length < 11) {
                        $('#mobileNo').notify('Provide a valid mobile no.', 'error', {autoHideDelay: 15000});
                        $('#mobileNo').focus();
                        return false;
                    }
                }
                document.getElementById("form1").submit();
            }
        </script>
    </head>
    <body class="page-md page-header-fixed">
        <div class="page-header md-shadow-none navbar  navbar-fixed-top">
            <div class="page-header-inner">
                <!-- BEGIN LOGO -->
                <div class="page-logo">
                    <a href="#">
                        <img src="images/ezimedic_black.png"  alt="Ezimedic" style="height: 50px;width: 160px;margin-top: 15px;" class="logo-default"/>
                    </a>
                </div>
            </div>
        </div>
        <div class="clearfix"></div>
        <div class="page-container">
            <div class="page-content-wrapper">
                <div class="page-content">
                    <div class="page-head">
                        <!-- BEGIN PAGE TITLE -->
                        <div class="page-title">
                            <h1>Reset Password</h1>
                        </div>
                    </div>
                    <hr/>
                    <div class="row">
                        <div class="col-md-12">
                            <form  id="form1" action="login.htm?action=processResetPassword" role="form" onsubmit="return false;" method="post">
                                <div class="form-group">
                                    <label>Enter your registered mobile no.</label>
                                    <div>
                                        <input type="text" class="form-control" id="mobileNo" required="" placeholder="Registered Mobile No." onkeyup="onlyInteger(this);" name="mobileNo" maxlength="11" >
                                    </div>
                                </div>
                                <button type="button" class="btn green" onclick="resetPassword();"><i class="fa fa-key"></i> Reset Password</button>
                            </form>
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
        <script type="text/javascript" src="js/notify.min.js"></script>
        <script type="text/javascript" src="js/script.js"></script>
        <script>
                                    $(function () {

                                    });

                                    jQuery(document).ready(function () {
                                        Metronic.init(); // init metronic core components
                                        Layout.init(); // init current layout
                                    });
        </script>
    </body>
</html>
