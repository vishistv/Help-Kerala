package com.vitvellore.vishistvarugeese.helpkerala;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PeopleActivity extends AppCompatActivity {

    // List view
    private ListView lv;
    ArrayAdapter<String> adapter;
    List<String> listItem;

    EditText inputSearch;

    private DatabaseReference databaseReference;
    ProgressDialog pd;

    private String name;
    private String address;
    private String phonenumber;
    private String message;
    private String date;
    private String time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        listItem = new ArrayList<String>();
        pd = new ProgressDialog(PeopleActivity.this);

        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.people, listItem);
        lv.setAdapter(adapter);

        pd.setMessage("Loading...");
        pd.show();

        listItem.clear();
        adapter.notifyDataSetChanged();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("person");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             for (DataSnapshot ds : dataSnapshot.getChildren()) {
                 name = ds.child("name").getValue(String.class);
                 address = ds.child("address").getValue(String.class);
                 phonenumber = ds.child("phonenumber").getValue(String.class);
                 message = ds.child("message").getValue(String.class);
                 date = ds.child("date").getValue(String.class);
                 time = ds.child("time").getValue(String.class);
                 String value = "Date: " + date + "\n" + "Time: " + time + "\n" + "Name: " + name + "\n" + "Address: " + address + "\n" + "Phone Number: " + phonenumber + "\n" + "Message: " + message;
                 listItem.add(value);
                 adapter.notifyDataSetChanged();
                 Log.d("ListItem", value);
             }
             pd.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ListItem", databaseError + "");

            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                PeopleActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
}