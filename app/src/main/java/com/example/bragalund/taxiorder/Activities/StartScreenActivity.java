package com.example.bragalund.taxiorder.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.bragalund.taxiorder.R;
import com.example.bragalund.taxiorder.Util.AnimatorClass;

public class StartScreenActivity extends AppCompatActivity {

    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        initWidgets();
        //doAnimation(); // <------ comment out this method to do integrationtests in StartScreenActivityIntrumentedTest.class
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

    //Button covering the whole start-screen uses this method as onClick (next_intent_button in activity_start_screen.xml)
    public void startOrder(View v) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }


}
