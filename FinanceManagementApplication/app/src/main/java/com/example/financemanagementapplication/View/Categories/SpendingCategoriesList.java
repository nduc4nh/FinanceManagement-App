package com.example.financemanagementapplication.View.Categories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.View.Categories.categoryAdapter.CategoryAdapter;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpendingCategoriesList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpendingCategoriesList extends Fragment implements commonOperator{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean edit;
    private CategoryAdapter adapter;
    private SpendingCategoryVM spendingCateVM;
    private   GridView grid;
    public SpendingCategoriesList() {
        // Required empty public constructor
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public SpendingCategoriesList(boolean edit) {
        this.edit = edit;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpendingCategoriesList.
     */
    // TODO: Rename and change types and number of parameters
    public static SpendingCategoriesList newInstance(String param1, String param2) {
        SpendingCategoriesList fragment = new SpendingCategoriesList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        spendingCateVM = new ViewModelProvider(requireActivity()).get(SpendingCategoryVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories_list, container, false);
        grid = view.findViewById(R.id.cate_list_ele);

        spendingCateVM.getSpendingCategories().observe(getViewLifecycleOwner(), spendingCategories -> {
            adapter = new CategoryAdapter(getContext(), R.layout.ele_list_fragment,spendingCategories,edit,spendingCateVM,getViewLifecycleOwner());
            grid.setAdapter(adapter);
        });
        grid.setScrollingCacheEnabled(true);
        Log.d("CREATE VIEW","ww");
        return view;
    }

    @Override
    public CategoryAdapter getAdapter() {
        return adapter;
    }

    public void update(boolean editMode)
    {
        spendingCateVM.getSpendingCategories().observe(getViewLifecycleOwner(), spendingCategories -> {
            adapter = new CategoryAdapter(getContext(), R.layout.ele_list_fragment,spendingCategories,edit,spendingCateVM,getViewLifecycleOwner());
            adapter.setEdit(editMode);
            grid.setAdapter(adapter);
        });
    }
    public void setSwitchLeft(boolean switchLeft)
    {
        this.adapter.setSwitchLeft(switchLeft);
    }
}