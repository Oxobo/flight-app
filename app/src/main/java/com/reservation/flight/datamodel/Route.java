package com.reservation.flight.datamodel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "route",
        indices = {
                @Index(value = {"airlineID"}),
                @Index(value = {"arrivalAirportID"}),
                @Index(value = {"departureAirportID"})
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Airline.class,
                        parentColumns = "airlineID",
                        childColumns = "airlineID"
                ),
                @ForeignKey(
                        entity = Airport.class,
                        parentColumns = "airportID",
                        childColumns = "departureAirportID"
                ),
                @ForeignKey(
                        entity = Airport.class,
                        parentColumns = "airportID",
                        childColumns = "arrivalAirportID"
                )
        })
public class Route {
    @PrimaryKey(autoGenerate = true)
    Integer routeID;
    @ColumnInfo(name = "airlineID")
    Integer airline;
    @ColumnInfo(name = "departureAirportID")
    Integer departureAirportID;
    @ColumnInfo(name = "arrivalAirportID")
    Integer arrivalAirportID;

    public Integer getRouteID() {
        return routeID;
    }

    public Integer getAirline() {
        return airline;
    }

    public Integer getDepartureAirport() {
        return departureAirportID;
    }

    public Integer getArrivalAirport() {
        return arrivalAirportID;
    }

}
