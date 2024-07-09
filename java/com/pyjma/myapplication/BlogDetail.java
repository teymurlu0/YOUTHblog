package com.pyjma.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.pyjma.myapplication.comments.CommentAdapter;
import com.pyjma.myapplication.comments.CommentModel;
import com.pyjma.myapplication.databinding.ActivityBlogDetailBinding;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.UnityAds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlogDetail extends AppCompatActivity {
    ActivityBlogDetailBinding binding;
    String id;
    String title, desc;
    int count;
    int n_count;
    String GameID = "5573243";
    String ADID = "Interstitial_Android";
    Boolean TestMode = true;
    ImageView imageView;
    private FirebaseFirestore db;
    private ArrayList<CommentModel> commentList = new ArrayList<>();
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getStringExtra("id");
        db = FirebaseFirestore.getInstance();
        showdata(id);
        setAdapter();
        showComments(id);
        UnityAds.initialize(getApplicationContext(), GameID, TestMode, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {

            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
            }
        });
        setListener();
    }

    private void setAdapter() {

        /*list.add(new CommentModel(1, id, "Hi test comment", "", "Anonymous", ""));
        list.add(new CommentModel(2, id, "Hi test comment", "", "Anonymous", ""));
        list.add(new CommentModel(3, id, "Hi test comment", "", "Anonymous", ""));
        list.add(new CommentModel(4, id, "Hi test comment", "", "Anonymous", ""));
        list.add(new CommentModel(5, id, "Hi test comment", "", "Anonymous", ""));
        list.add(new CommentModel(6, id, "Hi test comment", "", "Anonymous", ""));
        list.add(new CommentModel(7, id, "Hi test comment", "", "Anonymous", ""));
        list.add(new CommentModel(8, id, "Hi test comment", "", "Anonymous", ""));
        list.add(new CommentModel(9, id, "Hi test comment", "", "Anonymous", ""));
        list.add(new CommentModel(10, id, "Hi test comment", "", "Anonymous", ""));
        list.add(new CommentModel(11, id, "Hi test comment", "", "Anonymous", ""));*/
        adapter = new CommentAdapter(this, commentList);
        binding.rvComment.setAdapter(adapter);
    }

    private void setListener() {
        binding.sendButton.setOnClickListener(v -> {
            if (binding.etComment.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please write comment here", Toast.LENGTH_SHORT).show();
                return;
            }
            binding.sendButton.setVisibility(View.GONE);
            binding.progressCircular.setVisibility(View.VISIBLE);
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            String timestamp = String.valueOf(System.currentTimeMillis());
            CommentModel model = new CommentModel();
            model.setBlogId(id);
            model.setComment(binding.etComment.getText().toString());
            model.setName((account != null && account.getDisplayName() != null) ? account.getDisplayName() : "");
            model.setImage((account != null && account.getPhotoUrl() != null) ? account.getPhotoUrl().toString() : "");
            model.setTimeStamp(timestamp);
            addCommentData(model);
        });
    }

    private void showdata(String id) {
        try {

            Log.e("mehmet", "else " + id);
            db.collection("Blogs").get()
                    .addOnSuccessListener(queryDocumentSnapshot -> {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshot) {
                            String deger = document.getString("id");
                            Log.e("mehmet", "elsedeger " + deger);
                            if (id.equals(deger)) {
                                Log.e("mehmet", "if1");
                                Glide.with(getApplicationContext()).load(document.getString("img")).into(binding.imageView3);
                                Log.e("mehmet", "if2");
                                binding.textView4.setText(Html.fromHtml("<font color='B7B7B7'>By </font> <font color='#000000'>"
                                        + document.getString("author")));
                                Log.e("mehmet", "if3");
                                binding.textView5.setText(document.getString("tittle"));
                                Log.e("mehmet", "if4");
                                binding.textView6.setText(document.getString("desc"));
                                Log.e("mehmet", "if5");
                                title = document.getString("tittle");
                                Log.e("mehmet", "if6");
                                desc = document.getString("desc");
                                Log.e("mehmet", "if7");
                                int i_count = count;
                                Log.e("mehmet", "if8");
                                n_count = i_count + 1;
                            } else {
                                Log.e("mehmet", "else");
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e("mehmet", e.getMessage().toString());
        }
        try {
            binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String shareBody = desc;
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, title);
                    intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(intent, "Share Using"));

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("share_count", String.valueOf(n_count));
                    FirebaseFirestore.getInstance().collection("Blogs").document(id).update(map);
                }
            });
            binding.imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            Log.e("mehmet", e.getMessage().toString());
        }
    }

    private void addCommentData(CommentModel model) {
        db.collection("blog_Comments").add(model)
                .addOnSuccessListener(documentReference -> {
                    String commentId = documentReference.getId();
                    Log.e("COMMENT_ID", "addCommentData: " + commentId);
                    binding.etComment.setText("");
                    if (adapter != null) {
                        commentList.add(model);
                        adapter.notifyDataSetChanged();
                        binding.tvNoComments.setVisibility(View.GONE);
                    }
                    binding.sendButton.setVisibility(View.VISIBLE);
                    binding.progressCircular.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    binding.sendButton.setVisibility(View.VISIBLE);
                    binding.progressCircular.setVisibility(View.GONE);
                });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void showComments(String blogId) {
        db.collection("blog_Comments")
                .whereEqualTo("blogId", blogId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<CommentModel> commentsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        CommentModel comment = document.toObject(CommentModel.class);
                        commentsList.add(comment);
                    }
                    commentList.clear();
                    commentList.addAll(commentsList);
                    adapter.notifyDataSetChanged();
                    if (commentsList.size() == 0) {
                        binding.tvNoComments.setVisibility(View.VISIBLE);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });

    }
}