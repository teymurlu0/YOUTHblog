package com.pyjma.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class    Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private List<Model> list;

    public Adapter(Context context, List<Model> list) {
        this.list = new ArrayList<>(list);
        this.context = context;
        Log.e("mehmet", "Array Uzunluğu " + list.size());
    }

    public void filter_list(List<Model> filter_list) {
        list = filter_list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = list.get(position);
        holder.title.setText(model.getTittle());
        holder.date.setText(model.getDate());
        holder.share_count.setText(model.getShare_count());
        holder.author.setText(model.getAuthor());

        Glide.with(holder.author.getContext()).load(model.getImg()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context != null) {
                    Intent intent = new Intent(context, BlogDetail.class);
                    intent.putExtra("id", model.getId());
                    context.startActivity(intent);
                } else {
                    Log.e("mehmet", "context null");
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Burada silme veya güncelleme işlemini gerçekleştirin
                // Örneğin:
                // FirebaseFirestore.getInstance().collection("Blogs").document(model.getId()).delete();
                return true; // Uzun tıklama olayını tüketmek için true döndürün
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView date, title, share_count, author;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageView3);
            date = itemView.findViewById(R.id.t_date);
            title = itemView.findViewById(R.id.textView9);
            share_count = itemView.findViewById(R.id.textView10);
            author = itemView.findViewById(R.id.textView8);
        }
    }
}
