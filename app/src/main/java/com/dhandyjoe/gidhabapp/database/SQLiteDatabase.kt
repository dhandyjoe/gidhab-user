package com.dhandyjoe.gidhabapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dhandyjoe.gidhabapp.model.DetailUser
import com.dhandyjoe.gidhabapp.model.User

class SQLiteDatabase(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "user.db"
        private const val TABLE_NAME = "FavoriteUsers"

        // all coloumn database

        private const val COLOUMN_ID = "_id"
        private const val COLOUMN_USERNAME = "_username"
        private const val COLOUMN_NAME = "_name"
        private const val COLOUMN_IMAGE = "_image"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val create = ("CREATE TABLE " + TABLE_NAME + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY, " +
                COLOUMN_USERNAME + " TEXT, " +
                COLOUMN_NAME + " TEXT, " +
                COLOUMN_IMAGE + " TEXT)")
        p0?.execSQL(create)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE $TABLE_NAME")
        onCreate(p0)
    }

    fun addFavorite(detailUser: DetailUser): Long {
        val db = this.writableDatabase
        val content = ContentValues()

        content.put(COLOUMN_ID, detailUser.id)
        content.put(COLOUMN_USERNAME, detailUser.login)
        content.put(COLOUMN_NAME, detailUser.name)
        content.put(COLOUMN_IMAGE, detailUser.avatar_url)

        val result = db.insert(TABLE_NAME, null, content)
        db.close()
        return result
    }

    fun queryById(id: String): Cursor {
        val db = readableDatabase
        return db.query(
            TABLE_NAME,
            null,
            "$COLOUMN_ID=?",
            arrayOf(id),
            null,
            null,
            null
        )
    }

    fun deleteFavorite(detailUser: DetailUser): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, COLOUMN_ID + "=" + detailUser.id, null)
        db.close()
        return result
    }

    fun getFavoriteMovie(): ArrayList<User>{
        val favoriteUser = ArrayList<User>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"

        val cursor: Cursor = db.rawQuery(query, null)

        if(cursor.moveToFirst()) {
            do {
                val place = User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLOUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLOUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLOUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLOUMN_IMAGE))
                )
                favoriteUser.add(place)
            } while (cursor.moveToNext())
        }
        return favoriteUser
    }
}