package com.reservation.flight.viewmodel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

import com.reservation.flight.datamodel.Airport;
import com.reservation.flight.datamodel.Flight;

public class FlightView {

    @ColumnInfo(name = "airline_name")
    public String airlineName;
    @Embedded(prefix = "depar_") public Airport departureAirport;
    @Embedded(prefix = "arriv_")public Airport arrivalAirport;
    @Embedded(prefix = "fli_")public Flight flight;


}
