package com.example.uiactivity2nd

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
import com.example.uiactivity2nd.models.Song
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
//    private fun append(arr: Array<String>, element: String): Array<String>{
//        val arr: MutableList<String> = arr.toMutableList()
//        arr.add(element)
//        return arr.toTypedArray()
//    }
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

        lateinit var adapter: ArrayAdapter<Song>
        lateinit var songs: MutableList<Song>
        lateinit var songsTableHandler: SongsTableHandler
        lateinit var SList: ListView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        songsTableHandler = SongsTableHandler(this)
        songs = songsTableHandler.read()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,songs)
        SList = findViewById<ListView>(R.id.msic)
        SList.adapter = adapter
        registerForContextMenu(SList)

        val Lowerbutton: View = findViewById(R.id.addSongFab)
        Lowerbutton.setOnClickListener {
            startActivity(Intent(this, AddSong::class.java))
        }
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_menu, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.add_to_queue ->{
                queue = add(queue, songs[info.position].toString())
                val snackbar = Snackbar.make(findViewById<ListView>(R.id.msic), "${songs[info.position].toString()} moved to queue", Snackbar.LENGTH_LONG)
                snackbar.setAction("GO TO QUEUE") { startActivity(Intent(applicationContext, SongQueue::class.java)) }
                snackbar.show()
                return true
            }
            R.id.remove_from_queue ->{
                queue = remove(queue, songs[info.position].toString())
                Toast.makeText(this, "${songs[info.position].toString()} removed from Queue", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.Edit_song ->{
                val songId = songs[info.position].id
                val intent = Intent(applicationContext, EditSong::class.java)
                intent.putExtra("songId", songId)
                startActivity(intent)
                true
            }
            R.id.Delete_song ->{
                val song = songs[info.position]
                if(songsTableHandler.delete(song)){
                    songs.removeAt(info.position)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "Song Deleted from List", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext, "Oops something went wrong!", Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.SongQueue ->{
                startActivity(Intent(this, SongQueue::class.java))
                true }
            R.id.GoSongs ->{
                true }
            R.id.GoAlbum ->{
                startActivity(Intent(this, Albums::class.java))
                true }
            R.id.AddSong->{
                startActivity(Intent(this, AddSong::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun add(arr: Array<String>, element: String): Array<String>{
        val list: MutableList<String> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }
    private fun remove(list: Array<String>, element: String): Array<String>{
        val list = list.toMutableList()
        list.remove(element)
        return list.toTypedArray()
    }
}