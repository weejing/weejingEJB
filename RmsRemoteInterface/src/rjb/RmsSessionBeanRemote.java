/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rjb;

import javax.ejb.Remote;

/**
 *
 * @author WeeJing
 */
@Remote
public interface RmsSessionBeanRemote {
    
     public String sendRequest(String userName, String request);

    
}
