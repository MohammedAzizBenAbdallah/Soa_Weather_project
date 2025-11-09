package org.example.soa_ds;
import org.glassfish.tyrus.server.Server;
public class WeatherServer {

    public static Server server = new Server("localhost", 8080, "/ws", null, WeatherEndPoint.class);

    public static void main(String[] args)  {
        try{
           server.start();

            System.out.println("Server Started on ws://localhost:8080/ws/weather");
            new WeatherThread().start();
            Thread.currentThread().join();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            server.stop();
        }
    }


}
