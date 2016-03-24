/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmsclient;

import ejb.RMSSessionBeanRemote;
import java.util.Scanner;
import javax.ejb.EJB;

/**
 *
 * @author WeeJing
 * 
 */
public class Main {

    @EJB
    private static RMSSessionBeanRemote rMSSessionBean;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = new Scanner(System.in);
         
        while(true)
        {
         System.out.println("Enter your username");
         String username = scanner.nextLine();
         System.out.println("Enter your request");
         String request = scanner.nextLine();
         
         try{
             String response = rMSSessionBean.sendRequest(username, request);
             System.out.println(response);
         }catch(Exception e){
             e.printStackTrace();
         }
           System.out.println("Enter 0 to exit, 1 to continue");
           int quit = scanner.nextInt();
           
           if(quit == 0)
               break;
                  
        }
        
    }
    
}
