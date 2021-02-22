package com.gtsl.breakingbad.presentation.characterlist

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gtsl.breakingbad.R
import com.gtsl.breakingbad.databinding.FragmentCharactersListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CharacterListFragment : Fragment() {

    private val viewModel: CharacterListViewModel by sharedViewModel()
    private lateinit var adapter: CharacterAdapter
    lateinit var binding: FragmentCharactersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        adapter = CharacterAdapter(viewModel)
        binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        binding.characterListRecyclerView.adapter = adapter
        binding.characterListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadCharacters()
        viewModel.characterListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        viewModel.characterClickLiveData.observe(viewLifecycleOwner, Observer { event ->

            event.getContentIfNotHandled()?.let {view ->
                val imageView = view.findViewById<ImageView>(R.id.character_image)
                val extras = FragmentNavigatorExtras(imageView to "imageView")
                val action = CharacterListFragmentDirections.navToCharacterDetailsFragment(id = "imageView")

                findNavController().navigate(action, extras)
//                findNavController().navigate(R.id.fragment_character_details)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        val searchView = (menu.findItem(R.id.action_search).actionView as SearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.updateContents(newText.orEmpty())
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                findNavController().navigate(R.id.fragment_season_appearance)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}