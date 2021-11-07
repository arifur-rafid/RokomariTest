package com.adoresoft.rokomaritest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.adoresoft.rokomaritest.Database.DatabaseHelper;
import com.adoresoft.rokomaritest.EditTaskActivity;
import com.adoresoft.rokomaritest.R;
import com.adoresoft.rokomaritest.TaskDetails;
import com.adoresoft.rokomaritest.TaskDetailsActivity;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<TaskDetails> listItems;
    private Context context;
    private DatabaseHelper myDB;

    public TaskAdapter(List<TaskDetails> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_details_show_layout, parent, false);
        return new TaskAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        final TaskDetails taskDetails = listItems.get(position);
        holder.taskNameTv.setText(taskDetails.getName());
        holder.createdDateTv.setText(taskDetails.getCreatedDate());
        holder.deadlineTv.setText(taskDetails.getDeadline());
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TaskDetails taskDetails1 = listItems.get(position);
                Intent activity = new Intent(context, EditTaskActivity.class);
                activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.putExtra("myTaskObject", new Gson().toJson(taskDetails1));
                activity.putExtra("position", position);
                context.startActivity(activity);
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listItems.size() > 0) {
                    myDB = new DatabaseHelper(context);
                    boolean result = myDB.deleteSingleTask(taskDetails.getName());
                    if (result == true) {
                        if (listItems.size() > 0) {
                            Toast.makeText(context, "Task Deleted", Toast.LENGTH_LONG).show();
                            listItems.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    }
                }
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TaskDetails taskDetails1 = listItems.get(holder.getAdapterPosition());
                Intent activity = new Intent(context, TaskDetailsActivity.class);
                activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.putExtra("myTaskObject", new Gson().toJson(taskDetails1));
                activity.putExtra("position", holder.getAdapterPosition());
                context.startActivity(activity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView taskNameTv;
        public TextView createdDateTv;
        public TextView deadlineTv;
        public ImageButton editBtn;
        public ImageButton deleteBtn;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameTv = (TextView) itemView.findViewById(R.id.taskNameTv);
            createdDateTv = (TextView) itemView.findViewById(R.id.createdDateTv);
            deadlineTv = (TextView) itemView.findViewById(R.id.deadlineTv);
            editBtn = (ImageButton) itemView.findViewById(R.id.editBtn);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.deleteBtn);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
