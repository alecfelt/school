package edu.cmps121.app.myapplication;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment implements View.OnTouchListener, View.OnClickListener, Observer{

    Button menuButton;
    ImageView iv;
    int width, height;
    Bitmap b;
    Canvas canvas;
    Paint paint;
    Model model;
    String turn;
    OnBoardSelectedListener mCallback;

    public void displayWinner() {
        turn = model.getTurn();
        String s = turn + " won!";
        toast(s);
    }

    public void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    public BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = new View(getActivity());
        model = Model.getInstance();

        menuButton = new Button(this.getContext()); menuButton.setText("Menu");
        menuButton.setOnClickListener(this);

        FrameLayout.LayoutParams menuB = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ); menuB.gravity = Gravity.CENTER_HORIZONTAL;

        container.addView(menuButton, menuB);

        iv = new ImageView(this.getContext());

        FrameLayout.LayoutParams ivLayout = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ); ivLayout.gravity=Gravity.CENTER;

        container.addView(iv, ivLayout);
        iv.setOnTouchListener(this);

        v.post(new Runnable() {
            @Override
            public void run() {
                width = v.getMeasuredWidth();
                height = v.getMeasuredHeight();
                b = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(b);
                paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                onDraw(canvas);
            }
        });

        model.setWon(false);
        turn = model.getTurn();
        model.addObserver(this);

        return v;
    }

    @Override
    public boolean onTouch(View v, MotionEvent m) {
        int action = m.getAction();
        if(action == m.ACTION_UP) {
            float actionX = m.getX();
            int col = (int) (actionX / (width / 7));
            model.add(col);
            if(model.getWon()) { displayWinner(); model.clearBoard(); model.setWon(false); }
            mCallback.save();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        mCallback.menuButton();
    }

    public void onDraw(Canvas c) {
            b.eraseColor(Color.TRANSPARENT);
            int strokeWidth = 10;
            paint.setStrokeWidth(strokeWidth);
            turn = model.getTurn();
            if(Objects.equals(turn, "red")) { paint.setColor(Color.RED); } else { paint.setColor(Color.BLUE); }
            for (int i = 1; i < 7; i++) {
                int x = i * ((int) width / 7);
                canvas.drawLine(x, 0, x, height, paint);
            }
            for (int i = 1; i < 6; i++) {
                int y = i * ((int) height / 6);
                canvas.drawLine(0, y, width, y, paint);
            }
            String[][] grid = model.getBoard();
            if(grid[0][0] == null) { model.clearBoard(); grid=model.getBoard(); }
            for (int i = 0; i < 6; i++) {
                int y = ((i + 1) * ((int) height / 6)) - ((int) height / 12);
                for (int j = 0; j < 7; j++) {
                    int x = ((j + 1) * ((int) width / 7)) - ((int) width / 14);
                    int r = Math.min(((int) width / 14), ((int) height / 12)) - 10;
                    if (Objects.equals(grid[i][j], "red")) {
                        paint.setColor(Color.RED);
                        canvas.drawCircle(x, y, r, paint);
                    }
                    if (Objects.equals(grid[i][j], "blue")) {
                        paint.setColor(Color.BLUE);
                        canvas.drawCircle(x, y, r, paint);
                    }
                }
            }
            iv.setImageBitmap(b);
    }

    @Override
    public void update(Observable ob, Object o) { onDraw(canvas); }

    public interface OnBoardSelectedListener {
        public void menuButton();
        public void save();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (BoardFragment.OnBoardSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMenuSelectedListener");
        }
    }

}
