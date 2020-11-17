package com.example.zakupy


import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view.view.*

class CardViewAdapter(val context: Context, val db:SQLiteDatabase, var notes: ArrayList<note>): RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cardView_note = layoutInflater.inflate(R.layout.card_view, parent, false)
        return MyViewHolder(cardView_note)
    }

    override fun getItemCount(): Int {
        val cursor = db.query(TableInfo.table_name, null, null,
            null,null, null, null)

        val liczba_wierszy = cursor.count
        cursor.close()
        return liczba_wierszy
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cardView_note = holder.view.view_cardView
        val title = holder.view.title_cardView
        val message = holder.view.message_cardView
        val context: Context = holder.view.context

        //nazwa tabeli, firltrowanie po ID ,dodanie jedynki aby liczylo od 1 a nie od 0
        val cursor = db.query(TableInfo.table_name, null, BaseColumns._ID + "=?",
            arrayOf(holder.adapterPosition.plus(1).toString()),null, null, null)

        title.setText(notes[holder.adapterPosition].title)
        message.setText(notes[holder.adapterPosition].message)


        if(cursor.moveToFirst())
        {
            title.setText(cursor.getString(1))
            message.setText(cursor.getString(2))
        }

        //wczytanie danych/notatki
        cardView_note.setOnClickListener{
            val intent_edit = Intent(context,DetailsActivity::class.java)
            val title_edit = notes[holder.adapterPosition].title
            val message_edit = notes[holder.adapterPosition].message
            val id_edit = notes[holder.adapterPosition].ID.toString()

            intent_edit.putExtra("title",title_edit)
            intent_edit.putExtra("message",message_edit)
            intent_edit.putExtra("ID",id_edit)

            context.startActivity(intent_edit)
        }

        /* //Kopiowanie danych
         cardView_note.setOnLongClickListener(object: View.OnLongClickListener{
             override fun onLongClick(p0: View?): Boolean {
                 val cm = context.getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
                 val clipdata = ClipData.newPlainText("copyText","Title: " + title.text+"\n"+"Message: "+message.text)
                 cm.setPrimaryClip(clipdata)

                 Toast.makeText(context,"Skopiowano",Toast.LENGTH_SHORT).show()
                 return true
             }

         })
         */

        cardView_note.setOnLongClickListener(object: View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                //usuniecie z bazy danych          nazwa tabeli, WHERE,jakieID
                db.delete(TableInfo.table_name,BaseColumns._ID+"=?", arrayOf(notes[holder.adapterPosition].ID.toString()))

                //usuniecie z widoku
                notes.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
                Toast.makeText(context,"Usunięto notatke",Toast.LENGTH_SHORT).show()
                return true
            }

        })
    }

}



class MyViewHolder(val view:View): RecyclerView.ViewHolder(view)