package com.gaia.app.smartwarehouse;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;

import com.gaia.app.smartwarehouse.adapters.SearchAdapter;
import com.gaia.app.smartwarehouse.classes.ProductsData;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class SearchActivity extends AppCompatActivity {
    private List<String> searchList=new ArrayList<>();
    private ArrayList<String> suggestionList;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private LinearLayoutManager layoutManager;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21)
        {
            TransitionInflater transitionInflater=TransitionInflater.from(this);
            Transition transition=transitionInflater.inflateTransition(R.transition.transition_slide_right);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
        setContentView(R.layout.activity_search);
        ProductsData details = new ProductsData(this);
        Cursor cursor = details.getcategorydata();
        if (cursor.moveToFirst()) {

            do {
                String cname = cursor.getString(0);

                searchList.add(cname);
                Cursor cursor2 = details.getitemsdata(cname);
                if (cursor2.moveToFirst()) {
                    do {
                        String iname;
                        iname = cursor2.getString(0);

                        searchList.add(iname);
                    } while (cursor2.moveToNext());

                }
            } while (cursor.moveToNext());

        }
        editText= (EditText) findViewById(R.id.et_search);
        recyclerView = (RecyclerView) findViewById(R.id.rv_search);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SearchAdapter(this, (ArrayList<String>) searchList);
        ScaleInAnimationAdapter AnimationAdapter=new ScaleInAnimationAdapter(adapter);
        AnimationAdapter.setDuration(2000);
        AnimationAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(AnimationAdapter);
        searchsuggestions();


    }

    private void searchsuggestions() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

               String str=s.toString().trim().toLowerCase();
                List<String> filteredList = new ArrayList<>();
                if(s.length()>0)
                {
                    for(String string : searchList)
                    {
                        if (string.toLowerCase().contains(str))
                        {
                            filteredList.add(string);
                        }
                    }

                }
                else
                {
                    filteredList=searchList;
                }
                layoutManager = new LinearLayoutManager(SearchActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new SearchAdapter(SearchActivity.this, (ArrayList<String>) filteredList);
                ScaleInAnimationAdapter AnimationAdapter=new ScaleInAnimationAdapter(adapter);
                AnimationAdapter.setDuration(2000);
                AnimationAdapter.setInterpolator(new OvershootInterpolator());
                recyclerView.setAdapter(AnimationAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
