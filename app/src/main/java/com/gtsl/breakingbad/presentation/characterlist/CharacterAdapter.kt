package com.gtsl.breakingbad.presentation.characterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gtsl.breakingbad.R
import com.gtsl.breakingbad.databinding.ItemCharacterBinding
import com.gtsl.breakingbad.presentation.characterlist.uimodel.UiCharacterInfo

class CharacterAdapter(
    private val viewModel: CharacterListViewModel
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val data = arrayListOf<UiCharacterInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = DataBindingUtil.inflate<ItemCharacterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_character,
            parent,
            false
        )
        return CharacterViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int =
        data.size

    fun setData(uiCharacters: List<UiCharacterInfo>) {
        data.clear()
        data.addAll(uiCharacters)
        notifyDataSetChanged()
    }

    class CharacterViewHolder(
        private val binding: ItemCharacterBinding,
        private val viewModel: CharacterListViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiCharacter: UiCharacterInfo) {
            binding.characterInfo = uiCharacter
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }
}