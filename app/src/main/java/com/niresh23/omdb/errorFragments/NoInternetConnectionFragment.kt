package com.niresh23.omdb.errorFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.niresh23.omdb.databinding.NoInternetConectionBinding

class NoInternetConnectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return NoInternetConectionBinding.inflate(inflater, container, false).root
    }
}