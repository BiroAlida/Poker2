package com.example.poker2.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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


public class ViewGroupQuestionsFragment extends Fragment implements ViewGroupQuestionsAdapter.OnGroupQuestionsListener {

    private View view;
    private Button addGroup;
    private DatabaseReference reference;
    private RecyclerView rw;
    private Question questionObject;
    private ArrayList<String> questionList = new ArrayList<>();
    private ArrayList<Question> list = new ArrayList<>();
    private ViewGroupQuestionsAdapter adapter;
    private String groupId;
    private RecyclerView.LayoutManager layoutManager;


    public ViewGroupQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_group_questions, container, false);

        groupId = getArguments().getString("groupId");

        /*listingGroupsInRecyclerView(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Question> list) {
                Log.e("hjdhflgdf", list.toString());
            }
        });*/
        listingGroupsInRecyclerView();

        return view;
    }

    public void listingGroupsInRecyclerView()//final FirebaseCallback callback)
    {
        rw = view.findViewById(R.id.groupQuestions_recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        rw.setLayoutManager(layoutManager);
        adapter = new ViewGroupQuestionsAdapter(list, this); // passing the onGroupListener interface to the constructor of the GroupsAdapter
        rw.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("questions").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) { // searching for the question that is active and its groupid is the one given by the user


                    if(postSnapshot.child("groupId").getValue().equals(groupId)) // checking if the current question has the given groupid and if its active
                    {
                        Question question = postSnapshot.getValue(Question.class); // putting the searched question object into the questionObject variable
                        list.add(question);
                        adapter.notifyDataSetChanged();

                    }
                }

                //callback.onCallback(list);

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.e("TAG", "Failed to read value.", error.toException());
            }
        });

       /* reference = FirebaseDatabase.getInstance().getReference("questions");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Question question = dataSnapshot1.getValue(Question.class);
                    list.add(question);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    public void showDialog(final Question question) {
        CharSequence[] items;

        items = new String[]{"Activate question"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(question.getQuestion())
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       Toast.makeText(getContext(), "activated question successfully", Toast.LENGTH_SHORT).show();

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
}
