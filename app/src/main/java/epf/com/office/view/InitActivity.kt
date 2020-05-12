package epf.com.office.view

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import epf.com.office.R
import epf.com.office.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_init.*

class InitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_init)
        AppPreferences.init(this)
        startAnimation();
    }

    fun startAnimation() {
        splash_logo.startAnimation(AnimationUtils.loadAnimation(this,
            R.anim.fade_animation
        ))
        val timer: Thread = object : Thread() {
            override fun run() {
                super.run()
                try {
                    sleep(4000)
                } catch (e: InterruptedException) {
                } finally {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        timer.start()
    }

}
