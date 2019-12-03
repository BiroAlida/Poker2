package com.example.poker2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poker2.Classes.Group;
import com.example.poker2.Classes.Question;
import com.example.poker2.R;

import java.util.ArrayList;

public class ViewGroupQuestionsAdapter extends RecyclerView.Adapter<ViewGroupQuestionsAdapter.QuestionsViewHolder>  {

    private ArrayList<Question> questions;
    private OnGroupQuestionsListener onGroupQuestionsListener;

    public ViewGroupQuestionsAdapter(ArrayList<Question> questions, OnGroupQuestionsListener onGroupQuestionsListener) {
        this.questions = questions;
        this.onGroupQuestionsListener = onGroupQuestionsListener;
    }

    @NonNull
    @Override
    public ViewGroupQuestionsAdapter.QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.view_question, parent,false);
        QuestionsViewHolder viewHolder = new QuestionsViewHolder(listItem, onGroupQuestionsListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewGroupQuestionsAdapter.QuestionsViewHolder holder, int position) {

        holder.question.setText(questions.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return questions.size();
        //return questions == null ? 0 : questions.size();
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView question;
        OnGroupQuestionsListener onGroupQuestionsListener;

        public QuestionsViewHolder(@NonNull View itemView, OnGroupQuestionsListener onGroupQuestionsListener) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.textView_question);
            this.onGroupQuestionsListener = onGroupQuestionsListener;

            itemView.setOnClickListener(this); // attaching the onClickListener to the itemView of the entire ViewHolder
        }

        @Override
        public void onClick(View v) {

            onGroupQuestionsListener.onGroupQuestionClick(getAdapterPosition());
        }
    }

    public interface OnGroupQuestionsListener{ // detects the click
        void onGroupQuestionClick(int position); // sends the position of the clicked item
    }

    }

