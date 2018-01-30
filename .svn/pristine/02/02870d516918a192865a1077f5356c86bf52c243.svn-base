<%-- 
    Document   : addDoctorInfo
    Created on : Oct 21, 2017, 3:45:06 PM
    Author     : Cori 5
--%>

<%@include file="../header.jsp"%>
<script>
    $(function () {
        $('.date-picker').datepicker({
            autoclose: true
        });
    });
</script>
<div class="page-head">
    <!-- BEGIN PAGE TITLE -->
    <div class="page-title">
        <h1>Doctor Registration</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="portlet box green">
            <div class="portlet-title">
                <div class="caption">
                    Doctor Personal Info 
                    </div>
                    <ul class="nav nav-tabs">
                          <li class="active"><a data-toggle="tab" href="#home">Personal Info</a></li>
                          <li><a data-toggle="tab" href="#menu1">Education</a></li>
                          <li><a data-toggle="tab" href="#menu2">Experience</a></li>                         
                    </ul>
                </div>
        </div>
    </div>
</div>
            <div class="tab-content">
                <div id="home" class="tab-pane fade in active">
                    <div class="portlet box green">
                    <div class="portlet-body">
                      <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Name</label>
                                    <div>
                                        <input type="text" class="form-control" id="doctorName" placeholder="Doctor Name" >
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Email</label>
                                    <div>
                                        <input type="text" class="form-control" id="email" placeholder="Email" >
                                    </div>
                                </div>
                            </div>
                        </div>                    
                       <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Doctor Type</label>
                                    <select id="doctorType" class="form-control">
                                        <c:forEach items="${requestScope.refData.categories}" var="obj">
                                            <option value="${obj.TW_DOCTOR_CATEGORY_ID}">${obj.TITLE}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>CNIC</label>
                                    <div>
                                        <input type="text" class="form-control" id="cnic" placeholder="3500000000000" onkeyup="onlyInteger(this);" maxlength="13">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Cell No.</label>
                                    <div>
                                        <input type="text"   class="form-control" id="cellNo" placeholder="0300xxxxxxx" onkeyup="onlyInteger(this);" maxlength="11" >
                                    </div>
                                </div>
                            </div>
                        </div>
                     <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Address</label>
                                    <textarea class="form-control" id="doctorAddress" rows="2" cols="30"></textarea>
                                </div>
                            </div>                        
                        </div>     
                        <div class="form-actions right">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row">
                                        <div class="col-md-offset-9 col-md-3">
                                            <button type="button" value="Submit" id="saveBtn" onclick="getDoctorPersonalDetails();" class="btn green">Save</button>
                                            <button type="button" value="Submit"  class="btn default">Cancel</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                           </div>
                             </div>
                              </div>
                  <div id="menu1" class="tab-pane fade">
                        <div class="portlet box green">
                         <div class="portlet-body">
                           <div class="row">
                              <div class="col-md-5">
                               <div class="form-group">
                                <label>Degree</label>
                                <select id="degree" class="form-control">
                                    <option value="CAPSULE">MBBS</option>
                                    <option value="TABLET">BDS</option>                                   
                                </select>
                            </div>
                        </div>
                            <div class="col-md-7">
                            <div class="form-group">
                                <label>Institute Where From</label>
                                <select id="institute" class="form-control">
                                    <option value="CAPSULE">KInnard</option>
                                    <option value="TABLET">King Edward</option>
                                </select>
                            </div>
                        </div>                  
                   </div> 
                        <div class="row">
                         <div class="col-md-4">
                                <div class="form-group">
                                    <label>Attach Scanned Copy of Degree </label>
                                    <input id="fileInput" name="FileButton" class="input-file" type="file">
                                </div>
                            </div>
                    </div>
                          <div class="form-actions right">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row">
                                        <div class="col-md-offset-9 col-md-3">
                                            <button type="button" value="Submit" id="saveBtn" onclick="getDoctorEducationalDetails();" class="btn green">Save</button>
                                            <button type="button" value="Submit"  class="btn default">Cancel</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                       </div>   
                 </div>
             </div>                          
    <div id="menu2" class="tab-pane fade">
         <div class="portlet box green">
         <div class="portlet-body">
                 <div class="row">
                             <div class="col-md-12">
                            <div class="form-group">
                                <label>Affiliated With</label>
                                <select id="affiliation" class="form-control">
                                    <option value="Punjab">Punjab University</option>
                                    <option value="BDS">BDS</option>                                   
                                </select>
                            </div>
                        </div>                           
                      </div>
                        <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Duration Of Service From</label>
                                <div class="input-group input-medium date date-picker">
                                    <input id="durationFrom" type="text" class="form-control" readonly="">
                                    <span class="input-group-btn">
                                        <button class="btn default" type="button"><i  class="fa fa-calendar"></i></button>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>To</label>
                                <div class="input-group input-medium date date-picker">
                                    <input id="durationTo" type="text" class="form-control" readonly="">
                                    <span class="input-group-btn">
                                        <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
                                    </span>
                                </div>
                            </div>
                        </div>
                       <div class="col-md-4">
                           <div class="form-group">
                                    <label>Serving</label>
                                   <div class="input-group">
                                        <div class="icheck-inline" id="serving">
                                            <input id="s_fac" type="checkbox" class="sev_check" value="Y" name="serving">
                                     </div>
                                    </div>
                                </div>
                        </div>
                    </div>
             <div class="form-actions right">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row">
                                        <div class="col-md-offset-9 col-md-3">
                                            <button type="button" value="Submit" id="saveBtn" onclick="getDoctorExperienceDetails();" class="btn green">Save</button>
                                            <button type="button" value="Submit"  class="btn default">Cancel</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                       </div>
             
                         </div>
                           </div>
                            </div>
                             
                        
