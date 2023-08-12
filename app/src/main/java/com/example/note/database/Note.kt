package com.example.note.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String? = "",

    @ColumnInfo(name = "content")
    val content: String?,

    @ColumnInfo(name = "create_date")
    val createDate: LocalDateTime = LocalDateTime.now()
)

fun Note.getTimeFormat(): String = createDate
    .format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))

fun getCurrentTime(): String = LocalDateTime.now()
    .format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))

class Converters {
    @TypeConverter
    fun timeToString(time: LocalDateTime): String = time.toString()

    @TypeConverter
    fun stringToTime(string: String): LocalDateTime = LocalDateTime.parse(string)
}