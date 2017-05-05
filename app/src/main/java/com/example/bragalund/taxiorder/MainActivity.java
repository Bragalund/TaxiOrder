package com.example.bragalund.taxiorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        doAnimation();
    }

    private void initWidgets() {
        textview = (TextView) findViewById(R.id.firstTextView);
    }

    private void doAnimation() {
        AnimatorClass animator = new AnimatorClass(textview, new String[]{
                "We hope to see you again.",
                "Order your taxi here!",
                "Get it instantly.",
                "The easy way to order a taxi."
        });

        animator.startAnimation();
    }

    public void startOrder(View v) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }


}
