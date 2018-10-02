package com.vitvellore.vishistvarugeese.helpkerala;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HelpActivity extends AppCompatActivity {

    Button btnHelp, btnFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        btnHelp = (Button) findViewById(R.id.help);
        btnFind = (Button) findViewById(R.id.find);


    }

    public void onHelpClick(View view) {
        startActivity(new Intent(getApplicationContext(), DetailsActivity.class));
    }

    public void onFindClick(View view) {
        startActivity(new Intent(getApplicationContext(), PeopleActivity.class));
    }
}
