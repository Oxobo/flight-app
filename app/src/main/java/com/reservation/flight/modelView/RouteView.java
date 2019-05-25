package com.reservation.flight.modelView;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

import com.reservation.flight.model.Airport;

public class RouteView {
    @ColumnInfo(name = "airline_name")
    public String airlineName;
    @Embedded(prefix = "departure_")public Airport departureAirport;
    @Embedded(prefix = "arrival_")public Airport arrivalAirport;


}
