/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author WeeJing
 */
@Stateful
public class OssSessionBean implements OssSessionBeanRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext()
    EntityManager em;
    private UserEntity userEntity;
    private OrderEntity orderEntity;
    private ProductEntity productEntity;
    private ArrayList<OrderEntity> orders;
    private ArrayList listOfCartItems;
    private List<Vector> listOfOrders;
    private List<Vector> listOfProducts;
    private String prevPage = "";
    private Vector product;

    public OssSessionBean() {

    }

    // return false if user does not exist, true if user exist
    @Override
    public boolean checkUser(String userName) {
        userName.toLowerCase();
        userEntity = em.find(UserEntity.class, userName);
        if (userEntity == null) {
            return false;
        }

        return true;
    }

    @Override
    public void addUser(String userName, String password, int contact, String email, String address) {
        userEntity = new UserEntity();
        userEntity.create(userName, password, contact, email, address);
        em.persist(userEntity);
    }

    // users can only be deleted if they do not have any piror orders.
    @Override
    public String deleteUser(String userName) {
        String response = "user deleted succesfully";

        boolean check = checkUser(userName);
        if (check == false) {
            response = "user does no exist";
            return response;
        }

        Collection<OrderEntity> orders = userEntity.getOrders();
        if (orders.size() != 0) {
            response = "Selected user is associated with existing orders. As such this user is not"
                    + "eligible for deletion ";

            return response;
        }
        em.remove(userEntity);
        return response;
    }

    // returns a true if product exists
    // returns a false if product does not exist
    @Override
    public boolean checkProduct(String brand, String model) {
        Query query = em.createQuery("SELECT p FROM Products p WHERE"
                + " p.brand = :brand and p.model = :model")
                .setParameter("brand", brand)
                .setParameter("model", model);

        try {
            productEntity = (ProductEntity) query.getSingleResult();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public void addProduct(String productType, String brand, String model, String description,
            double unitPrice, int stockQuantity, int reorderLevel) {
        productEntity = new ProductEntity();
        productEntity.create(productType, brand, model, description, unitPrice, stockQuantity, reorderLevel);
        em.persist(productEntity);
    }

    @Override
    public String deleteProduct(String brand, String model) {
        String response = "Product deleted succesfully";
        boolean check = checkProduct(brand, model);
        if (check == false) {
            response = "Product does not exist";
            return response;
        }
        Set<OrderEntity> orders = productEntity.getOrders();
        if (orders.size() != 0) {
            response = "Product is associated with unshipped orders.";
            return response;
        }
        em.remove(productEntity);
        return response;
    }

    // returns 0 if product does not exist, 1 if product is liable for update with order assocaited
    // 2 if products is liable for update of all its field
    @Override
    public int checkProdUpdateChoice(String brand, String model) {
        boolean check = checkProduct(brand, model);

        if (check == false) {
            return 0;
        }
        Set<OrderEntity> orders = productEntity.getOrders();
        System.out.println("size: " +orders.size());
        if (orders.size() != 0) {
            return 1;
        }

        return 2;
    }

    // returns a product that is for updating
    @Override
    public ArrayList<String> displayProdForUpdate() {
        ArrayList<String> array = new ArrayList<String>();

        array.add(productEntity.getProductType());
        array.add(productEntity.getBrand());
        array.add(productEntity.getModel());
        array.add(productEntity.getDescription());
        array.add(productEntity.getUnitPrice() + "");
        array.add(productEntity.getStockQuantity() + "");
        array.add(productEntity.getReorderLevel() + "");

        return array;
    }

    @Override
    public String updateProdGotOrder(ArrayList<String> array) {
        String response = "Product updated succesfully";

        productEntity.setUnitPrice(Double.parseDouble(array.get(4)));
        productEntity.setStockQuantity(Integer.parseInt(array.get(5)));
        productEntity.setReorderLevel(Integer.parseInt(array.get(6)));

        em.merge(productEntity);

        return response;
    }

    @Override
    public String updateProdNoOrder(ArrayList<String> array) {
        String response = "Product updated succesfully";
        
        System.out.println(array.get(2));

        productEntity.setProductType(array.get(0));
        productEntity.setBrand(array.get(1));
        productEntity.setModel(array.get(2));
        productEntity.setDescription(array.get(3));
        productEntity.setUnitPrice(Double.parseDouble(array.get(4)));
        productEntity.setStockQuantity(Integer.parseInt(array.get(5)));
        productEntity.setReorderLevel(Integer.parseInt(array.get(6)));
        
        em.merge(productEntity);
        System.out.println(productEntity.getModel());

        return response;
    }

    // view orders that have not been processed for delivery
    @Override
    public List<Vector> viewNewOrders() {
        Query query = em.createQuery("SELECT o from Orders o");
        List<Vector> listOfOrders = new ArrayList();
        for (Object object : query.getResultList()) {
            OrderEntity order = (OrderEntity) object;
            if (order.getDelivery() == null) {
                UserEntity user = order.getUsers();
                Vector orderVector = new Vector();

                // add the user details into the order
                orderVector.add(user.getUserName());
                orderVector.add(user.getEmail());
                orderVector.add(user.getAddress());

                // add the order details
                orderVector.add(order.getorderID());
                orderVector.add(order.getTotalValue());
                orderVector.add(order.getTime());

                // add the order line details
                List<Vector> listOfOrderLines = new ArrayList();
                for (Object obj : order.getOrderLines()) {
                    OrderLineEntity orderLine = (OrderLineEntity) obj;
                    Vector orderLines = new Vector();
                    ProductEntity product = orderLine.getProduct();

                    orderLines.add(product.getProductType());
                    orderLines.add(product.getBrand());
                    orderLines.add(product.getModel());

                    orderLines.add(orderLine.getUnitPrice());
                    orderLines.add(orderLine.getQuantity());
                    orderLines.add(orderLine.getTotalValue());

                    listOfOrderLines.add(orderLines);
                }
                orderVector.add(listOfOrderLines);
                listOfOrders.add(orderVector);
            }
        }
        return listOfOrders;
    }

    // view payment deatils of selected order
    @Override
    public ArrayList<String> displayPayment(long orderId) {
        orderEntity = em.find(OrderEntity.class, orderId);

        if (orderEntity == null) {
            return null;
        }

        ArrayList<String> paymentDetails = new ArrayList<String>();
        PaymentEntity payment = orderEntity.getPayment();

        if (payment == null) {
            paymentDetails.add("no");
            return paymentDetails;
        }

        paymentDetails.add(payment.getCardOwner());
        paymentDetails.add(payment.getCardType());
        paymentDetails.add(payment.getCardNumber() + "");
        paymentDetails.add(payment.getTime());

        return paymentDetails;
    }

    // return 0 if order doe not exist, return 1 if payment does not eixst, return 2 if order has been delviered
    @Override
    public int checkOrder(Long orderId) {
        orderEntity = em.find(OrderEntity.class, orderId);

        if (orderEntity == null) {
            return 0;
        }
        if (orderEntity.getPayment() == null) {
            return 1;
        }
        if (orderEntity.getDelivery() != null) {
            return 2;
        }

        return 3;

    }

    // prerequiste: checkOrder method must return a 3.
    // order entity object is the same as checkorder 
    // create deliveryline and delivery entitys
    @Override
    public String shipOrder(int trackingNum, String deliveryDate) {
        String response = "Order sucesfully marked for delivery";

        DeliveryEntity delivery = new DeliveryEntity();
        delivery.create(trackingNum, deliveryDate);

        for (Object object : orderEntity.getOrderLines()) {
            OrderLineEntity orderLine = (OrderLineEntity) object;
            DeliveryLineEntity deliveryLine = new DeliveryLineEntity();

            deliveryLine.create(orderLine.getQuantity(), orderLine.getProduct());
            delivery.getDeliveryLine().add(deliveryLine);
        }

        orderEntity.setDelivery(delivery);
        em.persist(delivery);
        em.merge(orderEntity);

        return response;
    }

    // returns a vector containing the delivery details of requested order
    @Override
    public Vector deliveryReport(long orderId) {
        orderEntity = em.find(OrderEntity.class, orderId);
        if (orderEntity == null) {
            return null;
        }
        Vector deliveryDetails = new Vector();
        DeliveryEntity deliver = orderEntity.getDelivery();
        
        if(deliver ==null)
        {
            return null;
        }

        List<Vector> listOfDeliveryLines = new ArrayList();

        for (Object object : deliver.getDeliveryLine()) {
            DeliveryLineEntity deliveryLine = (DeliveryLineEntity) object;
            ProductEntity product = deliveryLine.getProduct();
            Vector dlv = new Vector();
            dlv.add(product.getProductType());
            dlv.add(product.getBrand());
            dlv.add(product.getModel());
            dlv.add(deliveryLine.getQuantity());
            listOfDeliveryLines.add(dlv);
        }

        deliveryDetails.add(deliver.getId());
        deliveryDetails.add(deliver.getTrackingNum());
        deliveryDetails.add(deliver.getShipTiming());
        deliveryDetails.add(deliver.getDeliveryDate());
        deliveryDetails.add(listOfDeliveryLines);

        return deliveryDetails;
    }

    // display all orders for a product in the selected month
    @Override
    public List<Vector> productOrderReport() {
        DateFormat formatter = new SimpleDateFormat("MMMM");
        String month = formatter.format(new Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date;

        List<Vector> listOfOrders = new ArrayList();

        for (Object object : productEntity.getOrders()) {
            orderEntity = (OrderEntity) object;
            String orderDate = orderEntity.getTime();
            try {
                date = dateFormat.parse(orderDate);
                orderDate = formatter.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (orderDate.equals(month)) {
                Vector order = new Vector();

                order.add(orderEntity.getorderID());
                order.add(orderEntity.getUsers().getUserName());
                OrderLineEntity orderLine = null;

                for (Object obj : orderEntity.getOrderLines()) {
                    OrderLineEntity tmpOrderLine = (OrderLineEntity) obj;

                    if (tmpOrderLine.getProduct().getId() == productEntity.getId()) {
                        orderLine = tmpOrderLine;
                    }
                }
                order.add(orderLine.getQuantity());
                order.add(orderEntity.getTime());
                listOfOrders.add(order);
            }

        }
        return listOfOrders;
    }

    @Override
    public List<Vector> reorderReport() {
        Query query = em.createQuery("SELECT p FROM Products p");
        List<Vector> listOfLowStockProduct = new ArrayList();
        for (Object object : query.getResultList()) {
            productEntity = (ProductEntity) object;

            if (productEntity.getStockQuantity() <= productEntity.getReorderLevel()) {
                Vector product = new Vector();
                product.add(productEntity.getProductType());
                product.add(productEntity.getBrand());
                product.add(productEntity.getModel());
                product.add(productEntity.getStockQuantity());
                product.add(productEntity.getReorderLevel());

                listOfLowStockProduct.add(product);
            }
        }
        return listOfLowStockProduct;
    }

    @Override
    public List<Vector> getRequest() {
        List<Vector> listOfRequest = new ArrayList();
        ArrayList<RequestEntity> requestLists = new ArrayList<RequestEntity>(userEntity.getRequests());

        for (int count = 0; count < requestLists.size(); count++) {
            Vector request = new Vector();
            if(requestLists.get(count).getStatus().equals("unread"))
            {
              request.add(requestLists.get(count).getId());
              request.add(requestLists.get(count).getRequestTime());
              request.add(requestLists.get(count).getRequestMessage());

               listOfRequest.add(request);
            }
            
        }
        return listOfRequest;
    }

    @Override
    public String updateRequest(long requestId, String comment) {
        RequestEntity request = em.find(RequestEntity.class, requestId);

        if (request == null) {
            return "You have entered a wrong request id";
        }

        String status = "processed";

        request.setStatus(status);
        request.setComment(comment);

        return "request updated succesfully";
    }

    /*
    All methods from this point onward would be called by the web app
     */
    // login the user to the application 
    @Override
    public boolean login(String username, String password) {
        userEntity = em.find(UserEntity.class, username);
        if (userEntity == null) {
            return false;
        }

        if (password.equals(userEntity.getPassword())) {
            listOfCartItems = new ArrayList();
            return true;
        }
        return false;
    }

    // logouts the user, thus deleting all sessions
    @Override
    public void logout() {
        userEntity = null;
    }

    // change a user details
    @Override
    public void updateUser(ArrayList<String> userDetails) {
        if (!(userDetails.get(0).equals(""))) {
            userEntity.setEmail(userDetails.get(0));
        }
        if (!(userDetails.get(1).equals(""))) {
            userEntity.setAddress(userDetails.get(1));
        }
        if (!(userDetails.get(2).equals(""))) {
            userEntity.setContactNumber(Integer.parseInt(userDetails.get(2)));
        }
        if (!(userDetails.get(3).equals(""))) {
            userEntity.setPassword(userDetails.get(3));
        }

        em.merge(userEntity);
    }

    // display the details of a user
    @Override
    public ArrayList displayUser() {
        ArrayList userDetails = new ArrayList();

        userDetails.add(userEntity.getEmail());
        userDetails.add(userEntity.getAddress());
        userDetails.add(userEntity.getContactNumber());

        return userDetails;
    }

    // return the previous page visited
    @Override
    public String getPageSession() {
        return this.prevPage;
    }

    // set the new page visited
    @Override
    public void setPageSession(String prevPage) {
        this.prevPage = prevPage;
    }

    // returns a product to be displayed on the website  
    public ArrayList<String> displayProduct(String brand, String model) {
        ArrayList<String> array = new ArrayList();
        checkProduct(brand, model);

        array.add(productEntity.getDescription());
        array.add(productEntity.getStockQuantity() + "");

        return array;
    }
    @Override
    public List<Vector> search(String type, String brand, String model, HashMap hash){
         listOfProducts = new ArrayList();
         
         Query query = em.createQuery("SELECT p FROM Products p WHERE"
                + " p.productType LIKE :type and p.brand LIKE :brand and p.model LIKE :model")
                .setParameter("type", "%"+type.toLowerCase()+"%")
                .setParameter("brand", "%"+brand.toLowerCase()+"%")
                .setParameter("model", "%"+model.toLowerCase()+"%");
         
          for (Object object : query.getResultList()) {
            productEntity = (ProductEntity) object;
               boolean check = addToSearch(hash);
               if (check == true) {

                Vector product = new Vector();
                product.add(productEntity.getProductType());
                product.add(productEntity.getBrand());
                product.add(productEntity.getModel());
                product.add(productEntity.getUnitPrice());
                product.add(productEntity.getStockQuantity());
                product.add(productEntity.getDescription());
                product.add(productEntity.getId());
                listOfProducts.add(product); 
               }
            }
          return listOfProducts;
    }
    
    // retursn products that matched the searched citeria
    @Override
    public List<Vector> searchProduct(HashMap hash) {
        listOfProducts = new ArrayList();
        Query query = em.createQuery("SELECT p from Products p");

        for (Object object : query.getResultList()) {
            productEntity = (ProductEntity) object;
            boolean check = addToSearch(hash);
            if (check == true) {
                Vector product = new Vector();
                product.add(productEntity.getProductType());
                product.add(productEntity.getBrand());
                product.add(productEntity.getModel());
                product.add(productEntity.getUnitPrice());
                product.add(productEntity.getStockQuantity());
                product.add(productEntity.getDescription());
                product.add(productEntity.getId());
                listOfProducts.add(product);
            }
        }

        return listOfProducts;
    }

    // checks if a product meets the search citeria 
    public boolean addToSearch(HashMap hash) {
        /*
        if (productEntity.getStockQuantity() == 0) {
            return false;
        }
        
        if (!(hash.get("type").equals("")) && !(productEntity.getProductType().equals((hash.get("type") +"").toLowerCase()))) {
            return false;
        }
        if (!(hash.get("brand").equals("")) && !(productEntity.getBrand().equals((hash.get("brand") +"").toLowerCase()))) {
            return false;
        }
        if (!(hash.get("model").equals("")) && !(productEntity.getModel().equals((hash.get("model")+"").toLowerCase()))) {
            return false;
        }
        */
        if (!(hash.get("minPrice").equals("")) && !(hash.get("maxPrice").equals(""))
                && productEntity.getUnitPrice() > Double.parseDouble((String) hash.get("maxPrice"))
                && productEntity.getUnitPrice() < Double.parseDouble((String) hash.get("minPrice"))) {
            return false;
        }
        if (!(hash.get("minPrice").equals("")) && productEntity.getUnitPrice() < Double.parseDouble((String) hash.get("minPrice"))) {
            return false;
        }
        if (!(hash.get("maxPrice").equals("")) && productEntity.getUnitPrice() > Double.parseDouble((String) hash.get("maxPrice"))) {
            return false;
        }

        return true;
    }

    // display the details of selected product. Product is retrieved from searched products
    @Override
    public ArrayList displayProductDetails(String brand, String model) {
        ArrayList array = new ArrayList();

        for (Object object : listOfProducts) {
            product = (Vector) object;
            if (product.get(1).equals(brand) && (product.get(2).equals(model))) {
                array.add(product.get(0));// type
                array.add(product.get(1));//brand
                array.add(product.get(2)); //model
                array.add(product.get(3));//price
                array.add(product.get(4)); //stock
                array.add(product.get(5)); // description
                array.add(product.get(6));// id
                return array;
            }
        }
        return array;
    }

    /*
     add a product to the current shopping cart
     The contents of the global variable "product" would be initliased 
    when the display product method is called
     */
    @Override
    public ArrayList addToShoppingCart(int quantity) {
        double price = quantity * (Double) product.get(3);

        if (listOfCartItems.size() == 0) {
            listOfCartItems.add(price);
        } else {
            double currentPrice = (Double) listOfCartItems.get(0);
            currentPrice += price;
            listOfCartItems.set(0, currentPrice);
        }

        Vector orderLine = new Vector();
        orderLine.add(product.get(1)); // brand
        orderLine.add(product.get(2)); //model
        orderLine.add(product.get(3) + "");//unit price
        orderLine.add(quantity);//quantity
        orderLine.add(price + "");//total price
        orderLine.add(product.get(6));//id

        listOfCartItems.add(orderLine);
        return listOfCartItems;
    }

    @Override
    public ArrayList deleteFromShoppingCart(long prodId) {
        for (int count = 1; count < listOfCartItems.size(); count++) {
            System.out.println("what is this: " + listOfCartItems.get(count));
            
            Vector orderLine = (Vector) listOfCartItems.get(count);

            long id = (long) orderLine.get(5);

            if (id == prodId) {
                System.out.println("count:" +count);
                System.out.println("prodID" + prodId);
                listOfCartItems.remove(count);
                break;
            }
        }

        if (listOfCartItems.size() == 1) {
            listOfCartItems.remove(0);
        }

        return listOfCartItems;
    }

    // reutrns the current shopping cart
    @Override
    public ArrayList getShoppingCart() {
        return this.listOfCartItems;
    }

    // pre-requiste: must have an exisiting shopping cart 
    // saves the shopping cart into order
    @Override
    public void Order() {
        OrderEntity order = new OrderEntity();
        order.create(userEntity);
        ProductEntity product = null;

        for (int count = 1; count < listOfCartItems.size(); count++) {
            Vector productVector = (Vector) listOfCartItems.get(count);
            long prodId = (long) productVector.get(5);
            product = em.find(ProductEntity.class, prodId);

            // create orderlines and add total value to order
            OrderLineEntity orderLine = new OrderLineEntity();
            int quantity = (int) productVector.get(3);
            orderLine.create(product.getUnitPrice(), quantity, product);
            order.setTotalValue(orderLine.getTotalValue());

            // establish the relationship
            order.getOrderLines().add(orderLine);
            order.getProducts().add(product);
            product.getOrders().add(order);
            product.reduceStockQuantity(quantity);
        }

        userEntity.getOrders().add(order);
        em.persist(order);
        em.merge(userEntity);
        orderEntity = order;

        listOfCartItems = new ArrayList();
        //em.merge(product);
    }

    // This method is only called when a user want to pay imeediately after ordering
    @Override
    public void payImmediate(String cardType, String cardName, String cardNum) {
        PaymentEntity pay = new PaymentEntity();
        pay.create(cardType, cardNum, cardName);
        orderEntity.setPayment(pay);

        em.persist(pay);
        em.merge(orderEntity);
        em.flush();

    }

    // This method is called only when a users want to pay for a past unpaid order
    @Override
    public void latePayment(String cardType, String cardName, String cardNum, long orderId) {
        OrderEntity order = em.find(OrderEntity.class, orderId);
        PaymentEntity pay = new PaymentEntity();
        pay.create(cardType, cardNum, cardName);
        order.setPayment(pay);

        em.persist(pay);
        em.merge(order);
        em.merge(userEntity);
        em.flush();

    }

    /// display orders that have been by user
    @Override
    public List<Vector> displayPastOrders() {
        String username = userEntity.getUserName();
        userEntity = em.find(UserEntity.class, username);
        ArrayList<OrderEntity> list = new ArrayList<OrderEntity>(userEntity.getOrders());
        String state = "";
        listOfOrders = new ArrayList();
        System.out.println("size =" +listOfOrders.size());
        for (int count = 0; count < list.size(); count++) {
            OrderEntity orderEntity = list.get(count);
            Vector order = new Vector();
            String needPay = "false";
            order.add(orderEntity.getTime());
            order.add(orderEntity.getTotalValue());
            order.add(orderEntity.getorderID());

            if (orderEntity.getDelivery() != null) {
                state = "shipped";
            }
            if (orderEntity.getDelivery() == null) {
                state = "Processing delivery";
            }
            if (orderEntity.getPayment() == null) {
                state = "Awaitng payment";
                needPay = "true";
            }
            
            System.out.println("state:" +state);

            order.add(state);
            order.add(needPay);

            listOfOrders.add(order);
        }
        return listOfOrders;
    }

    // dislay the details of a chosen order. Delivery details would also be displayed if order haven been shipped
    @Override
    public ArrayList displayOrderDetails(long orderId) {
        ArrayList orderDetails = new ArrayList();
        orderEntity = em.find(OrderEntity.class, orderId);

        List<Vector> listOfOrderLines = new ArrayList();

        for (Object object : orderEntity.getOrderLines()) {
            OrderLineEntity OrderLine = (OrderLineEntity) object;
            Vector orderLine = new Vector();
            ProductEntity product = OrderLine.getProduct();

            orderLine.add(product.getProductType());
            orderLine.add(product.getBrand());
            orderLine.add(product.getModel());
            orderLine.add(product.getUnitPrice());
            orderLine.add(OrderLine.getQuantity());
            orderLine.add(OrderLine.getTotalValue());

            listOfOrderLines.add(orderLine);
        }
        orderDetails.add(listOfOrderLines);

        if (orderEntity.getDelivery() != null) {
            DeliveryEntity delivery = orderEntity.getDelivery();
            List<Vector> listOfDeliveryLines = new ArrayList();
            Vector Delivery = new Vector();

            Delivery.add(delivery.getShipTiming());
            Delivery.add(delivery.getTrackingNum());
            Delivery.add(delivery.getDeliveryDate());

            orderDetails.add(Delivery);

            for (Object object : delivery.getDeliveryLine()) {
                DeliveryLineEntity DeliveryLine = (DeliveryLineEntity) object;
                Vector deliveryLine = new Vector();
                ProductEntity product = DeliveryLine.getProduct();

                deliveryLine.add(product.getProductType());
                deliveryLine.add(product.getBrand());
                deliveryLine.add(product.getModel());
                deliveryLine.add(DeliveryLine.getQuantity());

                listOfDeliveryLines.add(deliveryLine);
            }
            orderDetails.add(listOfDeliveryLines);
        }

        return orderDetails;
    }

    @Override
    public ArrayList displayRequest() {
        ArrayList listOfRequests = new ArrayList();

        for (Object object : userEntity.getRequests()) {
            RequestEntity request = (RequestEntity) object;
            Vector requestVector = new Vector();
            requestVector.add(request.getRequestTime());
            requestVector.add(request.getStatus());
            requestVector.add(request.getRequestMessage());
            requestVector.add(request.getComment());
            listOfRequests.add(requestVector);
        }

        return listOfRequests;
    }

}
