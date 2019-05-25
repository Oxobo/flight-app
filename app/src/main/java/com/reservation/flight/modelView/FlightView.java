package com.reservation.flight.modelView;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

import com.reservation.flight.model.Airport;
import com.reservation.flight.model.Flight;

public class FlightView {

    @ColumnInfo(name = "airline_name")
    public String airlineName;
    @Embedded(prefix = "depar_") public Airport departureAirport;
    @Embedded(prefix = "arriv_")public Airport arrivalAirport;
    @Embedded(prefix = "fli_")public Flight flight;


}
