package com.example.todolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;
    private OnTaskLongClickListener longClickListener;

    public interface OnTaskLongClickListener {
        void onTaskLongClick(Task task);
    }

    public TaskAdapter(Context context, List<Task> taskList, OnTaskLongClickListener listener) {
        this.context = context;
        this.taskList = taskList;
        this.longClickListener = listener;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;

        public TaskViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.taskTitle);
        }
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.titleText.setText(task.getTitle());
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onTaskLongClick(task);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
