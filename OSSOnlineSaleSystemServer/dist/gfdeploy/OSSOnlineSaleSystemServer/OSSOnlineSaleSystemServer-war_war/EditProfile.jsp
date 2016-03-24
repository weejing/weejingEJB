<%-- 
    Document   : EditProfile
    Created on : Mar 21, 2016, 3:12:00 PM
    Author     : WeeJing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="MasterPage.jsp"/>
        <title>Update Profile</title>
    </head>
    <body>
            <div class ="main-content"> 
                <form class ="form-basic" action="profile"> 
                    
                    <div class ="form-title-row"> 
                        <h1> Update Profile </h1>
                    </div>
                    
                    <div class ="form-row"> 
                        <label>
                          <span>Email</span>
                          <input type="email" name="email" placeholder="<%= request.getAttribute("email")%>"/>
                        </label>
                    </div>
                    
                    <div class ="form-row"> 
                        <label>
                           <span>Address</span>
                           <input type ="text" name="address" placeholder="<%=request.getAttribute("address")%>"/>
                        </label>
                    </div>
                    
                    <div class ="form-row">
                        <label>
                            <span>Number</span>
                            <input type="number" name="number" placeholder="<%=request.getAttribute("number")%>"/>
                        </label>
                    </div> 
                    
                    <div class ="col-sm-8"> 
                <button type="submit">Submit Form</button>
                    </div>
                </form>
            </div> 
    </body>
</html>
