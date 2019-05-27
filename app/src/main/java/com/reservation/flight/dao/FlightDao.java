package com.reservation.flight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.reservation.flight.modelView.FlightView;

import java.util.List;

@Dao
public interface FlightDao {

    @Query("SELECT fl.flightNumber        as fli_flightNumber,\n" +
            "       fl.routeID             as fli_routeID,\n" +
            "       fl.departureDate       as fli_departureDate,\n" +
            "       fl.departureTime       as fli_departureTime,\n" +
            "       fl.arrivalDate         as fli_arrivalDate,\n" +
            "       fl.arrivalTime         as fli_arrivalTime,\n" +
            "       al.name                as airline_name,\n" +
            "       deparAirport.airportID as depar_airportID,\n" +
            "       deparAirport.name      as depar_name,\n" +
            "       deparAirport.city      as depar_city,\n" +
            "       deparAirport.country   as depar_country,\n" +
            "       deparAirport.code      as depar_code,\n" +
            "       arrivAirport.airportID as arriv_airportID,\n" +
            "       arrivAirport.name      as arriv_name,\n" +
            "       arrivAirport.city      as arriv_city,\n" +
            "       arrivAirport.country   as arriv_country,\n" +
            "       arrivAirport.code      as arriv_code\n" +
            "from flight fl,\n" +
            "     airline al,\n" +
            "     route ro,\n" +
            "     airport deparAirport,\n" +
            "     airport arrivAirport\n" +
            "WHERE fl.routeID = ro.routeID\n" +
            "  and ro.airlineID = al.airlineID\n" +
            "  and ro.departureAirportID = deparAirport.airportID\n" +
            "  and ro.arrivalAirportID = arrivAirport.airportID\n" +
            "  AND fl.departureDate >= :departureDateFrom\n" +
            "  AND fl.departureDate <= :departureDateTo\n" +
            "  AND deparAirport.city = :departureCity\n" +
            "  AND arrivAirport.city = :arrivalCity\n" +
            "ORDER by fl.departureDate, fl.departureTime")
    List<FlightView> fetchFlightsByDepartureDateAndRoute(String departureDateFrom, String departureDateTo,
                                                                   String departureCity,
                                                                   String arrivalCity);

    @Query("SELECT fl.flightNumber        as fli_flightNumber,\n" +
            "       fl.routeID             as fli_routeID,\n" +
            "       fl.departureDate       as fli_departureDate,\n" +
            "       fl.departureTime       as fli_departureTime,\n" +
            "       fl.arrivalDate         as fli_arrivalDate,\n" +
            "       fl.arrivalTime         as fli_arrivalTime,\n" +
            "       al.name                as airline_name,\n" +
            "       deparAirport.airportID as depar_airportID,\n" +
            "       deparAirport.name      as depar_name,\n" +
            "       deparAirport.city      as depar_city,\n" +
            "       deparAirport.country   as depar_country,\n" +
            "       deparAirport.code      as depar_code,\n" +
            "       arrivAirport.airportID as arriv_airportID,\n" +
            "       arrivAirport.name      as arriv_name,\n" +
            "       arrivAirport.city      as arriv_city,\n" +
            "       arrivAirport.country   as arriv_country,\n" +
            "       arrivAirport.code      as arriv_code\n" +
            "from flight fl,\n" +
            "     airline al,\n" +
            "     route ro,\n" +
            "     airport deparAirport,\n" +
            "     airport arrivAirport\n" +
            "WHERE fl.routeID = ro.routeID\n" +
            "  and ro.airlineID = al.airlineID\n" +
            "  and ro.departureAirportID = deparAirport.airportID\n" +
            "  and ro.arrivalAirportID = arrivAirport.airportID\n" +
            "  AND fl.flightNumber = :flightNumber")
    FlightView getFlightDetailByFlightNumber(Integer flightNumber);
}
