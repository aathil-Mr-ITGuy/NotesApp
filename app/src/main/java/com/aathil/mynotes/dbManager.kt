package com.aathil.mynotes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import android.widget.Toast
import java.lang.Exception

class DbManager{
    val dbName = "MyNotes"
    val dbTable = "Notes"
    val colId = "ID"
    val colTitle ="Title"
    val colNotescolTitle = "Notes"
    val dbVersion = 1
    //create table
//    val sqlCreateTable  = "CREATE TABLE IF NOT EXISTS"+dbName+ ""+ dbTable +" ("+ colId +" INTEGER PRIMARY KEY,"+ colTitle + " TEXT, " + colNotes + " TEXT);"


    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS $dbTable (" +
    "$colId INTEGER PRIMARY KEY," +
    "$colTitle TEXT," +
    "$colNotescolTitle TEXT)"

    var sqlDb : SQLiteDatabase? = null

    constructor(context: Context){
        var db=DatabaseHelperNotes(context)
        sqlDb  = db.writableDatabase
    }

    inner class DatabaseHelperNotes:SQLiteOpenHelper{
        var context:Context? = null
        constructor(context: Context):super(context, dbName, null,dbVersion ){
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            try{
                db!!.execSQL(sqlCreateTable)
                Toast.makeText(this.context," database is created", Toast.LENGTH_LONG).show()
            }catch (ex: Exception){
                Log.d("Error", ex.toString())
            }

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS $dbTable")
        }

    }

    fun Insert(values: ContentValues):Long{

        val ID= sqlDb!!.insert(dbTable,"",values)
        return ID
    }

    fun insertQuery(projection: Array<String>, selection: String, selectionArgs: Array<String>, sortOrder: String ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = dbTable
        return queryBuilder.query(sqlDb, projection, selection, selectionArgs,  null, null, sortOrder)
    }

}