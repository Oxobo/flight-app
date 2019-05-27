package com.reservation.flight.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.reservation.flight.R;
import com.reservation.flight.model.Airport;
import com.reservation.flight.modelView.FlightView;
import com.reservation.flight.repository.AirportRepository;
import com.reservation.flight.repository.FlightRepository;
import com.reservation.flight.repository.RouteRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SearchForm extends MainActivity {

    DateSelector dateSelector;
    ListView deparListView;
    ListView arrivListView;
    EditText startDate;
    EditText endDate;
    EditText flightEditFrom;
    EditText flightEditTo;
    String selectedDeparture;
    String selectedDestination;
    DatePickerDialog startDatePicker;
    DatePickerDialog endDatePicker;
    AirportRepository airportRepository;
    FlightRepository flightRepository;
    RouteRepository routeRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_form);
        initData();
    }

    private void initData() {
        initRouteData();
        initDatePicker();
    }

    private void initDatePicker() {
        startDate = findViewById(R.id.dateStart);
        endDate = findViewById(R.id.dateAnd);
        dateSelector = new DateSelector(this, startDate, endDate);
        dateSelector.buildDatePickers();
    }

    private void initRouteData() {
        airportRepository = new AirportRepository(getApplication());
        flightRepository = new FlightRepository(getApplication());
        routeRepository = new RouteRepository(getApplication());

        deparListView = findViewById(R.id.deparlistView);
        deparListView.setVisibility(View.INVISIBLE);
        flightEditFrom = findViewById(R.id.departureText);
        arrivListView = findViewById(R.id.destlistView);
        arrivListView.setVisibility(View.INVISIBLE);

        flightEditTo = findViewById(R.id.destinationEditView);
        Set<String> allAirports = new HashSet<>();
        List<Airport> allAirportList = airportRepository.getAllAirports();
        for (Airport airport : allAirportList) {
            allAirports.add(airport.getCity());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item,
                                                        R.id.airport_name, new ArrayList<>(allAirports));
        deparListView.setAdapter(adapter);
        arrivListView.setAdapter(adapter);

        flightEditFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                deparListView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (deparListView.getVisibility() != View.VISIBLE)
                    deparListView.setVisibility(View.VISIBLE);
                deparListView.setOnItemClickListener((adapterView, view, i, l) -> {
                    flightEditFrom.setText(deparListView.getItemAtPosition(i).toString());
                    deparListView.setVisibility(View.INVISIBLE);
                });
                deparListView.getNextFocusDownId();
            }
        });

        flightEditTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrivListView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (arrivListView.getVisibility() != View.VISIBLE)
                    arrivListView.setVisibility(View.VISIBLE);
                arrivListView.setOnItemClickListener((adapterView, view, i, l) -> {
                    flightEditTo.setText(arrivListView.getItemAtPosition(i).toString());
                    arrivListView.setVisibility(View.INVISIBLE);
                });
            }
        });
    }

    public void searchFlights(View v) {

        selectedDeparture = String.valueOf(flightEditFrom.getText());
        selectedDestination = String.valueOf(flightEditTo.getText());

        String dateFromStr  = String.valueOf(startDate.getText());
        String dateToStr = String.valueOf(endDate.getText());

        if (validateFields(dateFromStr, dateToStr) && dateSelector.checkDates()) {
            if (!routeExists(selectedDeparture, selectedDestination)) {
                Toast.makeText(getApplicationContext(),
                        "There is no flight path between "
                                + selectedDeparture + " and " + selectedDestination,
                        Toast.LENGTH_LONG).show();
            } else {

                findingFlights(dateFromStr, dateToStr);
            }
        }

    }

    private boolean validateFields(String dateFrom, String dateTo) {
        if (selectedDeparture.isEmpty() || selectedDestination.isEmpty() ||
                dateTo.isEmpty() || dateFrom.isEmpty()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(SearchForm.this);
            alert.setMessage("Please complete the required information !")
                    .setTitle("Attention")
                    .setCancelable(true)
                    .setPositiveButton("OK",
                            (dialog, id) -> dialog.cancel())
                    .show();
            return false;
        }
        return true;
    }

    private void findingFlights(String dateFrom, String dateTo) {
        List<FlightView> flightViews =
                flightRepository.
                        fetchFlightsByDepartureDateAndRoute(
                                dateFrom,
                                dateTo,
                                selectedDeparture,
                                selectedDestination);
        if (!flightViews.isEmpty()) {
            Intent intent = new Intent(getBaseContext(), SearchResult.class);
            intent.putExtra("dateFrom", dateFrom);
            intent.putExtra("dateTo", dateTo);
            intent.putExtra("selectedDeparture", selectedDeparture);
            intent.putExtra("selectedDestination", selectedDestination);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "No Flight Found !", Toast.LENGTH_LONG).show();
        }
    }

    private boolean routeExists(String selectedDeparture, String selectedDestination) {
        return routeRepository.countRoutesBetween(selectedDeparture, selectedDestination) > 0;
    }
}
