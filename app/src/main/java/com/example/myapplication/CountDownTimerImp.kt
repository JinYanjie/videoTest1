package com.example.myapplication

import android.os.CountDownTimer
import java.text.SimpleDateFormat
import java.util.*

class CountDownTimerImp(millisInFuture: Long, countDownInterval: Long)
    : CountDownTimer(millisInFuture, countDownInterval) {

    var defMillis: Long = 0

    init {
        defMillis = millisInFuture
    }

    //通知跳转下一道题，
    override fun onFinish() {
        if (onTime != null) {
            onTime?.onFinish()
        }
    }

    /**
     * 将毫秒转换成 00:00:00的形式
     */
    override fun onTick(millisUntilFinished: Long) {
        if (onTime != null) {
            var hour = millisUntilFinished / (60 * 60 * 1000)
            var minute = millisUntilFinished % (60 * 60 * 1000) / (60 * 1000)
            var seconds = millisUntilFinished % (60 * 1000) / 1000
            var time = String.format("%02d:%02d:%02d", hour, minute, seconds)

            onTime!!.onTime(time)
        }
    }

    /**
     * 这个接口告知TimerManager 时间的变化
     */
    var onTime: OnTime? = null

    interface OnTime {
        fun onTime(time: String)
        fun onFinish()
    }

}