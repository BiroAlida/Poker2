package com.example.poker2.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GroupListFragment extends Fragment{

   /* private String currentUserId;
    private DatabaseReference database;
    private ArrayList<String> userList = new ArrayList<>();
    private GroupActivity.FirebaseCallback callback;
    private int userType;
    private View view;

    public GroupListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_group_list, container, false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        readCurrentUserData(user -> {
            userType = user.getUserType();

            if (userType == 1) {
                addSession.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    public void readCurrentUserData(final GroupActivity.FirebaseCallback callback) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance().getReference("Users");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User u = dataSnapshot.child(currentUserId).getValue(User.class);
                callback.onCallback(u);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private interface FirebaseCallback{  // ez szol a tobbi resznek h megvan a user es => csak akkor nezi meg a typet h a megvan h ki a bejelntkezett user (a user nullobjectkent vetelenek elkerulese)
        void onCallback(User user);
    }*/


}
