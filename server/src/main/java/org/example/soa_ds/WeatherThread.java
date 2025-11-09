package org.example.soa_ds;


import io.github.cdimascio.dotenv.Dotenv;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class WeatherThread extends Thread{
    Dotenv dotenv = Dotenv.load();
    public  String apikey = dotenv.get("WEATHER_API_KEY");
    private static String city = "london";
    public static void setCity(String newCity){
        city = newCity;
    }



    @Override
    public void run(){
        while(true){
            try {
                final String  API_URL = "http://api.weatherapi.com/v1/current.json?key="+ this.apikey + "&q=" + city+ "&aqi=no";
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

                // 3 send api response to websocket server
                String msg = content.toString();
                WeatherEndPoint.broadcast(msg);


                //4 wait  seconds before next fetch
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
