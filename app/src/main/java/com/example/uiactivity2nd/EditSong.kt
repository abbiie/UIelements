package com.example.uiactivity2nd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uiactivity2nd.models.Song

class EditSong : AppCompatActivity() {
    lateinit var editSongBtn: Button
    lateinit var titleTxt: EditText
    lateinit var artistTxt: EditText
    lateinit var albumTxt: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_song)
        val songId = intent.getIntExtra("songId", 0)
        val databaseHandler = SongsTableHandler(this)
        val song = databaseHandler.readOne(songId)

        titleTxt = findViewById(R.id.editTitle)
        artistTxt = findViewById(R.id.editArtist)
        albumTxt = findViewById(R.id.editAlbum)
        editSongBtn = findViewById(R.id.editBtn)

        titleTxt.setText(song.title)
        artistTxt.setText(song.artist)
        albumTxt.setText(song.album)
        editSongBtn.setOnClickListener {
            //set the fields from the form
            val title = titleTxt.text.toString()
            val artist = artistTxt.text.toString()
            val album = albumTxt.text.toString()

            val newSong = Song(id = songId, title = title, artist = artist, album = album)
            if(databaseHandler.update(newSong)){
                Toast.makeText(applicationContext, "Song was Updated", Toast.LENGTH_LONG).show()
                MainActivity.adapter = MainActivity.AdapterList(this, MainActivity.songs)
                MainActivity.SList.adapter = MainActivity.adapter
                clearFields()
            }else{
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun clearFields() {
        titleTxt.text.clear()
        artistTxt.text.clear()
        albumTxt.text.clear()
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
                true
            }
            R.id.GoSongs ->{
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.GoAlbum ->{
                startActivity(Intent(this, Albums::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}