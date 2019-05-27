package com.reservation.flight.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.reservation.flight.model.Airport;

import java.util.List;

@Dao
public interface AirportDao {

    @Query("SELECT * FROM airport order by country, city")
    List<Airport> getAllAirports();


}
