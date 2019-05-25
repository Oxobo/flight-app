package com.reservation.flight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.reservation.flight.model.Airport;

import java.util.List;

@Dao
public interface AirportDao {
    @Query("SELECT * FROM airport WHERE country = :country order by city")
    List<Airport> getAirportsByCountry (String country);

    @Query("SELECT * FROM airport order by country, city")
    List<Airport> getAllAirports();




}
