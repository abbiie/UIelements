package com.example.uielements

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView

class Album : AppCompatActivity() {
    var adapter: SongAdapter? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_album)

            adapter = SongAdapter(this)
            findViewById<GridView>(R.id.AlbumGrid).adapter = adapter }

     class SongAdapter : BaseAdapter {
         val myListSong = MainActivity.ArtistAlbum
         var context: Context? = null
         constructor(context: Context) : super() {
             this.context = context
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
             var inflater: LayoutInflater = LayoutInflater.from(context).context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
             val song = this.myListSong[position]
             var myView = inflater.inflate(R.layout.album_list, null)
             myView.findViewById<ImageView>(R.id.imgView).setOnClickListener {
                 val intent = Intent(context, SongInfo::class.java)
                 intent.putExtra("name", song)
                 intent.putExtra("songList", MainActivity.songList)
                 intent.putExtra("position", position)
                 context!!.startActivity(intent)
             }
             myView.findViewById<ImageView>(R.id.imgView).setImageResource(MainActivity.AlbumImg[position])
             myView.findViewById<TextView>(R.id.txtView).text = song
             return myView
         }
     }
}