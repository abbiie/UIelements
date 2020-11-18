package com.example.uiactivity2nd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class SongInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_info)


        var SList: Array<String> = arrayOf()
        val set = intent.extras

            findViewById<ImageView>(R.id.imageView).setImageResource(MainActivity.AlbumImg[set?.getInt("position")!!])
            findViewById<TextView>(R.id.textView).setText(set.getString("name"))

            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SList)
            val MusicList = findViewById<ListView>(R.id.ListofSongs)
            MusicList.adapter = adapter

            MusicList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                Log.i("Position", "Position $position")
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Song", SList[position])
                startActivity(intent)
            }
        }
    }
