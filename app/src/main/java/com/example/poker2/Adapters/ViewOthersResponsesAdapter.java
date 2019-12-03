package com.example.poker2.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poker2.Classes.Response;
import com.example.poker2.R;

import java.util.ArrayList;

public class ViewOthersResponsesAdapter extends RecyclerView.Adapter<ViewOthersResponsesAdapter.ViewOthersResponsesViewHolder> {

    private ArrayList<Response> questionResponses;
    private Context context;

    public ViewOthersResponsesAdapter(ArrayList<Response> questionResponses, Context context) {
        this.questionResponses = questionResponses;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewOthersResponsesAdapter.ViewOthersResponsesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return new ViewOthersResponsesViewHolder(LayoutInflater.from(context).inflate(R.layout.view_responses,parent,false));
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.view_responses, parent,false);
        ViewOthersResponsesAdapter.ViewOthersResponsesViewHolder viewHolder = new ViewOthersResponsesAdapter.ViewOthersResponsesViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOthersResponsesAdapter.ViewOthersResponsesViewHolder holder, int position) {
            holder.name.setText(questionResponses.get(position).getName());
            holder.chosenNumber.setText(String.valueOf(questionResponses.get(position).getAnswer()));

    }

    @Override
    public int getItemCount() {
        return questionResponses.size();
    }

    public class ViewOthersResponsesViewHolder extends RecyclerView.ViewHolder{

        TextView name, chosenNumber, question;

        public ViewOthersResponsesViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.textViewTitle);
            chosenNumber = (TextView) itemView.findViewById(R.id.textViewNumber);
            question = (TextView) itemView.findViewById(R.id.textViewQuestion);
        }
    }

}