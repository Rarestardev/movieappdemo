package com.rarestar.empirebesttv.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rarestar.empirebesttv.Movie_model.category_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.adapter.Category_Adapter;

import java.util.ArrayList;
import java.util.List;

public class Category_fragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    List<category_model> ModelCategory;
    Category_Adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView= v.findViewById(R.id.recyclerView);
        addListCategory();
        showData();
        return v;
    }
    @SuppressLint("NotifyDataSetChanged")
    private void addListCategory(){
        if (ModelCategory == null){
            ModelCategory = new ArrayList<>();
        }else {
            ModelCategory.clear();
        }
        ModelCategory.add(new category_model("اکشن"));
        ModelCategory.add(new category_model("آهنگ"));
        ModelCategory.add(new category_model("انیمیشن"));
        ModelCategory.add(new category_model("ایرانی"));
        ModelCategory.add(new category_model("بیوگرافی"));
        ModelCategory.add(new category_model("تاریخی"));
        ModelCategory.add(new category_model("تخیلی"));
        ModelCategory.add(new category_model("ترسناک"));
        ModelCategory.add(new category_model("ترکیه ای"));
        ModelCategory.add(new category_model("جنگی"));
        ModelCategory.add(new category_model("جنایی"));
        ModelCategory.add(new category_model("خانوادگی"));
        ModelCategory.add(new category_model("درام"));
        ModelCategory.add(new category_model("رمز آلود"));
        ModelCategory.add(new category_model("رمانتیک"));
        ModelCategory.add(new category_model("رزمی"));
        ModelCategory.add(new category_model("ژاپنی"));
        ModelCategory.add(new category_model("فانتزی"));
        ModelCategory.add(new category_model("کمدی"));
        ModelCategory.add(new category_model("کره ای"));
        ModelCategory.add(new category_model("کوتاه"));
        ModelCategory.add(new category_model("هیجان انگیز"));
        ModelCategory.add(new category_model("هندی"));
        ModelCategory.add(new category_model("مستند"));
        ModelCategory.add(new category_model("موزیکال"));
        ModelCategory.add(new category_model("وسترن"));
    }
    private void showData(){
        adapter = new Category_Adapter(ModelCategory,getContext());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}