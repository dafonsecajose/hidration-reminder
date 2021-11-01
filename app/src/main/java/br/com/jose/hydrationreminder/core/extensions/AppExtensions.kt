package br.com.jose.hydrationreminder.core

import android.animation.ValueAnimator
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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

@RequiresApi(Build.VERSION_CODES.O)
fun String.formatMilliSeconds(): Long{
    return LocalDateTime.parse(this, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.formatMilliSeconds() : Long {
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.formatLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:s"))
}

fun View.hideSoftKeyboard() {
    val im = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    im.hideSoftInputFromWindow(windowToken, 0)
}