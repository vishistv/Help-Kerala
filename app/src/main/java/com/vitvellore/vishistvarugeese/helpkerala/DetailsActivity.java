package com.vitvellore.vishistvarugeese.helpkerala;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {

    private EditText etName, etAddress, etPhoneNumber, etMessage;
    private String name;
    private String address;
    private String phonenumber;
    private String message;

    private DatabaseReference mDatabase;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        etName = (EditText) findViewById(R.id.name);
        etAddress = (EditText) findViewById(R.id.address);
        etMessage = (EditText) findViewById(R.id.message);
        etPhoneNumber = (EditText) findViewById(R.id.phoneNumber);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Value only for testing
//        etName.setText("Vishist");
//        etAddress.setText("VIT University vellore");
//        etMessage.setText("Hi I am Vishist I am in VIT");
//        etPhoneNumber.setText("9442799642 9443009642");

        dialog = new ProgressDialog(DetailsActivity.this);

    }

    public void onPostClick(View view) {
        dialog.show();
        dialog.setMessage("Sending Request. Please wait...");

        DateFormat dfTime = new SimpleDateFormat("h:mm a");
        String time = dfTime.format(Calendar.getInstance().getTime());

        DateFormat dfDate = new SimpleDateFormat("d-MMM-yyyy, EEE");
        String date = dfDate.format(Calendar.getInstance().getTime());

        name = etName.getText().toString();
        phonenumber = etPhoneNumber.getText().toString();
        address = etAddress.getText().toString();
        message = etMessage.getText().toString();

        Person person = new Person(name, address, phonenumber, message, date, time);

        mDatabase.child("person").child(phonenumber).setValue(person, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if(databaseError == null) {
                    Toast.makeText(getApplicationContext(), "Help request successfully sent", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try again", Toast.LENGTH_LONG).show();
            }
        });

    }
}
