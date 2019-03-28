package main;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Internet {

    public static double getUSDrate() throws IOException {
        URL urlForGetRequest = new URL("http://data.fixer.io/api/latest?access_key=f7e184e416001bd5af106b8c297a002d");
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");
//       connection.setRequestProperty("userId", "1bb0aff1f8b21c79723aefa5140f4477"); // set userId its a sample here
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } in .close();

            JsonObject jsonObject = (new JsonParser()).parse(response.toString()).getAsJsonObject();
            JsonObject rates = jsonObject.getAsJsonObject("rates");
            Double usd = rates.get("USD").getAsDouble();

           return  usd;


        } else {
            System.out.println("GET NOT WORKED");
            return -1;
        }
    }



}
