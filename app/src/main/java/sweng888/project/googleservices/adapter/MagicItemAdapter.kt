package edu.psu.sweng888.lessonfive_fragmentsui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import sweng888.project.googleservices.R
import sweng888.project.googleservices.data.MagicItem

class MagicItemAdapter(private val magic_item_list: ArrayList<MagicItem>) :
    RecyclerView.Adapter<MagicItemAdapter.MagicItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MagicItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.magic_items_list_item, parent, false)
        val cardView = view.findViewById<View>(R.id.book_card_view) as CardView
        cardView.useCompatPadding = true // Optional: adds padding for pre-lollipop devices
        return MagicItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MagicItemViewHolder, position: Int) {
        val book: MagicItem = magic_item_list[position]
        holder.magic_item_name.text = book.item_name
        holder.magic_item_rarity.text = book.rarity
        holder.magic_item_description.text = book.description
    }

    /** Added a new method to include a new Magic item, and update the list
     * This will dynamically update the RecyclerView to incorporate the new Book */
    fun addBook(book: MagicItem) {
        magic_item_list.add(book)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return magic_item_list.size
    }

    class MagicItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val magic_item_name: TextView = itemView.findViewById(R.id.magic_item_name)
        val magic_item_rarity: TextView = itemView.findViewById(R.id.magic_item_rarity)
        val magic_item_description: TextView = itemView.findViewById(R.id.magic_item_description)
    }
}