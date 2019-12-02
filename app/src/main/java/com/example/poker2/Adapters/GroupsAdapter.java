package com.example.poker2.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poker2.Classes.Group;
import com.example.poker2.R;


import java.util.ArrayList;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder> {

    private ArrayList<Group> groups;
    private OnGroupListener onGroupListener;

    public GroupsAdapter(ArrayList<Group> groups, OnGroupListener onGroupListener) {
        this.groups = groups;
        this.onGroupListener = onGroupListener;
    }

    @NonNull
    @Override
    public GroupsAdapter.GroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.view_groups, parent,false);
        GroupsViewHolder viewHolder = new GroupsViewHolder(listItem, onGroupListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsAdapter.GroupsViewHolder holder, int position) {

        holder.groupName.setText(groups.get(position).getGroupName());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class GroupsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView groupName;
        OnGroupListener onGroupListener;

        public GroupsViewHolder(@NonNull View itemView, OnGroupListener onGroupListener) {
            super(itemView);
            groupName = (TextView) itemView.findViewById(R.id.textView_groupName);
            this.onGroupListener = onGroupListener;

            itemView.setOnClickListener(this); // attaching the onClickListener to the itemView of the entire ViewHolder
    }

        @Override
        public void onClick(View v) {

            onGroupListener.onGroupClick(getAdapterPosition());
        }
    }

    public interface OnGroupListener{ // detects the click
        void onGroupClick(int position); // sends the position of the clicked item
    }
}
