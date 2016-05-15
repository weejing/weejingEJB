<%-- 
    Document   : Register
    Created on : Mar 25, 2016, 2:26:57 PM
    Author     : WeeJing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
       <jsp:include page="MasterFront.jsp"/>
        <title>Register</title>
    </head>
    
    <body onload ="takenUser()">
            
     <div class ="main-content">
        <form action ="home" class="form-basic" method = "GET">
            
            <div class ="form-title-row"> 
                <h1> Register </h1>
            </div>
            
     
            <div class="form-row"> 
                <label>
                <span> User Name </span>
                <input type = "text" name = "uname" placeholder="Enter a user name" required/>
                </label>
            </div>
            
             <div class="form-row"> 
                <label>
                <span> Password </span>
                <input type = "password" name = "password" placeholder="Enter a password" required/>
                </label>
            </div>
            
            <div class="form-row"> 
                <label>
                <span> Address</span>
                <input type = "text" name = "address" placeholder="Enter your address" required/>
                </label>
            </div>
           
            <div class="form-row"> 
                <label>
                <span> Contact Number </span>
                <input type = "text" pattern="[0-9]{8}" name = "number" placeholder="Enter your contact number" required title ="Only 0-9 allowed and length must be 8"/>
                </label>
            </div>
           
            <div class="form-row"> 
                <label>
                <span> Email Address </span>
                <input type = "email" name = "email" placeholder="Enter your email" required/>
                </label>
            </div>
           
             <div class="col-sm-8"> 
                <button type="submit">Submit Form</button>
             </div>
            
        </form>
     </div>          
        <br/>
        
  
        <script type="text/javascript">
            function takenUser(){
              
              var error = "<%= request.getAttribute("error") %>";
           
               if(error !== "null")
               {
                 alert("This username has been taken");
               }        
            }
            </script>
        
    </body>
</html>