package com.aathil.mynotes

class Notes{
    var notesId : Int? = null
    var title: String? = null
    var notes: String? = null

    constructor(notesId: Int, title: String, notes: String){
        this.notesId = notesId
        this.title = title
        this.notes = notes
    }

}