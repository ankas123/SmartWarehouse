package layout;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaia.app.smartwarehouse.R;
import com.gaia.app.smartwarehouse.adapters.FragmentDataAdapter;
import com.gaia.app.smartwarehouse.adapters.RecyclerRowAdapter;
import com.gaia.app.smartwarehouse.classes.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private FragmentDataAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<String> categoryList = new ArrayList<String>();

    public CategoryFragment() {
        // Required empty public constructor

    }
    @SuppressLint("ValidFragment")
    public CategoryFragment(List<String> data)
    {
        categoryList=data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_category,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_category);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FragmentDataAdapter(this, (ArrayList<String>) categoryList);
        recyclerView.setAdapter(adapter);

        return view;

    }

}
