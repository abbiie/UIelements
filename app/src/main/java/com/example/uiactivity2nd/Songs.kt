package com.example.uiactivity2nd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class Songs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs)

        val songArray:Array<String> = arrayOf("item 1", "item 2", "item 3", "item 4", "item 5")
        val adapter  = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songArray)
        val MusicList = findViewById<ListView>(R.id.List)
        MusicList.adapter = adapter

        MusicList.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            Log.i("Position", "Position $position")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Song", songArray[position])
            startActivity(intent)
        }
    }
}