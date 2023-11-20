package com.example.mysimpletestapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter (private var items: List<Item>, context: Context) : RecyclerView.Adapter<ItemAdapter.NoteViewHolder>() {

    private val db : ItemDatabaseHelper = ItemDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_for_cooler_view, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = items[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.upc

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply{
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteItem(note.id)
            refreshData(db.getAllItems())
            Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newItems: List<Item>){
        items = newItems
        notifyDataSetChanged()
    }

}