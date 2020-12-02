package com.example.uiactivity2nd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uiactivity2nd.models.Album

class AddAlbum : AppCompatActivity() {
    lateinit var addAlbumBtn: Button
    lateinit var titleTxt: EditText
    lateinit var releaseDateTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_album)
        val databaseHandler = AlbumsTableHandler(this)
        titleTxt = findViewById(R.id.addAlbumTitleTxt)
        releaseDateTxt = findViewById(R.id.addReleaseDateTxt)
        addAlbumBtn = findViewById(R.id.addAlbumBtn)
        addAlbumBtn.setOnClickListener {
            val title = titleTxt.text.toString()
            val releaseDate = releaseDateTxt.text.toString()
            val album = Album(title = title, releaseDate = releaseDate)
            if(databaseHandler.add(album)){
                Toast.makeText(applicationContext, "Album Added Successfully", Toast.LENGTH_LONG).show()
                clearFields()
            }else{
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun clearFields() {
        titleTxt.text.clear()
        releaseDateTxt.text.clear()
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
                startActivity(Intent(this, Album::class.java))
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
