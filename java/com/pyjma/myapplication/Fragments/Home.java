package com.pyjma.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.pyjma.myapplication.Adapter;
import com.pyjma.myapplication.Model;
import com.pyjma.myapplication.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment{

    private FragmentHomeBinding binding;
    private List<Model> list;
    private Adapter adapter;
    private Context context;

    private final FirebaseFirestore firestoreDatabase = FirebaseFirestore.getInstance();


    public Home(Context context) {
        this.context = context;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setUsearchView();
        setupRv();
        return binding.getRoot();
    }

    private void setUsearchView() {
        binding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        ArrayList<Model> filteredList = new ArrayList<>();
        for (Model item : list) {
            if (item.getTittle().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }

    }

    private void setupRv() {
        list = new ArrayList<>();
        firestoreDatabase.collection("Blogs").get().addOnSuccessListener(
                        queryDocumentSnapshots -> {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                String author = documentSnapshot.getString("author");
                                String date = documentSnapshot.getString("date");
                                String desc = documentSnapshot.getString("desc");
                                String img = documentSnapshot.getString("img");
                                String tittle = documentSnapshot.getString("tittle");
                                String id = documentSnapshot.getId();

                                Model model = new Model(tittle, desc, author, date, img, id);
                                list.add(model);
                            }

                            binding.rvBlogs.setHasFixedSize(true);
                            binding.rvBlogs.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                            adapter = new Adapter(context, list);
                            binding.rvBlogs.setAdapter(adapter);
                        })
                .addOnFailureListener(e -> Log.e("HomeFragment", "Error fetching data: " + e.getMessage()));
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager(); // Eğer FragmentActivity içindeyseniz
        //FragmentManager fragmentManager = requireActivity().getSupportFragmentManager(); // Eğer normal bir Activity içindeyseniz

        Fragment fragment = fragmentManager.findFragmentByTag("HomeFragment"); // "HomeFragment" yerine kendi fragmentinizin tag'ini kullanın
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }


}
