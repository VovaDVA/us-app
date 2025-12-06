package com.example.us.ui.screen.special.classes

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}
