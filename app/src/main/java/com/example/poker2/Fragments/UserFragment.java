package com.example.poker2.Fragments;

import android.content.Intent;
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

import com.example.poker2.Activities.GroupActivity;
import com.example.poker2.Classes.Question;
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
    private FirebaseDatabase  firebaseDatabase;
    private DatabaseReference rootRef, itemsRef;
    private String groupinput;
    private ArrayList grpIds;

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


        firebaseDatabase = FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        itemsRef = rootRef.child("groups");
        grpIds = new ArrayList<String>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String ids = ds.child("groupId").getValue(String.class); // putting all of the group ids into the grpids ArrayList
                    grpIds.add(ids);
                }
                Log.d("LIST",grpIds.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        itemsRef.addListenerForSingleValueEvent(valueEventListener);

        joinGroup.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onClick(View v) {

        if (v == joinGroup)
        {
            groupinput = tv_groupid.getText().toString();

            if(grpIds.contains(groupinput)) // if the users input of the group id is in the ArrayList then he can join the group
            {

                Toast.makeText(getContext(),"Join Successful",Toast.LENGTH_LONG).show();

                // passing the groupinput to the JoinGroupFragment
                Bundle bundle=new Bundle();
                bundle.putString("groupId", groupinput);

                JoinGroupFragment joinGroupFragment = new JoinGroupFragment();
                joinGroupFragment.setArguments(bundle);

                ((GroupActivity)getActivity()).replaceFragment(joinGroupFragment);
            }

            else {

                Toast.makeText(getContext(),"Invalid Group Id",Toast.LENGTH_LONG).show();
            }

        }

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
