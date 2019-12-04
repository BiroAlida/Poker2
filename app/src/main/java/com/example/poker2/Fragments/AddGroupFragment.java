package com.example.poker2.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poker2.Classes.Group;
import com.example.poker2.Classes.Question;
import com.example.poker2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddGroupFragment extends Fragment implements ValueEventListener{

    private View view;
    private LinearLayout questionContainer;
    private Button addQuestion, addGroup;
    private DatabaseReference database, databaseQuestions;
    private EditText groupName;
    private ArrayList<String> questions = new ArrayList<>();
    private static int groupCount = 0;
    private String active = "t";
    private String inactive = "f";
    private boolean inactiveQuestion = false;


    public AddGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance().getReference("groups");
        databaseQuestions = FirebaseDatabase.getInstance().getReference("questions");;

        view = inflater.inflate(R.layout.fragment_add_group, container, false);
        questionContainer = view.findViewById(R.id.questionContainer);
        groupName = view.findViewById(R.id.editText_groupName);
        addQuestion = view.findViewById(R.id.button_addQuestion);
        addGroup = view.findViewById(R.id.button_addGroup);

        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameOfGroup = groupName.getText().toString();

                if (TextUtils.isEmpty(nameOfGroup) || questions.isEmpty()){
                    Toast.makeText(getActivity(),getString(R.string.emptyField),Toast.LENGTH_SHORT);

                } else {
                    Group group = new Group(String.valueOf(groupCount),nameOfGroup, questions); // setting groupCount variable as the id of the groups

                    database.child(group.getGroupId()).setValue(group);
                    getFragmentManager().popBackStack(); // goes back to AdminFragment to create a new group
                }
            }
        });

        Query query = database.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(this); // calling onDataChange

        return view;
    }


    public void showDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Add question");

        final Switch sw = new Switch(getContext());
        alertDialog.setView(sw);

        final EditText questionInput = new EditText(getContext()); // creating an EditText for the input question
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        questionInput.setLayoutParams(layoutParams);


        alertDialog.setView(questionInput);
        alertDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String question = questionInput.getText().toString();
                if(!TextUtils.isEmpty(question)) {
                    questions.add(question);

                    //adding every question to a new TextView
                    TextView questionView = new TextView(getContext());
                    questionView.setText(question);
                    questionContainer.addView(questionView);

                    // adding the questions to the Questions node in database
                    String id = databaseQuestions.push().getKey();
                    Question q = new Question(id,String.valueOf(groupCount),question,inactiveQuestion);
                    databaseQuestions.child(id).setValue(q);


                }
            }

        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alertDialog.show();
    }


    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        for (DataSnapshot ds: dataSnapshot.getChildren()) {
            groupCount = Integer.parseInt(ds.getKey());
            groupCount++;
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
