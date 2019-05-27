package com.reservation.flight.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.reservation.flight.config.AppDatabase;
import com.reservation.flight.dao.FlightDao;
import com.reservation.flight.viewmodel.FlightView;

import java.util.List;

public class FlightRepository {
    private FlightDao flightDao;

    public FlightRepository(Application application){
        AppDatabase database = AppDatabase.getDatabase(application);
        flightDao = database.flightDao();

    }

    public List<FlightView> fetchFlightsByDateAndRoute(
           String departureDateFrom, String departureDateTo, String departureCity, String arrivalCity) {
        return flightDao
                .fetchFlightsByDepartureDateAndRoute(departureDateFrom, departureDateTo,departureCity,arrivalCity);
    }

    public LiveData<List<FlightView>> observableFlightsByDateAndRoute(
           String departureDateFrom, String departureDateTo, String departureCity, String arrivalCity) {
        return flightDao
                .observableFlightsByDepartureDateAndRoute(departureDateFrom, departureDateTo,departureCity,arrivalCity);
    }

    public FlightView getFlightDetailByFlightNumber(Integer flightNumber){
        return flightDao.getFlightDetailByFlightNumber(flightNumber);
    }

}
