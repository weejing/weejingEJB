<%-- 
    Document   : DisplayProduct
    Created on : Mar 17, 2016, 12:31:48 PM
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
                            <div class ="modal-body">

                                <form action="shoppingCart" class="form-basic" role="form" onsubmit="checkQuantity()">
                                    
                                    <div class ="form-title-row">
                                        <h4>Add to shopping cart</h4>
                                      </div>
                                    
                            
                                          <div class="form-group">
                                            <label for="quantity" class="col-sm-2" min="1" control-label >Quantity</label>
                                            <div class ="col-sm-10">
                                                <input type="number" class="form-control" id="quanity" name="quantity" onChange="changePrice()">
                                            </div>   
                                          </div>
                                    
                                    <br/>
                                          <div class="form-group">
                                            <label class="col-sm-2">Price</label>
                                            <label class="col-sm-2" id="price" label="<%=data.get(3)%>">$<%=data.get(3)%></label>
                                          </div>
                                          
                                      <br/>    
                                        
                                         <div class ="col-sm-8">
                                         <input type="submit" value="add" class="btn btn-primary" />
                                        </div>    
                                            
                                        <div class ="col-sm-2">
                                         <a class="btn btn-default" data-dismiss="modal">Close</a>
                                        </div>
                                       
                                      
                                </form>
                             </div>  
           
                            </div>
                        </div>
                    </div>
                
                    <script type ="text/javascript">
                        function changePrice()
                        {
                          var newAmount = document.getElementById("price").label * document.getElementById("quantity").value;
                          document.getElementById("price").label = "newAmount";
                        }
                        
                        function checkQuantity()
                        {
                            var quantity = <%= data.get(4)%>
                            var stockPurchased = document.getElementById("quanity").value;
                            
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
