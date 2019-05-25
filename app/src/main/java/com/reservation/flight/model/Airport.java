package com.reservation.flight.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "airport")
public class Airport {
    @PrimaryKey(autoGenerate = true)
    Integer airportID;
    String name;
    String city;
    String country;
    String code;

    public Integer getAirportID() {
        return airportID;
    }

    public void setAirportID(Integer airportID) {
        this.airportID = airportID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
