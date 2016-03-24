/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
/**
 *
 * @author WeeJing
 */
@Entity(name ="Products")
public class ProductEntity implements Serializable {

   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String productType;
    private String brand;
    private String model;
    private String description;
    private double unitPrice;
    private int stockQuantity;
    private int reorderLevel;

    @ManyToMany(mappedBy="products")
    private Set<OrderEntity> orders = new HashSet <OrderEntity>();
    
    
    public void create(String productType, String brand, String model, String description,
            double unitPrice, int stockQuantity, int reorderLevel)
    {
        this.productType = productType;
        this.brand = brand;
        this.model =model;
        this.description = description;
        this.unitPrice= unitPrice;
        this.stockQuantity = stockQuantity;
        this.reorderLevel = reorderLevel;
                
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProductType() {
        return productType;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getDescription() {
        return description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public void setStockQuantity(int stockQuantity){
        this.stockQuantity = stockQuantity;
    }

    public void reduceStockQuantity(int stockQuantity) {
        this.stockQuantity -= stockQuantity;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
    
    public Long getId() {
        return productId;
    }

    public void setId(Long productId) {
        this.productId = productId;
    }
    
    public Set<OrderEntity> getOrders()
    {
        return orders;
    }
    
}
