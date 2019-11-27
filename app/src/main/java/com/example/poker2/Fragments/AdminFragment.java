package com.example.poker2.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.poker2.GroupActivity;
import com.example.poker2.R;


public class AdminFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button addGroup;

    public AdminFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_admin, container, false);
        addGroup = view.findViewById(R.id.button_AddGroup);
        addGroup.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        ((GroupActivity)getActivity()).replaceFragment(new AddGroupFragment()); // after clicking the addGroup button the fragment changes inside the GroupActivity from AdminFragment to AddGroupFragment
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
