package edu.ucsc.cmps121.lecture3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;



public class MainActivity extends AppCompatActivity{
    ImageView image;
    static final private String LOG_TAG = "test2017app1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setBackgroundColor(Color.GREEN);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        image = new ImageView(this);
        image.setImageResource(R.drawable.off);
        mainLayout.addView(image, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        Switch lightSwitch = new Switch(this);

        LinearLayout.LayoutParams lightSwitchLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        lightSwitchLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        mainLayout.addView(lightSwitch, lightSwitchLayoutParams);

        setContentView(mainLayout);


        lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.i(LOG_TAG, Boolean.toString(b));
                if(b){
                    image.setImageResource(R.drawable.on);
                }
                else{
                    image.setImageResource((R.drawable.off));
                }
            }
        });

    }


}
