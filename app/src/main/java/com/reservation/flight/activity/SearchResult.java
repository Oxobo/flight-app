package com.reservation.flight.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.reservation.flight.R;
import com.reservation.flight.config.SaveSharedPreference;
import com.reservation.flight.modelView.FlightView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_list);
        ListView listView = (ListView) findViewById(R.id.reserveListview);
        generateListContent();
        listView.setAdapter(new ListAdapter(this, R.layout.reservation_list_item, deparTimes));
        selectOneFlight(listView);
    }

    private void generateListContent() {
        String dateFrom = getIntent().getStringExtra("dateFrom");
        String dateTo = getIntent().getStringExtra("dateTo");
        String selectedDeparture = getIntent().getStringExtra("selectedDeparture");
        String selectedDestination = getIntent().getStringExtra("selectedDestination");
        List<FlightView> flightViews =
                appDatabase.flightDao().fetchFlightsByDepartureDateAndRoute(dateFrom, dateTo, selectedDeparture, selectedDestination);

        departureLocation = flightViews.get(0).departureAirport.getCity();
        arrivalLocation = flightViews.get(0).arrivalAirport.getCity();

        for (FlightView flightView : flightViews) {
            deparTimes.add(flightView.flight.getDepartureTime());
            deparDates.add(flightView.flight.getDepartureDate());
            arrivTimes.add(flightView.flight.getArrivalTime());
            airlines.add(flightView.airlineName);
            flightNumbers.add(flightView.flight.getFlightNumber());
        }
    }

    private void selectOneFlight(ListView listView) {
        Integer[] flightNumberArr = flightNumbers.toArray(new Integer[0]);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
                            Intent intent = new Intent(getBaseContext(), ReserveDetail.class);
                            intent.putExtra("flightNumber", flightNumberArr[i]);
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(SearchResult.this);
                            alert.setMessage("Please login!!");
                            alert.setCancelable(true);
                            alert.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(getBaseContext(), Log.class);
                                            startActivity(intent);
                                        }
                                    });
                            alert.show();
                        }
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

        public ListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
            deparTimeArr = objects.toArray(new String[0]);
            deparDateArr = deparDates.toArray(new String[0]);
            arrivTimeArr = arrivTimes.toArray(new String[0]);
            airlineArr = airlines.toArray(new String[0]);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ListViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                viewHolder = new ListViewHolder();
                viewHolder.fromCity = (TextView) convertView.findViewById(R.id.reservation_from_city);
                viewHolder.departureTime = (TextView) convertView.findViewById(R.id.reservation_departure_time);
                viewHolder.departureDate = (TextView) convertView.findViewById(R.id.reservation_departure_date);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageInList);
                viewHolder.airlineName = (TextView) convertView.findViewById(R.id.reservation_airline_name);
                viewHolder.toCity = (TextView) convertView.findViewById(R.id.reservation_to_city);
                viewHolder.arriveTime = (TextView) convertView.findViewById(R.id.reservation_arrive_time);
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

        public class ListViewHolder {
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
