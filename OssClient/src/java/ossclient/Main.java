/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ossclient;

import ejb.OssSessionBeanRemote;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import javax.ejb.EJB;
import java.util.Scanner;
import java.util.Vector;



/**
 *
 * @author WeeJing
 */
public class Main {

    @EJB
    private static OssSessionBeanRemote ossManagerBean;
    Scanner sc;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Main client = new Main();
        client.enterChoice();
        
    }
    
    public void enterChoice()
    {
        int choice;
        sc = new Scanner(System.in);
        
        while(true)
        {
            System.out.println("\n\nPlease enter an option from the list below, enter '0' to exit ");
            System.out.println("'1' to add a new User \n'2' to delete an existing user");
            System.out.println("'3' to add a new product \n'4' to update an existing product");
            System.out.println("'5' to delete an existing product \n'6' to view new orders");
            System.out.println("'7' View Payment details of chosen order \n'8' ship orders");
            System.out.println("'9' Display an order's delivery report \n'10' Product monthly order report");
            System.out.println("'11' Display products that are low in stock \n'12' Display a user's requests ");
            System.out.print("Choice:");
            
            choice = checkInt();
            sc.nextLine();
            System.out.println();
            if(choice ==0)
                break;
            if(choice == 1)
                createUser();
            if(choice ==2)
                deleteUser();
            if(choice == 3)
                createProduct();
            if(choice == 4)
                updateProduct();
            if(choice==5)
                deleteProduct();
            if(choice ==6)
                viewNewOrders();
            if(choice ==7)
                viewPaymentDetails();
            if(choice ==8)
                shipOrders();
            if(choice ==9)
                deliveryReport();
            if(choice ==10)
                productOrderReport();
            if(choice ==11)
                lowStockProduct();  
            if(choice ==12)
                displayRequests();
        }
    }
    
    public int checkInt()
    { 
        while(true)
      {
        try{
            int result = sc.nextInt();
            return result;      
        }catch(InputMismatchException e){
            System.out.print("please enter your inputs in int: ");
            sc.next();
        }
      }
                
    }
    
    public double checkDouble()
    {
       while(true)
      {
        try{
            double result = sc.nextDouble();
            return result;      
        }catch(InputMismatchException e){
            System.out.print("please enter your inputs in double: ");
            sc.next();
        }
      }
    }
    
    public String checkDate()
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
       
        while(true)
        {
            try{
                String date = sc.nextLine();
                df.parse(date);
                return date;              
            }catch(ParseException p){
                System.out.println("please enter a valid date in the format dd-M-yyyy");
                //sc.nextLine();
            }
        }
    }
    
   
    // creates a new user into the system
    public void createUser()
    {
        boolean userExist = false;
        System.out.print("Enter the new user's Username:");
        String username = sc.next();
        try{
           userExist = ossManagerBean.checkUser(username.toLowerCase());
        }catch(Exception ex){}
        if(userExist ==true)
        {
            System.out.println("User account already exists");
            return;
        }
        
        System.out.print("Enter the new user's password:");
        String password = sc.next();
        System.out.print("Enter the new user's contact:");
        int contact = checkInt();
        System.out.print("Enter the new user's email:");
        String email = sc.next();
        System.out.print("Enter the new user's address:");
        sc.nextLine();
        String address = sc.nextLine();
        
        try{
            ossManagerBean.addUser(username, password, contact, email, address);
        }catch (Exception ex)
        {
            System.err.println("Caught an unxpected exception when adding user");
            ex.printStackTrace();
        }
        
    }
    // deletes an existing user
    public void deleteUser()
    {
        System.out.println("Enter the username of the user that you wish to delete");
        String userName = sc.next();
        
        try{
            String response = ossManagerBean.deleteUser(userName);
            System.out.println(response);
        }catch(Exception e){
            e.printStackTrace();
        }
            
    }
    //creates a new product into the system
    public void createProduct()
    {
        boolean productExist =false;
        System.out.println("Enter new products's type ");
        String type = sc.next();
        System.out.println("Enter product's brand");
        String brand = sc.next();
        System.out.println("Enter product's model");
        String model = sc.next();
        
        try{
            productExist =ossManagerBean.checkProduct(brand, model);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(productExist == true)
        {
           System.out.println("Product already exists");
           createProduct();
           return;
        }
        
        System.out.println("Enter product's Description ");
        String fluff = sc.nextLine();
        String description = sc.nextLine();
        System.out.println("Enter product's unit price");
        double unitPrice = checkDouble();
        System.out.println ("Enter products's stock count");
        int stockQuantity = checkInt();
        System.out.println ("Enter product's reoder level");
        int reorder = checkInt();
        
        try
        {
            ossManagerBean.addProduct(type,brand,model,description,unitPrice,stockQuantity,reorder);
        }catch(Exception ex)
        {
            System.err.println("caught an unexpected exception when adding products");
            ex.printStackTrace();        
        }     
    }
    //delete an exisitng product that is not asscociated with any orders
    public void deleteProduct()
    {
        System.out.println("Enter product's brand");
        String brand = sc.next();
        System.out.println("Enter product's model");  
        String model = sc.next();
        
        try{
            String response = ossManagerBean.deleteProduct(brand, model);
            System.out.println(response);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    //update an exisitng product details
    public void updateProduct()
    {
        int updateSuitabilty =0;
        System.out.println("Enter product's brand");
        String brand = sc.next();
        System.out.println("Enter product's model");
        String model = sc.next();
        
        try{
             updateSuitabilty = ossManagerBean.checkProdUpdateChoice(brand, model);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        if(updateSuitabilty == 0)
        {
            System.out.println("Product does not exists");
            return;
        }
        
        ArrayList<String> array = new ArrayList<String>();
        try{
            array = ossManagerBean.displayProdForUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        if(updateSuitabilty == 1)
            updateProdWithOrder(array);
        else
            updateProdNoOrder(array);         
    }
    // update product that are associated with 1 or more orders
    public void updateProdWithOrder( ArrayList<String> array)
    {
        boolean loop = true;
        while (loop)
        {
        System.out.println("Choose the field that you which to update");
        System.out.println("'4' Unit Price. Current unit price: " + array.get(4));
        System.out.println("'5' Stock Order. Current Stock order: " + array.get(5));
        System.out.println("'6' Reorder amount. Current reoder amount: " + array.get(6));
        System.out.println("11 to stop updating ");
        
        int choice = sc.nextInt();
        
        if(choice == 4)
        {
            System.out.print("Enter new unit price:");
            double price = checkDouble();
            array.set(4,price +"");      
        }if(choice ==5)
        {
            System.out.print("Enter new stock order value:");
            int stock = checkInt();
            array.set(5,stock + "");
        }if(choice == 6)
        {
            System.out.print("Enter new reorder value:");
            int reorder = checkInt();
            array.set(6,reorder +"");
        }if(choice == 11)
               loop = false;
        }
        
        try{
           String response = ossManagerBean.updateProdGotOrder(array);
           System.out.println(response);
        }catch(Exception e){
            e.printStackTrace();
        }

            
    }
    // update product that is not associated with any orders
    public void updateProdNoOrder( ArrayList<String> array)
    {
        
        boolean loop = true;
        while (loop)
        {
        System.out.println("Choose the field that you which to update");
        System.out.println("'0' Type. Current type:" + array.get(0));
        System.out.println("'1' Brand. Current brand:" + array.get(1));
        System.out.println("'2' Model. Current model:" +array.get(2));
        System.out.println("'3' Description. current description: " +array.get(3));
        System.out.println("'4' Unit Price. Current unit price: " + array.get(4));
        System.out.println("'5' Stock Order. Current Stock order: " + array.get(5));
        System.out.println("'6' Reorder amount. Current reoder amount: " + array.get(6));
        System.out.println("11 to stop updating ");
        
        int choice = checkInt();
        
        if(choice == 0)
        {
            System.out.print("Enter new type: ");
            array.set(0,sc.next());
        }
        if(choice == 1)
        {
            System.out.print("Enter new brand: ");
            array.set(1,sc.next());
        }
         if(choice == 2)
        {
            System.out.print("Enter new model: ");
            array.set(2,sc.next());
        }
         if(choice == 3)
        {
            System.out.print("Enter new description: ");
            sc.nextLine();
            String description = sc.nextLine();
            array.set(0,description);
        }
        
        if(choice == 4)
        {
            System.out.print("Enter new unit price:");
            double price = checkDouble();
            array.set(4,price +"");      
        }if(choice ==5)
        {
            System.out.print("Enter new stock order value:");
            int stock = checkInt();
            array.set(5,stock + "");
        }if(choice == 6)
        {
            System.out.print("Enter new reorder value:");
            int reorder = checkInt();
            array.set(6,reorder +"");
        }if(choice == 11)
               loop = false;
        }
        
        try{
           String response = ossManagerBean.updateProdNoOrder(array);
           System.out.println(response);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    // Display orders that have not been process for delivery
    public void viewNewOrders()
    {
        try{
            List<Vector> listOfOrders = ossManagerBean.viewNewOrders();
            
            if(listOfOrders.size() ==0)
            {
                System.out.println("There are currently no new orders");
                return;
            }
            
            for(Object object: listOfOrders )
            {
                Vector order = (Vector) object;
                System.out.println("\n\nOrder Number: " + order.get(3));
                System.out.println("UserName: " +order.get(0) +"    Email: "+order.get(1)
                +"    Address: "+order.get(2) +"\n");
                System.out.println("Order \n");
                
                List<Vector> orderLines = new ArrayList();
                orderLines = (List<Vector>)order.get(6);
                
                for(Object obj: orderLines)
                {
                    Vector orderLine = (Vector)obj;
                    System.out.println("Type: "+ orderLine.get(0) +"   Brand: " +orderLine.get(1)
                    +"   model: " +orderLine.get(2) +"   Unit Price: "+orderLine.get(3)+ "   Quantity: "
                    +orderLine.get(4) +"   Total Price: " +orderLine.get(5));
                }
                System.out.println("\nOrder Price: " +order.get(4) +"\nOrder Time: " +order.get(5)+"\n\n");                        
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    // view the payment details of selected order
    public void viewPaymentDetails()
    {
        System.out.println("Enter an order ID");
        int choice = sc.nextInt();
        Long id = Long.valueOf(choice);
        try{
            ArrayList<String> payment = ossManagerBean.displayPayment(id);
            if(payment == null)
            {
                System.out.println("Order Does not exist");
                return;
            }
            if(payment.get(0).equals("no"))
            {
                System.out.println("Customer have not made any payment to this order yet");
                return;
            }
            
            System.out.println("Card Owner: " + payment.get(0) + "\nCard Type:" +payment.get(1)
            + "\nCard Number:" +payment.get(2)+ "\nTime:" +payment.get(3));               
        }catch(Exception e){
            e.printStackTrace();
        }      
    }
    
     // Ship selected orders
     public void shipOrders()
     {
        System.out.println("Enter an order ID");
        int choice = checkInt();
        Long id = Long.valueOf(choice);
        try{
            int result = ossManagerBean.checkOrder(id);
            if(result == 0)
            {
                System.out.println("order does not exist");
                return;
            }if(result ==1)
            {
                System.out.println("Order has not recevied any form of payment yet");
                return;   
            }if(result ==2)
            {
                System.out.println("Order has already been Tagged for shipment");
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        System.out.print("Enter delivery tracking number:");
        int trackNum = checkInt();
        System.out.print("Enter estimated delivery date:");
        sc.nextLine();
        String date = checkDate();
        
        try{
            String response = ossManagerBean.shipOrder(trackNum, date);
            System.out.println(response);
        }catch(Exception e){
         e.printStackTrace();
     }    
     }
     
     // Display the delivery details of an order
     public void deliveryReport()
     {
         System.out.println("Enter an order's ID to view its delivery details");
         int choice = checkInt();
         Long id = Long.valueOf(choice);

         try{
             Vector response = ossManagerBean.deliveryReport(id);
             if(response ==null)
             {
                 System.out.println("Either orderId does not exist or order have not been paid");
                 return;
             }
             System.out.println("Delivery Id: " + response.get(0) +"\nTracking Number: " +response.get(1)
             +"\nShipment time: " + response.get(2) +"\nExcpeted delivery date: " +response.get(3));
             System.out.println("\nProducts");
             
             for(Object object: (List<Vector>)response.get(4))
             {
                 Vector deliveryLine = (Vector)object;
                 System.out.println("\nType: "+deliveryLine.get(0)+"   Brand: "+deliveryLine.get(1)
                 +"     Model: "+deliveryLine.get(2) +"    Quantity: " +deliveryLine.get(3));
             }
             
         }catch(Exception e){
             e.printStackTrace();
         }     
     }
     
     // displays the monthly order report of selected  product
     public void productOrderReport()
     {
         System.out.print("Enter product's brand:");
         String brand = sc.next();
         System.out.print("Enter product's model:");
         String model = sc.next();
         
         try{
             boolean check = ossManagerBean.checkProduct(brand,model);
             if(check == false){
                 System.out.println("Product does not exist");
                 return;   
             }            
             List<Vector> listOfOrders = ossManagerBean.productOrderReport();
             
             if(listOfOrders.size() ==0)
             {
                 System.out.println("product does not have any orders");
                 return;
             }
             
             for(Object object: listOfOrders)
             {
                 Vector order = (Vector)object;
                 System.out.println("\nOrder id: " +order.get(0)+ "     User: "+order.get(1)
                 +"    Quantity: " +order.get(2)+"    Order Time: " +order.get(3));
             }
             
         }catch(Exception e){
             e.printStackTrace();
         }             
     }
     // display products that has a lower stock count then its reorder count
     public void lowStockProduct()
     {
         try{
             List<Vector> listOfProds = ossManagerBean.reorderReport();
             
             if(listOfProds.size() ==0)
             {
                 System.out.println("\nThere are currently no products that are low in stock\n");
                 return;
             }
             for(Object object: listOfProds)
             {
                 Vector product = (Vector)object;
                 
                 System.out.println("\nType: " +product.get(0)+"\nBrand: "+product.get(1)
                 +"\nModel: "+product.get(2)+"\nCurrent Stock: "+product.get(3)+"\nReorder level: " +product.get(4));
                 
                 System.out.println("\n");
             }
         }catch(Exception e){
             e.printStackTrace();
         }
             
     }
     
     public void displayRequests()
     {
         System.out.println("Enter a username");
         String userName = sc.next();
         
         try{
             boolean check =ossManagerBean.checkUser(userName);
             if(check == false)
             {
                 System.out.println("user does not exist");
                 return;
             }
             List<Vector> listOfRequests = ossManagerBean.getRequest();
             
             for(Object object: listOfRequests)
             {
                 Vector request = (Vector)object;
                 System.out.println("Request");
                 System.out.println("Reuest Id: "+request.get(0));
                 System.out.println("Request date: " +request.get(1));
                 System.out.println("Request message: " +request.get(2));
             }
         }catch(Exception e){
             e.printStackTrace();
         }
         
         System.out.print("Enter request id: ");
         int id = checkInt();
         long reqId = (long)id;
         System.out.print("Enter comment: ");
         sc.nextLine();
         String comment = sc.nextLine();
         try{
            String response = ossManagerBean.updateRequest(reqId, comment);
            System.out.println("/n"+response);
         }catch(Exception e){
             e.printStackTrace();
         }
         
     }
    
}
