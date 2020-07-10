package com.example.wifi

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity(), TimePickDialog.TimePickDialogListener {

    private lateinit var mTextViewCountDown: TextView
    private var exampleDialog = TimePickDialog()
    private lateinit var wifiManager: WifiManager
    private val START_TIME_IN_MILLIS: Int = 0
    private lateinit var mButtonStartPause: Button
    private lateinit var mButtonReset: Button
    private lateinit var mCountDownTimer: CountDownTimer
    private var mTimerRunning = false
    private var mTimeLeftInMillis = START_TIME_IN_MILLIS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextViewCountDown = findViewById(R.id.text_view_countdown)

        mTextViewCountDown.setOnClickListener {
            if (!mTimerRunning)
                openDialog();

        }

        mButtonStartPause = findViewById(R.id.button_start_pause)
        mButtonReset = findViewById(R.id.button_reset)

        mButtonStartPause.setOnClickListener {
            if (mTimerRunning) {
                pauseTimer();
            } else {

                startTimer();
            }
        }
        mButtonReset.setOnClickListener {
            resetTimer()
        }
        updateCountDownText()

    }

    private fun openDialog() {
        val s = mTextViewCountDown.text.toString().split(":")
        exampleDialog.setNumber(s[0].toInt(), s[1].toInt())
        exampleDialog.show(supportFragmentManager, null)
    }

    override fun applyTexts(minutes: Int, seconds: Int) {
        mTimeLeftInMillis = minutes * 60000 + seconds * 1000 + 1000
        mTextViewCountDown.text = intToStringTimer(minutes, seconds)
        startTimer()
    }

    private fun disableWifi() {
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = false
    }

    private fun startTimer() {
        if (mTimeLeftInMillis.toLong() > 0) {
            mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis.toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    mTimeLeftInMillis = millisUntilFinished.toInt()
                    updateCountDownText()
                }

                override fun onFinish() {
                    disableWifi()
                    mTimerRunning = false
                    // mButtonStartPause.text = "Start"
                    mButtonStartPause.setBackgroundResource(R.drawable.ic_start)

                    mButtonStartPause.visibility = View.INVISIBLE
                    mButtonReset.visibility = View.VISIBLE
                }
            }.start()
            mTimerRunning = true
            //mButtonStartPause.text = "pause"
            mButtonStartPause.setBackgroundResource(R.drawable.pause)

            mButtonReset.visibility = View.INVISIBLE
        }
    }

    private fun pauseTimer() {
        mCountDownTimer.cancel()
        mTimerRunning = false
        mButtonStartPause.setBackgroundResource(R.drawable.ic_start)
        mButtonReset.visibility = View.VISIBLE
    }

    private fun resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS
        updateCountDownText()
        mButtonReset.visibility = View.INVISIBLE
        mButtonStartPause.visibility = View.VISIBLE
    }

    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis / 1000) / 60
        val seconds = (mTimeLeftInMillis / 1000) % 60
        mTextViewCountDown.text = intToStringTimer(minutes, seconds)
    }

    private fun intToStringTimer(minutes: Int, seconds: Int): String {
        return java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}




















