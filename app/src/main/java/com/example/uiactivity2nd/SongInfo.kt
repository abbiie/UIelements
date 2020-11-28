package com.example.uielements

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

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
            val albumName = set?.getString("name")
            MyList = MainActivity.songList[set?.getInt("position")!!]
            findViewById<ImageView>(R.id.imageView).setImageResource(MainActivity.AlbumImg[set?.getInt("position")!!])
            findViewById<TextView>(R.id.AlbumTitle).text=albumName
            adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MyList)
            val Music = findViewById<ListView>(R.id.ListofSongs)
            Music.adapter = adapter
            registerForContextMenu(Music)
        }
        private lateinit var adapter: ArrayAdapter<String>
        private lateinit var MyList: Array<String>
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
                        MainActivity.songList[set?.getInt("position")!!] =
                        remove(MainActivity.songList[set.getInt("position")], info.position)
                        MyList = MainActivity.songList[set.getInt("position")]
                        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MyList)
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