package com.arafat1419.argames.core.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "games_entities")
@Parcelize
data class GameEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int? = 0,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "background_image")
    val backgroundImage: String? = null,

    @ColumnInfo(name = "released")
    val released: String? = null,

    @ColumnInfo(name = "rating")
    val rating: String? = null,
) : Parcelable