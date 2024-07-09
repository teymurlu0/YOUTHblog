package com.pyjma.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.Tutucu>{
    private Context context;
    private List<Model> list;

    public NewAdapter(Context context, List<Model> list) {
        this.context = context;
        this.list = list;

        Log.e("mehmet","Array Uzunluğu " + list.size() );
    }

    @NonNull
    @Override
    public Tutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new Tutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tutucu holder, int position) {
        Model model = list.get(position);
        Log.e("mehmet",list.get(0).getAuthor());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Tutucu extends RecyclerView.ViewHolder{
        //ImageView img;
        //TextView date, title, share_count, author;
        public Tutucu(@NonNull View itemView) {
            super(itemView);
            //img = itemView.findViewById(R.id.imageView3);
           /* date = itemView.findViewById(R.id.t_date);
            title = itemView.findViewById(R.id.textView9);
            share_count = itemView.findViewById(R.id.textView10);
            author = itemView.findViewById(R.id.textView8);*/
        }
    }
}
