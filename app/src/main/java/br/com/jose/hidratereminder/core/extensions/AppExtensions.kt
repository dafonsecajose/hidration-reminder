package br.com.jose.hidratereminder.core

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
