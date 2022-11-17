package com.gamik.currencyconverter.util

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.gamik.currencyconverter.R

object Message {
    fun show(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun show(
        context: Context,
        message: String,
        positiveText: String,
        positiveListener: DialogInterface.OnClickListener,
    ) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(positiveText, positiveListener)
            .setNegativeButton(
                context.getString(R.string.cancel)
            ) { dialog, _ -> dialog.dismiss() }
            .create().show()
    }
}