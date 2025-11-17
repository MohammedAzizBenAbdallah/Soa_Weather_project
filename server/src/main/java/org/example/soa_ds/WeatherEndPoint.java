package org.example.soa_ds;

import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.OnOpen;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/weather")
public class WeatherEndPoint {
    private static final Map<Session, String> clients = new ConcurrentHashMap<>();
    private static final Map<Session, String> clientCities = new ConcurrentHashMap<>();
    
    @OnOpen
    public void onOpen(Session session){
        String clientId = UUID.randomUUID().toString();
        clients.put(session, clientId);
        System.out.println("Connected to client: "+ clientId);
        broadcastClientCount();
    }
    @OnClose
    public void onClose(Session session){
        String ClientId = clients.remove(session);
        clientCities.remove(session);
        System.out.println("Disconnected from client: "+ ClientId);
        broadcast("Disconnected from client: "+ ClientId);
        broadcastClientCount();

    }
    @OnMessage
    public static void onMessage(String city, Session session){
        String clientId = clients.get(session);
        clientCities.put(session, city);
        System.out.println("📩 Client " + clientId + " requested: " + city);
        sendToClient(session, "City updated to " + city);
    }
    
    public static Map<Session, String> getClientCities(){
        return clientCities;
    }
    
    public static void sendToClient(Session session, String message){
        try{
            if(session.isOpen()){
                session.getBasicRemote().sendText(message);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void broadcast(String message){
        for(Map.Entry<Session, String> entry: clients.entrySet()){
            try{
                entry.getKey().getBasicRemote().sendText(message);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void broadcastClientCount(){
        int count = clients.size();
        String message = "{\"type\": \"clientCount\", \"count\": " + count + "}";
        System.out.println("broadcastClientCount: " + message);
        broadcast(message);
    }
}
