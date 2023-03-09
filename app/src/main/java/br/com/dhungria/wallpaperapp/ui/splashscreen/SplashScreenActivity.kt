package br.com.dhungria.wallpaperapp.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import br.com.dhungria.wallpaperapp.MainActivity
import br.com.dhungria.wallpaperapp.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity: AppCompatActivity() {

    private val splashTime: Long = 2000

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashTime)
    }

}