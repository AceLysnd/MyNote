package com.ace.mynote.presentation.ui.homepage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ace.mynote.R
import com.ace.mynote.data.local.database.entity.NoteEntity
import com.ace.mynote.databinding.ItemNoteBinding

class HomePageAdapter(
    private val listener: NoteItemClickListener
) : RecyclerView.Adapter<HomePageAdapter.NoteItemViewHolder>() {

    private var items: MutableList<NoteEntity> = mutableListOf()

    fun setItems(items: List<NoteEntity>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }
    fun addItems(items: List<NoteEntity>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        val view =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteItemViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    class NoteItemViewHolder(
        var binding: ItemNoteBinding,
        private val listener: NoteItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: NoteEntity) {
            with(item) {
                binding.tvNoteTitle.text = noteTitle
                binding.tvNoteDescription.text = noteDescription
                binding.ivDelete.setOnClickListener{ listener.onDeleteMenuClicked(item)}
            }
        }
    }

    override fun getItemCount(): Int = items.size
}

interface NoteItemClickListener {
    fun onItemClicked(item: NoteEntity)
    fun onDeleteMenuClicked(item: NoteEntity)
    fun onEditMenuClicked(item: NoteEntity)
}
