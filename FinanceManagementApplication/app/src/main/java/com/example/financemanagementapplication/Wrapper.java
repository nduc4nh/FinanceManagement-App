package com.example.financemanagementapplication;

import androidx.fragment.app.FragmentManager;

import com.example.financemanagementapplication.View.GeneralDialog;

import java.io.Serializable;

public class Wrapper implements Serializable {
    FragmentManager mng;
    GeneralDialog dialog;

    public FragmentManager getMng() {
        return mng;
    }

    public GeneralDialog getDialog() {
        return dialog;
    }

    public Wrapper(GeneralDialog dialog) {
        this.dialog = dialog;
    }

    public Wrapper(FragmentManager mng) {
        this.mng = mng;
    }
}
