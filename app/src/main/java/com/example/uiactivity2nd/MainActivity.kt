package com.example.uiactivity2nd

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
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

        lateinit var adapter: AdapterList
        lateinit var songs: MutableList<Song>
        lateinit var songsTableHandler: SongsTableHandler
        lateinit var SList: ListView
    }
    class AdapterList(context: Context, songs: MutableList<Song>): BaseAdapter(){
        private val mContext: Context = context
        private val mSongs: MutableList<Song> = songs

        override fun getCount(): Int {
            return mSongs.size
        }

        override fun getItem(position: Int): Any {
            return mSongs[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.view, parent, false)

            val rowMainDescTxt = rowMain.findViewById<TextView>(R.id.ArtistAndAlbum)
            val rowMainSongTxt = rowMain.findViewById<TextView>(R.id.Title)
            rowMainSongTxt.text = mSongs[position].title
            val desc = "${mSongs[position].artist} - ${mSongs[position].album}"
            rowMainDescTxt.text = desc

            return rowMain
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        songsTableHandler = SongsTableHandler(this)
        songs = songsTableHandler.read()
        adapter = AdapterList(this, songs)
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
        inflater.inflate(R.menu.msic_details, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.AddQueue ->{
                queue = add(queue, songs[info.position].toString())
                val snackbar = Snackbar.make(findViewById<ListView>(R.id.msic), "${songs[info.position].toString()} moved to queue", Snackbar.LENGTH_LONG)
                snackbar.setAction("GO TO QUEUE") { startActivity(Intent(applicationContext, SongQueue::class.java)) }
                snackbar.show()
                return true
            }
            R.id.AddToAlbum ->{
                Albums.albumsTableHandler = AlbumsTableHandler(this)
                Albums.albums = Albums.albumsTableHandler.read()

                val arrayAdapt: ArrayAdapter<String> = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1)
                for(album in Albums.albums){
                    arrayAdapt.add(album.title)
                }

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Choose an Album")
                dialogBuilder.setAdapter(arrayAdapt) { _, which ->
                    Albums.albums[which].albumSongs.add(songs[info.position])
                    Toast.makeText(applicationContext, "${Albums.albums[which].albumSongs.size} Song has been added to album.", Toast.LENGTH_LONG).show()
                }
                dialogBuilder.setNegativeButton("Cancel", null)
                val dialog: AlertDialog = dialogBuilder.create()
                dialog.show()

                 true
            }
            R.id.ESong ->{
                val songId = songs[info.position].id
                val intent = Intent(applicationContext, EditSong::class.java)
                intent.putExtra("songId", songId)
                startActivity(intent)
                true
            }
            R.id.DSong ->{
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