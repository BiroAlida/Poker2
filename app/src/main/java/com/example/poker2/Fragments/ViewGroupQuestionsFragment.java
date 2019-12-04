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
import androidx.recyclerview.widget.DividerItemDecoration;
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
    private RecyclerView rw;
    private ArrayList<String> questionList = new ArrayList<>();
    private ArrayList<Question> list = new ArrayList<>();
    private ViewGroupQuestionsAdapter adapter;
    private String groupId;
    boolean active = true, inactive = false;
    private DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference("questions");
    private RecyclerView.LayoutManager layoutManager;


    public ViewGroupQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_group_questions, container, false);

        groupId = getArguments().getString("groupId");


      listingGroupsInRecyclerView();


        return view;
    }

    public void listingGroupsInRecyclerView()
    {
        rw = view.findViewById(R.id.groupQuestions_recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        rw.setLayoutManager(layoutManager);
        rw.addItemDecoration(new DividerItemDecoration(rw.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new ViewGroupQuestionsAdapter(list, this); // passing the onGroupListener interface to the constructor of the GroupsAdapter
        rw.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("questions").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    if(postSnapshot.child("groupId").getValue().equals(groupId))
                    {
                        Question question = postSnapshot.getValue(Question.class);
                        list.add(question);
                        adapter.notifyDataSetChanged();

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    // shows a dialogbox to activate a question
    public void showDialog(final Question question) {
        CharSequence[] items;

        items = new String[]{"Activate question"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(question.getQuestion())
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase.getInstance().getReference("questions").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                            if(postSnapshot.child("questionId").getValue(String.class).equals(question.getQuestionId())) // checking if the current question has the given groupid and if its active
                                            {

                                                questionRef.child(postSnapshot.getKey()).child("isActive").setValue(active);
                                                Toast.makeText(getContext(), getString(R.string.successfullActivation), Toast.LENGTH_SHORT).show();

                                            }
                                            else{
                                                questionRef.child(postSnapshot.getKey()).child("isActive").setValue(inactive);
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                        }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onGroupQuestionClick(int position) {
        showDialog(list.get(position));
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
