package com.example.finaldirectionexample01

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "It's time to start your journey!", Toast.LENGTH_LONG).show()
    }
}