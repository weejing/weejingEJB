/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author WeeJing
 */
@MessageDriven(mappedName = "jms/Queue", activationConfig = {
    //@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName="acknowledgeMode",propertyValue ="Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "jms/Queue"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/Queue")
})
public class RequestMessageBean implements MessageListener {
    
   @PersistenceContext()
    EntityManager em;
    
    RequestEntity request;
    UserEntity user;
    
    @Resource(mappedName="jms/QueueConnectionFactory")
    private ConnectionFactory queueConnectionFactory;
    
    public RequestMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) 
    {
        MapMessage msg =null;
        System.out.println("I recevied the message");
        
        try{
            if(message instanceof MapMessage){
                msg =(MapMessage) message;
                Thread.sleep(100);
                setUpRequest(msg);
            }else{
                System.out.println("Message is of wrong type");
            }
        }catch(InterruptedException ie){
          System.out.println("Request driven bean Interrupted exception: " +ie.toString());

        }catch(Throwable te){
            System.out.println("Request driven bean exception: " +te.toString());
        }
        
    }
    
    public void createRequest(String content)
    {
        request = new RequestEntity();
        request.create(user, content);
        em.persist(request);
       ArrayList req = (ArrayList)user.getRequests();
       req.add(0,request);
       user.setRequest(req);
        em.merge(user);
    }
    
    public void setUpRequest(MapMessage message)
    {
        String userName;
        String content;
        Connection connection;
        //Destination replyDest;
        //Session session;
        //MapMessage replyMessage;
        //MessageProducer producer;
        try{
            userName = message.getString("username");
            content = message.getString("request");
            
            // set up the message response
            
            connection = queueConnectionFactory.createConnection();
            //replyDest = message.getJMSReplyTo();
            //session =connection.createSession(true,0);
           // producer =session.createProducer(replyDest);
            
            
            user =em.find(UserEntity.class,userName);
            
            if(user == null)
            {
                //replyMessage =wrongUserName(session);
                //producer.send(replyMessage);
                return;
            }
            
            createRequest(content);
            
            //replyMessage = response(session);
            //producer.send(replyMessage);
                       
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /*
    private MapMessage wrongUserName(Session session) throws JMSException 
    {
        MapMessage replyMessage = session.createMapMessage();
        replyMessage.setString("response", "User name does not existt");
        
        return replyMessage;
    }
    
    private MapMessage response(Session session) throws JMSException
    {
         MapMessage replyMessage = session.createMapMessage();
         replyMessage.setString("response", "request created succesffuly");
         
         return replyMessage;
    }
    */
}
