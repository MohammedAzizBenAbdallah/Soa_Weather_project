package org.example.soa_ds;


import io.github.cdimascio.dotenv.Dotenv;
import jakarta.websocket.Session;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class WeatherThread extends Thread{
    Dotenv dotenv = Dotenv.load();
    public  String apikey = dotenv.get("WEATHER_API_KEY");

    @Override
    public void run(){
        while(true){
            try {
                // Get all client cities
                Map<Session, String> clientCities = WeatherEndPoint.getClientCities();
                
                // Fetch weather for each client's city individually
                for(Map.Entry<Session, String> entry : clientCities.entrySet()){
                    Session session = entry.getKey();
                    String city = entry.getValue();
                    
                    if(city == null || city.isEmpty()){
                        continue;
                    }
                    
                    try {
                        final String API_URL = "http://api.weatherapi.com/v1/current.json?key="+ this.apikey + "&q=" + city + "&aqi=no";
                        
                        // 1 create connection
                        URL url = new URL(API_URL);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");

                        //2 Read response
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuilder content = new StringBuilder();
                        while((inputLine = bufferedReader.readLine()) != null){
                            content.append(inputLine);
                        }

                        bufferedReader.close();
                        connection.disconnect();

                        // 3 send api response to specific client only
                        String msg = content.toString();
                        WeatherEndPoint.sendToClient(session, msg);
                        
                    } catch (Exception e) {
                        System.err.println("Error fetching weather for " + city + ": " + e.getMessage());
                        // Send error message to the specific client
                        WeatherEndPoint.sendToClient(session, "Error fetching weather for " + city);
                    }
                }

                //4 wait 5 seconds before next fetch
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
