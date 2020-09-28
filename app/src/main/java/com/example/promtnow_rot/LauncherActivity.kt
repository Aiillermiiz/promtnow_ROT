package com.example.promtnow_rot

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.cleveroad.androidmanimation.LoadingAnimationView


/**
 * Example of using animation as view in layout.
 */
class LauncherActivity : AppCompatActivity() {
    val SPLASH_TIME_OUT:Long = 1500 // 3000 = 1 sec
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            startActivity(Intent(this,LoginActivity()::class.java))
            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}