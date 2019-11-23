package com.example.poker2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity implements AdminFragment.OnFragmentInteractionListener, UserFragment.OnFragmentInteractionListener, View.OnClickListener {

    private String currentUserId;
    private DatabaseReference database;
    private ArrayList<String> userList = new ArrayList<>();
    private FirebaseCallback callback;
    private int userType;
    private Button addGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        addGroup = findViewById(R.id.button_AddGroup);
        addGroup.setOnClickListener(this);

        readCurrentUserData(new FirebaseCallback() {  // calling the readCurrentUserData function and passing an instance of my FirebaseCallback interface as an argument
            @Override
            public void onCallback(User user) {
                userType = user.getUserType();
                if (userType == 1) {

                    loadFragment(new AdminFragment()); // if usertype == 1 (admin) than we load the appropriate fragment

                }

                else{

                    loadFragment(new UserFragment()); // creating an object of the UserFragment and adding it to the fragment container by calling loadfragment
                }

            }
        });

    }

    public void readCurrentUserData(final FirebaseCallback callback) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance().getReference("Users");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        User u = dataSnapshot.child(currentUserId).getValue(User.class); // megkeressuk azt a usert az adatbazisbol akinek az id-ja megegyezik az eppen lekert userId-javal (currentuser id-javal)
                        callback.onCallback(u); // adding our user to our firebase callback

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View v) {

    }

    private interface FirebaseCallback{  // ez szol a tobbi resznek h megvan a user es => csak akkor nezi meg a typet ha megvan hogy ki a bejelntkezett user (a user nullobjectkent vetelenek elkerulese)
        void onCallback(User user);   // custom callback that waits for the data
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null); //By calling addToBackStack(), the replace transaction is saved to the back stack so the user can reverse the transaction and bring back the previous fragment by pressing the Back button.
        fragmentTransaction.commit();
    }

}
