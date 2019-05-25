package com.reservation.flight.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.reservation.flight.R;


public class Contactus extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
    }

    public void call(View v) {
        String phoneNumber = "+98 935 238 8098";
        Intent dialIntent = new Intent(Intent.ACTION_DIAL,
                                       Uri.parse("tel:" + phoneNumber));
        startActivity(dialIntent);

    }

    public void email(View v) {
        String emailAddr = "hossein.mahdiyan@gmail.com";
        Uri mailto = Uri.fromParts("mailto", emailAddr, null);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, mailto);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(emailIntent);
    }

    public void findMe(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/hossein-mahdian/"));
        startActivity(browserIntent);

    }

}
