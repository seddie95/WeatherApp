package GetRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class Request {
    public static void main(String[] args) throws IOException, InterruptedException, JSONException {
        String baseUrl = "http://api.openweathermap.org/data/2.5/weather?";
        String apiKey = GetRequest.ApiKey.getApiKey();
        double lat = 53.297213;
        double lon = -6.285571;

        String url = String.format("%slat=%f&lon=%f&appid=%s&units=metric",baseUrl, lat, lon, apiKey);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();


        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        JSONObject myResponse = new JSONObject(body);


        // parse json object
        double visibility = myResponse.getDouble("visibility") / 1000.00;

        // Temperature data
        JSONObject main = myResponse.getJSONObject("main");
        double actualTemp = main.getDouble("temp");
        double minTemp = main.getDouble("temp_min");
        double maxTemp = main.getDouble("temp_max");
        double realFeel = main.getDouble("feels_like");
        double humidity = main.getDouble("humidity");
        double pressure = main.getDouble("pressure");

        //Weather description data
        JSONObject weather = myResponse.getJSONArray("weather").getJSONObject(0);
        String description = weather.getString("description");
        String weatherType = weather.getString("main");

        JSONObject wind = myResponse.getJSONObject("wind");
        double windSpeed = (wind.getDouble("speed") * 3600) / 1000.00;

        System.out.println("Wind speed: " + windSpeed);
        System.out.println("Visibility: " + visibility);
        System.out.println(myResponse.getString("name"));
        System.out.println("Real feel: " + realFeel);


    }
}