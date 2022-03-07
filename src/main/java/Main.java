import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String BASE_URL = "http://api.weatherapi.com/v1";
    private static final String ENDPOINT = "/current.json";
    private static final String API_KEY = ""; // put API key here
    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static void main(String[] args) throws IOException {
        // Print instructions for user
        System.out.println("Hello! Please enter a city to display the current weather there: ");

        //  Get city from user
        String city = "";
        Scanner scanner = new Scanner(System.in);

        while (city.isBlank()) {
            city = scanner.nextLine();
        }

        // Query Api with city
        System.out.println(getCurrentWeather(city));

    }

    public static String getCurrentWeather(String city) throws IOException {
        String url = String.format("%s%s?key=%s&q=%s&aqi=yes", BASE_URL, ENDPOINT, API_KEY, city);

        Request request = new Request
                .Builder()
                .url(url)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            return formatResponse(response.body().string());
        }
    }

    public static String formatResponse(String unformatted) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(unformatted);
        return gson.toJson(je);
    }
}
