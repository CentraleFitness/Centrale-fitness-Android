package com.fitness.centrale.misc;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AlertDialogBuilder {

    public static AlertDialog createAlertDialog(final Context ctx, final String title, final String message, final String positiveButton){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    public static AlertDialog createAlertDialog(final Context ctx, final String title, final String message, final String positiveButton, final DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, listener);
        return builder.create();
    }

}