<script>

    function getDoctorPersonalDetails() {
        
           if($.trim($('#doctorName').val()) === ''){
            $('#doctorName').notify('Doctor Name is Required Field','error',{autoHideDelay:15000});
            $('#doctorName').focus();
            return false;
           }
          if($.trim($('#cellNo').val()) === ''){
            $('#cellNo').notify('Contact is Required Field','error',{autoHideDelay:15000});
            $('#cellNo').focus();
            return false;
          }
 
        var obj = {
            doctorName: $('#doctorName').val(), 
            email: $('#email').val(),
            doctorType: $('#doctorType').val(),
            cnic: $('#cnic').val(),
            cellNo: $('#cellNo').val(),
            doctorAddress: $('#doctorAddress').val()
                    };
                  
//
//          $.post('setup.htm?action=savePatient',obj,function (res) {
//            var obj = $.parseJSON(res);
//            if (obj.result === 'save_success'){
//                $('#saveBtn').notify('Save Succesfully','success',{autoHideDelay:15000});
//                $('input:text').val('');
//                $('#doctorAddress').val('');
//               
//                
//                return false;
//            }
//            else {
//                $('#saveBtn').notify('Erorr in saving','error',{autoHideDelay:15000});
//                return false;
//            }
//        });
         console.log(obj);     
                    return false;
    }
    
    jQuery.fn.getCheckboxVal = function(){
	var vals = [];
	var i = 0;
	this.each(function(){
		vals[i++] = jQuery(this).val();
	});
	return vals;
};


 function getDoctorEducationalDetails() {
        var obj = {
            degree: $('#degree').val(),
             institute: $('#institute').val(),
            fileInput: $('#fileInput').val()};
            console.log(obj);
            return false;
    }
    
    
    function getDoctorExperienceDetails() {
        var obj = {
            institute: $('#institute').val(),
            durationFrom: $('#durationFrom').val(),
            durationTo: $('#durationTo').val(),
            serving:$("input[name='serving']:checked").val()
                };
            console.log(obj);
            return false;
    }


</script>

<%@include file="../footer.jsp"%>
