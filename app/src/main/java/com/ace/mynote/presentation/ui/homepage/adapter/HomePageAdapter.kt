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
        val binding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    class NoteItemViewHolder(
        private val binding: ItemNoteBinding,
        private val listener: NoteItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: NoteEntity) {
            with(item) {
                val test = noteContent
                binding.tvNoteTitle.text = noteTitle
                binding.tvNoteDescription.text = noteDescription
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

interface NoteItemClickListener {
    fun onItemClicked(item: NoteEntity)
    fun onDeleteMenuClicked(item: NoteEntity)
    fun onEditMenuClicked(item: NoteEntity)
}
