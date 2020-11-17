package com.example.zakupy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zakupy.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
    }

    fun onClickTakeNotes(v: View){
        val intent = Intent(applicationContext, DetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        widok()
    }

    fun widok(){
        //stworzenie obiektu
        val dbHelper = DataBaseHelper(applicationContext)
        //rozmowa z baza - writable pozwala zapisywac i odczytywac
        val db = dbHelper.writableDatabase

        val cursor = db.query(TableInfo.table_name, null, null,
            null,null, null, null)

        val notes = ArrayList<note>()
        if(cursor.count > 0){
            cursor.moveToFirst()
            while(!cursor.isAfterLast){
                val note = note()
                note.ID = cursor.getInt(0)
                note.title = cursor.getString(1)
                note.message = cursor.getString(2)
                notes.add(note)
                cursor.moveToNext()
            }
        }
        cursor.close()

        recyler_view.layoutManager = GridLayoutManager(applicationContext, 1)
        recyler_view.adapter = CardViewAdapter(applicationContext,db, notes)
    }
}