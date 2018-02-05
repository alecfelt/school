package edu.cmps121.app.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */

public class MenuFragment extends Fragment implements View.OnClickListener {

    OnMenuSelectedListener mCallback;
    Button newButton, loadButton;
    ImageView iv;
    boolean dualPane;
    Model model;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = new View(getActivity());

        if(container.findViewById(R.id.layout) != null) {
            dualPane=false;
        } else {
            dualPane=true;
        }

        newButton = new Button(this.getContext()); newButton.setText("New");
        loadButton = new Button(this.getContext()); loadButton.setText("Load");

        newButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);

        FrameLayout.LayoutParams newB = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ); newB.gravity=Gravity.LEFT;
        FrameLayout.LayoutParams loadB = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ); loadB.gravity=Gravity.RIGHT;

        container.addView(newButton, newB);
        container.addView(loadButton, loadB);

        iv = new ImageView(this.getContext());

        model = Model.getInstance();

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == newButton) {
            model.clearBoard();
            mCallback.newButton();
        } else {
            mCallback.loadButton();
        }
    }

    // Container Activity must implement this interface
    public interface OnMenuSelectedListener {
        public void newButton();
        public void loadButton();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnMenuSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMenuSelectedListener");
        }
    }

}
