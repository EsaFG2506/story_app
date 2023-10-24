package com.kikilidyaaaa.storyapp.view.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.*
import androidx.lifecycle.lifecycleScope
import com.kikilidyaaaa.storyapp.R
import com.kikilidyaaaa.storyapp.data.pref.UserPreference
import com.kikilidyaaaa.storyapp.data.pref.dataStore
import com.kikilidyaaaa.storyapp.view.main.MainActivity
import com.kikilidyaaaa.storyapp.view.welcome.WelcomeActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    val handler = Handler(Looper.getMainLooper())
    private lateinit var pref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        pref = UserPreference.getInstance(this.dataStore)

        handler.postDelayed({
            lifecycleScope.launch {
                val userModel = pref.getSession().first()
                if (userModel.isLogin) {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashScreenActivity, WelcomeActivity::class.java))
                }
                finish()
            }
        },3000)
    }
}