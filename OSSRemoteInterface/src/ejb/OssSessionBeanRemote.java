/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.ejb.Remote;

/**
 *
 * @author WeeJing
 */
@Remote
public interface OssSessionBeanRemote {
    
    // non web app methods
    public void addUser(String userName, String password, int contact, String email, String address);
    public String deleteUser(String userName);
    public boolean checkUser(String userName);
    public void addProduct(String productType, String brand, String model, String description,
            double unitPrice, int stockQuantity, int reorderLevel);
    public boolean checkProduct(String brand, String model);
    public String deleteProduct(String brand, String model);
    public int checkProdUpdateChoice(String brand, String model);
    public ArrayList<String> displayProdForUpdate();
    public String updateProdGotOrder(ArrayList<String> array);
    public String updateProdNoOrder(ArrayList<String> array);
    public int checkOrder(Long objectId);
    public List<Vector> viewNewOrders();
    public ArrayList<String> displayPayment(long orderId);
    public String shipOrder(int trackingNum, String deliveryDate);
    public Vector deliveryReport(long orderId);
    public List<Vector> productOrderReport(); 
    public List<Vector> reorderReport();
    public List <Vector> getRequest();
    public String updateRequest(long requestId, String comment);
    
    // web app methods
    public boolean login(String username, String password );
    public List<Vector> searchProduct(HashMap hash);
    public void setPageSession(String prevPage);
    public String getPageSession();
    public ArrayList addToShoppingCart(int quantity);
    public ArrayList deleteFromShoppingCart(long prodId);
    public ArrayList getShoppingCart();
    public ArrayList displayProductDetails(String brand, String model);
    public void Order();
    public void payImmediate(String cardType, String cardName, String cardNum);
    public void latePayment(String cardType, String cardName, String cardNum,long orderId);
    public List<Vector> displayPastOrders();
    public ArrayList displayOrderDetails(long orderId);
    public ArrayList displayUser();
    public void updateUser(ArrayList<String> userDetails);
    public void logout();
    public ArrayList displayRequest();
    public List<Vector> search(String type, String brand,String model, HashMap hash);
     
}
