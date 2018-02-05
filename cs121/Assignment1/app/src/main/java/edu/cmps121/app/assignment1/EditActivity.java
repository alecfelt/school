package edu.cmps121.app.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Spinner;
import java.util.List;
import java.util.ArrayList;
import java.io.OutputStreamWriter;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private void populateSpinner(Spinner year) {
        List<String> years = new ArrayList<String>();
        for(int i = 2018; i > 1959; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter);
    }

    private void setData(EditText n, EditText p, Spinner y) {
        try {
            // open viewContents.txt for writing
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("file.txt",
                    MODE_APPEND));

            String str = "Name: " + n.getText().toString() + ", Photographer: " + p.getText().toString() + ", Year: " + y.getSelectedItem().toString() + "\n";
            out.write(str);
            out.close();

            Toast.makeText(this,"Saved!",Toast.LENGTH_LONG).show();
        } catch(java.io.IOException e){
            Toast.makeText(this,"Text not saved, ERROR",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        Button done = (Button)findViewById(R.id.back);
        final EditText name = (EditText)findViewById(R.id.nameText);
        final EditText photographer = (EditText)findViewById(R.id.photographerText);
        final Spinner year = (Spinner)findViewById(R.id.spinner);
        populateSpinner(year);

        done.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(name, photographer, year);
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

}