package com.gtsl.breakingbad.presentation.apperance

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.view.children
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.gtsl.breakingbad.R
import com.gtsl.breakingbad.databinding.FragmentSeasonAppearanceBinding
import com.gtsl.breakingbad.databinding.ItemSeasonAppearanceBinding
import com.gtsl.breakingbad.presentation.characterlist.CharacterListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AppearanceFragment : BottomSheetDialogFragment() {

    private val NumSeasons = listOf("Season 1", "Season 2", "Season 3", "Season 4", "Season 5")

    private val viewModel: CharacterListViewModel by sharedViewModel()
    private lateinit var binding: FragmentSeasonAppearanceBinding

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppearanceDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeasonAppearanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (season in NumSeasons) {
            val viewBinding = ItemSeasonAppearanceBinding.inflate(layoutInflater)
            viewBinding.appearanceInfo = AppearanceInfo(season, viewModel.selectedAppearances.contains(season))
            binding.seasonAppearanceList.addView(viewBinding.root)
        }

        binding.seasonAppearanceApply.setOnClickListener {
            updateFilters()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (view?.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }

    private fun updateFilters() {
        val selectedAppearances = binding.seasonAppearanceList.children.toList()
            .map { it as Chip }
            .filter { it.isChecked }
            .map { it.text }

        viewModel.updateAppearances(selectedAppearances)
        dismiss()
    }
}