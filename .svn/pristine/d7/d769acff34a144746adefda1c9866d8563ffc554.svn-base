<%-- 
    Document   : addUser
    Created on : Oct 10, 2017, 7:01:27 PM
    Author     : Cori 5
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('.date-picker').datepicker({
            autoclose: true
        });
    });
 
    
    function saveData() {
        if($.trim($('#userName').val()) === ''){
            $('#userName').notify('User Name is Required Field','error',{autoHideDelay:15000});
            $('#userName').focus();
            return false;
        }
        if($.trim($('#password').val()) === ''){
            $('#password').notify('Password is Required Field','error',{autoHideDelay:15000});
            $('#password').focus();
            return false;
        }
         if($.trim($('#reTypePassword').val()) === ''){
            $('#reTypePassword').notify('ReType Password Field is Required','error',{autoHideDelay:15000});
            $('#reTypePassword').focus();
            return false;
        }
        
        
       
        if($.trim($('#password').val()) !==$.trim($('#reTypePassword').val())){
            $('#reTypePassword').notify('Please corfirm the same password','error',{autoHideDelay:15000});
            $('#reTypePassword').focus();
            return false;
        }
       
        
        
//        var obj = {
//            patientName:$('#patientName').val(),contactNo:$('#contactNo').val(),age:$('#age').val(),
//            dob:$('#dob').val(),patientWeight:$('#patientWeight').val(),patientHeight:$('#patientHeight').val(),
//            patientAddress:$('#patientAddress').val(),gender:$('input[name=gender]:checked').val(),
//            attendClinic:$('input[name=attendClinic]:checked').val(),
//            medicineOpt:$('input[name=medicineOpt]:checked').val(),
//            steroidOpt:$('input[name=steroidOpt]:checked').val(),
//            allergy:$('input[name=allergy]:checked').val(),
//            Rheumatic:$('input[name=Rheumatic]:checked').val(),
//            smoker:$('input[name=smoker]:checked').val()
//        };
//        $.post('setup.htm?action=savePatient',obj,function (res) {
//            var obj = $.parseJSON(res);
//            if (obj.result === 'save_success'){
//                $('#saveBtn').notify('Save Succesfully','success',{autoHideDelay:15000});
//                $('input:text').val('');
//                $('#patientAddress').val('');
//                return false;
//            }
//            else {
//                $('#saveBtn').notify('Erorr in saving','error',{autoHideDelay:15000});
//                return false;
//            }
//        });
    }
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>User Registration</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title tabbable-line">
                <div class="caption">
                    User Info 
                </div>
            </div>
            <div class="portlet-body">
                <form action="#" role="form" method="post" onsubmit="return false;">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>First Name</label>
                                    <div>
                                        <input type="text" class="form-control" id="firstName" placeholder="First Name" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Last Name</label>
                                    <div>
                                        <input type="text" class="form-control" id="lastName" placeholder="Last Name" >
                                    </div>
                                </div>
                            </div>       
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>User Name</label>
                                <input type="text" class="form-control" id="userName" placeholder="User Name">
                            </div>
                        </div>
                    </div>
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Password</label>
                                    <div>
                                        <input type="password" class="form-control" id="password" placeholder="Password" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Re-Type Password</label>
                                    <div>
                                        <input type="password" class="form-control" id="reTypePassword" placeholder="Retype Password" >
                                    </div>
                                </div>
                            </div>       
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Email</label>
                                <input type="text" class="form-control" id="email" placeholder="Email">
                            </div>
                        </div>
                    </div>
                    <div class="form-actions right">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row">
                                        <div class="col-md-offset-9 col-md-3">
                                            <button onclick="saveData();" id="saveBtn" class="btn green">Save</button>
                                            <button type="button" class="btn default">Cancel</button>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                </form>
                <!-- END FORM-->
            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>

