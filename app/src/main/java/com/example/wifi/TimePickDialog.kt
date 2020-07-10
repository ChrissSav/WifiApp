package com.example.wifi

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatDialogFragment


class TimePickDialog : AppCompatDialogFragment() {

    private lateinit var numberPickerMinutes: NumberPicker
    private lateinit var numberPickerSeconds: NumberPicker
    private lateinit var listener: TimePickDialogListener
    private var minutes = 0
    private var seconds = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity) as AlertDialog.Builder

        val inflater = activity?.layoutInflater
        lateinit var view: View

        inflater?.let {
            view = inflater.inflate(R.layout.time_pick_dialog, null)
        }

        view.let {
            numberPickerMinutes = view.findViewById(R.id.numberPicker_minutes)
            numberPickerSeconds = view.findViewById(R.id.numberPicker_seconds)
            numberPickerMinutes.maxValue = 100
            numberPickerMinutes.minValue = 0
            numberPickerMinutes.value = minutes
            numberPickerSeconds.minValue = 0
            numberPickerSeconds.maxValue = 59
            numberPickerSeconds.value = seconds

        }


        builder.setView(view)
            .setPositiveButton("Αποθήκευση", DialogInterface.OnClickListener { _, i ->
                listener.applyTexts(numberPickerMinutes.value, numberPickerSeconds.value)

            })


        return builder.create()

    }


    fun setNumber(minutes: Int, seconds: Int) {
        this.minutes = minutes
        this.seconds = seconds
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as TimePickDialogListener

        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString() +
                        "must implement TimePickDialogListener"
            );
        }
    }

    interface TimePickDialogListener {
        fun applyTexts(minutes: Int, seconds: Int)
    }

}