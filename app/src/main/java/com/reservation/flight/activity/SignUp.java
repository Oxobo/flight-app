package com.reservation.flight.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.reservation.flight.R;
import com.reservation.flight.model.User;


public class SignUp extends MainActivity {
    EditText userName;
    EditText password;
    EditText firstname;
    EditText lastname;
    EditText age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        userName = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordForm);
        firstname = (EditText) findViewById(R.id.firstnameEditText);
        lastname = (EditText) findViewById(R.id.lastnameEditText);
        age = (EditText) findViewById(R.id.ageEditText);

    }

    public void submit(View v) {
        User user = new User(userName.getText().toString(),
                password.getText().toString());
        user.firstname(firstname.getText().toString())
                .lastname(lastname.getText().toString())
                .age(Integer.valueOf(age.getText().toString()));
        appDatabase.userDao().insertUser(user);
        Toast.makeText(getApplicationContext(),
                "Accout Created : " + userName.getText().toString(),
                Toast.LENGTH_LONG).show();
    }

}
