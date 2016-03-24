/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Remote;

/**
 *
 * @author WeeJing
 */
@Remote
public interface RMSSessionBeanRemote {
    
    public String sendRequest(String userName, String request);
}
