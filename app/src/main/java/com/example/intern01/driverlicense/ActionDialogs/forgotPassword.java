package com.example.intern01.driverlicense.ActionDialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.intern01.driverlicense.R;

/**
 * Created by intern01 on 25/05/2017.
 */

public class forgotPassword extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_forgot_password);
        return builder.create();
    }
}
