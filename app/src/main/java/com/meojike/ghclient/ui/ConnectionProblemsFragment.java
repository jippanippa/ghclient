package com.meojike.ghclient.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class ConnectionProblemsFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context mContext = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("Проблемы с интернетом")
                .setMessage("Пожалуйста, проверьте соединение и перезапустите приложение")
                .setPositiveButton(("ОК"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
