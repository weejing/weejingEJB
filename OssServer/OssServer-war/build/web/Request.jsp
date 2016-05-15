<%-- 
    Document   : Request
    Created on : Mar 25, 2016, 7:49:44 PM
    Author     : WeeJing
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, java.util.Vector"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <jsp:include page="MasterPage.jsp"/>

        <title>Request</title>
    </head>
    <body>
        <h2>Request details</h2>
        <br/>
        <div id ="requestTable">
            <table class ="features-table">
                <thead>
                    <tr>
                        <td>Date</td>
                        <td>State</td>
                        <td>Message</td>
                        <td>Comment</td>            
                    </tr>
                </thead>
                <tbody>
                    <%
            ArrayList listOfRequest = (ArrayList)request.getAttribute("request");
            for(Object object: listOfRequest)
            {
                Vector requests = (Vector)object;
         %> 
                    
                 <tr>
                     <td><%=requests.get(0)%></td>
                     <td><%=requests.get(1)%></td>
                     <td><%=requests.get(2)%></td>
                     <td><%=requests.get(3)%></td>
                 </tr>
                 <% 
                   }
                     %>
                </tbody>
                   
                
            </table>
        </div>
                <br/>
                <br/>
    </body>
</html>
