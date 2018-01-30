<%-- 
    Document   : viewPatientsSearch
    Created on : Oct 20, 2017, 6:42:09 PM
    Author     : farazahmad
--%>
<%@include file="../header.jsp"%>
<script>
    $(function () {

    });
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Patient Searching</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Searching Result
                </div>
            </div>
            <div class="portlet-body">
                <form action="#" onsubmit="return false;" role="form" method="post">

                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr>
                                <th width="5%">Sr. #</th>
                                <th width="30%">Patient Name</th>
                                <th width="10%">Mobile No.</th>
                                <th width="55%">&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.refData.results}" var="obj" varStatus="i">
                                <tr>
                                    <td>${i.count}</td>
                                    <td>${obj.PATIENT_NME}</td>
                                    <td>${obj.MOBILE_NO}</td>
                                    <td>&nbsp;</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="../footer.jsp"%>
