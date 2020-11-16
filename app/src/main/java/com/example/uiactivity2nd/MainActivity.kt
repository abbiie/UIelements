package com.example.uiactivity2nd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val songArray:Array<String> = arrayOf("stay with me", "Love goes", "Diamonds", "How do you Sleep", "I'm not the only one",
            "Happier", "Dive", "Photograph", "perfect", "shape of you",
            "At my wordst", "17", "Honesty", "Only a fool", "I know",
            "Lasting Lover", "Train Wreck", "Safe Inside", "You", "Marine Parade")
        val adapter  = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songArray)
        val MList = findViewById<ListView>(R.id.songss)
        MList.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.GoAlbum -> {
                startActivity(Intent(this, SongInfo::class.java))
                true
            }
            R.id.GoSongs -> {
                startActivity(Intent(this, Songs::class.java))
                true
            }
            R.id.SongQueue -> {
                startActivity(Intent(this, SongQueue::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}