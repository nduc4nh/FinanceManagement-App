package com.example.financemanagementapplication.View.User;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.View.GeneralDialog;
import com.example.financemanagementapplication.Wrapper;
import com.example.financemanagementapplication.model.database.entity.User;
import com.example.financemanagementapplication.model.viewmodel.UserVM;
import com.example.financemanagementapplication.tool.Converter;

import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UserVM uvm;
    private Toolbar tb;
    private LiveData<List<User>> us;
    private LiveData<User> us1;
    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        tb = getActivity().findViewById(R.id.toolbar);
        tb.setTitle("User");
        uvm = new ViewModelProvider(requireActivity()).get(UserVM.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("START","-------------------------------------------------------------");
        Log.d("DUP",uvm.toString());
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        Button btn = view.findViewById(R.id.button_user_money);
        TextView textView = view.findViewById(R.id.user_name_header);
        uvm.setId(0l);
        uvm.tmp.observe(getViewLifecycleOwner(), user -> {
            String name;
            Long balance;
            if (user == null)
            {
                User tmp = new User("Duc Anh",0l);
                tmp.setId(0l);
                uvm.insertUser(tmp);
                name = tmp.getName();
                balance = tmp.getBalance();
            }
            else
            {
                Log.d("name",user.toString());
                name = user.getName();
                balance = user.getBalance();
            }
            textView.setText(name);
            btn.setText(Converter.rawToReadable(balance));
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*User user = new User("cd",new Random().nextLong());
                user.setId(0l);
                uvm.updateUser(user);
                //us1.removeObservers(getViewLifecycleOwner());*/
                Bundle b = new Bundle();
                GeneralDialog dialog = new GeneralDialog();
                dialog.setMessenger("Test Purpose");
                dialog.setTitle("Test");
                Wrapper w = new Wrapper(dialog);
                b.putSerializable("dialog", w);
                Navigation.findNavController(v).navigate(R.id.action_user_nav_to_test3,b);
            }
        });
        return view;
    }

}