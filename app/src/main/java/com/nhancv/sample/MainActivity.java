package com.nhancv.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.nhancv.nexpandable.ExpandableLayoutListView;

public class MainActivity extends AppCompatActivity {
    private final String[] array = {"Hello", "World", "Android", "is", "Awesome", "World", "Android", "is", "Awesome", "World", "Android", "is", "Awesome", "World", "Android", "is", "Awesome"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.view_row, R.id.header_text, array);
        final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) findViewById(R.id.listview);
        expandableLayoutListView.setAdapter(arrayAdapter);
    }
}
