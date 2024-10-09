package quanlynganhang.BUS;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import quanlynganhang.DTO.WeatherDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class WeatherBUS {

    public static WeatherDTO getCityLocation(String cityName) {
        try {
            JSONObject cityLocaData = (JSONObject) getLocationData(cityName);

            if (cityLocaData == null) {
                return null;
            }

            String name = (String) cityLocaData.get("name");
            String countryName = (String) cityLocaData.get("country");
            double latitude = (double) cityLocaData.get("latitude");
            double longitude = (double) cityLocaData.get("longitude");
            

            return getWeatherData(name, countryName, latitude, longitude);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JSONObject getLocationData(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, "UTF-8");

            String urlString = "https://geocoding-api.open-meteo.com/v1/search?name="
                + encodedCity + "&count=1&language=en&format=json";

            HttpURLConnection apiConnection = fetchApiResponse(urlString);
            if (apiConnection.getResponseCode() != 200) {
                MessageBox.showErrorMessage(null, "Không thể kết nối đến dịch vụ địa lý!");
                return null;
            }
            String jsonResponse = readApiResponse(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);

            JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
            return (JSONObject) locationData.get(0);

        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showErrorMessage(null, "Không tìm thấy thành phố, vui lòng không nhập chữ \"Thành phố\" ở đầu khi nhập tên");
            return null;
        }
    }

    private static WeatherDTO getWeatherData(String name, String countryName, double latitude, double longitude) {
        WeatherDTO weatherInfo = new WeatherDTO();
        weatherInfo.setCityName(name);
        weatherInfo.setCountryName(countryName);
        
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude
                + "&longitude=" + longitude
                + "&current=temperature_2m,relative_humidity_2m,precipitation,rain,showers,cloud_cover,wind_speed_10m&daily=temperature_2m_max,temperature_2m_min&timezone=Asia%2FBangkok&forecast_days=1";

            HttpURLConnection apiConnection = fetchApiResponse(url);

            if (apiConnection.getResponseCode() != 200) {
                MessageBox.showErrorMessage(null, "Không thể kết nối đến dịch vụ thời tiết!");
                return null;
            }

            String jsonResponse = readApiResponse(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");
            JSONObject currentLowHighTempJson = (JSONObject) jsonObject.get("daily");
            
            double tempCValue = (double) currentWeatherJson.get("temperature_2m");
            int tempC = (int) Math.round(tempCValue);

            weatherInfo.setCurrentCTemp(String.valueOf(tempC));
            weatherInfo.setCurrentFTemp(convertCToF(tempCValue));

            long relativeHumidity = (long) currentWeatherJson.get("relative_humidity_2m");
            weatherInfo.setHumidity(relativeHumidity + "%");

            long cloudTotal = (long) currentWeatherJson.get("cloud_cover");
            weatherInfo.setCloudTotal(cloudTotal + "%");
            
            double windSpeed = (double) currentWeatherJson.get("wind_speed_10m");
            weatherInfo.setWindSpeed(windSpeed + "km/h");

            double precipitation = (double) currentWeatherJson.get("precipitation");
            double rain = (double) currentWeatherJson.get("rain");
            double showers = (double) currentWeatherJson.get("showers");

            JSONArray tempMinArray = (JSONArray) currentLowHighTempJson.get("temperature_2m_min");
            JSONArray tempMaxArray = (JSONArray) currentLowHighTempJson.get("temperature_2m_max");
        
            double lowTemp = (double) tempMinArray.get(0);
            double highTemp = (double) tempMaxArray.get(0);
            int lowTempC = (int) Math.round(lowTemp);
            int highTempC = (int) Math.round(highTemp);
            String lowHighCTemp = lowTempC + "-" + highTempC;
            String lowHighFTemp = convertCToF(lowTemp) + "-" + convertCToF(highTemp);
            
            weatherInfo.setLowHighCTemp(lowHighCTemp);
            weatherInfo.setLowHighFTemp(lowHighFTemp);
            
            String status = getWeatherStatus(relativeHumidity, cloudTotal, windSpeed, precipitation, rain, showers);
            weatherInfo.setStatus(status);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return weatherInfo;
    }

    private static String readApiResponse(HttpURLConnection apiConnection) {
        try {
            StringBuilder resultJson = new StringBuilder();

            Scanner scanner = new Scanner(apiConnection.getInputStream());

            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }

            scanner.close();

            return resultJson.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HttpURLConnection fetchApiResponse(String urlString) {
        try {
            URI uri = new URI(urlString);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertCToF(double tempCValue) {
        int temperatureFahrenheit = (int) Math.round((tempCValue * 9 / 5) + 32);

        return String.valueOf(temperatureFahrenheit);
    }

    private static String getWeatherStatus(long humidity, long cloudTotal, double windSpeed, double precipitation, double rain, double showers) {
        String weatherStatus = "";

        if (humidity < 30 && cloudTotal < 25) {
            weatherStatus = "1-Nắng đẹp";
        } else if (humidity >= 30 && humidity < 60 && cloudTotal >= 25 && cloudTotal < 50) {
            weatherStatus = "2-Ít mây";
        } else if ((humidity >= 60 && humidity < 80) || (cloudTotal >= 50 && cloudTotal < 75)) {
            weatherStatus = "2-Nhiều mây";
        } else if (humidity >= 80 || cloudTotal >= 75) {
            weatherStatus = "3-Khả năng mưa";
        } else if (cloudTotal >= 20 && cloudTotal <= 40 && humidity <= 70){
            weatherStatus = "2-Nhiều nắng";
        } else {
            weatherStatus = "2-Có nắng";
        }

        if (precipitation > 0) {
            if (rain > 10 || precipitation > 8) {
                weatherStatus += ", có mưa giông";
            } else if (precipitation < 3 || showers > 0) {
                weatherStatus += ", có mưa rào";
            } else {
                weatherStatus += ", có mưa vừa";
            }
        }

        if (windSpeed > 20) {
            weatherStatus += " với gió mạnh";
        }

        return weatherStatus.trim();
    }
    
}
