package com.reservation.flight.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.reservation.flight.config.AppDatabase;
import com.reservation.flight.dao.AirportDao;
import com.reservation.flight.dao.FlightDao;
import com.reservation.flight.model.Airport;
import com.reservation.flight.modelView.FlightView;

import java.util.List;

public class FlightRepository {
    private FlightDao flightDao;

    public FlightRepository(Application application){
        AppDatabase database = AppDatabase.getDatabase(application);
        flightDao = database.flightDao();

    }

    public List<FlightView> fetchFlightsByDepartureDateAndRoute(
           String departureDateFrom, String departureDateTo, String departureCity, String arrivalCity) {
        return flightDao
                .fetchFlightsByDepartureDateAndRoute(departureDateFrom, departureDateTo,departureCity,arrivalCity);
    }

    public FlightView getFlightDetailByFlightNumber(Integer flightNumber){
        return flightDao.getFlightDetailByFlightNumber(flightNumber);
    }

}
