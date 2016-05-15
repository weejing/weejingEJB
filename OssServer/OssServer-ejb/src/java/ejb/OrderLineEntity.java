/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author WeeJing
 */
@Entity(name ="OrderLine")
@Table(name ="lineline")
public class OrderLineEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderLineId;
    private double unitPrice;
    private int quantity;
    private double totalValue;
    
    @OneToOne
    private ProductEntity productId;
   
    
    public void create(double unitPrice , int quanity, ProductEntity product)
    {
        this.unitPrice =unitPrice;
        this.quantity = quanity;
        this.totalValue = unitPrice * quanity;
        this.productId = product;
                  
    }
    
    public void editOrderline(int quantity)
    {
        this.quantity =quantity;
        
        this.totalValue = quantity * unitPrice;
        
    }

    public Long getId() {
        return orderLineId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalValue() {
        return totalValue;
    }

   public void setProduct(ProductEntity product)
   {
       this.productId = product;
   }
   
   public ProductEntity getProduct()
   {
       return this.productId;
   }
    
   

    
    
}
