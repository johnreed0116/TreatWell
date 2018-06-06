<%-- 
    Document   : viewLabTestDetailsForDoctor
    Created on : Jun 6, 2018, 3:18:54 PM
    Author     : farazahmad
--%>
<%@include file="../header.jsp"%>
<form action="#" onsubmit="return false;" role="form" method="post" id="prescForm"></form>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>View Lab Tests</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet-body">
            <div class="portlet box green">
                <div class="portlet-title">
                    <div class="caption">
                        View Lab Tests
                    </div>
                </div>
                <div class="portlet-body">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>Sr.#</th>
                                <th>Patient Name</th>
                                <th>Test Name</th>
                                <th>Recommended Lab</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.refData.labReports}" var="obj" varStatus="i">
                                <tr>
                                    <td align="center">${i.count}</td>
                                    <td>${obj.PATIENT_NME}</td>
                                    <td>${obj.TEST_NME}</td>
                                    <td>${obj.CENTER_NME}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${obj.TOTAL_ATTACHMENTS !='0'}">DONE</c:when>
                                            <c:otherwise>
                                                PENDING
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
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
