/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 *
 * @author WeeJing
 */
@Entity(name="Deliveryline")
public class DeliveryLineEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int quanity;
    
    @OneToOne
    private ProductEntity product;
    
    public void create(int quanity, ProductEntity product)
    {
        this.quanity = quanity;
        this.product = product;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quanity;
    }

    public ProductEntity getProduct() {
        return product;
    }

}
