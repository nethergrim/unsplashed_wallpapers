package com.nethergrim.unsplashed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.textView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {

            val text = textView {
                text = "Hello"
            }
            text.gravity = Gravity.CENTER
        }
    }
}
