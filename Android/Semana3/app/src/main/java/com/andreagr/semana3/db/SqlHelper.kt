package com.andreagr.semana3.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.andreagr.semana3.model.ZooAnimal
import java.lang.Exception

class SqlHelper(
    context : Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "user.db"
        private const val TBL_USER = "tlb_user"
        private const val ID = "id"
        private const val NAME = "name"
        private const val IMAGE = "image"
    }

    override fun onCreate(database: SQLiteDatabase?) {
        val sqlCreate = "CREATE TABLE $TBL_USER ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $NAME TEXT, $IMAGE TEXT)"
        database?.execSQL(sqlCreate)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sqlUpdate = "DROP TABLE IF EXISTS $TBL_USER"
        database?.execSQL(sqlUpdate)
        onCreate(database)
    }

    fun insert(zooAnimal: ZooAnimal) : Long{
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(NAME, zooAnimal.name)
            put(IMAGE, zooAnimal.image)
        }

        val result = db.insert(TBL_USER, null, contentValues)
        db.close()
        return result
    }

    fun getAllAnimals(): ArrayList<ZooAnimal>{
        val zooList = arrayListOf<ZooAnimal>()
        val query = "SELECT * FROM $TBL_USER"
        val db = readableDatabase
        val cursor : Cursor?

        try {
            cursor = db.rawQuery(query, null)
        }catch (e: Exception){
            e.printStackTrace()
            return zooList
        }

        var id : Int
        var name : String
        var image : String

        with(cursor){
            while (moveToNext()) {
                id = getInt(getColumnIndexOrThrow(ID))
                name = getString(getColumnIndexOrThrow(NAME))
                image = getString(getColumnIndexOrThrow(IMAGE))

                val animal = ZooAnimal(id, name, image)
                zooList.add(animal)
            }
        }
        cursor.close()
        return zooList
    }

    fun updateAnimal(animal : ZooAnimal) : Int{
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(ID, animal.id)
            put(NAME, animal.name)
            put(IMAGE, animal.image)
        }

        val result = db.update(TBL_USER, contentValues,"id=?", arrayOf("${animal.id}"))
        db.close()
        return result
    }

    fun deleteAnimal(userId: Int) : Int{
        val db = writableDatabase
        val result = db.delete(TBL_USER, "id=?", arrayOf("$userId"))
        db.close()
        return result
    }
}