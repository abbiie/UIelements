package com.example.uiactivity2nd

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.example.uiactivity2nd.models.Album

class Albums : AppCompatActivity() {
    companion object {
        var adapter: AlbumAdapter? = null
        lateinit var albumsTableHandler: AlbumsTableHandler
        lateinit var albums: MutableList<Album>
        lateinit var albumGrid: GridView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        albumsTableHandler = AlbumsTableHandler(this)
        albums = albumsTableHandler.read()
        adapter = AlbumAdapter(this, albums)
        albumGrid = findViewById<GridView>(R.id.AlbumGrid)
        albumGrid.adapter = adapter
        registerForContextMenu(albumGrid)
        albumGrid.onItemClickListener = object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent = Intent(applicationContext, SongInfo::class.java)
                intent.putExtra("albumId", position)
                startActivity(intent)
            }
        }
        val addBtn: View = findViewById(R.id.AddAlbuum)
        addBtn.setOnClickListener {
            startActivity(Intent(this, AddAlbum::class.java))
        }
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater

        //Add the menu items for the context menu
        inflater.inflate(R.menu.album_menu, menu)
    }

    //Method when a context item is selected
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.edit_album -> {
                //get the song that was selected
                val albumId = albums[info.position].id

                //put it in an extra
                val intent = Intent(applicationContext, EditAlbum::class.java)
                intent.putExtra("albumId", albumId)

                //start activity
                startActivity(intent)
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
                true
            }
            R.id.GoSongs ->{
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.GoAlbum->{
                true
            }
            R.id.add ->{
                startActivity(Intent(this, AddSong::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    class AlbumAdapter : BaseAdapter {
        var myListSong: MutableList<Album>
        var context: Context? = null
        constructor(context: Context, albumList: MutableList<Album>) : super() {
            this.context = context
            this.myListSong = albumList
        }

        override fun getCount(): Int {
            return myListSong.size
        }

        override fun getItem(position: Int): Any {
            return myListSong[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var inflater: LayoutInflater = LayoutInflater.from(context).context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val song = this.myListSong[position]
            var myView = inflater.inflate(R.layout.album_list, null)
            myView.isEnabled = false
            myView.findViewById<ImageView>(R.id.imgView).setOnClickListener {
                val intent = Intent(context, SongInfo::class.java)
                intent.putExtra("name", song.toString())
                context!!.startActivity(intent) }
            myView.findViewById<ImageView>(R.id.imgView).setImageResource(MainActivity.AlbumImg[position])
            myView.findViewById<TextView>(R.id.txtView).text = song.toString()
            return myView
        }
    }
}