<%-- 
    Document   : Login
    Created on : Mar 16, 2016, 5:20:14 PM
    Author     : WeeJing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
       <jsp:include page="MasterFront.jsp"/>
        <title>Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    
     
    <body onload="wrongCredentials()">
        <br/>
        <br/>
        <form class="form-horizontal" action ="home" method="POST">
            <div class ="form-group">
            <label for ="uName"class ="col-sm-2"> Username</label>
            <div class ="col-sm-4">
                <input type ="text" class="form-control" name="uName" required>
            </div>
            </div>
            
            
             <div class ="form-group">
            <label for ="password"class ="col-sm-2"> Password</label>
            <div class ="col-sm-4">
                <input type ="password" class="form-control" name="passWord" required>
            </div>
            </div>
            
            <div class ="modal-footer-left">
                <button type="submit" class ="btn btn-primary">Submit</button>
            </div>
                       
        </form>
    </body>
    
   
     <script type ="text/javascript"> 
        function wrongCredentials(){
         
            var error = "<%= request.getAttribute("error") %>";
           
            if(error !== "null")
            {
                alert("Your username or password is wrong");
            }            
        }       
    </script>
    
</html> 