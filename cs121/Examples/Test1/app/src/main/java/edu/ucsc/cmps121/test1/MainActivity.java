package edu.ucsc.cmps121.test1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private String LOG_TAG = "TestTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setBackgroundColor(Color.GREEN);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.android);
        image.setBackgroundColor(Color.BLACK);
        mainLayout.addView(image, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 2));

        Button myButton = new Button(this);
        myButton.setText(R.string.field);
        myButton.setBackgroundColor(Color.BLUE);
        mainLayout.addView(myButton, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1));
        myButton.setClickable(true);

        setContentView(mainLayout);
    }
}
