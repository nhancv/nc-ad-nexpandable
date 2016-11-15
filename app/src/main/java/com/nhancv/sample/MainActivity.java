package com.nhancv.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.nhancv.nexpandable.NExpandableListView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private final String[] array = {"Header 1", "Header 2", "Header 3", "Header 4", "Header 5", "Header 6", "Header 7"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CustomAdapter<String> arrayAdapter = new CustomAdapter<>(this, Arrays.asList(array));
        arrayAdapter.setContentListAdapter(new QuickAdapter<String>(this, R.layout.view_list_item, Arrays.asList(array)) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.tvTx1, item);
                helper.setVisible(R.id.vContentLine, (helper.getPosition() < array.length - 1));
            }
        });
        final NExpandableListView expandableLayoutListView = (NExpandableListView) findViewById(R.id.listview);
        expandableLayoutListView.setAdapter(arrayAdapter);
    }
}
