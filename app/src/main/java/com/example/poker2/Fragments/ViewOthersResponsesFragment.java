package com.example.poker2.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.poker2.Adapters.ViewOthersResponsesAdapter;
import com.example.poker2.Classes.Question;
import com.example.poker2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class ViewOthersResponsesFragment extends Fragment {

    private View view;
    private String questionId;
    private DatabaseReference reference;
    private RecyclerView rw;
    //private ArrayList<> list;
    private ViewOthersResponsesAdapter adapter;
    private FirebaseAuth mAuth;

    public ViewOthersResponsesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_others_responses, container, false);

        questionId =  getArguments().getString("questionId");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        rw = view.findViewById(R.id.recview);
        rw.setLayoutManager(new LinearLayoutManager(getContext()));
       // list = new ArrayList<Question>();

        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
