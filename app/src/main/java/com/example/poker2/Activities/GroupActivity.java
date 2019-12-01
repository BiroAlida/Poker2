package com.example.poker2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;

import com.example.poker2.Fragments.AddGroupFragment;
import com.example.poker2.Fragments.AdminFragment;
import com.example.poker2.Fragments.JoinGroupFragment;
import com.example.poker2.Fragments.UserFragment;
import com.example.poker2.Classes.Group;
import com.example.poker2.Fragments.ViewOthersResponsesFragment;
import com.example.poker2.R;
import com.example.poker2.Classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class GroupActivity extends AppCompatActivity implements AdminFragment.OnFragmentInteractionListener, UserFragment.OnFragmentInteractionListener, AddGroupFragment.OnFragmentInteractionListener, JoinGroupFragment.OnFragmentInteractionListener, ViewOthersResponsesFragment.OnFragmentInteractionListener {

    private String currentUserId;
    private DatabaseReference database;
    private ArrayList<String> userList = new ArrayList<>();
    private FirebaseCallback callback;
    private int userType;

    private RecyclerView rw;
    private ArrayList<Group> list;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


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


    public interface FirebaseCallback{  // ez szol a tobbi resznek h megvan a user es => csak akkor nezi meg a typet ha megvan hogy ki a bejelntkezett user (a user nullobjectkent vetelenek elkerulese)
        void onCallback(User user);   // custom callback that waits for the data
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null); //By calling addToBackStack(), the replace transaction is saved to the back stack so the user can reverse the transaction and bring back the previous fragment by pressing the Back button.
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
