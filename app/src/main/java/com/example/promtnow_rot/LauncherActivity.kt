package com.example.promtnow_rot

import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.cleveroad.androidmanimation.LoadingAnimationView


/**
 * Example of using animation as view in layout.
 */
class LauncherActivity : AppCompatActivity() {
    val SPLASH_TIME_OUT:Long = 1500 // 1000 = 1 sec
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        //set status bar color
//        val window: Window = window
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = Color.parseColor("#00B6E3")
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            startActivity(Intent(this,LoginActivity()::class.java))
            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}