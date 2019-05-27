package com.reservation.flight.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.reservation.flight.R;
import com.reservation.flight.datamodel.User;


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
        String usernameStr = userName.getText().toString();
        String passwordStr = password.getText().toString();
        String firstnameStr = firstname.getText().toString();
        String lastnameStr = lastname.getText().toString();
        String ageStr = age.getText().toString();

        if (usernameStr.isEmpty() || passwordStr.isEmpty() ||
                firstnameStr.isEmpty() || lastnameStr.isEmpty()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(SignUp.this);
            alert.setMessage("Please complete the required information !")
                    .setTitle("Attention")
                    .setCancelable(true)
                    .setPositiveButton("OK",
                            (dialog, id) -> dialog.cancel())
                    .show();

        } else {
            User user = new User(usernameStr, passwordStr);
            user.firstname(firstnameStr)
                    .lastname(lastnameStr)
                    .age(Integer.parseInt(ageStr));
            userRepository.insertUser(user);
            Toast.makeText(getApplicationContext(),
                    "Accout Created : " + usernameStr,
                    Toast.LENGTH_LONG).show();
            Intent login = new Intent(this, Log.class);
            startActivity(login);
        }
    }

}
