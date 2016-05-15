<%-- 
    Document   : ListProducts
    Created on : Mar 25, 2016, 2:25:20 PM
    Author     : WeeJing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, java.util.Vector"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <jsp:include page="MasterPage.jsp"/>
        <title>Products</title>
    </head>
    <body>
        <% 
           ArrayList data =(ArrayList)request.getAttribute("data");
           %>
           <h2> Your search returned <%=data.size()%> results</h2>
               
           <%
           for(Object object: data){
               Vector product = (Vector)object; 
            %>   
        <div class ="container">
                <div class ="row" style= "padding-right:5; text-align:left;">
                    <div class ="products">
                        <h3><%= (String)product.get(0)%></h3>
                        <hr>
                        <p font color ="#FFFFFF">Brand: <%= (String)product.get(1)%> <br/>
                           Model: <%= (String)product.get(2)%> <br/>
                           Price: <%= product.get(3)+""%>  
                        </p>
                        <a href ="displayProduct?brand=<%=(String)product.get(1)%>&model=<%=(String)product.get(2)%>" class ="btn btn-danger">View</a>        
                    </div>
                                                       
                         <%
           }
       %>
                    
         </div>
                
            </div>
        
       
    </body>
</html>