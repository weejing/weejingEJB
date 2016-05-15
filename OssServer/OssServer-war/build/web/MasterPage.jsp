<%-- 
    Document   : MasterPage
    Created on : Mar 25, 2016, 2:26:01 PM
    Author     : WeeJing
--%>
<%@page import="java.util.ArrayList, java.util.Vector"%>


<meta charset="utf-8">
         <meta http-equiv="X-UA-Compatible" content="IE=edge">
         <meta name="viewport" content="width=device-width, initial-scale=1">
         
         <link href="../css/bootstrap.min.css" rel="stylesheet">
         <link href="../css/custom.css" rel="stylesheet">
         

         
<div class ="navbar navbar-default navbar-fixed-top" role ="navigaton">
            <div class="container"> 
                
                <div class ="navbar-header">  
                    <a class ="navbar-brand" href ="#">OSS</a>
                </div>
                
                <div class ="navbar-collapse collaspe">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href ="register">Home</a></li>
                        <li><a href ="shoppingCart">Shopping cart</a></li>
                        <li><a href ="#search" data-toggle ="modal">Search</a></li>
                    </ul>
                                    
                </div>                
            </div>
        </div>
         
         
         
         <div id = "wrapper">
             <div id="sidebar-wrapper">
                 <ul class ="sidebar-nav">
                     <li>
                         <a href="profile">Profile</a>
                     </li>
                     
                     <li>
                         <a href ="listOrders">Orders</a>
                     </li>
                     
                     <li>
                         <a href="request">Request</a>
                     </li>
                     
                     <li>
                         <a href ="login">Logout</a>
                     </li>
                     
                 </ul>
             </div>
         </div>
        
        <div class ="navbar navbar-inverse navbar-fixed-bottom" role ="navigaton" positon="absolute"> 
            <div class="container">
                <div class ="navbar-text pull-left">
                    <p>Orange Electronics</p>
                </div>
            </div>
        </div>
         
         
         <div class ="modal fade" id ="search" role="dialog"> 
             <div class ="modal-dialog">
                 <div class="modal-content">
                     <form action="listProducts" class ="form-horizontal" role="form">
                         <div class ="modal-header"> 
                             <h4>Search Products</h4>
                         </div>
                         <div class ="modal-body"> 
                             
                             <div class ="form-group"> 
                                 <label for="type" class="col-sm-2" min="1" control-label>Type</label>
                                 <div class ="col-sm-8"> 
                                    <input type ="text" name ="type" class ="form-control"/>
                                 </div>
                             </div>
                             
                             <div class="form-group">
                                 <label for ="brand" class="col-sm-2" min="1" control-label>brand</label>
                                 <div class="col-sm-8"> 
                                     <input type ="text" name="brand" class="form-control"/>
                                 </div>
                             </div>
                             
                              <div class="form-group">
                                 <label for ="model" class="col-sm-2" min="1" control-label>model</label>
                                 <div class="col-sm-8"> 
                                     <input type ="text" name="model" class="form-control"/>
                                 </div>
                             </div>
                             
                              <div class="form-group">
                                 <label for ="minPrice" class="col-sm-2" min="1" control-label>Min Price</label>
                                 <div class="col-sm-8"> 
                                     <input type ="number" name="minPrice" class="form-control"/>
                                 </div>
                             </div>
                             
                             <div class="form-group">
                                 <label for ="maxPrice" class="col-sm-2" min="1" control-label>Max Price</label>
                                 <div class="col-sm-8"> 
                                     <input type ="number" name="maxPrice" class="form-control"/>
                                 </div>
                             </div>    
                             
                         </div>
                         
                         <div class ="modal-footer">
                            <a class="btn btn-default" data-dismiss="modal">Close</a>
                            <button type="submit" class="btn btn-primary">Search</button>

                         </div>
                     </form>
                 </div>
             </div>
         </div>
         
        
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
