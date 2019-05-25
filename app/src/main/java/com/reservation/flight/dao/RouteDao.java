package com.reservation.flight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.reservation.flight.modelView.RouteView;

import java.util.List;

@Dao
public interface RouteDao {

    @Query("SELECT li.name as airline_name,\n" +
            " a1.airportID as departure_airportID,\n" +
            " a1.name as departure_name,\n" +
            " a1.city as departure_city,\n" +
            " a1.country as departure_country,\n" +
            " a1.code as departure_code,\n" +
            " a2.airportID as arrival_airportID,\n" +
            " a2.name as arrival_name,\n" +
            " a2.city as arrival_city,\n" +
            " a2.country as arrival_country,\n" +
            " a2.code as arrival_code\n" +
            " from route ro, airport a1, airport a2, airline li\n" +
            " where ro.departureAirportID = a1.airportID\n" +
            " and ro. arrivalAirportID = a2.airportID\n" +
            " and ro.airlineID = li.airlineID\n" +
            " and a1.city = :departureCity \n" +
            " and a2.city = :arrivalCity ")
    List<RouteView> getRoutesToDestinationCity(String departureCity, String arrivalCity);

    @Query("SELECT li.name as airline_name,\n" +
            " a1.airportID as departure_airportID,\n" +
            " a1.name as departure_name,\n" +
            " a1.city as departure_city,\n" +
            " a1.country as departure_country,\n" +
            " a1.code as departure_code,\n" +
            " a2.airportID as arrival_airportID,\n" +
            " a2.name as arrival_name,\n" +
            " a2.city as arrival_city,\n" +
            " a2.country as arrival_country,\n" +
            " a2.code as arrival_code\n" +
            " from route ro, airport a1, airport a2, airline li\n" +
            " where ro.departureAirportID = a1.airportID\n" +
            " and ro. arrivalAirportID = a2.airportID\n" +
            " and ro.airlineID = li.airlineID\n" +
            " and a1.city = :departureCity \n" +
            " and a2.country = :arrivalCountry ")
    List<RouteView> getRoutesToDestinationCountry(String departureCity, String arrivalCountry);

    @Query("SELECT count(*) \n" +
            " from route ro, airport a1, airport a2, airline li\n" +
            " where ro.departureAirportID = a1.airportID\n" +
            " and ro. arrivalAirportID = a2.airportID\n" +
            " and ro.airlineID = li.airlineID\n" +
            " and a1.city = :departureCity \n" +
            " and a2.city = :arrivalCity ")
    int countRoutesBetween(String departureCity, String arrivalCity);
}
