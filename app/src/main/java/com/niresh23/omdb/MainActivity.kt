package com.niresh23.omdb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.niresh23.omdb.base.AppError
import com.niresh23.omdb.base.Constants
import com.niresh23.omdb.databinding.ActivityMainBinding
import com.niresh23.omdb.search.SearchParamBottomSheetFragment
import com.niresh23.omdb.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationController = findNavController(R.id.nav_host_fragment_content_main)

        navigationController.addOnDestinationChangedListener { conroller, destination, bundle ->
            when(destination.id) {
                R.id.DetailsFrament -> {
                    binding.appBarBtn.setImageResource(R.drawable.arrow_1)
                    binding.appBarBtn.setOnClickListener {
                        findNavController(R.id.nav_host_fragment_content_main).popBackStack()
                    }
                }

                R.id.SearchFragment -> {
                    binding.appBarBtn.setImageResource(R.drawable.search)
                    binding.appBarBtn.setOnClickListener {
                        openSearchRequestParamsDialog()
                    }
                }
            }
        }

        observe()
    }

    private fun observe() {
        mainViewModel.showDetailsLiveData.observe(this) {
            findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_SearchFragment_to_DetailsFragment)
        }

        mainViewModel.openImdbWebSiteLiveData.observe(this) {
            val browserIntent =  Intent(Intent.ACTION_VIEW, Uri.parse(Constants.IMDb_URL + it));
            startActivity(browserIntent);
        }

        searchViewModel.errorLiveData.observe(this) { appError ->
            when(appError) {
                is AppError.NoInternetConnection -> {
                    val request = NavDeepLinkRequest.Builder.fromUri(
                        resources.getString(R.string.no_internet_connection_intent_uri).toUri()
                    ).build()
                    navigationController.navigate(request)
                }

                is AppError.PageNotFound -> {

                }

                else -> {}
            }
        }

        searchViewModel.searchDataLiveData.observe(this) {
            if (navigationController.currentDestination?.id == R.id.NoInternetConnectionFragment) {
                navigationController.navigate(R.id.action_NoInternetConnectionFragment_to_SearchFragment)
            }
        }
    }

    private fun openSearchRequestParamsDialog() {
        val fragment = SearchParamBottomSheetFragment()
        fragment.show(supportFragmentManager, "Search Fragment")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        findNavController(R.id.nav_host_fragment_content_main).handleDeepLink(intent)
    }
}