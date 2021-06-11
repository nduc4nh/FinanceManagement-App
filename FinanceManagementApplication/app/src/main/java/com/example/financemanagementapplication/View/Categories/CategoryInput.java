package com.example.financemanagementapplication.View.Categories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.tool.NumberTextWatcher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryInput#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryInput extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String name;
    private String desc;
    private String limit;

    private EditText nameEt;
    private EditText descEt;
    private EditText limitEt;

    public View view;
    private boolean isSpending;
    private boolean switchLeft;
    public CategoryInput() {
        // Required empty public constructor
    }
    public CategoryInput(boolean switchLeft) {
        // Required empty public constructor
        this.switchLeft = switchLeft;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryInput.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryInput newInstance(String param1, String param2) {
        CategoryInput fragment = new CategoryInput();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category_input, container, false);
        if (!switchLeft)
        {
            view.findViewById(R.id.limit_row_cate).setVisibility(View.VISIBLE);
        }
        nameEt = view.findViewById(R.id.cate_name_edit);
        descEt = view.findViewById(R.id.cate_desc_edit);
        limitEt = view.findViewById(R.id.cate_limit_edit);
        try{
            limitEt.addTextChangedListener(new NumberTextWatcher(limitEt));
        }catch (Exception e) {

        }
        Log.d("nameEt", String.valueOf(nameEt == null));
        return view;
    }

    public String getName()
    {
        name = nameEt.getText() == null?"":nameEt.getText().toString();
        return name;
    }
    public String getDesc()
    {
        desc = descEt.getText() == null?"":descEt.getText().toString();
        return desc;
    }
    public String getLimit()
    {
        limit = limitEt.getText() == null?"":limitEt.getText().toString();
        return limit;
    }

    public void setName(String name)
    {
        nameEt.setText(name);
        nameEt.invalidate();
    }
    public void setDesc(String desc)
    {
        descEt.setText(desc);
        descEt.invalidate();
    }
    public void setLimit(String limit)
    {
        limitEt.setText(limit);
    }
    public View getView()
    {
        return view;
    }
}