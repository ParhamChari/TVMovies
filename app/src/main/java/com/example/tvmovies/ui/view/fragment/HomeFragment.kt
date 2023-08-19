package com.example.tvmovies.ui.view.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tvmovies.R
import com.example.tvmovies.data.event.ItemTvShowClickListener
import com.example.tvmovies.data.model.TvShowsItem
import com.example.tvmovies.databinding.FragmentHomeBinding
import com.example.tvmovies.ui.adapter.TvShowsAdapter
import com.example.tvmovies.ui.view.activity.MainActivity
import com.example.tvmovies.ui.viewmodel.TvShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class HomeFragment : Fragment(), ItemTvShowClickListener {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel : TvShowsViewModel by viewModels()
    private lateinit var showsAdapter: TvShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        showViews()

        return binding.root
    }

    private fun bindViews() {
        showsAdapter = TvShowsAdapter(this)

        viewModel.responseTvShows.observe(viewLifecycleOwner) { response->
            showsAdapter.tvShowModel = response
        }

        // initializing recycler view
        binding.recyclerview.apply {
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = showsAdapter
        }

    }


    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun showViews() {
        if (checkForInternet(binding.root.context)) {
            bindViews()
        }
        else {

            AlertDialog.Builder(binding.root.context).apply {
                setTitle("Internet Error")
                setMessage("Please check your internet connection")

                setPositiveButton("RETRY") { _, _ ->
                    showViews()
                }

                setNegativeButton("EXIT") { _, _ ->
                    MainActivity().finish()
                    requireActivity().moveTaskToBack(true)
                    exitProcess(-1)
                }

                setCancelable(false)
            }.create().show()
        }

    }

    override fun onItemClick(tvShow: TvShowsItem) {

        val bundle = Bundle().apply {
            putString("IMAGE_BACK", tvShow.image.original)
            putString("NAME", tvShow.name)
            putString("GENRES", tvShow.genres.toString())
            putString("RATE", tvShow.rating.average.toString())
            putString("DATE", tvShow.premiered)
            putString("TIME", tvShow.averageRuntime.toString())
            putString("LANGUAGE", tvShow.language)
            putString("SUMMARY",tvShow.summary)
            putString("WEB_SITE",tvShow.officialSite)
        }
        //Action to HomeFragment
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)

    }

}