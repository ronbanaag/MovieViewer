package com.codepb.moviewviewer.ui.fragment

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

open class BaseFragment: Fragment() {

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun navigateTo(navigationController: Int, navigationId: Int){
        activity?.findNavController(navigationController)?.navigate(navigationId)
    }
}