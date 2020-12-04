package com.example.uiactivity2nd.models

class Album(var id: Int = 0, var title: String, var releaseDate: String) {
    val albumSongs: MutableList<Song> = arrayListOf()
    override fun toString(): String {
        return "$title - $releaseDate"
    }
}