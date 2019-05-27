package com.reservation.flight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.reservation.flight.R;
import com.reservation.flight.config.SaveSharedPreference;
import com.reservation.flight.datamodel.FlightResrvation;
import com.reservation.flight.datamodel.User;
import com.reservation.flight.viewmodel.FlightView;
import com.reservation.flight.repository.FlightRepository;
import com.reservation.flight.repository.ReservationRepository;

public class ReserveDetail extends MainActivity {

    private TextView userFirstnameText;
    private TextView userLastnameText;
    private TextView departCityText;
    private TextView departAirportCodeText;
    private TextView fromTimeText;
    private TextView fromDateText;
    private TextView arriveCityText;
    private TextView arriveAirportCodeText;
    private TextView toTimeText;
    private TextView toDateText;
    private TextView flightNumberText;
    private EditText seatNumberEdit;

    private String userFirstname;
    private String userLastname;
    private String departCity;
    private String departAirportCode;
    private String fromTime;
    private String fromDate;
    private String arriveCity;
    private String arriveAirportCode;
    private String toTime;
    private String toDate;
    private String flightNumber;
    private User user;

    FlightRepository flightRepository;
    ReservationRepository reservationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_detail);
        getViewItems();
        initData();
        initializeViewItems();
    }

    private void getViewItems() {
        userFirstnameText = findViewById(R.id.userFirstnameText);
        userLastnameText = findViewById(R.id.userLastnameText);
        departCityText = findViewById(R.id.departCity);
        departAirportCodeText = findViewById(R.id.departAirportCode);
        fromTimeText = findViewById(R.id.fromTime);
        fromDateText = findViewById(R.id.fromDate);
        arriveCityText = findViewById(R.id.arriveCity);
        arriveAirportCodeText = findViewById(R.id.arriveAirportCode);
        toTimeText = findViewById(R.id.toTime);
        toDateText = findViewById(R.id.toDate);
        flightNumberText = findViewById(R.id.flightNumber);
        seatNumberEdit = findViewById(R.id.seatNumberEdit);
    }

    private void initData() {
        flightRepository = new FlightRepository(getApplication());
        reservationRepository = new ReservationRepository(getApplication());
        String username = SaveSharedPreference.getUsername(getApplicationContext());
        user = userRepository.fetchUsersWithUsername(username);
        userFirstname = user.getFirstname();
        userLastname = user.getLastname();

        Integer flightNumberInt = getIntent().getIntExtra("flightNumber", -1);
        FlightView flightView = flightRepository.getFlightDetailByFlightNumber(flightNumberInt);
        departCity = flightView.departureAirport.getCity();
        departAirportCode = flightView.departureAirport.getCode();
        fromTime = flightView.flight.getDepartureTime();
        fromDate = flightView.flight.getDepartureDate();
        arriveCity = flightView.arrivalAirport.getCity();
        arriveAirportCode = flightView.arrivalAirport.getCode();
        toTime = flightView.flight.getArrivalTime();
        toDate = flightView.flight.getArrivalDate();
        flightNumberInt = flightView.flight.getFlightNumber();
        flightNumber = String.valueOf(flightNumberInt);
    }

    private void initializeViewItems() {
        userFirstnameText.setText(userFirstname);
        userLastnameText.setText(userLastname);
        departCityText.setText(departCity);
        departAirportCodeText.setText(departAirportCode);
        fromTimeText.setText(fromTime);
        fromDateText.setText(fromDate);
        arriveCityText.setText(arriveCity);
        arriveAirportCodeText.setText(arriveAirportCode);
        toTimeText.setText(toTime);
        toDateText.setText(toDate);
        flightNumberText.setText(flightNumber);
    }

    public void reserveFlight(View view) {
        int seatNum = Integer.parseInt(seatNumberEdit.getText().toString());
        FlightResrvation flightResrvation = new FlightResrvation();
        flightResrvation.setFlightNumber(Integer.parseInt(flightNumber));
        flightResrvation.setSeatNumber(seatNum);
        flightResrvation.setUserID(user.getUserID());
        long ticketNumber = reservationRepository.insertFlightResrvation(flightResrvation);
        Toast.makeText(getApplicationContext(),
                "Reserved Successfully : " + userFirstname + " "+ userLastname
                + " your Ticket number is "+ ticketNumber,
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), Profile.class);
        startActivity(intent);
    }

}