<%-- 
    Document   : ShoppingCart
    Created on : Mar 16, 2016, 9:11:03 PM
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
        <title>Shopping Cart</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-xs-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <div class="panel-title">
                                <div class="row">
                                    <div class="col-md-6">
                                        <h5><span class="glyphicon glyphicon-shopping-cart"</span>Cart</h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class ="panel-body">
                            
                            <% ArrayList data = (ArrayList)request.getAttribute("data");
                                System.out.println("how big is array: "+ data.size());
                                   for(int count=1; count<data.size(); count++){
                                       Vector orderLine = (Vector)data.get(count);                          
                                %>
                                
                            <div class="row">
                                <div class="col-md-3 col-xs-12">
                                    <h4>  <strong><%=orderLine.get(1)%></strong></h4>
                                    <h4> <small>brand: <%=orderLine.get(0)%>  price: <%=orderLine.get(2)%> </small></h4>
                                </div>
                                <div class="col-md-6 col-xs-12">
                                    <div class="col-md-6 text-right">
                                        <h4><strong><%=orderLine.get(3)%>x </strong></h4>
                                    </div>
                                    <div class="col-md-4 col-xs-9">
                                        <h4> <strong>$<%=orderLine.get(4)%> </strong></h4>
                                    </div>
                                    <div class="col-md-2 col-xs-2">
                                        <a href="deleteItem?prodId=<%= orderLine.get(5)%>" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></a>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <% 
                               }
                            %>
                        </div>
                        <div class ="panel-footer">
                            <div class ="row">
                                <div class ="col-md-9 col-xs-12">
                                    <h4><Strong> <%=data.get(0)%></Strong></h4>
                                </div>
                                <div class="col-md-3 col-sx-12">
                                    <a href="payment?Price=<%=data.get(0)+""%>" class="btn btn-success btn-sm btn-block">Checkout</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
