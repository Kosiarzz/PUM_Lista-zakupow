package com.example.zakupy

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zakupy.MainActivity
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //odwolanie sie do bazy
        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        val save_notes_info = Toast.makeText(applicationContext,"Lista została zapisana!",Toast.LENGTH_SHORT)

        //sprawdzenie czy istnieje notatka
        if(intent.hasExtra("title")) title_details.setText(intent.getStringExtra("title"))
        if(intent.hasExtra("message")) message_details.setText((intent.getStringExtra("message")))


        /*zapis notatki po kliknieciu
        dodaj_do_listy.setOnClickListener {

            //zmienne przechowujace to co wpisalismy do pol tekstowych
            //aby umiescic cos w bazie trzeba najpierw stworzyc instancje klasy/dodac do niej dane i z niej do tabeli
            val title = title_details.text.toString() //to co wpisałem do tytuł
            val message = message_details.text.toString() //to co wpisałem do produkt

            if (!message.isNullOrEmpty() && !message.isNullOrEmpty()){
                val value = ContentValues()
                value.put(TableInfo.table_column_message,message)
                value.put(TableInfo.table_column_title,title)
                db.insertOrThrow(TableInfo.table_name, null, value)
            }
            else{
                Toast.makeText(applicationContext,"Puste pole",Toast.LENGTH_SHORT).show()
            }

            //sprawdzanie czy notatka istnieje
            if(intent.hasExtra("ID")){
                db.update(TableInfo.table_name, value, BaseColumns._ID+"=?",
                arrayOf(intent.getStringExtra("ID")))
                Toast.makeText(applicationContext,"Zmiany zostały zapisane!",Toast.LENGTH_SHORT).show()
            }
            else
            {
                if (!title.isNullOrEmpty() || !message.isNullOrEmpty()){
                    db.insertOrThrow(TableInfo.table_name,null, value)
                    save_notes_info.show()
                }
                else{
                    Toast.makeText(applicationContext,"Uzupełnij tytuł lub notatke",Toast.LENGTH_SHORT).show()
                }
            }*/


    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalis, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item?.itemId == R.id.save_button)
        {
            val dbHelper = DataBaseHelper(applicationContext)
            val db = dbHelper.writableDatabase
            //zmienne przechowujace to co wpisalismy do pol tekstowych
            //aby umiescic cos w bazie trzeba najpierw stworzyc instancje klasy/dodac do niej dane i z niej do tabeli
            val title = title_details.text.toString()
            val message = message_details.text.toString()

            val value = ContentValues()
            value.put(TableInfo.table_column_title,title)
            value.put(TableInfo.table_column_message,message)

            var nowaAktywnosc: Intent = Intent(applicationContext, MainActivity::class.java)

            //sprawdzanie czy notatka istnieje
            if(intent.hasExtra("ID")){
                db.update(TableInfo.table_name, value, BaseColumns._ID+"=?",
                    arrayOf(intent.getStringExtra("ID")))
                Toast.makeText(applicationContext,"Zmiany zostały zapisane!",Toast.LENGTH_SHORT).show()
                startActivity(nowaAktywnosc)
            }
            else
            {
                if (!title.isNullOrEmpty()){
                    db.insertOrThrow(TableInfo.table_name,null, value)
                    Toast.makeText(applicationContext,"Lista została zapisana!",Toast.LENGTH_SHORT).show()
                    startActivity(nowaAktywnosc)
                }
                else{
                    Toast.makeText(applicationContext,"Uzupełnij nazwe",Toast.LENGTH_SHORT).show()
                }
            }


        }

        return super.onOptionsItemSelected(item)
    }

}