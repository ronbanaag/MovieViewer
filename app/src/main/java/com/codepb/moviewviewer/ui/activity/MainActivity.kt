package com.codepb.moviewviewer.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.codepb.moviewviewer.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    companion object {
        var mainActivityReference: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        mainActivityReference = this
    }

    fun showLoading(){
        ll_loading_data.visibility = View.VISIBLE
    }

    fun hideLoading(){
        ll_loading_data.visibility = View.GONE
    }

}