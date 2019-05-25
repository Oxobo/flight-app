package com.reservation.flight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.reservation.flight.R;
import com.reservation.flight.config.AppDatabase;
import com.reservation.flight.config.SaveSharedPreference;
import com.reservation.flight.model.User;

public class MainActivity extends AppCompatActivity {
    TextView userProfileName;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getDatabase(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        if (SaveSharedPreference.getLoggedStatus(getApplicationContext()))
            setProfileName();
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
    }

    private void setProfileName() {

        getSupportActionBar().setCustomView(R.layout.action_bar);
        userProfileName = (TextView) findViewById(R.id.user_profile_name);
        String username = SaveSharedPreference.getUsername(getApplicationContext());
        User user = appDatabase.userDao().fetchUsersWithUsername(username);
        String userFirstname = user.getFirstname();
        String userLastname = user.getLastname();
        userProfileName.setText(userFirstname+ " "+userLastname);

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent mainIntent = new Intent(this, MainActivity.class);
        switch (item.getItemId()) {
            case R.id.homeMenu:
                startActivity(mainIntent);
                return true;
            case R.id.menu_book:
                Intent reservationIntent = new Intent(this, SearchForm.class);
                startActivity(reservationIntent);
                return true;
            case R.id.menu_log_in:
                Intent intent4 = new Intent(this, Log.class);
                startActivity(intent4);
                return true;
            case R.id.menu_log_out:
                String username = SaveSharedPreference.getUsername(getApplicationContext());
                SaveSharedPreference.setLoggedIn(getApplicationContext(),username, false);
                Toast.makeText(getApplicationContext(),
                        "Loged out Successfully ",
                        Toast.LENGTH_LONG).show();
                startActivity(mainIntent);
                return true;
            case R.id.menu_share:
                Intent intent6 = new Intent(this, Share.class);
                startActivity(intent6);
                return true;
            case R.id.menu_contact:
                Intent intent5 = new Intent(this, Contactus.class);
                startActivity(intent5);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Profile(View v) {
        Intent i1 = new Intent(this, Profile.class);
        this.startActivity(i1);
    }

    public void Search(View v) {
        Intent i2 = new Intent(this, SearchForm.class);
        startActivity(i2);

    }

    public void Log(View v) {
        Intent i3 = new Intent(this, Log.class);
        startActivity(i3);

    }

    public void Share(View v) {
        Intent i4 = new Intent(this, Share.class);
        startActivity(i4);

    }

    public void Contact(View v) {
        Intent i5 = new Intent(this, Contactus.class);
        startActivity(i5);

    }


}
