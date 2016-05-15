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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author WeeJing
 */
@Entity(name="Delivery")
@Table(name="dddeesd")
public class DeliveryEntity implements Serializable {

   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deliveryId;
    private int trackingNum;
    private String shipTiming;
    private String deliveryDate;
    
    @OneToMany(cascade = {CascadeType.PERSIST})
    private Collection<DeliveryLineEntity> deliveryLine = new ArrayList<DeliveryLineEntity>();


      public void create(int trackNum, String date){
        setShipTiming();
        
        this.trackingNum = trackNum;
        this.deliveryDate =date;
    }

    public Long getId() {
        return deliveryId;
    }

    public void setId(Long id) {
        this.deliveryId = id;
    }

    public int getTrackingNum() {
        return trackingNum;
    }

    public String getShipTiming() {
        return shipTiming;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setTrackingNum(int trackingNum) {
        this.trackingNum = trackingNum;
    }

    public void setShipTiming() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInText = dateFormat.format(date);
        this.shipTiming = dateInText;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Collection<DeliveryLineEntity> getDeliveryLine() {
        return deliveryLine;
    }
    
}
