package com.christopher.mycharacterviewer.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.christopher.mycharacterviewer.databinding.FragmentItemDetailBinding
import com.christopher.mycharacterviewer.util.Resource
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentItemDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        subscribeObserver()

        return rootView
    }

    private fun subscribeObserver() {
        lifecycleScope.launch {
            viewModel.character.collectLatest { state ->
                when(state) {
                    is Resource.Loading -> {
                        Log.i("API Response: ", "Loading...")
                    }
                    is Resource.Success -> {
                        Log.i("API Response: ", "Success")
                        Picasso.get()
                            .load("https://duckduckgo.com"+ state.data?.icon!!.uRL)
                            .into(binding.characterImage)
                        val charNameAndDesc = state.data?.text?.split("-")
                        binding.characterName.text = charNameAndDesc?.get(0)
                        binding.characterDesc.text = charNameAndDesc?.get(1)
                    }
                    is Resource.Error -> {
                        Log.i("API Response: ", "Error -> ${state.message}")
                        Toast.makeText(context, "Sorry, no results found.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}