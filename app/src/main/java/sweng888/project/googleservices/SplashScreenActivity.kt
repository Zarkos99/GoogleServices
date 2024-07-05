package sweng888.project.googleservices

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        /** Configure and start the progress bar animation  */
        val m_progress_bar = findViewById(R.id.splash_progress_bar) as ProgressBar


        val duration: Long = 3000

        /** initialize the progress bar and animate it from 0% to 100% using an ObjectAnimator
         * object. The animation uses a decelerate interpolator to give it a smoother animation  */
        val animation = ObjectAnimator.ofInt(m_progress_bar, "progress", 0, 100)
        animation.setDuration(duration)
        animation.interpolator = DecelerateInterpolator()
        animation.start()

        /** We use a Handler object to delay the launch of the MainActivity.
         * The delay, an intent is created to launch the LoginActivity, and the finish()
         * method is called to destroy the splash screen activity.  */
        Handler().postDelayed({
            /** Start the next activity after a delay  */
            /** Start the next activity after a delay  */
            /** Start the next activity after a delay  */

            /** Start the next activity after a delay  */
            val intent = Intent(
                this@SplashScreenActivity,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish() // Destroy splash screen activity
        }, duration)
    }
}