package org.example.soa_ds;

import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.OnOpen;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ServerEndpoint("/weather")
public class WeatherEndPoint {
    private static final Map<Session, String> clients = new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session){
        String clientId = UUID.randomUUID().toString();
        clients.put(session, clientId);
        System.out.println("Connected to client: "+ clientId);

    }
    @OnClose
    public void onClose(Session session){
        String ClientId = clients.remove(session);
        System.out.println("Disconnected from client: "+ ClientId);
        broadcast("Disconnected from client: "+ ClientId);
    }
    @OnMessage
    public static void onMessage(String city, Session session){
        String clientId = clients.get(session);
        WeatherThread.setCity(city);
        System.out.println("📩 Client " + clientId + " requested: " + city);
        broadcast("City updated  to" + city);
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
}
