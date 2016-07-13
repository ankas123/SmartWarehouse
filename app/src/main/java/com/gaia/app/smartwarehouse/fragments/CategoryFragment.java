package com.gaia.app.smartwarehouse.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.adapters.CategoryTabAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryTabAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<String> categoryList = new ArrayList<String>();
    private List<String> suggestionList = new ArrayList<String>();



    public CategoryFragment() {
        // Required empty public constructor

    }
    @SuppressLint("ValidFragment")
    public CategoryFragment(List<String> data,String s)
    {
        categoryList=data;
        suggestionList.clear();
        if(s.length()>0)
        {
           for(String string : categoryList)
            {
                if (string.toLowerCase().contains(s.toLowerCase()))
                {
                    suggestionList.add(string);
                }
            }

        }
        else
        {
            suggestionList=categoryList;
        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_category,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_category);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryTabAdapter(this, (ArrayList<String>) suggestionList);
        ScaleInAnimationAdapter AnimationAdapter=new ScaleInAnimationAdapter(adapter);
        AnimationAdapter.setDuration(2000);
        AnimationAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(AnimationAdapter);

        return view;

    }


}
