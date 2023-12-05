package com.example.mysimpletestapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
        val idView: TextView = itemView.findViewById(R.id.idView)
        val upcView: TextView = itemView.findViewById(R.id.upcView)
        val quantityView: TextView = itemView.findViewById(R.id.quantityView)
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
        holder.titleTextView.text = "Name: " + note.title
        holder.idView.text = "ID: " + note.id.toString()
        holder.upcView.text = "UPC: " + note.upc.toString()
        holder.quantityView.text = "Quantity: " + note.qty.toString()

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply{
                putExtra("key1", note.upc.toString())
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