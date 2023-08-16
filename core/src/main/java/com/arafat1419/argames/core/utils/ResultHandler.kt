package com.arafat1419.argames.core.utils

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.arafat1419.argames.core.vo.Resource

class ResultHandler(
    private val context: Context,
    private var indicatorView: Dialog?,
) {

    fun <T> handle(
        result: Resource<T>,
        isSuccess: (T) -> Unit
    ) {
        when (result) {
            is Resource.Error -> {
                isLoading(false)
                Log.d("TAG", result.error?.message.toString())
                Toast.makeText(context, result.error?.message, Toast.LENGTH_SHORT).show()
            }

            is Resource.Loading -> {
                isLoading(true)
                Log.d("TAG", "Loading..")
            }

            is Resource.Success -> {
                isLoading(false)
                Log.d("TAG", result.data.toString())
                if (result.data == null) return
                isSuccess(result.data)
            }
        }
    }

    fun isLoading(state: Boolean) {
        if (state) {
            showActivityIndicator()
        } else {
            hideActivityIndicator()
        }
    }

    private fun showActivityIndicator() {
        hideActivityIndicator()
        indicatorView = CommonUtils.showLoadingDialog(context)
    }

    private fun hideActivityIndicator() {
        if (indicatorView != null && indicatorView?.isShowing == true) {
            indicatorView!!.cancel()
        }
    }
}