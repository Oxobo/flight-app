package com.reservation.flight.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.reservation.flight.R;
import com.reservation.flight.model.Airport;
import com.reservation.flight.modelView.FlightView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class SearchForm extends MainActivity {

    ListView deparListView;
    ListView arrivListView;
    EditText startDate;
    EditText endDate;
    DatePickerDialog startDatePicker;
    DatePickerDialog endDatePicker;
    EditText flightEditFrom;
    EditText flightEditTo;
    String selectedDeparture;
    String selectedDestination;
    TextWatcher txtSearch;

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
        startDate = (EditText) findViewById(R.id.dateStart);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                startDatePicker = new DatePickerDialog(SearchForm.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String monthString = String.valueOf(monthOfYear + 1);
                                if (monthString.length() == 1) {
                                    monthString = "0" + monthString;
                                }
                                String dayOfMonthString = String.valueOf(dayOfMonth);
                                if (dayOfMonthString.length() == 1) {
                                    dayOfMonthString = "0" + dayOfMonthString;
                                }
                                // set day of month , month and year value in the edit usernameText
                                startDate.setText(String.format(Locale.ENGLISH, "%d-%s-%s",
                                        year, monthString, dayOfMonthString));

                            }
                        }, year, month, day);
                startDatePicker.show();
            }
        });

        endDate = (EditText) findViewById(R.id.dateAnd);
        // perform click event on edit usernameText
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c1 = Calendar.getInstance();
                int year1 = c1.get(Calendar.YEAR);
                int month1 = c1.get(Calendar.MONTH);
                int day1 = c1.get(Calendar.DAY_OF_MONTH);
                // date picker dialog
                endDatePicker = new DatePickerDialog(SearchForm.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String monthString = String.valueOf(monthOfYear + 1);
                                if (monthString.length() == 1) {
                                    monthString = "0" + monthString;
                                }
                                String dayOfMonthString = String.valueOf(dayOfMonth);
                                if (dayOfMonthString.length() == 1) {
                                    dayOfMonthString = "0" + dayOfMonthString;
                                }
                                // set day of month , month and year value in the edit usernameText
                                endDate.setText(String.format(Locale.ENGLISH, "%d-%s-%s",
                                        year, monthString, dayOfMonthString));


                            }
                        }, year1, month1, day1);
                endDatePicker.show();
            }
        });
    }

    private void initRouteData() {

        deparListView = findViewById(R.id.deparlistView);
        deparListView.setVisibility(View.INVISIBLE);
        flightEditFrom = findViewById(R.id.departureText);

        arrivListView = findViewById(R.id.destlistView);
        arrivListView.setVisibility(View.INVISIBLE);
        flightEditTo = findViewById(R.id.destinationEditView);

        List<Airport> allAirports = appDatabase.airportDao().getAllAirports();
        Set<String> airports = new HashSet<>();
        for (Airport aAirport : allAirports) {
            airports.add(aAirport.getCity());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.airport_name, new ArrayList<>(airports));
        deparListView.setAdapter(adapter);
        arrivListView.setAdapter(adapter);

        flightEditFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                deparListView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (deparListView.getVisibility() != View.VISIBLE)
                    deparListView.setVisibility(View.VISIBLE);
                deparListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        flightEditFrom.setText(deparListView.getItemAtPosition(i).toString());
                        deparListView.setVisibility(View.INVISIBLE);
                    }
                });
                deparListView.getNextFocusDownId();
            }
        });

        flightEditTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrivListView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (arrivListView.getVisibility() != View.VISIBLE)
                    arrivListView.setVisibility(View.VISIBLE);

                arrivListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        flightEditTo.setText(arrivListView.getItemAtPosition(i).toString());
                        arrivListView.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });


    }

    public void searchFlights(View v) {

        selectedDeparture = String.valueOf(flightEditFrom.getText());
        selectedDestination = String.valueOf(flightEditTo.getText());

        String dateFrom = startDate.getText().toString();
        String dateTo = endDate.getText().toString();

        if (selectedDeparture.length() == 0 || selectedDestination.length() == 0 || dateTo.length() == 0 || dateFrom.length() == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(SearchForm.this);
            alert.setMessage("Please fill the information !");
            alert.setCancelable(true);
            alert.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alert.show();
        } else {
            if (!routeExists(selectedDeparture, selectedDestination)) {
                Toast.makeText(getApplicationContext(),
                        "There is no flight path between "
                                + selectedDeparture + " and " + selectedDestination,
                        Toast.LENGTH_LONG).show();
            } else {

                List<FlightView> flightViews =
                        appDatabase.
                                flightDao().
                                fetchFlightsByDepartureDateAndRoute(
                                        dateFrom,
                                        dateTo,
                                        selectedDeparture,
                                        selectedDestination);
                if (flightViews.size() > 0) {
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
        }
    }

    private boolean routeExists(String selectedDeparture, String selectedDestination) {
        return appDatabase.routeDao()
                .countRoutesBetween(selectedDeparture, selectedDestination) > 0;
    }

}
