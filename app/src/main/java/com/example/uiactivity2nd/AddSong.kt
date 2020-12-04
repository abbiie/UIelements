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

class AddSong : AppCompatActivity() {
    lateinit var addSongBtn: Button
    lateinit var titleTxt: EditText
    lateinit var artistTxt: EditText
    lateinit var albumTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_song)
        val databaseHandler = SongsTableHandler(this)

        titleTxt = findViewById(R.id.addTitle)
        artistTxt = findViewById(R.id.addArtist)
        albumTxt = findViewById(R.id.addAlbum)
        addSongBtn = findViewById(R.id.SongBtn)
        addSongBtn.setOnClickListener {
            val title = titleTxt.text.toString()
            val artist = artistTxt.text.toString()
            val album = albumTxt.text.toString()

            val song = Song(title = title, artist = artist, album = album)
            if(databaseHandler.add(song)){
                Toast.makeText(applicationContext, "Song was Added", Toast.LENGTH_LONG).show()
                clearFields()
            }else{
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun clearFields() {
        titleTxt.text.clear()
        artistTxt.text.clear()
        albumTxt.text.clear() }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.SongQueue ->{
                startActivity(Intent(this, SongQueue::class.java))
                true }
            R.id.GoSongs ->{
                startActivity(Intent(this, MainActivity::class.java))
                true }
            R.id.GoAlbum ->{
                startActivity(Intent(this, Albums::class.java))
                true }
            R.id.AddToAlbum ->{
                true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}