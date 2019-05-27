package com.reservation.flight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.reservation.flight.R;
import com.reservation.flight.config.SaveSharedPreference;
import com.reservation.flight.datamodel.User;

public class Log extends MainActivity {
    EditText usernameText;
    EditText passwordText;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usernameText = findViewById(R.id.edituser);
        passwordText = findViewById(R.id.editpassword);
    }

    public void logIn(View v) {
        username = usernameText.getText().toString();
        password = passwordText.getText().toString();
        User user = userRepository.fetchUsersWithUsername(username);
        if (user != null) {
            SaveSharedPreference.setLoggedIn(getApplicationContext(), username, true);
            Toast.makeText(getApplicationContext(), "Welcome  " + username, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(), "Unknow user ! please sign up !", Toast.LENGTH_LONG).show();
    }

    public void addAccount(View v) {
        Intent signUpIntent = new Intent(this, SignUp.class);
        startActivity(signUpIntent);
    }

}
