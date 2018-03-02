<%-- 
    Document   : index
    Created on : Aug 12, 2016, 5:24:56 PM
    Author     : farazahmad
--%>
<!DOCTYPE html>
<html lang="en" class="no-js">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8"/>
        <title>Ezimedic</title>
        <link rel="shortcut icon" href="manifest/favicon.ico" type="image/x-icon">
        <link rel="icon" href="manifest/favicon.ico" type="image/x-icon">
        <link rel="manifest" href="manifest/manifest.json">
        <meta name="msapplication-TileColor" content="#ffffff">
        <meta name="msapplication-TileImage" content="manifest/ms-icon-144x144.png">
        <meta name="theme-color" content="#ffffff">
        <meta name="description" content="Treat Well Services">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport"/>
        <meta content="Ezimedic" name="description"/>
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

        <!-- DOC: To use 'rounded corners' style just load 'components-rounded.css' stylesheet instead of 'components.css' in the below style tag -->
        <link href="assets/global/css/components-md.css" id="style_components" rel="stylesheet" type="text/css"/>
        <link href="assets/global/css/plugins-md.css" rel="stylesheet" type="text/css"/>
        <link href="assets/admin/layout4/css/layout.css" rel="stylesheet" type="text/css"/>
        <link href="assets/admin/layout4/css/themes/light.css" rel="stylesheet" type="text/css" id="style_color"/>
        <link rel="stylesheet" type="text/css" href="assets/global/plugins/icheck/skins/all.css">
        <link rel="stylesheet" type="text/css" href="css/login.css">

        <!-- END THEME STYLES -->


        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
        <!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
        <script src="assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>


        <script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="assets/global/scripts/metronic.js" type="text/javascript"></script>
        <script src="assets/admin/layout4/scripts/layout.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/icheck/icheck.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/notify.min.js"></script>
        <script src="js/md5.js"></script>
        <script>
            jQuery(document).ready(function () {
                // initiate layout and plugins
                Metronic.init(); // init metronic core components
                Layout.init(); // init current layout
                $('#remeberSelect').iCheck({
                    checkboxClass: 'icheckbox_minimal',
                    radioClass: 'iradio_minimal'

                });
                $('#username').focus();
                if (localStorage) {
                    if (localStorage.getItem('treatwell_user_name')) {
                        document.getElementById("username").value = localStorage.getItem('treatwell_user_name');
                    }
                    if (localStorage.getItem('treatwell_password')) {
                        $('#remeberSelect').iCheck('check');
                        document.getElementById("password").value = localStorage.getItem('treatwell_password');
                    }
                }
                if ($('#displayMsg').val() !== '') {
                    if ($('#displayMsg').val() === 'invalid') {
                        $('#username').notify("Invalid UserName/Password.", "error", {autoHideDelay: 15000});
                    } else if ($('#displayMsg').val() === 'empty') {
                        $('#username').notify("UserName/Password cannot be empty.", "error", {autoHideDelay: 15000});
                    } else if ($('#displayMsg').val() === 'invalid ip') {
                        $('#username').notify("You are not allowed to login from other system.", "error", {autoHideDelay: 15000});
                    } else if ($('#displayMsg').val() === 'logged Out') {
                        $.notify("Logged Out Successfully", "success", {autoHideDelay: 15000});
                    } else if ($('#displayMsg').val() === 'session') {
                        $.notify("Your Session has expired. Please Login again.", "error", {autoHideDelay: 15000});
                    } else if ($('#displayMsg').val() === 'not allowed') {
                        $('#username').notify("You are not allowed to login.", "error", {autoHideDelay: 15000});
                    }
                }
                $('#loginButton').click(function () {
                    if (validateFun()) {
                        localStorage.setItem('treatwell_user_name', document.getElementById("username").value);
                        if ($('#remeberSelect').is(':checked')) {
                            localStorage.setItem('treatwell_password', document.getElementById("password").value);
                        } else {
                            localStorage.removeItem('treatwell_password');
                        }
                        encryptPassword();
                        document.getElementById("loginForm").action = "login.htm?action=processLogin";
                        document.getElementById("loginForm").submit();
                    }
                    return false;
                });
            });
            function encryptPassword() {
                var pass = document.getElementById('password').value;
                if (pass !== '') {
                    var encryptpass = calcMD5(pass);
                    document.getElementById('password').value = encryptpass;
                }
            }
            function validateFun() {
                if (document.getElementById("username").value === '') {
                    $('#username').notify("User Name cannot be empty.", "error", {autoHideDelay: 15000});
                    $('#username').focus();
                    return false;
                }
                if (document.getElementById("password").value === '') {
                    $('#password').notify("Password cannot be empty.", "error", {autoHideDelay: 15000});
                    $('#password').focus();
                    return false;
                }
                return true;
            }
        </script>

    </head>
    <body class="page-md login">
        <div class="logo" style="padding-top: 0px;">
            <a href="login.htm?action=login">
                <img src="images/ezimedic_png.png" alt="Ezimedic" style="height: 75px;"  />
            </a>
        </div>
        <div class="content">
            <form class="login-form" action="login.htm?action=processLogin" method="post" id="loginForm" onsubmit="return validateFun();">
                <input type="hidden" id="displayMsg" value="${requestScope.refData.msg}">
                <h3 class="form-title">Sign In</h3>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">User Name</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="User Name" id="username" name="username">
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Password</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" id="password" name="password">
                </div>
                <div class="form-actions">
                    <button type="button" class="btn btn-danger uppercase btn-block" id="loginButton" >Login</button><br/>
                    <a class="btn btn-success btn-block uppercase" href="http://ezimedic.com/web.htm?action=signUp">Register Now</a>
                    <br/>
                    <div style="text-align: center;">
                        <input type="checkbox" id="remeberSelect" value="Y" > Remember me
                    </div>
                </div>
                <div class="text-right" style="margin-top: 13px;"> 
                    <span style="font-weight: bold;">Powered By: </span>
                    <a href="http://www.fabsolution.net/" target="_blank">
                        <img src="images/fabsol.png" alt="" style="height: 50px;" />
                    </a>
                </div>
            </form>
        </div>
        <!--div class="copyright">
            <a href="http://www.treatwellservices.com/" target="_blank">© TreatWell Services (Pvt) Ltd.</a>
        </div-->
    </body>
    <style>

    </style>
</html>
