package com.example.zakupy

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

//BUDOWA TABELI (JAK WYGLADA)
object TableInfo: BaseColumns{
    const val table_name = "zakupy"
    const val table_column_title = "title"
    const val table_column_message = "message"
}

//TWORZENIE TABELI
object BasicCommand{
    const val SQL_CREATE_TABLE = "CREATE TABLE ${TableInfo.table_name} ("+
            "${BaseColumns._ID} INTEGER PRIMARY KEY,"+
            "${TableInfo.table_column_title} TEXT NOT NULL,"+
            "${TableInfo.table_column_message} TEXT NOT NULL)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TableInfo.table_name}"

}

//SQLiteOpenHelber - umozliwia tworzenie i aktualizacje bazy

class DataBaseHelper(context: Context): SQLiteOpenHelper(context, TableInfo.table_name, null, 1){
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(BasicCommand.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(BasicCommand.SQL_DELETE_TABLE)
        onCreate(p0)
    }

}