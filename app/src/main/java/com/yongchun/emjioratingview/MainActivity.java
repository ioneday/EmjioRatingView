package com.yongchun.emjioratingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView score = (TextView) findViewById(R.id.score);
        RatingView view = (RatingView) findViewById(R.id.rating_view);
        view.setOnRateChangeListener(new RatingView.OnRateChangeListener() {
            @Override
            public void onChanged(float ratio) {
                DecimalFormat df = new DecimalFormat("0.0");
                score.setText(df.format(ratio*5));
            }
        });
    }
}
