package x_wolves.ais.Models;

/**
 * Created by macbook on 25/12/2016.
 */

public class DataLogs {

    String latitude;
    String longitude;
    String date;
    String Address;
    String City;
    String State;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }


    public DataLogs(String latitude, String longitude, String date, String Address, String City, String State) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.City = City;
        this.Address = Address;
        this.State = State;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
