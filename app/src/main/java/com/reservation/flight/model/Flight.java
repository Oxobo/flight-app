package com.reservation.flight.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "flight",
        indices = {@Index(value = {"routeID"})},
        foreignKeys = @ForeignKey(
                entity = Route.class,
                parentColumns = "routeID",
                childColumns = "routeID"
        ))
public class Flight {
    @PrimaryKey(autoGenerate = true)
    Integer flightNumber;
    String departureTime;
    String departureDate;
    String arrivalTime;
    String arrivalDate;
    Integer routeID;

    public Flight() {
    }

    public Flight(final FlightBuilder flightBuilder) {
        this.flightNumber = flightBuilder.flightNumber;
        this.departureTime = flightBuilder.departureTime;
        this.departureDate = flightBuilder.departureDate;
        this.arrivalTime = flightBuilder.arrivalTime;
        this.arrivalDate = flightBuilder.arrivalDate;
        this.routeID = flightBuilder.routeID;
    }

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setRouteID(Integer routeID) {
        this.routeID = routeID;
    }

    public Integer getRouteID() {
        return routeID;
    }

    public class FlightBuilder {
        private Integer flightNumber;
        private String departureTime;
        private String departureDate;
        private String arrivalTime;
        private String arrivalDate;
        private Integer routeID;

        public FlightBuilder flightNumber(Integer flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }

        public FlightBuilder departureTime(String departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public FlightBuilder departureDate(String departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public FlightBuilder arrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public FlightBuilder arrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
            return this;
        }

        public FlightBuilder routeID(Integer routeID) {
            this.routeID = routeID;
            return this;
        }

        public Flight createFlight() {
            return new Flight(this);
        }
    }
}
