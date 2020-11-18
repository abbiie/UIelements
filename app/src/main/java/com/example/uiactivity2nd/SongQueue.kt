package com.example.uiactivity2nd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class SongQueue : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_queue)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.queue)
        val addQueue = findViewById<ListView>(R.id.queues)
        addQueue.adapter = adapter
    }
}