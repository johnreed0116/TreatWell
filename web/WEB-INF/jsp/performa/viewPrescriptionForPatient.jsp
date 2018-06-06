<%-- 
    Document   : viewPrescriptionForPatient
    Created on : Feb 1, 2018, 2:20:32 AM
    Author     : Ali Zaidi
--%>
<%@include file="../header.jsp"%>
<script>
    function printPrescription(masterId) {
        document.getElementById("prescForm").action = 'report.htm?action=reportPrescriptionById&prescriptionId=' + masterId;
        document.getElementById("prescForm").target = '_blank';
        document.getElementById("prescForm").submit();
    }
</script>
<form action="#" onsubmit="return false;" role="form" method="post" id="prescForm"></form>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>View Prescriptions</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet-body">
            <div class="portlet box green">
                <div class="portlet-title">
                    <div class="caption">
                        View Prescriptions
                    </div>
                </div>
                <div class="portlet-body">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>Sr.#</th>
                                <th>Doctor Name</th>
                                <th>Clinic Name</th>
                                <th>Date & Time</th>
                                <th>Remarks</th>
                                <th>&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.refData.Precription}" var="obj" varStatus="i">
                                <tr>
                                    <td>${i.count}</td>
                                    <td>${obj.DOCTOR_NME}</td>
                                    <td>${obj.CLINIC_NME}</td>
                                    <td>${obj.PREPARED_DTE}</td>
                                    <td>${obj.REMARKS}</td>
                                    <td><i class="fa fa-print" aria-hidden="true" title="Click to Print" style="cursor: pointer;" onclick="printPrescription(' ${obj.TW_PRESCRIPTION_MASTER_ID }');"></i></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>
