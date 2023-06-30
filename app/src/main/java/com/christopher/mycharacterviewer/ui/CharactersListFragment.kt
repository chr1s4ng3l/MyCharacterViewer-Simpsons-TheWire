package com.christopher.mycharacterviewer.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.christopher.mycharacterviewer.R
import com.christopher.mycharacterviewer.adapter.CharactersRecyclerViewAdapter
import com.christopher.mycharacterviewer.databinding.FragmentItemListBinding
import com.christopher.mycharacterviewer.model.RelatedTopicModel
import com.christopher.mycharacterviewer.util.Resource
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class CharactersListFragment : Fragment() {

    private var _binding: FragmentItemListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrieveCharacters()

        subscribeObservers()

        setSearchSubmitListener()
    }

    private fun subscribeObservers() {
        subscribeObserver(viewModel.characters)
        subscribeObserver(viewModel.filteredCharacters)
    }

    private fun retrieveCharacters() {
        viewModel.getCharacters(getString(R.string.api_query_parameter))
    }

    private fun subscribeObserver(charactersListState: StateFlow<Resource<List<RelatedTopicModel>>>) {
        lifecycleScope.launch {
            charactersListState.collectLatest { state ->
                when (state) {
                    is Resource.Loading -> {
                        Log.i("API Response: ", "Loading...")
                    }
                    is Resource.Success -> {
                        Log.i("API Response: ", "Success")
                        binding.itemList.adapter = state.data?.let {
                            CharactersRecyclerViewAdapter(it) { view, position ->
                                run {
                                    // Leaving this not using view binding as it relies on if the view is visible the current
                                    // layout configuration (layout, layout-sw600dp)
                                    val itemDetailFragmentContainer: View? =
                                        view.findViewById(R.id.item_detail_nav_container)

                                    if (itemDetailFragmentContainer != null) {
                                        itemDetailFragmentContainer.findNavController()
                                            .navigate(R.id.fragment_item_detail)
                                    } else {
                                        view.findNavController().navigate(R.id.show_item_detail)
                                    }
                                }
                                viewModel.getCharacterDetails(it, position)
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i("API Response: ", "Error -> ${state.message}")
                        Toast.makeText(context, "Sorry, no results found.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun setSearchSubmitListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterCharacters(newText)
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}