package com.reservation.flight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

@Dao
public interface RouteDao {

    @Query("SELECT count(*) \n" +
            " from route ro, airport a1, airport a2, airline li\n" +
            " where ro.departureAirportID = a1.airportID\n" +
            " and ro. arrivalAirportID = a2.airportID\n" +
            " and ro.airlineID = li.airlineID\n" +
            " and a1.city = :departureCity \n" +
            " and a2.city = :arrivalCity ")
    int countRoutesBetween(String departureCity, String arrivalCity);
}
