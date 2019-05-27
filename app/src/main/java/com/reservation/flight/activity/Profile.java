package com.reservation.flight.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.reservation.flight.R;
import com.reservation.flight.config.SaveSharedPreference;
import com.reservation.flight.datamodel.User;
import com.reservation.flight.viewmodel.ReservationView;
import com.reservation.flight.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

public class Profile extends MainActivity {
    ReservationRepository reservationRepository;

    private ArrayList<String> fromCities = new ArrayList<>();
    private ArrayList<String> toCities = new ArrayList<>();
    private ArrayList<String> deparTimes = new ArrayList<>();
    private ArrayList<String> deparDates = new ArrayList<>();
    private ArrayList<String> arrivTimes = new ArrayList<>();
    private ArrayList<String> airlines = new ArrayList<>();
    private ArrayList<Integer> flightNumbers = new ArrayList<>();
    private ArrayList<Integer> seatNumbers = new ArrayList<>();
    private ArrayList<Integer> ticketNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_list);
        ListView listView = (ListView) findViewById(R.id.profileListview);
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.profile_header_layout, listView,false);
        listView.addHeaderView(headerView);
        generateTravelList();
        listView.setAdapter(new Profile.ProfileAdapter(this, R.layout.profile_row_layout, deparTimes));


    }

    private void generateTravelList() {
        reservationRepository = new ReservationRepository(getApplication());
        String username = SaveSharedPreference.getUsername(getApplicationContext());
        User user = userRepository.fetchUsersWithUsername(username);
        List<ReservationView> reservationHistory = reservationRepository.getReservationHistory(user.getUserID());

        for (ReservationView reservationView : reservationHistory) {
            fromCities.add(reservationView.departureAirport.getCity());
            toCities.add(reservationView.arrivalAirport.getCity());
            deparTimes.add(reservationView.flight.getDepartureTime());
            deparDates.add(reservationView.flight.getDepartureDate());
            arrivTimes.add(reservationView.flight.getArrivalTime());
            airlines.add(reservationView.airlineName);
            flightNumbers.add(reservationView.flightResrvation.getFlightNumber());
            seatNumbers.add(reservationView.flightResrvation.getSeatNumber());
            ticketNumbers.add(reservationView.flightResrvation.getTicketNumber());
        }
    }

    public class ProfileAdapter extends ArrayAdapter<String> {
        private int layout;

        private String[] fromCitiesArr;
        private String[] toCitiesArr;
        private String[] deparTimesArr;
        private String[] deparDatesArr;
        private String[] arrivTimesArr;
        private String[] airlinesArr;
        private Integer[] flightNumbersArr;
        private Integer[] seatNumbersArr;
        private Integer[] ticketNumbersArr;

        public ProfileAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
            fromCitiesArr = fromCities.toArray(new String[0]);
            toCitiesArr = toCities.toArray(new String[0]);
            deparTimesArr = deparTimes.toArray(new String[0]);
            deparDatesArr = deparDates.toArray(new String[0]);
            arrivTimesArr = arrivTimes.toArray(new String[0]);
            airlinesArr = airlines.toArray(new String[0]);
            flightNumbersArr = flightNumbers.toArray(new Integer[0]);
            seatNumbersArr = seatNumbers.toArray(new Integer[0]);
            ticketNumbersArr = ticketNumbers.toArray(new Integer[0]);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ProfileViewHolder viewHolder;
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                rowView = inflater.inflate(layout, parent, false);
                viewHolder = new ProfileViewHolder();
                viewHolder.fromCity = (TextView) rowView.findViewById(R.id.from_city_column);
                viewHolder.toCity = (TextView) rowView.findViewById(R.id.to_city_column);
                viewHolder.departureTime = (TextView) rowView.findViewById(R.id.departure_time_column);
                viewHolder.departureDate = (TextView) rowView.findViewById(R.id.departure_date_column);
                viewHolder.arriveTime = (TextView) rowView.findViewById(R.id.arrive_time_column);
                viewHolder.airlineName = (TextView) rowView.findViewById(R.id.airline_name_column);
                viewHolder.flightNumber = (TextView) rowView.findViewById(R.id.flight_number_column);
                viewHolder.seatNumber = (TextView) rowView.findViewById(R.id.seat_number_column);
                viewHolder.ticketNumber = (TextView) rowView.findViewById(R.id.ticket_number_column);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ProfileViewHolder) rowView.getTag();
            }
            viewHolder.fromCity.setText(fromCitiesArr[position]);
            viewHolder.toCity.setText(toCitiesArr[position]);
            viewHolder.departureTime.setText(deparTimesArr[position]);
            viewHolder.departureDate.setText(deparDatesArr[position]);
            viewHolder.arriveTime.setText(arrivTimesArr[position]);
            viewHolder.airlineName.setText(airlinesArr[position]);
            viewHolder.arriveTime.setText(arrivTimesArr[position]);
            viewHolder.flightNumber.setText(String.valueOf(flightNumbersArr[position]));
            viewHolder.seatNumber.setText(String.valueOf(seatNumbersArr[position]));
            viewHolder.ticketNumber.setText(String.valueOf(ticketNumbersArr[position]));
            return rowView;
        }

        public class ProfileViewHolder {
            TextView fromCity;
            TextView toCity;
            TextView departureTime;
            TextView departureDate;
            TextView arriveTime;
            TextView airlineName;
            TextView flightNumber;
            TextView seatNumber;
            TextView ticketNumber;
        }
    }
}
