package ru.kpfu.itis.arifulina.combcalc

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ru.kpfu.itis.arifulina.combcalc.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        val anim = binding.ivSplashAnim.drawable
        (anim as? AnimatedVectorDrawable)?.registerAnimationCallback(object: Animatable2.AnimationCallback(){
            override fun onAnimationEnd(drawable: Drawable?) {
                anim.start()
            }
        })
        (anim as? Animatable)?.start()
        Handler().postDelayed( Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 500)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}
