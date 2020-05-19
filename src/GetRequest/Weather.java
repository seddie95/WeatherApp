package GetRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Weather {
    private double lat;
    private double lon;
    private String url;

    public Weather() {
        lat = 53.297213;
        lon = -6.285571;



    }

    public Weather(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;

    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public JSONObject getWeatherData() throws  IOException, InterruptedException,JSONException {

        //create the url
        String baseUrl = "http://api.openweathermap.org/data/2.5/weather?";
        String apiKey = ApiKey.getApiKey();
        url = String.format("%slat=%f&lon=%f&appid=%s&units=metric",
                baseUrl, lat, lon, apiKey);

        // Create Response using url
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.url))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

//        String body = response.body();
//        JSONObject myResponse = new JSONObject(body);
        return new JSONObject(response.body());


    }
}
