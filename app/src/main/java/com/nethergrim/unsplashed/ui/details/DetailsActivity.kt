package com.nethergrim.unsplashed.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    companion object {
        val EXTRA_WALLPAPER_ID = "id"

        fun getIntent(context: Context, id: String) : Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_WALLPAPER_ID, id)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(android.R.color.black)
    }

}
