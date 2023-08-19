package com.example.tvmovies.ui.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.tvmovies.R
import com.example.tvmovies.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        bindViews()

        return binding.root
    }

    private fun bindViews() {
        //Action to HomeFragment
        binding.backIcon.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_homeFragment)
        }

        val args = requireArguments()
        val imageBack = args.getString("IMAGE_BACK")

        binding.apply {
            imageBackground.load(imageBack) {
                crossfade(true)
                crossfade(1000)
            }
            movieImage.load(imageBack) {
                crossfade(true)
                crossfade(1000)
            }

            movieName.text = args.getString("NAME")
            movieGenres.text = args.getString("GENRES")
            rate.text = args.getString("RATE")
            date.text = args.getString("DATE")
            time.text = args.getString("TIME")
            language.text = args.getString("LANGUAGE")
            movieSummary.text = "${args.getString("SUMMARY")}..."

            btnWeb.setOnClickListener {
                val url = args.getString("WEB_SITE")
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                } catch (e: Exception) {
                    Log.d("Error", "No application can handle this request.")
                }
            }

        }

    }

}