package com.example.financemanagementapplication.View;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.financemanagementapplication.R;

public class GeneralDialog extends DialogFragment {
    FragmentManager mng;
    String msg = "Nothing";
    String title = "Nothing";

    public GeneralDialog() {
    }

    public GeneralDialog(FragmentManager mng) {
        super();
        this.mng = mng;
    }
    public void setMessenger(String msg)
    {
        this.msg = msg;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.support_fragment,null);
        TextView tv = view.findViewById(R.id.text_empty_label);
        tv.setText(msg);
        builder.setTitle(title).setNegativeButton("Ok",null);
        return builder.create();
    }
}
