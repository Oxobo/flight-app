package com.reservation.flight.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.reservation.flight.R;
import com.reservation.flight.config.SaveSharedPreference;
import com.reservation.flight.viewmodel.FlightView;
import com.reservation.flight.repository.FlightRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchResult extends MainActivity {
    private String departureLocation = "";
    private String arrivalLocation = "";
    private ArrayList<String> deparTimes = new ArrayList<>();
    private ArrayList<String> deparDates = new ArrayList<>();
    private ArrayList<String> arrivTimes = new ArrayList<>();
    private ArrayList<String> airlines = new ArrayList<>();
    private ArrayList<Integer> flightNumbers = new ArrayList<>();

    FlightRepository flightRepository;
    List<FlightView> flightViews;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_list);
        listView = findViewById(R.id.reserveListview);
        generateListContent();
        listView.setAdapter(new ListAdapter(this, R.layout.reservation_list_item, deparTimes));
        selectOneFlight(listView);
    }

    private void generateListContent() {
        flightRepository = new FlightRepository(getApplication());
        String dateFrom = getIntent().getStringExtra("dateFrom");
        String dateTo = getIntent().getStringExtra("dateTo");
        String selectedDeparture = getIntent().getStringExtra("selectedDeparture");
        String selectedDestination = getIntent().getStringExtra("selectedDestination");
        flightViews = flightRepository
                .fetchFlightsByDateAndRoute(dateFrom, dateTo,
                        selectedDeparture, selectedDestination);

        departureLocation = flightViews.get(0).departureAirport.getCity();
        arrivalLocation = flightViews.get(0).arrivalAirport.getCity();
        setFlightDetails(flightViews);

        flightRepository
                .observableFlightsByDateAndRoute(dateFrom, dateTo,
                        selectedDeparture, selectedDestination)
                .observe(this, flightViewList -> {
                    if (flightViewList != null && !flightViewList.isEmpty()) {
                        listView.requestLayout();
                        setFlightDetails(flightViewList);
                    }
                });
    }

    private void setFlightDetails(@Nullable List<FlightView> flightViews) {
        if (flightViews != null && !flightViews.isEmpty()) {
            for (FlightView flightView : flightViews) {
                deparTimes.add(flightView.flight.getDepartureTime());
                deparDates.add(flightView.flight.getDepartureDate());
                arrivTimes.add(flightView.flight.getArrivalTime());
                airlines.add(flightView.airlineName);
                flightNumbers.add(flightView.flight.getFlightNumber());
            }
        }
    }

    private void selectOneFlight(ListView listView) {
        Integer[] flightNumberArr = flightNumbers.toArray(new Integer[0]);
        listView.requestLayout();
        listView.setOnItemClickListener(
                (adapterView, view, i, l) -> {
                    if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
                        Intent intent = new Intent(getBaseContext(), ReserveDetail.class);
                        intent.putExtra("flightNumber", flightNumberArr[i]);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(SearchResult.this);
                        alert.setTitle("Please login!!")
                                .setMessage("You must log in to book a flight.")
                                .setCancelable(true)
                                .setPositiveButton("OK",
                                        (dialog, id) -> {
                                            Intent intent = new Intent(getBaseContext(), Log.class);
                                            startActivity(intent);
                                        }).show();
                    }
                }
        );
    }

    private class ListAdapter extends ArrayAdapter<String> {
        private int layout;
        private String[] deparTimeArr;
        private String[] deparDateArr;
        private String[] arrivTimeArr;
        private String[] airlineArr;

        private ListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
            deparTimeArr = deparTimes.toArray(new String[0]);
            deparDateArr = deparDates.toArray(new String[0]);
            arrivTimeArr = arrivTimes.toArray(new String[0]);
            airlineArr = airlines.toArray(new String[0]);
        }

        @Override
        public int getCount() {
            return deparTimeArr.length;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            listView.requestLayout();
            ListViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                viewHolder = new ListViewHolder();
                viewHolder.fromCity = convertView.findViewById(R.id.reservation_from_city);
                viewHolder.departureTime = convertView.findViewById(R.id.reservation_departure_time);
                viewHolder.departureDate = convertView.findViewById(R.id.reservation_departure_date);
                viewHolder.imageView = convertView.findViewById(R.id.imageInList);
                viewHolder.airlineName = convertView.findViewById(R.id.reservation_airline_name);
                viewHolder.toCity = convertView.findViewById(R.id.reservation_to_city);
                viewHolder.arriveTime = convertView.findViewById(R.id.reservation_arrive_time);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ListViewHolder) convertView.getTag();
            }
            viewHolder.fromCity.setText(departureLocation);
            viewHolder.departureTime.setText(deparTimeArr[position]);
            viewHolder.departureDate.setText(deparDateArr[position]);
            viewHolder.airlineName.setText(airlineArr[position]);
            viewHolder.toCity.setText(arrivalLocation);
            viewHolder.arriveTime.setText(arrivTimeArr[position]);
            return convertView;
        }

        private class ListViewHolder {
            TextView fromCity;
            TextView departureTime;
            TextView departureDate;
            ImageView imageView;
            TextView airlineName;
            TextView toCity;
            TextView arriveTime;
        }
    }


}
