package com.gtsl.breakingbad.presentation.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.gtsl.breakingbad.databinding.FragmentCharacterDetailsBinding
import com.gtsl.breakingbad.presentation.characterlist.CharacterListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CharacterDetailsFragment : Fragment() {

    private val viewModel: CharacterListViewModel by sharedViewModel()
    lateinit var binding: FragmentCharacterDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characterInfoLiveData.observe(viewLifecycleOwner, Observer {
            binding.characterInfo = it
            binding.characterDetailsImage.transitionName = "imageView"
            binding.executePendingBindings()
        })
    }
}
