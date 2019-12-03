package com.example.poker2.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poker2.Activities.GroupActivity;
import com.example.poker2.Classes.Question;
import com.example.poker2.Classes.User;
import com.example.poker2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class JoinGroupFragment extends Fragment implements View.OnClickListener {

    private View view;
    private String groupId;
    private TextView tv;
    private  int response;
    private Question questionObject;
    private Button btnSend, btn1,btn2,btn3,btn4,btn5, btnEvaluate,buttonGoBack;
    private String username;
    private boolean active = true;
    private int chosenButton = 0;
    private DatabaseReference responsesReference;
    private String currentQuestionId;


    public JoinGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_join_group, container, false);

        buttonGoBack = view.findViewById(R.id.button_goBack);
        buttonGoBack.setVisibility(View.INVISIBLE);

        tv = view.findViewById(R.id.questionText);
        btn1 = view.findViewById(R.id.button_1);
        btn2 = view.findViewById(R.id.button_2);
        btn3 = view.findViewById(R.id.button_3);
        btn4 = view.findViewById(R.id.button_4);
        btn5 = view.findViewById(R.id.button_5);
        btnSend = view.findViewById(R.id.button_send);
        btnEvaluate = view.findViewById(R.id.button_evaluation);

        groupId =  getArguments().getString("groupId");

        ((GroupActivity)getActivity()).readCurrentUserData(new GroupActivity.FirebaseCallback() {
            @Override
            public void onCallback(User user) {

                username = user.getUserName();
            }
        });


        readData(new FirebaseCallback() {
            @Override
            public void onCallback(Question questionObject) {

                if(questionObject != null)
                {
                    String text = questionObject.getQuestion();
                    tv.setText(text);
                }
                else{

                    tv.setText("This group does not have any active questions!");
                    btn1.setVisibility(View.INVISIBLE);
                    btn2.setVisibility(View.INVISIBLE);
                    btn3.setVisibility(View.INVISIBLE);
                    btn4.setVisibility(View.INVISIBLE);
                    btn5.setVisibility(View.INVISIBLE);
                    btnSend.setVisibility(View.INVISIBLE);
                    btnEvaluate.setVisibility(View.INVISIBLE);

                    buttonGoBack.setVisibility(View.VISIBLE);
                    buttonGoBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.popBackStack();

                        }
                    });
                }

            }

        });

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chosenButton == 0)
                {
                    Toast.makeText(getContext(), "You must chose a button!", Toast.LENGTH_SHORT).show();
                }

                else{

                    responsesReference = FirebaseDatabase.getInstance().getReference("responses");
                    responsesReference.child(groupId).child(questionObject.getQuestionId()).child(username).setValue(response);
                    Toast.makeText(getContext(), "Your answer was saved successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentQuestionId = questionObject.getQuestionId();

                // passing the id of the question to which we want to see the responses to the ViewOthersResponses fragment
                Bundle bundle=new Bundle();
                bundle.putString("questionId", currentQuestionId);
                bundle.putString("groupId", groupId);
                ViewOthersResponsesFragment viewOthersResponsesFragment = new ViewOthersResponsesFragment();
                viewOthersResponsesFragment.setArguments(bundle);
                ((GroupActivity)getActivity()).replaceFragment(viewOthersResponsesFragment);

            }
        });


        return view;
    }

    public void readData (final FirebaseCallback callback) {

        FirebaseDatabase.getInstance().getReference("questions").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) { // searching for the question that is active and its groupid is the one given by the user


                    if(postSnapshot.child("groupId").getValue().equals(groupId) && postSnapshot.child("isActive").getValue().equals(String.valueOf(active))) // checking if the current question has the given groupid and if its active
                    {
                        questionObject = postSnapshot.getValue(Question.class); // putting the searched question object into the questionObject variable

                    }

                }
                callback.onCallback(questionObject);

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.e("TAG", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.button_1:
                response = 1;
                chosenButton = 1;
                break;

            case R.id.button_2:
                response = 2;
                chosenButton = 1;
                break;

            case R.id.button_3:
                response = 3;
                chosenButton = 1;
                break;

            case R.id.button_4:
                response = 4;
                chosenButton = 1;
                break;

            case R.id.button_5:
                response = 5;
                chosenButton = 1;
                break;
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private interface FirebaseCallback{
        void onCallback(Question questionObject );
    }

}
