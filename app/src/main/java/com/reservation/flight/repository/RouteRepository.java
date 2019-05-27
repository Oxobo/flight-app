package com.reservation.flight.repository;

import android.app.Application;

import com.reservation.flight.config.AppDatabase;
import com.reservation.flight.dao.RouteDao;

public class RouteRepository {
    private RouteDao routeDao;

    public RouteRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        routeDao = database.routeDao();
    }

    public int countRoutesBetween(String departureCity, String arrivalCity){
        return routeDao.countRoutesBetween(departureCity, arrivalCity);
    }


}
