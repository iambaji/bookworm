package com.bajishaik.bookie.screens.readingnow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bajishaik.bookie.databinding.FragmentReadingnowBinding

class ReadingNowFragment : Fragment() {

    private lateinit var binding : FragmentReadingnowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentReadingnowBinding.inflate(layoutInflater)
        return binding.root
    }
}