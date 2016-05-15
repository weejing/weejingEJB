/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rjb;

import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.annotation.Resource;


/**
 *
 * @author WeeJing
 */
@Stateless
public class RmsSessionBean implements RmsSessionBeanRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Resource(mappedName="jms/QueueConnectionFactory")
    private  ConnectionFactory queueConnectionFactory;
    @Resource(mappedName="jms/Queue")
    private  Queue queue;
    static Object waitUntilDone = new Object();
    static int outStandingMessages =0;

    
     // send Message
    @Override
    public String sendRequest(String userName, String request)
    {
       String reply ="";
       Connection queueConnection = null;
        try{
           // sets up connections 
         queueConnection = queueConnectionFactory.createConnection();
         Session session = queueConnection.createSession(false,Session.AUTO_ACKNOWLEDGE);
         
         // create a temporary queue to hold replies and set up consumers 
         /*
         Queue replyQueue = session.createTemporaryQueue();
         MessageConsumer consumer = session.createConsumer(replyQueue);
         consumer.setMessageListener(new RequestListener());
         */
         
         // sets up producer to submit message 
         queueConnection.start();
         MessageProducer producer =session.createProducer(queue);
         
         // set up message and configure reply destination 
         MapMessage message =session.createMapMessage();
         //message.setJMSReplyTo(replyQueue);
         
         
         // prepare message details and send message
         message.setString("username", userName);
         message.setString("request", request);
         System.out.println("message: " + message);
         producer.send(message);
         outStandingMessages = 1;
         
        /* 
         synchronized(waitUntilDone)
         {
             waitUntilDone.wait();
         }
*/

      /*
         Message msg = consumer.receive();
         MapMessage response = (MapMessage)msg;
         
         reply = response.getString("response");
         
        */         
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            if(queueConnection != null){
                try{
                    queueConnection.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        
        return reply;
        
    }
 
 /*   
static class RequestListener implements MessageListener
{
    @Override
    public void onMessage(Message msg)
    {
        MapMessage message = (MapMessage)msg;
        
        // print out the message for debugging purposes
        try{
            System.out.println("Response: " + message.getString("response"));
            outStandingMessages =0;
        }catch(JMSException je){
            je.printStackTrace();
        }
        
        
        if(outStandingMessages ==0)
        {
            synchronized (waitUntilDone){
                waitUntilDone.notify();
            }
        }else{
            System.out.println("waiting for 1 mesages");
        }

            
    }
    
} */



}
