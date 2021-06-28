package pt.uevora.di.atividade5

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CounterActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private var untilFinished = 21000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        findViewById<Button>(R.id.list_breed).setOnClickListener {
            openListActivity()
        }

    }

    private fun startCountDownTimer(time: Long) {
        timer = object : CountDownTimer(time, 1000) {

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

    private fun openListActivity() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }



}