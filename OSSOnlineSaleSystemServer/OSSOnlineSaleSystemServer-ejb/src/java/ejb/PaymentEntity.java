/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author WeeJing
 */
@Entity(name="Payment")
public class PaymentEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    private String cardType;
    private int cardNumber;
    private String cardOwner;
    private String time;
    
    
    public void create(String cardType, int cardNumber, String cardOwner)
    {
        setTime();
        
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
    }

    public Long getId() {
        return paymentId;
    }

    public void setId(Long id) {
        this.paymentId = id;
    }

    public String getCardType() {
        return cardType;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public String getTime() {
        return time;
    }

    public void setTime() {
        Date date = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInText = dateFormat.format(date);
        this.time = dateInText;
    }

    
    
}
