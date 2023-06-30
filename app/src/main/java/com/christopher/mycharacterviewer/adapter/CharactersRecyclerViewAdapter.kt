package com.christopher.mycharacterviewer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.christopher.mycharacterviewer.databinding.ItemListContentBinding
import com.christopher.mycharacterviewer.model.RelatedTopicModel

class CharactersRecyclerViewAdapter(
    private val charactersList: List<RelatedTopicModel>,
    private var onItemClicked: ((itemView: View, position: Int) -> Unit)
) :
    RecyclerView.Adapter<CharactersRecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCharacter = charactersList[position]
        holder.itemCharacterTitle.text = currentCharacter.text

        holder.itemView.setOnClickListener {
            onItemClicked(holder.itemView, position)
        }
    }

    override fun getItemCount() = charactersList.size

    inner class ViewHolder(binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemCharacterTitle: TextView = binding.itemCharacterTitle
    }

}