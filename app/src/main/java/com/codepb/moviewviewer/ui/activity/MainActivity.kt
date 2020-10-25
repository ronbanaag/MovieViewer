package com.codepb.moviewviewer.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.codepb.moviewviewer.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    companion object {
        var mainActivityReference: MainActivity? = null

        const val VIEW_MODE_SMALL = 1
        const val VIEW_MODE_ZOOMED = 2
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

    fun showConfirmationDialog(context: Context, title: String, message: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMessage(message).setCancelable(false)
            .setPositiveButton(android.R.string.yes) { dialog, which ->

            }
        builder.show()
    }


}