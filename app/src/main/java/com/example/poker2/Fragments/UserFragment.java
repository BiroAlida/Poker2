package com.example.poker2.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poker2.Group;
import com.example.poker2.GroupActivity;
import com.example.poker2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView tv_groupid;
    private Button joinGroup;
    private DatabaseReference database;
    private String groupinput;
    private ArrayList<Integer> groupIds = new ArrayList<>();

    public UserFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user, container, false);
        tv_groupid = view.findViewById(R.id.editText_groupId);
        joinGroup = view.findViewById(R.id.button_joinGroup);
        groupinput = tv_groupid.getText().toString();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference orderidRef = rootRef.child("groups").child(groupinput);
        ValueEventListener ev = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())
                {
                    Toast.makeText(getContext(), "invalid id", Toast.LENGTH_LONG);
                }
                else
                {
                   // joinGroup.setOnClickListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

       /* database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    groupIds.add((Integer) dataSnapshot.child("groups").getValue());

                }
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

       // joinGroup.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onClick(View v) {

        ((GroupActivity)getActivity()).replaceFragment(new JoinGroupFragment());
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
