package edu.cmps121.app.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewActivity extends AppCompatActivity {

    private void getData(ListView l) {
        try {
            FileInputStream in = openFileInput("file.txt");
            InputStreamReader inn = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inn);
            String line;
            ArrayList<String> listItems=new ArrayList<String>();
            ArrayAdapter<String> adapter;
            while ((line = bufferedReader.readLine()) != null) {
                listItems.add(line);
            }
            adapter=new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    listItems);
            l.setAdapter(adapter);
        } catch(java.io.IOException e) {
            Toast.makeText(this,"Text not saved, ERROR",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Button back = (Button)findViewById(R.id.back);
        ListView list = (ListView)findViewById(R.id.list);
        getData(list);

        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}