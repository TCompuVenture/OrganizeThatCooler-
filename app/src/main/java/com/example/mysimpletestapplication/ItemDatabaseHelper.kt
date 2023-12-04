package com.example.mysimpletestapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ItemDatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_Version){

    companion object{
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_Version = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_UPC = "upc"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_QUANTITY = "quantity"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_UPC TEXT, $COLUMN_QUANTITY TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertItem(item: Item){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, item.title)
            put(COLUMN_UPC, item.upc)
            put(COLUMN_QUANTITY, item.qty) //Should break stuff
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllItems(): List<Item> {
        val notesList = mutableListOf<Item>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"// WHERE $COLUMN_QUANTITY > 0
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val upc = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_UPC))
            val qty = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))
            val item = Item(id, title, upc, qty) //Passing in dummy qty for now
            notesList.add(item)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateItem(item: Item){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, item.title)
            put(COLUMN_UPC, item.upc)
            put(COLUMN_QUANTITY, item.qty) //Should break stuff
        }
        val whereClause = "$COLUMN_UPC = ?"
        val whereArgs = arrayOf(item.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getNoteByID(noteId: Int): Item{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val upc = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_UPC))
        //val upc = cursor.getColumnIndexOrThrow(COLUMN_CONTENT)
        val qty = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))

        //remember to add val upc here!

        cursor.close()
        db.close()
        return Item(id, title, upc, qty) //Passing in dummy qty for now
    }
    @SuppressLint("Range") //Ignoring the fact that .getColumnIndex can return a negative value
    fun getNoteByUPC(upc: Long): Item{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_UPC = $upc"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        //If nothing found in DB, -1 for everything will be returned
        var id = -1;
        var title = "-1";
        var upc = -1L;
        var qty = -1;

        if(cursor != null && cursor.moveToFirst()){ //So if we find something in the DB, return it
            id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)) //Must be getColumnIndex so it will throw a -1 if not found. But how make it be OK with a negative 1 coming back?
            title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            upc = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_UPC))
            qty = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))
            cursor.close();
        }
        else
        {

        }
       // cursor.close()

        db.close()
        return Item(id, title, upc, qty) //Passing in dummy qty for now
    }

    fun deleteItem(noteId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}