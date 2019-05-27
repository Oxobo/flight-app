package com.reservation.flight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.reservation.flight.datamodel.FlightResrvation;
import com.reservation.flight.viewmodel.ReservationView;

import java.util.List;

@Dao
public interface ReservationDao {
    @Insert
    Long insertFlightResrvation(FlightResrvation flightResrvation);

    @Query("select fr.ticketNumber  as fr_ticketNumber,\n" +
            "       fr.flightNumber  as fr_flightNumber,\n" +
            "       fr.seatNum       as fr_seatNum,\n" +
            "       fr.userID        as fr_userID,\n" +
            "       fl.flightNumber  as fl_flightNumber,\n" +
            "       fl.departureTime as fl_departureTime,\n" +
            "       fl.departureDate as fl_departureDate,\n" +
            "       fl.arrivalTime   as fl_arrivalTime,\n" +
            "       fl.arrivalDate   as fl_arrivalDate,\n" +
            "       fl.routeID       as fl_routeID,\n" +
            "       a1.airportID     as departure_airportID,\n" +
            "       a1.name          as departure_name,\n" +
            "       a1.city          as departure_city,\n" +
            "       a1.country       as departure_country,\n" +
            "       a1.code          as departure_code,\n" +
            "       a2.airportID     as arrival_airportID,\n" +
            "       a2.name          as arrival_name,\n" +
            "       a2.city          as arrival_city,\n" +
            "       a2.country       as arrival_country,\n" +
            "       a2.code          as arrival_code,\n" +
            "       al.name          as airline_name\n" +
            "from flight_reservation fr,\n" +
            "     flight fl,\n" +
            "     route ro,\n" +
            "     airline al,\n" +
            "     airport a1,\n" +
            "     airport a2\n" +
            "where fr.flightNumber = fl.flightNumber\n" +
            "  and fl.routeID = ro.routeID\n" +
            "  and ro.departureAirportID = a1.airportID\n" +
            "  and ro.arrivalAirportID = a2.airportID\n" +
            "  and ro.airlineID = al.airlineID\n" +
            "  and userID = :userID \n" +
            "  order by fl.departureDate, fl.departureTime desc ")
    List<ReservationView> getReservationHistory(Integer userID);
}
