package com.example.movieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_list")
data class MovieData (
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "") val
)

