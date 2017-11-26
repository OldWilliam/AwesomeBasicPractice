package me.jim.wx.awesomebasicpractice;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import me.jim.wx.awesomebasicpractice.view.primary.FlowLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testFlowLayout();
    }

    private void testFlowLayout() {
        LayoutInflater inflater = LayoutInflater.from(this);
        FlowLayout layout = findViewById(R.id.flow_layout);
        for (int i = 0; i < 19; i++) {
            layout.addView(inflater.inflate(R.layout.flow_item, null), 0);
        }
        layout.setArrow(true);
    }
}
