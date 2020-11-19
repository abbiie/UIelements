package com.example.uielements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class SongInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_info)


        var MusicList: Array<String> = emptyArray()
        val set = intent.extras
        if (set != null) {
            when (set.getInt("position")) {
                0 -> MusicList = MainActivity.songList.sliceArray(0..4)
                1 -> MusicList = MainActivity.songList.sliceArray(5..9)
                2 -> MusicList = MainActivity.songList.sliceArray(10..14)
                3 -> MusicList = MainActivity.songList.sliceArray(15..19)
            }
            findViewById<ImageView>(R.id.imageView).setImageResource(MainActivity.AlbumImg[set?.getInt("position")!!])
            findViewById<TextView>(R.id.textView).setText(set.getString("name"))

            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MusicList)
            val Music = findViewById<ListView>(R.id.ListofSongs)
            Music.adapter = adapter

        }
    }
}