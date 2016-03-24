<%-- 
    Document   : ListOrders
    Created on : Mar 19, 2016, 1:46:52 AM
    Author     : WeeJing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, java.util.Vector"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <jsp:include page="MasterPage.jsp"/>
        <title>Orders list</title>
    </head>
    <body>
        

        <% 
          ArrayList data = (ArrayList)request.getAttribute("orders");
          for(Object object: data){
              Vector order = (Vector)object;
              
           %>
           <div class="container">
               <div class="row" style="padding-right:5; text-align:left">
                   <div class ="products">
                       <h3> <%= order.get(0)%> </h3>
                       <hr>
                       <p>  
                           Total Value:<%= order.get(1)%> <br/>
                           State: <%= order.get(3)%>
                       </p> 
                      <a href ="orderDetails?orderId=<%=order.get(2)%>&orderTime=<%= order.get(0)%>&orderValue=<%= order.get(1)%>&orderState=<%= order.get(3)%>" class ="btn btn-danger">View</a>
                      <% 
                          if(order.get(4).equals("true"))
                          {
                      %>
                      <a href="payment?orderId=<%=order.get(2)%>&Price=<%=order.get(1)%>" class ="btn btn-primary">Pay</a>
                   <% 
                     }
                    %>
                   </div>
              
                
        <%   
          }
        %>
         </div>
           </div>
      <br/>
      <br/>
    </body>
</html>
