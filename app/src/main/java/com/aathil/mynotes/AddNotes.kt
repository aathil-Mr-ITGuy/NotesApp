package com.aathil.mynotes

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.media.projection.MediaProjection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*

class AddNotes : AppCompatActivity() {

    val dbTable = "Notes"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

//        //actionbar
        val actionbar = supportActionBar
//        //set actionbar title
        actionbar!!.title = "Add Note"
//        //set back button
//        actionbar.setDisplayHomeAsUpEnabled(true)
//        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    fun addNote(view: View){
        var dbManager  = DbManager(this)

        //values
        var values = ContentValues()

        values.put("Title",titleInput.text.toString() )
        values.put("Notes",notesInput.text.toString() )
        val ID = dbManager.Insert(values)

        if(ID > 0){
           // Toast.makeText(this, "Note is Added", Toast.LENGTH_LONG).show()

            result.text  =  "Note is Added"

            goHome()
        }
        else{
            Toast.makeText(this, "Cannot Add", Toast.LENGTH_LONG).show()
        }

    }

    fun goHome(){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}