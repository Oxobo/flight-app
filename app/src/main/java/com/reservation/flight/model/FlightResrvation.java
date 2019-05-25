package com.reservation.flight.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "flight_reservation",
        indices = {@Index( value ={"flightNumber"}),
                   @Index( value ={"userID"})},
        foreignKeys = {
                @ForeignKey(
                        entity = Flight.class,
                        parentColumns = "flightNumber",
                        childColumns = "flightNumber"
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userID",
                        childColumns = "userID"
                )}
)
public class FlightResrvation {

    @PrimaryKey
    Integer ticketNumber;
    @ColumnInfo(name = "flightNumber")
    Integer flightNumber;
    @ColumnInfo(name = "seatNum")
    Integer seatNumber;
    @ColumnInfo(name = "userID")
    Integer userID;

    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
