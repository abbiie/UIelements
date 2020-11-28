package com.example.uielements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View

class MainActivity : AppCompatActivity() {
    private fun append(arr: Array<String>, element: String): Array<String>{
        val arr: MutableList<String> = arr.toMutableList()
        arr.add(element)
        return arr.toTypedArray()
    }
    companion object {
        var queue: Array<String> = emptyArray()
        val songList = arrayOf(
                arrayOf("stay with me", "Love goes", "Diamonds", "How do you Sleep", "I'm not the only one"),
                arrayOf("Happier", "Dive", "Photograph", "perfect", "shape of you"),
                arrayOf("At my wordst", "17", "Honesty", "Only a fool", "I know"),
                arrayOf("Lasting Lover", "Train Wreck", "Safe Inside", "You", "Marine Parade")
        )

        val ArtistAlbum = arrayOf("Sam Smith", "Ed Sheeran", "James Arthur", "Pink Sweat$")
        val AlbumImg = arrayOf(R.drawable.sam, R.drawable.ed, R.drawable.james, R.drawable.pink)
    }
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songList.flatten())
            val MList = findViewById<ListView>(R.id.msic)
            MList.adapter = adapter
            registerForContextMenu(MList)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.GoAlbum -> {
                startActivity(Intent(this, Album::class.java))
                true
            }
            R.id.GoSongs -> {
                true
            }
            R.id.SongQueue -> {
                startActivity(Intent(this, SongQueue::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        val details = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.AddQueue-> {
                queue = append(queue, songList.flatten()[details.position])
                Toast.makeText(this, "Added to Queue", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}