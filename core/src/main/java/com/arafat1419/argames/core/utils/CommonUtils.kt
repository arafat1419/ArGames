package com.arafat1419.argames.core.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.arafat1419.argames.core.R

object CommonUtils {
    fun showLoadingDialog(context: Context): Dialog {
        val progressDialog = Dialog(context)

        progressDialog.apply {
            setContentView(R.layout.progress_dialog)

            if (window != null) {
                window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
        return progressDialog
    }

    fun setRecyclerView(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager, adapter: RecyclerView.Adapter<*>) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    fun EditText.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}