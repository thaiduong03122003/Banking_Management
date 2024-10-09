package quanlynganhang.DTO;

public class WeatherDTO {
    private String cityName, countryName, humidity, windSpeed, cloudTotal, status, lowHighCTemp, lowHighFTemp, currentCTemp, currentFTemp;

    public WeatherDTO() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCloudTotal() {
        return cloudTotal;
    }

    public void setCloudTotal(String cloudTotal) {
        this.cloudTotal = cloudTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLowHighCTemp() {
        return lowHighCTemp;
    }

    public void setLowHighCTemp(String lowHighCTemp) {
        this.lowHighCTemp = lowHighCTemp;
    }

    public String getLowHighFTemp() {
        return lowHighFTemp;
    }

    public void setLowHighFTemp(String lowHighFTemp) {
        this.lowHighFTemp = lowHighFTemp;
    }

    public String getCurrentCTemp() {
        return currentCTemp;
    }

    public void setCurrentCTemp(String currentCTemp) {
        this.currentCTemp = currentCTemp;
    }

    public String getCurrentFTemp() {
        return currentFTemp;
    }

    public void setCurrentFTemp(String currentFTemp) {
        this.currentFTemp = currentFTemp;
    }
}
