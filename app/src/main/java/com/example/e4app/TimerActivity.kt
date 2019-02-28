package com.example.e4app

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import com.example.e4app.ui.sensor.SensorActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_timer.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream


class TimerActivity : AppCompatActivity() {

    private var flagTimer = false

    private var flagStop = false

    private var secondsRemaining = 12L


    val timer = Counter(secondsRemaining * 1000, 1000)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_timer)
        timer.start()
        progress_countdown.max = secondsRemaining.toInt()


        btnStop.setOnClickListener { v ->
            timer.cancel()
            flagStop = true

            val returnIntent:Intent = Intent()

            returnIntent.putExtra("flagStop", flagStop.toString())
            returnIntent.putExtra("flagTimer", flagTimer.toString())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

    }

    inner class Counter(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            println("Timer Completed.")
            progress_countdown.progress = 0

            flagTimer = true

            val returnIntent:Intent = Intent()
            returnIntent.putExtra("flagTimer", flagTimer.toString())
            returnIntent.putExtra("flagStop", flagStop.toString())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()


        }

        override fun onTick(millisUntilFinished: Long) {
            secondsRemaining = millisUntilFinished / 1000
            println("Timer  : " + millisUntilFinished / 1000)
            updateCountdownUI()
            //textViewTimer.text = (millisUntilFinished / 1000).toString()
            progress_countdown.progress = (secondsRemaining).toInt()

        }
    }

    private fun updateCountdownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinutesUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinutesUntilFinished.toString()
        textViewTimer.text = "$minutesUntilFinished:${
        if (secondsStr.length == 2) secondsStr
        else "0" + secondsStr}"
        //progress_countdown.progress = (timerLenghtSeconds - secondsRemaining).toInt()
    }

}
