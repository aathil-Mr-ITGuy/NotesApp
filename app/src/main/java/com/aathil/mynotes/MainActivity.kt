package com.aathil.mynotes

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ActionMenuView
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


private var notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        notesList.add(Notes(1, "Note 1", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's and typesetting industry. Lorem Ipsum has been the industry's " ))
//        notesList.add(Notes(2, "Note 2", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's and typesetting industry. Lorem Ipsum has been the industry's " ))
//        notesList.add(Notes(3, "Note 3", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's and typesetting industry. Lorem Ipsum has been the industry's " ))
//        notesList.add(Notes(4, "Note 4", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's and typesetting industry. Lorem Ipsum has been the industry's " ))
//        notesList.add(Notes(5, "Note 5", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's and typesetting industry. Lorem Ipsum has been the industry's " ))
//        notesList.add(Notes(6, "Note 6", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's and typesetting industry. Lorem Ipsum has been the industry's " ))
//        notesList.add(Notes(7, "Note 7", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's and typesetting industry. Lorem Ipsum has been the industry's " ))
//
//        var myListAdapter = MyNotesAdapter(notesList)
//        listView.adapter = myListAdapter

        loadQuery("%")

    }

    private fun loadQuery(title: String){

        var dbManager = DbManager(this)
        var projection = arrayOf("ID", "Title","Notes")
        var selectionArgs = arrayOf(title)

        var cursor = dbManager.insertQuery(projection, "Title Like ?", selectionArgs, "Title")

        notesList.clear()

        if(cursor.moveToFirst()){
            do {
                val id =cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val notes = cursor.getString(cursor.getColumnIndex("Notes"))

                notesList.add(Notes(id, title, notes))

            }while(cursor.moveToNext())
        }

        var myListAdapter = MyNotesAdapter(notesList)
        listView.adapter = myListAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)

        val searchView : SearchView = menu?.findItem(R.id.search)?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                loadQuery("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item != null){
            when(item.itemId){
                R.id.add -> {
                    var intent = Intent(this, AddNotes::class.java)
                    startActivity(intent)

                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyNotesAdapter: BaseAdapter{
        var listNotesAdapter = ArrayList<Notes>()
        constructor(listNotesAdapter: ArrayList<Notes>):super(){
            this.listNotesAdapter = listNotesAdapter
        }

        override fun getCount(): Int {
            return listNotesAdapter.size
        }

        override fun getItem(p0: Int): Any {
            return listNotesAdapter[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var rowView = layoutInflater.inflate(R.layout.notes, null, true)
            var myNotes = listNotesAdapter[position]

            val titleText = rowView.findViewById(R.id.titleText) as TextView
            val details = rowView.findViewById(R.id.details) as TextView

            titleText.text = myNotes.title
            details.text = myNotes.notes

           return  rowView
        }
    }
}