package com.example.foodapp.ui.login

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapp.R
import com.example.foodapp.presenter.SplashPresenter

class SplashScreen : AppCompatActivity() {

    private var splashPresenter = SplashPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(Runnable {
            splashPresenter.checkLogin(this)
            finish()
        }, 1000)
    }
}