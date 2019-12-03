package com.example.poker2.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.poker2.Activities.GroupActivity;
import com.example.poker2.Adapters.GroupsAdapter;
import com.example.poker2.Adapters.ViewGroupQuestionsAdapter;
import com.example.poker2.Classes.Group;
import com.example.poker2.Classes.Question;
import com.example.poker2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.util.Objects.isNull;


public class ViewGroupQuestionsFragment extends Fragment implements ViewGroupQuestionsAdapter.OnGroupQuestionsListener {

    private View view;
    private Button addGroup;
    private DatabaseReference reference;
    private RecyclerView rw;
    private ArrayList<String> questionList = new ArrayList<>();
    private ArrayList<Question> list = new ArrayList<>();
    private ViewGroupQuestionsAdapter adapter;
    private String groupId;
    boolean active = true, inactive = false;
    private DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference("questions");
    private RecyclerView.LayoutManager layoutManager;
    private Question questionObject;
    private ArrayList<String> listQuestion = new ArrayList<>();


    public ViewGroupQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_group_questions, container, false);

        groupId = getArguments().getString("groupId");

        listingGroupsInRecyclerView(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Question> list) {

            }
        });


        return view;
    }

    public void listingGroupsInRecyclerView(final FirebaseCallback callback)
    {
        rw = view.findViewById(R.id.groupQuestions_recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        rw.setLayoutManager(layoutManager);
        adapter = new ViewGroupQuestionsAdapter(list, this); // passing the onGroupListener interface to the constructor of the GroupsAdapter
        rw.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("questions").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) { // searching for the question that is active and its groupid is the one given by the user


                    if(postSnapshot.child("groupId").getValue().equals(groupId)) // checking if the current question has the given groupid and if its active
                    {
                        Question question = postSnapshot.getValue(Question.class); // putting the searched question object into the questionObject variable
                        list.add(question);
                        adapter.notifyDataSetChanged();

                    }
                }

                callback.onCallback(list);

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.e("TAG", "Failed to read value.", error.toException());
            }
        });

    }

    public void readQuestionData(final FirebaseCallbackQuestions callback)
    {
        FirebaseDatabase.getInstance().getReference("questions").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    if(postSnapshot.child("groupId").getValue().equals(groupId) && postSnapshot.child("isActive").getValue().equals(String.valueOf(active))) // checking if the current question has the given groupid and if its active
                    {
                        //questionObject = null;
                        questionObject = postSnapshot.getValue(Question.class);
                        listQuestion.add(questionObject.getQuestion());

                    }

                }
                Log.e("EREDMENY2",listQuestion.toString());
                callback.onCallback(questionObject);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void showDialog(final Question question) {
        CharSequence[] items;

        items = new String[]{"Activate question", "Dezactivate question"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(question.getQuestion())
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                readQuestionData(new FirebaseCallbackQuestions() {

                                    @Override
                                    public void onCallback(Question questionObject) {
                                        if (questionObject != null) // there is already an active question
                                        {
                                            Log.e("EREDMENY3",questionObject.getQuestion().toString());
                                            Toast.makeText(getContext(), "There is already an active question, only one question can be active at a time", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getContext(), "Activated question successfully", Toast.LENGTH_SHORT).show();
                                            String questionId = question.getQuestionId();
                                            questionRef.child(questionId).child("isActive").setValue(String.valueOf(active));

                                        }
                                    }
                                });

                                break;
                            case 1:

                                Toast.makeText(getContext(), "Dezactivated question successfully", Toast.LENGTH_SHORT).show();
                                String questionId2 = question.getQuestionId();
                                questionRef.child(questionId2).child("isActive").setValue(String.valueOf(inactive));

                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onGroupQuestionClick(int position) {
        showDialog(list.get(position));
    }

    private interface FirebaseCallback{
        void onCallback(ArrayList<Question> list );
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private interface FirebaseCallbackQuestions{
        void onCallback(Question questionObject );
    }
}
