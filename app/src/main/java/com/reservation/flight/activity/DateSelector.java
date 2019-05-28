package com.reservation.flight.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Locale;

public class DateSelector {
    EditText startDate;
    EditText endDate;
    DatePickerDialog startDatePicker;
    DatePickerDialog endDatePicker;
    final Calendar todayCal = Calendar.getInstance();
    Calendar selectedStartCal;
    Calendar selectedEndCal;
    Context context;

    public DateSelector(Context ctx, EditText startDate, EditText endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.context = ctx;
    }

    void buildDatePickers() {

        startDatePicker();
        endDatePicker();
    }

    public String getStartDate() {
        return startDate.getText().toString();
    }

    public String getEndDate() {
        return endDate.getText().toString();
    }

    public Calendar getSelectedStartCal() {
        return selectedStartCal;
    }

    public Calendar getSelectedEndCal() {
        return selectedEndCal;
    }

    private void startDatePicker() {
        startDate.setOnClickListener(v -> {
            int currentYear = todayCal.get(Calendar.YEAR);
            int currentMonth = todayCal.get(Calendar.MONTH);
            int currentDay = todayCal.get(Calendar.DAY_OF_MONTH);
            startDatePicker = new DatePickerDialog(context,
                    (view, selectedYear, selectedMonthOfYear, selectedDayOfMonth) -> {
                        String monthString = String.valueOf(selectedMonthOfYear + 1);
                        String modifiedMonthString = monthString;
                        if (monthString.length() == 1) {
                            modifiedMonthString = "0" + monthString;
                        }
                        String dayOfMonthString = String.valueOf(selectedDayOfMonth);
                        String modifiedDayOfMonthString = dayOfMonthString;
                        if (dayOfMonthString.length() == 1) {
                            modifiedDayOfMonthString = "0" + dayOfMonthString;
                        }
                        selectedStartCal = Calendar.getInstance();
                        selectedStartCal.set(selectedYear, selectedMonthOfYear, selectedDayOfMonth);

                        startDate.setText(String.format(Locale.ENGLISH, "%d-%s-%s",
                                selectedYear, modifiedMonthString, modifiedDayOfMonthString));

                    }, currentYear, currentMonth, currentDay);
            startDateShow();
        });
    }

    private void endDatePicker() {
        endDate.setOnClickListener(v -> {
            int currentYear = todayCal.get(Calendar.YEAR);
            int currentMonth = todayCal.get(Calendar.MONTH);
            int currentDay = todayCal.get(Calendar.DAY_OF_MONTH);

            endDatePicker = new DatePickerDialog(context,
                    (view, selectedYear, selectedMonthOfYear, selectedDayOfMonth) -> {
                        String monthString = String.valueOf(selectedMonthOfYear + 1);
                        String modifiedMonthString = monthString;
                        if (monthString.length() == 1) {
                            modifiedMonthString = "0" + monthString;
                        }
                        String dayOfMonthString = String.valueOf(selectedDayOfMonth);
                        String modifiedDayOfMonthString = dayOfMonthString;
                        if (dayOfMonthString.length() == 1) {
                            modifiedDayOfMonthString = "0" + dayOfMonthString;
                        }
                        selectedEndCal = Calendar.getInstance();
                        selectedEndCal.set(selectedYear, selectedMonthOfYear, selectedDayOfMonth);

                        endDate.setText(String.format(Locale.ENGLISH, "%d-%s-%s",
                                selectedYear, modifiedMonthString, modifiedDayOfMonthString));

                    }, currentYear, currentMonth, currentDay);
            endDateShow();
        });
    }

    public void endDateShow() {
        endDatePicker.show();
    }

    public void startDateShow() {
        startDatePicker.show();
    }



}