package com.example.uiactivity2nd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*

class SongInfo : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_info)

        val Mtitle = intent.getStringExtra("Song")
        findViewById<TextView>(R.id.sam).text = Mtitle

//        val spinner = findViewById<Spinner>(R.id.spinner)
//        ArrayAdapter.createFromResource(
//            this,
//            R.array.Music_Option,
//            android.R.layout.simple_list_item_1
//        )
//            .also { adapter ->
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                spinner.adapter = adapter
//            }
//        spinner.onItemSelectedListener = this
//        findViewById<Button>(R.id.button).setOnClickListener { display() }
        val imageView = findViewById<ImageView>(R.id.sams)
        registerForContextMenu(imageView)
        val ed = findViewById<ImageView>(R.id.edS)
        registerForContextMenu(ed)
        val james= findViewById<ImageView>(R.id.jamesA)
        registerForContextMenu(james)
        val pink = findViewById<ImageView>(R.id.pinkS)
        registerForContextMenu(pink)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.msic_details, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.Artist -> {
                Toast.makeText(this, "The Artist", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

//    }
//    fun display(){
//        val button = button()
//        val fm = supportFragmentManager
//        button.show(fm, "ignore_button")
//    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.i("MusicList", "Slected Music at Position $position")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}