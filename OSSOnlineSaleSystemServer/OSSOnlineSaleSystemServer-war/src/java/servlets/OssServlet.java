/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.OssManagerBeanRemote;
import javax.ejb.EJB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author WeeJing
 */
public class OssServlet extends HttpServlet {

@EJB
private OssManagerBeanRemote ossManagerBean;


    public void init() {
        System.out.println("OssServlet: init()");        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ossServlet: processRequest()");
        try{
            RequestDispatcher dispatcher;
            ServletContext servletContext = getServletContext();
            
            String prevPage = ossManagerBean.getPageSession();
            if(prevPage!= null)
                System.out.println("the previous page is:" +prevPage);
            
            String page = request.getPathInfo();
            page = page.substring(1);

            System.out.println("what is the page:" +page);
            
            if("home".equals(page))
            {
                if(prevPage.equals("login"))
                {
                 boolean check = login(request);
                 if(check == false){
                    request.setAttribute("error","siaolaio");
                    page ="login";
                 }
                }
                
                if(prevPage.equals("register"))
                {
                    boolean check = createCustomer(request);
                    if(check == false){
                        request.setAttribute("error","hahah");
                        page="register";
                    }
                }
            }
            if("displayProduct".equals(page))
            {               
                ArrayList product = displayProductDetails(request);
                request.setAttribute("data", product);               
            }
            if("listProducts".equals(page))
            {
                   ArrayList listOfProducts = search(request);
                   request.setAttribute("data", listOfProducts);        
            }
            if("shoppingCart".equals(page))
            {
                ArrayList listOfCartItems;
               if(prevPage.equals("displayProduct"))
               {
                   int quantity = Integer.parseInt(request.getParameter("quantity"));
                   listOfCartItems = ossManagerBean.addToShoppingCart(quantity);                
               }else{
                   listOfCartItems = ossManagerBean.getShoppingCart();
               }
               
               request.setAttribute("data",listOfCartItems);
            }
            if("deleteItem".equals(page))
            {
                long prodId = Long.parseLong(request.getParameter("prodId"));
                ArrayList listOfCartItems = ossManagerBean.deleteFromShoppingCart(prodId);
                if(listOfCartItems.size() ==0)
                    page="home";
                else
                    page="shoppingCart";
            }
            if("payment".equals(page))
            {
                if(prevPage.equals("shoppingCart"))
                {
                   ossManagerBean.Order();
                   request.setAttribute("orderId", "");
                }
                
                if(prevPage.equals("listOrders"))
                {
                    String orderId = request.getParameter("orderId");
                    request.setAttribute("orderId",orderId);
                }
 
                String price = request.getParameter("Price");
                request.setAttribute("price",price);
            }
            if("listOrders".equals(page))
            {
                if(prevPage.equals("payment")){
                    String orderId = request.getParameter("orderId");
                    
                    if(orderId.equals(""))
                        payImmediate(request);
                    else
                        latePayment(orderId,request);
                }   
               ArrayList listOfOrders =(ArrayList)ossManagerBean.displayPastOrders();  
               request.setAttribute("orders", listOfOrders);
            }
            if("orderDetails".equals(page))
            {   
                String orderId = request.getParameter("orderId");
                ArrayList order = orderDetails(orderId);
                request.setAttribute("orderDetails", order);
                request.setAttribute("date",request.getParameter("orderTime"));
                request.setAttribute("amount", request.getParameter("orderValue"));
                request.setAttribute("state",request.getParameter("orderState"));      
            }
            if("login".equals(page))
            {
                String logout = request.getParameter("logout");
            }
            if("profile".equals(page))
            {
                if(prevPage.equals("editProfile"))
                {
                    updateProfile(request);
                }
                
                ArrayList userDetails = ossManagerBean.displayUser();
                request.setAttribute("email", userDetails.get(0));                  
                request.setAttribute("address", userDetails.get(1));                
                request.setAttribute("number", userDetails.get(2));                 
            }
            if("editProfile".equals(page))
            {
                request.setAttribute("email", request.getParameter("email"));             
                request.setAttribute("address", request.getParameter("address"));   
                request.setAttribute("number", request.getParameter("number"));           
            }
                       
            dispatcher = servletContext.getNamedDispatcher(page);  
            ossManagerBean.setPageSession(page);


            if(dispatcher == null)
            {
                dispatcher = servletContext.getNamedDispatcher("Error");
                
            }
            dispatcher.forward(request,response);
            
       
        }catch(Exception e)
        {
            System.out.println("Servlet exception caught");
            e.printStackTrace();
        }
    }

    //handle http get methods
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }

    // handle http post methods
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }

 
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
    private boolean createCustomer(HttpServletRequest request)
    {
        String userName = request.getParameter("uname");
        boolean check = ossManagerBean.checkUser(userName);
        if(check == true)
        {
            return false;
        }
        String password = request.getParameter("password");
         int contactNum = Integer.parseInt(request.getParameter("number"));
         String address = request.getParameter("address");
         String email = request.getParameter("email");
         
        ossManagerBean.addUser(userName, password, contactNum, email, address);    
        return true;
    }
    
    private boolean login(HttpServletRequest request)
    {
        String userName = request.getParameter("uName");
        String password = request.getParameter("passWord");
        System.out.println("Whats the username:"+userName);
        
        boolean check = ossManagerBean.login(userName,password);
        
        if(check == false)
            return false;
        
        return true;                  
    }
    // search for products 
    private ArrayList search(HttpServletRequest request)
    {
        HashMap hash = new HashMap();
        hash.put("type",request.getParameter("type"));
        hash.put("brand", request.getParameter("brand"));
        hash.put("model", request.getParameter("model"));
        hash.put("minPrice",request.getParameter("minPrice"));
        hash.put("maxPrice", request.getParameter("maxPrice"));
        System.out.println("iis this" + request.getParameter("maxPrice"));

        ArrayList data = (ArrayList)ossManagerBean.searchProduct(hash);
        System.out.println("arrayList size =" +data.size());
        
        return data;
                       
    }
    // update prfiles 
    private void updateProfile(HttpServletRequest request )
    {
        ArrayList<String> updatedProfile = new ArrayList<String>();
        updatedProfile.add(request.getParameter("email"));
        updatedProfile.add(request.getParameter("address"));
        updatedProfile.add(request.getParameter("number"));
        
        
        ossManagerBean.updateUser(updatedProfile);

    }
    
    // display all the seleted product details 
    private ArrayList displayProductDetails(HttpServletRequest request)
    {
        ArrayList array = new ArrayList();
        
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        array = ossManagerBean.displayProductDetails(brand, model);          
        return array;
    }
    // run this method when a user want to pay after shiopping cart page   
    private void payImmediate(HttpServletRequest request)
    {
        String cardName = request.getParameter("cardName");
        String cardNum =request.getParameter("cardNum");
        String cardType = request.getParameter("cardType");
        
        ossManagerBean.payImmediate(cardType, cardName, Integer.parseInt(cardNum));
    }
    
    private void latePayment(String orderId, HttpServletRequest request)
    {
        String cardName = request.getParameter("cardName");
        String cardNum =request.getParameter("cardNum");
        String cardType = request.getParameter("cardType");
        long ID = Long.parseLong(orderId);
        
        ossManagerBean.latePayment(cardType, cardName, Integer.parseInt(cardNum), ID);
    }
    
    private ArrayList orderDetails(String orderId)
    {
        ArrayList array = new ArrayList();
        Long Id = Long.parseLong(orderId);
        
        array = (ArrayList)ossManagerBean.displayOrderDetails(Id);
                
        return array;        
    }
              
}
