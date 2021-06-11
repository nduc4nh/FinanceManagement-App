package com.example.financemanagementapplication.tool;

import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.Toast;

public class SupportToast {
    Toast t;
    public SupportToast(Toast t) {
        this.t = t;
    }

    public void showToast(int length)
    {
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(length, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                t.show();
            }
            public void onFinish() {
                t.cancel();
            }
        };
        t.show();
        toastCountDown.start();
    }
}
