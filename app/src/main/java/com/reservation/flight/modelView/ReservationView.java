package com.reservation.flight.modelView;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

import com.reservation.flight.model.Airport;
import com.reservation.flight.model.Flight;
import com.reservation.flight.model.FlightResrvation;

public class ReservationView {

    @ColumnInfo(name = "airline_name")
    public String airlineName;
    @Embedded(prefix = "departure_") public Airport departureAirport;
    @Embedded(prefix = "arrival_")public Airport arrivalAirport;
    @Embedded(prefix = "fl_")public Flight flight;
    @Embedded(prefix = "fr_")public FlightResrvation flightResrvation;


}
