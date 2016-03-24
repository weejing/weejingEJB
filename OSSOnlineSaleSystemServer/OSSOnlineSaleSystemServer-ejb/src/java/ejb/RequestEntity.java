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
import javax.persistence.ManyToOne;

/**
 *
 * @author WeeJing
 */
@Entity(name="Request")
public class RequestEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String requestTime;
    private String requestMessage;
    private String status;
    private String comment;
    
    @ManyToOne
    private UserEntity userName;
    
    public void create(UserEntity userName, String requestMessage)
    {
        this.userName= userName;
        this.requestMessage =requestMessage;
        this.status="unread";
        
        setRequestTime();
                       
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public UserEntity getUserName() {
        return userName;
    }

    public void setRequestTime() {
        Date date = new Date();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInText = dateFormat.format(date);
        this.requestTime =dateInText;    
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUsername(UserEntity username) {
        this.userName = username;
    }


   
    
}
