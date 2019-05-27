package com.reservation.flight.repository;

import android.app.Application;

import com.reservation.flight.config.AppDatabase;
import com.reservation.flight.dao.ReservationDao;
import com.reservation.flight.model.FlightResrvation;
import com.reservation.flight.modelView.ReservationView;

import java.util.List;

public class ReservationRepository {
    private ReservationDao reservationDao;

    public ReservationRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        reservationDao = database.reservationDao();

    }

    public List<ReservationView> getReservationHistory(Integer userID){
        return reservationDao.getReservationHistory(userID);
    }

    public Long insertFlightResrvation(FlightResrvation flightResrvation) {
        return reservationDao.insertFlightResrvation(flightResrvation);
    }

}
