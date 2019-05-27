package com.reservation.flight.config;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.reservation.flight.dao.AirportDao;
import com.reservation.flight.dao.FlightDao;
import com.reservation.flight.dao.ReservationDao;
import com.reservation.flight.dao.RouteDao;
import com.reservation.flight.dao.UserDao;
import com.reservation.flight.datamodel.Airline;
import com.reservation.flight.datamodel.Airport;
import com.reservation.flight.datamodel.Flight;
import com.reservation.flight.datamodel.FlightResrvation;
import com.reservation.flight.datamodel.Route;
import com.reservation.flight.datamodel.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Database(entities = {Airline.class, Airport.class, Flight.class,
        FlightResrvation.class, Route.class, User.class},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase database;
    private static final String DB_NAME = "app";

    public abstract UserDao userDao();

    public abstract AirportDao airportDao();

    public abstract FlightDao flightDao();

    public abstract RouteDao routeDao();

    public abstract ReservationDao reservationDao();

    private static AppDatabase buildDatabase(Context context) {
        final File dbFile = context.getDatabasePath(DB_NAME);

        if (!dbFile.exists()) {
            copyDatabaseFile(context);
        }

        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, DB_NAME).allowMainThreadQueries()
                .build();
    }

    private static void copyDatabaseFile(Context context) {
        // code to copy the file from assets/database directory to destinationPath
        final File dbPath = context.getDatabasePath(DB_NAME);

        // If the database already exists, return
        if (dbPath.exists()) {
            Log.d("Activity", "db Path Exists");
            return;
        }

        // Make sure we have a path to the file
        dbPath.getParentFile().mkdirs();

        // Try to copy database file
        try (final InputStream inputStream = context.getAssets().open(DB_NAME);
             final OutputStream output = new FileOutputStream(dbPath)) {

            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();

        } catch (IOException e) {
            Log.d("Activity", "Failed to open file", e);
        }
    }

    public static synchronized AppDatabase getDatabase(Context context) {
        if (database == null)
            database = buildDatabase(context);
        return database;
    }


}
