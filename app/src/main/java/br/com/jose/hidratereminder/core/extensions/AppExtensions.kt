package br.com.jose.hidratereminder.core

import android.animation.ValueAnimator
import android.widget.TextView
import br.com.jose.hidratereminder.databinding.QuantityPickerBinding
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

var TextInputLayout.text : String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }

val dateNow: Date = Calendar.getInstance().time

fun getDateString(date: Date = dateNow): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

fun getTimeString(date: Date = dateNow): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(date)
}

fun TextView.addNumberDrink(drunk: Int, quantity: Int) {
    val animator = ValueAnimator.ofInt(drunk, quantity)
    animator.duration = 1000
    animator.addUpdateListener {
        this.text = it.animatedValue.toString()
    }
    animator.start()
}