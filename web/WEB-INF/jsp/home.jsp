<%-- 
    Document   : home
    Created on : Oct 11, 2017, 11:34:44 AM
    Author     : farazahmad
--%>

<%@include file="header.jsp"%>
<script>
    $(function () {
        if ($('#userType').val() === 'DOCTOR') {
            getAppointments();
        } else if ($('#userType').val() === 'PATIENT') {
            getPatientInfo();
        }
    });
    function getAppointments() {
        $.get('login.htm?action=getDoctorDashBoard', {},
                function (list) {
                    if (list.length) {
                        for (var i = 0; i < list.length; i++) {
                            if (list[i].TITLE === 'REGISTERED_PATIENT') {
                                $('#totalRegsteredDiv').html(list[i].TOTAL);
                            } else if (list[i].TITLE === 'TOTAL_CHECKED') {
                                $('#totalCheckedDiv').html(list[i].TOTAL);
                            } else if (list[i].TITLE === 'TOTAL_APPOINTMENTS') {
                                $('#totalAppointmentsDiv').html(list[i].TOTAL);
                            }
                        }
                    }

                }, 'json');
    }
    function getPatientInfo() {
        $.get('login.htm?action=getDoctorDashBoard', {},
                function (list) {
                    if (list.length) {
                        for (var i = 0; i < list.length; i++) {
                            if (list[i].TITLE === 'NEXT_APPOINTMENT') {
                                $('#nextAppointmentDiv').html(list[i].NEXT_APP);
                            } else if (list[i].TITLE === 'TOTAL_PRESC') {
                                $('#prevPrescDiv').html(list[i].TOTAL);
                            }
                        }
                    }

                }, 'json');
    }
    function openPage(page) {
        if (page === 'Appointments') {
            document.getElementById("dashBoardForm").action = 'performa.htm?action=viewAppointments';
            document.getElementById("dashBoardForm").submit();
        }
        if (page === 'previousPrescriptions') {
            document.getElementById("dashBoardForm").action = 'performa.htm?action=viewPrescriptionForPatient';
            document.getElementById("dashBoardForm").submit();
        }
        if (page === 'editProfile') {
            document.getElementById("dashBoardForm").action = 'performa.htm?action=editPatientProfile';
            document.getElementById("dashBoardForm").submit();
        }
    }
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Dashboard</h1>
    </div>
</div>
<input type="hidden" id="userType" value="${sessionScope.userType}">
<form method="post" action="#" onsubmit="return false;" id="dashBoardForm">
    <c:choose>
        <c:when test="${sessionScope.userType=='DOCTOR'}">
            <div class="row margin-top-10">
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="tiles">
                        <div class="tile double bg-red-sunglo" onclick="openPage('Appointments');">
                            <div class="tile-body">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <div class="tile-object">
                                <div class="name">
                                    Appointments
                                </div>
                                <div class="number" id="totalAppointmentsDiv">
                                    0
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="tiles">
                        <div class="tile double bg-purple-studio">
                            <div class="tile-body">
                                <i class="fa fa-address-book"></i>
                            </div>
                            <div class="tile-object">
                                <div class="name">
                                    Registered Patients
                                </div>
                                <div class="number" id="totalRegsteredDiv">
                                    0
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="tiles">
                        <div class="tile double bg-red-intense">
                            <div class="tile-body">
                                <i class="fa fa-user-md"></i>
                            </div>
                            <div class="tile-object">
                                <div class="name">
                                    Patients Checked
                                </div>
                                <div class="number" id="totalCheckedDiv">
                                    0
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:when test="${sessionScope.userType=='PATIENT'}">
            <div class="row margin-top-10">
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="tiles">
                        <div class="tile double bg-red-sunglo">
                            <div class="tile-body">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <div class="tile-object">
                                <div class="name">
                                    Next Appointment
                                </div>
                                <div class="number" id="nextAppointmentDiv">
                                    N/A
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="tiles">
                        <div class="tile double bg-blue-madison"  onclick="openPage('previousPrescriptions');">
                            <div class="tile-body">
                                <i class="fa fa-hospital-o"></i>
                            </div>
                            <div class="tile-object">
                                <div class="name">
                                    Total Prescriptions
                                </div>
                                <div class="number" id="prevPrescDiv">
                                    0
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="tiles">
                        <div class="tile double bg-green"  onclick="openPage('editProfile');">
                            <div class="tile-body">
                                <i class="fa fa-user"></i>
                            </div>
                            <div class="tile-object">
                                <div class="name">
                                    Edit Profile
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>
</form>
<%@include file="footer.jsp"%>

