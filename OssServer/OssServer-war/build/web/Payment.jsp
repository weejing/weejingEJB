<%-- 
    Document   : Payment
    Created on : Mar 25, 2016, 2:26:32 PM
    Author     : WeeJing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <jsp:include page="MasterPage.jsp"/>
        <title>Payment Page</title>
    </head>
    <body>
        <div class ="container">
            <div class ="row">
                <div class="col-md-12 text-center">
                    <h2>Credit Card Payment Form</h2>
                </div>
            </div>
            <% 
               double price = Double.parseDouble((String)request.getAttribute("price"));
               String orderId = (String)request.getAttribute("orderId");
               System.out.println("what is id: "+orderId);
            %>
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="credit-card-div">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <form action = "pay?orderId=<%= orderId%>" onsubmit="checkPrice();" method="POST" >  
                                    
                                    <div class="row">
                                        <div class="col-md-12">
                                            <input type ="text" pattern="[0-9]{16}" name="cardNum" class="form-control"  placeholder="Enter card number" required title ="13 to 16 numbers"/> 
                                        </div>     
                                    </div>
                                    <div class ="row">
                                        <div class="col-md-5 col-xs-4">
                                            <span class ="help-block text-muted small-font">Card Type</span>
                                            <input type ="text" name="cardType" class="form-control" required/>
                                        </div>
                                        <div class="col-md-5 col-xs-4">
                                            <span class="help-block text-muted small-font">Amount</span>
                                            <input type ="number" name="amount" id="amountPaid" class="form-control" placeholder="Pay <%=price%>" required/>
                                        </div>  
                                    </div>
                                    <div class ="row">
                                        <div class="col-md-12 pad-adjust">
                                            <input type ="text" name ="cardName" class="form-control" placeholder="Name of cardholder" required />
                                        </div>
                                    </div>
                                    <div class="row">
                                         
                                        <div class="col-md-6 col-sm-6 col-xs-6 pad-adjust">
                                            <a href="home" class="btn btn-danger">Cancel</a>
                                        </div>
                                        
                                         <div class="col-md-6 col-sm-6 col-xs-6 pad-adjust">
                                            <input type ="submit" class="btn btn-warning btn-block" value="Pay Now" onclick="checkPrice()"/>
                                        </div>  
                                        
                                    </div>
                                </form>
                            </div>
                          
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                    
        <script type = "text/javascript" >
            function checkPrice()
            {
                var price = <%= price%>;
                var amountPaid = document.getElementById("amountPaid").value;
                
                if(price != amountPaid)
                {
                    alert("The amount you entered does not equals the required price to be paid");
                    event.preventDefault();
                    returnToPreviousPage();
                    return false;
                }
                
                return true;
            }
        </script>                           
    </body>
</html>
