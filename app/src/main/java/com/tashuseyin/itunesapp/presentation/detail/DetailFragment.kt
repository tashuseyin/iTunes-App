package com.tashuseyin.itunesapp.presentation.detail

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.tashuseyin.itunesapp.databinding.FragmentDetailBinding
import com.tashuseyin.itunesapp.presentation.binding_adapter.BindingFragment


class DetailFragment : BindingFragment<FragmentDetailBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentDetailBinding::inflate


}