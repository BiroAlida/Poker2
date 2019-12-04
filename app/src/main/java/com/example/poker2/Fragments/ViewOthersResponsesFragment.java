package com.example.poker2.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poker2.Activities.GroupActivity;
import com.example.poker2.Adapters.ViewOthersResponsesAdapter;
import com.example.poker2.Classes.Question;
import com.example.poker2.Classes.Response;
import com.example.poker2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ViewOthersResponsesFragment extends Fragment {

    private View view;
    private TextView tv_question;
    private String questionId, groupId;
    private DatabaseReference reference;
    private RecyclerView rw;
    private ArrayList<Response> list = new ArrayList<>();
    private ViewOthersResponsesAdapter adapter;
    private DatabaseReference responsesRef = FirebaseDatabase.getInstance().getReference("responses");
    private DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference("questions");
    private RecyclerView.LayoutManager layoutManager;


    public ViewOthersResponsesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_view_others_responses, container, false);

        questionId =  getArguments().getString("questionId");
        groupId = getArguments().getString("groupId");
        tv_question = view.findViewById(R.id.questionText);


        readResponses(new FirebaseCallback() {

            @Override
            public void onCallbackQuestionId(String currentQuestionId) {
                Log.e("eri",currentQuestionId);
                questionRef.child(currentQuestionId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Question question = dataSnapshot.getValue(Question.class);
                        tv_question.setText("Question: " + question.getQuestion());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCallback(ArrayList<Response> list) {

                rw = view.findViewById(R.id.recview);
                layoutManager = new LinearLayoutManager(getContext());
                rw.setLayoutManager(layoutManager);
                rw.addItemDecoration(new DividerItemDecoration(rw.getContext(), DividerItemDecoration.VERTICAL));
                adapter = new ViewOthersResponsesAdapter(list, getContext());
                rw.setAdapter(adapter);

            }

        });


        return view;
    }


    public void readResponses(final FirebaseCallback callback) // reads the responses of each user out of the responses node
    {
        responsesRef.child(groupId).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot questions : dataSnapshot.getChildren()){

                    for (DataSnapshot users : questions.getChildren()) {

                        String currentQuestionId = questions.getKey();
                        String userName = users.getKey();
                        Integer response = users.getValue(Integer.class);
                        list.add(new Response(userName,response));
                        callback.onCallbackQuestionId(currentQuestionId);

                    }

                   callback.onCallback(list);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface FirebaseCallback{
        void onCallbackQuestionId(String currentQuestionId);
        void onCallback(ArrayList<Response> responseList);


    }

}




