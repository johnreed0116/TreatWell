<%-- 
    Document   : assignRoleRights
    Created on : Dec 23, 2017, 1:13:58 PM
    Author     : farazahmad
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        if ($('#res_msg').val() !== '') {
            if ($('#res_msg').val() === 'saved') {
                $.bootstrapGrowl("User rights saved successfully.", {
                    ele: 'body',
                    type: 'success',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
            } else {
                $.bootstrapGrowl("Error in saving user rights. ", {
                    ele: 'body',
                    type: 'danger',
                    offset: {from: 'top', amount: 80},
                    align: 'right',
                    allow_dismiss: true,
                    stackup_spacing: 10
                });
            }
        }
        $('#roleList').select2();
        displayRights();

    });
    function displayRights() {
        $.get('ums.htm?action=getRoleRights', {roleId: $('#roleList').val()}, function (htm) {
            $('#displayDiv').html(htm);
            $('.selectAllRightsBtn').on('ifChecked', function (event) {
                $("input.assignRight").each(function (i, o) {
                    $(o).iCheck('check');
                });
            });
            $('.selectAllRightsBtn').on('ifUnchecked', function (event) {
                $("input.assignRight").each(function (i, o) {
                    $(o).iCheck('uncheck');
                });
            });
            $('input:checkbox').iCheck({
                checkboxClass: 'icheckbox_minimal',
                radioClass: 'iradio_minimal',
                increaseArea: '20%' // optional
            });
        }, 'html');
    }
    function saveRoleRights() {
        $('#form_1').submit();
    }
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Rights Management</h1>
    </div>
</div>
<input type="hidden" id="res_msg" value="${requestScope.refData.msg}">
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    Role Rights
                </div>
            </div>
            <div class="portlet-body">
                <form action="ums.htm?action=processAssignRoleRights" role="form" method="post" id="form_1">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Role</label>
                                    <select id="roleList" name="roleList" class="form-control" onchange="displayRights();">
                                        <c:forEach items="${requestScope.refData.roles}" var="obj">
                                            <option value="${obj.TW_ROLE_ID}"
                                                    <c:if test="${requestScope.refData.selectedUser==obj.TW_ROLE_ID}" >
                                                        selected="selected"
                                                    </c:if>
                                                    >${obj.ROLE_NME}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div id="displayDiv">

                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>