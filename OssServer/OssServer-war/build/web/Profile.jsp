<%-- 
    Document   : Profile
    Created on : Mar 25, 2016, 2:26:45 PM
    Author     : WeeJing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="MasterPage.jsp"/>
        <title>Profile</title>
    </head>
    <body>
        <div class ="container">
            <div class ="page-header">
                User Profile
            </div>
            
            <div class ="col-sm-6">
                <p>
                    Email: <%= request.getAttribute("email")%> <br/>
                    Address: <%= request.getAttribute("address")%> <br/>
                    Number: <%= request.getAttribute("number")%> <br/>
                </p>
                
                <a href ="editProfile?email=<%= request.getAttribute("email")%>&address=<%= request.getAttribute("address")%> &number=<%= request.getAttribute("number")%>" 
                   class ="btn btn-success" role ="button">Edit</a>
            </div>
        </div>
    </body>
</html>