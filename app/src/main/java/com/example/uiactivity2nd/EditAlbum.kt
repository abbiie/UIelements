package com.example.uiactivity2nd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uiactivity2nd.models.Album
import com.example.uiactivity2nd.models.Song

class EditAlbum : AppCompatActivity() {
    lateinit var editAlbumBtn: Button
    lateinit var titleTxt: EditText
    lateinit var releaseDateTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_album)

        //get extras from intent
        val albumId = intent.getIntExtra("albumId", 0)

        //get record from database
        val databaseHander = AlbumsTableHandler(this)
        val album = databaseHander.readOne(albumId)

        //set the edittext values based on the record
        titleTxt = findViewById(R.id.editAlbumTitleTxt)
        releaseDateTxt = findViewById(R.id.editReleaseDateTxt)
        editAlbumBtn = findViewById(R.id.editAlbumBtn)

        titleTxt.setText(album.title)
        releaseDateTxt.setText(album.releaseDate)

        editAlbumBtn.setOnClickListener {
            //set the fields from the form
            val title = titleTxt.text.toString()
            val artist = releaseDateTxt.text.toString()

            val newAlbum = Album(id = albumId, title = title, releaseDate = artist)
            if (databaseHander.update(newAlbum)) {
                Toast.makeText(applicationContext, "Album was Updated", Toast.LENGTH_LONG).show()
                Albums.adapter = Albums.AlbumAdapter(this, Albums.albums)
                Albums.albumGrid.adapter = Albums.adapter
                clearFields()
            } else {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun clearFields() {
        titleTxt.text.clear()
        releaseDateTxt.text.clear()
    }

    //Add the options for the main menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    //Method when an option in the main menu is selected
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
            R.id.AddToAlbum ->{
                startActivity(Intent(this, AddSong::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}