package com.reservation.flight.viewmodel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

import com.reservation.flight.datamodel.Airport;
import com.reservation.flight.datamodel.Flight;
import com.reservation.flight.datamodel.FlightResrvation;

public class ReservationView {

    @ColumnInfo(name = "airline_name")
    public String airlineName;
    @Embedded(prefix = "departure_") public Airport departureAirport;
    @Embedded(prefix = "arrival_")public Airport arrivalAirport;
    @Embedded(prefix = "fl_")public Flight flight;
    @Embedded(prefix = "fr_")public FlightResrvation flightResrvation;


}
