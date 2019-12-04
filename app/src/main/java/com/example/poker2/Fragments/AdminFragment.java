package com.example.poker2.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.poker2.Activities.GroupActivity;
import com.example.poker2.Adapters.GroupsAdapter;
import com.example.poker2.Classes.Group;
import com.example.poker2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class AdminFragment extends Fragment implements View.OnClickListener, GroupsAdapter.OnGroupListener{

    private View view;
    private Button addGroup;
    private DatabaseReference reference;
    private RecyclerView rw;
    private ArrayList<Group> list = new ArrayList<>();
    private GroupsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_admin, container, false);


        addGroup = view.findViewById(R.id.button_AddGroup);
        addGroup.setOnClickListener(this);

        listingGroupsInRecyclerView();

        return view;
    }

    public void listingGroupsInRecyclerView()
    {

        rw = view.findViewById(R.id.groupList_recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        rw.setLayoutManager(layoutManager);
        rw.addItemDecoration(new DividerItemDecoration(rw.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new GroupsAdapter(list, this); // passing the onGroupListener interface to the constructor of the GroupsAdapter
        rw.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("groups");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Group group = dataSnapshot1.getValue(Group.class);
                    list.add(group);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showDialog(final Group group) {
        CharSequence[] items;

        items = new String[]{"View Responses", "Activate questions"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(group.getGroupName())
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:

                                Bundle bundle = new Bundle();
                                bundle.putString("groupId", group.getGroupId());
                                ViewOthersResponsesFragment viewOthersResponsesFragment = new ViewOthersResponsesFragment();
                                viewOthersResponsesFragment.setArguments(bundle);
                                ((GroupActivity) getActivity()).replaceFragment(viewOthersResponsesFragment);
                                break;

                            case 1:
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("groupId", group.getGroupId());
                                ViewGroupQuestionsFragment viewGroupQuestionsFragment = new ViewGroupQuestionsFragment();
                                viewGroupQuestionsFragment.setArguments(bundle2);
                                ((GroupActivity) getActivity()).replaceFragment(viewGroupQuestionsFragment);
                                break;
                        }

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        ((GroupActivity)getActivity()).replaceFragment(new AddGroupFragment());
    }

    @Override
    public void onGroupClick(int position) {

        showDialog(list.get(position));
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
