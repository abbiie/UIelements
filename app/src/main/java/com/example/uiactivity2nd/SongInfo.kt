package com.example.uiactivity2nd

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.uiactivity2nd.models.Song

class SongInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_info)

//        old code,
//        var MusicList: Array<String> = emptyArray()
//
//        if (set != null) {
//            when (set.getInt("position")) {
//                0 -> MusicList = MainActivity.songList.sliceArray(0..4)
//                1 -> MusicList = MainActivity.songList.sliceArray(5..9)
//                2 -> MusicList = MainActivity.songList.sliceArray(10..14)
//                3 -> MusicList = MainActivity.songList.sliceArray(15..19)
//            }
             val set = intent.extras
            val albumName = set?.getString("albumId")
            MyList = Albums.albums[albumId!!].albumSongs
            findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.alum)
            findViewById<TextView>(R.id.AlbumTitle).text=Albums.albums[albumId!!].title

            adapter = MainActivity.AdapterList(applicationContext, MyList)
            val Music = findViewById<ListView>(R.id.ListofSongs)
            Music.adapter = adapter
            registerForContextMenu(Music)
        }
        private lateinit var adapter: MainActivity.AdapterList
        private lateinit var MyList: MutableList<Song>
        private var albumId = 0

        private fun remove(arr: Array<String>, item: Int): Array<String>{
            if(item < 0 || item >= arr.size){
                return arr }
            val result = arr.toMutableList()
            result.removeAt(item)
            return result.toTypedArray()
        }
        override fun onCreateContextMenu(
            menu: ContextMenu?, v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            super.onCreateContextMenu(menu, v, menuInfo)
            val inflater = menuInflater
            inflater.inflate(R.menu.remove, menu)
        }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val set = intent.extras
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.RQueue ->{
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Are you sure you want to remove this song from this Album?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", DialogInterface.OnClickListener{
                            _, _ ->
                        Albums.albums[albumId].albumSongs.removeAt(info.position)
                        MyList = Albums.albums[albumId!!].albumSongs
                        adapter = MainActivity.AdapterList(applicationContext, MyList)
                        val songList = findViewById<ListView>(R.id.ListofSongs)
                        songList.adapter = adapter })
                    .setNegativeButton("No", DialogInterface.OnClickListener{
                            dialog, _ ->
                        dialog.cancel() })
                val alert = dialogBuilder.create()
                alert.setTitle("Remove from ${set?.getString("name")} Album")
                alert.show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}