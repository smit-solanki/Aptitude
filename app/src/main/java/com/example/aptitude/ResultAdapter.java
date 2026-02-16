package com.example.aptitude;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private List<Question> questionList;

    public ResultAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question q = questionList.get(position);
        holder.tvQuestion.setText(q.getQuestionText());

        // Get option text based on number
        String userAnsText = getOptionText(q, q.getUserSelectedOption());
        String correctAnsText = getOptionText(q, q.getCorrectAnsNo());

        holder.tvUserAns.setText("Your Answer: " + userAnsText);
        holder.tvCorrectAns.setText("Correct Answer: " + correctAnsText);

        // Color Logic: Green if correct, Red if wrong
        if (q.getUserSelectedOption() == q.getCorrectAnsNo()) {
            holder.tvUserAns.setTextColor(Color.parseColor("#4CAF50")); // Green
        } else {
            holder.tvUserAns.setTextColor(Color.RED);
        }
    }

    private String getOptionText(Question q, int optionNo) {
        switch (optionNo) {
            case 1: return q.getOptA();
            case 2: return q.getOptB();
            case 3: return q.getOptC();
            case 4: return q.getOptD();
            default: return "Not Answered";
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvUserAns, tvCorrectAns;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvResQuestion);
            tvUserAns = itemView.findViewById(R.id.tvResUserAns);
            tvCorrectAns = itemView.findViewById(R.id.tvResCorrectAns);
        }
    }
}