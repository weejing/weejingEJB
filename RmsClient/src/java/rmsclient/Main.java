/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmsclient;

import java.util.InputMismatchException;
import java.util.Scanner;
import javax.ejb.EJB;
import rjb.RmsSessionBeanRemote;

/**
 *
 * @author WeeJing
 */
public class Main {

    @EJB
    private static RmsSessionBeanRemote rmsSessionBean;
    static Scanner scanner; 


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       Main client = new Main();
       client.request();   
    }
    
    public void request()
    {
          scanner = new Scanner(System.in);
        while(true)
        {
         System.out.println("Enter your username");
         String username = scanner.nextLine();
         System.out.println("Enter your request");
         String request = scanner.nextLine();
         
         try{
             String response = rmsSessionBean.sendRequest(username, request);
             System.out.println(response);
         }catch(Exception e){
             e.printStackTrace();
         }
           System.out.println("Request sent successfully");
         
           System.out.println("Enter 0 to exit, 1 to continue");
           
           
           int quit = checkInt();
           scanner.nextLine();
           
           if(quit == 0)
               break;
                  
        }
    }
    
     public int checkInt()
    { 
        while(true)
      {
        try{
            int result = scanner.nextInt();
            return result;      
        }catch(InputMismatchException e){
            System.out.print("please enter your inputs in int: ");
            scanner.next();
        }
      }
                
    }
 }
    

