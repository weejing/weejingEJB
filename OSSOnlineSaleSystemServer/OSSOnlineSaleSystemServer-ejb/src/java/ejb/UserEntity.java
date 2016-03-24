/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author WeeJing
 */
@Entity(name = "Users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String userName;
    private String password;
    private int contactNumber;
    private String email;
    private String address;
    
    @OneToMany(mappedBy="username")
    private Collection<OrderEntity> orders = new ArrayList<OrderEntity>();
    
    @OneToMany(mappedBy="userName")
    private Collection<RequestEntity> requests = new ArrayList<RequestEntity>();

    public void create(String userName, String password, int contactNumber, String email, 
            String address)
    {
        this.userName =userName;
        this.password = password;
        this.contactNumber =contactNumber;
        this.email = email;
        this.address = address;
    }
    
    
    public String getPassword() {
        return password;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
    
    public String getUserName() {
        return this.userName;
    }

    public void setId(String userName) {
        this.userName = userName;
    }
    
    public Collection<OrderEntity> getOrders()
    {
        return orders;
    }

    public void setOrders(ArrayList<OrderEntity> orders)
    {
        this.orders =orders;
    }
    
    public Collection<RequestEntity> getRequests()
    {
        return requests;
    }
    
}
