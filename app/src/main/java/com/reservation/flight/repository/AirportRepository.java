package com.reservation.flight.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.reservation.flight.config.AppDatabase;
import com.reservation.flight.dao.AirportDao;
import com.reservation.flight.model.Airport;

import java.util.List;

public class AirportRepository {
    private AirportDao airportDao;
    private List<Airport> allAirports;

    public AirportRepository(Application application){
        AppDatabase database = AppDatabase.getDatabase(application);
        airportDao = database.airportDao();
        allAirports = airportDao.getAllAirports();
    }

    public List<Airport> getAllAirports(){
        return allAirports;
    }
}
