package com.tashuseyin.itunesapp.presentation.search.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tashuseyin.itunesapp.R
import com.tashuseyin.itunesapp.common.Constant
import com.tashuseyin.itunesapp.databinding.FragmentBottomSheetBinding
import com.tashuseyin.itunesapp.presentation.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels()
    private var mediaType = Constant.DEFAULT_MEDIA_TYPE
    private var mediaTypeId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readSelectMediaType()
        setListener()
    }


    private fun setListener() {
        binding.apply {
            mediaTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
                val chip = group.findViewById<Chip>(selectedChipId)
                val selectedMediaType = chip.text.toString().lowercase(Locale.ROOT)
                mediaType = selectedMediaType
                mediaTypeId = selectedChipId
            }

            binding.applyBtn.setOnClickListener {
                searchViewModel.saveSelectMediaType(mediaType, mediaTypeId)
                findNavController().navigate(BottomSheetFragmentDirections.actionBottomSheetFragmentToSearchFragment(true))
            }
        }
    }

    private fun readSelectMediaType() {
        lifecycleScope.launch {
            searchViewModel.readSelectMediaType.collect {
                mediaType = it.mediaType
                updateChip(it.mediaTypeId, binding.mediaTypeChipGroup)
            }
        }
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}