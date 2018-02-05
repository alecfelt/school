package edu.cmps121.app.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends FragmentActivity implements MenuFragment.OnMenuSelectedListener, BoardFragment.OnBoardSelectedListener {

    Model model;
    BoardFragment boardFragment;
    MenuFragment menuFragment;
    FrameLayout frameLayout;
    String boardStr;
    Boolean dualPane;
    Button newButton, loadButton, menuButton;
    ImageView board;
    float width;
    float height;
    Paint paint;
    Bitmap b;
    Canvas canvas;
    Observable ob;
    String turn;
    ViewGroup rootView;

    public void fillBoardStr() {
        boardStr = "";
        String[][] b = model.getBoard();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++) {
                String color = b[i][j];
                boardStr += color;
                if(j!=6) boardStr += "     ";
            }
            boardStr += "\n";
        }
        boardStr += model.getTurn();
    }

    public void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void loadBoard() {
        try {

            FileInputStream in = openFileInput("file.txt");

            InputStreamReader inn = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inn);
            String line;
            int row = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] rowString = line.split("[ ]+", 0);
                if(row==6) { model.setTurn(rowString[0]); row++; }
                if(rowString.length == 7) { model.setBoard(row, rowString); row++; }
            }
        } catch(java.io.IOException e) {

            Toast.makeText(this,"Text not saved, ERROR",Toast.LENGTH_LONG).show();

        }
    }

    public void saveBoard() {
        fillBoardStr();
        try {
            // open viewContents.txt for writing
            deleteFile("file.txt");
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("file.txt",
                    MODE_APPEND));

            out.write(boardStr);
            out.close();
        } catch(java.io.IOException e){
            toast("Text not saved, ERROR");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.dualPaneLayout) != null) {
            dualPane = true;
            rootView = findViewById(R.id.dualPaneLayout);

            FrameLayout menuLayout = new FrameLayout(this); menuLayout.setId(1);
            FrameLayout boardLayout = new FrameLayout(this); boardLayout.setId(2);

            rootView.addView(menuLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            rootView.addView(boardLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2));

            menuFragment = new MenuFragment();
            boardFragment = new BoardFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(menuLayout.getId(), menuFragment);
            ft.add(boardLayout.getId(), boardFragment);
            ft.commit();
        } else {
            dualPane = false;
            rootView = findViewById(R.id.layout);

            menuFragment = new MenuFragment();

            frameLayout = new FrameLayout(this); frameLayout.setId(3);
            rootView.addView(frameLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(frameLayout.getId(), menuFragment);
            ft.commit();
        }
        model = Model.getInstance();
        loadBoard();

    }

    public void newButton() {
        model.clearBoard();
        saveBoard();
        if(dualPane == false) addBoardFragment();
    }
    public void loadButton() {
        loadBoard();
        if(dualPane == false) addBoardFragment();
    }
    public void menuButton() { if(dualPane == false) addMenuFragment(); }
    public void save() { saveBoard(); }

    public void addBoardFragment() {
        rootView.removeView(frameLayout);
        boardFragment = new BoardFragment();
        frameLayout = new FrameLayout(this); frameLayout.setId(3);
        rootView.addView(frameLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(frameLayout.getId(), boardFragment);
        ft.commit();
    }

    public void addMenuFragment() {
        rootView.removeView(frameLayout);
        menuFragment = new MenuFragment();
        frameLayout = new FrameLayout(this); frameLayout.setId(3);
        rootView.addView(frameLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(frameLayout.getId(), menuFragment);
        ft.commit();
    }


}
