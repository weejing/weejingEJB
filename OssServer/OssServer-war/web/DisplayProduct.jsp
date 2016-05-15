<%-- 
    Document   : DisplayProduct
    Created on : Mar 25, 2016, 2:24:09 PM
    Author     : WeeJing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <jsp:include page="MasterPage.jsp"/>
         <% ArrayList data = (ArrayList)request.getAttribute("data");%>
        <title><%= (String)data.get(2) %></title>
    </head>
    <body>
        <div class="container">
            <div class ="page-header">
                <h1><%= (String)data.get(2) %></h1>
            </div>
            
            <div class="col-sm-6">
            <p>
                <%=(String)data.get(5) %>
            </p> 
            
            <h3>Product Details</h3>
            <li>Type: <%=(String)data.get(0)%></li>
            <li>Brand: <%=(String)data.get(1)%></li> 
            <li>Stock:<%=data.get(4)%></li>
            </div>
            
            <div class="col-sm-4">
                <div class ="thumbnail bg-grey text-center">
                    <h2 color="#FFFFFF">$<%=data.get(3)%></h2>
                    <a href ="#order"  data-toggle ="modal" class ="btn btn-success" role ="button">Order now</a>
                </div>                
            </div>
        </div>
                               
                    <div class="modal fade" id="order" role="dialog">
                        <div class="modal-dialog">
                            <div class ="modal-content">

                                <form action="shoppingCart" role="form" class="form-horizontal" onsubmit="checkQuantity()">
                                    
                                    <div class ="modal-header">
                                        <h4>Add to shopping cart</h4>
                                      </div>
                                    
                                    <div class= "modal-body">
                                          <div class="form-group">
                                            <label for="quantity" class="col-sm-2" control-label >Quantity</label>
                                            <div class ="col-sm-8">
                                                <input type="number" class="form-control" min="1" id="quanity" name="quantity" onChange="changePrice()" required>
                                            </div>   
                                          </div>
                                    
                                  
                                          <div class="form-group">
                                            <label class="col-sm-2">Price</label>
                                            <label class="col-sm-8" id="price" label="<%=data.get(3)%>">$<%=data.get(3)%></label>
                                          </div>
                                          
                                    </div>    
                                        
                                     <div class="modal-footer">     
                                         <a class="btn btn-default" data-dismiss="modal">Close</a>
                                         <button type="submit" class="btn btn-primary">Add</button>
                                     </div>   
                                      
                                </form>
           
                            </div>
                        </div>
                    </div>
                
                    <script type ="text/javascript">
                        function changePrice()
                        {
                          var newAmount = document.getElementById("price").value * document.getElementById("quantity").value;
                          document.getElementById("price").value = newAmount;
                        }
                        
                        function checkQuantity()
                        {
                            var quantity = <%= data.get(4)%>
                            var stockPurchased = document.getElementById("quanity").value;
                            
                            if(quantity === 0)
                            {
                                alert("Sorry we have ran out of stock");
                                event.preventDefault();
                                returnToPreviousPage();
                                return false;
                            }
                                
                            
                            if(stockPurchased > quantity)
                            {
                                alert("You cannot place an order that is greater then the current stock amount");
                                event.preventDefault();
                                returnToPreviousPage();
                                return false;
                            }
                                             
                            return true;
                                
                        }
                        
                    </script>
    </body>
</html>
