package pt.uevora.di.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import java.util.logging.Logger

class CounterActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private var untilFinished = 21000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)
    }

    private fun startCountDownTimer(time: Long) {
        timer = object: CountDownTimer(time, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                untilFinished = millisUntilFinished
                findViewById<TextView>(R.id.timer).text = getString(R.string.remaining_time, (untilFinished / 1000))
            }

            override fun onFinish() {
                findViewById<TextView>(R.id.timer).text = getString(R.string.finished)
            }

        }

        timer.start()
    }

    override fun onResume() {
        super.onResume()

        startCountDownTimer(untilFinished)
    }

    override fun onPause() {
        super.onPause()

        timer.cancel()
    }
}