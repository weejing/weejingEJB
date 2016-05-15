/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 *
 * @author WeeJing
 */
@Entity(name="Orders")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderID;
    private String orderTime;
    private double totalValue;
    @ManyToOne
    private UserEntity username;
    @ManyToMany()
    private Set <ProductEntity> products = new HashSet<ProductEntity>();
    @OneToMany(cascade={CascadeType.PERSIST})
    private Collection<OrderLineEntity> orderLines = new ArrayList<OrderLineEntity>();
    @OneToOne
    private DeliveryEntity delivery;
    @OneToOne
    private PaymentEntity payment;
    

    public void create (UserEntity username)
    {
        this.setOrderTime();
        this.username = username;
    }
    
    public void setUser(UserEntity username)
    {
        this.username =username;
    }
    public void setProducts(Set<ProductEntity> products)
    {
        this.products = products;
    }
    
    public Long getorderID() {
        return orderID;
    }
    public UserEntity getUsers()
    {
        return this.username;
    }

    public void setId(Long orderID) {
        this.orderID = orderID;
    }
    
    public void setOrderTime()
    {
        Date date = new Date();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInText = dateFormat.format(date);
        this.orderTime =dateInText;    
    }
    
    public String getTime()
    {
        return this.orderTime;
    }
    public void setTotalValue(double totalValue)
    {
        this.totalValue += totalValue;
    }
    
    public double getTotalValue()
    {
        return this.totalValue; 
    }
   
    
    public Collection<OrderLineEntity> getOrderLines()
    {
        return this.orderLines;
    }
    
    public Set<ProductEntity> getProducts()
    {
        return this.products;
    }

    public DeliveryEntity getDelivery() {
        return delivery;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public void setDelivery(DeliveryEntity delivery) {
        this.delivery = delivery;
    }
    
    
}
