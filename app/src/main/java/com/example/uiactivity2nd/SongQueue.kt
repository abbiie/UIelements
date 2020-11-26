package com.example.uielements

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class SongQueue : AppCompatActivity() {
    private lateinit var NM : NotificationManager
    private lateinit var NC : NotificationChannel
    private lateinit var Build : Notification.Builder
    private val NCid = "i.apps.notifications"
    private val Description = "Test notification"

    private fun remove(list: Array<String>, item: Int): Array<String>{
        if(item < 0 || item >= list.size){
            return list }
        val list = list.toMutableList()
        list.removeAt(item)
        return list.toTypedArray()
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.remove, menu)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_queue)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.queue)
            val addQueue = findViewById<ListView>(R.id.queues)
        addQueue.adapter = adapter
        registerForContextMenu(addQueue)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.RQueue ->{
                MainActivity.queue = remove(MainActivity.queue, info.position)
                var adapter:ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.queue)
                adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.queue)
                val newQueue = findViewById<ListView>(R.id.queues)
                newQueue.adapter = adapter
                Toast.makeText(this, "Song removed from Queue", Toast.LENGTH_SHORT).show()
                if(MainActivity.queue.isEmpty()){
                    val intent = Intent(this, MainActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NC = NotificationChannel(NCid,Description, NotificationManager.IMPORTANCE_HIGH)
                        NC.enableLights(true)
                        NC.lightColor = Color.BLACK
                        NC.enableVibration(true)
                        NM.createNotificationChannel(NC)

                        Build = Notification.Builder(this,NCid).setContentTitle("QUEUE EMPTY").setContentText("Add Songs to your Queue Now!")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentIntent(pendingIntent)
                    }else{
                        Build = Notification.Builder(this)
                            .setContentTitle("Notifications Example")
                            .setContentText("This is a notification message")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentIntent(pendingIntent)
                    }
                    NM.notify(1234,Build.build())
                }
                return true
            }
            else -> super.onContextItemSelected(item) }
    }
}