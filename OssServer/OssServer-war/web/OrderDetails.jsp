<%-- 
    Document   : OrderDetails
    Created on : Mar 25, 2016, 2:26:21 PM
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
        <title>Order details</title>
    </head>
    <body>
        <h2> Order Details</h2><br/>
        <p>
          Date: <%= request.getAttribute("date")%> <br/>
          Total: <%= request.getAttribute("amount")%> <br/>
          State: <%= request.getAttribute("state")%>
        </p>
        <br/>
        <div id ="orderTable">
            <table class="features-table">
                <thead>
                    <tr>
                        <td>Type</td>
                        <td>Brand</td>
                        <td>Model</td>
                        <td>Price</td>
                        <td>Quantity</td>
                        <td>Total</td>
                    </tr>
                </thead>
                <tbody>
           <%
            ArrayList orderDetails = (ArrayList)request.getAttribute("orderDetails");
            for(Object object: (List<Vector>)orderDetails.get(0))
            {
                Vector orderLine = (Vector)object;
         %>
         <tr>
             <td><%= orderLine.get(0)%></td>
             <td><%= orderLine.get(1)%></td>
             <td><%= orderLine.get(2)%></td>             
             <td><%= orderLine.get(3)%></td>            
             <td><%= orderLine.get(4)%></td>  
             <td><%= orderLine.get(5)%></td>
         </tr>
         <%
            }
        %>
                </tbody>
            </table>  
        </div>
        
                <% 
                   if(orderDetails.size() >1)
                   {
                       Vector delivery = (Vector)orderDetails.get(1);
                 %>
                 <h2> Delivery details </h2>  
                 <br/>
                 <p>
                     Shipment Date: <%= delivery.get(0)%> <br/>
                     Tracking Num: <%= delivery.get(1)%> <br/>
                     Expected delivery: <%= delivery.get(2)%>                    
                 </p> <br/>
                 <div id ="deliverTable">
                     <table class ="features-table">
                         <thead>
                             <tr>
                                 <td>Type</td>
                                 <td>Brand</td>
                                 <td>Model</td>
                                 <td>Quantity</td>
                             </tr>
                         </thead>
                         <tbody>
                             <%
                             for(Object object: (List<Vector>)orderDetails.get(2))
                             {
                                 Vector deliveryLine = (Vector)object;
                              %>
                              <tr>
                                  <td><%= deliveryLine.get(0) %></td> 
                                  <td><%= deliveryLine.get(1) %></td>
                                  <td><%= deliveryLine.get(2) %></td>
                                  <td><%= deliveryLine.get(3) %></td>
                              </tr> 
                              <%
                             }
                             %>
                         </tbody>
                     </table>
                 </div>
                 <%
                   }
                %>  
                <br/>
                <br/>
                <br/>
                <br/>
    </body>
</html>
