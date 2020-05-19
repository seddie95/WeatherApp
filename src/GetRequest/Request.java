package GetRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.json.JSONException;
import org.json.JSONObject;
import GetRequest.ApiKey;

public class Request {
    public static void main(String[] args) throws IOException, InterruptedException, JSONException {
        // instantiate Weather class and call getWeather method
        Weather weather = new Weather();
        weather.setLat(42.3601);
        weather.setLon(-71.0589);
        JSONObject  myResponse = weather.getWeatherData();

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
        JSONObject weatherObject = myResponse.getJSONArray("weather").getJSONObject(0);
        String description = weatherObject.getString("description");
        String weatherType = weatherObject.getString("main");

        JSONObject wind = myResponse.getJSONObject("wind");
        double windSpeed = (wind.getDouble("speed") * 3600) / 1000.00;

        // get the timezone to work out local sunrise and sunset
        int tZ = myResponse.getInt("timezone")/3600;

        String timezone = "+"+tZ;
        if (tZ <0){
            timezone = ""+tZ;
        }


        JSONObject sun = myResponse.getJSONObject("sys");
        int sr = sun.getInt("sunrise");
        int ss = sun.getInt("sunset");

        final String sunRise = getTimeFromTimezone(timezone, sr);
        final String sunSet = getTimeFromTimezone(timezone, ss);

        System.out.println(sunRise);
        System.out.println(sunSet);



    }
    public static String getTimeFromTimezone(String timezone, int timestamp) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.of("UTC" + timezone))
                .format(formatter);

    }



}