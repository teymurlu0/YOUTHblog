package com.pyjma.myapplication.comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.pyjma.myapplication.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.viewHolder> {
    private ArrayList<CommentModel> list;
    private Context context;

    public CommentAdapter(Context context, ArrayList<CommentModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        CommentModel model = list.get(position);
        holder.tv_name.setText(model.getName());
        holder.tv_comment.setText(model.getComment());
        Glide.with(context).load(model.getImage()).placeholder(R.drawable.baseline_account_circle_24).into(holder.iv_profile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_comment;
        private ShapeableImageView iv_profile;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_profile = itemView.findViewById(R.id.iv_profile);
        }
    }
}
