package com.tashuseyin.itunesapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.tashuseyin.itunesapp.databinding.FragmentDetailBinding
import com.tashuseyin.itunesapp.presentation.binding_adapter.BindingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BindingFragment<FragmentDetailBinding>() {
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDetailBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.id
        println(id)
        detailViewModel.getDetailApi(id = args.id)

    }

}