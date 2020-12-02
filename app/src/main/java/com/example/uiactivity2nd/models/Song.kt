package com.example.uiactivity2nd.models

class Song (var id: Int = 0, var title: String, var artist: String, var album: String) {
    override fun toString(): String {
        return "$title by $artist - $album"
    }
}