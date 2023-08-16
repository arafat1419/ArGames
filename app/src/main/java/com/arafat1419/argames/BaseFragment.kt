package com.arafat1419.argames

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.arafat1419.argames.core.utils.CommonUtils
import com.arafat1419.argames.core.utils.ResultHandler

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    private var indicatorView: Dialog? = null

    val resultHandler by lazy {
        ResultHandler(requireContext(), indicatorView)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickHandler()
    }

    protected abstract fun onClickHandler()

    fun showActivityIndicator() {
        hideActivityIndicator()
        indicatorView = CommonUtils.showLoadingDialog(requireContext())
    }

    fun hideActivityIndicator() {
        if (indicatorView != null && indicatorView?.isShowing == true) {
            indicatorView!!.cancel()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}